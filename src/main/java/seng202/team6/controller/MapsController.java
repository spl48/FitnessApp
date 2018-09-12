package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
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
        ObservableList<String> availableActivities = FXCollections.observableArrayList(testRun1.getDate().toString() + " " + testRun1.getType(), testRun2.getDate().toString() + " " + testRun2.getType());
        activitySelection.setItems(availableActivities);
        activitySelection.getSelectionModel().select(testRun1.getDate().toString() + " " + testRun1.getType());

        webEngine = mapWebView.getEngine();
        webEngine.load(MapsController.class.getResource("map.html").toExternalForm());
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
        LocalTime time7 = time6.plusMinutes(10);
        LocalTime time8 = time6.plusMinutes(10);
        LocalTime time9 = time6.plusMinutes(10);
        LocalTime time10 = time6.plusMinutes(10);
        LocalTime time11 = time6.plusMinutes(10);
        LocalTime time12 = time6.plusMinutes(10);
        LocalTime time13 = time6.plusMinutes(10);
        Activity testActivity = new Activity("Running", inputDate, time1, time6, 4.00, 80, 120);
        ActivityDataPoint p1 = new ActivityDataPoint(time1, 89, -37.661814, 176.210183, 88);
        ActivityDataPoint p2 = new ActivityDataPoint(time2, 88, -37.660642, 176.210290, 107);
        ActivityDataPoint p3 = new ActivityDataPoint(time3, 93, -37.657895, 176.206574, 19);
        ActivityDataPoint p4 = new ActivityDataPoint(time4, 120, -37.653879, 176.207636, 32);
        ActivityDataPoint p5 = new ActivityDataPoint(time5, 118, -37.633347, 176.183552, 50);
        ActivityDataPoint p6 = new ActivityDataPoint(time6, 98, -37.629820, 176.176356, 76);
        ActivityDataPoint p7 = new ActivityDataPoint(time6, 98, -37.625469, 176.173646, 76);
        ActivityDataPoint p8 = new ActivityDataPoint(time6, 98, -37.628460, 176.166950, 76);
        ActivityDataPoint p9 = new ActivityDataPoint(time6, 98, -37.635733, 176.168966, 76);
        ActivityDataPoint p10 = new ActivityDataPoint(time6, 98, -37.633796, 176.176387, 76);
        ActivityDataPoint p11 = new ActivityDataPoint(time6, 98, -37.640307, 176.182369, 76);
        ActivityDataPoint p12 = new ActivityDataPoint(time6, 98, -37.647151, 176.190807, 76);
        ActivityDataPoint p13 = new ActivityDataPoint(time6, 98, -37.661286, 176.205262, 76);
        testActivity.addActivityData(p1);
        testActivity.addActivityData(p2);
        testActivity.addActivityData(p3);
        testActivity.addActivityData(p4);
        testActivity.addActivityData(p5);
        testActivity.addActivityData(p6);
        testActivity.addActivityData(p7);
        testActivity.addActivityData(p8);
        testActivity.addActivityData(p9);
        testActivity.addActivityData(p10);
        testActivity.addActivityData(p11);
        testActivity.addActivityData(p12);
        testActivity.addActivityData(p13);
        return testActivity;
    }
}
