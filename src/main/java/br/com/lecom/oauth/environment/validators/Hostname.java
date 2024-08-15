package br.com.lecom.oauth.environment.validators;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RUNTIME)
@Target({ METHOD, FIELD, PARAMETER, ANNOTATION_TYPE })
@Constraint(validatedBy = HostnameValidator.class)
@Documented
public @interface Hostname {
	String message() default "Need is a hostname";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
