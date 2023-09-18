package controller_view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.JukeboxAccount;

/**
 * This pane contains components necessary for successfully logging in 
 * users, creating new accounts, and logging users out. 
 * 
 * @author Amelia Matheson, with some things done by Adrianna Koppes
 * 
 */

public class LoginCreateAccountPane extends BorderPane {

	private GridPane loggingInPane = new GridPane();
	private GridPane loggingOutPane = new GridPane();
	private HBox box1 = new HBox();
	private HBox box2 = new HBox();

	private ArrayList<JukeboxAccount> accounts;
	private JukeboxAccount loggedIn;
	private SongSelectionPlaylistPane songPane;

	private Label label = new Label("Login or Create Account");
	private Label username = new Label("Username: ");
	private Label password = new Label("Password: ");
	private TextField userField = new TextField();
	private PasswordField passField = new PasswordField();
	private Button loginButton = new Button("Login");
	private Button createAccButton = new Button("Create Account");
	private Label message = new Label();
	private Button logoutButton = new Button("Logout");
	private Label welcome;

	/**
	 * Lays out the GUI objects for the pane and also initializes all the necessary
	 * variables.
	 */
	public LoginCreateAccountPane(SongSelectionPlaylistPane songPane) {
		this.songPane = songPane;
		accounts = new ArrayList<>();
		readAccountsList();
		loggedIn = null;

		welcome = new Label();
		welcome.setFont(new Font("Elephant", 14));
		welcome.setStyle("-fx-text-fill: magenta");
		loggingOutPane.add(welcome, 0, 0);
		loggingOutPane.add(logoutButton, 0, 1);
		loggingOutPane.setPadding(new Insets(15, 0, 22, 0));

		label.setFont(new Font("Elephant", 14));
		label.setStyle("-fx-text-fill: magenta;");
		username.setPrefWidth(75);
		username.setFont(new Font("Verdana", 12));
		username.setStyle("-fx-text-fill: seagreen");
		
		password.setPrefWidth(75);
		password.setFont(new Font("Verdana", 12));
		password.setStyle("-fx-text-fill: seagreen");
		loginButton.setPrefWidth(100);
		createAccButton.setPrefWidth(100);
		message.setPrefWidth(400);
		message.setStyle("-fx-text-fill: darkgoldenrod");

		box1.getChildren().addAll(username, userField, loginButton);
		box2.getChildren().addAll(password, passField, createAccButton);
		box1.setSpacing(5);
		box2.setSpacing(5);

		loggingInPane.setAlignment(Pos.CENTER);
		loggingInPane.add(label, 0, 0);
		loggingInPane.setVgap(5);
		loggingInPane.add(box1, 0, 1);
		loggingInPane.add(box2, 0, 2);
		loggingInPane.add(message, 0, 3);
		loggingInPane.setPadding(new Insets(10, 0, 0, 0));

		this.setCenter(loggingInPane);
		setUpButtons();
	}

	/**
	 * Sets action for each button.
	 */
	private void setUpButtons() {
		loginButton.setOnAction((event) -> {
			int code = logUserIn(userField.getText(), passField.getText()); // send it username and password

			if (code == 1) {
				userField.clear();
				passField.clear();
				message.setText("Username/Password is incorrect. Create account if you are a new user");
				code = logUserIn(userField.getText(), passField.getText());
			}

			else {
				welcome.setText("Welcome " + userField.getText() + "!");
				userField.clear();
				passField.clear();
				message.setText("");
				this.setCenter(loggingOutPane);
				notifyObservers();
			}
		});

		logoutButton.setOnAction((event) -> {
			logOut();
			message.setText("");
			this.setCenter(loggingInPane);
			notifyObservers();
		});

		createAccButton.setOnAction((event) -> {
			if (createAccount(userField.getText(), passField.getText())) {
				welcome.setText("Welcome " + userField.getText() + "!");
				userField.clear();
				passField.clear();
				this.setCenter(loggingOutPane);
				notifyObservers();
			} else {
				userField.clear();
				passField.clear();
				message.setText("Username unavailable. Please choose unique username");
			}
		});
	}

	/**
	 * Attempts to log the user in, or returns an error code if log in cannot be
	 * done.
	 * 
	 * @param username : String representing the inputted username from the user.
	 * @param password : String representing the inputted password.
	 * @return integer representing the "error code" produced by the operation. 0 is
	 *         returned if there is no error and the user has logged in
	 *         successfully, 1 is returned if the user has the wrong password, or if
	 *         the account doesn't exist.
	 */
	public int logUserIn(String username, String password) {
		for (JukeboxAccount account : accounts) {
			if (account.getUsername().equals(username)) {
				if (account.getPassword().equals(password)) {
					loggedIn = account;
					return 0;
				} else {

					return 1;
				}
			}
		}
		// no matches with existing accounts
		return 1;
	}

	/**
	 * Creates a new user account. Users must all have unique usernames.
	 * 
	 * @param username : String representing username of the new account.
	 * @param password : String representing password of the new account.
	 * @return boolean representing whether the account was created successfully or
	 *         not. true if it was, false otherwise (if usernames are not unique).
	 */
	public boolean createAccount(String username, String password) {
		for (JukeboxAccount account : accounts) {
			if (account.getUsername().equals(username)) {
				return false;
			}
		}
		JukeboxAccount newUser = new JukeboxAccount(username, password);
		loggedIn = newUser;
		accounts.add(newUser);
		return true;
	}

	/**
	 * Logs the current user out.
	 */
	public void logOut() {
		loggedIn = null;
	}

	/**
	 * Gets the current user.
	 * 
	 * @return JukeboxAccount representing the user currently logged in.
	 */
	public JukeboxAccount getUser() {
		return loggedIn;
	}

	/**
	 * Reads the accounts list from a serialized file so that account data can be
	 * preserved between runs.
	 */
	public void readAccountsList() {
		FileInputStream fromFile;
		try {
			fromFile = new FileInputStream("accounts.ser");
			ObjectInputStream inFile = new ObjectInputStream(fromFile);

			accounts = (ArrayList<JukeboxAccount>) inFile.readObject();

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
	 * Writes the accounts list from a serialized file so that account data can be
	 * preserved between runs.
	 */
	public void writeAccountsList() {
		FileOutputStream bytesToDisk;
		try {
			bytesToDisk = new FileOutputStream("accounts.ser");
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);

			outFile.writeObject(accounts);

			outFile.close();
		} catch (FileNotFoundException err) {
			System.out.println("Output file not found");
		} catch (IOException err) {
			System.out.println("Couldn't write to file");
		}
	}

	/**
	 * The method notifies the SongSelectionPlaylistPane whenever there is a change
	 * in login status by sending it the current logged in user. (null if no user
	 * logged in)
	 */
	public void notifyObservers() {
		songPane.update(loggedIn);
	}
}