package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng202.team6.datahandling.DataHandlerUtilities;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.utilities.ActivityValidation;
import seng202.team6.utilities.DatabaseValidation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

/**
 * <h1>Activity Manual Entry Controller</h1>
 * <p>Initialises and applies functionality to the Activity Entry screen. Allows the user to manually enter their
 * Activity data./p>
 */
public class workoutManualEntryController extends GeneralScreenController {

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
    private String sessionName, sessionType, notes, startTime, endTime, startDateTime, endDateTime, startDateString, endDateString;

    /**
     * Maximum and minimum Heart rate of the user for the activity entered.
     */
    private double distance;

    /**
     * The dates of the activity.
     */
    private LocalDate startDate, endDate;

    /**
     * The applications database manager.
     */
    private DatabaseManager dbManager = ApplicationManager.getDatabaseManager();


    @FXML
    void initialize() {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Walking", "Running", "Biking", "Other");
        sessionType_E.setItems(availableChoices);
    }

    /**
     * Redirects the user back to the workouts splash screen.
     * @param event When the user clicks the back button.
     */
    @FXML
    public void toWorkOutScreen(Event event) {
        changeScreen(event, "/seng202/team6/view/WorkoutsScreenSplash.fxml", "WORKOUTS");
    }

    /**
     * Enters thr activity into the database if it is valid.
     * @param event When the user clicks create Activity.
     */
    @FXML
    public void createActivity(ActionEvent event) throws SQLException {

        // Sets the data to the class variables
        setEnteredData();

        // Checks if the data is valid and enters it into the database if so.
        if (validEnteredData()) {
            startDateString = startDate.toString().replace("/", "-");
            endDateString = endDate.toString().replace("/", "-");
            startDateTime = startDateString + "T" + startTime;
            endDateTime = endDateString + "T" + endTime;
            dbManager.getActivityManager().addActivity(ApplicationManager.getCurrentUserID(), sessionName, startDateTime, endDateTime, sessionType, distance, notes);

            // Shows a confirmation pop up and returns the user to the workouts screen.
            ApplicationManager.displayPopUp("Entry Successful", "The activity was successfully loaded into the database!", "confirmation");
            toWorkOutScreen(event);
        }
    }

    /**
     * Sets the data in the activity form to the corresponding class attributes.
     */
    private void setEnteredData() {

        // Sets the entered data and saves it under class variables
        sessionName = sessionName_E.getText();
        startTime = startTime_E.getText();
        endTime = endTime_E.getText();
        sessionType = sessionType_E.getValue();
        startDate = startDate_E.getValue();
        endDate = endDate_E.getValue();
        startDateString = startDate.toString();
        endDateString = endDate.toString();

        // Checking the distance field is a valid double.
        try {
            distance = Double.parseDouble(distance_E.getText());
        } catch (NumberFormatException e) {
            ApplicationManager.displayPopUp("Invalid Data", "Please enter numerical data using numerical values.", "error");
        }
    }

    /**
     * Validates the entered activity data, displaying error pop ups when relevant.
     * @return Whether all fields are valid.
     */
    private boolean validEnteredData() throws SQLException {
        DateTimeFormatter strictTimeFormatter = DateTimeFormatter.ofPattern("H:mm:ss")
                .withResolverStyle(ResolverStyle.STRICT);
        // Validate all the activity fields.
        if(DatabaseValidation.validateTime(startTime) &&
                DatabaseValidation.validateTime(endTime) &&
                DatabaseValidation.validateDateWithFormat(startDateString) &&
                DatabaseValidation.validateDateWithFormat(endDateString) &&
                DatabaseValidation.validateStartEndDate(startDate, endDate) &&
                DatabaseValidation.validateStartEndTime(startTime, endTime) &&
                ActivityValidation.validateDistance(distance) &&
                ActivityValidation.validateNotes(notes_E.getText())) {
            LocalTime localEndTime = LocalTime.parse(endTime, strictTimeFormatter);

            if(DatabaseValidation.validateNonDuplicateActivity(localEndTime, startDate, endDate,dbManager)){
                notes = notes_E.getText();
                return true;
            }
            else {
                return false;
            }
        }
        else{
            return false;
        }
    }

}
