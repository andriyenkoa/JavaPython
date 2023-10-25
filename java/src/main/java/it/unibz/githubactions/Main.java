package it.unibz.githubactions;

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
		try {
			crud.openConnection();
			crud.selectAll();
		}
		catch (SQLException ex) {
			ex.printStackTrace();	
		}
		finally {
			crud.closeConnection();
		}	
    }
}
