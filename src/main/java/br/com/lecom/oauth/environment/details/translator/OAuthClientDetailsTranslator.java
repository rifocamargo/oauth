package br.com.lecom.oauth.environment.details.translator;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.lecom.oauth.environment.details.controller.OAuthClientDetailsController;
import br.com.lecom.oauth.environment.details.dto.OAuthClientDetailsDTO;
import br.com.lecom.oauth.environment.details.model.OAuthClientDetails;
import br.com.lecom.oauth.environment.user.controller.UsersController;

@Component
public class OAuthClientDetailsTranslator
		extends RepresentationModelAssemblerSupport<OAuthClientDetails, OAuthClientDetailsDTO> {

	public OAuthClientDetailsTranslator() {
		super(UsersController.class, OAuthClientDetailsDTO.class);
	}

	@Override
	public OAuthClientDetailsDTO toModel(OAuthClientDetails entity) {
		OAuthClientDetailsDTO authClientDetailsDTO = new OAuthClientDetailsDTO();
		authClientDetailsDTO.setId(entity.getId());
		authClientDetailsDTO.setAccessTokenValidity(entity.getAccessTokenValidity());
		authClientDetailsDTO.setAdditionalInformation(entity.getAdditionalInformation());
		authClientDetailsDTO.setAuthorities(entity.getAuthorities());
		authClientDetailsDTO.setAuthorizedGrantTypes(entity.getAuthorizedGrantTypes());
		authClientDetailsDTO.setAutoapprove(entity.getAutoapprove());
		authClientDetailsDTO.setClientId(entity.getClientId());
		authClientDetailsDTO.setRefreshTokenValidity(entity.getRefreshTokenValidity());
		authClientDetailsDTO.setResourceIds(entity.getResourceIds());
		authClientDetailsDTO.setScope(entity.getScope());
		authClientDetailsDTO.setWebServerRedirectUri(entity.getWebServerRedirectUri());
		authClientDetailsDTO
				.add(WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(OAuthClientDetailsController.class)
								.retrieveByClientId(entity.getEnvironments().getId(), entity.getId()))
						.withRel("edit"));
		authClientDetailsDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OAuthClientDetailsController.class)
				.delete(entity.getEnvironments().getId(), entity.getId())).withRel("delete"));
		authClientDetailsDTO
				.add(WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(OAuthClientDetailsController.class)
								.resetSecret(entity.getEnvironments().getId(), entity.getId()))
						.withRel("reset-secret"));
		return authClientDetailsDTO;
	}

}
