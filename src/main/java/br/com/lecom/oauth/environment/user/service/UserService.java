package br.com.lecom.oauth.environment.user.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.lecom.oauth.environment.model.Environments;
import br.com.lecom.oauth.environment.user.dto.UserDTO;
import br.com.lecom.oauth.environment.user.model.User;
import br.com.lecom.oauth.environment.user.model.User.UserKey;
import br.com.lecom.oauth.environment.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User saveUser(UserDTO userDTO, final Environments client) {
		final User user = new User();
		user.setActivated(true);
		user.setActivationKey(userDTO.getActivationKey());
		user.setAuthorities(userDTO.getAuthorities().stream().collect(Collectors.joining(",")));
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setResetPasswordKey(userDTO.getResetPasswordKey());
		user.setId(new UserKey(userDTO.getUsername(), client));
		return userRepository.save(user);
	}

}
