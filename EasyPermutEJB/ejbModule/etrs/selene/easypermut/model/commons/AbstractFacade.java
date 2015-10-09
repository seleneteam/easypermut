package etrs.selene.easypermut.model.commons;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Facade abstraite générique CRUD pour les entités, paramétrée par "generics".
 * 
 * @author RBN
 */
public abstract class AbstractFacade<T>
{
	/**
	 * référence vers le log de cette classe.
	 */
	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * instance de EntityManager, injectée.
	 */
	@PersistenceContext
	protected EntityManager em;

	/**
	 * référence vers le PersistenceUnitUtil de l'entityManager courant. Cette
	 * référence est récupérée en PostConstruct.
	 */
	protected PersistenceUnitUtil puu;

	/**
	 * instance de Class représentant la classe réelle du type paramétré.
	 */
	private Class<T> parameterizedType;

	/**
	 * méthode déclenchée après construction pour déterminer la Classe réelle du
	 * type paramétré.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostConstruct
	public void init()
	{
		Class thisClass = this.getClass();
		ParameterizedType paramType = (ParameterizedType) thisClass.getGenericSuperclass();
		parameterizedType = (Class<T>) paramType.getActualTypeArguments()[0];
		if (em != null)
		{
			puu = em.getEntityManagerFactory().getPersistenceUnitUtil();
		}
	}

	/**
	 * crée une nouvelle instance du type paramétrée &lt;T&gt; de la classe.
	 * 
	 * @return nouvelle instance
	 */
	public T newInstance()
	{
		try
		{
			T t = parameterizedType.newInstance();		
			this.injectUUID(t);
			return t;
			
		} catch (Exception ex)
		{
			if (log.isErrorEnabled())
			{
				log.error(ex);
			}
			return null;
		}
	}
	
	/**
	 * parcours les champs de type String ou byte[] et vérifie que l'annotation GeneratedUUID est présente.
	 * Si cela est le cas, elle est injectée.
	 * 
	 * @param t
	 */
	private void injectUUID(T t)
	{
		Field[] fields = t.getClass().getDeclaredFields();
		for (Field f : fields)
		{
			if (f.isAnnotationPresent(GeneratedUUID.class))
			{
				UUID uuid = UUID.randomUUID();
				Class<?> fieldClass = f.getType();
				String fieldName = f.getName();
				
				try
				{			
					if (fieldClass.equals(String.class))
					{					
							BeanUtils.setProperty(t, fieldName, uuid.toString());					
					}
					else
					{
						if (fieldClass.isArray() && fieldClass.getComponentType().equals(long.class))
						{
					      
				// tableau d'octets ... 
				// TODO : à faire (UUID comme tableau). Attention à la relecture par JPA.
				// Refaire un getter qui contruit le UUID à partir du tableau d'octets.
							byte[] buffer = UUIDUtils.convertToBytes(uuid);
							BeanUtils.setProperty(t, fieldName, buffer);							
						}
										
					}
				} catch (IllegalAccessException | InvocationTargetException e) 
				{
					if (log.isErrorEnabled())
					{
						log.error("Erreur pendant l'injection de l'UUID");
						log.error(e);
					}
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes" })
	/**
	 * renvoie la classe du type paramétré de la classe &lt;T&gt;.
	 *
	 * @return la définition d'une classe (objet Class)
	 */
	public Class getBusinessClass()
	{
		return this.parameterizedType;
	}

	/**
	 * ajoute une nouvelle entity au contexte JPA (persist).
	 * 
	 * @param t
	 *            instance d'une entité à ajouter.
	 */
	public void create(T t)
	{
		em.persist(t);
		if (log.isInfoEnabled())
		{
			log.info("Ecriture en base de : " + t);
		}
	}

	/**
	 * met à jour les données portées par l'instance de T (merge).
	 * 
	 * @param t
	 */
	public T update(T t)
	{
		t = em.merge(t);
		if (log.isInfoEnabled())
		{
			log.info("Mise à jour en base de : " + t);
		}
		return t;
	}

	/**
	 * supprime l'instance de T.
	 * 
	 * @param t
	 */
	public void delete(T t)
	{
		T attachedEntity = (T) em.getReference(parameterizedType, puu.getIdentifier(t));
		em.remove(attachedEntity);

		if (log.isInfoEnabled())
		{
			log.info("Suppression en base de : " + t);
		}
	}

	// méthodes de lecture : readAll, readAll(String...), read(Object key)

	/**
	 * récupère toutes les instances de l'entité T.
	 * 
	 * @return liste des instances, éventuellement triées.
	 */
	public List<T> readAll()
	{
		return this.readAll(new String[0]);
	}

	/**
	 * récupère toutes les instances de l'entité T, éventuellement triées par
	 * ordre descendant (DESC) sur une liste d'attributs.
	 * 
	 * @return liste des instances, éventuellement triées.
	 */
	public List<T> readAll(String... orderBy)
	{
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<T> c = qb.createQuery(parameterizedType);
		Root<T> from = c.from(parameterizedType);
		this.addSort(qb, c, from, orderBy);
		TypedQuery<T> query = em.createQuery(c);
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
		return (T) em.find(parameterizedType, id);
	}

	// méthodes de recherches

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
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<T> c = qb.createQuery(parameterizedType);
		Root<T> from = c.from(parameterizedType);
		Predicate restriction = qb.equal(from.get(parameterName), parameterValue);
		c.where(restriction);
		this.addSort(qb, c, from, orderBy);
		TypedQuery<T> query = em.createQuery(c);
		return query.getResultList();
	}

	/**
	 * cherche la première occurence de la classe T où l'attribut passé en
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
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<T> c = qb.createQuery(parameterizedType);
		Root<T> from = c.from(parameterizedType);
		Predicate restriction = qb.equal(from.get(parameterName), parameterValue);
		c.where(restriction);
		TypedQuery<T> query = em.createQuery(c);

		try
		{
			return query.getSingleResult();
		} catch (javax.persistence.NoResultException ex)
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
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<Long> c = qb.createQuery(Long.class);
		c.select(qb.count(c.from(parameterizedType)));
		return em.createQuery(c).getSingleResult();
	}

	// méthodes privées

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

	// méthodes techniques.

	/**
	 * Rafraichit l'instance en mémoire par rapport à la base de données.
	 * 
	 * @param t
	 */
	public void refresh(T t)
	{
		t = (T) this.em.getReference(parameterizedType, puu.getIdentifier(t));
		this.em.refresh(t);
	}

}
