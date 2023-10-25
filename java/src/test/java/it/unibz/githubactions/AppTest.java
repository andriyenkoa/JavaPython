package it.unibz.githubactions;

import java.sql.Connection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
	static Crud crud = null;
	
	@BeforeAll
	static void setup() throws Exception {
		crud = new Crud();
		crud.openConnection();
	}
	
	@Test
	@Disabled
	void showAll() throws Exception {
		crud.selectAll();
		assertTrue(true);
	}
	
	@Test
	void testUserName() throws Exception {
		assertEquals("rogers63", crud.selectUserNameById(1));
	}
	
	@AfterAll
	static void clean() throws Exception {
		crud.closeConnection();
	}
}
