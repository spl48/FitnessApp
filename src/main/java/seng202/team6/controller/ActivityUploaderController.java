package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * <h1>File Uploader GUI Controller</h1>
 * <p>Initialises and applies functionality to the File Upload screen allowing the user to upload Activities</p>
 */
public class  ActivityUploaderController extends WorkoutsNavigator {

    /**
     * A Table view which holds all the top level activity data that the user uploads.
     */
    @FXML
    private TableView activityTable;

    /**
     * The ID column in the table.
     */
    @FXML
    private TableColumn idCol;

    /**
     * The description column in the table.
     */
    @FXML
    private TableColumn descriptionCol;

    /**
     * The date column in the table.
     */
    @FXML
    private TableColumn dateCol;

    /**
     * The type column in the table.
     */
    @FXML
    private TableColumn typeCol;

    /**
     * The notes column in the table.
     */
    @FXML
    private TableColumn notesCol;

    /**
     * The application database manager.
     */
    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();

    /**
     * The current selected activity.
     */
    private Activity selectedActivity;

    /**
     * The type selection box. Used when the user would like to edit the type.
     */
    @FXML
    private ChoiceBox typeSelect;

    /**
     * The notes editing area if the user would like to add or edit notes.
     */
    @FXML
    private TextArea notesEditor;


    /**
     * Sets the values of the activity types selection drop down, sets up the table display
     * and selects the first item.
     */
    @FXML
    void initialize() {
        
        //Setting up the list of options for the user to choose their activity type.
        ObservableList<String> activityTypes = FXCollections.observableArrayList("Walking", "Running", "Biking", "Other");
        typeSelect.setItems(activityTypes);
        
        // Setting up the table and populating it with activities, selecting the first item by default.
        setupTable();
        refreshActivities();
        activityTable.getSelectionModel().select(0);
        updateEditing();
    }

    
    /**
     * Binds selected attributes of Activity to selected columns in the table.
     */
    private void setupTable() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("activityid"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }

    /**
     * Adds a an activity into the table.
     * @param activity The activity instance to be added to the table.
     */
    private void addActivityToTable(Activity activity) {
        activityTable.getItems().add(activity);
    }


    /**
     * Updates the table with current data in the database.
     */
    private void refreshActivities() {
        try {
            // Gets all the current activities from the database. (Can be made more efficient with query possibly)
            ArrayList<Activity> activities = databaseManager.getActivities(ApplicationManager.getCurrentUserID());

            // Clears the old data from the table.
            for (int i = 0; i < activityTable.getItems().size(); i++) {
                activityTable.getItems().clear();
            }

            // Adds the new data to the table only if it is apart of the recently uploaded data.
            for (Activity activity : activities) {
                if (activity.getActivityid() > ApplicationManager.getCurrentActivityNumber()) {
                    addActivityToTable(activity);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Database Error", "Cannot get activities from the database.", "error");
        }
    }


    /**
     * Updates the editing fields to values relating to a selected activity.
     * Occurs when a new activity is selected from the table.
     */
    @FXML
    public void updateEditing() {
        // Selects the activity
        selectedActivity = (Activity) (activityTable.getSelectionModel().getSelectedItem());

        // Updates the type and notes editing fields.
        typeSelect.getSelectionModel().select(selectedActivity.getType());
        notesEditor.setText(selectedActivity.getNotes());
    }

    /**
     * Updates the desired activity in the database based on the entered data into edit fields.
     */
    public void updateActivity() {
        
        // Updates the activity details.
        databaseManager.updateActivityType((String) typeSelect.getSelectionModel().getSelectedItem(), selectedActivity.getActivityid());
        databaseManager.updateNotes(notesEditor.getText(), selectedActivity.getActivityid());

        // Updates the table with the new data. (Can be made more efficient by changing row only.)
        refreshActivities();
    }


    /**
     * Updates the current activity number to keep track of what was the last record to be uploaded.
     * Directs the user back to the Add Activities Splash Screen.
     * @param event When the user clicks the back arrow and wishes to exit from the upload activity checking area.
     */
    @FXML
    public void finishEditing(Event event) {
        ApplicationManager.setCurrentActivityNumber(ApplicationManager.getCurrentActivityNumber()+activityTable.getItems().size());
        toAddWorkout(event);
    }



}