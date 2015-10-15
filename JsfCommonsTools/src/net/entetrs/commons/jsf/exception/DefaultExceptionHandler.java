package net.entetrs.commons.jsf.exception;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
/**
 * Cet intercepteur remonte la stacktrace pour transformer l'exception
 * trouvée en une véritable erreur JSF.
 * Cet intercepteur est instancié par la factory suitée dans le même package;
 * 
 * @author CDT ROBIN
 *
 */

class DefaultExceptionHandler extends ExceptionHandlerWrapper
{

	private ExceptionHandler wrapped;

	public DefaultExceptionHandler(ExceptionHandler exceptionHandler)
	{
		this.wrapped = exceptionHandler;
	}

	@Override
	public ExceptionHandler getWrapped()
	{
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException
	{
		Iterator<ExceptionQueuedEvent> eventQueue = getUnhandledExceptionQueuedEvents().iterator();

		while (eventQueue.hasNext())
		{
			ExceptionQueuedEvent event = eventQueue.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			Throwable cause = context.getException();
			while (cause.getCause() != null)
			{
				cause = cause.getCause();
			}
			// fonctionne pour du PrimeFaces "Growl"
			// JsfUtils.sendGrowlMessage(cause.getMessage());

			// fonctionne pour du JSF pur et du PrimeFaces "Growl"
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, cause.getMessage(), ""));
		}
	}
}
