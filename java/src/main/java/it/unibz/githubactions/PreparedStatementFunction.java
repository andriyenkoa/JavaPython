package it.unibz.githubactions;

import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * Function that handles database communications
 */
@FunctionalInterface
public interface PreparedStatementFunction 
{
	void call(PreparedStatement model) throws SQLException;
}
