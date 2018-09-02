package seng202.team6.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Uploader {

    /**
     * Opens up a file selection window and allows user to select a desired .csv file.
     * @param event The button selection event.
     * @return The complete path for a selected .csv file from the users filing system.
     */
    @FXML
    public String fileSelector(ActionEvent event) {
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
        System.out.println("File path: " + filePath);

        return filePath;
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