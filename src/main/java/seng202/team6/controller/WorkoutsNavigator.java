package seng202.team6.controller;

import javafx.event.Event;
import javafx.fxml.FXML;

import java.sql.SQLException;

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
        changeScreen(event, "/seng202/team6/view/WorkoutsScreenSplash.fxml", "WORKOUTS");
    }

    /**
     * Navigates the user to the Raw Data Viewer Screen..
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toRawDataVeiwer(Event event) {
        ApplicationManager.setBackOptions(true, "/seng202/team6/view/WorkoutsScreenSplash.fxml", "WORKOUTS");
        changeScreen(event, "/seng202/team6/view/RawDataVeiwer2.fxml", "RAW DATA VIEWER");
    }

    /**
     * Navigates the user to the Workouts Analysis Screen.
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toWorkoutAnalysis(Event event) throws SQLException {
        ApplicationManager.setBackOptions(true, "/seng202/team6/view/WorkoutsScreenSplash.fxml", "WORKOUTS");
        if (ApplicationManager.getDatabaseManager().getActivityManager().getActivities(ApplicationManager.getCurrentUserID()).size() > 0) {
            changeScreen(event, "/seng202/team6/view/WorkoutAnalysis3.fxml", "ANALYSIS");
        } else {
            ApplicationManager.displayPopUp("Cannot Open", "No activities uploaded, please upload some activites first!", "error");
        }
    }

    /**
     * Navigates the user to the Add Workouts Screen.
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toAddWorkout(Event event) {
        ApplicationManager.setBackOptions(true, "/seng202/team6/view/WorkoutsScreenSplash.fxml", "WORKOUTS");
        changeScreen(event, "/seng202/team6/view/AddWorkout.fxml", "ADD ACTIVITY");
    }

    /**
     * Navigates the user to the Workouts Manual Entry Screen.
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toWorkoutManualEntry(Event event) {
        ApplicationManager.setBackOptions(true, "/seng202/team6/view/AddWorkout.fxml", "ADD WORKOUT");
        changeScreen(event, "/seng202/team6/view/WorkoutManualEntry.fxml", "ADD MANUAL ACTIVITY");
    }

}
