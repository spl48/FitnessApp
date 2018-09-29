package seng202.team6.controller;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.User;

import java.sql.SQLException;

public class GoalsScreenController {
    @FXML
    private TextField stepGoalField, distanceGoalField;

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
