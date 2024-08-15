package br.com.lecom.oauth.environment.user.translator;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.user.controller.UsersController;
import br.com.lecom.oauth.environment.user.dto.UserDTO;
import br.com.lecom.oauth.environment.user.model.User;

@Component
public class UserTranslator extends RepresentationModelAssemblerSupport<User, UserDTO> {

	public UserTranslator() {
		super(UsersController.class, UserDTO.class);
	}

	@Override
	public UserDTO toModel(User entity) {
		UserDTO userDTO = new UserDTO();
		userDTO.setActivated(entity.isActivated());
		userDTO.setActivationKey(entity.getActivationKey());
		userDTO.setAuthorities(Stream.of(entity.getAuthorities().split(",")).collect(Collectors.toSet()));
		userDTO.setEmail(entity.getEmail());
		userDTO.setUsername(entity.getId().getUsername());

		userDTO.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UsersController.class)
						.retrieveByUsername(entity.getId().getEnvironments().getId(), entity.getId().getUsername()))
				.withRel("edit"));
		return userDTO;
	}

}
