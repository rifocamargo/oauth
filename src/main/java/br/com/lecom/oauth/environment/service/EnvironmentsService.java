package br.com.lecom.oauth.environment.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lecom.oauth.environment.dto.EnvironmentsToSaveDTO;
import br.com.lecom.oauth.environment.model.Environments;
import br.com.lecom.oauth.environment.repository.EnvironmentsRepository;

@Service
public class EnvironmentsService {

	@Autowired
	private EnvironmentsRepository repository;

	public Environments saveClient(final EnvironmentsToSaveDTO dto) {
		Environments clients = new Environments();
		clients.setHostname(dto.getHostname());
		clients.setCreatedAt(new Date());

		return repository.save(clients);
	}

}
