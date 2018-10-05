package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.LocalTimeStringConverter;
import seng202.team6.datahandling.ActivityManager;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import seng202.team6.models.User;

import javafx.scene.image.ImageView;
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
    private Node editButton;

    @FXML
    private Node editOn;

    @FXML
    private Button updateButton;


    Activity selectedActivity;
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
    HashMap<String, Integer> filteredActivities;

    private boolean isEditing = false;


    /**
     * Initialises the raw data viewer screen, populates table and list view based on filters.
     */
    public void initialize() {
        ActivityManager activityManager = dbManager.getActivityManager();
        filteredActivities = activityManager.getFilteredActivties(yearFilter, monthFilter, dayFilter, typeFilter);
        ObservableList<String> activityList = FXCollections.observableArrayList(filteredActivities.keySet());
        addActivitiesToListView(activityList);

        ObservableList<String> activityTypes = FXCollections.observableArrayList("All", "Walking", "Running", "Biking", "Other");
        typeEdit.setItems(activityTypes);
    }

    /**
     * Adds activities to the list view if there are any with the current filter.
     * Otherwise if no result from the filter is returned then displays 'No Activities Avaliable' and locks the list view.
     * @param activityList The list of activities that fits the filter values from the database.
     */
    private void addActivitiesToListView(ObservableList<String> activityList) {

        if (activityList.size() > 0) {
            activitySelect.setItems(activityList);
            activitySelect.getSelectionModel().select(0);
            activitySelect.setMouseTransparent( false );
            activitySelect.setFocusTraversable( true );
            setupTable();
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
        ArrayList<ActivityDataPoint> records = dbManager.getActivityManager().getActivityRecords(filteredActivities.get(activitySelect.getSelectionModel().getSelectedItem()));

        for ( int i = 0; i<rawDataTable.getItems().size(); i++) {
            rawDataTable.getItems().clear();
        }

        for (ActivityDataPoint record : records) {
            addRecordToTable(record);
        }

        selectedActivity = dbManager.getActivityManager().getActivity(filteredActivities.get(activitySelect.getSelectionModel().getSelectedItem()));
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

    @FXML
    public void filterActivities(Event event) {

        if (isEditing) {
            ApplicationManager.displayPopUp("Can't open filter", "Please finish editing before trying to filter!", "error");
        } else {
            ApplicationManager.displayPopUp("Activity Filtering", "To be - filtering window", "filter");
            ActivityManager activityManager = dbManager.getActivityManager();

            filteredActivities = activityManager.getFilteredActivties(yearFilter, monthFilter, dayFilter, typeFilter);
            ObservableList<String> activityList = FXCollections.observableArrayList(filteredActivities.keySet());
            addActivitiesToListView(activityList);
        }
    }

    @FXML
    public void editActivity() throws SQLException {
        if (selectedActivity != null) {
            activitySelect.setMouseTransparent( true );
            activitySelect.setFocusTraversable( false );
            isEditing = true;

            typeEdit.getSelectionModel().select(selectedActivity.getType());
            descriptionEdit.setText(selectedActivity.getDescription());
            startDateEdit.setValue(selectedActivity.getStartDate());
            endDateEdit.setValue(selectedActivity.getEndDate());
            startTimeEdit.setText(selectedActivity.getStartTime().toString());
            endTimeEdit.setText(selectedActivity.getEndTime().toString());
            distanceEdit.setText(Double.toString(selectedActivity.getDistance()));
            notesEdit.setText(selectedActivity.getNotes());

            setVisablityEdit(true);
        } else {
            ApplicationManager.displayPopUp("No Activity Selected", "Please select an activity", "error");
        }
    }


    @FXML
    public void stopEditing() throws SQLException {
//        typeEdit.getSelectionModel().select(selectedActivity.getType());
        isEditing = false;
        setVisablityEdit(false);

        activitySelect.setMouseTransparent( false );
        activitySelect.setFocusTraversable( true );
    }

    private void setVisablityEdit(Boolean isVisable) {
        if (selectedActivity.isManualActivity()) {
            distanceEdit.setVisible(isVisable);
        }

        editOn.setVisible(isVisable);
        descriptionEdit.setVisible(isVisable);
        startDateEdit.setVisible(isVisable);
        endDateEdit.setVisible(isVisable);
        startTimeEdit.setVisible(isVisable);
        endTimeEdit.setVisible(isVisable);
        typeEdit.setVisible(isVisable);
        notesEdit.setVisible(isVisable);
        updateButton.setVisible(isVisable);
    }

    @FXML
    public void updateActivity() throws SQLException {

        if (validEnteredData()) {
            String startDateString = startDateEdit.getValue().toString().replace("/", "-");
            String startDateTime = startDateString + "T" + startTimeEdit.getText();
            String endDateString = endDateEdit.getValue().toString().replace("/", "-");
            String endDateTime = endDateString + "T" + endTimeEdit.getText();

            dbManager.getActivityManager().updateStartDate(startDateTime, selectedActivity.getActivityid());
            dbManager.getActivityManager().updateEndDate(endDateTime, selectedActivity.getActivityid());
            dbManager.getActivityManager().updateDescription(descriptionEdit.getText(), selectedActivity.getActivityid());
            dbManager.getActivityManager().updateActivityType((String) typeEdit.getSelectionModel().getSelectedItem(),selectedActivity.getActivityid());
            dbManager.getActivityManager().updateNotes(notesEdit.getText(), selectedActivity.getActivityid());

            filteredActivities = dbManager.getActivityManager().getFilteredActivties(yearFilter, monthFilter, dayFilter, typeFilter);
            ObservableList<String> activityList = FXCollections.observableArrayList(filteredActivities.keySet());
            addActivitiesToListView(activityList);
        }
        showActivity();
        ApplicationManager.displayPopUp("Success!", "Your activity update was successful!", "confirmation");

    }


//    /**
//     * Validates the entered activity data, displaying error pop ups when relevant.
//     * @return Whether all fields are valid.
//     */
//    private boolean validEnteredData() {
//        return (DatabaseValidation.validateDateWithFormat(dateEdit.getValue().toString()) &&
//                DatabaseValidation.validateDescription(descriptionEdit.getText()));
//    }

    private boolean validEnteredData() throws SQLException {

        Double distance;
        if (selectedActivity.isManualActivity()) {
            distance = Double.parseDouble(distanceEdit.getText());
        } else {
            distance = selectedActivity.getDistance();
        }

        return (DatabaseValidation.validateDescription(descriptionEdit.getText())) &&
                DatabaseValidation.validateTime(startTimeEdit.getText()) &&
                DatabaseValidation.validateTime(endTimeEdit.getText()) &&
                DatabaseValidation.validateDateWithFormat(startDateEdit.getValue().toString()) &&
                DatabaseValidation.validateDateWithFormat(endDateEdit.getValue().toString()) &&
                DatabaseValidation.validateStartEndDate(startDateEdit.getValue(), endDateEdit.getValue()) &&
                DatabaseValidation.validateDistance(distance) &&
                DatabaseValidation.validateNonDuplicateActivity(LocalTime.parse(startTimeEdit.getText()), LocalTime.parse(endTimeEdit.getText()), startDateEdit.getValue(), endDateEdit.getValue());

    }

}
