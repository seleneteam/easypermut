package etrs.selene.easypermut.beans;

import java.io.Serializable;
import java.util.LinkedList;
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
import etrs.selene.easypermut.model.entities.Poste;
import etrs.selene.easypermut.model.entities.Unite;
import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.entities.Ville;
import etrs.selene.easypermut.model.entities.ZMR;
import etrs.selene.easypermut.model.sessions.DemandePermutationSession;
import etrs.selene.easypermut.model.sessions.PosteSession;
import etrs.selene.easypermut.model.sessions.UniteSession;
import etrs.selene.easypermut.model.sessions.VilleSession;
import etrs.selene.easypermut.model.sessions.ZMRSession;

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
     * {@link ZMRSession}
     */
    @Inject
    ZMRSession facadeZmr;

    /**
     * {@link VilleSession}
     */
    @Inject
    VilleSession facadeVille;

    /**
     * {@link UniteSession}
     */
    @Inject
    UniteSession facadeUnite;

    /**
     * {@link PosteSession}
     */
    @Inject
    PosteSession facadePoste;

    /**
     * La permutation qui sera crée.
     */
    DemandePermutation permutation;

    /**
     * Utilisateur connecté.
     */
    Utilisateur utilisateur;

    /**
     * Poste désiré.
     */
    Poste poste;

    /**
     * Liste des ZMR.
     */
    List<ZMR> listeZMR;

    /**
     * Liste des villes.
     */
    List<Ville> listeVille;

    /**
     * Liste des unitées.
     */
    List<Unite> listeUnite;

    /**
     * Methode de post-construction. Recupere l'utilisateur du FlashScope et
     * initialise les champs a remplir.
     */
    @PostConstruct
    public void init() {
        this.utilisateur = (Utilisateur) JsfUtils.getFromFlashScope("_utilisateur");
        this.permutation = this.facadePermutations.newInstance();
        this.poste = this.facadePoste.newInstance();

        this.listeZMR = this.facadeZmr.readAll();
        this.listeVille = new LinkedList<>();
        this.listeUnite = new LinkedList<>();
    }

    /**
     * Methode de mise en FlashScope de l'utilisateur.
     *
     * @param utilisateur
     *            L'utilisateur a mettre dans le FlashScope.
     */
    public void flashUtilisateur(final Utilisateur utilisateur) {
        JsfUtils.putInFlashScope("_utilisateur", utilisateur);
    }

    /**
     * Methode de creation d'une permutation.
     *
     * @return La page suivante.
     */
    public String creerDemande() {

        if (this.utilisateur == null) {
            return "/connexion.xhtml";
        }

        this.poste.setUnite(this.permutation.getUnite());
        this.permutation.setUtilisateurCreateur(this.utilisateur);
        this.permutation.setPoste(this.poste);
        if ((this.poste = this.facadePoste.searchFirstResult("libelle", this.poste.getLibelle())) != null) {
            this.facadePoste.create(this.poste);
        }
        this.facadePermutations.create(this.permutation);
        this.flashUtilisateur(this.utilisateur);
        JsfUtils.sendGrowlMessage("Demande de permutation crée");

        return "/pages/accueil.xhtml";
    }

    /**
     * Methode de chargement des villes en fonction de la ZMR selectionée.
     */
    public void chargerVilles() {
        this.permutation.setVille(null);
        this.permutation.setUnite(null);
        if (this.permutation.getZmr() != null) {
            this.listeVille = facadeVille.search("zmr", this.permutation.getZmr());
        } else {
            this.listeVille = new LinkedList<>();
        }
        this.listeUnite = new LinkedList<>();
    }

    /**
     * Methode de chargement des unitées en fonction des la Ville sélectionée.
     */
    public void chargerUnitees() {
        this.permutation.setUnite(null);
        if (this.permutation.getVille() != null) {
            this.listeUnite = facadeUnite.search("ville", this.permutation.getVille());
        } else {
            this.listeUnite = new LinkedList<>();
        }

    }
}
