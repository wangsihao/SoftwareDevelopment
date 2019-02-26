/**
 * 
 */
package edu.gatech.seclass.utilities;

import static org.junit.Assert.*;

import java.util.List;


import org.junit.BeforeClass;
import org.junit.Test;

import edu.gatech.seclass.utilities.ExternalWebService.PlayerRating;

/**
 * @author Erin
 *
 */
public class ExternalWebServiceTest {
	static ExternalWebService ex;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ex = ExternalWebService.getInstance();
	}

	
	/**
	 * successful add
	 */
	@Test
	public void testCryptogramService() {
		List<String[]> crypt = ex.syncCryptogramService();
		assertEquals(crypt.size(), 4);
		assertEquals(crypt.get(0)[0], "1");
		assertEquals(crypt.get(0)[1], "Eyhfx, ugq tsga ykkykkfsydfgsk ywx knxpfcfpyzzu nwgrfvfdxl vu xhxpqdfjx gwlxw sqevxw 12333.");
		assertEquals(crypt.get(0)[2], "Maxie, you know assassinations are specifically prohibited by executive order number 12333.");

		String newUUID = ex.addCryptogramService("cat", "xyz");
		assertNotNull(newUUID);
		crypt = ex.syncCryptogramService();
		assertEquals(crypt.size(), 5);
		assertEquals(crypt.get(4)[0], "5");
		assertEquals(crypt.get(4)[1], "cat");
		assertEquals(crypt.get(4)[2], "xyz");
	}
	
	/**
	 * failed add - inconsistent puzzle letters
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCryptogramServiceFailure1() {
		ex.addCryptogramService("bat", "vdv");
	}
	
	/**
	 * failed add - inconsistent sizes
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCryptogramServiceFailure2() {
		ex.addCryptogramService("bat", "xyzl");
	}
	
	/**
	 * failed add - inconsistent solution letters
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCryptogramServiceFailure3() {
		ex.addCryptogramService("abcba", "xyztr");
	}
	
	/**
	 * failed add - inconsistent punctuation
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCryptogramServiceFailure4() {
		ex.addCryptogramService("abc.", "xyz!");
	}
	
	/**
	 * failed add - inconsistent capitalization
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCryptogramServiceFailure5() {
		ex.addCryptogramService("ABC", "xyz");
	}

	
	/**
	 * successful update
	 */
	@Test
	public void testRatingService() {
		List<PlayerRating> pr = ex.syncRatingService();
		assertEquals(pr.size(), 4);
		assertTrue(pr.contains(ex.new PlayerRating("George", "Burdell", 3, 4, 2)));
		assertTrue(ex.updateRatingService("example557", "George", "Burdell", 4, 4, 7));
		pr = ex.syncRatingService();
		assertFalse(pr.contains(ex.new PlayerRating("George", "Burdell", 3, 4, 2)));
		assertTrue(pr.contains(ex.new PlayerRating("George", "Burdell", 4, 4, 7)));
	}
	
	/**
	 * successful add
	 */
	@Test
	public void testRatingService2() {
		List<PlayerRating> pr = ex.syncRatingService();
		assertEquals(pr.size(), 4);
		assertTrue(ex.updateRatingService("example559", "Jane", "Doe", 4, 4, 1));
		pr = ex.syncRatingService();
		assertEquals(pr.size(), 5);
		assertTrue(pr.contains(ex.new PlayerRating("Jane", "Doe", 4, 4, 1)));
		List<String> names = ex.playernameService();
		assertEquals(names.size(), 5);
		assertTrue(names.contains("example557"));
		assertTrue(names.contains("example559"));
		assertTrue(names.contains("example555"));
		
	}
	
	/**
	 * failed add
	 */
	@Test
	public void testRatingServiceFailure() {
		List<PlayerRating> pr = ex.syncRatingService();
		int size_initial = pr.size();
		assertFalse(ex.updateRatingService("", "Jane", "Doe", 4, 4, 1));
		pr = ex.syncRatingService();
		assertEquals(pr.size(), size_initial);
	}

	

}
