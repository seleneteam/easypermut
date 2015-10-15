package net.entetrs.commons.jpa;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.entetrs.commons.introspection.IntrospectionUtils;
import net.entetrs.commons.jpa.annotations.EntityServiceAll;
import net.entetrs.commons.jpa.annotations.EntityServiceCreate;
import net.entetrs.commons.jpa.annotations.EntityServiceDelete;
import net.entetrs.commons.jpa.annotations.EntityServiceRead;
import net.entetrs.commons.jpa.annotations.EntityServiceUpdate;
import net.entetrs.commons.logs.LogUtils;
import net.entetrs.commons.logs.LogUtils.LogLevel;

/**
 * classe qui permet d'activer par annotations les fonctionnalités offertes via
 * JPA.
 * 
 * @author CDT ROBIN
 *
 * @param <T>
 */

public abstract class AbstractEntityService<T> extends	AbstractFacadeJavaEntrepriseEdition<T> {
	/**
	 * messages d'erreurs.
	 */
	private static final String SUPPRESSION_NON_ACTIVEE = "Suppression non activée par @EntityServiceDelete";
	private static final String MODIFICATION_NON_ACTIVEE = "Modification non activée par @EntityServiceUpdate";
	private static final String LECTURE_NON_ACTIVEE = "Lecture non activée par @EntityServiceRead";
	private static final String CREATION_NON_ACTIVEE = "Création non activée par @EntityServiceCreate";

	private enum Capability 
	{
		ALL(EntityServiceAll.class), 
		CREATE(EntityServiceCreate.class),
		READ(EntityServiceRead.class), 
		UPDATE(EntityServiceUpdate.class), 
		DELETE(EntityServiceDelete.class);
		
		private final Class <? extends Annotation> bindedAnnotation;
		
		private Capability(Class <? extends Annotation> bindedAnnotation) 
		{
			this.bindedAnnotation = bindedAnnotation;
		}
		
		public Class<? extends Annotation> getBindedAnnotation() 
		{
			return bindedAnnotation;
		}
	};

	/**
	 * fonctionnalités activées ou non.
	 */
	private boolean canCreate = false;
	private boolean canRead = false;
	private boolean canUpdate = false;
	private boolean canDelete = false;

	/**
	 * droits fin en fonction des roles.
	 */
	private Map<Capability, String[]> acl = new HashMap<>();

	@Override
	@PostConstruct
	public void init() {
		super.init();

		if (IntrospectionUtils.isClassAnnotatedWith(EntityServiceAll.class,
				this.getClass())) {
			// @EntityServiceAll est présente, on active tout.
			this.canCreate = true;
			this.canRead = true;
			this.canUpdate = true;
			this.canDelete = true;
		} else {
			// @On vérifie toutes les annotation @EntityService**
			this.canCreate = IntrospectionUtils.isClassAnnotatedWith(
					EntityServiceCreate.class, this.getClass());
			this.canRead = IntrospectionUtils.isClassAnnotatedWith(
					EntityServiceRead.class, this.getClass());
			this.canUpdate = IntrospectionUtils.isClassAnnotatedWith(
					EntityServiceUpdate.class, this.getClass());
			this.canDelete = IntrospectionUtils.isClassAnnotatedWith(
					EntityServiceDelete.class, this.getClass());
		}

		this.populateRoles();
		
		// on log les capacités de la classe.
		this.logCapabilities();
	}

	private void populateRoles() 
	{
		for(Capability c : Capability.values())
		{
			if (IntrospectionUtils.isClassAnnotatedWith(c.getBindedAnnotation(), this.getClass()))
			{
				String[] roles = (String[]) IntrospectionUtils.getAnnotationAttribute(this.getClass(), c.getBindedAnnotation(), "roles");
				if (roles != null) 
				{
					acl.put(c, roles);
				}
			}
		}
	}

	/**
	 * loggue les fonctionnalité de la classe.
	 */
	private void logCapabilities() {
		LogUtils.logFormat(this.getLog(), LogLevel.INFO, "canCreate ? %b",
				canCreate);
		LogUtils.logFormat(this.getLog(), LogLevel.INFO, "canRead ? %b",
				canRead);
		LogUtils.logFormat(this.getLog(), LogLevel.INFO, "canUpdate ? %b",
				canUpdate);
		LogUtils.logFormat(this.getLog(), LogLevel.INFO, "canDelete ? %b",
				canDelete);
	}

	@Override
	public void create(T t) {
		if (canCreate) {
			super.create(t);
		} else {
			throw new SecurityException(CREATION_NON_ACTIVEE);
		}
	}

	@Override
	public T read(Object id) {
		if (canRead) {
			return super.read(id);
		} else {
			throw new SecurityException(LECTURE_NON_ACTIVEE);
		}
	}

	@Override
	public List<T> readAll() {
		if (canRead) {
			return super.readAll();
		} else {
			throw new SecurityException(LECTURE_NON_ACTIVEE);
		}
	}

	@Override
	public List<T> search(String parameterName, Object parameterValue,
			String... orderBy) {
		if (canRead) {
			return super.search(parameterName, parameterValue, orderBy);
		} else {
			throw new SecurityException(LECTURE_NON_ACTIVEE);
		}
	}

	@Override
	public T searchFirstResult(String parameterName, Object parameterValue) {
		if (canRead) {
			return super.searchFirstResult(parameterName, parameterValue);
		} else {
			throw new SecurityException(LECTURE_NON_ACTIVEE);
		}
	}

	@Override
	public List<T> advancedSearch(Map<String, String> filters,
			String sortColumn, SortOrder sortOrder, int first, int pageSize) {
		if (canRead) {
			return super.advancedSearch(filters, sortColumn, sortOrder, first,
					pageSize);
		} else {
			throw new SecurityException(LECTURE_NON_ACTIVEE);
		}
	}

	@Override
	public T update(T t) {
		if (canUpdate) {
			return super.update(t);
		} else {
			throw new SecurityException(MODIFICATION_NON_ACTIVEE);
		}
	}

	@Override
	public void delete(T t) {
		if (canDelete) {
			super.delete(t);
		} else {
			throw new SecurityException(SUPPRESSION_NON_ACTIVEE);
		}
	}
}
