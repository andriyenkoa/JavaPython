package it.unibz.githubactions;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Basic Create Read Update Delete operations
 */
public class Crud 
{
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost:5432/mydatabase";
	private static final String USER = "myuser";
	private static final String PASSWORD = "mypassword";
	
	private Connection con = null;
	
	static {
		try {
		  Class.forName(DRIVER);
		} catch (Exception e) {
		    System.out.println("Driver failed to register.");
		    System.out.println(e.getMessage());
		    System.exit(1);
		}
	}
	
	private int executeQuery(String query, DBFunction<PreparedStatement> dbFunct) throws SQLException {
		int result = 0;
		
		PreparedStatement pstm = null;
		try {
			pstm = con.prepareStatement(query);
			result = dbFunct.call(pstm);
		}
		finally {
			if (pstm != null)
				pstm.close();
		}
		return result;
	}
	
	private String readSingleStringValue(String query) throws SQLException {
		StringMapperFunction mapper = new StringMapperFunction();
		executeQuery(query, new ResultSetFunction(mapper));
		return mapper.getValue();
	}
	
	public void openConnection() throws SQLException {
		System.out.println("Connecting ....");
		this.con = DriverManager.getConnection(URL, USER, PASSWORD);
		System.out.println("Connected to the database");
	}
	
	public void closeConnection() {
		if (con != null) {
			try {
				con.close();
				System.out.println("Connection is closed now");
			}
			catch (SQLException ex) {
				System.err.println("An error occurred closing the database connection. Probably the Database is down");
			}
		}
		else
			System.err.println("Cannot close a null connection");
	}
	
	public void selectAll() throws SQLException {
		MapperFunction mapper = rs -> {
			
			System.out.println(rs.getInt("user_id")
				+ " | " + rs.getString("username")
				+ " | " + rs.getString("first_name")
				+ " | " + rs.getString("last_name")
				+ " | " + rs.getString("gender")	
				+ " | " + rs.getString("pwd")
				+ " | " + rs.getInt("status")
			);
				
				/*
			System.out.printf("%d | %s | %s | %s | %s | %s | %d %n", 
					rs.getInt("user_id"), rs.getString("username"), rs.getString("first_name")
					, rs.getString("last_name"), rs.getString("gender"), rs.getString("pwd"),
					, rs.getInt("status"));
			*/
		};
		
		executeQuery("SELECT * FROM user_details ORDER BY user_id", new ResultSetFunction(mapper));
	}
	
	public String selectUserNameById(int userId) throws SQLException {
		return readSingleStringValue("SELECT username FROM user_details WHERE user_id=" + userId);
	}
}
