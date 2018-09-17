package seng202.team6.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.stage.Stage;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.User;

import java.io.IOException;
import java.sql.SQLException;

/**
 * <h1>Header Controller</h1>
 * <p>Sets up the application header and provides relevant functinality.</p>
 */
public class HeaderController {

    /**
     * The profile image, used as a node to get the current scene.
     */
    @FXML
    private Node profileImg;

    /**
     * The users name which is the menu text of user options.
     */
    @FXML
    private Menu usernameMenu;

    /**
     * The application's database manager.
     */
    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();
    

    /**
     * Initialises the name at the right of the header.
     */
    @FXML
    void initialize() {
        try {
            User currUser = databaseManager.getUser(ApplicationManager.getCurrentUserID()); //Replace with database current user.
            usernameMenu.setText(currUser.getFullName().toUpperCase());
        } catch (SQLException e) {
            ApplicationManager.displayPopUp("Database Error", "There is a problem accessing the database.", "error");
        }
    }


    /**
     * Redirects the user to the start screen when logging out.
     */
    @FXML
    public void toStartScreen() {
        try {
            Parent loginParent = FXMLLoader.load(getClass().getResource("/seng202/team6/view/startScreen2.fxml"));
            Scene loginScene = new Scene(loginParent);
            Stage appStage = (Stage) (profileImg.getScene().getWindow());
            appStage.setScene(loginScene);
            appStage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the fxml file.");
        }
    }
}
