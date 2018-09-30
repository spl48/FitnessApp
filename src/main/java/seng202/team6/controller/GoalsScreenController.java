package seng202.team6.controller;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.User;

import javax.swing.text.html.ImageView;
import java.sql.SQLException;

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
        double progressRatio = stepsLeft / user.getStepGoal();
        if (progressRatio > 1) {
            progressRatio = 1;
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
            progressRatio = distanceLeft / distanceGoal;
        }
        if (progressRatio > 1) {
            progressRatio = 1;
        }
        distanceProgress.setProgress(progressRatio);
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
