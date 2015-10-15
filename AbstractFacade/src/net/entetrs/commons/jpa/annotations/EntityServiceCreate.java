package net.entetrs.commons.jpa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * active la fonctionnalité "create" de EntityService.
 * 
 * @author CDT ROBIN
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface EntityServiceCreate 
{
    String[] roles() default {"all"};
}
