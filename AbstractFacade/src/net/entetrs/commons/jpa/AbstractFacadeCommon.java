package net.entetrs.commons.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.entetrs.commons.logs.LogUtils;
import net.entetrs.commons.logs.LogUtils.LogLevel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Facade abstraite générique CRUD pour les entités, paramétrée par "generics"
 * Cette classe abstraite ne sert qu'à factoriser du code entre les facades
 * abstraites Java SE et Java EE.
 * 
 * @author CDT RBN
 * @version 2.0
 * 
 */
public abstract class AbstractFacadeCommon<T>
{
	public static final String MESSAGE_CREATE = "Ecriture en base de %s.";
	public static final String MESSAGE_UPDATE = "Mise à jour en base de %s.";
	public static final String MESSAGE_DELETE = "Suppression en base de %s.";
	public static final String MESSAGE_CANNOT_CREATE_INSTANCE = "Erreur pendant l'instanciation de la classe %s : %s.";

	/**
	 * référence vers le log de cette classe.
	 */
	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * référence vers le PersistenceUnitUtil de l'entityManager courant. Cette
	 * référence est récupérée en PostConstruct.
	 */
	protected PersistenceUnitUtil puu;
	
	private EntityFactory <T> entityFactory;

	/**
	 * retourne l'instance de l'entity manager en cours. cette méthode est
	 * abstraite car seul un EJB du module EJB-JAR pourra obtenir l'injection
	 * correcte d'un entity manager.
	 * 
	 * @return instance de l'entity manager.
	 * 
	 */
	abstract protected EntityManager getEntityManager();
	
	/**
	 * méthode déclenchée après construction pour récupérer un PersistenceUnitUtil.
	 */
	@PostConstruct
	public void init()
	{			
		Class<?> thisClass = this.getClass();
		ParameterizedType paramType = (ParameterizedType) thisClass.getGenericSuperclass();
		@SuppressWarnings("unchecked")
		Class <T> parameterizedType = (Class<T>) paramType.getActualTypeArguments()[0];
		entityFactory = new EntityFactory<T>(parameterizedType);
		
		// si l'entity manager n'est pas null, alors on récupère une instance de PersistenceUnitUtil.
		puu = (this.getEntityManager() == null) ? null : this.getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil();
	}

	/**
	 * crée une nouvelle instance du type paramétrée &lt;T&gt; de la classe.
	 * 
	 * @return nouvelle instance
	 */
	public T newInstance()
	{
		return entityFactory.newInstance();
	}

	/**
	 * renvoie la classe du type paramétré de la classe &lt;T&gt;.
	 *
	 * @return la définition d'une classe (objet Class)
	 */
	public Class<T> getBusinessClass()
	{
		return entityFactory.getConcreteClass();
	}

	/**
	 * ajoute une nouvelle entity au contexte JPA (persist).
	 * 
	 * @param t
	 *            instance d'une entité à ajouter.
	 */
	public void create(T t)
	{
		this.getEntityManager().persist(t);
		LogUtils.logFormat(log, LogLevel.INFO, MESSAGE_CREATE, t);
	}

	/**
	 * met à jour les données portées par l'instance de T (merge).
	 * 
	 * @param t
	 */
	public T update(T t)
	{
		t = this.getEntityManager().merge(t);
		LogUtils.logFormat(log, LogLevel.INFO, MESSAGE_UPDATE, t);
		return t;
	}

	/**
	 * supprime l'instance de T.
	 * 
	 * @param t
	 */
	public void delete(T t)
	{
		T attachedEntity = (T) this.getEntityManager().getReference(this.getBusinessClass(), puu.getIdentifier(t));
		this.getEntityManager().remove(attachedEntity);
		LogUtils.logFormat(log, LogLevel.INFO, MESSAGE_DELETE, t);
	}

	/**
	 * récupère toutes les instances de l'entité T.
	 * 
	 * @return liste des instances, éventuellement triées.
	 */
	public List<T> readAll()
	{
		return this.readAll((String[]) null);
	}

	/**
	 * récupère toutes les instances de l'entité T, éventuellement triées par
	 * ordre descendant (DESC) sur une liste d'attributs.
	 * 
	 * @return liste des instances, éventuellement triées.
	 */
	public List<T> readAll(String... orderBy)
	{
		CriteriaBuilder qb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> c = qb.createQuery(this.getBusinessClass());
		Root<T> from = c.from(this.getBusinessClass());
		this.addSort(qb, c, from, orderBy);
		TypedQuery<T> query = this.getEntityManager().createQuery(c);
		return query.getResultList();
	}

	/**
	 * lit une instance de T dont l'ID est passé en paramètre (find).
	 * 
	 * @param id
	 * @return instance d'une entité
	 */
	public T read(Object id)
	{
		return (T) this.getEntityManager().find(this.getBusinessClass(), id);
	}

	/**
	 * effectue une recherche simple sur la valeur d'un attribut avec
	 * l'opérateur d'égalité. Le résultat est trié (ou non) selon une liste
	 * d'attributs par ordre "ASC".
	 * 
	 * @param parameterName
	 *            attribut testé.
	 * @param parameterValue
	 *            valeur à tester.
	 * @param orderBy
	 *            liste d'attributs pour le tri ascendant.
	 * @return liste triée des entités correpondant à la recherche.
	 */
	public List<T> search(String parameterName, Object parameterValue, String... orderBy)
	{
		CriteriaBuilder qb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> c = qb.createQuery(this.getBusinessClass());
		Root<T> from = c.from(this.getBusinessClass());
		Predicate restriction = qb.equal(from.get(parameterName), parameterValue);
		c.where(restriction);
		this.addSort(qb, c, from, orderBy);
		TypedQuery<T> query = this.getEntityManager().createQuery(c);
		return query.getResultList();
	}

	/**
	 * cherche la première occurrence de la classe T où l'attribut passé en
	 * paramètre est égal à l'objet passé en paramètre.
	 * 
	 * @param parameterName
	 *            attribut à tester
	 * @param parameterValue
	 *            valeur à trouver
	 * @return instance de T, ou alors retourne null si aucune occurence n'a été
	 *         trouvée..
	 */
	public T searchFirstResult(String parameterName, Object parameterValue)
	{
		CriteriaBuilder qb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> c = qb.createQuery(this.getBusinessClass());
		Root<T> from = c.from(this.getBusinessClass());
		Predicate restriction = qb.equal(from.get(parameterName), parameterValue);
		c.where(restriction);
		TypedQuery<T> query = this.getEntityManager().createQuery(c);
		query.setMaxResults(1);
		try
		{
			return query.getSingleResult();
		}
		catch (javax.persistence.NoResultException ex)
		{
			return null;
		}
	}

	/**
	 * retourne le nombre d'occurences (tuples) en base de données de la classe
	 * T.
	 * 
	 * @return nombre d'occurences.
	 */
	public Long count()
	{
		CriteriaBuilder qb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> c = qb.createQuery(Long.class);
		c.select(qb.count(c.from(this.getBusinessClass())));
		return this.getEntityManager().createQuery(c).getSingleResult();
	}
	
	/**
	 * permet de savoir si la table associée à cette classe ne contient aucun tuple
	 * en base de données.
	 * 
	 * @return
	 * 		true si la table est vide, false dans le cas contraire.
	 */
	public boolean isEmpty()
	{
		return (this.count() == 0);
	}

	/**
	 * retourne "true" si une entité contient un attribut dont la valeur est
	 * spécifiée.
	 * 
	 * @param parameterName
	 *            attribut à tester
	 * @param parameterValue
	 *            valeur à tester
	 * @return vrai si une entité a été trouvé, false dans le cas contraire.
	 */
	public boolean exists(String parameterName, Object parameterValue)
	{
		CriteriaBuilder qb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> c = qb.createQuery(Long.class);
		c.select(qb.count(c.from(this.getBusinessClass())));
		Root<T> from = c.from(this.getBusinessClass());
		Predicate restriction = qb.equal(from.get(parameterName), parameterValue);
		c.where(restriction);
		Long found = this.getEntityManager().createQuery(c).getSingleResult();
		return (found != 0);
	}

	/**
	 * Rafraichit l'instance en mémoire par rapport à la base de données.
	 * 
	 * @param t
	 *            instance à relire depuis la base de données.
	 * 
	 */
	public void refresh(T t)
	{
		t = (T) this.getEntityManager().getReference(this.getBusinessClass(), puu.getIdentifier(t));
		this.getEntityManager().refresh(t);
	}

	/**
	 * Méthode qui effectue une recherche "paginée".
	 * 
	 * @author SCH FRANQUIN
	 * @author CDT ROBIN
	 * 
	 * @param filters
	 *            les filtres sur le champs de type String.
	 * @param sortColumn
	 *            colonne de tri.
	 * @param sortOrder
	 *            l'ordre de tri (ASC ou DESC).
	 * @param first
	 *            l'index de la première instance à retourner parmi celles qui
	 *            répondent à la requête.
	 * @param pageSize
	 *            le nombre maximum d'éléments à retourner.
	 * @return List<T> liste des entités qui répondent à l'ensemble des
	 *         critères, de manière paginée.
	 */
	public List<T> advancedSearch(Map<String, String> filters, String sortColumn, SortOrder sortOrder, int first, int pageSize)
	{
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> c = cb.createQuery(this.getBusinessClass());
		Root<T> root = c.from(this.getBusinessClass());
		List<Predicate> predicates = new ArrayList<Predicate>();
		for (Entry<String, String> entry : filters.entrySet())
		{
			// vérification : le % ?
			Predicate p = cb.like(root.<String> get(entry.getKey()), entry.getValue() + "%");
			predicates.add(p);
		}
		c.where(predicates.toArray(new Predicate[0]));

		if (sortColumn != null)
		{
			Order order;

			if (sortOrder == SortOrder.ASC)
			{
				order = cb.asc(root.get(sortColumn));
			}
			else
			{
				order = cb.desc(root.get(sortColumn));
			}
			c.orderBy(order);
		}

		TypedQuery<T> query = this.getEntityManager().createQuery(c);
		// pagination.
		query.setFirstResult(first);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	
	/**
	 * retourne une liste d'objets corresponds au critère de restriction fournis:
	 * attribut = objet.
	 * 
	 * @author CDT ROBIN
	 * 
	 * @param restrictions
	 * 		map de nom d'attributs d'entités JPA et de valeurs (instance d'objets).
	 * @return
	 * 		liste des tuples correspondant aux critères.
	 */
	public List<T> filteredSearch(Map<String,Object> restrictions)
	{
		// TODO : à coder.
		throw new RuntimeException("Fonctionnalité non implémentée dans cette version.");
	}
	
	/**
	 * retourne le log courant.
	 * 
	 * @return instance du log.
	 */
	public final Log getLog()
	{
		return this.log;
	}

	/**
	 * Méthode privée qui ajouter un critère de tri à une requête Criteria.
	 * Cette méthode est utilisée en interne par readAll() et search().
	 * 
	 * @param criteriaBuilder
	 * @param query
	 * @param from
	 * @param orderBy
	 */
	private void addSort(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> query, Root<T> from, String... orderBy)
	{
		if (orderBy != null && orderBy.length > 0)
		{
			List<Order> orders = new ArrayList<Order>();
			for (String orderParameter : orderBy)
			{
				Order order = criteriaBuilder.asc(from.get(orderParameter));
				orders.add(order);
			}
			query.orderBy(orders);
		}
	}

}
