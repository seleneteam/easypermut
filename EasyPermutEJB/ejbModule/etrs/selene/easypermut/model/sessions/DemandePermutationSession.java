package etrs.selene.easypermut.model.sessions;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import etrs.selene.easypermut.model.commons.AbstractFacade;
import etrs.selene.easypermut.model.entities.DemandePermutation;
import etrs.selene.easypermut.model.entities.Specialite;
import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.entities.ZMR;

@Stateless
public class DemandePermutationSession extends AbstractFacade<DemandePermutation> {

    /**
     * Permet d'obtenir une liste de demandes de permutation correspondant au
     * grade et à la spécialité de l'utilisateur passé en paramètre. La demande
     * ne doit pas avoir d'interessé et ne doit pas avoir été créée par
     * l'utilisateur.
     *
     * @param utilisateur
     *            L'utilisateur dont on veut les demandes.
     * @return La liste des demandes.
     */
    public List<DemandePermutation> listeFiltre(final Utilisateur utilisateur) {

        TypedQuery<DemandePermutation> tq = super.getEntityManager().createNamedQuery("DemandesPermut.listeSpecifique", DemandePermutation.class);
        tq.setParameter("grade", utilisateur.getGrade());
        tq.setParameter("specialite", utilisateur.getSpecialite());
        tq.setParameter("createur", utilisateur);

        List<DemandePermutation> lst = tq.getResultList();

        return lst;
    }

    /**
     * Permet d'obtenir la liste des demandes créées par l'utilisateur passé en
     * paramètre.
     *
     * @param utilisateur
     *            L'utilisateur dont on veut les demandes.
     * @return La liste des demandes.
     */
    public List<DemandePermutation> listeMesDemandes(final Utilisateur utilisateur) {
        TypedQuery<DemandePermutation> tq = super.getEntityManager().createNamedQuery("DemandesPermut.listeMesDemandes", DemandePermutation.class);
        tq.setParameter("id", utilisateur.getId());

        List<DemandePermutation> lst = tq.getResultList();

        return lst;
    }

    /**
     * Permet d'obtenir le nombre de demandes de permutation pour la ZMR passé
     * en paramètre.
     *
     * @param zmr
     *            La ZMR dont on veut le nombre de permutaions.
     * @return Le nombre de demandes.
     */
    public Long calculerDemandesParZMR(final ZMR zmr) {

        TypedQuery<Long> tq = super.getEntityManager().createNamedQuery("DemandesPermut.qtParZMR", Long.class);
        tq.setParameter("zmr", zmr);
        Long resultat = tq.getSingleResult();

        return resultat;
    }

    /**
     * Permet d'obtenir le nombre de demandes de permutation pour la spécilité
     * passé en paramètre.
     *
     * @param spe
     *            La Specilite dont on veut le nombre de permutaions.
     * @return Le nombre de demandes.
     */
    public Long calculerDemandesParSpe(final Specialite spe) {

        TypedQuery<Long> tq = super.getEntityManager().createNamedQuery("DemandesPermut.qtParSpe", Long.class);
        tq.setParameter("spe", spe);
        Long resultat = tq.getSingleResult();

        return resultat;
    }

}
