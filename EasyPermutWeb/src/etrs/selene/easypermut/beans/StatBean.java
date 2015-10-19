package etrs.selene.easypermut.beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
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

	public long nombreUtilisateurs()
	{
		return this.facadeUtilisateur.count();
	}

	public long nombreDemandes()
	{
		return this.facadeDemande.count();
	}
}
