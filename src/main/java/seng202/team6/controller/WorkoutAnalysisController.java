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

	/**
	 * Array that stores the Activity's that are currently being displayed in the graph
	 */
	private ArrayList<Activity> currentSeriesTypes = new ArrayList();
	/**
	 * Array that has all the activities the user can select to display on the graph
	 */
    private ArrayList<Activity> activities = new ArrayList();
    /**
     * A string of the current data type being displayed on the graph. E.g "Heart rate", "Distance", etc.
     */
    private String curSeriesType;

    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();

    /**
     * A choice box to select the desired activity to be displayed on graph
     */
    @FXML
    private ChoiceBox<String> activitySelection;
    /**
     * A choice box to select the data type to be displayed on graph. E.g "Heart rate", "Distance", etc.
     */
    @FXML
    private ChoiceBox<String> activityTypeSelection;
    /**
     * x axis of chart
     */
    @FXML
    private NumberAxis xAxis;
    /**
     * y axis of graph
     */
    @FXML
    private NumberAxis yAxis;
    /**
     * The line chart that displays the activity data
     */
    @FXML
    private LineChart<Number,Number> analysisGraph;

    /**
     * Initializes chart to display latest activity.
     * @throws SQLException
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Heart Rate", "Distance", "Elevation", "Calories");
        activityTypeSelection.setItems(availableChoices);
        activityTypeSelection.getSelectionModel().select("Heart Rate");
        activities = databaseManager.getActivities(ApplicationManager.getCurrentUserID());
        ObservableList<String> availableActivities = FXCollections.observableArrayList();
        for (Activity activity : activities){
            availableActivities.add(activity.getDate().toString());
        }
        activitySelection.setItems(availableActivities);
        if (activities.size() >= 1) {
        	activitySelection.getSelectionModel().select(activities.get(0).getDate().toString());
        }
        analysisGraph.setCreateSymbols(false);
    }

    /**
     * Creates a new graph to be displayed in the chart.
     */
    public void newGraph() {
    	if (activities.size() >= 1) {
	        int activity = activitySelection.getSelectionModel().getSelectedIndex();
	        Activity selectedActivity = activities.get(activity);
	    	String seriesType = activityTypeSelection.getSelectionModel().getSelectedItem();
	    	if (currentSeriesTypes.size() == 1 && currentSeriesTypes.get(0) == selectedActivity && curSeriesType == seriesType) {
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
	            String errorMessage = String.format("Already displaying data for %s ya dinguss", selectedActivity.getDate().toString());
	            ApplicationManager.displayPopUp("YA DINGUSS!", errorMessage, "error");
	        }
    	} else {
    		ApplicationManager.displayPopUp("YA DINGUSS!", "You have no uploaded activity data.\nGo to workouts to upload your activities.", "error");
    	}
    }

    /**
     *Adds a series of data to the chart
     * @throws SQLException
     */
    public void addSeries() throws SQLException {
    	if (activities.size() >= 1) {
	        int activity = activitySelection.getSelectionModel().getSelectedIndex();
	        Activity selectedActivity = activities.get(activity);
	    	String seriesType = activityTypeSelection.getSelectionModel().getSelectedItem();
	    	if (!currentSeriesTypes.contains(selectedActivity) && seriesType == curSeriesType) {
		        //defining the axes
	    		xAxis.setLabel("Time");
		        //populating the series with data
		        addData(selectedActivity);
		        
	    	} else {
	    		ApplicationManager.displayPopUp("YA DINGUSS!", "Must compare different activities and same data type ya dinguss", "error");
	    	}
    	} else {
    		ApplicationManager.displayPopUp("YA DINGUSS!", "You have no uploaded activity data.\nGo to workouts to upload your activities.", "error");
    	}
    }
    
    /**
     * Adds the required data to the current series to be displayed on the chart.
     * @param selectedActivity The activity whose data will be added to the chart
     * @throws SQLException
     */
    public void addData(Activity selectedActivity) throws SQLException {
    	//defining a series
        XYChart.Series series = new XYChart.Series();
    	String ActivityType = activityTypeSelection.getSelectionModel().getSelectedItem();
        series.setName(selectedActivity.getDate().toString() + " " + ActivityType);
    	for (ActivityDataPoint point : selectedActivity.getActivityData()) {
        	Duration duration = Duration.between(selectedActivity.getStartTime(), point.getTime());
        	double time = duration.toMillis() / 6000;
        	time = time / 10;
            if (ActivityType == "Heart Rate") {
    	        yAxis.setLabel("Heart Rate (BPM)");
                series.getData().add(new XYChart.Data(time, point.getHeartRate()));
            } else if (ActivityType == "Distance") {
            	yAxis.setLabel("Total Distance (KM)");
            	ActivityAnalysis activityAnalysis = new ActivityAnalysis();
            	int index = selectedActivity.getActivityData().indexOf(point);
            	double distance = activityAnalysis.findDistanceFromStart(selectedActivity, index);
                series.getData().add(new XYChart.Data(time, distance));
            } else if (ActivityType == "Elevation") {
            	yAxis.setLabel("Elevation (M)");
            	series.getData().add(new XYChart.Data(time, point.getElevation()));
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
                series.getData().add(new XYChart.Data(time, calories));
            }
        }
    	currentSeriesTypes.add(selectedActivity);
        analysisGraph.getData().add(series);
    }
}


