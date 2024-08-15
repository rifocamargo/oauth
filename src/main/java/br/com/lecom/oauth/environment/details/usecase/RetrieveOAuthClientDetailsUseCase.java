package br.com.lecom.oauth.environment.details.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.details.dto.OAuthClientDetailsDTO;
import br.com.lecom.oauth.environment.details.model.OAuthClientDetails;
import br.com.lecom.oauth.environment.details.repository.OAuthClientDetailsRepository;
import br.com.lecom.oauth.environment.details.translator.OAuthClientDetailsTranslator;

@Component
public class RetrieveOAuthClientDetailsUseCase {

	@Autowired
	private OAuthClientDetailsRepository repository;

	@Autowired
	private OAuthClientDetailsTranslator translator;

	public OAuthClientDetailsDTO execute(final long environmentId, final long id) {
		final OAuthClientDetails entity = repository.findByIdAndEnvironmentsId(id, environmentId)
				.orElseThrow(() -> new EmptyResultDataAccessException("OAuthClientDetails not found", 1));
		return translator.toModel(entity);
	}
}
