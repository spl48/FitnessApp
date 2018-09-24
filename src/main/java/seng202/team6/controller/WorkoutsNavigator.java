package seng202.team6.controller;

import javafx.event.Event;
import javafx.fxml.FXML;

/**
 * <h1>Workouts navigator.</h1> //Could potentially be called workouts screen controller where all other workout screens inherit from.
 * <p>Provides navigation methods for the Workouts area./p>
 */
public class WorkoutsNavigator extends GeneralScreenController {

    /**
     * Navigates the user to the Workouts Splash Screen.
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toWorkoutsScreen(Event event) {
        changeScreen(event, "/seng202/team6/view/WorkoutsScreenSplash.fxml");
    }

    /**
     * Navigates the user to the Raw Data Viewer Screen..
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toRawDataVeiwer(Event event) {
        changeScreen(event, "/seng202/team6/view/RawDataVeiwer2.fxml");
    }

    /**
     * Navigates the user to the Workouts Analysis Screen.
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toWorkoutAnalysis(Event event) {
        changeScreen(event, "/seng202/team6/view/WorkoutAnalysis.fxml");
    }

    /**
     * Navigates the user to the Add Workouts Screen.
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toAddWorkout(Event event) {
        changeScreen(event, "/seng202/team6/view/AddWorkout.fxml");
    }

    /**
     * Navigates the user to the Workouts Manual Entry Screen.
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toWorkoutManualEntry(Event event) {
        changeScreen(event, "/seng202/team6/view/WorkoutManualEntry.fxml");
    }

    /**
     * Navigates the user to the Upload Workouts Screen.
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toUploadWorkoutFile(Event event) {
        changeScreen(event, "/seng202/team6/view/WorkoutUpload2.fxml");
    }

    @FXML
    public void toMaps(Event event) {
        changeScreen(event, "/seng202/team6/view/MapsScreen.fxml");
    }

}
