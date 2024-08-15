package br.com.lecom.oauth.environment.details.controller.filter;

import io.swagger.annotations.ApiParam;

public class OAuthClientDetailsFilter {

	@ApiParam(value = "Identificador único do módulo")
	private String clientId;

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
