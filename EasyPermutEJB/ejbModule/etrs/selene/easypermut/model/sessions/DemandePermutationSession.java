package etrs.selene.easypermut.model.sessions;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import etrs.selene.easypermut.model.commons.AbstractFacade;
import etrs.selene.easypermut.model.entities.DemandePermutation;
import etrs.selene.easypermut.model.entities.Utilisateur;

@Stateless
public class DemandePermutationSession extends AbstractFacade<DemandePermutation>
{

	public List<DemandePermutation> listeFiltre(final Utilisateur utilisateur)
	{
		Query q = super.getEntityManager().createQuery("SELECT DEMANDE_PERMUTATION.id FROM DEMANDE_PERMUTATION, LEFT JOIN UTILISATEUR ON DEMANDE_PERMUTATION.utilisateur_createur_id = UTILISATEUR.id WHERE UTILISATEUR.grade_id = :1 AND UTILISATEUR.specialite_id = :2");
		q.setParameter("1", utilisateur.getGrade().getId());
		q.setParameter("2", utilisateur.getSpecialite().getId());
		List<DemandePermutation> lst = q.getResultList();

		return lst;
	}

}
