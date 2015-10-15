package net.entetrs.commons.uuid;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Set;
import java.util.UUID;

import net.entetrs.commons.introspection.IntrospectionUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * classe utilitaire pour l'injection d'un UUID dans un champ
 * d'un objet. Ce champ doit être de type String ou byte[].
 * 
 * 
 * @author CDT ROBIN
 *
 */

public final class UUIDInjector
{

	private static final String ERROR_MESSAGE = "Erreur pendant l'injection de l'UUID.";
	private static final String ERROR_DETAILS = "Le champ annoté avec @GeneratedUUID %s n'est pas de type String ou byte[].";
	
	protected static Log log = LogFactory.getLog(UUIDInjector.class);

	private UUIDInjector()
	{
		// Protection du constructeur.
	}

	/**
	 * parcours les champs de type String ou byte[] et vérifie que l'annotation
	 * GeneratedUUID est présente. Si cela est le cas, elle est injectée.
	 * 
	 * @param t
	 */
	public final static <T> void inject(T t)
	{
		Set<Field> fields = IntrospectionUtils.getAllFields(t.getClass());

		for (Field f : fields)
		{
			if (f.isAnnotationPresent(GeneratedUUID.class))
			{
				UUID uuid = UUID.randomUUID();
				Class<?> fieldClass = f.getType();
				String fieldName = f.getName();

				try
				{
					boolean accessible = f.isAccessible();
					f.setAccessible(true);

					if (fieldClass.equals(String.class))
					{	
						GeneratedUUID annotation = f.getAnnotation(GeneratedUUID.class);
						String value = null;
						
						if ("base64".equals(annotation.format()))
						{
							// mode base64
							value = Base64.getEncoder().encodeToString(UUIDUtils.convertToBytes(uuid));
						}
						else
						{   
							// mode hexadécimal pur
							value = uuid.toString();
						}		
						
						// injection de la valeur
						f.set(t, value);
					}
					else
					{
						// mode binaire
						if (fieldClass.isArray() && fieldClass.getComponentType().equals(byte.class))
						{
							byte[] buffer = UUIDUtils.convertToBytes(uuid);
							// injection de la valeur
							f.set(t, buffer);
							
						}
						else
						{
							if (log.isInfoEnabled())
							{
								log.info(ERROR_MESSAGE);
								log.info(String.format(ERROR_DETAILS, fieldName));
							}
						}
					}
					f.setAccessible(accessible);
				}
				catch (IllegalAccessException | SecurityException e)
				{
					if (log.isErrorEnabled())
					{
						log.error(ERROR_MESSAGE, e);
					}
				}
			}
		}
	}

}
