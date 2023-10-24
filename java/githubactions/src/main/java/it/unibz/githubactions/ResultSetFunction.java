package it.unibz.githubactions;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ResultSetFunction implements DBFunction<PreparedStatement> {
	
	private MapperFunction mapper;
	
	public ResultSetFunction(MapperFunction mapper) {
		this.mapper = mapper;
	}
	
	public int call(PreparedStatement pstm) throws SQLException {
		int numberOfFoundRecords = 0;
		ResultSet rs = null;
		try {
			rs = pstm.executeQuery();
			while (rs.next()) {
				numberOfFoundRecords++;
				mapper.call(rs);
			}
		}
		finally {
			if (rs != null)
				rs.close();
		}
		return numberOfFoundRecords;
	}
}