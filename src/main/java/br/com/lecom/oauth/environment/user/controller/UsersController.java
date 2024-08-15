package br.com.lecom.oauth.environment.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lecom.oauth.environment.user.controller.filter.UserFilter;
import br.com.lecom.oauth.environment.user.dto.UserDTO;
import br.com.lecom.oauth.environment.user.usecase.RetrievePagedUserUseCase;
import br.com.lecom.oauth.environment.user.usecase.RetrieveUserUseCase;
import br.com.lecom.oauth.environment.user.usecase.SaveUserUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/environments/{environmentId}/users")
public class UsersController {

	@Autowired
	public RetrieveUserUseCase retrieveUserUseCase;

	@Autowired
	public RetrievePagedUserUseCase retrievePagedUserUseCase;

	@Autowired
	private SaveUserUseCase saveUserUseCase;

	@Secured("ROLE_INFRA")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedModel<UserDTO>> retrieveUsers(@PathVariable("environmentId") final long environmentId,
			final UserFilter filter, final @PageableDefault(page = 0, size = 20) Pageable pageable) {
		return ResponseEntity.ok(retrievePagedUserUseCase.execute(environmentId, filter, pageable));
	}

	@Secured("ROLE_INFRA")
	@GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> retrieveByUsername(@PathVariable("environmentId") final long environmentId,
			@PathVariable("username") String username) {
		return ResponseEntity.ok(retrieveUserUseCase.execute(environmentId, username));
	}

	@Secured("ROLE_INFRA")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> save(@PathVariable("environmentId") final long environmentId,
			@Valid @RequestBody final UserDTO userDTO) {
		return new ResponseEntity<>(saveUserUseCase.execute(environmentId, userDTO), HttpStatus.CREATED);
	}
}