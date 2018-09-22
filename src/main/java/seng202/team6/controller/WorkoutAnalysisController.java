package seng202.team6.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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
import javafx.scene.control.*;
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
	 * Combobox to select year to filter activities by
	 */
	@FXML
    private ComboBox yearSelection;
	/**
	 * Combobox to select month to filter activities by
	 */
    @FXML
    private ComboBox monthSelection;
	/**
	 * Combobox to select day to filter activities by
	 */
    @FXML
    private ComboBox daySelection;

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
	/**
	 * The set of all years the user has activity data for
	 */
    private Set<String> yearSet = new HashSet<>();
	/**
	 * List of days with 31 days as strings
	 */
	ArrayList<String> thirtyOneDayMonths = new ArrayList<>(Arrays.asList("January", "March", "May", "July", "August", "October", "December"));
	/**
	 * List of days wih 30 days as strings
	 */
	ArrayList<String> thirtyDayMonths = new ArrayList<>(Arrays.asList("April","June", "September", "November"));
	/**
	 * List of all months as strings
	 */
	ObservableList<String> monthChoices = FXCollections.observableArrayList("All", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
	/**
	 * List of days in a month as Strings up to day 29
	 */
	ObservableList<String> dayChoices = FXCollections.observableArrayList("All", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29");
	/**
	 * List of possible data types to be displayed on the graph
	 */
	ObservableList<String> dataChoices = FXCollections.observableArrayList("Distance", "Heart Rate", "Elevation", "Calories");


	/**
     * Initializes chart to display latest activity.
     * @throws SQLException
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException {
        activityTypeSelection.setItems(dataChoices);
        activityTypeSelection.getSelectionModel().select(0);
        activities = databaseManager.getActivities(ApplicationManager.getCurrentUserID());
        ObservableList<String> availableActivities = FXCollections.observableArrayList();
        for (Activity activity : activities) { // Add all activities to available activities initially
            availableActivities.add(activity.getStartDate().toString() + " " + activity.getDescription());
        }
        activitySelection.setItems(availableActivities);
        if (activities.size() >= 1) {
        	populateComboBoxes();
        	activitySelection.getSelectionModel().select(0);
        }
        analysisGraph.setCreateSymbols(false);
		monthSelection.setDisable(true);
		daySelection.setDisable(true);
    }

	/**
	 * Populates the year combobox with the yearSet data, month combobox with all months in year, and day combobox with all days in the month
	 */
    private void populateComboBoxes() {
    	yearSet.add("All");
    	for (Activity activity : activities) {
    		LocalDate date = activity.getStartDate();
    		yearSet.add(Integer.toString(date.getYear()));
    	}
    	monthSelection.getSelectionModel().select(monthChoices.get(0));
    	daySelection.getSelectionModel().select(dayChoices.get(0));
    	ObservableList<Integer> yearOptions = FXCollections.observableArrayList();
    	List yearList = new ArrayList(yearSet);
    	yearOptions.addAll(yearList);
    	yearSelection.getSelectionModel().select(yearOptions.get(0));
    	yearSelection.getItems().setAll(yearOptions);
    	monthSelection.setItems(monthChoices);
    	daySelection.setItems(dayChoices);
    }

	/**
	 * Updates the activity selector combobox to display only the activities that meet the seleced criteria
	 */
	@FXML
    private void updateActivityComboBox() {
		int selectedYearInt = 0;
		int selectedDayInt = 0;
		dayChoices = FXCollections.observableArrayList("All", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29");
    	ObservableList<String> availableActivities = FXCollections.observableArrayList();
    	String selectedYear = yearSelection.getValue().toString();
    	String selectedMonth = monthSelection.getValue().toString();
    	String selectedDay = daySelection.getValue().toString();
		if (!selectedYear.equals("All")) {
			selectedYearInt = Integer.parseInt(selectedYear); //Turns string of chosen year into an int
		}
		if (!selectedDay.equals("All")) {
			selectedDayInt = Integer.parseInt(selectedDay); //Turns string of chosen day into an int
		}
		//Goes through all activities of the user and adds the ones that match the filtering criteria to the available activities list
    	for (Activity activity : activities) {
    		LocalDate activityDate = activity.getStartDate();
			activitySelection.setDisable(false);
    		if (selectedYear.equals("All")) { //Display all activities
				availableActivities.add(activity.getStartDate().toString() + " " + activity.getDescription());
				monthSelection.getSelectionModel().select(0);
				monthSelection.setDisable(true);
				daySelection.getSelectionModel().select(0);
				daySelection.setDisable(true);
			} else if (selectedYearInt == activityDate.getYear() && selectedMonth.equals("All")) { //display all activities in given year
				availableActivities.add(activityDate.toString() + " " + activity.getDescription());
				daySelection.getSelectionModel().select(0);
				daySelection.setDisable(true);
				monthSelection.setDisable(false);
			} else if (selectedYearInt == activityDate.getYear() && selectedMonth.equalsIgnoreCase(activityDate.getMonth().toString()) && selectedDay.equals("All")) { //display all activities in given year and month
				availableActivities.add(activityDate.toString() + " " + activity.getDescription());
				daySelection.setDisable(false);
			} else if (selectedYearInt == activityDate.getYear() && selectedMonth.equalsIgnoreCase(activityDate.getMonth().toString()) && selectedDayInt == activityDate.getDayOfMonth()) { //display activitie on given day
				availableActivities.add(activityDate.toString() + " " + activity.getDescription());
			}
    	}
		if (thirtyOneDayMonths.contains(selectedMonth)) {
			dayChoices.add("30");
			dayChoices.add("31");
		} else if (thirtyDayMonths.contains(selectedMonth)) {
			dayChoices.add("30");
		}
    	if (availableActivities.size() >= 1) {
			activitySelection.getItems().setAll(availableActivities);
    		activitySelection.getSelectionModel().select(0);
    	} else {
			ObservableList<String> noData = FXCollections.observableArrayList("No Activities");
			activitySelection.setItems(noData);
			activitySelection.getSelectionModel().select(0);
    		activitySelection.setDisable(true);
		}
		daySelection.setItems(dayChoices);
    }

    /**
     * Creates a new graph to be displayed in the chart.
     */
    @FXML
    private void newGraph() {
    	if (activities.size() >= 1) {
	        int activity = activitySelection.getSelectionModel().getSelectedIndex();
	        Activity selectedActivity = activities.get(activity);
	    	String seriesType = activityTypeSelection.getSelectionModel().getSelectedItem();
	    	if (activitySelection.getValue().toString().equals("No Activities")) {
	    		ApplicationManager.displayPopUp("YA DINGUSS", "No activities to display ya friggen frig with selected dates", "error");
			} else if (currentSeriesTypes.size() == 1 && currentSeriesTypes.get(0) == selectedActivity && curSeriesType == seriesType) {
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
	            String errorMessage = String.format("Already displaying data for %s ya dinguss", selectedActivity.getStartDate().toString());
	            ApplicationManager.displayPopUp("YA DINGUSS!", errorMessage, "error");
	        }
    	} else {
    		ApplicationManager.displayPopUp("YA DINGUSS!", "You have no uploaded activity data. Go to workouts to upload your activities.", "error");
    	}
    }

    /**
     *Adds a series of data to the chart
     * @throws SQLException
     */
    @FXML
    private void addSeries() throws SQLException {
    	if (activities.size() >= 1) {
	        int activity = activitySelection.getSelectionModel().getSelectedIndex();
	        Activity selectedActivity = activities.get(activity);
	    	String seriesType = activityTypeSelection.getSelectionModel().getSelectedItem();
	    	if (!currentSeriesTypes.contains(selectedActivity) && seriesType == curSeriesType) {
		        //defining the axes
	    		xAxis.setLabel("Time (Minutes)");
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
    	String activityType = activityTypeSelection.getSelectionModel().getSelectedItem();
        series.setName(selectedActivity.getStartDate().toString() + " " + activityType);
		ActivityAnalysis activityAnalysis = new ActivityAnalysis();
    	for (ActivityDataPoint point : selectedActivity.getActivityData()) {
        	Duration duration = Duration.between(selectedActivity.getStartTime(), point.getTime());
        	double time = duration.toMillis() / 60000.0;
        	//time = time / 10;
        	switch (activityType) {
				case ("Heart Rate"):
					yAxis.setLabel("Heart Rate (BPM)");
					series.getData().add(new XYChart.Data(time, point.getHeartRate()));
					break;
				case ("Distance"):
					yAxis.setLabel("Total Distance (KM)");

					int index = selectedActivity.getActivityData().indexOf(point);
					double distance = activityAnalysis.findDistanceFromStart(selectedActivity, index);
					series.getData().add(new XYChart.Data(time, distance));
					break;
				case ("Elevation"):
					yAxis.setLabel("Elevation (M)");
					series.getData().add(new XYChart.Data(time, point.getElevation()));
					break;
				case ("Calories"):
					String userName = ApplicationManager.getCurrentUsername();
					yAxis.setLabel("Calories Burned");
					double calories = activityAnalysis.findCaloriesBurnedFromStart(duration.toMinutes(), selectedActivity.getType(), databaseManager.getUser(userName).getWeight());
					series.getData().add(new XYChart.Data(time, calories));
					break;
            }
        }
    	currentSeriesTypes.add(selectedActivity);
        analysisGraph.getData().add(series);
    }
}


