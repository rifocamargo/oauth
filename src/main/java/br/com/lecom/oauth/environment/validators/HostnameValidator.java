package br.com.lecom.oauth.environment.validators;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.commons.validator.routines.RegexValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HostnameValidator implements ConstraintValidator<Hostname, String> {

	private static final RegexValidator DOT_DEV_DOT_LOCAL_ADDRESS = new RegexValidator(".*(\\.dev\\.local)");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		DomainValidator domainValidator = DomainValidator.getInstance(true);
		InetAddressValidator inetAddressValidator = InetAddressValidator.getInstance();

		return domainValidator.isValid(value) || inetAddressValidator.isValid(value)
				|| DOT_DEV_DOT_LOCAL_ADDRESS.isValid(value);
	}
}
