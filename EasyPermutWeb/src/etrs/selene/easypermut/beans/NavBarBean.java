package etrs.selene.easypermut.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.entetrs.commons.jsf.JsfUtils;
import etrs.selene.easypermut.model.entities.Utilisateur;

@Named
@ViewScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NavBarBean implements Serializable {
    private static final long serialVersionUID = 1L;

    Utilisateur utilisateur;

    /**
     * Methode de post-construction. Recupere l'utilisateur du FlashScope.
     */
    @PostConstruct
    public void init() {
        this.utilisateur = (Utilisateur) JsfUtils.getFromFlashScope("_utilisateur");
    }

    /**
     * Methode de redirection pour la page d'accueil.
     * 
     * @return La page vers laquelle l'utilisateur sera redirige en fonction du
     *         cas.
     */
    public String pageAccueil() {
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        } else {
            reflasherUtilisateur();
            return "/pages/acccueil.xhtml";
        }
    }

    /**
     * Methode de redirection pour la page de creation d'une permutation.
     * 
     * @return La page vers laquelle l'utilisateur sera redirige en fonction du
     *         cas.
     */
    public String pageCreation() {
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        } else {
            reflasherUtilisateur();
            return "/pages/creationPermutation.xhtml";
        }
    }

    /**
     * Methode de redirection pour la page de la liste des permutations.
     * 
     * @return La page vers laquelle l'utilisateur sera redirige en fonction du
     *         cas.
     */
    public String pageLister() {
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        } else {
            reflasherUtilisateur();
            return "/pages/listePermutations.xhtml";
        }
    }

    /**
     * Methode permettant de reflasher un utilisateur.
     */
    public void reflasherUtilisateur() {
        JsfUtils.putInFlashScope("_utilisateur", this.utilisateur);
    }

}
