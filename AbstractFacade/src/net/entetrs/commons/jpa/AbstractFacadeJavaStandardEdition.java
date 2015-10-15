package net.entetrs.commons.jpa;

import javax.persistence.EntityTransaction;

/**
 * Facade abstraite générique CRUD pour les entités, paramétrée par "generics"
 * version Java SE. ATTENTION, NE PAS UTILISER CETTE FACADE EN JAVA ENTERPRISE
 * EDITION !
 * 
 * @author CDT RBN
 * @version 1.0
 * 
 */
public abstract class AbstractFacadeJavaStandardEdition<T> extends AbstractFacadeCommon<T>
{

	public AbstractFacadeJavaStandardEdition() 
	{
		super(); // on déclenche le contructeur de la classe mère
		init();  // on lance manuellement l'initialisation parce qu'elle n'est pas faite automatiquement en Java SE
	}
	
	/*
	 * cette classe enrobe les méthodes transactionnelles de la classe mère pour
	 * gérer manuelle la transaction en mode Java Standard Edition.
	 */

	/**
	 * enregistre l'entité JPA passée en paramètre.
	 */
	@Override
	public void create(T t)
	{
		EntityTransaction transaction = this.getEntityManager().getTransaction();
		boolean transactionEnCours = transaction.isActive();
		if (!transactionEnCours) transaction.begin();

		super.create(t);

		if (!transactionEnCours) transaction.commit();
	}

	/**
	 * sauvegarde l'entité JPA passé en paramètre.
	 */
	@Override
	public T update(T t)
	{
		EntityTransaction transaction = this.getEntityManager().getTransaction();
		boolean transactionEnCours = transaction.isActive();
		if (!transactionEnCours) transaction.begin();

		T managed = super.update(t);

		if (!transactionEnCours) transaction.commit();
		return managed;
	}

	/**
	 * supprime l'entité JPA passée en paramètre.
	 */
	@Override
	public void delete(T t)
	{
		EntityTransaction transaction = this.getEntityManager().getTransaction();
		boolean transactionEnCours = transaction.isActive();
		if (!transactionEnCours) transaction.begin();

		super.delete(t);

		if (!transactionEnCours) transaction.commit();
	}

}
