package it.unibz.githubactions;

import java.sql.SQLException;

/**
 * Function that handles database communications
 */
@FunctionalInterface
public interface DBFunction<T> 
{
	int call(T model) throws SQLException;
}
