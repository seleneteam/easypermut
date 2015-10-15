package net.entetrs.commons.jsf.security;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;


/**
 * phase listener à déclarer dans faces-config.xml qui permet de transformer
 * une exception suite à une erreur d'authentification en message JSF classique.
 * Ce PhaseListener est très utile pour retourner les erreurs JSF au sein même
 * du formulaire d'authentification.
 * 
 * @author CDT ROBIN
 *
 */

@SuppressWarnings("serial")
public class CheckCredentialsPhaseListener implements PhaseListener
{
	public static final String CREDENTIAL_ERROR = "Login et/ou mot de passe invalide(s).";
	
	public void afterPhase(PhaseEvent event)
	{
		// méthode vide intentionnellement, car imposée par l'interface PhaseListener.
	}
	
	/**
	 * interaction de ce PhaseListener avec "RenderResponse".
	 */
	public PhaseId getPhaseId()
	{
		return PhaseId.RENDER_RESPONSE;
	}

	/**
	 * méthode lancée avant la phase "RENDER_RESPONSE" pour vérifier
	 * les accréditations de l'utilisateur courant.
	 * Cette méthode transforme une expcetion placée dans la SessionMap par SpringSecurity
	 * en une erreur JSF.
	 */
	public void beforePhase(PhaseEvent event)
	{
		Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);

		if (e != null && e instanceof BadCredentialsException)
		{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, CREDENTIAL_ERROR, CREDENTIAL_ERROR));
		}
	}	
}
