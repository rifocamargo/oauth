package br.com.lecom.oauth.environment.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "SEQ_ENVIRONMENT_ID", sequenceName = "SEQ_ENVIRONMENT_ID", allocationSize = 1, initialValue = 1)
public class Environments implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -589804329125268412L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ENVIRONMENT_ID")
	private Long id;

	@Column(name = "hostname", nullable = false, unique = true)
	@Size(min = 0, max = 255)
	private String hostname;

	@Column(name = "created_at")
	private Date createdAt;

	public Environments() {
	}

	public Environments(Long id) {
		super();
		this.id = id;
	}

	public Environments(@Size(min = 0, max = 255) String hostname) {
		super();
		this.hostname = hostname;
	}

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
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * @param hostname the hostname to set
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
		return Objects.hash(hostname, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Environments)) {
			return false;
		}
		Environments other = (Environments) obj;
		return Objects.equals(hostname, other.hostname) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Clients [hostname=" + hostname + "]";
	}

}
