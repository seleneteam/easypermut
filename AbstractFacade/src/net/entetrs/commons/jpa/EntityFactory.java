package net.entetrs.commons.jpa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.entetrs.commons.logs.LogUtils;
import net.entetrs.commons.logs.LogUtils.LogLevel;
import net.entetrs.commons.uuid.UUIDInjector;


/**
 * EntityFactory générique qui permet d'instancier la classe paramétrée T et injecte un UUID sur un attribut annoté avec @GenerateUUID
 * 
 * @author CDT ROBIN
 *
 * @param <T>
 */

public class EntityFactory <T> 
{
	
	public static final String MESSAGE_CANNOT_INSTANCIATE = "Instanciation impossible : %s (%s)";
	
	/**
	 * référence vers le log de cette classe.
	 */
	private Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * référence vers une classe concrète
	 */
	private Class <T> concreteClass;

	/**
	 * instancie la factory de T.
	 * 
	 * @param concreteClass
	 */
	public EntityFactory(Class<T> concreteClass) {
		super();
		this.concreteClass = concreteClass;
	}
	
	/**
	 * construit un instance de la classe T.
	 * 
	 * @return instance de T
	 */
	public T newInstance()
	{
		try
		{
			T t = concreteClass.newInstance();
			UUIDInjector.inject(t);
			return t;
		}
		catch (Exception ex)
		{
			LogUtils.logFormat(log, LogLevel.ERROR, MESSAGE_CANNOT_INSTANCIATE, concreteClass.toString(), ex);
			return null;
		}
	}	
	
	public Class<T> getConcreteClass() 
	{
		return concreteClass;
	}

}
