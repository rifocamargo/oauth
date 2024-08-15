package br.com.lecom.oauth.environment.details.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.lecom.oauth.environment.details.dto.OAuthClientDetailsToSaveDTO;
import br.com.lecom.oauth.environment.details.model.OAuthClientDetails;
import br.com.lecom.oauth.environment.details.repository.OAuthClientDetailsRepository;
import br.com.lecom.oauth.environment.model.Environments;

@Service
public class OAuthClientDetailsService {

	@Autowired
	private OAuthClientDetailsRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public OAuthClientDetails saveOAuthClientDetails(Environments environments, OAuthClientDetailsToSaveDTO dto) {
		final String clientSecret = UUID.randomUUID().toString();
		final OAuthClientDetails oAuthClientDetails = new OAuthClientDetails();
		oAuthClientDetails.setAccessTokenValidity(dto.getAccessTokenValidity());
		oAuthClientDetails.setAdditionalInformation(dto.getAdditionalInformation());
		oAuthClientDetails.setAuthorities(dto.getAuthorities());
		oAuthClientDetails.setAuthorizedGrantTypes(dto.getAuthorizedGrantTypes());
		oAuthClientDetails.setAutoapprove(dto.getAutoapprove());
		oAuthClientDetails.setClientId(dto.getClientId());
		oAuthClientDetails.setClientSecret(passwordEncoder.encode(clientSecret));
		oAuthClientDetails.setRefreshTokenValidity(dto.getRefreshTokenValidity());
		oAuthClientDetails.setResourceIds(dto.getResourceIds());
		oAuthClientDetails.setScope(dto.getScope());
		oAuthClientDetails.setWebServerRedirectUri(dto.getWebServerRedirectUri());
		oAuthClientDetails.setEnvironments(environments);
		
		final OAuthClientDetails authClientDetails = repository.save(oAuthClientDetails);
		authClientDetails.setClientSecret(clientSecret);
		return authClientDetails;
	}
	
	@CacheEvict(value = "oauthCache", key = "'RedisJdbcClientDetailsService.loadClientByClientId('.concat(#oAuthClientDetails.clientId).concat(')')")
	public String resetSecret(OAuthClientDetails oAuthClientDetails) {
		final String clientSecret = UUID.randomUUID().toString();
		oAuthClientDetails.setClientSecret(passwordEncoder.encode(clientSecret));
		repository.save(oAuthClientDetails);
		return clientSecret;
	}

}
