package etrs.selene.easypermut.beans;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
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
 * Bean de la page de connexion à l'application.
 *
 * @author SGT Mora Leo
 */
@Named
@ViewScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConnexionPageBean implements Serializable {

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
     * {@link UtilisateurSessionBean}
     */
    @Inject
    UtilisateurSessionBean sessionUtilisateur;

    /**
     * Methode de post-construction. Instancie un nouvel utilisateur.
     */
    @PostConstruct
    public void init() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.utilisateur = this.facadeUtilisateur.newInstance();
    }

    /**
     * Methode de connexion. Si l'utilisateur existe et qu'il est validé (voir
     * {@link Utilisateur#getEstValide()}) il est redirigé vers la page
     * d'accueil de l'application. Si l'utilisateur existe, qu'il est valide
     * mais qu'il n'a pas verifié ces informations, il est redirigé vers la page
     * de validation des informations. Si l'utilisateur existe, mais qu'il n'est
     * pas validé, il est redirigé vers une page d'attente. Si l'utilisateur
     * n'existe pas, il est ajouté dans la BDD et son statut est non valide.
     *
     * @return L'URL de redirection en fonction du cas.
     */
    public String connexion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        String redirectedUrl;
        Utilisateur utilisateurATester = this.facadeUtilisateur.searchFirstResult("identifiantAnudef", this.utilisateur.getIdentifiantAnudef().toLowerCase());

        if (utilisateurATester != null && utilisateurATester.getEstValide() == true) {
            if (utilisateurATester.getInformationsValide() == true) {
                redirectedUrl = "./pages/accueil.xhtml";
            } else {
                redirectedUrl = "./pages/verification.xhtml";
            }
            this.sessionUtilisateur.setUtilisateur(utilisateurATester);

        } else if (utilisateurATester != null && utilisateurATester.getEstValide() == false) {
            redirectedUrl = "./pages/validationEnCours.xhtml";
        } else {
            this.utilisateur.setIdentifiantAnudef(this.utilisateur.getIdentifiantAnudef().toLowerCase());
            this.utilisateur.setEstValide(false);
            this.utilisateur.setDateInscription(new Date());
            this.utilisateur.setInformationsValide(false);
            this.utilisateur.setEstInteresse(false);
            this.facadeUtilisateur.create(this.utilisateur);
            redirectedUrl = "./pages/validation.xhtml";
        }

        return redirectedUrl;
    }
}
