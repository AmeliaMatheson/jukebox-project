package controller_view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import model.Song;
import model.SongList;

/**
 * This class is responsible for displaying the SongList in a TableView. 
 * 
 * @author Amelia Matheson
 */

public class SongListTableView extends BorderPane {
	private static TableView<Song> table;
	private static ObservableList<Song> obslist;
	private SongList songList;
	private ArrayList<Song> songs;

	/**
	 * Creates a new SongListTableView to display the list of songs to select.
	 */
	@SuppressWarnings("unchecked")
	public SongListTableView() {
		this.setMinWidth(300);
		this.setMaxHeight(400);
		
		Label label = new Label("Song List");
		label.setFont(new Font("Elephant", 14));
		label.setStyle("-fx-text-fill: magenta");
		this.setTop(label);
		Insets insets = new Insets(3);
		this.setMargin(label, insets);

		table = new TableView<Song>();
		table.setPrefWidth(300);
		songList = new SongList();
		songs = songList.getSongs();
		obslist = FXCollections.observableArrayList(songs);

		TableColumn<Song, String> title = new TableColumn<>("Title");
		TableColumn<Song, String> artist = new TableColumn<>("Artist");
		TableColumn<Song, String> time = new TableColumn<>("Time");
		

		title.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		artist.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
		time.setCellValueFactory(new PropertyValueFactory<Song, String>("playtimeAsString"));

		table.setItems(obslist);
		table.getColumns().addAll(title, artist, time);
		title.setPrefWidth(148);
		table.setStyle("-fx-border-style: dotted; -fx-border-width: 2; -fx-border-color: darkgoldenrod");
		this.setCenter(table);
	}

	/**
	 * Retrieves the TableView used to display the songs.
	 * 
	 * @return TableView of Songs representing the current TableView being used to
	 *         display the songs.
	 */
	public TableView<Song> getTable() {
		return table;
	}
}
