package br.com.lecom.oauth.environment.details.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.lecom.oauth.environment.details.controller.filter.OAuthClientDetailsFilter;
import br.com.lecom.oauth.environment.details.model.OAuthClientDetails;

public interface OAuthClientDetailsRepositoryQuery {
	public Page<OAuthClientDetails> filter(final long environmentId, final OAuthClientDetailsFilter filter, final Pageable pageable);
}
