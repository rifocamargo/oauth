package br.com.lecom.oauth.environment.user.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.user.repository.UserRepository;

@Component
public class UsersValidator {

	@Autowired
	private UserRepository userRepository;

	public void verifyIfExists(final String username, final long environmentId) {

		userRepository.findByIdUsernameIgnoreCaseAndIdEnvironmentsId(username, environmentId).ifPresent(user -> {
			throw new DuplicateKeyException("User already exists");
		});
	}
}
