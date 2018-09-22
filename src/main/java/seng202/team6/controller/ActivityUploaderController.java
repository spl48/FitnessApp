package seng202.team6.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * <h1>File Uploader GUI Controller</h1>
 * <p>Initialises and applies functionality to the File Upload screen allowing the user to upload Activities</p>
 */
public class  ActivityUploaderController extends WorkoutsNavigator {

    /**
     * Session/Activity type ?? Might actually be redundant since multiple activities in file.
     */
    @FXML
    private TableView activityTable;

    @FXML
    private TableColumn idCol;

    @FXML
    private TableColumn descriptionCol;

    @FXML
    private TableColumn dateCol;

    @FXML
    private TableColumn typeCol;

    @FXML
    private TableColumn notesCol;

    /**
     * The application database manager.
     */
    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();

    private DatabaseManager dbManager = ApplicationManager.getDatabaseManager();

    /**
     * The current user which is signed in.
     */
    private User currUser;

    private Activity selectedActivity;

    private ArrayList<Activity> activities;


    @FXML
    private ChoiceBox typeSelect;

    @FXML
    private TextArea notesEditor;


    /**
     * Initialising the current user and the activity type drop down.
     * @throws SQLException Error when getting user from the database.
     */
    @FXML
    void initialize() throws SQLException {
        currUser = databaseManager.getUser(ApplicationManager.getCurrentUsername());
        ObservableList<String> activityTypes = FXCollections.observableArrayList("Walking", "Running", "Biking", "Other");
        typeSelect.setItems(activityTypes);
        setupTable();
        refreshActivities();
        activityTable.getSelectionModel().select(0);
    }

    private void setupTable() {
//
//        activityTable.setEditable(true);
//        Callback<TableColumn<Activity, String>, TableCell<Activity, String>> cellFactory
//                = (TableColumn<Activity, String> param) -> new EditingCell();
//        Callback<TableColumn<Activity, Typ>, TableCell<Activity, Typ>> comboBoxCellFactory
//                = (TableColumn<Activity, Typ> param) -> new ComboBoxEditingCell();

        idCol.setCellValueFactory(new PropertyValueFactory<>("activityid"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }

    public void addRecordToTable(Activity activity) {
        activityTable.getItems().add(activity);
    }

    public void refreshActivities() throws SQLException {
        activities = dbManager.getActivities(ApplicationManager.getCurrentUserID());
        for ( int i = 0; i<activityTable.getItems().size(); i++) {
            activityTable.getItems().clear();
        }

        for (Activity activity : activities) {
            if (activity.getActivityid() > ApplicationManager.getCurrentActivityNumber()) {
                addRecordToTable(activity);
            }

        }
    }

    public void updateEditing() {
        selectedActivity = (Activity) (activityTable.getSelectionModel().getSelectedItem());
        typeSelect.getSelectionModel().select(selectedActivity.getType());
        notesEditor.setText(selectedActivity.getNotes());
    }

    public void updateActivity() throws  SQLException {
        System.out.println((String) typeSelect.getSelectionModel().getSelectedItem());
        System.out.println(selectedActivity.getActivityid());
        databaseManager.updateType((String) typeSelect.getSelectionModel().getSelectedItem(), selectedActivity.getActivityid());
        databaseManager.updateNotes(notesEditor.getText(), selectedActivity.getActivityid());
        refreshActivities();
    }

    @FXML
    public void finishEditing(Event event) {
        ApplicationManager.setCurrentActivityNumber(ApplicationManager.getCurrentActivityNumber()+activityTable.getItems().size());
        System.out.println(ApplicationManager.getCurrentActivityNumber());
        toWorkoutsScreen(event);
    }


    /**
     * Directs the user back to the add workout screen.
     * @param event When the user clicks the back button.
     */
    @FXML
    public void toAddWorkout(Event event) {
        changeScreen(event, "/seng202/team6/view/AddWorkout.fxml");
    }


}