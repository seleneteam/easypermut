package etrs.selene.easypermut.model.sessions;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import etrs.selene.easypermut.model.commons.AbstractFacade;
import etrs.selene.easypermut.model.entities.Grade;
import etrs.selene.easypermut.model.entities.Utilisateur;

@Stateless
public class UtilisateurSession extends AbstractFacade<Utilisateur> {
    /**
     * Permet d'obtenir le nombre d'utilisateurs pour le grade passé en
     * paramètre.
     *
     * @param grade
     *            Le grade dont on veut le nombre d'utilisateurs.
     * @return Le nombre d'utilisateurs.
     */
    public Long quantiteUtilisateurParGrade(final Grade grade) {

        TypedQuery<Long> tq = super.getEntityManager().createNamedQuery("Utilisateur.qtParGrade", Long.class);
        tq.setParameter("grade", grade);
        Long resultat = tq.getSingleResult();

        return resultat;
    }
}
