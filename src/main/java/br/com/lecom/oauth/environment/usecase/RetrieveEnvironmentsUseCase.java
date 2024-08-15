package br.com.lecom.oauth.environment.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.dto.EnvironmentsDTO;
import br.com.lecom.oauth.environment.model.Environments;
import br.com.lecom.oauth.environment.repository.EnvironmentsRepository;
import br.com.lecom.oauth.environment.translator.EnvironmentsTranslator;

@Component
public class RetrieveEnvironmentsUseCase {

	@Autowired
	private EnvironmentsRepository repository;

	@Autowired
	private EnvironmentsTranslator translator;

	public EnvironmentsDTO execute(final long id) {
		final Environments entity = repository.findById(id)
				.orElseThrow(() -> new EmptyResultDataAccessException("Client not found", 1));
		return translator.toModel(entity);
	}
}
