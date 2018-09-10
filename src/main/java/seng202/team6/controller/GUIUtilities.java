package seng202.team6.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.paint.Color.rgb;

/**
 * <h1>GUI Utilities</h1> //Could be re named to GUIController since a lot of classes inherit, might make more sense.
 * <p>Provides useful methods used within many GUI controllers.</p>
 */
public class GUIUtilities {

    /**
     * Darkens the button on entering.
     * @param event User enters button area with mouse.
     */
    @FXML
    public void darkenButton(Event event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color:rgb(51,145,133);");
    }


    /**
     * Darkens the button on exiting.
     * @param event User exits button area with mouse.
     */
    @FXML
    public void lightenButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color:rgb(63,179,164);");
    }

    /**
     * Darkens a circle object when entered with mouse.
     * @param event User enters circle area with mouse.
     */
    @FXML
    public void darkenCircle(Event event) {
        Circle circle = (Circle) event.getSource();
        circle.setFill(rgb(63,179,164));
    }

    /**
     * Lightens a circle object when exitied with mouse.
     * @param event User exits circle area with mouse.
     */
    @FXML
    public void lightenCircle(MouseEvent event){
        Circle circle = (Circle) event.getSource();
        circle.setFill(rgb(51,145,133));
    }

    /**
     * Changes the screen to a desired screen.
     * @param event When something is clicked.
     * @param screen The screen to change to.
     */
    public void changeScreen(Event event, String screen) {
        try {
            Parent loginParent = FXMLLoader.load(getClass().getResource(screen));
            Scene loginScene = new Scene(loginParent);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(loginScene);
            appStage.show();
        } catch (IOException e) {
            ApplicationManager.displayPopUp("Error Loading Screen", "Sorry there has been a problem loading: \n" + screen);
        }
    }
}
