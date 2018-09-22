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

import javax.activity.ActivityCompletedException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class RawDataController2 extends WorkoutsNavigator{

    private static String yearFilter = "2015";
    private static String monthFilter = "All";
    private static String dayFilter = "All";
    private static String typeFilter = "All";

    @FXML
    private TableView rawDataTable;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label notesLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ListView activitySelect;

    private static ListView activitySelectEdit;

    @FXML
    private ImageView filterButton;

    private DatabaseManager dbManager = ApplicationManager.getDatabaseManager();

    public void initialize() throws SQLException {
        User currUser = dbManager.getUser(ApplicationManager.getCurrentUserName());
        ActivityManager activityManager = dbManager.getActivityManager();
        HashMap<Integer, String> activities = activityManager.getFilteredActivties(yearFilter, monthFilter, dayFilter, typeFilter);
        ObservableList<Integer> activityList = FXCollections.observableArrayList(activities.keySet());
        activitySelect.setItems(activityList);
        setupTable();
    }

    public static void setFilters(String newDayFilter, String newMonthFilter, String  newYearFilter, String newTypeFilter) {
        dayFilter = newDayFilter;
        monthFilter = newMonthFilter;
        yearFilter = newYearFilter;
        typeFilter = newTypeFilter;
    }

    private TableColumn make_column(String name, String attributeName, int size) {
        TableColumn col = new TableColumn(name);
        col.setCellValueFactory(new PropertyValueFactory<>(attributeName));
        col.setPrefWidth((rawDataTable.getPrefWidth()/5)-1);
        col.setResizable(false);
        return col;
    }

    private void setupTable() {
        TableColumn timeCol = make_column("Time", "time", 120);
        TableColumn heartRateCol = make_column("Heart Rate", "heartRate", 120);
        TableColumn latitudeCol = make_column("Latitude", "latitude", 120);
        TableColumn longitudeCol = make_column("Longitude", "longitude", 120);
        TableColumn elevationCol = make_column("Elevation", "elevation", 120);

        rawDataTable.getColumns().addAll(timeCol, heartRateCol, latitudeCol, longitudeCol, elevationCol);
    }

    public void addRecordToTable(ActivityDataPoint record) throws SQLException {
        rawDataTable.getItems().add(record);
    }

    public void showActivity() throws SQLException {
        //ArrayList<ActivityDataPoint> records = dbManager.getActivityManager().getFilteredActivties((int) activitySelect.getSelectionModel().getSelectedItem());
        ArrayList<ActivityDataPoint> records = dbManager.getActivityRecords((int) activitySelect.getSelectionModel().getSelectedItem());

        for ( int i = 0; i<rawDataTable.getItems().size(); i++) {
            rawDataTable.getItems().clear();
        }

        for (ActivityDataPoint record : records) {
            addRecordToTable(record);
        }

        descriptionLabel.setText(dbManager.getActivity((int) activitySelect.getSelectionModel().getSelectedItem()).getDescription());
        dateLabel.setText(dbManager.getActivity((int) activitySelect.getSelectionModel().getSelectedItem()).getStartDate().toString());
        typeLabel.setText(dbManager.getActivity((int) activitySelect.getSelectionModel().getSelectedItem()).getType());
        notesLabel.setText(dbManager.getActivity((int) activitySelect.getSelectionModel().getSelectedItem()).getNotes());

        activitySelectEdit = activitySelect;

    }

    public static ListView getActivitySelector() {
        return activitySelectEdit;
    }

    @FXML
    public void filterActivities(Event event) {
        ApplicationManager.displayPopUp("Activity Filtering", "To be - filtering window", "filter");
        ActivityManager activityManager = dbManager.getActivityManager();
        HashMap<Integer, String> activities = activityManager.getFilteredActivties(yearFilter, monthFilter, dayFilter, typeFilter);
        System.out.println(yearFilter);
        ObservableList<Integer> activityList = FXCollections.observableArrayList(activities.keySet());
        activitySelect.setItems(activityList);
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
