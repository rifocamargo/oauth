package br.com.lecom.oauth.environment.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.user.dto.UserDTO;
import br.com.lecom.oauth.environment.user.model.User;
import br.com.lecom.oauth.environment.user.repository.UserRepository;
import br.com.lecom.oauth.environment.user.translator.UserTranslator;

@Component
public class RetrieveUserUseCase {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserTranslator userTranslator;

	public UserDTO execute(final long environmentId, final String username) {
		final User page = userRepository.findByIdUsernameAndIdEnvironmentsId(username, environmentId)
				.orElseThrow(() -> new EmptyResultDataAccessException("User not found", 1));
		return userTranslator.toModel(page);
	}
}
