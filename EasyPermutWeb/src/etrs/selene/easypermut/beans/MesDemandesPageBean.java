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
import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.sessions.DemandePermutationSession;

@Named
@ViewScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MesDemandesPageBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * {@link DemandePermutationSession}
	 */
	@Inject
	DemandePermutationSession facadeDemandePermutation;

	/**
	 * Utilisateur dont ont veut afficher les informations sur les demandes.
	 */
	Utilisateur utilisateur;

	/**
	 * La demande de permuattion (si elle existe) en cours de traitement.
	 */
	DemandePermutation demandeEnCours;
	
	/**
	 * Liste de toutes les demandes créées par l'utilisateur.
	 */
	List<DemandePermutation> lstDemandesPassees;

	/**
	 * Methode de post-construction. Recupere l'utilisateur du FlashScope,la
	 * demande en cours et les demandes passées.
	 */
	@PostConstruct
	public void init()
	{
		this.utilisateur = (Utilisateur)JsfUtils.getFromFlashScope("_utilisateur");
		this.demandeEnCours = this.facadeDemandePermutation.searchFirstResult("utilisateurInteresse", this.utilisateur);
		this.lstDemandesPassees = this.facadeDemandePermutation.listeMesDemandes(this.utilisateur);
	}

}
