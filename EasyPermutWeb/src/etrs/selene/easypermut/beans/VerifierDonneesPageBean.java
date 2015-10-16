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
import net.entetrs.commons.jsf.JsfUtils;
import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.sessions.UtilisateurSession;

/**
 * Bean de la page de verification des données.
 * 
 * @author SGT Mora Leo
 *
 */
@Named
@ViewScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerifierDonneesPageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    UtilisateurSession facadeUtilisateur;

    /**
     * Utilisateur connecté.
     */
    Utilisateur utilisateur;

    @PostConstruct
    public void init() {
        this.utilisateur = (Utilisateur) JsfUtils.getFromFlashScope("_utilisateur");
    }

    /**
     * Methude de mise en FlashScope de l'utilisateur.
     *
     * @param utilisateur
     *            L'utilisateur a mettre dans le FlashScope.
     */
    public void flashUtilisateur(final Utilisateur utilisateur) {
        JsfUtils.putInFlashScope("_utilisateur", utilisateur);
    }

    /**
     * Methode de sauvegarde des nouvelles données rentrées par l'utilisateur.
     * 
     * @return La page suivante.
     */
    public String sauvegarderDonnees() {
        if (utilisateur == null) {
            return "/connexion.xhtml";
        }
        this.utilisateur.setInformationsValide(true);
        this.facadeUtilisateur.update(utilisateur);
        this.flashUtilisateur(utilisateur);
        return "/accueil.xhtml";
    }
    // TODO Ajouter liens ???
}
