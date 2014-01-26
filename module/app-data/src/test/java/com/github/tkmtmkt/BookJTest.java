package com.github.tkmtmkt;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>BookJTest</code> contains tests for the class <code>{@link BookJ}</code>.
 *
 * @generatedBy CodePro at 13/09/15 21:25
 * @author takamatu
 * @version $Revision: 1.0 $
 */
public class BookJTest {
	/**
	 * Run the String getAuthor() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 13/09/15 21:25
	 */
	@Test
	public void testGetAuthor_1()
		throws Exception {
		BookJ fixture = new BookJ();
		fixture.setAuthor("");
		fixture.setTitle("");

		String result = fixture.getAuthor();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getTitle() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 13/09/15 21:25
	 */
	@Test
	public void testGetTitle_1()
		throws Exception {
		BookJ fixture = new BookJ();
		fixture.setAuthor("");
		fixture.setTitle("");

		String result = fixture.getTitle();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the void setAuthor(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 13/09/15 21:25
	 */
	@Test
	public void testSetAuthor_1()
		throws Exception {
		BookJ fixture = new BookJ();
		fixture.setAuthor("");
		fixture.setTitle("");
		String author = "";

		fixture.setAuthor(author);

		// add additional test code here
	}

	/**
	 * Run the void setTitle(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 13/09/15 21:25
	 */
	@Test
	public void testSetTitle_1()
		throws Exception {
		BookJ fixture = new BookJ();
		fixture.setAuthor("");
		fixture.setTitle("");
		String title = "";

		fixture.setTitle(title);

		// add additional test code here
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 13/09/15 21:25
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 13/09/15 21:25
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 13/09/15 21:25
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(BookJTest.class);
	}
}