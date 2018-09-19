package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.datahandling.FileDataLoader;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import seng202.team6.models.User;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * <h1>File Uploader GUI Controller</h1>
 * <p>Initialises and applies functionality to the File Upload screen allowing the user to upload Activities</p>
 */
public class ActivityUploaderController extends WorkoutsNavigator {

    /**
     * Session/Activity type ?? Might actually be redundant since multiple activities in file.
     */
    @FXML
    private TableView activityTable;

    /**
     * The application database manager.
     */
    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();

    private DatabaseManager dbManager = ApplicationManager.getDatabaseManager();

    /**
     * The current user which is signed in.
     */
    private User currUser;

    /**
     * Initialising the current user and the activity type drop down.
     * @throws SQLException Error when getting user from the database.
     */
    @FXML
    void initialize() throws SQLException {
        currUser = databaseManager.getUser(ApplicationManager.getCurrentUsername());
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Walking", "Running", "Biking");
        setupTable();
        showActivity();
    }

    private void setupTable() {
        TableColumn idCol = new TableColumn("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("activityid"));

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn typeCol = new TableColumn("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn notesCol = new TableColumn("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));


        activityTable.getColumns().addAll(idCol, descriptionCol, dateCol, typeCol, notesCol);
    }

    public void addRecordToTable(Activity activity) throws SQLException {
        activityTable.getItems().add(activity);
    }

    public void showActivity() throws SQLException {
        ArrayList<Activity> activities = dbManager.getActivities(ApplicationManager.getCurrentUserID());
        for ( int i = 0; i<activityTable.getItems().size(); i++) {
            activityTable.getItems().clear();
        }

        for (Activity activity : activities) {
            addRecordToTable(activity);
        }


    }


    public void uploadActivity() {

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