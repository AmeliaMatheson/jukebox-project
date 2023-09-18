package controller_view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.JukeboxAccount;
import model.PlayList;
import model.Song;

/**
 * The pane that will contain components necessary for displaying SongList
 * and PlayList. Currently has three buttons to play three hard-coded songs.
 * 
 * Please disregard commented out lines; implementation subject to change
 * in iteration 2 of this project. 
 * 
 * @author Amelia Matheson, with some things done by Adrianna Koppes
 * @since March 19, 2023
 */

public class SongSelectionPlaylistPane extends GridPane {

	private BorderPane songQueuePane;
	private Button addButton = new Button("Add and Play");
	private PlayList play;
	private ObservableList<Song> playlistTracker; // necessary because PlayList
	// only plays songs (not displays) as per spec
	private ListView<Song> songList;
	private JukeboxAccount currAcc;
	private SongListTableView songsTableView;
	private TableView<Song> theTable;

	/**
	 * Sets up a SongSelectionPlaylistPane containing all the user needs to select
	 * songs and see the songs that are playing.
	 */
	public SongSelectionPlaylistPane() {
		play = new PlayList(this);
		this.setHgap(10);
		this.setVgap(5);
		this.setPadding(new Insets(0, 0, 0, 20));
		songQueuePane = new BorderPane();
		songQueuePane.setPrefWidth(300);
		Label queueLabel = new Label("Song Queue");
		queueLabel.setFont(new Font("Elephant", 14));
		queueLabel.setStyle("-fx-text-fill: magenta");
		songQueuePane.setTop(queueLabel);
		Insets insets = new Insets(0, 0, 3, 0);
		songQueuePane.setMargin(queueLabel, insets);

		// start with empty playlist; if an old playlist is to be loaded, use the
		// readPlayList method to load it
		playlistTracker = FXCollections.observableArrayList(new ArrayList<Song>());
		songList = new ListView<>();
		songList.setItems(playlistTracker);
		songList.setStyle("-fx-border-style: dotted; -fx-border-width: 2; -fx-border-color: darkgoldenrod");
		songQueuePane.setCenter(songList);

		songsTableView = new SongListTableView();
		this.add(songsTableView, 1, 1);
		this.add(songQueuePane, 3, 1);
		this.add(addButton, 2, 1);

		theTable = songsTableView.getTable();
		addButton.setOnAction((event) -> {
			Song song = (Song) theTable.getSelectionModel().getSelectedItem();
			if (song != null) {
				addSong(song);
			}
		});

	}

	/**
	 * Removes the song that was just played from the playlist ListView.
	 */
	public void songOver() {
		if (!(playlistTracker.isEmpty())) {
			playlistTracker.remove(0);
		}
	}

	/**
	 * Selects the playing song in the list view.
	 */
	public void showPlayingSong() {
		songList.getSelectionModel().clearSelection();
		songList.getSelectionModel().select(0);
	}

	/**
	 * Updates the current account to reflect the currently logged in user.
	 * 
	 * @param account : JukeboxAccount representing the user currently logged in.
	 */
	public void update(JukeboxAccount account) {
		currAcc = account;

	}

	/**
	 * Reads the PlayList from the serialized file to restore it to a saved version.
	 */
	public void readPlayList() {
		FileInputStream fromFile;
		try {
			fromFile = new FileInputStream("playlist.ser");
			ObjectInputStream inFile = new ObjectInputStream(fromFile);

			LinkedBlockingQueue<Song> oldPlay = (LinkedBlockingQueue<Song>) inFile.readObject();
			play = new PlayList(this, oldPlay);
			playlistTracker = FXCollections.observableArrayList(play.getSongsAsList());
			songList.setItems(playlistTracker);
			play.play();

			inFile.close();
		} catch (FileNotFoundException err) {
			System.out.println("Input file not found");
		} catch (IOException err) {
			System.out.println("Couldn't read from file");
		} catch (ClassNotFoundException err) {
			System.out.println("Incorrect cast");
		}
	}

	/**
	 * Writes the current PlayList to a serialized file so it can be saved.
	 */
	public void writePlayList() {
		play.writeSongQueue();
	}

	/**
	 * Adds the specified song to the playlist. Needs to manually get the song to
	 * play if the playlist is empty.
	 * 
	 * @param song : Song object representing the song to be played.
	 * @return boolean representing whether the song was successfully added or not.
	 *         true if it was, false otherwise (this would happen if the user has
	 *         already added three songs for the day or if a user isn't logged in).
	 */
	private boolean addSong(Song song) {
		if (currAcc == null) {
			Alert noLogin = new Alert(AlertType.WARNING);
			noLogin.setHeaderText("No user logged in");
			noLogin.setContentText("Please log in or create an account to add songs.");
			noLogin.show();
			return false;
		}
		boolean added = currAcc.addSong();
		if (!(added)) {
			Alert threeAdded = new Alert(AlertType.WARNING);
			threeAdded.setHeaderText("Sorry, you already added three songs today.");
			threeAdded.setContentText("Please come back tomorrow.");
			threeAdded.show();
			return false;
		}
		playlistTracker.add(song);
		if (play.playListEmpty()) {
			play.queueUpNextSong(song);
			play.play();
			showPlayingSong();
		} else {
			play.queueUpNextSong(song);
		}
		return true;
	}
}
