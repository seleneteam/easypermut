package etrs.selene.easypermut.beans;

import java.io.Serializable;
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
public class CreerPermutationPageBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/**
	 * {@link DemandePermutationSession}
	 */
	@Inject
	DemandePermutationSession facadePermutations;

	@Inject
	ZMRSession facadeZmr;

	@Inject
	VilleSession facadeVille;

	@Inject
	UniteSession facadeUnite;
	
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

	List<ZMR> listeZMR;
	List<Ville> listeVille;
	List<Unite> listeUnite;

	/**
	 * Methode de post-construction. Recupere l'utilisateur du FlashScope.
	 */
	@PostConstruct
	public void init()
	{
		this.utilisateur = (Utilisateur)JsfUtils.getFromFlashScope("_utilisateur");
		this.permutation = this.facadePermutations.newInstance();
		this.listeZMR = this.facadeZmr.readAll();
		this.listeVille = this.facadeVille.readAll();
		this.listeUnite = this.facadeUnite.readAll();
		this.poste = this.facadePoste.newInstance();
	}

	/**
	 * Methode de mise en FlashScope de l'utilisateur.
	 *
	 * @param utilisateur
	 *            L'utilisateur a mettre dans le FlashScope.
	 */
	public void flashUtilisateur(final Utilisateur utilisateur)
	{
		JsfUtils.putInFlashScope("_utilisateur", utilisateur);
	}

	/**
	 * Methode de creation d'une permutation.
	 *
	 * @return La page suivante.
	 */
	public String creerDemande()
	{

		if (this.utilisateur == null)
			return "/connexion.xhtml";
		this.poste.setUnite(this.permutation.getUnite());
		System.out.println(this.poste.getId());
		this.permutation.setUtilisateurCreateur(this.utilisateur);
		this.permutation.setPoste(this.poste);

		this.facadePermutations.create(this.permutation);
		this.flashUtilisateur(this.utilisateur);
		return "/pages/accueil.xhtml";

	}

}
