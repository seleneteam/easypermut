package etrs.selene.easypermut.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 * Bean de la page d'affichage des permutations.
 * 
 * @author SGT Mora Leo
 *
 */
@Named
@RequestScoped
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
        utilisateur = (Utilisateur) JsfUtils.getFromFlashScope("_utilisateur");
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
        if (utilisateur.getEstInteresse() == false) {
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

    public void choisirPermutation(DemandePermutation permutation) {
        if (utilisateur.getEstInteresse() == false) {
            utilisateur.setEstInteresse(true);
            permutation.setUtilisateurInteresse(utilisateur);
        }
    }
}
