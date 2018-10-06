package seng202.team6.controller;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.User;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static java.util.concurrent.TimeUnit.DAYS;
import static seng202.team6.models.Goal.distanceAchieved;
import static seng202.team6.models.Goal.stepsAchieved;

public class GoalsScreenController {
    @FXML
    private TextField stepGoalField, distanceGoalField;

    @FXML
    private Node stepEditButton;

    @FXML
    private Node editDistanceButton;

    @FXML
    private Node stepsOnEditing;

    @FXML
    private Node onDistanceEditing;

    @FXML
    private TextField distanceEdit;

    @FXML
    private TextField stepsEdit;

    @FXML
    private Button updateStep;

    @FXML
    private Button updateDistance;

    @FXML
    private Node editStepGoal;

    @FXML
    private Node editDistanceGoal;

    @FXML
    private Button updateButton;

    @FXML
    private ProgressIndicator stepProgress, distanceProgress;

    @FXML
    private Label stepGoal1;

    @FXML
    private Label stepsLeftLabel;

    @FXML
    private Label distanceGoalLabel, distanceLeftLabel;

    @FXML
    private Label daysToGoLabel1, daysToGoLabel2;

    @FXML
    private Circle stepCircle, distanceCircle;

    @FXML
    private javafx.scene.image.ImageView feetImage, distanceImage;

    /**
     * The current database manager
     */
    private DatabaseManager databaseManager;

    /**
     * The current user
     */
    private User user;


    public void initialize() throws SQLException {
        databaseManager = ApplicationManager.getDatabaseManager();
        String userName = ApplicationManager.getCurrentUsername();
        user = databaseManager.getUserReader().getUser(userName);

        setStepData();
        setDistanceData();
        setDaysToGo();


    }

    /**
     * Updated the users step goal in the database to the users entered value
     * @throws SQLException
     */
    @FXML
    private void setStepsGoal() throws SQLException {
        int newStepGoal = user.getStepGoal();
        try {
            newStepGoal = Integer.parseInt(stepsEdit.getText());
            ApplicationManager.displayPopUp("Updated Goal", "Successfully changed weekly step goal to " + newStepGoal + " steps per week", "confirmation");
        } catch (NumberFormatException e) {
            ApplicationManager.displayPopUp("Invalid Data", "Please enter numerical data using numbers!", "error");
        }
        user.setStepGoal(newStepGoal);
        if (!stepsAchieved(user)) {
            stepCircle.setVisible(true);
            feetImage.setVisible(true);
            stepProgress.setStyle("..\\resources\\css\\progressIndicator.css");
        }
        stopEditing();
    }

    /**
     * Sets the data for the step goal section with the users current step goal per week, and how many steps until this goal is reached.
     * If the goal is reached, only the progress chart is displayed
     * @throws SQLException
     */
    private void setStepData() throws SQLException {
        double totalSteps = ApplicationManager.getDatabaseManager().getActivityManager().getUpdatedStepGoal(ApplicationManager.getCurrentUserID());
        String stepGoalString = Integer.toString(user.getStepGoal()) + " Steps";
        stepGoal1.setText(stepGoalString);
        double stepsLeft = user.getStepGoal() - totalSteps;
        if (stepsLeft <= 0) {
            stepsLeft = 0;
        }
        String stepsLeftString = String.format("%.0f Steps", stepsLeft);
        stepsLeftLabel.setText(stepsLeftString);
        double progressRatio = totalSteps / user.getStepGoal();
        System.out.println(progressRatio);
        if (progressRatio >= 1) {
            progressRatio = 1;
            stepCircle.setVisible(false);
            feetImage.setVisible(false);
            stepProgress.setStyle("-fx-accent: green");
        }
        stepProgress.setProgress(progressRatio);
    }

    /**
     * Sets the data for the step distance section with the users current distance goal per week, and how many kilometers until this goal is reached.
     * If the goal is reached, only the progress chart is displayed
     * @throws SQLException
     */
    public void setDistanceData() throws SQLException {
        int distanceGoal = user.getDistanceGoal();
        double totalDistance = ApplicationManager.getDatabaseManager().getActivityManager().getUpdatedDistanceGoal(ApplicationManager.getCurrentUserID());
        String distanceGoalString = Integer.toString(distanceGoal) + " Kilometers";
        distanceGoalLabel.setText(distanceGoalString);
        double distanceLeft = distanceGoal - totalDistance;
        if (distanceLeft <= 0) {
            distanceLeft = 0;
        }
        String distanceLeftString = String.format("%.0f Kilometers", distanceLeft);
        distanceLeftLabel.setText(distanceLeftString);
        double progressRatio;
        if (distanceGoal == 0) {
            progressRatio = 1;
        } else {
            progressRatio = totalDistance / distanceGoal;
        }
        if (progressRatio >= 1) {
            progressRatio = 1;
            distanceCircle.setVisible(false);
            distanceImage.setVisible(false);
            distanceProgress.setStyle("-fx-accent: green");
        }
        distanceProgress.setProgress(progressRatio);
    }

    /**
     * Sets the step and distance goal sections to display the number of days the user has for the current goal week. i.e days until Sunday.
     */
    private void setDaysToGo() {
        LocalDate ld = LocalDate.now();
        LocalDate endOfWeek = ld.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        long daysBetween = ld.until(endOfWeek, ChronoUnit.DAYS);
        if (daysBetween == 7) {
            daysBetween = 0;
        }
        String days = Long.toString(daysBetween);
        String daysToGo = days + " DAYS TO GO";
        daysToGoLabel1.setText(daysToGo);
        daysToGoLabel2.setText(daysToGo);
    }

    public void editStepGoals() {
        stepsEdit.setText(Integer.toString(user.getStepGoal()));
        stepsOnEditing.setVisible(true);
        stepsEdit.setVisible(true);
        updateStep.setVisible(true);
    }

    public void editDistanceGoals() {
        distanceEdit.setText(Integer.toString(user.getDistanceGoal()));
        onDistanceEditing.setVisible(true);
        distanceEdit.setVisible(true);
        updateDistance.setVisible(true);
    }

    public void stopEditing() throws SQLException {
        setDistanceData();
        setStepData();
        stepsOnEditing.setVisible(false);
        onDistanceEditing.setVisible(false);
        stepsEdit.setVisible(false);
        distanceEdit.setVisible(false);
        updateStep.setVisible(false);
        updateDistance.setVisible(false);
    }

    @FXML
    private void setDistanceGoal() throws SQLException {
        int newDistanceGoal = user.getDistanceGoal();
        try {
            newDistanceGoal = Integer.parseInt(distanceEdit.getText());
            ApplicationManager.displayPopUp("Updated Goal", "Succesfully changed weekly distance goal to " + newDistanceGoal + " kms per week", "confirmation");
        } catch (NumberFormatException e) {
            ApplicationManager.displayPopUp("Invalid Data", "Please enter numerical data using numbers!", "error");
        }
        user.setDistanceGoal(newDistanceGoal);
        if (!distanceAchieved(user)) {
            distanceCircle.setVisible(true);
            distanceImage.setVisible(true);
            distanceProgress.setStyle("..\\resources\\css\\progressIndicator.css");
        }
        stopEditing();
    }
}
