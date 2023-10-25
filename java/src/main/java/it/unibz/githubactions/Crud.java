package it.unibz.githubactions;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.function.Consumer;

/**
 * Basic Create Read Update Delete operations
 * @author paolo.proni@student.unibz.it 
 */
public class Crud {
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
	
	private int executeQuery(String query, PreparedStatementFunction dbParam, DBFunction<PreparedStatement> dbFunct) throws SQLException {
		int result = 0;
		
		PreparedStatement pstm = null;
		try {
			pstm = con.prepareStatement(query);
			if (dbParam != null)
				dbParam.call(pstm);
			
			if (dbFunct == null)
				result = pstm.executeUpdate();
			else
				result = dbFunct.call(pstm);
		}
		finally {
			if (pstm != null)
				pstm.close();
		}
		return result;
	}
	
	private int executeModifyData(String query, PreparedStatementFunction dbParam) throws SQLException {
		return executeQuery(query, dbParam, null);
	}
	
	private String readSingleStringValue(String query, int keyValue) throws SQLException {
		StringMapperFunction mapper = new StringMapperFunction();
		executeQuery(query, pstm -> pstm.setInt(1, keyValue), new ResultSetFunction(mapper));
		return mapper.getValue();
	}
	
	private long readSingleLongValue(String query, int keyValue) throws SQLException {
		LongMapperFunction mapper = new LongMapperFunction();
		executeQuery(query, pstm -> pstm.setInt(1, keyValue), new ResultSetFunction(mapper));
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
	
	public void beginTransaction() throws SQLException {
		if (con != null)
			con.setAutoCommit(false);
	}
	
	public void commitTransaction() throws SQLException {
		if (con != null)
			con.setAutoCommit(true);
	}
	
	public void selectAll() throws SQLException {
		MapperFunction mapper = rs -> {
			System.out.printf("%d | %s | %s |  %s | %s | %s | %d | %n", 
				rs.getInt("user_id"), rs.getString("username"), rs.getString("first_name")
				, rs.getString("last_name"), rs.getString("gender"), rs.getString("pwd")
				, rs.getInt("status")
			);
		};
		
		executeQuery("SELECT * FROM user_details ORDER BY user_id", null, new ResultSetFunction(mapper));
	}
	
	public String selectUserNameById(int userId) throws SQLException {
		return readSingleStringValue("SELECT username FROM user_details WHERE user_id=?", userId);
	}
	
	public boolean exists(int userId) throws SQLException {
		return readSingleLongValue("SELECT count(*) FROM user_details WHERE user_id=?", userId) > 0;
	}
	
	private void setRecordFields(PreparedStatement pstm,
								 int userId, String userName, String firstName, String lastName, String gender, String pwd, int status	
								) throws SQLException {
		int index = 0;
		pstm.setInt(++index, userId);
		pstm.setString(++index, userName);
		pstm.setString(++index, firstName);
		pstm.setString(++index, lastName);
		pstm.setString(++index, gender);
		pstm.setString(++index, pwd);
		pstm.setInt(++index, status);
	}
	
	public void insert(final int userId, final String userName, final String firstName, 
						final String lastName, final String gender, final String pwd, 
						final int status) throws SQLException {
							
		executeModifyData("INSERT INTO user_details (user_id, username, first_name, last_name, gender, pwd, status) VALUES (?,?,?,?,?,?,?)",
						pstm -> setRecordFields(pstm, userId, userName, firstName, lastName, gender, pwd, status));
	}
	
	public void update(int existingUserId, int userId, String userName, String firstName, String lastName, String gender, String pwd, int status) throws SQLException {
		executeModifyData("UPDATE user_details SET user_id=?, username=?, first_name=?, last_name=?, gender=?, pwd=?, status=? WHERE user_id=?",
			pstm -> { 
				setRecordFields(pstm, userId, userName, firstName, lastName, gender, pwd, status);
				pstm.setInt(8, existingUserId);
			});
	}
	
	public void delete(final int userId) throws SQLException {
		executeModifyData("DELETE FROM user_details WHERE user_id = ?", pstm -> pstm.setInt(1, userId));
	}
}
