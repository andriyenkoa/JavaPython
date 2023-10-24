package it.unibz.githubactions;

import java.sql.SQLException;
import java.sql.ResultSet;

@FunctionalInterface
public interface MapperFunction  {

	void call(ResultSet rs) throws SQLException;
}