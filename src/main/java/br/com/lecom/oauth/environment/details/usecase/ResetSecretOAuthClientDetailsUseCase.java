package br.com.lecom.oauth.environment.details.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.details.dto.OAuthClientDetailsSecretResetDTO;
import br.com.lecom.oauth.environment.details.model.OAuthClientDetails;
import br.com.lecom.oauth.environment.details.repository.OAuthClientDetailsRepository;
import br.com.lecom.oauth.environment.details.service.OAuthClientDetailsService;

@Component
public class ResetSecretOAuthClientDetailsUseCase {

	@Autowired
	private OAuthClientDetailsRepository repository;

	@Autowired
	private OAuthClientDetailsService service;

	public OAuthClientDetailsSecretResetDTO execute(final long environmentId, long id) {
		final OAuthClientDetails entity = repository.findByIdAndEnvironmentsId(id, environmentId)
				.orElseThrow(() -> new EmptyResultDataAccessException("OAuthClientDetails not found", 1));
		return new OAuthClientDetailsSecretResetDTO(service.resetSecret(entity));
	}
}
