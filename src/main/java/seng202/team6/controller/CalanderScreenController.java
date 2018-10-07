package seng202.team6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * <h1>Calendar screen controller</h1>
 * <p>Initializes and displays a calendar of a users activates, and displays activities on
 * a chosen date if that date is selected/p>
 */
public class CalanderScreenController {

    private int activityArrayIndex = 0;
    /**
     * Button for next activity
     */
    @FXML
    private ImageView activityNext;

    /**
     * Button for previous activity
     */
    @FXML
    private ImageView activityPrev;


    /**
     * The activity description label.
     */
    @FXML
    private Label descriptionLabel;

    /**
     * The type description label
     */
    @FXML
    private Label typeLabel;

    /**
     * The notes description label.
     */
    @FXML
    private Label notesLabel;

    /**
     * The date description label.
     */
    @FXML
    private Label startDateLabel, endDateLabel, startTimeLabel, endTimeLabel;


    /**
     * The average velocity label.
     */
    @FXML
    private Label velocityLabel;

    /**
     * The distance label.
     */
    @FXML
    private Label distanceLabel;

    //Title of the calendar, showing the month and year
    @FXML
    Text title;

    // The grid pane which represents the calendar view.
    @FXML
    GridPane calendar;


    // List of the current users activities
    ArrayList<Activity> activities;

    //The date being used, initialized to the current dates
    LocalDate date = LocalDate.now();
    ArrayList<AnchorPane> days   = new ArrayList<>();

    /**
     * Array list containing the activities performed on that day.
     */
    ArrayList<Activity> dayActivities = new ArrayList<>();


    /**
     * Initializes the fitness calendar screen, setting panes for each
     * box / date in the calendar view.
     * @throws SQLException When there is an error retrieving a users activities
     */
    public void initialize() throws SQLException{
        int row;
        int column;

        DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();
        activities = databaseManager.getActivityManager().getActivities(ApplicationManager.getCurrentUserID());

        // Adds a column pane to each calender box
        for (row = 0; row <= 6; row++) {
            for (column = 0; column <= 6; column++) {
                AnchorPane pane = new AnchorPane();
                calendar.add(pane, column, row);
                days.add(pane);
            }
        }

        setUpCalendar();
    }


    /**
     * Sets the calender up for the current month
     */
    public void setUpCalendar() {
        AnchorPane pane;
        Text text;
        int row = 1;
        int column = 0;

        for (AnchorPane day : days) {
            day.getChildren().clear();
        }

        title.setText(date.getMonth().toString() + " " + date.getYear());

        //Decreases the date until the first day of the month is reached
        while (date.getDayOfMonth() != 1) {
            date = date.minusDays(1);
        }

        column = date.getDayOfWeek().getValue();

        if (column == 7) {          // sets to first column if the day is sunday
            column = 0;
        }

        // Sets the date of the first day of the month
        text = new Text(String.valueOf(date.getDayOfMonth()));
        pane = days.get(row * 7 + column);
        pane.setTopAnchor(text, 5.0);
        pane.setLeftAnchor(text, 5.0);
        pane.getChildren().add(text);
        checkDateActivities(pane);
        date = date.plusDays(1);
        column++;


        for (row = 1; row <= 6; row++) {
            while (column <= 6 && date.getDayOfMonth() != 1) {
                pane = days.get(row * 7 + column);
                text = new Text(String.valueOf(date.getDayOfMonth()));
                pane.setTopAnchor(text, 5.0);
                pane.setLeftAnchor(text, 5.0);
                pane.getChildren().add(text);


                checkDateActivities(pane);

                date = date.plusDays(1);
                column++;

            }
            column = 0;
        }

        date = date.minusMonths(1);
        pane.setBackground(null);
    }


    /**
     * Checks if any activities were done at the
     * current date and displays in the calendar if so
     * @param pane the pane for which the activity (if found) is displayed
     */
    public void checkDateActivities(AnchorPane pane) {
        ArrayList<Activity> activitiesOnDate = new ArrayList<>();

        for(Activity activity : activities) {
            if (activity.getStartDate().equals(date)) {     // Activity was undertake on current date

                pane.getChildren().clear();
                Text text = new Text(String.valueOf(date.getDayOfMonth()));
                pane.setTopAnchor(text, 5.0);
                pane.setLeftAnchor(text, 5.0);
                pane.getChildren().add(text);

                ImageView image = new ImageView("/seng202/team6/resources/pics/calendaractivityicon.png");
                pane.getChildren().add(image);
                pane.setBottomAnchor(image, 12.0);
                pane.setLeftAnchor(image, 32.0);

                activitiesOnDate.add(activity);
            }
        }
        pane.setOnMouseClicked(e -> showActivity(activitiesOnDate));
    }

    /**
     * Sets the calendar view to the next month
     */
    public void nextMonth(){
        date = date.plusMonths(1);
        setUpCalendar();
    }

    /**
     * Sets the calendar view to the previous month
     */
    public void previousMonth(){
        date = date.minusMonths(1);
        setUpCalendar();
    }


    /**
     * Displays activities performed on selected day
     * @param activities
     */
    public void showActivity(ArrayList<Activity> activities) {
        if (activities.size() == 0) {     // No activity on selected date
            activityNext.setVisible(false);
            activityPrev.setVisible(false);
            descriptionLabel.setText("No Activity Selected");
            velocityLabel.setText("No Activity Selected");
            distanceLabel.setText("No Activity Selected");
            startDateLabel.setText("No Activity Selected");
            startTimeLabel.setText("No Activity Selected");
            endTimeLabel.setText("No Activity Selected");
            endDateLabel.setText("No Activity Selected");
            typeLabel.setText("No Activity Selected");
            notesLabel.setText("No Activity Selected");
        } else if (activities.size() == 1){
            activityNext.setVisible(false);
            activityPrev.setVisible(false);
            Activity selectedActivity = activities.get(0);
            descriptionLabel.setText(selectedActivity.getDescription());
            velocityLabel.setText(Double.toString(Math.round(selectedActivity.findAverageSpeed())) + " km/h");
            distanceLabel.setText(String.format("%.2f Km\n", selectedActivity.findDistanceFromStart(selectedActivity.getActivityData().size() - 1)));
            startDateLabel.setText(selectedActivity.getStartDate().toString());
            endDateLabel.setText(selectedActivity.getEndDate().toString());
            startTimeLabel.setText(selectedActivity.getStartTime().toString());
            endTimeLabel.setText(selectedActivity.getEndTime().toString());
            typeLabel.setText(selectedActivity.getType());
            notesLabel.setText(selectedActivity.getNotes());
        } else if (activities.size() > 1) {
            activityNext.setVisible(true);
            activityPrev.setVisible(true);
            Activity selectedActivity = activities.get(0);
            descriptionLabel.setText(selectedActivity.getDescription());
            velocityLabel.setText(Double.toString(Math.round(selectedActivity.findAverageSpeed())) + " km/h");
            distanceLabel.setText(String.format("%.2f Km\n", selectedActivity.findDistanceFromStart(selectedActivity.getActivityData().size() - 1)));
            startDateLabel.setText(selectedActivity.getStartDate().toString());
            endDateLabel.setText(selectedActivity.getEndDate().toString());
            startTimeLabel.setText(selectedActivity.getStartTime().toString());
            endTimeLabel.setText(selectedActivity.getEndTime().toString());
            typeLabel.setText(selectedActivity.getType());
            notesLabel.setText(selectedActivity.getNotes());
            setDayActivities(activities);

        }
    }

    /**
     * Button to let user see next activity of that day on calendar.
     * allows for cyclic loop of activities.
     */
    public void nextActivity() {
        activityArrayIndex++;

        // if you want to do a cyclic loop of the data
        if (activityArrayIndex >= dayActivities.size()) {
            activityArrayIndex = 0;
        }
        Activity selectedActivity = dayActivities.get(activityArrayIndex);
        descriptionLabel.setText(selectedActivity.getDescription());
        velocityLabel.setText(Double.toString(Math.round(selectedActivity.findAverageSpeed())) + " km/h");
        distanceLabel.setText(String.format("%.2f Km\n", selectedActivity.findDistanceFromStart(selectedActivity.getActivityData().size() - 1)));
        startDateLabel.setText(selectedActivity.getStartDate().toString());
        endDateLabel.setText(selectedActivity.getEndDate().toString());
        startTimeLabel.setText(selectedActivity.getStartTime().toString());
        endTimeLabel.setText(selectedActivity.getEndTime().toString());
        typeLabel.setText(selectedActivity.getType());
        notesLabel.setText(selectedActivity.getNotes());
    }



    public void prevActivity() {
        activityArrayIndex--;

        // if you want to do a cyclic loop of the data
        if (activityArrayIndex < 0) {
            activityArrayIndex = dayActivities.size() - 1;
        }
        Activity selectedActivity = dayActivities.get(activityArrayIndex);
        descriptionLabel.setText(selectedActivity.getDescription());
        velocityLabel.setText(Double.toString(Math.round(selectedActivity.findAverageSpeed())) + " km/h");
        distanceLabel.setText(String.format("%.2f Km\n", selectedActivity.findDistanceFromStart(selectedActivity.getActivityData().size() - 1)));
        startDateLabel.setText(selectedActivity.getStartDate().toString());
        endDateLabel.setText(selectedActivity.getEndDate().toString());
        startTimeLabel.setText(selectedActivity.getStartTime().toString());
        endTimeLabel.setText(selectedActivity.getEndTime().toString());
        typeLabel.setText(selectedActivity.getType());
        notesLabel.setText(selectedActivity.getNotes());
    }

    /**
     * Button to let user see previous activity of that day on calendar.
     * allows for cyclic loop of activities.
     */
    public void setDayActivities(ArrayList<Activity> activities) {
        dayActivities = activities;
    }
}
