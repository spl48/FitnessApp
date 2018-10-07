package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.User;
import seng202.team6.utilities.UserDataValidation;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * <h1>Profile Controller</h1>
 * <p>Initialises and applies functionality to the Profile screen.</p>
 */
public class profileController extends GeneralScreenController {

    /**
     * The labels containing the user details from the database.
     */
    @FXML
    private Label firstNameLabel, lastNameLabel, usernameLabel, birthDateLabel, genderLabel, heightLabel, weightLabel, strideLabel;

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
     * Profile detail text fields used when editing textual attributes of the user.
     */
    @FXML
    private TextField fnameField, lnameField, usernameField, weightField, heightField;

    /**
     * Gender choice used for editing the gender.
     */
    @FXML
    private ComboBox<String> genderField;

    /**
     * A Date Picker used when the user enters their date of birth.
     */
    @FXML
    private DatePicker dobField;

    /** The update profile button. */
    @FXML
    private Button updateButton;

    /** The edit toggle button. */
    @FXML
    private Node editOn;

    /**
     * String attrbutes of the user.
     */
    private String username, first, last, gender;

    /**
     * Double (Decimal) attributes associated with the user.
     */
    private double height, weight;

    /**
     * The date of birth of the user.
     */
    private LocalDate birthDate;

    /** The user object representing the user which is currently signed in. */
    private User currUser;


    /**
     * Initialises the profile screen by setting the labels to the user details from the database.
     * @throws SQLException Occurs when there is a problem when getting a user from the database.
     */
    public void initialize() throws SQLException {

        // Gets the current user object.
        currUser = databaseManager.getUserReader().getUserFromID(ApplicationManager.getCurrentUserID()); //Replace with database current user.

        // Sets the top name display.
        topNameLabel.setText(currUser.getFullName().toUpperCase());

        // Sets all the details labels
        firstNameLabel.setText(currUser.getFirstName());
        lastNameLabel.setText(currUser.getLastName());
        usernameLabel.setText(currUser.getUsername());
        genderLabel.setText(currUser.getGender());
        heightLabel.setText(Double.toString(currUser.getHeight()));
        weightLabel.setText(Double.toString(currUser.getWeight()));
        strideLabel.setText(Double.toString(currUser.getWalkingStrideLength()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        birthDateLabel.setText(currUser.getDOB().format(formatter));
    }


    /**
     * Turns on editing functionality for the profile screen.
     */
    public void toEditProfile() {

        // Sets the array list to the available gender options.
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Male", "Female");
        genderField.setItems(availableChoices);

        // Sets the initial values of the editing fields to the existing data.
        fnameField.setText(currUser.getFirstName());
        lnameField.setText(currUser.getLastName());
        usernameField.setText(currUser.getUsername());
        genderField.setValue(currUser.getGender());
        heightField.setText(Double.toString(currUser.getHeight()));
        weightField.setText(Double.toString(currUser.getWeight()));
        strideLabel.setText(Double.toString(currUser.getWalkingStrideLength()));
        dobField.setValue(currUser.getDOB());

        // Sets the viability of the editing fields to true - un hides them.
        setVisablityProfileEdit(true);
        ApplicationManager.setEditingStatus(true);
    }

    /**
     * Changes the screen back to the profile screen, discards current edits.
     * @param event The edit toggle button click.
     * @throws IOException When the profile screen fxml cannot be loaded.
     */
    public void toProfile(Event event) throws IOException {
        changeScreen(event, "/seng202/team6/view/profileScreen.fxml", "PROFILE");
    }


    /**
     * Updates the user profile and returns the user to the profile screen.
     * @param event When the user clicks the UPDATE button.
     * @throws IOException When the profile screen fxml in toProfile cannot be loaded.
     */
    public void updateProfile(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        // Gets the user and the usernames from the database and initialises their data in the edit screen.
        User currUser = ApplicationManager.getDatabaseManager().getUserReader().getUser(ApplicationManager.getCurrentUsername());
        ArrayList<String> usernames = databaseManager.getUserReader().getUsernames();
        setEnteredData(); // Sets the class variables to the entered data

        // Checks if the user has entered a duplicate username and displays error if so.
        boolean duplicate = false;
        for (String user : usernames) {
            if ((!(currUser.getUsername().equalsIgnoreCase(username))) && (user.equalsIgnoreCase(username))) {
                duplicate = true;
                break;
            }
        }

        // Checks if there is a duplicate, if so then displays error,
        if (duplicate == true) {
            ApplicationManager.displayPopUp("Username Already Exists", "Username Already Exists.\nPlease choose another username.", "error");
        // Checks if the entered data is valid.
        } else if (validEnteredData()) {

            // Enters data into the database.
            ApplicationManager.getDatabaseManager().getUserWriter().updateFirstName(first);
            ApplicationManager.getDatabaseManager().getUserWriter().updateLastName(last);
            ApplicationManager.getDatabaseManager().getUserWriter().updateUsername(username);
            ApplicationManager.getDatabaseManager().getUserWriter().updateGender(gender);
            ApplicationManager.getDatabaseManager().getUserWriter().updateDateOfBirth(birthDate);
            ApplicationManager.getDatabaseManager().getUserWriter().updateHeight(height);
            ApplicationManager.getDatabaseManager().getUserWriter().updateWeight(weight);

            // Directs back to the profile screen and displays confirmation method.
            ApplicationManager.displayPopUp("Update Success", "You have successfully updated your profile!", "confirmation");
            ApplicationManager.setEditingStatus(false);
            toProfile(event);
        }
    }

    /**
     * Sets the class variables to the data entered into the form with
     * appropriate conversions of type.
     */
    private void setEnteredData() {

        // Sets textual attributes
        username = usernameField.getText();
        first = fnameField.getText();
        last = lnameField.getText();
        gender = genderField.getValue();
        System.out.println(gender);
        birthDate = dobField.getValue();

        // Tries to set the double attributes.
        // Otherwise shows and error pop up.
        try {
            height = Double.parseDouble(heightField.getText());
            weight = Double.parseDouble(weightField.getText());
        } catch (NumberFormatException e) {
            ApplicationManager.displayPopUp("Invalid Type", "Please ensure measurement fields are numeric values.", "error");
        }
    }

    /**
     * Stops the user editing the the profile, checks with a yes /no box.
     */
    @FXML
    public void stopEditing() {

        // Gets the decision from the user if they want to continue editing
        boolean stopEditing = ApplicationManager.getAnswerFromPopUp("Are you sure you want to stop editing. Any changes made will lost.");

        // Disables all the profile edit fields if the user desires to exit.
        if (stopEditing) {
            setVisablityProfileEdit(false);
            ApplicationManager.setEditingStatus(false);
        }
    }


    /** Sets the viability to all the profile edit fields to true or false as desired. Hiding or showing them */
    private void setVisablityProfileEdit(Boolean isVisable) {
        editOn.setVisible(isVisable);
        usernameField.setVisible(isVisable);
        fnameField.setVisible(isVisable);
        lnameField.setVisible(isVisable);
        dobField.setVisible(isVisable);
        genderField.setVisible(isVisable);
        heightField.setVisible(isVisable);
        weightField.setVisible(isVisable);
        updateButton.setVisible(isVisable);
    }

    /**
     * Validates all the form data starting from the top of the form.
     * Displays error pop ups corresponding to any invalid data entered.
     * @return Whether all data entry fields contain valid data.
     */
    private boolean validEnteredData() {
        return UserDataValidation.validateUserName(username) &&
                UserDataValidation.validateName(first, "First Name") &&
                UserDataValidation.validateName(last, "Last Name") &&
                UserDataValidation.validateBirthDate(birthDate) &&
                UserDataValidation.validateGender(gender) &&
                UserDataValidation.validateDoubleValue(height, "Height", 280, 55, "cm") &&
                UserDataValidation.validateDoubleValue(weight, "Weight", 600,2, "kg");
    }

}
