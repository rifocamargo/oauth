package br.com.lecom.oauth.environment.user.model;

import java.io.Serializable;
import java.util.Objects;

import br.com.lecom.oauth.environment.model.Environments;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7839949060231613642L;

	@EmbeddedId
	private UserKey id;

	@Size(min = 0, max = 500)
	private String password;

	@Email
	@Size(min = 0, max = 50)
	private String email;

	private boolean activated;

	@Size(min = 0, max = 100)
	@Column(name = "activationkey")
	private String activationKey;

	@Size(min = 0, max = 100)
	@Column(name = "resetpasswordkey")
	private String resetPasswordKey;

	@Size(min = 0, max = 500)
	private String authorities;

	/**
	 * @return the id
	 */
	public UserKey getId() {
		return id;
	}

	/**
	 * @return the authorities
	 */
	public String getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UserKey id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public String getActivationKey() {
		return activationKey;
	}

	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}

	public String getResetPasswordKey() {
		return resetPasswordKey;
	}

	public void setResetPasswordKey(String resetPasswordKey) {
		this.resetPasswordKey = resetPasswordKey;
	}

	@Embeddable
	public static class UserKey implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4554211507930188174L;

		@Column(updatable = false, nullable = false)
		@Size(min = 0, max = 50)
		private String username;

		@ManyToOne
		@JoinColumn(name = "environment_id")
		private Environments environments;

		public UserKey(String username, Environments client) {
			this.username = username;
			this.environments = client;
		}

		public UserKey() {
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
		 * @return the environments
		 */
		public Environments getEnvironments() {
			return environments;
		}

		/**
		 * @param environments the environments to set
		 */
		public void setEnvironments(Environments environments) {
			this.environments = environments;
		}

		@Override
		public int hashCode() {
			return Objects.hash(environments, username);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof UserKey)) {
				return false;
			}
			UserKey other = (UserKey) obj;
			return Objects.equals(environments, other.environments) && Objects.equals(username, other.username);
		}

		@Override
		public String toString() {
			return "UserKey [username=" + username + ", environments=" + environments + "]";
		}

	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}

}
