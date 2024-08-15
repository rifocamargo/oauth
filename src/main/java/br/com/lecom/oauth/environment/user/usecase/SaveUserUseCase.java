package br.com.lecom.oauth.environment.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.model.Environments;
import br.com.lecom.oauth.environment.repository.EnvironmentsRepository;
import br.com.lecom.oauth.environment.user.dto.UserDTO;
import br.com.lecom.oauth.environment.user.model.User;
import br.com.lecom.oauth.environment.user.service.UserService;
import br.com.lecom.oauth.environment.user.translator.UserTranslator;
import br.com.lecom.oauth.environment.user.validators.UsersValidator;

@Component
public class SaveUserUseCase {

	@Autowired
	private UserService userService;

	@Autowired
	private UserTranslator userTranslator;

	@Autowired
	private UsersValidator usersValidator;

	@Autowired
	private EnvironmentsRepository clientsRepository;

	public UserDTO execute(final long clientId, UserDTO userDTO) {

		final Environments client = clientsRepository.findById(clientId)
				.orElseThrow(() -> new EmptyResultDataAccessException("Client not found", 1));

		usersValidator.verifyIfExists(userDTO.getUsername(), clientId);

		User user = userService.saveUser(userDTO, client);
		return userTranslator.toModel(user);
	}
}
