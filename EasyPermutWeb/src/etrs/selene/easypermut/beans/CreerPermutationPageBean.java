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
import etrs.selene.easypermut.model.entities.DemandePermutation;
import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.sessions.DemandePermutationSession;

/**
 * Bean de la page de la creation d'une permutation.
 * 
 * @author SGT Mora Leo
 */
@Named
@ViewScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreerPermutationPageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * {@link DemandePermutationSession}
     */
    @Inject
    DemandePermutationSession facadePermutations;

    /**
     * La permutation qui sera crée.
     */
    DemandePermutation permutation;

    /**
     * Utilisateur connecté.
     */
    Utilisateur utilisateur;

    /**
     * Methode de post-construction. Recupere l'utilisateur du FlashScope.
     */
    @PostConstruct
    public void init() {
        utilisateur = (Utilisateur) JsfUtils.getFromFlashScope("_utilisateur");
        this.permutation = this.facadePermutations.newInstance();
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
     * Methode de reation d'une permutation.
     * 
     * @return La page suivante.
     */
    public String creerDemande() {
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        }
        this.permutation.setUtilisateurCreateur(utilisateur);
        this.facadePermutations.create(permutation);
        this.flashUtilisateur(utilisateur);
        return "/accueil.xhtml";
    }

    // TODO Ajouter comments.
    public String pageListePermutation() {
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        }
        this.flashUtilisateur(this.utilisateur);
        return "/listePermutations.xhtml";
    }

    public String pageAccueil() {
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        }
        this.flashUtilisateur(this.utilisateur);
        return "/accueil.xhtml";
    }
}
