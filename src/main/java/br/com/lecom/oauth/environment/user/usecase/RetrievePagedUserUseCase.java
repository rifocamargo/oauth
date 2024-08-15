package br.com.lecom.oauth.environment.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.user.controller.filter.UserFilter;
import br.com.lecom.oauth.environment.user.dto.UserDTO;
import br.com.lecom.oauth.environment.user.model.User;
import br.com.lecom.oauth.environment.user.repository.UserRepository;
import br.com.lecom.oauth.environment.user.translator.UserTranslator;

@Component
public class RetrievePagedUserUseCase {

	@Autowired
	private PagedResourcesAssembler<User> pagedResourcesAssembler;

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserTranslator translator;

	public PagedModel<UserDTO> execute(final long clientId, final UserFilter filter, final Pageable pageable) {
		final Page<User> page = repository.filter(clientId, filter, pageable);
		return pagedResourcesAssembler.toModel(page, translator);
	}
}
