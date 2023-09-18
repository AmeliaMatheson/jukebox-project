package controller_view;

/**
 * The main GUI that runs the Jukebox program. Also extracts some useful
 * information and ties the entire program together.
 * 
 * @author Amelia Matheson, with some contributions from Adrianna Koppes
 * @since March 19, 2023
 */

import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.geometry.Insets;

public class JukeboxGUI extends Application {

	/**
	 * Begins the run of the program.
	 * 
	 * @param args : String array representing the command line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	private LoginCreateAccountPane loginPane;
	private SongSelectionPlaylistPane songsPane;
	private BorderPane everything;

	/**
	 * Sets up the graphical program for run.
	 * 
	 * @param primaryStage : Stage object representing the window of the program.
	 * @throws Exception, if the program encounters some unexpected error (should
	 *                    not occur).
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		LayoutGUI();
		everything.setStyle("-fx-background-color: mintcream");
		Scene scene = new Scene(everything, 800, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Student Affair Office Jukebox");
		primaryStage.show();

		primaryStage.setOnCloseRequest((event) -> {
			shutDownHandle();
			loginPane.writeAccountsList();
			Platform.exit();
			System.exit(0);
		});
	}

	/**
	 * Lays out the GUI elements, also starts up the program by asking if the
	 * playlist should be restored from a previous version.
	 */
	private void LayoutGUI() {
		songsPane = new SongSelectionPlaylistPane();
		ButtonType makeNew = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
		ButtonType read = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
		Alert readOld = new Alert(AlertType.CONFIRMATION);
		readOld.setTitle("Startup Option");
		readOld.setHeaderText("Read saved data?");
		readOld.setContentText("Press cancel while system testing");
		readOld.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		readOld.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
		ButtonBar buttons = (ButtonBar) readOld.getDialogPane().lookup(".button-bar");
		buttons.setButtonOrder(ButtonBar.BUTTON_ORDER_LINUX);
		readOld.getButtonTypes().setAll(makeNew, read);
		Button readerButton = (Button) readOld.getDialogPane().lookupButton(read);
		readerButton.setDefaultButton(true);
		Optional<ButtonType> result = readOld.showAndWait();
		ButtonBar.ButtonData resultData = result.get().getButtonData();
		if (resultData == ButtonBar.ButtonData.CANCEL_CLOSE) {
			// default: read the serialized playlist
			songsPane.readPlayList();
			readOld.close();
		} else if (resultData == ButtonBar.ButtonData.NO) {
			// start from scratch
			readOld.close();
		}

		everything = new BorderPane();
		everything.setPadding(new Insets(10));

		loginPane = new LoginCreateAccountPane(songsPane);
		everything.setCenter(songsPane); // was songsPane
		everything.setBottom(loginPane);
	}

	/**
	 * Handles when the application is shut down. Saves the playlist if necessary.
	 */
	private void shutDownHandle() {
		ButtonType noSave = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
		ButtonType write = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
		Alert closeAlert = new Alert(AlertType.CONFIRMATION);
		closeAlert.setTitle("Shutdown Option");
		closeAlert.setHeaderText("Save data?");
		closeAlert.setContentText("Press cancel while system testing");
		closeAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		closeAlert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
		ButtonBar buttons = (ButtonBar) closeAlert.getDialogPane().lookup(".button-bar");
		buttons.setButtonOrder(ButtonBar.BUTTON_ORDER_LINUX);
		closeAlert.getButtonTypes().setAll(noSave, write);
		Button writer = (Button) closeAlert.getDialogPane().lookupButton(write);
		writer.setDefaultButton(true);
		Optional<ButtonType> result = closeAlert.showAndWait();
		ButtonBar.ButtonData resultData = result.get().getButtonData();
		if (resultData == ButtonBar.ButtonData.CANCEL_CLOSE) {
			// save Playlist; serialization -> default
			songsPane.writePlayList();
			closeAlert.close();
		} else if (resultData == ButtonBar.ButtonData.NO) {
			// don't do anything and close
			closeAlert.close();
		}
	}
}