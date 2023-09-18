package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class representing a Song, with a title, artist, duration, and filename to
 * access the song file.
 * 
 * @author Amelia Matheson
 */

public class Song implements Comparable<Song>, Serializable {
	private String title;
	private String artist;
	private String fileName;
	private int duration;
	private String playtimeAsString;

	/**
	 * Creates a new Song with a specified filename.
	 * 
	 * @param name : String representing the filename to access the song.
	 */
	public Song(String name) {
		title = name;
		artist = "";
		duration = 20; // default value
		fileName = name;
	}

	/**
	 * Creates a new Song with a specified title, artist, duration, and filename.
	 * 
	 * @param name     : String representing song title.
	 * @param artist   : String representing song artist.
	 * @param playtime : integer representing song duration in seconds.
	 * @param fileName : String representing the filename to access the song.
	 */
	public Song(String name, String artist, int playtime, String fileName) {
		title = name;
		this.artist = artist;
		duration = playtime;
		this.fileName = fileName;
	}

	/**
	 * Compares two Songs by title.
	 * 
	 * @param other : Song object to be compared to.
	 * @return integer representing value of the current Song compared to the other.
	 *         If the integer is 0 then they are equal. See String.compareTo().
	 */
	public int compareByTitle(Song other) {
		return this.title.compareTo(other.title);
	}

	/**
	 * Compares two Songs by artist.
	 * 
	 * @param other : Song object to be compared to.
	 * @return integer representing the value of the current Song compared to the
	 *         other. If the integer is 0 then they are equal. See
	 *         String.compareTo().
	 */
	public int compareByArtist(Song other) {
		return this.artist.compareTo(other.artist);
	}

	/**
	 * Compares two Songs by duration.
	 * 
	 * @param other : Song object to be compared to.
	 * @return integer representing the value of the current Song compared to the
	 *         other. If the integer is negative then the current Song is shorter.
	 *         If the integer is positive then the other Song is shorter. If the
	 *         integer is 0, then they have equal durations.
	 */
	public int compareByDuration(Song other) {
		if (this.duration == other.duration) {
			return 0;
		} else if (this.duration < other.duration) {
			return -1;
		}
		return 1;
	}

	/**
	 * Retrieves the title of the song.
	 * 
	 * @return String representing the title of the song.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Retrieves the artist of the song.
	 * 
	 * @return String representing the artist of the song.
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Retrieves the duration of the song, in milliseconds.
	 * 
	 * @return long representing the duration of the song in milliseconds.
	 */
	public long getDurationMilliseconds() {
		return duration * 1000;
	}

	/**
	 * Returns the duration of the song, as a String. Format: 00:00, with the digits
	 * on the left side of the colon representing minutes and the digits on the
	 * right side of the colon representing seconds.
	 * 
	 * @return String representing the duration of the song.
	 */
	public String getPlaytimeAsString() {
		int minutes = duration / 60;
		int seconds = duration % 60;

		playtimeAsString = minutes + ":" + seconds;
		if (seconds < 10) {
			playtimeAsString = minutes + ":0" + seconds;
		}
		return playtimeAsString;
	}

	/**
	 * Returns the filename that the song is stored at.
	 * 
	 * Adrianna Koppes added this method because she thought it was necessary for
	 * the PlayList class.
	 * 
	 * @return String representing the song's filename.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Compares two songs.
	 * 
	 * @param other : Song object to be compared to.
	 * @return integer representing the value of the current song compared to the
	 *         other song. Compares the songs by title.
	 */
	@Override
	public int compareTo(Song other) {
		return compareByTitle(other);
	}

	/**
	 * Creates a String representation of the song.
	 * 
	 * @return String representing the current Song object as a String.
	 */
	@Override
	public String toString() {
		return artist + " - \"" + title + "\" (" + getPlaytimeAsString() + ")";
	}

	/**
	 * Checks to see if the two Objects are equal.
	 * 
	 * @param obj : Object to be compared to.
	 * @return boolean representing whether the two Objects are equal or not. True
	 *         if they are, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Song other = (Song) obj;
		return Objects.equals(artist, other.artist) && duration == other.duration
				&& Objects.equals(fileName, other.fileName) && Objects.equals(title, other.title);
	}
}
