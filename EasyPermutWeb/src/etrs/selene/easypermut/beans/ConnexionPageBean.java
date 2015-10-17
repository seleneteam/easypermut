package etrs.selene.easypermut.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.entetrs.commons.jsf.JsfUtils;
import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.sessions.UtilisateurSession;

/**
 * Bean de la page de connexion a l'application.
 *
 * @author SGT Mora Leo
 */
@Named
@ViewScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConnexionPageBean implements Serializable
{
	
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
	 * Methode de post-construction. Instancie un nouvel utilisateur.
	 */
	@PostConstruct
	public void init()
	{
		this.utilisateur = this.facadeUtilisateur.newInstance();
	}
	
	/**
	 * Methude de mise en FlashScope de l'utilisateur.
	 *
	 * @param utilisateur
	 *            L'utilisateur a mettre dans le FlashScope.
	 */
	public void flashUtilisateur(final Utilisateur utilisateur)
	{
		JsfUtils.putInFlashScope("_utilisateur", utilisateur);
	}
	
	/**
	 * Methode de connexion. Si l'utilisateur existe et que il est validé (voir
	 * {@link Utilisateur#getEstValide()}) redirige vers la page d'accueil de
	 * l'application. Si l'utilisateur existe, qu'il est valide mais qu'il n'a
	 * pas verifié ces informations, le redirige vers la page de validation des
	 * informations. Si l'utilisateur existe, mais qu'il n'est pas validé,
	 * redirige vers une page d'attente. Si l'utilisateur n'existe pas, l'ajoute
	 * dans la BDD et met son status a non valide.
	 *
	 * @return L'URL de redirection en fonction du cas.
	 */
	public String connexion()
	{
		String redirectedUrl;
		Utilisateur utilisateurATester = this.facadeUtilisateur.searchFirstResult("identifiantAnudef", this.utilisateur.getIdentifiantAnudef().toLowerCase());
		
		if (utilisateurATester != null && utilisateurATester.getEstValide() == true)
		{
			if (this.utilisateur.getInformationsValide() == true)
			{
				redirectedUrl = "./pages/accueil.xhtml";
			}
			else
			{
				// TODO ajouter page de validation des informations.
				redirectedUrl = "";
			}
			this.flashUtilisateur(utilisateurATester);
			
		}
		else if (utilisateurATester != null && utilisateurATester.getEstValide() == false)
		{
			redirectedUrl = "./pages/validationEnCours.xhtml";
		}
		else
		{
			this.utilisateur.setIdentifiantAnudef(this.utilisateur.getIdentifiantAnudef().toLowerCase());
			this.utilisateur.setEstValide(false);
			this.facadeUtilisateur.create(this.utilisateur);
			redirectedUrl = "./pages/validation.xhtml";
		}
		
		return redirectedUrl;
	}
}
