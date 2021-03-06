package etrs.selene.easypermut.model.sessions;

import java.util.Date;
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
    public Long calculerUtilisateurParGrade(final Grade grade) {

        TypedQuery<Long> tq = super.getEntityManager().createNamedQuery("Utilisateur.qtParGrade", Long.class);
        tq.setParameter("grade", grade);
        Long resultat = tq.getSingleResult();

        return resultat;
    }

    /**
     * Permet d'obtenir le nombre d'utilisateurs total vérifiés.
     *
     * @return Le nombre d'utilisateurs total.
     */
    public Long calculerUtilisateurTotal() {

        TypedQuery<Long> tq = super.getEntityManager().createNamedQuery("Utilisateur.qtTotal", Long.class);
        Long resultat = tq.getSingleResult();

        return resultat;
    }

    /**
     * Permet d'obtenir le nombre d'utilisateur inscripts avant la date passé en
     * parametre.
     *
     * @return Le nombre d'utilisateurs.
     */
    public Long calculerUtilisateurAvantLaDate(Date date) {

        TypedQuery<Long> tq = super.getEntityManager().createNamedQuery("Utilisateur.qtInscriptionParMois", Long.class);
        tq.setParameter("date", date);
        Long resultat = tq.getSingleResult();

        return resultat;
    }
}
