package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import seng202.team6.models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class RawDataController extends WorkoutsNavigator{

    @FXML
    private TableView rawDataTable;

    @FXML
    private ChoiceBox activitySelect;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label notesLabel;

    @FXML
    private Label dateLabel;

    private DatabaseManager dbManager = ApplicationManager.getDatabaseManager();

    public void initialize() throws SQLException {
        User currUser = dbManager.getUser(ApplicationManager.getCurrentUsername());
        ObservableList<Integer> activityList = FXCollections.observableArrayList(dbManager.getActivityIDs(currUser.getUserID()));
        activitySelect.setItems(activityList);
        setupTable();
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
        ArrayList<ActivityDataPoint> records = dbManager.getActivityRecords(1);
        for ( int i = 0; i<rawDataTable.getItems().size(); i++) {
            rawDataTable.getItems().clear();
        }

        for (ActivityDataPoint record : records) {
            addRecordToTable(record);
        }

        descriptionLabel.setText(dbManager.getActivity((int) activitySelect.getValue()).getDescription());
        dateLabel.setText(dbManager.getActivity((int) activitySelect.getValue()).getStartDate().toString());
        typeLabel.setText(dbManager.getActivity((int) activitySelect.getValue()).getType());
        notesLabel.setText(dbManager.getActivity((int) activitySelect.getValue()).getNotes());

    }
}
