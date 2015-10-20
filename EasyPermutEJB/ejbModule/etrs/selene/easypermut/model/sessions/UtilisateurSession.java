package etrs.selene.easypermut.model.sessions;

import javax.ejb.Stateless;
import javax.persistence.Query;

import etrs.selene.easypermut.model.commons.AbstractFacade;
import etrs.selene.easypermut.model.entities.Grade;
import etrs.selene.easypermut.model.entities.Utilisateur;

@Stateless
public class UtilisateurSession extends AbstractFacade<Utilisateur>
{
	public Long quantiteUtilisateurParGrade(final Grade grade)
	{
		Query q = super.getEntityManager().createQuery("SELECT COUNT(u) FROM Utilisateur u WHERE u.grade = :grade");
		q.setParameter("grade", grade);
		Long resultat = (Long)q.getSingleResult();

		return resultat;
	}
}
