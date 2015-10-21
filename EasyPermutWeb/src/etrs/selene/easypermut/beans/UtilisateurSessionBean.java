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
 * Bean de la sessin utilisateur.
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

    @Inject
    UtilisateurSession facadeUtilisateur;

    Utilisateur utilisateur;

    @PostConstruct
    public void init() {
        this.utilisateur = facadeUtilisateur.newInstance();
    }

}
