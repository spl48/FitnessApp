package seng202.team6.view;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import javafx.scene.chart.CategoryAxis;

public class WorkoutAnalysisController {

    private Activity makeTestRun() {
        LocalDate inputDate = LocalDate.of(2018, 10, 9);
        LocalTime time1 = LocalTime.of(5, 30);
        LocalTime time2 = LocalTime.of(6, 00);
        LocalTime time3 = LocalTime.of(6, 30);
        Activity testActivity = new Activity("Running", inputDate, time1, time3, 4.00, 80, 120);
        ActivityDataPoint p1 = new ActivityDataPoint(time1, 85, -41.245120, 174.771260, 88);
        ActivityDataPoint p2 = new ActivityDataPoint(time2, 120, -32.244893, 148.608375, 100);
        ActivityDataPoint p3 = new ActivityDataPoint(time3, 111, -32.555990, 148.944830, 94);
        testActivity.addActivityData(p1);
        testActivity.addActivityData(p2);
        testActivity.addActivityData(p3);
        return testActivity;
    }

    @FXML
    private ChoiceBox<String> activityTypeSelection;

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("lem72", "rch141", "gon12", "dla72", "spl8");
        activityTypeSelection.setItems(availableChoices);
    }

}
