package br.com.lecom.oauth.environment.details.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.details.repository.OAuthClientDetailsRepository;

@Component
public class OAuthClientDetailsValidator {

	@Autowired
	private OAuthClientDetailsRepository oAuthClientDetailsRepository;

	public void verifyIfExists(final String clientId) {
		oAuthClientDetailsRepository.findByClientId(clientId).ifPresent(user -> {
			throw new DuplicateKeyException("Client details already exists");
		});
	}
}
