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

    /**
     * The Application database manager.
     */
    private DatabaseManager dbManager = ApplicationManager.getDatabaseManager();

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


    // Text representing activities information
    @FXML
    Text description, speed, distance, type, dateText;

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

                pane.setOnMouseClicked(e -> displayActivities(null));

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
//                text = new Text("*");
//                pane.setBottomAnchor(text, 5.0);
//                pane.getChildren().add(text);
                pane.getChildren().clear();

                Text text = new Text(String.valueOf(date.getDayOfMonth()));
                pane.setTopAnchor(text, 5.0);
                pane.setLeftAnchor(text, 5.0);
                pane.getChildren().add(text);

                ImageView image = new ImageView("/seng202/team6/resources/pics/calendaractivityicon.png");
                pane.getChildren().add(image);
                pane.setBottomAnchor(image, 5.0);
                pane.setLeftAnchor(image, 15.0);
//                BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

//                pane.setBackground(new Background(new BackgroundImage(image,
//                        BackgroundRepeat.NO_REPEAT,
//                        BackgroundRepeat.NO_REPEAT,
//                        BackgroundPosition.CENTER,
//                        bSize)));
                activitiesOnDate.add(activity);
            }
        }
        pane.setOnMouseClicked(e -> showActivity(activitiesOnDate));
    }


    /**
     * Displays the activity imformation when the activity has been selected,
     * sets field to empty if a date with no activity (null) was selected
     * @param activities
     */
    public void displayActivities(ArrayList<Activity> activities) {
        if (activities.size() == 0) {     // No activity on selected date
            System.out.println("1");
            description.setText("");
            speed.setText("");
            distance.setText("");
            type.setText("");
        } else {
            System.out.println("2");
            String descriptionText = "";
            String speedText = "";
            String distanceText = "";
            String typeText = "";
            for (Activity activity : activities) {
                descriptionText += activity.getDescription() + "\n";
                speedText += String.format("%.2f Km / hour\n" , activity.findAverageSpeed());
                distanceText += String.format("%.2f Km\n", activity.findDistanceFromStart(activity.getActivityData().size() - 1));
                typeText += activity.getType() + "\n";
            }
            description.setText(descriptionText);
            speed.setText(speedText);
            distance.setText(distanceText);
            type.setText(typeText);
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

    public void showActivity(ArrayList<Activity> activities) {
        if (activities.size() == 0) {     // No activity on selected date
            descriptionLabel.setText("No Activity Selected");
            velocityLabel.setText("No Activity Selected");
            distanceLabel.setText("No Activity Selected");
            startDateLabel.setText("No Activity Selected");
            startTimeLabel.setText("No Activity Selected");
            endTimeLabel.setText("No Activity Selected");
            endDateLabel.setText("No Activity Selected");
            typeLabel.setText("No Activity Selected");
            notesLabel.setText("No Activity Selected");
        } else {
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
        }
    }
}
