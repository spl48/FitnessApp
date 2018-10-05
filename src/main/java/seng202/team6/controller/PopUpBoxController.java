package seng202.team6.controller;

import javafx.concurrent.Service;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seng202.team6.datahandling.DatabaseManager;

import java.io.IOException;


/**
 * <h1>Pop Up Box Controller</h1>
 * <p>Contains methods which initialise and display error pop ups.</p>
 */
public class PopUpBoxController extends GeneralScreenController {

    /** The label which holds the error description and comment. */
    @FXML
    private Label errorText;
    
    /** The label which holds the title of the error pop up box. */
    @FXML
    private Label errorTitle;

    @FXML
    public ImageView cancelButton;

    /** The error title text. */
    private static String errorTitleText;

    /** Error message text. */
    private static String errorMessage;

    /**
     * The Application's database manager.
     */
    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();




    /**
     * Initialises the error pop by setting the title and message to the desired text.
     */
    @FXML
    void initialize() {
        if (errorText != null) {
            errorText.setText(errorMessage);
            errorTitle.setText(errorTitleText);
            errorText.setWrapText(true);
        }
    }

    /**
     * Lightens the error box controller on mouse entry.
     * @param event User exits button area with mouse.
     */
    @FXML
    public void lightenButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color:rgb(79,79,79);");
    }

    /**
     * Darkens the error box controller on mouse exit.
     * @param event User enters button area with mouse.
     */
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

        // Tries to create a pop up box based on a type and displays it.
            if (type == "error") {
                createPopUpBox("/seng202/team6/view/errorPopUp.fxml", 400, 350);
            } else if (type == "confirmation") {
                createPopUpBox("/seng202/team6/view/confirmationPopUp.fxml", 400, 350);
            } else if (type == "notification") {
                createPopUpBox("/seng202/team6/view/notificationPopUp.fxml", 400, 350);
            } else if (type == "filter") {
                createPopUpBox("/seng202/team6/view/FilterActivityPopUp.fxml", 400, 350);
            } else if (type == "loader") {
                createPopUpBox("/seng202/team6/view/LoadingPopUp.fxml", 400, 150);
            } else if (type == "tutorialsmall") {
                createPopUpBox("/seng202/team6/view/tutorialPopUp.fxml", 400, 350);
            } else if (type == "tutorialbig") {
                createPopUpBox("/seng202/team6/view/tutorialPopUp.fxml", 400, 380);
            } else if (type == "profileDelete") {
                createPopUpBox("/seng202/team6/view/profileDeletePopUp.fxml", 400, 350);
            }
    }

    public void cancel() {
        closeWindow();
    }

    public void removedUser() {
        System.out.println(ApplicationManager.getCurrentUsername());
        databaseManager.getUserWriter().removeUser(ApplicationManager.getCurrentUsername());
        closeWindow();
    }


    /**
     * Creates a pop up box with a given scene using an fxml file and specified width and height.
     * @param fxmlPath The fxml path that is loaded.
     * @param width The width of the pop up.
     * @param height The height of the pop up.
     */
    private void createPopUpBox(String fxmlPath, int width, int height) {

        // Sets up the error window.
        Stage errorWindow = new Stage();
        errorWindow.initModality(Modality.APPLICATION_MODAL);
        errorWindow.setMinWidth(400);
        errorWindow.initStyle(StageStyle.UNDECORATED); // Gets rid of the default bar at top of a window.

        // Gets the relevant fxml path and sets the scene of the error window showing it to the user.
        // Otherwise if fxml cannot be loaded then shows an error on the terminal.
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root, width, height);
            errorWindow.setScene(scene);
            errorWindow.showAndWait();
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
