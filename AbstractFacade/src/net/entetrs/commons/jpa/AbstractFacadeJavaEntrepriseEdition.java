package net.entetrs.commons.jpa;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Facade abstraite générique CRUD pour les entités, paramétrée par "generics"
 * version Java EE. ATTENTION, NE PAS UTILISER CETTE FACADE EN JAVA STANDARD
 * EDITION !
 * 
 * @author CDT RBN
 * @version 2.0
 * 
 */
public abstract class AbstractFacadeJavaEntrepriseEdition<T> extends AbstractFacadeCommon<T>
{

	/**
	 * ajoute une nouvelle entity au contexte JPA (persist).
	 * 
	 * @param t
	 *            instance d'une entité à ajouter.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void create(T t)
	{
		super.create(t);
	}

	/**
	 * met à jour les données portées par l'instance de T (merge).
	 * 
	 * @param t
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public T update(T t)
	{
		return super.update(t);
	}

	/**
	 * supprime l'instance de T.
	 * 
	 * @param t
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(T t)
	{
		super.delete(t);
	}

}
