package etrs.selene.easypermut.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.sessions.UtilisateurSession;

/**
 * Bean de la session utilisateur. Cette bean sert a transporter l'utilisateur
 * connecté a travers toue l'application.
 * 
 * @author SGT Mora Leo
 *
 */
@Named
@SessionScoped
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UtilisateurSessionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * {@link UtilisateurSession}
     */
    @Inject
    UtilisateurSession facadeUtilisateur;

    /**
     * L'utilisateur connecté.
     */
    Utilisateur utilisateur;

    /**
     * Methode de post-construction. Recupere une nouvelle instance
     * d'Utilisateur.
     */
    @PostConstruct
    public void init() {
        this.utilisateur = facadeUtilisateur.newInstance();
    }

}
