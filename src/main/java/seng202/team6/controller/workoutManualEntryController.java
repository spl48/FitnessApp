package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng202.team6.datahandling.DatabaseManager;

import java.sql.SQLException;
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
    private TextField startTime_E, endTime_E, distance_E, sessionName_E;


    /**
     * Text area space for user to supply any additional workout notes.
     */
    @FXML
    private TextArea notes_E;

    /**
     * Date Picker for the user to select the date of the activity.
     */
    @FXML
    private DatePicker startDate_E, endDate_E;

    /**
     * Choice box for the user to select the activity type.
     */
    @FXML
    private ChoiceBox<String> sessionType_E;

    /**
     * Textual activity details.
     */
    private String sessionName, sessionType, notes, startTime, endTime, startDateTime, endDateTime;

    /**
     * Maximum and minimum Heart rate of the user for the activity entered.
     */
    private double distance;

    /**
     * The date of the activity.
     */
    private LocalDate startDate, endDate;

    private DatabaseManager dbManager = ApplicationManager.getDatabaseManager();


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
        changeScreen(event, "/seng202/team6/view/WorkoutsScreenSplash.fxml");
    }

    /**
     * Enters thr activity into the database if it is valid.
     * @param event When the user clicks create Activity.
     */
    @FXML
    public void createActivity(ActionEvent event) throws SQLException {
        setEnteredData();
        if (validEnteredData()) {
            dbManager.addActivity(ApplicationManager.getCurrentUserID(), sessionName, startDateTime, endDateTime, sessionType, distance, notes);
            ApplicationManager.displayPopUp("Entry Successful", "The activity was successfully loaded into the database!", "confirmation");
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
        startDate = startDate_E.getValue();
        endDate = endDate_E.getValue();
        String startDateString = startDate.toString().replace("/", "-");
        String endDateString = endDate.toString().replace("/", "-");
        startDateTime = startDateString + "T" + startTime;
        endDateTime = endDateString + "T" + endTime;
        notes = notes_E.getText();
        try {
            distance = Double.parseDouble(distance_E.getText());
        } catch (NumberFormatException e) {
            ApplicationManager.displayPopUp("Invalid Data", "Please enter numerical data using numbers!", "error");
        }
    }

    /**
     * Validates the entered activity data, displaying error pop ups when relevant.
     * @return Whether all fields are valid.
     */
    private boolean validEnteredData() {
//        return UserDataValidation.validateUserName(username) &&
//                UserDataValidation.validateName(first, "First Name") &&
//                UserDataValidation.validateName(last, "Last Name") &&
//                UserDataValidation.validateBirthDate(birthDate) &&
//                UserDataValidation.validateGender(gender) &&
//                UserDataValidation.validateDoubleValue(height, "Height", 280, 55) &&
//                UserDataValidation.validateDoubleValue(weight, "Weight", 600,2) &&
//                UserDataValidation.validateDoubleValue(stride, "Stride Length", 2.5,0.3);
    return true;
}

}
