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
import etrs.selene.easypermut.model.sessions.UtilisateurSession;

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
     * {@link UtilisateurSession}
     */
    @Inject
    UtilisateurSession facadeUtilisateur;

    /**
     * Utilisateur connecté.
     */
    Utilisateur utilisateur;

    /**
     * La demannde de permutation selectionée pour les details et pour
     * l'acceptation.
     */
    DemandePermutation demandePermutationSelectionee;

    /**
     * Methode de post-construction. Recupere l'utilisateur du FlashScope.
     */
    @PostConstruct
    public void init() {
        this.utilisateur = (Utilisateur) JsfUtils.getFromFlashScope("_utilisateur");
        this.demandePermutationSelectionee = this.facadePermutations.newInstance();
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
     * Retourne la liste des permutations sans celles en cours de transaction.
     * 
     * @return Une liste de permutation valide.
     */
    public List<DemandePermutation> getLstDemandesPermutationValides() {
        List<DemandePermutation> lstPermutValides = new ArrayList<>();

        for (DemandePermutation demandePermutation : this.facadePermutations.readAll()) {
            if (demandePermutation.getUtilisateurInteresse() == null) {
                lstPermutValides.add(demandePermutation);
            }
        }
        return lstPermutValides;
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
                    if (demandePermutation.getUtilisateurInteresse() == null) {
                        lstPermutSpecifique.add(demandePermutation);
                    }
                }
            }
            return lstPermutSpecifique;
        } else {
            return null;
        }
    }

    /**
     * Methode de choix d'une permutation. Ajoute l'utilisateur a la
     * permutation.
     * 
     * @param permutation
     * @return La page suivante.
     */
    public String choisirPermutation() {
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        }
        if (this.utilisateur.getEstInteresse() == false) {
            this.utilisateur.setEstInteresse(true);
            this.demandePermutationSelectionee.setUtilisateurInteresse(this.utilisateur);
            this.facadePermutations.update(this.demandePermutationSelectionee);
            this.facadeUtilisateur.update(this.utilisateur);
            JsfUtils.sendGrowlMessage("Vous avez choisi la permutation de %s", this.demandePermutationSelectionee.getUtilisateurCreateur().getNom());
        }
        return "/pages/accueil.xhtml";
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
        return "/pages/creationPermutation.xhtml";
    }

    /**
     * Navigation entre les pages.
     * 
     * @return La page a ateindre en fonction du cas.
     */
    public String pageAccueil() {
        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        }
        this.flashUtilisateur(this.utilisateur);
        return "/accueil.xhtml";
    }

    /**
     * Sélectionne une ermutation pour les details et l'acceptation.
     * 
     * @param permutation
     *            La permutation.
     */
    public void selectionerPermutation(DemandePermutation permutation) {
        this.demandePermutationSelectionee = permutation;
    }

}
