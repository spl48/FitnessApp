package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.ActivityDataPoint;
import seng202.team6.models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class RawDataController extends WorkoutsNavigator{

    @FXML
    private TableView rawDataTable;

    @FXML
    private ChoiceBox activitySelect;

    private DatabaseManager dbManager = ApplicationManager.getDatabaseManager();

    public void initialize() throws SQLException {
        User currUser = dbManager.getUser(ApplicationManager.getCurrentUsername());
        ObservableList<Integer> activityList = FXCollections.observableArrayList(dbManager.getActivityIDs(currUser.getUserID()));
        activitySelect.setItems(activityList);
        setupTable();
    }

    private void setupTable() {
        TableColumn timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn heartRateCol = new TableColumn("Heart Rate");
        heartRateCol.setCellValueFactory(new PropertyValueFactory<>("heartRate"));

        TableColumn latitudeCol = new TableColumn("Latitude");
        latitudeCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));

        TableColumn longitudeCol = new TableColumn("Longitude");
        longitudeCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));

        TableColumn elevationCol = new TableColumn("Elevation");
        elevationCol.setCellValueFactory(new PropertyValueFactory<>("elevation"));


        rawDataTable.getColumns().addAll(timeCol, heartRateCol, latitudeCol, longitudeCol, elevationCol);
    }

    public void addRecordToTable(ActivityDataPoint record) throws SQLException {
        rawDataTable.getItems().add(record);
    }

    public void showActivity() throws SQLException {
        ArrayList<ActivityDataPoint> records = dbManager.getActivityRecords((int) activitySelect.getValue());
        for ( int i = 0; i<rawDataTable.getItems().size(); i++) {
            rawDataTable.getItems().clear();
        }

        for (ActivityDataPoint record : records) {
            addRecordToTable(record);
        }
    }
}
