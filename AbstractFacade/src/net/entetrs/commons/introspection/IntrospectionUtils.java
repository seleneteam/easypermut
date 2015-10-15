package net.entetrs.commons.introspection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * classe utilitaire permettant de bénéficier de fonctionnalités
 * d'introspection complémentaires.
 * 
 * @author CDT ROBIN
 *
 */

public class IntrospectionUtils
{
	
	private IntrospectionUtils() 
	{
		// protection du constructeur pour cette classe utilitaire.
	}
	

	/**
	 * retourne un set des champs présents dans la classe, même hérités, quelle
	 * que soit leur visibilité.
	 * 
	 * @param classe
	 *            classe à introspecter.
	 * @return set des champs.
	 */
	public static <T> Set<Field> getAllFields(Class<T> classe)
	{
		Set<Field> fields = new LinkedHashSet<Field>();
		fields.addAll(Arrays.asList(classe.getDeclaredFields()));

		if (classe.getSuperclass() != null)
		{
			// récursivité tant qu'on a pas atteind la classe mère "Object".
			fields.addAll(IntrospectionUtils.getAllFields(classe.getSuperclass()));
		}

		return fields;
	}
	
	/**
	 * renvoie "true" si la classe (ou la première de ses classes mères en remontant l'arbre d'héritage) possède l'annotation passée en paramètre.
	 * 
	 * @param annotation
	 * 		annotation à trouver.
	 * @param testedClass
	 * 		classe à vérifier.
	 * @return
	 * 		true si l'annotation a été trouvée, false dans le cas contraire.
	 */
	public static boolean isClassAnnotatedWith(Class <? extends Annotation> annotation, Class <?> testedClass)
	{
		boolean result = false;

		if (testedClass != null && annotation != null && testedClass.isAnnotationPresent(annotation)) 
		{
			result = true;
		}
		else
		{		
			// récursif si on a pas atteint la classe "Object" qui est sans classe mère.
			result = (testedClass.getSuperclass() == null) ? false : isClassAnnotatedWith(annotation, testedClass.getSuperclass());	
		}	
		return result;
	}
	
	/**
	 * TODO
	 * 
	 * @param inspectedClass
	 * @param annotation
	 * @param attributeName
	 * @return
	 */
	public static Object getAnnotationAttribute(Class <?> inspectedClass, Class <? extends Annotation> annotationClass, String attributeName)
	{
		System.out.println("On analyse la classe : " + inspectedClass);
		System.out.println("On va chercher l'annotation : " + annotationClass);
		System.out.println("A la recherche de l'attribut : " + attributeName);
		
		
		Annotation annotation = inspectedClass.getAnnotation(annotationClass);
		if (annotation == null)
		{
			System.out.println("L'annotation n'existe pas");
			return null;
		}
		else
		{
			try 
			{
				Method m = annotation.annotationType().getMethod(attributeName);
				if (m != null)
				{	
					System.out.println("trouvé l'attribut");
					return m.invoke(annotation);
				}
				else
				{
					System.out.println("pas d'attribut");
					return null;
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
			{
				System.err.printf("L'attribut %s n'existe pas sur l'annotation %s\n", attributeName, annotationClass.toString());
				System.err.printf("Erreur : " + e);
				return null;
			}
		}
	}
}
