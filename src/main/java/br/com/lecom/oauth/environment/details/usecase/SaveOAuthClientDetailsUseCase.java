package br.com.lecom.oauth.environment.details.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.details.dto.OAuthClientDetailsDTO;
import br.com.lecom.oauth.environment.details.dto.OAuthClientDetailsToSaveDTO;
import br.com.lecom.oauth.environment.details.model.OAuthClientDetails;
import br.com.lecom.oauth.environment.details.service.OAuthClientDetailsService;
import br.com.lecom.oauth.environment.details.translator.OAuthClientDetailsTranslator;
import br.com.lecom.oauth.environment.details.validators.OAuthClientDetailsValidator;
import br.com.lecom.oauth.environment.model.Environments;
import br.com.lecom.oauth.environment.repository.EnvironmentsRepository;

@Component
public class SaveOAuthClientDetailsUseCase {

	@Autowired
	private OAuthClientDetailsService service;

	@Autowired
	private OAuthClientDetailsTranslator translator;

	@Autowired
	private EnvironmentsRepository clientsRepository;
	
	@Autowired
	private OAuthClientDetailsValidator oAuthClientDetailsValidator;

	public OAuthClientDetailsDTO execute(final long environmentId, OAuthClientDetailsToSaveDTO oAuthClientDetailsDTO) {
		final Environments environments = clientsRepository.findById(environmentId)
				.orElseThrow(() -> new EmptyResultDataAccessException("Environment not found", 1));
		oAuthClientDetailsDTO.setClientId(String.format("%s-%s", environments.getHostname(), oAuthClientDetailsDTO.getClientId()));
		oAuthClientDetailsValidator.verifyIfExists(oAuthClientDetailsDTO.getClientId());

		final OAuthClientDetails oAuthClientDetails = service.saveOAuthClientDetails(environments,
				oAuthClientDetailsDTO);
		final OAuthClientDetailsDTO dto = translator.toModel(oAuthClientDetails);
		dto.setClientSecret(oAuthClientDetails.getClientSecret());
		return dto;
	}
}
