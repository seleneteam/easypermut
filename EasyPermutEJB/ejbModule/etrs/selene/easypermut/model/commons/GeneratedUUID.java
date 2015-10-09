package etrs.selene.easypermut.model.commons;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * permet de faire générer, par la façade un UUID sur un champs de type :
 * 
 * <pre>
 *  - String ;
 *  - byte[16] (16 octects pour 128 bits d'un UUID).
 * </pre>
 * 
 * @author francois.robin
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GeneratedUUID {

}
