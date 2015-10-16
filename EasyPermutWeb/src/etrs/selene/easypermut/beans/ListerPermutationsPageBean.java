package etrs.selene.easypermut.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 * Bean de la page d'affichage des permutations.
 * 
 * @author SGT Mora Leo
 */
@Named
@ViewScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListerPermutationsPageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * {@link DemandePermutationSession}
     */
    @Inject
    DemandePermutationSession facadePermutations;

    /**
     * Utilisateur connecté.
     */
    Utilisateur utilisateur;

    /**
     * Methode de post-construction. Recupere l'utilisateur du FlashScope.
     */
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
     * Retourne la liste de toutes les permutations.
     * 
     * @return Une liste de permutation.
     */
    public List<DemandePermutation> getLstDemandesPermutation() {
        return this.facadePermutations.readAll();
    }

    /**
     * Retourne la liste des permutations compatibles avec l'utilisateur. Donc,
     * de meme grade et de meme spécilité.
     * 
     * @return Une liste de permutation.
     */
    public List<DemandePermutation> getLstDemandesPermutationSpecifiques() {
        if (this.utilisateur.getEstInteresse() == false) {
            List<DemandePermutation> lstPermutSpecifique = new ArrayList<>();
            List<DemandePermutation> lstPermut = this.facadePermutations.search("specilite", utilisateur.getSpecialite());

            for (DemandePermutation demandePermutation : lstPermut) {
                if (demandePermutation.getUtilisateurCreateur().getGrade() == this.utilisateur.getGrade()) {
                    lstPermutSpecifique.add(demandePermutation);
                }
            }
            return lstPermutSpecifique;
        } else {
            return null;
        }
    }

    /**
     * Methode de choix d'une permutation. Ajoute l'utilisateura la permutation.
     * 
     * @param permutation
     * @return La page suivante.
     */
    public String choisirPermutation(DemandePermutation permutation) {
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        }
        if (this.utilisateur.getEstInteresse() == false) {
            this.utilisateur.setEstInteresse(true);
            permutation.setUtilisateurInteresse(utilisateur);
        }
        return "/accueil.xhtml";
    }

    // TODO Ajouter comments.
    public String pageCreerPermutation() {
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        }
        this.flashUtilisateur(this.utilisateur);
        return "/creationPermutation.xhtml";
    }

    public String pageAccueil() {
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        }
        this.flashUtilisateur(this.utilisateur);
        return "/accueil.xhtml";
    }
}
