package seng202.team6.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.User;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

/**
 * <h1>Profile Controller</h1>
 * <p>Initialises and applies functionality to the Profile screen.</p>
 */
public class profileController extends GeneralScreenController {

    /**
     * The labels containing the user details from the database.
     */
    @FXML
    private Label firstNameLabel, lastNameLabel, usernameLabel, birthDateLabel, genderLabel, heightLabel, weightLabel;

    /**
     * The top name display.
     */
    @FXML
    private Label topNameLabel;

    /**
     * The Application database manager.
     */
    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();

    /**
     * Initialises the profile screen by setting the labels to the user details from the database.
     * @throws SQLException Occurs when there is a problem when getting a user from the database.
     */
    public void initialize() throws SQLException {

        // Gets the current user object.
        User currUser = databaseManager.getUserFromID(ApplicationManager.getCurrentUserID()); //Replace with database current user.

        // Sets the top name display.
        topNameLabel.setText(currUser.getFullName().toUpperCase());

        // Sets all the details labels
        firstNameLabel.setText(currUser.getFirstName());
        lastNameLabel.setText(currUser.getLastName());
        usernameLabel.setText(currUser.getUsername());
        genderLabel.setText(currUser.getGender());
        heightLabel.setText(Double.toString(currUser.getHeight()));
        weightLabel.setText(Double.toString(currUser.getWeight()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        birthDateLabel.setText(currUser.getDOB().format(formatter));
    }


    /**
     * Redirects the user to a screen where they can edit their details.
     * @param event When the edit toggle button is pressed.
     */
    public void toEditProfile(Event event) {
        changeScreen(event, "/seng202/team6/view/EditProfileScreen.fxml", "PROFILE");
    }
}
