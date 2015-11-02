package etrs.selene.easypermut.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.entetrs.commons.jsf.JsfUtils;
import etrs.selene.easypermut.model.entities.Utilisateur;

/**
 * Classe permettant de rediriger un utilisateur en fonction lien séléctionné
 * dans la navbar.
 *
 * @author louis-marie Merminod
 */
@Named
@ViewScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NavBarBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * {@link UtilisateurSessionBean}
     */
    @Inject
    UtilisateurSessionBean sessionUtilisateur;

    /**
     * L'utilisateur connecté.
     */
    Utilisateur utilisateur;

    /**
     * Methode de post-construction. Recupère l'utilisateur du FlashScope.
     */
    @PostConstruct
    public void init() {
        this.utilisateur = this.sessionUtilisateur.getUtilisateur();
    }

    /**
     * Methode de redirection vers la page d'accueil.
     *
     * @return La page vers laquelle l'utilisateur sera redirigé en fonction du
     *         cas.
     */
    public String redirigerPageAccueil() {
        this.init();
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        } else {
            return "/pages/accueil.xhtml";
        }
    }

    /**
     * Methode de redirection vers la page de création d'une permutation.
     *
     * @return La page vers laquelle l'utilisateur sera redirigé en fonction du
     *         cas.
     */
    public String redirigerPageCreation() {
        this.init();
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        } else {
            return "/pages/creationPermutation.xhtml";
        }
    }

    /**
     * Methode de redirection vers la page de la liste des permutations.
     *
     * @return La page vers laquelle l'utilisateur sera redirigé en fonction du
     *         cas.
     */
    public String redirigerPageLister() {
        this.init();
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        } else {
            return "/pages/listePermutations.xhtml";
        }
    }

    /**
     * Methode de redirection vers la page des statistiques.
     *
     * @return La page vers laquelle l'utilisateur sera redirigé en fonction du
     *         cas.
     */
    public String redirigerPageStatistiques() {
        this.init();
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        } else {
            return "/pages/statistiques.xhtml";
        }
    }

    /**
     * Methode de redirection vers la page des demandes personnelles.
     *
     * @return La page vers laquelle l'utilisateur sera redirigé en fonction du
     *         cas.
     */
    public String redirigerPageMesDemandes() {
        this.init();
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        } else {
            return "/pages/mesDemandes.xhtml";
        }
    }

    public String seDeconnecter() {
        this.utilisateur = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.sessionUtilisateur.setUtilisateur(this.utilisateur);

        JsfUtils.sendGrowlMessage("Vous avez été déconnecté");
        return "/connexion.xhtml";
    }

}
