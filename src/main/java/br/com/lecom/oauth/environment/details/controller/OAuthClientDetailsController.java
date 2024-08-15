package br.com.lecom.oauth.environment.details.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lecom.oauth.config.annotations.ApiPageable;
import br.com.lecom.oauth.environment.details.controller.filter.OAuthClientDetailsFilter;
import br.com.lecom.oauth.environment.details.dto.OAuthClientDetailsDTO;
import br.com.lecom.oauth.environment.details.dto.OAuthClientDetailsSecretResetDTO;
import br.com.lecom.oauth.environment.details.dto.OAuthClientDetailsToSaveDTO;
import br.com.lecom.oauth.environment.details.usecase.DeleteOAuthClientDetailsUseCase;
import br.com.lecom.oauth.environment.details.usecase.ResetSecretOAuthClientDetailsUseCase;
import br.com.lecom.oauth.environment.details.usecase.RetrieveOAuthClientDetailsUseCase;
import br.com.lecom.oauth.environment.details.usecase.RetrievePagedOAuthClientDetailsUseCase;
import br.com.lecom.oauth.environment.details.usecase.SaveOAuthClientDetailsUseCase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;

@Api(tags = "Módulos")
@RestController
@RequestMapping("/api/environments/{environmentId}/details")
public class OAuthClientDetailsController {

	@Autowired
	private RetrieveOAuthClientDetailsUseCase retrieveOAuthClientDetailsUseCase;

	@Autowired
	private RetrievePagedOAuthClientDetailsUseCase retrievePagedOAuthClientDetailsUseCase;

	@Autowired
	private SaveOAuthClientDetailsUseCase saveOAuthClientDetailsUseCase;

	@Autowired
	private ResetSecretOAuthClientDetailsUseCase resetOAuthClientDetailsUseCase;

	@Autowired
	private DeleteOAuthClientDetailsUseCase deleteOAuthClientDetailsUseCase;

	@ApiOperation("Listagem de todos os módulos em um determinado ambiente")
	@ApiPageable
	@Secured("ROLE_INFRA")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedModel<OAuthClientDetailsDTO>> retrieveAll(
			final @PathVariable("environmentId") @ApiParam("Identificador do ambiente") long environmentId,
			final OAuthClientDetailsFilter filter, final @PageableDefault(page = 0, size = 20) Pageable pageable) {
		return ResponseEntity.ok(retrievePagedOAuthClientDetailsUseCase.execute(environmentId, filter, pageable));
	}

	@ApiOperation("Criação de novos módulos em um determinado ambiente")
	@Secured("ROLE_INFRA")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<OAuthClientDetailsDTO> save(
			final @PathVariable("environmentId") @ApiParam("Identificador do ambiente") long environmentId,
			@Valid @RequestBody final OAuthClientDetailsToSaveDTO authClientDetailsDTO) {
		return new ResponseEntity<>(saveOAuthClientDetailsUseCase.execute(environmentId, authClientDetailsDTO),
				HttpStatus.CREATED);
	}

	@ApiOperation("Consulta de um módulo em um determinado ambiente pelo identificador")
	@Secured("ROLE_INFRA")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OAuthClientDetailsDTO> retrieveByClientId(
			final @PathVariable("environmentId") @ApiParam("Identificador do ambiente") long environmentId,
			@PathVariable("id") long id) {
		return ResponseEntity.ok(retrieveOAuthClientDetailsUseCase.execute(environmentId, id));
	}

	@ApiOperation("Exclusão de um módulo em um determinado ambiente pelo identificador")
	@Secured("ROLE_INFRA")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(
			final @PathVariable("environmentId") @ApiParam("Identificador do ambiente") long environmentId,
			@PathVariable("id") @ApiParam(value = "Identificador do módulo") long id) {
		deleteOAuthClientDetailsUseCase.execute(environmentId, id);
		return ResponseEntity.ok().build();
	}

	@ApiOperation("Redefinição da senha de um módulo em um determinado ambiente pelo identificador. A senha antiga será descartada e uma outra será gerada")
	@Secured("ROLE_INFRA")
	@PutMapping(value = "/{id}/secret", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OAuthClientDetailsSecretResetDTO> resetSecret(
			final @PathVariable("environmentId") @ApiParam("Identificador do ambiente") long environmentId,
			@PathVariable("id") @ApiParam(value = "Identificador do módulo") long id) {
		return ResponseEntity.ok(resetOAuthClientDetailsUseCase.execute(environmentId, id));
	}

}