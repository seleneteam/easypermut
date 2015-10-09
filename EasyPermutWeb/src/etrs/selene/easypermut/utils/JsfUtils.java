package etrs.selene.easypermut.utils;



import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;

import lombok.extern.apachecommons.CommonsLog;

/**
 * Outillage générique JSF.
 *
 * @author francois.robin
 */

@Named
@ApplicationScoped
@CommonsLog
public class JsfUtils
{
	
	

    /**
     * Envoie un message JSF pour l'IHM. Classe : INFO
     *
     * @param message
     */
    public static void sendMessage(String message)
    {
    	if (log.isInfoEnabled()) log.info("MESSAGE JSF : " + message);		   
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
    }

    /**
     * Envoie le message d'une exception à l'IHM. Classe : ERROR
     *
     * @param ex
     */
    public static void sendMessage(Exception ex)
    {
    	if (log.isErrorEnabled()) log.error(ex.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
    }

    /**
     * Envoie un message de classe INFO à un composant JSF identifié par son "id".
     *
     * @param composantId
     * @param message
     */
    public static void sendMessage(String composantId, String message)
    {
        FacesContext.getCurrentInstance().addMessage(composantId, new FacesMessage(message));
    }

    /**
     * Dépose une instance d'objet en fonction d'une clé (String) dans le scope FLASH de JSF 2.
     *
     * @param key
     * @param data
     */
    public static void putInFlashScope(String key, Object data)
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Flash flash = ctx.getExternalContext().getFlash();
        flash.put(key, data);
    }

    /**
     * Récupère l'instance d'un objet en fonction d'une clé (String) dans le scope FLASH de JSF 2.
     *
     * @param key
     * @return
     */
    public static Object getFromFlashScope(String key)
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Flash flash = ctx.getExternalContext().getFlash();
        return flash.get(key);
    }
}
