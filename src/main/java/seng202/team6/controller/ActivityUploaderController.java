package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.User;
import seng202.team6.utilities.ActivityValidation;
import seng202.team6.utilities.DatabaseValidation;

import java.sql.SQLException;
import java.util.ArrayList;

import static seng202.team6.models.Goal.distanceAchieved;
import static seng202.team6.models.Goal.stepsAchieved;

/**
 * <h1>File Uploader GUI Controller</h1>
 * <p>Initialises and applies functionality to the file upload editing screen allowing the user to update the type of
 * their activities and add any desired notes.</p>
 */
public class  ActivityUploaderController extends WorkoutsNavigator {

    /**
     * A Table view which holds all the raw level activity data that the user uploads.
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
     * The current user which is logged in.
     */
    private User user;


    /**
     * Sets the values of the activity types selection drop down, sets up the table display
     * and selects the first item.
     */
    @FXML
    void initialize() throws SQLException {
        
        //Setting up the list of options for the user to choose their activity type.
        ObservableList<String> activityTypes = FXCollections.observableArrayList("Walking", "Running", "Biking", "Other");
        typeSelect.setItems(activityTypes);
        
        // Setting up the table and populating it with activities, selecting the first item by default.
        setupTable();
        refreshActivities();
        activityTable.getSelectionModel().select(0);
        updateEditing();

        // Gets the current instance data
        databaseManager = ApplicationManager.getDatabaseManager();
        String userName = ApplicationManager.getCurrentUsername();
        user = databaseManager.getUserReader().getUser(userName);
    }

    
    /**
     * Binds selected attributes of an Activity Record to selected columns in the table.
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
            // Gets all the current activities from the database.
            ArrayList<Activity> activities = databaseManager.getActivityManager().getActivities(ApplicationManager.getCurrentUserID());

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

        if (ActivityValidation.validateNotes(notesEditor.getText())) {

            // Updates the activity details.
            databaseManager.getActivityManager().updateActivityType((String) typeSelect.getSelectionModel().getSelectedItem(), selectedActivity.getActivityid());
            databaseManager.getActivityManager().updateNotes(notesEditor.getText(), selectedActivity.getActivityid());

            // Updates the table with the new data. (Can be made more efficient by changing row only.)
            refreshActivities();
        }
    }


    /**
     * Updates the current activity number to keep track of what was the last record to be uploaded.
     * Directs the user back to the Add Activities Splash Screen.
     * @param event When the user clicks the back arrow and wishes to exit from the upload activity checking area.
     */
    @FXML
    public void toAddWorkout(Event event) {

        // Updates the current activity number and executes the super class version of to add workout screen.
        super.toAddWorkout(event);
    }

    /**
     * Checks if the user has achieved any weekly goals with the new file upload and displays a pop up if they have saying the goal has been achieved.
     * If not will display a random progress pop up to say how many steps or kilometers to achieve a weekly goal.
     * @param event
     */
    @FXML
    private void doneEditing(Event event) {
        // Checks if the user has achieved their step goal, if so displays a congratulatory message.
        if (stepsAchieved(user)) {
            int stepGoal = user.getStepGoal();
            String stepGoalString = Integer.toString(stepGoal) + " steps";
            ApplicationManager.displayPopUp("Congratulations", "You have achieved your weekly step goal of " + stepGoalString + "!" , "confirmation");
        }

        // Checks if the user has achieved their distance goal and if so displays a congratulatory message. Otherwise there is a chance to display a random progress report.
        if (distanceAchieved(user)) {
            int distanceGoal = user.getDistanceGoal();
            String distanceGoalString = Integer.toString(distanceGoal) + " kilometers";
            ApplicationManager.displayPopUp("Congratulations", "You have achieved your weekly step goal of " + distanceGoalString + "!" , "confirmation");
        }

        // If no weekly goal has been achieved, display a random progress pop up
        if (!stepsAchieved(user) & !distanceAchieved(user)) {
            displayRandomProgressReport();
        }
        ApplicationManager.setCurrentActivityNumber(ApplicationManager.getCurrentActivityNumber()+activityTable.getItems().size());
        super.toAddWorkout(event);
    }


    /**
     * Displays a random progress report based on the user's updated goal once updating their activity goals.
     */
    private void displayRandomProgressReport() {

        // Gets a random double to act as the probability.
        double ran = Math.random();

        // Displays a goal or distance motivation pop up with a chance of 50%.
        if (ran <= 1) {
            int stepGoal = user.getStepGoal();
            double totalSteps = ApplicationManager.getDatabaseManager().getActivityManager().getUpdatedStepGoal(ApplicationManager.getCurrentUserID());
            double stepsLeft = stepGoal - totalSteps;
            String stepsLeftString = String.format("%.0f Steps", stepsLeft);
            ApplicationManager.displayPopUp("Congratulations", "You only have " + stepsLeftString + " until you reach your goal steps!", "confirmation");
        } else {
            int distanceGoal = user.getDistanceGoal();
            double totalDistance = ApplicationManager.getDatabaseManager().getActivityManager().getUpdatedDistanceGoal(ApplicationManager.getCurrentUserID());
            double distanceLeft = distanceGoal - totalDistance;
            String distanceLeftString = String.format("%.0f Kilometers", distanceLeft);
            ApplicationManager.displayPopUp("Congratulations", "You only have " + distanceLeftString + " until you reach your goal distance!" , "confirmation");
        }
    }


}