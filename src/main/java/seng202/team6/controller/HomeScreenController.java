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
import seng202.team6.analysis.HealthConcernChecker;
import seng202.team6.analysis.ProfileAnalysis;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import seng202.team6.models.User;

public class HomeScreenController {

	int userid = ApplicationManager.getCurrentUserID();
    
	/**
     * A choice box to select the data type to be displayed on graph. E.g "Heart rate", "Distance", etc.
     */
    @FXML
    private ComboBox activityTypeSelection;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    /**
     * The line chart that displays the activity data
     */
    @FXML
    private LineChart<Number,Number> analysisGraph;
    @FXML
    private Text graphPanelTitle;
    @FXML
    private Text noDataText;
    @FXML
    private Text BMIText;
    @FXML
    private Text weightType;
    @FXML
    private Text stepCount;
    @FXML
    private Text healthConcernsText;

    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();
    /**
	 * Array that has all the activities the user can select to display on the graph
	 */
    private ArrayList<Activity> activities = new ArrayList();

	@FXML
    public void initialize() throws SQLException {
		ObservableList<String> activityDataTypes = FXCollections.observableArrayList("Distance", "Heart Rate", "Elevation", "Calories");
	    activityTypeSelection.setItems(activityDataTypes);
	    activityTypeSelection.getSelectionModel().select(activityDataTypes.get(0));


        setHealthInfo();
        setStepsInfo();
	    newGraph();
    }

    @FXML
    private void setHealthInfo() throws SQLException{

        String userName = ApplicationManager.getCurrentUsername(); //TODO put this stuff outside functiom?? -- used again later
        User user = databaseManager.getUser(userName);
        String healthConcerns = "";

        ProfileAnalysis profileAnalysis = new ProfileAnalysis();
        double BMI = profileAnalysis.calculateBMI(user);
        String BMIString = String.format("%.1f", BMI);
        BMIText.setText(BMIString);


        activities = databaseManager.getActivities(ApplicationManager.getCurrentUserID());
        weightType.setText(profileAnalysis.analyseBMI(BMI));
	    newGraph();
	    analysisGraph.setCreateSymbols(false);


        if (HealthConcernChecker.checkTachycardia()) {
            healthConcerns += "Tachycardia\n";
            healthConcernsText.setText(healthConcerns);
        }

        if (HealthConcernChecker.checkBradycardia()) {
            healthConcerns += "Bradycardia\n";
            healthConcernsText.setText(healthConcerns);
        }

        if (HealthConcernChecker.checkCardiovascularMortality()) {
            healthConcerns += "Cardiac Diseases\n";
            healthConcernsText.setText(healthConcerns);
        }
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

    /**
     * Creates a new graph to be displayed in the chart.
     */
    @FXML
    private void newGraph() {
        analysisGraph.getData().clear();
        if (activities.size() >= 1) {
        	analysisGraph.setVisible(true);
        	activityTypeSelection.setVisible(true);
        	graphPanelTitle.setVisible(true);
        	noDataText.setVisible(false);
	        try {
	            addSeries();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
        } else {
        	analysisGraph.setVisible(false);
        	activityTypeSelection.setVisible(false);
        	graphPanelTitle.setVisible(false);
        	noDataText.setVisible(true);
        }
    }
    /**
     *Adds a series of data from the most recent activity to the chart
     * @throws SQLException
     */
    public void addSeries() throws SQLException {
        //Activity selectedActivity = makeselectedActivity1();
    	int lastIndex = activities.size() - 1;
    	Activity selectedActivity = activities.get(lastIndex);
    	String seriesType = activityTypeSelection.getValue().toString();
        //defining the axes
		xAxis.setLabel("Time (Minutes)");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        //populating the series with data

        String activityDataType = activityTypeSelection.getValue().toString();
        series.setName(selectedActivity.getStartDate().toString() + " " + activityDataType);
        for (ActivityDataPoint point : selectedActivity.getActivityData()) {
        	Duration duration = Duration.between(selectedActivity.getStartTime(), point.getTime());
        	double time = duration.toMillis() / 6000;
        	time = time / 10;
            if (activityDataType == "Heart Rate") {
    	        yAxis.setLabel("Heart Rate (BPM)");
                series.getData().add(new XYChart.Data(time, point.getHeartRate()));
            } else if (activityDataType == "Distance") {
            	yAxis.setLabel("Total Distance (KM)");
            	ActivityAnalysis activityAnalysis = new ActivityAnalysis();
            	int index = selectedActivity.getActivityData().indexOf(point);
            	double distance = activityAnalysis.findDistanceFromStart(selectedActivity, index);
                series.getData().add(new XYChart.Data(time, distance));
            } else if (activityDataType == "Elevation") {
            	yAxis.setLabel("Elevation (M)");
            	series.getData().add(new XYChart.Data(time, point.getElevation()));
            } else if  (activityDataType == "Calories") {
                String userName = null;
                yAxis.setLabel("Calories Burned");
                ActivityAnalysis activityAnalysis = new ActivityAnalysis();
                userName = ApplicationManager.getCurrentUsername();
                double calories = activityAnalysis.findCaloriesBurnedFromStart(time, point.getHeartRate(), databaseManager.getUser(userName));
                series.getData().add(new XYChart.Data(time, calories));
            }
        }
        analysisGraph.getData().add(series);
    }
}
