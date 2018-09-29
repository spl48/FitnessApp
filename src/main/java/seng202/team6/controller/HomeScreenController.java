package seng202.team6.controller;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seng202.team6.analysis.HealthConcernChecker;
import seng202.team6.analysis.ProfileAnalysis;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import seng202.team6.models.User;

public class HomeScreenController {
    
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
    private Text graphPanelTitle, noDataText;

    /**
     * Text that displays various user statistics
     */
    @FXML
    private Text BMIText, weightType, stepCount, stepsLeftLabel;

    @FXML
    private VBox healthConcernsText;

    /**
     * Displays the MATES AI quote
     */
    @FXML
    private Label quoteLabel;



    /**
     * The current database manager
     */
    private DatabaseManager databaseManager;

    /**
     * The current user
     */
    private User user;

    /**
	 * Array that has all the activities the user can select to display on the graph
	 */
    private ArrayList<Activity> activities;

    /**
     * List of quotes to be displayed on home page by MATES AI
     */
    private ArrayList<String> quoteList = new ArrayList<>(Arrays.asList("DREAMS DON'T WORK UNLESS YOU DO", "LIFE HAS NO REMOTE, GET UP AND CHANGE IT YOURSELF", "LIFE IS ABOUT GETTING UP AN HOUR EARLIER, SO YOU CAN LIVE AN HOUR LONGER",
            "GOOD THINGS COME TO THOSE THAT SWEAT", "IT'S NOT ALWAYS EASY, BUT IT'S ALWAYS WORTH IT", "THE ONLY BAD WORKOUT IS THE ONE THAT DIDN'T HAPPEN", "LIFE IS TOUGH, BUT SO ARE YOU!"));


    public void initialize() throws SQLException {
        ObservableList<String> activityDataTypes = FXCollections.observableArrayList("Distance", "Heart Rate", "Elevation", "Calories");
        activityTypeSelection.setItems(activityDataTypes);
        activityTypeSelection.getSelectionModel().select(activityDataTypes.get(0));
        System.out.println("Init Home");

        setUpInfo();
        setHealthInfo();
        setStepsInfo();
        newGraph();
        updateQuote();

    }

    /**
     * Updates the quote by MATES AI to be the quote at the given index in the quote list
     */
    private void updateQuote() {
        int curQuoteIndex = ApplicationManager.getCurQuoteIndex();
        quoteLabel.setText(quoteList.get(curQuoteIndex));
        quoteLabel.setWrapText(true);
        if (curQuoteIndex == quoteList.size()-1) {
            curQuoteIndex = 0;
        } else {
            curQuoteIndex++;
        }
        ApplicationManager.setCurQuoteIndex(curQuoteIndex);

    }

    /**
     * Gets the current user and their activities
     * @throws SQLException When there is an error in the database when getting usernames and/or activities.
     */
    private void setUpInfo() throws SQLException {
        databaseManager = ApplicationManager.getDatabaseManager();
        String userName = ApplicationManager.getCurrentUsername();
        user = databaseManager.getUser(userName);
        activities = databaseManager.getActivitiesWithRecords(ApplicationManager.getCurrentUserID());
    }

    /**
     * Displays a users BMI analysis and any
     * health concerns they have
     */
    private void setHealthInfo() {
        String healthConcerns = "";

        double BMI = ProfileAnalysis.calculateBMI(user);
        int age = user.getAge();

        String BMIString = String.format("%.1f", BMI);
        BMIText.setText(BMIString);

        weightType.setText("(" + ProfileAnalysis.analyseBMI(BMI).toUpperCase() + ")");
	    newGraph();
	    analysisGraph.setCreateSymbols(false);

        if (HealthConcernChecker.checkTachycardia(activities, age)) {
            healthConcerns += "-" + "Tachycardia\n".toUpperCase();
            Text healthText = new Text();
            healthText.setText(healthConcerns);
            healthText.setStyle("-fx-font: 17 NexaBold; -fx-fill: #494949;");
            healthConcernsText.getChildren().add(healthText);
//            selectProfileButton.setOnAction(new EventHandler<ActionEvent>() {
//                public void handle(ActionEvent event) {
//                    loginController.this.changeSelected(event);
//                }
//            });

        } else if (HealthConcernChecker.checkBradycardia(activities, age)) {
            Text healthText = new Text();
            healthText.setText(healthConcerns);
            healthConcerns += "-" + "Bradycardia\n".toUpperCase();
            healthConcernsText.getChildren().add(new Text(healthConcerns));
        } else if (HealthConcernChecker.checkCardiovascularMortality(activities, age)) {
            Text healthText = new Text();
            healthText.setText(healthConcerns);
            healthConcerns += "-" + "Cardiac Diseases\n".toUpperCase();
            healthConcernsText.getChildren().add(new Text(healthConcerns));
        } else {
            Text healthText = new Text();
            healthText.setText(healthConcerns);
            healthConcernsText.getChildren().add(new Text("Nothing"));
        }
    }

    
    /**
     * Displays the steps a user has taken
     */
    private void setStepsInfo() throws SQLException {
        //double strideLength = user.getWalkingStrideLength();
        double totalSteps = ApplicationManager.getDatabaseManager().getUpdatedStepGoal(ApplicationManager.getCurrentUserID());
        String totalStepsString = String.format("%.0f", totalSteps);
        stepCount.setText(totalStepsString);
        double stepsLeft = user.getStepGoal() - totalSteps;
        if (stepsLeft <= 0) {
            stepsLeft = 0;
        }
        String stepsLeftString = String.format("%.0f", stepsLeft);
        stepsLeftLabel.setText(stepsLeftString);
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

    	int lastIndex = activities.size() - 1;
    	Activity selectedActivity = activities.get(lastIndex);
        graphPanelTitle.setText("LATEST " + selectedActivity.getType().toUpperCase() + " ACTIVITY");
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
            switch (activityDataType) {
                case ("Heart Rate"):
                    yAxis.setLabel("Heart Rate (BPM)");
                    series.getData().add(new XYChart.Data(time, point.getHeartRate()));
                    break;
                case ("Distance"):
                    yAxis.setLabel("Total Distance (KM)");

                    int index = selectedActivity.getActivityData().indexOf(point);
                    double distance = selectedActivity.findDistanceFromStart(index);
                    series.getData().add(new XYChart.Data(time, distance));
                    break;
                case ("Elevation"):
                    yAxis.setLabel("Elevation (M)");
                    series.getData().add(new XYChart.Data(time, point.getElevation()));
                    break;
                case ("Calories"):
                    String userName = ApplicationManager.getCurrentUsername();
                    yAxis.setLabel("Calories Burned");
                    double calories = selectedActivity.findCaloriesBurnedFromStart(duration.toMinutes(), databaseManager.getUser(userName).getWeight());
                    series.getData().add(new XYChart.Data(time, calories));
                    break;
            }
        }
        analysisGraph.getData().add(series);
    }
}
