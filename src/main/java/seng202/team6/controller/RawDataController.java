package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team6.datahandling.ActivityManager;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;

import seng202.team6.utilities.DatabaseValidation;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <h1>Raw Data Viewer and Activity Edit Controller</h1>
 * <p>Displays activity records in a table view while high level activities are displayed to the side.
 * Allows selection of activities though a list view which can be filtered based on date or type information.</p>
 */
public class RawDataController extends WorkoutsNavigator{

    /**
     * The year filter value.
     */
    private static String yearFilter = "All";

    /**
     * The month filter value.
     */
    private static String monthFilter = "All";

    /**
     * The day filter value.
     */
    private static String dayFilter = "All";

    /**
     * The type filter value.
     */
    private static String typeFilter = "All";

    /**
     * The table view which contains the records.
     */
    @FXML
    private TableView rawDataTable;

    /**
     * The activity description label.
     */
    @FXML
    private Label descriptionLabel;

    /**
     * The type description label
     */
    @FXML
    private Label typeLabel;

    /**
     * The notes description label.
     */
    @FXML
    private Label notesLabel;

    /**
     * The date description label.
     */
    @FXML
    private Label startDateLabel, endDateLabel, startTimeLabel, endTimeLabel;


    /**
     * The average velocity label.
     */
    @FXML
    private Label velocityLabel;

    /**
     * The distance label.
     */
    @FXML
    private Label distanceLabel;

    /**
     * The description edit field.
     */
    @FXML
    private TextField descriptionEdit;

    /**
     * The start date edit field.
     */
    @FXML
    private DatePicker startDateEdit;


    /**
     * The end date edit field.
     */
    @FXML
    private DatePicker endDateEdit;

    /**
     * The start time edit field.
     */
    @FXML
    private TextField startTimeEdit;

    /**
     * The end time edit field.
     */
    @FXML
    private TextField endTimeEdit;

    /**
     * The end time edit field.
     */
    @FXML
    private TextField distanceEdit;


    /**
     * The type edit field.
     */
    @FXML
    private ChoiceBox typeEdit;

    /**
     * The notes edit field.
     */
    @FXML
    private TextArea notesEdit;


    @FXML
    private Node editOn;

    @FXML
    private Button updateButton;


    private Activity selectedActivity;

    private int selectedActivityIndex;

    /**
     * The list view which contains the activities to select from.
     */
    @FXML
    private ListView activitySelect;

    /**
     * The Application database manager.
     */
    private DatabaseManager dbManager = ApplicationManager.getDatabaseManager();

    /**
     * The filtered list of activities.
     */
    private  ArrayList<Activity> filteredActivities;

    private boolean isEditing = false;


    /**
     * Initialises the raw data viewer screen, populates table and list view based on filters.
     */
    public void initialize() {

        // Resets the filter values and gets the filtered activities.
        resetFilters();
        setupTable();
        updateListView();

        // Sets up the activity type editing drop down.
        ObservableList<String> activityTypes = FXCollections.observableArrayList("All", "Walking", "Running", "Biking", "Other");
        typeEdit.setItems(activityTypes);
    }

    private void updateListView() {

        filteredActivities = dbManager.getActivityManager().getFilteredFullActivties(yearFilter, monthFilter, dayFilter, typeFilter);
        ObservableList<String> availableActivities = FXCollections.observableArrayList();

        // Checks if there are activities available
        if (filteredActivities.size() > 0) {

           for (Activity activity : filteredActivities) { // Add all activities to available activities initially
               availableActivities.add(activity.getStartDate().toString() + " " + activity.getDescription());
           }
        }

        addActivitiesToListView(availableActivities);
    }



    /**
     * Adds activities to the list view if there are any with the current filter.
     * Otherwise if no result from the filter is returned then displays 'No Activities Avaliable' and locks the list view.
     * @param activityList The list of activities that fits the filter values from the database.
     */
    private void addActivitiesToListView(ObservableList<String> activityList) {

        // Checks if there are activities available if so sets the items in the list view and selects the first item.
        // Then sets the corresponding raw data in the table.
        if (activityList.size() > 0) {
            activitySelect.setItems(activityList);
            activitySelect.getSelectionModel().select(0);
            activitySelect.setMouseTransparent( false );
            activitySelect.setFocusTraversable( true );
        // If no activities then all labels are changed and the list view is locked.
        } else {
            activitySelect.setItems(FXCollections.observableArrayList("No Activities Available"));
            descriptionLabel.setText("No Activity Selected");
            velocityLabel.setText("No Activity Selected");
            distanceLabel.setText("No Activity Selected");
            startDateLabel.setText("No Activity Selected");
            endDateLabel.setText("No Activity Selected");
            startTimeEdit.setText("No Activity Selected");
            endTimeEdit.setText("No Activity Selected");
            typeLabel.setText("No Activity Selected");
            notesLabel.setText("No Activity Selected");
            activitySelect.setMouseTransparent( true );
            activitySelect.setFocusTraversable( false );
        }
    }

    /**
     * Sets the filter values.
     * @param newDayFilter The day filter value.
     * @param newMonthFilter The month filter value.
     * @param newYearFilter The year filter value.
     * @param newTypeFilter The type filter value.
     */
    public static void setFilters(String newDayFilter, String newMonthFilter, String  newYearFilter, String newTypeFilter) {
        dayFilter = newDayFilter;
        monthFilter = newMonthFilter;
        yearFilter = newYearFilter;
        typeFilter = newTypeFilter;
    }

    /**
     * Makes a column in the raw data table.
     * @param name The name of the column
     * @param attributeName The attribute name in ActivityDataPoint which is associated with the column.
     * @return
     */
    private TableColumn make_column(String name, String attributeName) {
        TableColumn col = new TableColumn(name);
        col.setCellValueFactory(new PropertyValueFactory<>(attributeName));
        col.setPrefWidth((rawDataTable.getPrefWidth()/5)-1);
        col.setResizable(false);
        return col;
    }

    /**
     * Sets up all the columns in the table.
     */
    private void setupTable() {
        TableColumn timeCol = make_column("Time", "time");
        TableColumn heartRateCol = make_column("Heart Rate", "heartRate");
        TableColumn latitudeCol = make_column("Latitude", "latitude");
        TableColumn longitudeCol = make_column("Longitude", "longitude");
        TableColumn elevationCol = make_column("Elevation", "elevation");

        rawDataTable.getColumns().addAll(timeCol, heartRateCol, latitudeCol, longitudeCol, elevationCol);
    }


    /**
     * Adds a record from an activity into the table view.
     * @param record A record/ActivityDataPoint from an activity.
     */
    public void addRecordToTable(ActivityDataPoint record) {
        rawDataTable.getItems().add(record);
    }

    /**
     * Shows all the records of an activity in the table view and stats to the left.
     * @throws SQLException
     */
    public void showActivity() throws SQLException {
        // Gets the filtered records to populate the raw data table.
        selectedActivity = filteredActivities.get(activitySelect.getSelectionModel().getSelectedIndex());
        ArrayList<ActivityDataPoint> records = dbManager.getActivityManager().getActivityRecords(selectedActivity.getActivityid());

        for ( int i = 0; i<rawDataTable.getItems().size(); i++) {
            rawDataTable.getItems().clear();
        }

        // Adds the records to the raw data table.
        for (ActivityDataPoint record : records) {
            addRecordToTable(record);
        }

        // Gets the selected activity object and sets the information values to those which correspond with the activity.
        selectedActivity.addAllActivityData(records);
        descriptionLabel.setText(selectedActivity.getDescription());
        velocityLabel.setText(Double.toString(Math.round(selectedActivity.findAverageSpeed())) + " km/h");
        distanceLabel.setText(Double.toString(Math.round(selectedActivity.findDistanceFromStart(records.size()-1))) + " km");
        startDateLabel.setText(selectedActivity.getStartDate().toString());
        endDateLabel.setText(selectedActivity.getEndDate().toString());
        startTimeLabel.setText(selectedActivity.getStartTime().toString());
        endTimeLabel.setText(selectedActivity.getEndTime().toString());
        typeLabel.setText(selectedActivity.getType());
        notesLabel.setText(selectedActivity.getNotes());
    }

    /**
     * Opens up the filter pop up and allows the user to select options to filter their results.
      */
    @FXML
    public void filterActivities() {

        // Displays the filter pop up if user not editing their activity.
        if (isEditing) {
            ApplicationManager.displayPopUp("Can't open filter", "Please finish editing before trying to filter!", "error");
        } else {
            ApplicationManager.displayPopUp("Activity Filtering", "To be - filtering window", "filter");
            updateListView();
        }
    }

    /**
     * Allows the user to edit their activities. Changes the raw data screen to edit mode and initialises the edit fields.
     * Otherwise displays and error message which notifies the user that there is no activity selected.
     */
    @FXML
    public void editActivity() {
        if (selectedActivity != null) {

            // Locks the list view.
            activitySelect.setMouseTransparent( true );
            activitySelect.setFocusTraversable( false );
            isEditing = true;

            // Initialises the edit fields to the existing data.
            typeEdit.getSelectionModel().select(selectedActivity.getType());
            descriptionEdit.setText(selectedActivity.getDescription());
            startDateEdit.setValue(selectedActivity.getStartDate());
            endDateEdit.setValue(selectedActivity.getEndDate());
            startTimeEdit.setText(selectedActivity.getStartTime().toString());
            endTimeEdit.setText(selectedActivity.getEndTime().toString());
            distanceEdit.setText(Double.toString(selectedActivity.getDistance()));
            notesEdit.setText(selectedActivity.getNotes());

            // Shows the edit fields - sets their visibility to true.
            setVisablityEdit(true);
        } else {
            // Displays an notification that tells the user to select and activity.
            ApplicationManager.displayPopUp("No Activity Selected", "Please select an activity", "error");
        }
    }


    /**
     * Checks if the user is finished editing.
     */
    @FXML
    public void stopEditingCheck() {
        // Shows a yes / no pop up for finish editing confirmation.
        boolean exitEditing = ApplicationManager.getAnswerFromPopUp("Are you sure you want to finish editing? Your changes will not be saved.");

        // If the user selects yes then the screen will return to normal.
        if (exitEditing) {
            stopEditing();
        }
    }

    /**
     * Stops the user editing and hides the edit fields, unlocks the list view.
     */
    @FXML
    private void stopEditing() {
        isEditing = false; // Sets editing status to false
        setVisablityEdit(false); // Hides edit fields

        // Unlocks the list view.
        activitySelect.setMouseTransparent(false);
        activitySelect.setFocusTraversable(true);
    }

    /**
     * Sets the visibility of the edit fields, limiting this if the activity is connected to records which
     * means the user can only only edit type, description and notes.
     * @param isVisible Whether or not to display the edit fields.
     */
    private void setVisablityEdit(Boolean isVisible) {

        // Checks if the activity selected is manual if it is set the visibility the date time fields.
        if (selectedActivity.isManualActivity()) {
            distanceEdit.setVisible(isVisible);
            startDateEdit.setVisible(isVisible);
            endDateEdit.setVisible(isVisible);
            startTimeEdit.setVisible(isVisible);
            endTimeEdit.setVisible(isVisible);
        }

        // Sets the viability of description, type and notes to editable, and the viability of the update and
        // edit toggle buttons.
        editOn.setVisible(isVisible);
        descriptionEdit.setVisible(isVisible);
        typeEdit.setVisible(isVisible);
        notesEdit.setVisible(isVisible);
        updateButton.setVisible(isVisible);
    }

    /**
     * Updates the currently edited activity.
     * @throws SQLException When the
     */
    @FXML
    public void updateActivity() throws SQLException {
        // Checks if the entered information is valid.
        if (validEnteredData()) {

            // Updates the additional fields if it is a manual activity.
            if (selectedActivity.isManualActivity()) {
                String startDateString = startDateEdit.getValue().toString().replace("/", "-");
                String startDateTime = startDateString + "T" + startTimeEdit.getText();
                String endDateString = endDateEdit.getValue().toString().replace("/", "-");
                String endDateTime = endDateString + "T" + endTimeEdit.getText();

                dbManager.getActivityManager().updateStartDate(startDateTime, selectedActivity.getActivityid());
                dbManager.getActivityManager().updateEndDate(endDateTime, selectedActivity.getActivityid());
            }

            // Updates the description, type and notes fields.
            dbManager.getActivityManager().updateDescription(descriptionEdit.getText(), selectedActivity.getActivityid());
            dbManager.getActivityManager().updateActivityType((String) typeEdit.getSelectionModel().getSelectedItem(), selectedActivity.getActivityid());
            dbManager.getActivityManager().updateNotes(notesEdit.getText(), selectedActivity.getActivityid());

            // Updates the list view to express the new changes made if any.
            updateListView();

            // Stops the user editing and reloads the data.
            stopEditing();
            showActivity();
            ApplicationManager.displayPopUp("Success!", "Your activity update was successful!", "confirmation");
        } else {
            // Displays a pop up notification
            ApplicationManager.displayPopUp("Update Failure", "Your activity update was unsuccessful", "error");
        }
    }


     /**
     * Validates the entered activity data, displaying error pop ups when relevant.
     * @return Whether all fields are valid.
     */
    private boolean validEnteredData() throws SQLException {

        Double distance;

        // Checks if the data is valid, this is different for the manual entry as a there are more fields
        // as there are more fields to edit.
        if (selectedActivity.isManualActivity()) {
            distance = Double.parseDouble(distanceEdit.getText());
            return (DatabaseValidation.validateDescription(descriptionEdit.getText())) &&
                    DatabaseValidation.validateTime(startTimeEdit.getText()) &&
                    DatabaseValidation.validateTime(endTimeEdit.getText()) &&
                    DatabaseValidation.validateDateWithFormat(startDateEdit.getValue().toString()) &&
                    DatabaseValidation.validateDateWithFormat(endDateEdit.getValue().toString()) &&
                    DatabaseValidation.validateStartEndDate(startDateEdit.getValue(), endDateEdit.getValue()) &&
                    DatabaseValidation.validateStartEndTime(startTimeEdit.getText(), endTimeEdit.getText()) &&
                    DatabaseValidation.validateNotes(notesEdit.getText()) &&
                    DatabaseValidation.validateDistance(distance) &&
                    DatabaseValidation.validateNonDuplicateActivity(LocalTime.parse(startTimeEdit.getText()), LocalTime.parse(endTimeEdit.getText()), startDateEdit.getValue(), endDateEdit.getValue());

        } else {
            return (DatabaseValidation.validateDescription(descriptionEdit.getText()) &&
                    DatabaseValidation.validateNotes(notesEdit.getText()));
        }
    }

}
