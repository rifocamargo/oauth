package br.com.lecom.oauth.environment.details.usecase;

import org.springframework.cache.CacheManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.details.model.OAuthClientDetails;
import br.com.lecom.oauth.environment.details.repository.OAuthClientDetailsRepository;

@Component
public class DeleteOAuthClientDetailsUseCase {

	private OAuthClientDetailsRepository repository;

	private CacheManager cacheManager;

	public DeleteOAuthClientDetailsUseCase(OAuthClientDetailsRepository repository, CacheManager cacheManager) {
		this.repository = repository;
		this.cacheManager = cacheManager;
	}

	public void execute(final long environmentId, final long id) {
		final OAuthClientDetails entity = repository.findByIdAndEnvironmentsId(id, environmentId)
				.orElseThrow(() -> new EmptyResultDataAccessException("OAuthClientDetails not found", 1));
		cacheManager.getCache("oauthCache").evict(String.format("RedisJdbcClientDetailsService.loadClientByClientId(%s)", entity.getClientId()));
		repository.delete(entity);
	}
}
