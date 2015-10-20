package etrs.selene.easypermut.beans;

import java.io.InputStream;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.entetrs.commons.jsf.JsfUtils;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import etrs.selene.easypermut.model.entities.Utilisateur;
import etrs.selene.easypermut.model.sessions.DemandePermutationSession;
import etrs.selene.easypermut.model.sessions.UtilisateurSession;

@Named
@ViewScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Inject
	UtilisateurSession facadeUtilisateur;

	@Inject
	DemandePermutationSession facadeDemande;

	Utilisateur utilisateur;

	private StreamedContent file;

	@PostConstruct
	public void init()
	{
		this.utilisateur = (Utilisateur)JsfUtils.getFromFlashScope("_utilisateur");
	}

	public long nombreUtilisateurs()
	{
		return this.facadeUtilisateur.count();
	}

	public long nombreDemandes()
	{
		return this.facadeDemande.count();
	}

	public void exporter()
	{
		InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/resources/docs/export.csv");
		this.file = new DefaultStreamedContent(stream, "text/csv", "exp.csv");

	}

}
