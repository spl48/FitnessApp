package seng202.team6.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.Callback;
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

    public static class Typ {

        private final SimpleStringProperty typ;

        public Typ(String typ) {
            this.typ = new SimpleStringProperty(typ);
        }

        public String getTyp() {
            return this.typ.get();
        }

        public StringProperty typProperty() {
            return this.typ;
        }

        public void setTyp(String typ) {
            this.typ.set(typ);
        }

        @Override
        public String toString() {
            return typ.get();
        }

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
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }

    public void addRecordToTable(Activity activity) {
        activityTable.getItems().add(activity);
    }

    public void showActivity() throws SQLException {
        ArrayList<Activity> activities = dbManager.getActivities(ApplicationManager.getCurrentUserID());
        for ( int i = 0; i<activityTable.getItems().size(); i++) {
            activityTable.getItems().clear();
        }

        for (Activity activity : activities) {
            System.out.println("Activity Name: " + activity.getDescription());
            addRecordToTable(activity);
        }


    }

    public void editNotes(TableColumn.CellEditEvent editedCell) {
        Activity activitySelected = (Activity) activityTable.getSelectionModel().getSelectedItem();

        activitySelected.setNotes(editedCell.getNewValue().toString());
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