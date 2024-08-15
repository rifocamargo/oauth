package br.com.lecom.oauth.environment.details.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lecom.oauth.environment.details.model.OAuthClientDetails;

public interface OAuthClientDetailsRepository
		extends JpaRepository<OAuthClientDetails, Long>, OAuthClientDetailsRepositoryQuery {
	@Cacheable(value = "oauthCache", key = "'OAuthClientDetailsRepository.findByIdAndEnvironmentsId('.concat(#id).concat('-').concat(#environmentsId).concat(')')")
	public Optional<OAuthClientDetails> findByIdAndEnvironmentsId(final long id, final long environmentsId);
	
	@Cacheable(value = "oauthCache", key = "'OAuthClientDetailsRepository.findByClientId('.concat(#clientId).concat(')')")
	public Optional<OAuthClientDetails> findByClientId(final String clientId);
	
	@Cacheable(value = "oauthCache", key = "'OAuthClientDetailsRepository.findByClientIdAndEnvironmentsId('.concat(#clientId).concat('-').concat(#environmentsId).concat(')')")
	public Optional<OAuthClientDetails> findByClientIdAndEnvironmentsId(final String clientId, final long environmentsId);
}
