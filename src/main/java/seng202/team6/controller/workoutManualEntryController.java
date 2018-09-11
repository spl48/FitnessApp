package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

/**
 * <h1>Activity Manual Entry Controller</h1>
 * <p>Initialises and applies functionality to the Activity Entry screen. Allows the user to manually enter their
 * Activity data./p>
 */
public class workoutManualEntryController extends GUIUtilities {

    // Note start and end time will be time objects of sort when research best way to represent and get input from using
    // gui.
    /**
     * Text fields for activity entry form.
     */
    @FXML
    private TextField startTime_E, endTime_E, maxHR_E, minHR_E, sessionName_E;


    /**
     * Text area space for user to supply any additional workout notes.
     */
    @FXML
    private TextArea notes_E;

    /**
     * Date Picker for the user to select the date of the activity.
     */
    @FXML
    private DatePicker sessionDate_E;

    /**
     * Choice box for the user to select the activity type.
     */
    @FXML
    private ChoiceBox<String> sessionType_E;

    /**
     * Textual activity details.
     */
    private String sessionName, sessionType, notes, startTime, endTime;

    /**
     * Maximum and minimum Heart rate of the user for the activity entered.
     */
    private double maxHR, minHR;

    /**
     * The date of the activity.
     */
    private LocalDate sessionDate;


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Walking", "Running", "Biking");
        sessionType_E.setItems(availableChoices);
    }

    /**
     * Redirects the user back to the workouts splash screen.
     * @param event When the user clicks the back button.
     */
    @FXML
    public void toWorkOutScreen(Event event) {
        changeScreen(event, "../view/WorkoutsScreenSplash.fxml");
    }

    /**
     * Enters thr activity into the database if it is valid.
     * @param event When the user clicks create Activity.
     */
    @FXML
    public void createActivity(ActionEvent event) {
        setEnteredData();
        if (validEnteredData()) {
            System.out.println("Created a new activity!!");
            printData();
            toWorkOutScreen(event);
            //Enter into database
        }
    }

    /**
     * Sets the data in the activity form to the corresponding class attributes.
     */
    private void setEnteredData() {
        sessionName = sessionName_E.getText();
        startTime = startTime_E.getText();
        endTime = endTime_E.getText();
        sessionType = sessionType_E.getValue();
        sessionDate = sessionDate_E.getValue();
        notes = notes_E.getText();
        try {
            minHR = Double.parseDouble(minHR_E.getText());
            maxHR = Double.parseDouble(maxHR_E.getText());
        } catch (NumberFormatException e) {
            ApplicationManager.displayPopUp("Invalid Data", "Please enter numerical data using numbers!");
        }
    }

    /**
     * Validates the entered activity data, displaying error pop ups when relevant.
     * @return Whether all fields are valid.
     */
    private boolean validEnteredData() {
//        return DataValidation.validateUserName(username) &&
//                DataValidation.validateName(first, "First Name") &&
//                DataValidation.validateName(last, "Last Name") &&
//                DataValidation.validateBirthDate(birthDate) &&
//                DataValidation.validateGender(gender) &&
//                DataValidation.validateDoubleValue(height, "Height", 280, 55) &&
//                DataValidation.validateDoubleValue(weight, "Weight", 600,2) &&
//                DataValidation.validateDoubleValue(stride, "Stride Length", 2.5,0.3);
    return true;
}

    /**
     * Prints all the workout data for testing purposes - can get rid of later.
     */
    private void printData() {
        System.out.println("Session Name: " + sessionName);
        System.out.println("Session Type: " + sessionType);
        System.out.println("Session Date: " + sessionDate);
        System.out.println("Start Time: " + startTime);
        System.out.println("Finish Time: " + endTime);
        System.out.println("Minimum Heart Rate: " + minHR);
        System.out.println("Maximum Heart Rate: " + maxHR);
        System.out.println("Notes: \n" + notes);
    }


}
