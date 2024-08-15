package br.com.lecom.oauth.environment.details.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.details.controller.filter.OAuthClientDetailsFilter;
import br.com.lecom.oauth.environment.details.dto.OAuthClientDetailsDTO;
import br.com.lecom.oauth.environment.details.model.OAuthClientDetails;
import br.com.lecom.oauth.environment.details.repository.OAuthClientDetailsRepository;
import br.com.lecom.oauth.environment.details.translator.OAuthClientDetailsTranslator;

@Component
public class RetrievePagedOAuthClientDetailsUseCase {

	@Autowired
	private PagedResourcesAssembler<OAuthClientDetails> pagedResourcesAssembler;

	@Autowired
	private OAuthClientDetailsRepository repository;

	@Autowired
	private OAuthClientDetailsTranslator translator;

	public PagedModel<OAuthClientDetailsDTO> execute(final long environmentId, final OAuthClientDetailsFilter filter,
			final Pageable pageable) {
		final Page<OAuthClientDetails> page = repository.filter(environmentId, filter, pageable);
		return pagedResourcesAssembler.toModel(page, translator);
	}
}
