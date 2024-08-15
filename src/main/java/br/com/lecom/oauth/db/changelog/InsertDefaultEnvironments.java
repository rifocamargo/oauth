package br.com.lecom.oauth.db.changelog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import liquibase.change.custom.CustomSqlChange;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawSqlStatement;

public class InsertDefaultEnvironments implements CustomSqlChange {
	private static final Logger LOGGER = LoggerFactory.getLogger(InsertDefaultEnvironments.class);

	@Override
	public String getConfirmationMessage() {
		return "Environments crated";
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

		return new SqlStatement[] { this.createInsertClient("localhost")};
	}

	private RawSqlStatement createInsertClient(final String hostname) {
		final StringBuilder insert = new StringBuilder("INSERT INTO environments (hostname)");
		insert.append(" VALUES (");
		insert.append("'").append(hostname).append("'");
		insert.append(")");
		return new RawSqlStatement(insert.toString());
	}
}
