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

public class InsertBehaviorServiceOAuthClientDetails implements CustomSqlChange {
	private static final Logger LOGGER = LoggerFactory.getLogger(InsertBehaviorServiceOAuthClientDetails.class);

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
		return new SqlStatement[] { this.createClient("behavior-service", "behavior-service", "8082") };
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
