package br.com.lecom.oauth.environment.details.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class OAuthClientDetailsToSaveDTO {
	
	@ApiModelProperty(value = "Nome único do módulo")
	private String clientId;

	@ApiModelProperty(value = "Recursos no qual o módulo tem acesso. Atualmente não estamos usando esse campo pra nenhum tipo de validação")
	private String resourceIds;

	@ApiModelProperty(value = "O escopo  para limitar o acesso de um aplicativo. Atualmente devemos usar o valor 'read'")
	private String scope;

	@ApiModelProperty(value = "Tipos de concessão. Usar somente o valor: client_credentials")
	private String authorizedGrantTypes;

	@ApiModelProperty(value = "Endereço de redirecionamento do módulo. Atualmente não estamos usando esse campo pra nenhum tipo de validação")
	private String webServerRedirectUri;

	@ApiModelProperty(value = "Regras de autoridades. Usar somente o valor: ROLE_APPLICATION")
	private String authorities;

	@ApiModelProperty(value = "Tempo em segundos da validade do token")
	private Integer accessTokenValidity;

	@ApiModelProperty(value = "Tempo em segundos da validade do token de atualização")
	private Integer refreshTokenValidity;

	@ApiModelProperty(value = "Informações adicionais. Atualmente não estamos usando esse campo pra nenhum tipo de validação")
	private String additionalInformation;

	@ApiModelProperty(value = "Flag para indicar se o módulo terá aprovação automática. Usar o valor: true")
	private String autoapprove;

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

}
