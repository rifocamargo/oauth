package br.com.lecom.oauth.db.changelog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.lecom.oauth.config.security.SecurityConfig;
import liquibase.change.custom.CustomSqlChange;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawSqlStatement;

public class InsertDefaultOAuthClientDetails implements CustomSqlChange {
	private static final Logger LOGGER = LoggerFactory.getLogger(InsertDefaultOAuthClientDetails.class);

	private static final String SECRET = "secret";

	@Override
	public String getConfirmationMessage() {
		return "Default clients created";
	}

	@Override
	public void setUp() throws SetupException {
		LOGGER.info("Not implemented");
	}

	@Override
	public void setFileOpener(ResourceAccessor resourceAccessor) {
		LOGGER.info("Not implemented");
	}

	@Override
	public ValidationErrors validate(Database database) {
		return new ValidationErrors();
	}

	@Override
	public SqlStatement[] generateStatements(Database database) throws CustomChangeException {
		return new SqlStatement[] { this.createClient("service-a", "service-a", "8082"),
				this.createClient("service-b", "service-b", "8183"), this.createClient("form-app", "form-app", "8080"),
				this.createClient("form-service", "form-service", "8081"),
				this.createClient("service-integration", null, "8091"),
				this.createClient("social-service", "social-service", "8090"),
				this.createClient("workspace-service", "workspace-service", "8083"),
				this.createClient("lecom-admin", "admin", "443"), this.createClient("lecom-bpm", "bpm", "443"),
				this.createClient("audit-service", "audit-service", "8082"),
				this.createClient("lecom-core", "core", "443"), this.createClient("lecom-ecm", "ecmcore", "443"),
				this.createClient("lecom-sso", "sso", "443"),
				this.createClient("mailqueue-service", "mailqueue-service", "8086") };
	}

	private RawSqlStatement createClient(final String clientId, final String clientContext, final String clientPort) {

		final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance().host("localhost")
				.scheme("https");
		if (clientPort != null) {
			uriComponentsBuilder.port(clientPort);
		}
		if (clientContext != null) {
			uriComponentsBuilder.path(clientContext);
		}
		final String redirectUri = uriComponentsBuilder.path("/login/oauth2/code/").build().toUriString();
		final StringBuilder insert = new StringBuilder(
				"INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity, additional_information, autoapprove, web_server_redirect_uri, environment_id, authorities)");
		insert.append(" VALUES (");
		insert.append("'").append("localhost-").append(clientId).append("'");
		insert.append(", '").append(SecurityConfig.delegatingPasswordEncoder().encode(SECRET)).append("'");
		insert.append(", 'read'");
		insert.append(", 'authorization_code,password,refresh_token,implicit,client_credentials'");
		insert.append(", '900'");
		insert.append(", '{}'");
		insert.append(", 'true'");
		insert.append(", '").append(redirectUri).append("'");
		insert.append(", 1");
		insert.append(", 'ROLE_APPLICATION'");
		insert.append(")");

		return new RawSqlStatement(insert.toString());

	}
}
