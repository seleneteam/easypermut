package etrs.selene.easypermut.model.sessions;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import etrs.selene.easypermut.model.commons.AbstractFacade;
import etrs.selene.easypermut.model.entities.DemandePermutation;
import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.entities.ZMR;

@Stateless
public class DemandePermutationSession extends AbstractFacade<DemandePermutation>
{

	public List<DemandePermutation> listeFiltre(final Utilisateur utilisateur)
	{
		Query q = super.getEntityManager().createQuery("SELECT dp FROM DemandePermutation dp WHERE dp.utilisateurCreateur.grade = :grade AND dp.utilisateurCreateur.specialite = :specialite AND dp.utilisateurInteresse IS NULL AND dp.utilisateurCreateur != :createur");
		q.setParameter("grade", utilisateur.getGrade());
		q.setParameter("specialite", utilisateur.getSpecialite());
		q.setParameter("createur", utilisateur);
		List<DemandePermutation> lst = q.getResultList();

		return lst;
	}
	
	public List<DemandePermutation> listeMesDemandes(final Utilisateur utilisateur)
	{
		Query q = super.getEntityManager().createQuery("SELECT dp FROM DemandePermutation dp WHERE dp.utilisateurCreateur.id = :id");
		q.setParameter("id", utilisateur.getId());
		List<DemandePermutation> lst = q.getResultList();

		return lst;
	}

	public Long quantiteDemandesParZMR(final ZMR zmr)
	{
		Query q = super.getEntityManager().createQuery("SELECT COUNT(dp) FROM DemandePermutation dp WHERE dp.zmr = :zmr");
		q.setParameter("zmr", zmr);
		Long resultat = (Long)q.getSingleResult();

		return resultat;
	}
	
}
