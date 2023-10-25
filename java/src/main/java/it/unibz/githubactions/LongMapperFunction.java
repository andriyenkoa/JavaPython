package it.unibz.githubactions;

import java.sql.SQLException;
import java.sql.ResultSet;

public class LongMapperFunction implements MapperFunction {
	final String fieldName;
	Long value = null;
	
	public LongMapperFunction(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public LongMapperFunction() {
		this.fieldName = "";
	}
	
	public void call(ResultSet rs) throws SQLException {
		if (fieldName.isEmpty())
			value = rs.getLong(1);
		else
			value = rs.getLong(fieldName);
	}
	
	public void setValue(Long value) {
		this.value = value;
	}
	
	public Long getValue() {
		return this.value;
	}
}