package br.com.lecom.oauth.db.changelog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.lecom.oauth.config.security.SecurityConfig;
import liquibase.change.custom.CustomSqlChange;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawSqlStatement;

public class InsertDefaultUser implements CustomSqlChange {
	private static final Logger LOGGER = LoggerFactory.getLogger(InsertDefaultUser.class);

	@Override
	public String getConfirmationMessage() {
		return "Default users created";
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
		return null;
	}

	@Override
	public SqlStatement[] generateStatements(Database database) throws CustomChangeException {

		return new SqlStatement[] { this.createInsertUserStatement("user") };
	}

	private RawSqlStatement createInsertUserStatement(final String username) {
		final StringBuilder insert = new StringBuilder(
				"INSERT INTO user (username, email, password, activated, authorities, environment_id)");
		insert.append(" VALUES (");
		insert.append("'").append(username).append("'");
		insert.append(", '").append(username).append("@lecom.com.br'");
		insert.append(", '").append(SecurityConfig.delegatingPasswordEncoder().encode("secret")).append("'");
		insert.append(", true");
		insert.append(", 'ROLE_USER,ROLE_ADMIN'");
		insert.append(", 1");
		insert.append(")");
		return new RawSqlStatement(insert.toString());
	}
}
