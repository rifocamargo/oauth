package br.com.lecom.oauth.environment.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.dto.EnvironmentsDTO;
import br.com.lecom.oauth.environment.dto.EnvironmentsToSaveDTO;
import br.com.lecom.oauth.environment.model.Environments;
import br.com.lecom.oauth.environment.service.EnvironmentsService;
import br.com.lecom.oauth.environment.translator.EnvironmentsTranslator;
import br.com.lecom.oauth.environment.validators.EnvironmentsValidator;

@Component
public class SaveEnvironmentsUseCase {

	@Autowired
	private EnvironmentsService service;

	@Autowired
	private EnvironmentsTranslator translator;

	@Autowired
	private EnvironmentsValidator clientsValidator;

	public EnvironmentsDTO execute(final EnvironmentsToSaveDTO dto) {
		clientsValidator.verifyIfExists(dto.getHostname());

		final Environments clients = service.saveClient(dto);
		return translator.toModel(clients);
	}
}
