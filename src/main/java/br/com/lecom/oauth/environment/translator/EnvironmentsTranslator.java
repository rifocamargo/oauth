package br.com.lecom.oauth.environment.translator;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.controller.EnvironmentsController;
import br.com.lecom.oauth.environment.dto.EnvironmentsDTO;
import br.com.lecom.oauth.environment.model.Environments;

@Component
public class EnvironmentsTranslator extends RepresentationModelAssemblerSupport<Environments, EnvironmentsDTO> {

	public EnvironmentsTranslator() {
		super(EnvironmentsController.class, EnvironmentsDTO.class);
	}

	@Override
	public EnvironmentsDTO toModel(Environments entity) {
		final EnvironmentsDTO environmentsDTO = new EnvironmentsDTO();
		environmentsDTO.setId(entity.getId());
		environmentsDTO.setHostname(entity.getHostname());
		environmentsDTO.setCreatedAt(entity.getCreatedAt());

		environmentsDTO.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(EnvironmentsController.class).retrieveByClientId(entity.getId()))
				.withRel("edit"));
		environmentsDTO.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(EnvironmentsController.class).delete(entity.getId())).withRel("delete"));
		return environmentsDTO;
	}

}
