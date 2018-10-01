package seng202.team6.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * <h1>Calendar screen controller</h1>
 * <p>Initializes and displays a calendar of a users activates, and displays activities on
 * a chosen date if that date is selected/p>
 */
public class CalanderScreenController {
    //Title of the calendar, showing the month and year
    @FXML
    Text title;
    @FXML
    GridPane calendar;
    // Text representing activities information
    @FXML
    Text description, speed, distance, type;

    // List of the current users activities
    ArrayList<Activity> activities;

    //The date being used, initialized to the current dates
    LocalDate date = LocalDate.now();
    ArrayList<AnchorPane> days   = new ArrayList<>();


    /**
     * Initializes the fitness calendar screen, setting panes for each
     * box / date in the calendar view.
     * @throws SQLException When there is an error retrieving a users activities
     */
    public void initialize() throws SQLException{
        int row;
        int column;

        DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();
        activities = databaseManager.getActivities(ApplicationManager.getCurrentUserID());

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

        //Decreases the date unitl the first day of the month is reached
        while (date.getDayOfMonth() != 1) {
            date = date.minusDays(1);
        }

        column = date.getDayOfWeek().getValue();

        // Sets the date of the first day of the month
        text = new Text(String.valueOf(date.getDayOfMonth()));
        pane = days.get(row * 7 + column);
        pane.setTopAnchor(text, 5.0);
        pane.getChildren().add(text);
        checkDateActivities(pane);
        date = date.plusDays(1);
        column++;


        for (row = 1; row <= 6; row++) {
            while (column <= 6 && date.getDayOfMonth() != 1) {
                pane = days.get(row * 7 + column);
                text = new Text(String.valueOf(date.getDayOfMonth()));
                pane.setTopAnchor(text, 5.0);
                pane.getChildren().add(text);

                pane.setOnMouseClicked(e -> displayActivity(null));

                checkDateActivities(pane);

                date = date.plusDays(1);
                column++;

            }
            column = 0;
        }

        date = date.minusMonths(1);

    }


    /**
     * Checks if any activities were done at the
     * current date and displays in the calendar if so
     * @param pane the pane for which the activity (if found) is displayed
     */
    public void checkDateActivities(AnchorPane pane) {
        Text text;
        for(Activity activity : activities) {
            if (activity.getStartDate().equals(date)) {     // Activity was undertake on current date
                text = new Text("*");
                pane.setBottomAnchor(text, 5.0);
                pane.getChildren().add(text);
                pane.setOnMouseClicked(e -> displayActivity(activity));
            }
        }
    }


    /**
     * Displays the activity imformation when the activity has been selected,
     * sets field to empty if a date with no activity (null) was selected
     * @param activity
     */
    public void displayActivity(Activity activity) {
        if (activity == null) {     // No activity on selected date
            description.setText("");
            speed.setText("");
            distance.setText("");
            type.setText("");
        } else {
            description.setText(activity.getDescription());
            speed.setText(String.format("%.2f Km / hour" , activity.findAverageSpeed()));
            distance.setText(String.format("%.2f Km", activity.findDistanceFromStart(activity.getActivityData().size() - 1)));
            type.setText(activity.getType());
        }
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

    public void previousActivity() {

    }

    public void nextActivity() {

    }
}
