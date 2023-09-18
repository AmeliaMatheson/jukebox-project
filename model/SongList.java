package model;

/**
 * Contains the list of songs that can be played by the Jukebox.
 * 
 * @author Amelia Matheson
 */

import java.util.ArrayList;
import java.util.Collections;

public class SongList {
	 private ArrayList<Song> songList;
	 private int size;
	 
	 /**
	  * Creates a new SongList with all the available songs.
	  */
	 public SongList() {
		 songList = new ArrayList<>();
		 songList.add(new Song("Pokemon Capture", "Pikachu", 5, "Capture.mp3"));
		 songList.add(new Song("Danse Macabre", "Kevin MacLeod", 34, "DanseMacabreViolinHook.mp3"));
		 songList.add(new Song("Determined Tumbao", "FreePlay Music", 20, "DeterminedTumbao.mp3"));
		 songList.add(new Song("LopingSting", "Kevin MacLeod", 5, "LopingSting.mp3"));
		 songList.add(new Song("Swing Cheese", "FreePlay Music", 15, "SwingCheese.mp3"));
		 songList.add(new Song("The Curtain Rises", "Kevin MacLeod", 28, "TheCurtainRises.mp3"));
		 songList.add(new Song("UntameableFire", "Pierre Langer", 282, "UntameableFire.mp3"));
		 size = 7;
	 }
	 
	 /**
	  * Adds a new song to the SongList.
	  * 
	  * @param newSong : Song representing the song to be added.
	  */
	 public void addSong(Song newSong) {
		 songList.add(newSong);
		 size++;
	 }
	 
	 /**
	  * Removes a song from the SongList.
	  * 
	  * @param toBeRemoved : Song representing the song to be removed.
	  */
	 public void removeSong(Song toBeRemoved) {
		 for (Song song : songList) {
			 if (song.equals(toBeRemoved)) {
				 songList.remove(song);
				 return;
			 }
		 }
	 }
	 
	 /**
	  * Retrieves the size of the song list.
	  * 
	  * @return : integer representing the size of the song list.
	  */
	 public int size() {
		 return size;
	 }
	 
	 /**
	  * Retrieves a specified song based on the index of that song.
	  * 
	  * @param i : integer representing the index of the song in the list.
	  * @return Song object at the specified index.
	  */
	 public Song get(int i) {
		 return songList.get(i);
	 }
	 
	 /**
	  * Retrieves the list of songs.
	  * 
	  * @return ArrayList of Songs representing the song list.
	  */
	 public ArrayList<Song> getSongs() {
		 return songList;
	 }
	 
	 /**
	  * Sorts the song list by title.
	  */
	 public void sortByTitle() {
		 Collections.sort(songList, Song::compareByTitle);
	 }
	 
	 /**
	  * Sorts the song list by artist.
	  */
	 public void sortByArtist() {
		 Collections.sort(songList, Song::compareByArtist);
	 }
	 
	 /**
	  * Sorts the song list by duration.
	  */
	 public void sortByDuration() {
		Collections.sort(songList, Song::compareByDuration);
	}
}