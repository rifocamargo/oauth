package br.com.lecom.oauth.environment.dto;

import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import br.com.lecom.oauth.environment.validators.Hostname;
import io.swagger.annotations.ApiModelProperty;

public class EnvironmentsDTO extends RepresentationModel<EnvironmentsDTO> {

	@ApiModelProperty(value = "Identificador do ambiente")
	private Long id;

	@ApiModelProperty(value = "Domínio do ambiente")
	@Hostname
	private String hostname;

	@ApiModelProperty(value = "Data de criação do ambiente")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

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

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(hostname, id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof EnvironmentsDTO)) {
			return false;
		}
		EnvironmentsDTO other = (EnvironmentsDTO) obj;
		return Objects.equals(hostname, other.hostname) && Objects.equals(id, other.id);
	}

}
