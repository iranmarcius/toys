/*
 * Criado em 23/03/2011 16:14:08
 */

package toys.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation com conjunto de permissões requeridas e/ou opcionais.
 * @author Iran
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permissoes {
	String[] requeridas() default {};
	String[] opcionais() default {};
}
