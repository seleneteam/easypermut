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
 * Bean de la page de connexion a l'application.
 *
 * @author SGT Mora Leo
 */
@Named
@ViewScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccueilPageBean implements Serializable {

    // TODO Supprimer Page

    private static final long serialVersionUID = 1L;

    /**
     * L'utilisateur dont l'identifiant anudef est present dans le formulaire.
     */
    Utilisateur utilisateur;

    /**
     * {@link UtilisateurSession}
     */
    @Inject
    UtilisateurSession facadeUtilisateur;

    /**
     * Methode de post-construction. Instancie un nouvel utilisateur.
     */
    @PostConstruct
    public void init() {
        this.utilisateur = this.facadeUtilisateur.newInstance();
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
     * Navigation entre les pages.
     * 
     * @return La page a ateindre en fonction du cas.
     */
    public String pageCreerPermutation() {
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        }
        this.flashUtilisateur(this.utilisateur);
        return "/creationPermutation.xhtml";
    }

    /**
     * Navigation entre les pages.
     * 
     * @return La page a ateindre en fonction du cas.
     */
    public String pageListePermutation() {
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        }
        this.flashUtilisateur(this.utilisateur);
        return "/listePermutations.xhtml";
    }
}
