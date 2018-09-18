package seng202.team6.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import seng202.team6.analysis.ActivityAnalysis;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import static java.time.temporal.ChronoUnit.MINUTES;
import javafx.scene.chart.CategoryAxis;

/**
 * <h1>Activity Analysis Controller</h1>
 * <p>Initialises and applies functionality to the Activity Analysis screen allowing the user to select existing
 * activities then view corresponding statistics and visualisations./p>
 */
public class WorkoutAnalysisController extends WorkoutsNavigator {

	private ArrayList<Activity> currentSeriesTypes = new ArrayList();
    private ArrayList<Activity> activities = new ArrayList();
    private String curSeriesType;

    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();

    @FXML
    private ChoiceBox<String> activitySelection;
    @FXML
    private ChoiceBox<String> activityTypeSelection;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LineChart<Number,Number> analysisGraph;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Heart Rate", "Distance", "Elevation", "Calories");
        activityTypeSelection.setItems(availableChoices);
        activityTypeSelection.getSelectionModel().select("Heart Rate");
        /*
        Activity testRun1 = makeTestRun1();
        Activity testRun2 = makeTestRun2();
        activities.add(testRun1);
        activities.add(testRun2);
        */
        activities = databaseManager.getActivities(ApplicationManager.getCurrentUserID());
        /*
        ObservableList<String> availableActivities = FXCollections.observableArrayList(testRun1.getDate().toString(), testRun2.getDate().toString());
        System.out.println(availableActivities);
        System.out.println(testRun1.getDate().toString().getClass().getName());
        activitySelection.getSelectionModel().select(testRun1.getDate().toString());
        */
        ObservableList<String> availableActivities = FXCollections.observableArrayList();
        for (Activity activity : activities){
            availableActivities.add(activity.getDate().toString());
        }
        activitySelection.setItems(availableActivities);
        activitySelection.getSelectionModel().select(activities.get(0).getDate().toString());
        analysisGraph.setCreateSymbols(false);
    }

    /**
     * Creates a new graph to be displayed in the chart.
     */
    public void newGraph() {
        int activity = activitySelection.getSelectionModel().getSelectedIndex();
        Activity testRun = activities.get(activity);
    	String seriesType = activityTypeSelection.getSelectionModel().getSelectedItem();
    	if (currentSeriesTypes.size() == 1 && currentSeriesTypes.get(0) == testRun && curSeriesType == seriesType) {
            ApplicationManager.displayPopUp("YA DINGUSS!", "Already displaying selected graph", "error");
        } else if (!currentSeriesTypes.contains(activity) || currentSeriesTypes.size() > 1) {
	    	currentSeriesTypes.clear();
	        analysisGraph.getData().clear();
            curSeriesType = seriesType;
            try {
                addSeries();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String errorMessage = String.format("Already displaying data for %s ya dinguss", testRun.getDate().toString());
            ApplicationManager.displayPopUp("YA DINGUSS!", errorMessage, "error");
        }
    }

    /**
     *
     * @throws SQLException
     */
    public void addSeries() throws SQLException {
        int activity = activitySelection.getSelectionModel().getSelectedIndex();
        Activity testRun = activities.get(activity);
    	String seriesType = activityTypeSelection.getSelectionModel().getSelectedItem();
    	if (!currentSeriesTypes.contains(testRun) && seriesType == curSeriesType) {
	        //defining the axes
    		xAxis.setLabel("Time");
	        //defining a series
	        XYChart.Series series = new XYChart.Series();
	        //populating the series with data

	        String ActivityType = activityTypeSelection.getSelectionModel().getSelectedItem();
	        series.setName(testRun.getDate().toString() + " " + ActivityType);
	        for (ActivityDataPoint point : testRun.getActivityData()) {
	        	Duration duration = Duration.between(testRun.getStartTime(), point.getTime());
	            if (ActivityType == "Heart Rate") {
	    	        yAxis.setLabel("Heart Rate (BPM)");
	                series.getData().add(new XYChart.Data(duration.toMinutes(), point.getHeartRate()));
	            } else if (ActivityType == "Distance") {
	            	yAxis.setLabel("Total Distance (KM)");
	            	ActivityAnalysis activityAnalysis = new ActivityAnalysis();
	            	int index = testRun.getActivityData().indexOf(point);
	            	double distance = activityAnalysis.findDistanceFromStart(testRun, index);
	                series.getData().add(new XYChart.Data(duration.toMinutes(), distance));
	            } else if (ActivityType == "Elevation") {
	            	yAxis.setLabel("Elevation (M)");
	            	series.getData().add(new XYChart.Data(duration.toMinutes(), point.getElevation()));
	            } else if  (ActivityType == "Calories") {
                    String userName = null;
                    yAxis.setLabel("Calories Burned");
                    ActivityAnalysis activityAnalysis = new ActivityAnalysis();
                    try {
                        userName = databaseManager.getUsernames().get(0);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    double calories = activityAnalysis.findCaloriesBurnedFromStart(duration.toMinutes(), point.getHeartRate(), databaseManager.getUser(userName));
                    series.getData().add(new XYChart.Data(duration.toMinutes(), calories));
	            }
	        }
	        currentSeriesTypes.add(testRun);
	        analysisGraph.getData().add(series);
    	} else {
    		ApplicationManager.displayPopUp("YA DINGUSS!", "Must compare different activities and same data type ya dinguss", "error");
    	}
    }
/*
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
    */
}

