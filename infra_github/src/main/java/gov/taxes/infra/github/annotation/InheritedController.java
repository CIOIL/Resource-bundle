package gov.taxes.infra.github.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Dan Erez
 *
 * Annotation to indicate a super class of components. Saving the need for the sub class
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestController
@Inherited
public @interface InheritedController {

}
