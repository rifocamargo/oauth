package br.com.lecom.oauth.environment.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.repository.EnvironmentsRepository;

@Component
public class EnvironmentsValidator {

	@Autowired
	private EnvironmentsRepository clientsRepository;

	public void verifyIfExists(final String hostname) {
		clientsRepository.findByHostname(hostname).ifPresent(user -> {
			throw new DuplicateKeyException("Environment already exists");
		});
	}
}
