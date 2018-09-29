package seng202.team6.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import seng202.team6.analysis.ActivityAnalysis;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CalanderScreenController {
    @FXML
    GridPane calendar;
    @FXML
    Text title, description, speed, time, distance, type;

    ArrayList<Activity> activities;

    LocalDate date = LocalDate.now();
    ArrayList<AnchorPane> days   = new ArrayList<>();



    public void initialize() throws SQLException{
        int row;
        int column;

        DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();
        activities = databaseManager.getActivitiesWithRecords(ApplicationManager.getCurrentUserID());

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

    public void checkDateActivities(AnchorPane pane) {
        Text text;
        for(Activity activity : activities) {
            if (activity.getStartDate().equals(date)) {
                text = new Text("*");
                pane.setBottomAnchor(text, 5.0);
                pane.getChildren().add(text);
                pane.setOnMouseClicked(e -> displayActivity(activity));
            }
        }
    }


    public void displayActivity(Activity activity) {
        if (activity == null) {
            description.setText("");
            speed.setText("");
            distance.setText("");
            type.setText("");
        } else {
            description.setText(activity.getDescription());
            speed.setText(String.format("%.1f Km / hour" ,ActivityAnalysis.findAverageSpeed(activity)));
            distance.setText(String.format("%.1f Km", ActivityAnalysis.findDistanceFromStart(activity, activity.getActivityData().size() - 1)));
            type.setText(activity.getType());
        }
    }


    public void nextMonth(){
        date = date.plusMonths(1);
        setUpCalendar();
    }

    public void previousMonth(){
        date = date.minusMonths(1);
        setUpCalendar();
    }
}
