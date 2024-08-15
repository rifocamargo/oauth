package br.com.lecom.oauth.environment.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.model.Environments;
import br.com.lecom.oauth.environment.repository.EnvironmentsRepository;

@Component
public class DeleteEnvironmentsUseCase {

	@Autowired
	private EnvironmentsRepository repository;

	public void execute(final long id) {
		final Environments entity = repository.findById(id)
				.orElseThrow(() -> new EmptyResultDataAccessException("Client not found", 1));
		repository.delete(entity);
	}
}
