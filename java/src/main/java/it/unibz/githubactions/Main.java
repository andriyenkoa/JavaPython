package it.unibz.githubactions;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Entry Point Class
 *
 */
public class Main  
{
    public static void main( String[] args )
    {
		Crud crud = new Crud();
		Connection con = null;
		try {
			con = crud.openConnection();
			crud.selectAll(con);
		}
		catch (SQLException ex) {
			ex.printStackTrace();	
		}
		finally {
			crud.closeConnection(con);
		}	
    }
}
