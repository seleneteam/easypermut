package etrs.selene.easypermut.beans;

import java.io.Serializable;
import java.net.URI;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.entetrs.commons.jsf.JsfUtils;
import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.sessions.UtilisateurSession;

@Named
@RequestScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IdentificationPageBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/**
	 * L'utilisateur dont l'identifiant anudef est present dans le formulaire.
	 */
	Utilisateur utilisateur;

	/**
	 * URI de redirection en cas d'erreur.
	 */
	URI uri;

	@Inject
	UtilisateurSession facadeUtilisateur;

	/**
	 * Methode de post contruction. Instancie un nouvel utilisateur.
	 */
	@PostConstruct
	public void init()
	{
		this.utilisateur = this.facadeUtilisateur.newInstance();
	}

	/**
	 * Methude de mise en FlashScope de l'utilisateur.
	 *
	 * @param u
	 *            L'utilisateur a mettre dans le FlashScope.
	 */
	public void flashUtilisateur(final Utilisateur utilisateur)
	{
		JsfUtils.putInFlashScope("_utilisateur", utilisateur);
	}

	/**
	 * Methode de connexion. Si l'utilisateur existe et que il est validé (voir
	 * {@link Utilisateur#getEstValide()}) redirige vers la page d'accueil de
	 * l'application. Si l'utilisateur existe, mais qu'il n'est pas validé,
	 * redirige vers une page d'attente. Si l'utilisateur n'existe pas, l'ajoute
	 * dans la BDD et met son status a non valide.
	 *
	 * @return L'URL de redirection en fonction du cas.
	 */
	public String connexion()
	{
		String redirectedUrl;

		Utilisateur utilisateurATester = this.facadeUtilisateur.searchFirstResult("identifiantAnudef", this.utilisateur.getIdentifiantAnudef());

		if (utilisateurATester != null && utilisateurATester.getEstValide() == true)
		{
			this.flashUtilisateur(utilisateurATester);
			redirectedUrl = "";
		}
		else if (utilisateurATester != null && utilisateurATester.getEstValide() == false)
		{
			redirectedUrl = "";
		}
		else
		{
			utilisateurATester.setEstValide(false);
			this.facadeUtilisateur.create(utilisateurATester);
			redirectedUrl = "";
		}

		return redirectedUrl;
	}
}
