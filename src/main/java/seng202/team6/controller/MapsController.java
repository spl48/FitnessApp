package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team6.analysis.Position;
import seng202.team6.analysis.Route;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MapsController extends WorkoutsNavigator {

    @FXML
    private WebView mapWebView;

    @FXML
    private ChoiceBox<String> activitySelection;

    private WebEngine webEngine;
    private ArrayList<Activity> activities = new ArrayList();

    @FXML
    public void initialize() {
        Activity testRun1 = makeTestRun1();
        Activity testRun2 = makeTestRun2();
        activities.add(testRun1);
        activities.add(testRun2);
        ObservableList<String> availableActivities = FXCollections.observableArrayList(testRun1.getDate().toString(), testRun2.getDate().toString());
        activitySelection.setItems(availableActivities);
        activitySelection.getSelectionModel().select(testRun1.getDate().toString());
    }

    private Route makeRoute(Activity activity) {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (ActivityDataPoint point : activity.getActivityData()) {
            double lat = point.getLatitude();
            double lng = point.getLongitude();
            Position p = new Position(lat, lng);
            positions.add(p);
        }
        Route route = new Route(positions);
        return route;
    }


    @FXML
    private void initMap() {
        webEngine = mapWebView.getEngine();
        webEngine.load(MapsController.class.getResource("map.html").toExternalForm());
        int activity = activitySelection.getSelectionModel().getSelectedIndex();
        Activity desiredActivity = activities.get(activity);
        Route route = makeRoute(desiredActivity);
        displayRoute(route);

    }

    private void displayRoute(Route newRoute) {
        String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
        webEngine.executeScript(scriptToExecute);
    }

    private Activity makeTestRun1() {
        LocalDate inputDate = LocalDate.of(2018, 10, 9);
        LocalTime time1 = LocalTime.of(5, 30);
        LocalTime time2 = LocalTime.of(5, 40);
        LocalTime time3 = LocalTime.of(5, 45);
        LocalTime time4 = LocalTime.of(5, 55);
        LocalTime time5 = LocalTime.of(6, 10);
        LocalTime time6 = LocalTime.of(6, 15);
        Activity testActivity = new Activity("Running", inputDate, time1, time6, 4.00, 80, 120);
        ActivityDataPoint p1 = new ActivityDataPoint(time1, 85, -43.530029, 172.582520, 88);
        ActivityDataPoint p2 = new ActivityDataPoint(time2, 120, -43.523584, 172.579179, 100);
        ActivityDataPoint p3 = new ActivityDataPoint(time3, 111, -43.519975, 172.579222, 94);
        ActivityDataPoint p4 = new ActivityDataPoint(time4, 104, -43.522371, 172.589474, 88);
        ActivityDataPoint p5 = new ActivityDataPoint(time5, 101, -43.530834, 172.586771, 88);
        ActivityDataPoint p6 = new ActivityDataPoint(time6, 98, -43.530029, 172.582520, 92);
        testActivity.addActivityData(p1);
        testActivity.addActivityData(p2);
        testActivity.addActivityData(p3);
        testActivity.addActivityData(p4);
        testActivity.addActivityData(p5);
        testActivity.addActivityData(p6);
        return testActivity;
    }

    private Activity makeTestRun2() {
        LocalDate inputDate = LocalDate.of(2018, 10, 12);
        LocalTime time1 = LocalTime.of(12, 30);
        LocalTime time2 = LocalTime.of(12, 35);
        LocalTime time3 = LocalTime.of(12, 55);
        LocalTime time4 = LocalTime.of(13, 8);
        LocalTime time5 = LocalTime.of(13, 17);
        LocalTime time6 = LocalTime.of(13, 36);
        Activity testActivity = new Activity("Running", inputDate, time1, time6, 4.00, 80, 120);
        ActivityDataPoint p1 = new ActivityDataPoint(time1, 89, -43.530029, 172.582520, 88);
        ActivityDataPoint p2 = new ActivityDataPoint(time2, 88, -43.523584, 172.579179, 107);
        ActivityDataPoint p3 = new ActivityDataPoint(time3, 93, -43.519975, 172.579222, 19);
        ActivityDataPoint p4 = new ActivityDataPoint(time4, 120, -43.522371, 172.589474, 32);
        ActivityDataPoint p5 = new ActivityDataPoint(time5, 118, -43.530834, 172.586771, 50);
        ActivityDataPoint p6 = new ActivityDataPoint(time6, 98, -43.530029, 172.582520, 76);
        testActivity.addActivityData(p1);
        testActivity.addActivityData(p2);
        testActivity.addActivityData(p3);
        testActivity.addActivityData(p4);
        testActivity.addActivityData(p5);
        testActivity.addActivityData(p6);
        return testActivity;
    }
}
