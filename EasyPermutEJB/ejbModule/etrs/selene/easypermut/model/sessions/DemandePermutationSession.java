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
		Query q = super.getEntityManager().createQuery("SELECT dp FROM DemandePermutation dp WHERE dp.utilisateurCreateur.grade = :grade AND dp.utilisateurCreateur.specialite = :specialite");
		q.setParameter("grade", utilisateur.getGrade());
		q.setParameter("specialite", utilisateur.getSpecialite());
		List<DemandePermutation> lst = q.getResultList();

		for (DemandePermutation demandePermutation : lst)
		{
			System.err.println(demandePermutation.getId().toString());
		}
		
		return lst;
	}

	public List<DemandePermutation> listeMesDemandes(final Utilisateur utilisateur)
	{
		Query q = super.getEntityManager().createQuery("SELECT dp FROM DemandePermutation dp WHERE dp.utilisateurCreateur.id = :id");
		q.setParameter("id", utilisateur.getId());
		List<DemandePermutation> lst = q.getResultList();

		for (DemandePermutation demandePermutation : lst)
		{
			System.err.println(demandePermutation.getId().toString());
		}
		
		return lst;
	}

}
