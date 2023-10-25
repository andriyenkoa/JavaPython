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
	void testSelectUserName() throws Exception {
		assertEquals("rogers63", crud.selectUserNameById(1));
	}
	
	@Test
	void testInsert() throws Exception {
		final String name = "Paolo75";
		final int id = 200;
		crud.beginTransaction();
		crud.insert(id, name, "Paolo", "Proni", "Male", "mypassword", 1);
		assertEquals(name, crud.selectUserNameById(id));
		crud.delete(id);
		assertNull(crud.selectUserNameById(id));
		crud.commitTransaction();
	}
	
	@Test
	void testUpdate() throws Exception {
		crud.beginTransaction();
		final int id = 200;
		final String oldName = "Paolo75";
		final String newName = "Paolo1975";
		crud.insert(id, oldName, "Paolo", "Proni", "Male", "mypassword", 1);
		crud.update(id, id, newName, "Paolo", "Proni", "Male", "mypassword", 1);
		assertEquals(newName, crud.selectUserNameById(id));
		crud.update(id, id, oldName, "Paolo", "Proni", "Male", "mypassword", 1);
		crud.delete(id);
		crud.commitTransaction();
	}
	
	@AfterAll
	static void clean() throws Exception {
		crud.closeConnection();
	}
}
