package seng202.team6.controller;

import java.io.IOException;
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
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import static java.time.temporal.ChronoUnit.MINUTES;
import javafx.scene.chart.CategoryAxis;

public class WorkoutAnalysisController {

	private ArrayList<String> currentSeriesTypes = new ArrayList();

    @FXML
    private ChoiceBox<String> activityTypeSelection;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LineChart<Number,Number> analysisGraph;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Heart Rate", "Distance", "Elevation");
        activityTypeSelection.setItems(availableChoices);
        activityTypeSelection.getSelectionModel().select("Heart Rate");
    }

    public void changeScreen(Event event, String screen) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource(screen));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }

    @FXML
    public void toWorkoutsScreen(Event event) throws IOException {
        changeScreen(event, "WorkoutsScreenSplash.fxml");
    }

    public void newGraph() {
    	String seriesType = activityTypeSelection.getSelectionModel().getSelectedItem();
    	if (!currentSeriesTypes.contains(seriesType) || currentSeriesTypes.size() > 1) {
	    	currentSeriesTypes.clear();
	        analysisGraph.getData().clear();
	        addSeries();
    	}
    }

    public void addSeries() {
    	String seriesType = activityTypeSelection.getSelectionModel().getSelectedItem();
    	if (!currentSeriesTypes.contains(seriesType)) {
	        //defining the axes
    		xAxis.setLabel("Time");


	        //analysisGraph.setTitle("Heart Rate");
	        //defining a series
	        XYChart.Series series = new XYChart.Series();
	        //populating the series with data
	        Activity testRun = makeTestRun();

	        String ActivityType = activityTypeSelection.getSelectionModel().getSelectedItem();
	        series.setName(ActivityType);
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
	            }
	        }
	        currentSeriesTypes.add(seriesType);
	        analysisGraph.getData().add(series);
    	} else {
    		System.out.println("Already on graph");
    	}
    }

    private Activity makeTestRun() {
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
}

