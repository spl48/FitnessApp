package seng202.team6.controller;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import seng202.team6.analysis.ActivityAnalysis;
import seng202.team6.analysis.ProfileAnalysis;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import seng202.team6.models.User;

public class HomeScreenController {

	int userid = ApplicationManager.getCurrentUserID();
    
    @FXML
    private ComboBox activityTypeSelection;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LineChart<Number,Number> analysisGraph;
    @FXML
    private Text BMIText;
    @FXML
    private Text weightType;
    @FXML
    private Text stepCount;

    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();
    private ArrayList<Activity> activities = new ArrayList();

	@FXML
    public void initialize() throws SQLException {
		ObservableList<String> activityDataTypes = FXCollections.observableArrayList("Heart Rate", "Distance", "Elevation", "Calories");
	    activityTypeSelection.setItems(activityDataTypes);
	    activityTypeSelection.getSelectionModel().select(activityDataTypes.get(0));


        setBMIInfo();
        setStepsInfo();
	    newGraph();
    }

    @FXML
    private void setBMIInfo() throws SQLException{
        String userName = ApplicationManager.getCurrentUsername(); //TODO put this stuff outside functiom?? -- used again later
        User user = databaseManager.getUser(userName);

        ProfileAnalysis profileAnalysis = new ProfileAnalysis();
        double BMI = profileAnalysis.calculateBMI(user);
        String BMIString = String.format("%.1f", BMI);
        BMIText.setText(BMIString);


        activities = databaseManager.getActivities(ApplicationManager.getCurrentUserID());
        weightType.setText(profileAnalysis.analyseBMI(BMI));
	    newGraph();
	    analysisGraph.setCreateSymbols(false);
    }

    @FXML
    private void setStepsInfo() throws SQLException{
        DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();
        int userId = ApplicationManager.getCurrentUserID();
        String userName = ApplicationManager.getCurrentUserName();
        User user = databaseManager.getUser(userName);
        ArrayList<Activity> activities = databaseManager.getActivities(userId);

        ProfileAnalysis profileAnalysis = new ProfileAnalysis();

        double strideLength = user.getStrideLength();

        double totalSteps = profileAnalysis.findTotalStepCount(activities, strideLength);
        String totalStepsString = String.format("%.0f", totalSteps);
        stepCount.setText(totalStepsString);
    }

    @FXML
    private void newGraph() {
        analysisGraph.getData().clear();
        try {
            addSeries();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addSeries() throws SQLException {
        //Activity testRun = makeTestRun1();
    	int lastIndex = activities.size() - 1;
    	Activity testRun = activities.get(lastIndex);
    	String seriesType = activityTypeSelection.getValue().toString();
        //defining the axes
		xAxis.setLabel("Time (Minutes)");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        //populating the series with data

        String activityDataType = activityTypeSelection.getValue().toString();
        series.setName(testRun.getDate().toString() + " " + activityDataType);
        for (ActivityDataPoint point : testRun.getActivityData()) {
        	Duration duration = Duration.between(testRun.getStartTime(), point.getTime());
            if (activityDataType == "Heart Rate") {
    	        yAxis.setLabel("Heart Rate (BPM)");
                series.getData().add(new XYChart.Data(duration.toMinutes(), point.getHeartRate()));
            } else if (activityDataType == "Distance") {
            	yAxis.setLabel("Total Distance (KM)");
            	ActivityAnalysis activityAnalysis = new ActivityAnalysis();
            	int index = testRun.getActivityData().indexOf(point);
            	double distance = activityAnalysis.findDistanceFromStart(testRun, index);
                series.getData().add(new XYChart.Data(duration.toMinutes(), distance));
            } else if (activityDataType == "Elevation") {
            	yAxis.setLabel("Elevation (M)");
            	series.getData().add(new XYChart.Data(duration.toMinutes(), point.getElevation()));
            } else if  (activityDataType == "Calories") {
                String userName = null;
                yAxis.setLabel("Calories Burned");
                ActivityAnalysis activityAnalysis = new ActivityAnalysis();
                userName = ApplicationManager.getCurrentUsername();
                double calories = activityAnalysis.findCaloriesBurnedFromStart(duration.toMinutes(), point.getHeartRate(), databaseManager.getUser(userName));
                series.getData().add(new XYChart.Data(duration.toMinutes(), calories));
            }
        }
        analysisGraph.getData().add(series);
    }
    
    private Activity makeTestRun1() {
        LocalDate inputDate = LocalDate.of(2018, 10, 9);
        LocalTime time1 = LocalTime.of(5, 30);
        LocalTime time2 = LocalTime.of(5, 40);
        LocalTime time3 = LocalTime.of(5, 45);
        LocalTime time4 = LocalTime.of(5, 55);
        LocalTime time5 = LocalTime.of(6, 10);
        LocalTime time6 = LocalTime.of(6, 15);
        Activity testActivity = new Activity(10, "Running", "test description", inputDate, inputDate, time1, time6);
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
