package br.com.lecom.oauth.environment.user.dto;

import java.util.Objects;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@JsonInclude(Include.NON_NULL)
public class UserDTO extends RepresentationModel<UserDTO> {

	private Long id;

	@NotNull
	private String username;

	private String password;

	@NotNull
	@Email
	private String email;

	private boolean activated;

	private String activationKey;

	private String resetPasswordKey;

	@NotNull
	private Set<String> authorities;

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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the activated
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * @param activated the activated to set
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	/**
	 * @return the activationKey
	 */
	public String getActivationKey() {
		return activationKey;
	}

	/**
	 * @param activationKey the activationKey to set
	 */
	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}

	/**
	 * @return the resetPasswordKey
	 */
	public String getResetPasswordKey() {
		return resetPasswordKey;
	}

	/**
	 * @param resetPasswordKey the resetPasswordKey to set
	 */
	public void setResetPasswordKey(String resetPasswordKey) {
		this.resetPasswordKey = resetPasswordKey;
	}

	/**
	 * @return the authorities
	 */
	public Set<String> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(email, username);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UserDTO)) {
			return false;
		}
		UserDTO other = (UserDTO) obj;
		return Objects.equals(email, other.email) && Objects.equals(username, other.username);
	}

}
