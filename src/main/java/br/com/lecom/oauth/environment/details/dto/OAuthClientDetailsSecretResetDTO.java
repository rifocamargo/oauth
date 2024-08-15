package br.com.lecom.oauth.environment.details.dto;

import io.swagger.annotations.ApiModelProperty;

public class OAuthClientDetailsSecretResetDTO {

	@ApiModelProperty(value = "Senha redefinida do módulo")
	private String clientSecret;

	public OAuthClientDetailsSecretResetDTO(String clientSecret) {
		super();
		this.clientSecret = clientSecret;
	}

	/**
	 * @return the clientSecret
	 */
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * @param clientSecret the clientSecret to set
	 */
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

}
