package seng202.team6.controller;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.User;

import javax.swing.text.html.ImageView;
import java.sql.SQLException;

public class GoalsScreenController {
    @FXML
    private TextField stepGoalField;

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
    private Label stepGoal;

    @FXML
    private Label distanceGoal;

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
    private void setStepGoal() {
        int newStepGoal = user.getStepGoal();
        try {
            newStepGoal = Integer.parseInt(stepGoalField.getText());
            ApplicationManager.displayPopUp("Invalid Data", "Succesfully changed weekly step goal to " + newStepGoal + " steps per week", "confirmation");
        } catch (NumberFormatException e) {
            ApplicationManager.displayPopUp("Invalid Data", "Please enter numerical data using numbers!", "error");
        }
        user.setStepGoal(newStepGoal);
        System.out.println("user step goal" + user.getStepGoal());
    }

    public void editGoals() {
        editButton.setVisible(false);
        updateButton.setVisible(true);
        onEditing.setVisible(true);
        editDistanceGoal.setVisible(true);
        editStepGoal.setVisible(true);
        stepGoal.setVisible(false);
        distanceGoal.setVisible(false);
    }

    public void stopEditing() {
        editButton.setVisible(true);
        updateButton.setVisible(false);
        onEditing.setVisible(false);
        stepGoal.setVisible(true);
        distanceGoal.setVisible(true);
        editDistanceGoal.setVisible(false);
        editStepGoal.setVisible(false);
    }

    public void updateGoals() {

    }
}
