package etrs.selene.easypermut.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
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
 *
 */
@Named
@RequestScoped
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
        permutation = facadePermutations.newInstance();
    }

    /**
     * Methode de creation d'une permutation.
     */
    public void creerDemande() {
        permutation.setUtilisateurCreateur(utilisateur);
        facadePermutations.create(permutation);
    }
}
