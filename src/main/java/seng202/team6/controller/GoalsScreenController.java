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
import seng202.team6.utilities.UserDataValidation;

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

    /** Steps goal editing toggleButton */
    @FXML
    private Node stepsOnEditing;

    /** Distance goal editing toggleButton */
    @FXML
    private Node onDistanceEditing;

    /** Distance goal editing field */
    @FXML
    private TextField distanceEdit;

    /** Steps goal editing field */
    @FXML
    private TextField stepsEdit;

    /** Step goal update button */
    @FXML
    private Button updateStep;

    /** Distance goal update button */
    @FXML
    private Button updateDistance;

    /** Progress indicators for steps and progress goals.*/
    @FXML
    private ProgressIndicator stepProgress, distanceProgress;

    /** Step goal label. */
    @FXML
    private Label stepGoal1;

    /** Steps left label */
    @FXML
    private Label stepsLeftLabel;

    /** The distance left and steps left labels. */
    @FXML
    private Label distanceGoalLabel, distanceLeftLabel;

    /** The days to go labels */
    @FXML
    private Label daysToGoLabel1, daysToGoLabel2;

    /** The circles covering the goal progress indicators to make a ring. These are removed once the goal is reached. */
    @FXML
    private Circle stepCircle, distanceCircle;

    /** The images that are displayed in the center of the goal progress indicators, for goals and distance. */
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


    /** Initialises the current instance properties and sets the initial data. */
    public void initialize() throws SQLException {

        // Sets up the current instance of the database manager and the current User instance.
        databaseManager = ApplicationManager.getDatabaseManager();
        String userName = ApplicationManager.getCurrentUsername();
        user = databaseManager.getUserReader().getUser(userName);

        // Sets the initial data into labels such as the distance, step and days to go labels.
        setStepData();
        setDistanceData();
        setDaysToGo();
    }

    /**
     * Updated the users step goal in the database to the users entered value
     * @throws SQLException
     */
    @FXML
    private void setStepsGoal() {

        // Initialises the new step goal to their current step goal.
        int newStepGoal = user.getStepGoal();

        //  Tries to get a valid integer new step goal from the user entry if valid otherwise displays an error.
        try {
            newStepGoal = Integer.parseInt(stepsEdit.getText());

            // Sets the user step goal to the value read and checks if the goal is achieved. If not it ensures the images
            // are displayed on top of the progress indicator so it looks like a ring.
            if (UserDataValidation.validStepGoal(newStepGoal)) {
                user.setStepGoal(newStepGoal);
                ApplicationManager.displayPopUp("Updated Goal", "Successfully changed weekly step goal to " + newStepGoal + " steps per week", "confirmation");
                if (!stepsAchieved(user)) {
                    stepCircle.setVisible(true);
                    feetImage.setVisible(true);
                    stepProgress.setStyle("..\\resources\\css\\progressIndicator.css");
                }

                // Stops the editing situation once an update has been made.
                stopEditingStep();
            }
        } catch (NumberFormatException e) {
            ApplicationManager.displayPopUp("Invalid Data", "Please enter numerical data using numbers!", "error");
        }
    }

    /**
     * Sets the data for the step goal section with the users current step goal per week, and how many steps until this goal is reached.
     * If the goal is reached, only the progress chart is displayed
     * @throws SQLException
     */
    private void setStepData() {
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
    public void setDistanceData() {
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


    /** Allows the user to edit their step goals after clicking the step edit toggle button. */
    public void editStepGoals() {
        stepsEdit.setText(Integer.toString(user.getStepGoal()));
        setVisibilityStep(true);
        ApplicationManager.setEditingStatus(true);
    }

    /** Allows the user to edit their distance goals after clicking the distance edit toggle button. */
    public void editDistanceGoals() {
        distanceEdit.setText(Integer.toString(user.getDistanceGoal()));
        setVisibilityDistance(true);
        ApplicationManager.setEditingStatus(true);
    }

    /** Stops all editing of all fields, attempts to set the distance data */
    public void stopEditingDistance() {
        if (ApplicationManager.exitEditingCheck()) {
            setDistanceData();
            setVisibilityDistance(false);
            ApplicationManager.setEditingStatus(false);
        }
    }

    /** Stops all editing of all fields, attempts to set the step data */
    public void stopEditingStep() {
        if (ApplicationManager.exitEditingCheck()) {
            setStepData();
            setVisibilityStep(false);
            ApplicationManager.setEditingStatus(false);
        }
    }

    /**
     * Toggles the visibility of the edit step goal elements
     * @param isVisible boolean true if elements visible
     */
    private void setVisibilityStep(boolean isVisible) {
        stepsOnEditing.setVisible(isVisible);
        stepsEdit.setVisible(isVisible);
        updateStep.setVisible(isVisible);
    }

    /**
     * Toggles the visibility of the edit distance goal elements
     * @param isVisible boolean true of elements visible
     */
    private void setVisibilityDistance(boolean isVisible) {
        onDistanceEditing.setVisible(isVisible);
        distanceEdit.setVisible(isVisible);
        updateDistance.setVisible(isVisible);
    }

    /** Sets the distance goal if valid and enters it into the database. */
    @FXML
    private void setDistanceGoal() {
        try {
            int newDistanceGoal = Integer.parseInt(distanceEdit.getText());

            if (UserDataValidation.validDistanceGoal(newDistanceGoal)) {
                user.setDistanceGoal(newDistanceGoal);
                ApplicationManager.displayPopUp("Updated Goal", "Successfully changed weekly distance goal to " + newDistanceGoal + " kms per week", "confirmation");
                if (!distanceAchieved(user)) {
                    distanceCircle.setVisible(true);
                    distanceImage.setVisible(true);
                    distanceProgress.setStyle("..\\resources\\css\\progressIndicator.css");
                }
                stopEditingDistance();
                ApplicationManager.setEditingStatus(false);
            }

        } catch (NumberFormatException e) {
            ApplicationManager.displayPopUp("Invalid Data", "Please enter numerical data into distance field!", "error");
        }
    }
}
