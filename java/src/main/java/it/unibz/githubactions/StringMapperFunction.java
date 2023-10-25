package it.unibz.githubactions;

import java.sql.SQLException;
import java.sql.ResultSet;

public class StringMapperFunction implements MapperFunction {
	String fieldName;
	String value = null;
	
	public StringMapperFunction(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public void call(ResultSet rs) throws SQLException {
		value = rs.getString(fieldName);
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}