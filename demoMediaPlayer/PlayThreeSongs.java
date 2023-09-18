package demoMediaPlayer;

import controller_view.SongSelectionPlaylistPane;
/**
 * Plays three songs.
 * 
 * Adrianna Koppes, Amelia Matheson
 * 
 * @author Rick Mercer
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.PlayList;
import model.Song;

public class PlayThreeSongs extends Application {

  public static void main(String[] args) {
    launch(args);
  }
  
  @Override
  public void start(Stage stage) throws Exception {
    PlayList playList = new PlayList(new SongSelectionPlaylistPane());
    
    //playList.queueUpNextSong(new Song("", "", 5, "Capture.mp3"));
    //playList.queueUpNextSong(new Song("", "", 15, "SwingCheese.mp3"));
    //playList.queueUpNextSong(new Song("", "", 5, "Capture.mp3"));
    
    playList.play();
    
    BorderPane pane = new BorderPane();
    pane.setCenter(new Label("Play three songs"));
    // Put the pane in a sized Scene and show the GUI
    Scene scene = new Scene(pane, 255, 85);
    stage.setScene(scene);
    // Don't forget to show the running app:
    stage.show();

  }

}
