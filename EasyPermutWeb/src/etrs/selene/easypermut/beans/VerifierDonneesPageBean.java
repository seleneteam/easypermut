package etrs.selene.easypermut.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.sessions.UtilisateurSession;

/**
 * Bean de la page de verification des données.
 *
 * @author SGT Mora Leo
 */
@Named
@ViewScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerifierDonneesPageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * {@link UtilisateurSession}
     */
    @Inject
    UtilisateurSession facadeUtilisateur;

    /**
     * {@link UtilisateurSessionBean}
     */
    @Inject
    UtilisateurSessionBean sessionUtilisateur;

    /**
     * Utilisateur connecté.
     */
    Utilisateur utilisateur;

    /**
     * Méthode de post-construction. Récupère l'utilisateur flashé.
     */
    @PostConstruct
    public void init() {
        this.utilisateur = this.sessionUtilisateur.getUtilisateur();
    }

    /**
     * Méthode de sauvegarde des nouvelles données rentrées par l'utilisateur.
     *
     * @return La page suivante.
     */
    public String sauvegarderDonnees() {

        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        }
        System.out.println(this.utilisateur.getNom());
        this.utilisateur.setInformationsValide(true);
        this.facadeUtilisateur.update(this.utilisateur);
        this.sessionUtilisateur.setUtilisateur(this.utilisateur);

        return "/pages/accueil.xhtml";
    }
}
