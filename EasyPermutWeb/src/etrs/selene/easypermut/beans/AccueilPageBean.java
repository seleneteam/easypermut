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

/**
 * Bean de la page de connexion à l'application.
 *
 * @author SGT Mora Leo
 */
@Named
@ViewScoped
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccueilPageBean implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	/**
	 * {@link UtilisateurSessionBean}
	 */
	@Inject
	UtilisateurSessionBean sessionUtilisateur;

	@PostConstruct
	public void init()
	{
	}

}
