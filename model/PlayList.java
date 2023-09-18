package model;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import controller_view.SongSelectionPlaylistPane;

import java.io.Serializable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * PlayList accepts songs to add to a queue to play. It also plays songs in a
 * separate thread in the background with a 2-second delay.
 * 
 * @author Adrianna Koppes
 * @since March 12 2023
 */

public class PlayList implements Serializable {

	private LinkedBlockingQueue<Song> songs;
	private SongSelectionPlaylistPane display;
	private Song curr;
	private Media media;
	private static MediaPlayer mediaPlayer;

	/**
	 * Sets up a new PlayList with the specified observer.
	 * 
	 * @param songPane : SongSelectionPlaylistPane object observing the playlist.
	 */
	public PlayList(SongSelectionPlaylistPane songPane) {
		display = songPane;
		songs = new LinkedBlockingQueue<>();
		curr = null;
		media = null;
		mediaPlayer = null;
	}

	/**
	 * Sets up a new PlayList with the specified observer and song queue.
	 * 
	 * @param songPane : SongSelectionPlaylistPane object observing the playlist.
	 * @param prev     : LinkedBlockingQueue of Songs representing an old playlist
	 *                 that is being restored.
	 */
	public PlayList(SongSelectionPlaylistPane songPane, LinkedBlockingQueue<Song> prev) {
		display = songPane;
		songs = prev;
		curr = null;
		media = null;
		mediaPlayer = null;
	}

	/**
	 * Adds a song to the queue.
	 * 
	 * @param songToAdd : Song object to be added to the queue.
	 */
	public void queueUpNextSong(Song songToAdd) {
		songs.add(songToAdd);
	}

	/**
	 * Starts the thread that plays the songs.
	 * 
	 * Thanks to TA Aditya Jadhav for helping with this.
	 */
	public void play() {
		if (songs.isEmpty()) {
			return;
		}
		curr = songs.peek();
		String path = "songfiles/" + curr.getFileName();
		File file = new File(path);
		URI uri = file.toURI();
		media = new Media(uri.toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		display.showPlayingSong();
		mediaPlayer.setOnEndOfMedia(new Waiter());
	}

	/**
	 * Checks if the playlist is empty. If it is play() must be called manually.
	 * 
	 * @return boolean representing if the playlist is empty; returns true if it is
	 *         empty, false otherwise.
	 */
	public boolean playListEmpty() {
		return songs.isEmpty();
	}

	public void writeSongQueue() {
		FileOutputStream bytesToDisk;
		try {
			bytesToDisk = new FileOutputStream("playlist.ser");
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);

			outFile.writeObject(songs);

			outFile.close();
		} catch (FileNotFoundException err) {
			System.out.println("Output file not found");
		} catch (IOException err) {
			err.printStackTrace();
		}
	}

	/**
	 * Gives the entire song queue as an ArrayList. Necessary for persistence.
	 * 
	 * @return ArrayList of Songs representing the current song queue.
	 */
	public ArrayList<Song> getSongsAsList() {
		ArrayList<Song> songsList = new ArrayList<>();
		Song[] tempSongsList = songs.toArray(new Song[2]);
		for (int i = 0; i < tempSongsList.length; i++) {
			songsList.add(tempSongsList[i]);
		}
		return songsList;
	}

	/**
	 * Waiter is a class that dictates the actions of what should be done after a
	 * song has finished playing.
	 * 
	 * @author Adrianna Koppes
	 */
	private class Waiter implements Runnable {

		/**
		 * Runs the necessary actions for what should be done after a song finishes
		 * playing. First pauses for two seconds, and then plays the next song.
		 */
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			songs.poll();
			display.songOver();
			play();
		}
	}
}