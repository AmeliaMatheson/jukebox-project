package model;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * Class representing a JukeboxAccount with the username, password, songs played
 * today, and current date.
 * 
 * @author Adrianna Koppes
 * @since March 12 2023
 */
public class JukeboxAccount implements Serializable {

	private String username;
	private String password;
	private int songsToday;
	private LocalDate today;

	/**
	 * Initializes the new account with the username, password, and today's date,
	 * and sets number of songs played today to 0.
	 * 
	 * @param username : String representing the account username.
	 * @param password : String representing the account password.
	 */
	public JukeboxAccount(String username, String password) {
		this.username = username;
		this.password = password;
		songsToday = 0;
		today = LocalDate.now();
	}

	/**
	 * Updates number of songs played today. Will not add another song if three
	 * songs have already been played today.
	 * 
	 * @return boolean representing whether the song can be added. true if it can
	 *         be, false otherwise (if a user has already added 3 songs).
	 */
	public boolean addSong() {
		if (songsToday == 3 && today.equals(LocalDate.now())) {
			return false;
		}
		if (!(today.equals(LocalDate.now()))) {
			updateDate();
			songsToday = 0;
		}
		songsToday++;
		return true;
	}

	/**
	 * Updates today's date.
	 */
	public void updateDate() {
		today = LocalDate.now();
	}

	/**
	 * Sets the date to 3 days in the past and the number of songs played to the
	 * maximum allowed.
	 * 
	 * For testing purposes only. Will never be used in the real Jukebox program.
	 */
	public void setFakeDateAndSongs() {
		today = today.minusDays(3);
		songsToday = 3;
	}

	/**
	 * Retrieves the account's username.
	 * 
	 * @return String representing the account's username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Retrieves the account's password.
	 * 
	 * @return String representing the account's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Retrieves the number of songs played today.
	 * 
	 * @return integer representing number of songs played today.
	 */
	public int getSongsToday() {
		return songsToday;
	}

	/**
	 * Retrieves the current date stored by the account.
	 * 
	 * @return LocalDate object representing today's date.
	 */
	public LocalDate getDate() {
		return today;
	}
}