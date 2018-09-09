package seng202.team6.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.team6.controller.ApplicationManager;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.datahandling.FileDataLoader;
import seng202.team6.models.User;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Uploader {

    @FXML
    private ChoiceBox<String> sessionType_E;

    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();
    private User currUser;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException, ClassNotFoundException {
        currUser = databaseManager.getUser(ApplicationManager.getCurrentUserID()); //Replace with database current user.
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Walking", "Running", "Biking");
        sessionType_E.setItems(availableChoices);
    }


    /**
     * Opens up a file selection window and allows user to select a desired .csv file.
     * @param event The button selection event.
     * @return The complete path for a selected .csv file from the users filing system.
     */
    @FXML
    public String fileSelector(ActionEvent event) throws SQLException, ClassNotFoundException {
        
        String filePath = null;

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fc.showOpenDialog(null);


        if (selectedFile != null) {
            filePath = selectedFile.getAbsolutePath();
        } else {
            System.out.println("File not valid!");
        }

        return filePath;
    }

    @FXML
    public void uploadActivity(ActionEvent event)  throws SQLException, ClassNotFoundException{

        String filePath = fileSelector(event);
        if (filePath != null) {
            FileDataLoader loader = new FileDataLoader();
            loader.importDataFromCSV(currUser.getUserID(), filePath, databaseManager);
        } else {
            System.out.println("Nothing is selected!");
        }
    }


    public void changeScreen(Event event, String screen) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource(screen));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }

    @FXML
    public void toAddWorkout(Event event) throws IOException {
        changeScreen(event, "AddWorkout.fxml");
    }


}