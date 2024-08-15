package br.com.lecom.oauth.environment.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.controller.filter.EnvironmentsFilter;
import br.com.lecom.oauth.environment.dto.EnvironmentsDTO;
import br.com.lecom.oauth.environment.model.Environments;
import br.com.lecom.oauth.environment.repository.EnvironmentsRepository;
import br.com.lecom.oauth.environment.translator.EnvironmentsTranslator;

@Component
public class RetrievePagedEnvironmentsUseCase {

	@Autowired
	private PagedResourcesAssembler<Environments> pagedResourcesAssembler;

	@Autowired
	private EnvironmentsRepository repository;

	@Autowired
	private EnvironmentsTranslator translator;

	public PagedModel<EnvironmentsDTO> execute(final EnvironmentsFilter filter, final Pageable pageable) {
		final Page<Environments> page = repository.filter(filter, pageable);
		return pagedResourcesAssembler.toModel(page, translator);
	}
}
