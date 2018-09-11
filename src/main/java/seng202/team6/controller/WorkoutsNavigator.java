package seng202.team6.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * <h1>Workouts navigator.</h1> //Could potentially be called workouts screen controller where all other workout screens inherit from.
 * <p>Provides navigation methods for the Workouts area./p>
 */
public class WorkoutsNavigator extends GUIUtilities {

    /**
     * Navigates the user to the Workouts Splash Screen.
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toWorkoutsScreen(Event event) {
        changeScreen(event, "../view/WorkoutsScreenSplash.fxml");
    }

    /**
     * Navigates the user to the Raw Data Viewer Screen..
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toRawDataVeiwer(Event event) {
        changeScreen(event, "../view/RawDataVeiwer.fxml");
    }

    /**
     * Navigates the user to the Workouts Analysis Screen.
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toWorkoutAnalysis(Event event) {
        changeScreen(event, "../view/WorkoutAnalysis.fxml");
    }

    /**
     * Navigates the user to the Add Workouts Screen.
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toAddWorkout(Event event) {
        changeScreen(event, "../view/AddWorkout.fxml");
    }

    /**
     * Navigates the user to the Workouts Manual Entry Screen.
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toWorkoutManualEntry(Event event) {
        changeScreen(event, "../view/WorkoutManualEntry.fxml");
    }

    /**
     * Navigates the user to the Upload Workouts Screen.
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toUploadWorkoutFile(Event event) {
        changeScreen(event, "../view/WorkoutUpload.fxml");
    }

}
