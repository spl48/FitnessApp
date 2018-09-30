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

public class GoalsScreenController {
    @FXML
    private TextField stepGoalField, distanceGoalField;

    @FXML
    private Node editButton;

    @FXML
    private Node onEditing;

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
        user = databaseManager.getUser(userName);

        setStepData();
        setDistanceData();
        setDaysToGo();


    }

    @FXML
    private void setStepGoal() throws SQLException {
        int newStepGoal = user.getStepGoal();
        try {
            newStepGoal = Integer.parseInt(stepGoalField.getText());
            ApplicationManager.displayPopUp("Updated Goal", "Succesfully changed weekly step goal to " + newStepGoal + " steps per week", "confirmation");
        } catch (NumberFormatException e) {
            ApplicationManager.displayPopUp("Invalid Data", "Please enter numerical data using numbers!", "error");
        }
        user.setStepGoal(newStepGoal);
        System.out.println("user step goal" + user.getStepGoal());
    }

    private void setStepData() throws SQLException {
        double totalSteps = ApplicationManager.getDatabaseManager().getUpdatedStepGoal(ApplicationManager.getCurrentUserID());
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
        }
        stepProgress.setProgress(progressRatio);
    }

    public void setDistanceData() throws SQLException {
        int distanceGoal = user.getDistanceGoal();
        double totalDistance = ApplicationManager.getDatabaseManager().getUpdatedDistanceGoal(ApplicationManager.getCurrentUserID());
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
        }
        distanceProgress.setProgress(progressRatio);
    }

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

    public void editGoals() {
        //editButton.setVisible(false);
        //updateButton.setVisible(true);
        //onEditing.setVisible(true);
        //editDistanceGoal.setVisible(true);
        //editStepGoal.setVisible(true);
        //stepGoal.setVisible(false);
        //distanceGoal.setVisible(false);

    }

    public void stopEditing() {
        editButton.setVisible(true);
        updateButton.setVisible(false);
        onEditing.setVisible(false);
        stepGoal1.setVisible(true);
        stepsLeftLabel.setVisible(true);
        distanceGoalLabel.setVisible(true);
        distanceLeftLabel.setVisible(true);
        editDistanceGoal.setVisible(false);
        editStepGoal.setVisible(false);
    }

    public void updateGoals() {

    }

    @FXML
    private void setDistanceGoal() throws SQLException {
        int newDistanceGoal = user.getDistanceGoal();
        try {
            newDistanceGoal = Integer.parseInt(distanceGoalField.getText());
            ApplicationManager.displayPopUp("Updated Goal", "Succesfully changed weekly step goal to " + newDistanceGoal + " steps per week", "confirmation");
        } catch (NumberFormatException e) {
            ApplicationManager.displayPopUp("Invalid Data", "Please enter numerical data using numbers!", "error");
        }
        user.setDistanceGoal(newDistanceGoal);
    }
}
