package br.com.lecom.oauth.environment.details.model;

import java.io.Serializable;
import java.util.Objects;

import br.com.lecom.oauth.environment.model.Environments;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "oauth_client_details")
@SequenceGenerator(name = "SEQ_OAUTH_CLIENT_DETAILS_ID", sequenceName = "SEQ_OAUTH_CLIENT_DETAILS_ID", allocationSize = 1, initialValue = 1)
public class OAuthClientDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8344739531350812534L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_OAUTH_CLIENT_DETAILS_ID")
	private Long id;

	@Column(name = "client_id", updatable = false, nullable = false)
	@Size(min = 0, max = 256)
	private String clientId;

	@ManyToOne
	@JoinColumn(name = "environment_id")
	private Environments environments;

	@Column(name = "resource_ids")
	@Size(min = 0, max = 255)
	private String resourceIds;

	@Column(name = "client_secret")
	@Size(min = 0, max = 255)
	private String clientSecret;

	@Column
	@Size(min = 0, max = 255)
	private String scope;

	@Column(name = "authorized_grant_types")
	@Size(min = 0, max = 255)
	private String authorizedGrantTypes;

	@Column(name = "web_server_redirect_uri")
	@Size(min = 0, max = 255)
	private String webServerRedirectUri;

	@Column
	@Size(min = 0, max = 255)
	private String authorities;

	@Column(name = "access_token_validity")
	private Integer accessTokenValidity;

	@Column(name = "refresh_token_validity")
	private Integer refreshTokenValidity;

	@Column(name = "additional_information")
	@Size(min = 0, max = 4096)
	private String additionalInformation;

	@Column
	@Size(min = 0, max = 255)
	private String autoapprove;

	public OAuthClientDetails(Long id, @Size(min = 0, max = 256) String clientId, Environments environments) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.environments = environments;
	}

	public OAuthClientDetails() {
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

	/**
	 * @return the resourceIds
	 */
	public String getResourceIds() {
		return resourceIds;
	}

	/**
	 * @param resourceIds the resourceIds to set
	 */
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
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

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return the authorizedGrantTypes
	 */
	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	/**
	 * @param authorizedGrantTypes the authorizedGrantTypes to set
	 */
	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	/**
	 * @return the webServerRedirectUri
	 */
	public String getWebServerRedirectUri() {
		return webServerRedirectUri;
	}

	/**
	 * @param webServerRedirectUri the webServerRedirectUri to set
	 */
	public void setWebServerRedirectUri(String webServerRedirectUri) {
		this.webServerRedirectUri = webServerRedirectUri;
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
	 * @return the accessTokenValidity
	 */
	public Integer getAccessTokenValidity() {
		return accessTokenValidity;
	}

	/**
	 * @param accessTokenValidity the accessTokenValidity to set
	 */
	public void setAccessTokenValidity(Integer accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	/**
	 * @return the refreshTokenValidity
	 */
	public Integer getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	/**
	 * @param refreshTokenValidity the refreshTokenValidity to set
	 */
	public void setRefreshTokenValidity(Integer refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}

	/**
	 * @return the additionalInformation
	 */
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	/**
	 * @param additionalInformation the additionalInformation to set
	 */
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	/**
	 * @return the autoapprove
	 */
	public String getAutoapprove() {
		return autoapprove;
	}

	/**
	 * @param autoapprove the autoapprove to set
	 */
	public void setAutoapprove(String autoapprove) {
		this.autoapprove = autoapprove;
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
		return Objects.hash(clientId, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof OAuthClientDetails)) {
			return false;
		}
		OAuthClientDetails other = (OAuthClientDetails) obj;
		return Objects.equals(clientId, other.clientId) && Objects.equals(id, other.id);
	}

}
