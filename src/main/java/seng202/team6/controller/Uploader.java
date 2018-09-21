package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.datahandling.FileDataLoader;
import seng202.team6.models.User;

import java.io.File;
import java.sql.SQLException;

/**
 * <h1>File Uploader GUI Controller</h1>
 * <p>Initialises and applies functionality to the File Upload screen allowing the user to upload Activities</p>
 */
public class Uploader extends WorkoutsNavigator {

    /**
     * The application database manager.
     */
    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();

    /**
     * The current user which is signed in.
     */
    private User currUser;

    /**
     * Initialising the current user and the activity type drop down.
     * @throws SQLException Error when getting user from the database.
     */
    @FXML
    void initialize() throws SQLException {
        currUser = databaseManager.getUser(ApplicationManager.getCurrentUsername());
    }


    /**
     * Opens up a file selection window and allows user to select a desired .csv file.
     * @return The complete path for a selected .csv file from the users filing system.
     */
    @FXML
    private String fileSelector() {

        // Initialising the file path.
        String filePath = null;

        // Creating and showing the file chooser.
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fc.showOpenDialog(null);

        // Checks if a valid csv file was selected.
        if (selectedFile != null) {
            filePath = selectedFile.getAbsolutePath();
        } else {
            System.out.println("File not valid!");
        }

        return filePath;
    }

    /**
     * Uploads a csv activities file to the database.
     */
    @FXML
    public void uploadActivity(Event event) {

        String filePath = fileSelector();
        if (filePath != null) {
            FileDataLoader loader = new FileDataLoader();
            loader.importDataFromCSV(currUser.getUserID(), filePath, databaseManager);
            changeScreen(event, "/seng202/team6/view/WorkoutUpload.fxml");
        } else {
            System.out.println("Nothing is selected!");
        }
        changeScreen(event, "/seng202/team6/view/WorkoutUpload.fxml");
    }
}