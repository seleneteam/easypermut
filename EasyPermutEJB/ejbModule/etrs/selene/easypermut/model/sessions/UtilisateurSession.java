package etrs.selene.easypermut.model.sessions;

import javax.ejb.Stateless;
import javax.persistence.Query;

import etrs.selene.easypermut.model.commons.AbstractFacade;
import etrs.selene.easypermut.model.entities.Grade;
import etrs.selene.easypermut.model.entities.Utilisateur;

@Stateless
public class UtilisateurSession extends AbstractFacade<Utilisateur>
{
	/**
	 * Permet d'obtenir le nombre d'utilisateurs pour le grade passé en
	 * paramètre.
	 *
	 * @param grade
	 *            Le grade dont on veut le nombre d'utilisateurs.
	 * @return Le nombre d'utilisateurs.
	 */
	public Long quantiteUtilisateurParGrade(final Grade grade)
	{
		Query q = super.getEntityManager().createQuery("SELECT COUNT(u) FROM Utilisateur u WHERE u.grade = :grade");
		q.setParameter("grade", grade);
		Long resultat = (Long)q.getSingleResult();
		
		return resultat;
	}
}
