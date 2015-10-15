package net.entetrs.commons.jsf.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * factory à déclarer dans faces-config.xml qui permet
 * d'instancier et retourner une instance de DefaultExceptionHandler.
 * 
 * @author CDT ROBIN
 *
 */
public class DefaultExceptionHandlerFactory extends ExceptionHandlerFactory
{

	private ExceptionHandlerFactory parent;

	public DefaultExceptionHandlerFactory(ExceptionHandlerFactory parent)
	{
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler()
	{
		return new DefaultExceptionHandler(parent.getExceptionHandler());
	}
}
