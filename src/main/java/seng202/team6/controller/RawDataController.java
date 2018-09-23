package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team6.datahandling.ActivityManager;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import seng202.team6.models.User;

import javafx.scene.image.ImageView;

import java.sql.SQLException;
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
    private Label dateLabel;

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


    /**
     * Initialises the raw data viewer screen, populates table and list view based on filters.
     */
    public void initialize() {
        ActivityManager activityManager = dbManager.getActivityManager();
        filteredActivities = activityManager.getFilteredActivties(yearFilter, monthFilter, dayFilter, typeFilter);
        ObservableList<String> activityList = FXCollections.observableArrayList(filteredActivities.keySet());
        addActivitiesToListView(activityList);
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
        ArrayList<ActivityDataPoint> records = dbManager.getActivityRecords(filteredActivities.get(activitySelect.getSelectionModel().getSelectedItem()));

        for ( int i = 0; i<rawDataTable.getItems().size(); i++) {
            rawDataTable.getItems().clear();
        }

        for (ActivityDataPoint record : records) {
            addRecordToTable(record);
        }

        Activity selectedActivity = dbManager.getActivity(filteredActivities.get(activitySelect.getSelectionModel().getSelectedItem()));
        descriptionLabel.setText(selectedActivity.getDescription());
        dateLabel.setText(selectedActivity.getStartDate().toString());
        typeLabel.setText(selectedActivity.getType());
        notesLabel.setText(selectedActivity.getNotes());
    }

    @FXML
    public void filterActivities(Event event) {
        ApplicationManager.displayPopUp("Activity Filtering", "To be - filtering window", "filter");
        ActivityManager activityManager = dbManager.getActivityManager();
        filteredActivities = activityManager.getFilteredActivties(yearFilter, monthFilter, dayFilter, typeFilter);
        ObservableList<String> activityList = FXCollections.observableArrayList(filteredActivities.keySet());
        addActivitiesToListView(activityList);
    }

    @FXML
    public void lightenFilter(Event event) {

    }

    @FXML
    public void darkenFilter(Event event) {

    }

    /**
     * Navigates the user to the Workouts Splash Screen.
     * @param event When the user clicks on a button directing to this screen.
     */
    @FXML
    public void toActivityEditScreen(Event event) {
        changeScreen(event, "/seng202/team6/view/RawDataVeiwerEditing.fxml");
    }
}
