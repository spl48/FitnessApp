package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seng202.team6.datahandling.ActivityManager;
import seng202.team6.datahandling.DatabaseManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * <h1>Error Box Controller</h1>
 * <p>Contains methods which initialise and display error pop ups.</p>
 */
public class ErrorBoxController extends GUIUtilities {

    /** The label which holds the error description and comment. */
    @FXML
    private Label errorText;
    
    /** The label which holds the title of the error pop up box. */
    @FXML
    private Label errorTitle;

    /** The error title text. */
    private static String errorTitleText;

    /** Error message text. */
    private static String errorMessage;


    /**
     * Initialises the error pop by setting the title and message to the desired text.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        if (errorText != null) {
            errorText.setText(errorMessage);
            errorTitle.setText(errorTitleText);
            errorText.setWrapText(true);
        }
    }

    @FXML
    public void lightenButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color:rgb(79,79,79);");
    }

    @FXML
    public void darkenButton(Event event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color:rgb(55,55,55);");
    }

    /**
     * Displays the error pop up with title and message using the fxml file for style.
     * @param title The title of the error pop up.
     * @param message The message contained within the error pop up.
     */
    public void displayErrorPopUP(String title, String message, String type) {
        
        // Sets the class properties to the error message and title.
        errorMessage = message.toUpperCase();
        errorTitleText = title.toUpperCase();
        
        // Sets up the error window.
        Stage errorWindow = new Stage();
        errorWindow.initModality(Modality.APPLICATION_MODAL);
        errorWindow.setMinWidth(400);
        errorWindow.initStyle(StageStyle.UNDECORATED); // Gets rid of the default bar at top of a window.

        // Tries to load the error box fxml and displays it.
        // Otherwise, shows error on the terminal.
        try {
            if (type == "error") {
                Parent root = FXMLLoader.load(getClass().getResource("/seng202/team6/view/errorPopUp.fxml"));
                Scene scene = new Scene(root, 400, 350);
                errorWindow.setScene(scene);
                errorWindow.showAndWait();
            } else if (type == "confirmation") {
                Parent root = FXMLLoader.load(getClass().getResource("/seng202/team6/view/confirmationPopUp.fxml"));
                Scene scene = new Scene(root, 400, 350);
                errorWindow.setScene(scene);
                errorWindow.showAndWait();
            } else if (type == "notification") {
                Parent root = FXMLLoader.load(getClass().getResource("/seng202/team6/view/notificationPopUp.fxml"));
                Scene scene = new Scene(root, 400, 350);
                errorWindow.setScene(scene);
                errorWindow.showAndWait();
            } else if (type == "filter") {
                Parent root = FXMLLoader.load(getClass().getResource("/seng202/team6/view/FilterActivityPopUp.fxml"));
                Scene scene = new Scene(root, 400, 350);
                errorWindow.setScene(scene);
                errorWindow.showAndWait();
            } else if (type == "loader") {
                Parent root = FXMLLoader.load(getClass().getResource("/seng202/team6/view/LoadingPopUp.fxml"));
                Scene scene = new Scene(root, 400, 150);
                errorWindow.setScene(scene);
                errorWindow.showAndWait();
            }

        } catch (IOException e) {
            System.out.println("Loading error\nSorry, could not load pop up.");
            e.printStackTrace();
        }
    }


    /**
     * Closes the error window.
     */
    @FXML
    public void closeWindow() {
        Stage stage = (Stage) errorTitle.getScene().getWindow();
        stage.close();
    }
    
}
