package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

/**
 * Tests for the JukeboxAccount.
 * 
 * @author Adrianna Koppes
 */

import model.JukeboxAccount;

class JukeboxAccountTest {

	/**
	 * Test the getters for the JukeboxAccount.
	 */
	@Test
	void testGetters() {
		JukeboxAccount aJBA = new JukeboxAccount("Name", "PW");
		JukeboxAccount another = new JukeboxAccount("Sample", "12345");
		assertEquals(aJBA.getUsername(), "Name");
		assertEquals(aJBA.getPassword(), "PW");
		assertEquals(aJBA.getDate(), LocalDate.now());
		assertEquals(aJBA.getSongsToday(), 0);
		
		assertEquals(another.getUsername(), "Sample");
		assertEquals(another.getPassword(), "12345");
		assertEquals(another.getDate(), LocalDate.now());
		assertEquals(another.getSongsToday(), 0);
	}
	
	/**
	 * Tests the add song function.
	 * NOTE: I implemented addSong such that if the current date and
	 * the stored date weren't the same, then the program would automatically
	 * update to the current date, as that would be the case for any run of
	 * the actual Jukebox program. This test will also test this functionality.
	 */
	@Test
	void testAddSong() {
		JukeboxAccount account = new JukeboxAccount("ABC", "123");
		assertTrue(account.addSong());
		assertEquals(account.getSongsToday(), 1);
		assertTrue(account.addSong());
		assertEquals(account.getSongsToday(), 2);
		assertTrue(account.addSong());
		assertEquals(account.getSongsToday(), 3);
		// same day, can't add any new songs.
		assertFalse(account.addSong());
		assertEquals(account.getSongsToday(), 3);
		
		// a new day has dawned, so we can add a new song with no trouble.
		// date updates automatically.
		JukeboxAccount timeTravel = new JukeboxAccount("XYZ", "456");
		timeTravel.setFakeDateAndSongs();
		assertEquals(timeTravel.getSongsToday(), 3);
		assertTrue(timeTravel.addSong());
		assertEquals(timeTravel.getSongsToday(), 1);
		assertEquals(timeTravel.getDate(), LocalDate.now());
	}
}
