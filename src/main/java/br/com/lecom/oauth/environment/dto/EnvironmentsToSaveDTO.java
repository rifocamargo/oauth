package br.com.lecom.oauth.environment.dto;

import br.com.lecom.oauth.environment.validators.Hostname;
import io.swagger.annotations.ApiModelProperty;

public class EnvironmentsToSaveDTO {

	@ApiModelProperty(value = "Atributo que vai receber um domínio válido")
	@Hostname
	private String hostname;

	/**
	 * @return the clientName
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * @param hostname the clientName to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

}
