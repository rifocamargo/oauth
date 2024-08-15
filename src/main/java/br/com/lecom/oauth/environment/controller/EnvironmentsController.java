package br.com.lecom.oauth.environment.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lecom.oauth.config.annotations.ApiPageable;
import br.com.lecom.oauth.environment.controller.filter.EnvironmentsFilter;
import br.com.lecom.oauth.environment.dto.EnvironmentsDTO;
import br.com.lecom.oauth.environment.dto.EnvironmentsToSaveDTO;
import br.com.lecom.oauth.environment.usecase.DeleteEnvironmentsUseCase;
import br.com.lecom.oauth.environment.usecase.RetrieveEnvironmentsUseCase;
import br.com.lecom.oauth.environment.usecase.RetrievePagedEnvironmentsUseCase;
import br.com.lecom.oauth.environment.usecase.SaveEnvironmentsUseCase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;

@Api(tags = "Ambientes")
@RestController
@RequestMapping("/api/environments")
public class EnvironmentsController {

	@Autowired
	private RetrievePagedEnvironmentsUseCase retrievePagedClientsUseCase;

	@Autowired
	private SaveEnvironmentsUseCase saveClientsUseCase;

	@Autowired
	private RetrieveEnvironmentsUseCase retrieveClientsUseCase;

	@Autowired
	private DeleteEnvironmentsUseCase deleteClientsUseCase;

	@ApiOperation("Listagem de todos os ambientes")
	@ApiPageable
	@Secured("ROLE_INFRA")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedModel<EnvironmentsDTO>> retrieveAll(final EnvironmentsFilter filter,
			final @PageableDefault(page = 0, size = 20, sort = {"createdAt"}) Pageable pageable) {
		return ResponseEntity.ok(retrievePagedClientsUseCase.execute(filter, pageable));
	}

	@ApiOperation("Criação de novos ambientes")
	@Secured("ROLE_INFRA")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<EnvironmentsDTO> save(@Valid @RequestBody final EnvironmentsToSaveDTO dto) {
		return new ResponseEntity<>(saveClientsUseCase.execute(dto), HttpStatus.CREATED);
	}

	@ApiOperation("Consulta de um ambiente específico pelo identificador")
	@Secured("ROLE_INFRA")
	@GetMapping(value = "/{environmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EnvironmentsDTO> retrieveByClientId(final @PathVariable("environmentId") @ApiParam("Identificador do ambiente") long id) {
		return ResponseEntity.ok(retrieveClientsUseCase.execute(id));
	}

	@ApiOperation("Exclusão de um ambiente específico pelo identificador")
	@Secured("ROLE_INFRA")
	@DeleteMapping("/{environmentId}")
	public ResponseEntity<Void> delete(final @PathVariable("environmentId") @ApiParam("Identificador do ambiente") long id) {
		deleteClientsUseCase.execute(id);
		return ResponseEntity.ok().build();
	}
}
