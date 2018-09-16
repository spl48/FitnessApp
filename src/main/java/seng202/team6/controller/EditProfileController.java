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
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.team6.models.User;
import seng202.team6.utilities.UserDataValidation;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * <h1>Edit Profile Controller</h1>
 * <p>Contains methods which initilizes and processes data from a user details entry form.</p>
 * <p>If valid entered data is put into a database.</p>
 */
public class EditProfileController {
    
    /**
     * Profile detail text fields used when editing textual attributes of the user.
     */
    @FXML
    private TextField fnameField, lnameField, usernameField, weightField, heightField, strideField;

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

    /**
     * Name at the top of the profile page.
     */
    @FXML
    private Label nameTitle;

    /**
     * String attrbutes of the user.
     */
    private String username, first, last, gender;

    /**
     * Double (Decimal) attributes associated with the user.
     */
    private double height, weight, stride;

    /**
     * The date of birth of the user.
     */
    private LocalDate birthDate;


    /**
     * Initialization of the user details edit form. 
     * Sets the input fields to the existing user data.
     * Initialises available values for the gender drop down menu.
     */
    public void initialize() throws SQLException {
        
        // Gets current user.
        User currUser = ApplicationManager.getDatabaseManager().getUser(ApplicationManager.getCurrentUserID()); 

        // Initialises the gender drop down options.
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Male", "Female");
        genderField.setItems(availableChoices);

        // Sets the default values to user details.
        fnameField.setText(currUser.getFirstName());
        lnameField.setText(currUser.getLastName());
        usernameField.setText(currUser.getUsername());
        genderField.setValue(currUser.getGender());
        heightField.setText(Double.toString(currUser.getHeight()));
        weightField.setText(Double.toString(currUser.getWeight()));
        strideField.setText(Double.toString(currUser.getStrideLength()));
        dobField.setValue(currUser.getDOB());

        // Sets the name display at the top of the profile window.
        nameTitle.setText(currUser.getFullName().toUpperCase());
    }


    /**
     * Changes the screen back to the profile screen, discards current edits.
     * @param event The edit toggle button click.
     * @throws IOException When the profile screen fxml cannot be loaded.
     */
    public void toProfile(Event event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("../view/profileScreen.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }


    /**
     * Updates the user profile and returns the user to the profile screen.
     * @param event When the user clicks the UPDATE button.
     * @throws IOException When the profile screen fxml in toProfile cannot be loaded.
     */
    public void updateProfile(ActionEvent event) throws IOException {
        
        setEnteredData(); // Sets the class variables to the entered data.
        
        if (validEnteredData()) {
            System.out.println("Updated User Data!!"); //Testing - can be replaced with a confirmation message later...
            
            // ENTER DATA INTO DATABASE
            
            printData(); // Testing 
            toProfile(event); // Directs back to the profile screen.
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
        birthDate = dobField.getValue();

        // Tries to set the double attributes.
        // Otherwise shows and error pop up.
        try {
            height = Double.parseDouble(heightField.getText());
            weight = Double.parseDouble(weightField.getText());
            stride = Double.parseDouble(strideField.getText());
        } catch (NumberFormatException e) {
            ApplicationManager.displayPopUp("Invalid Type", "Please ensure measurement fields are numeric values.", "error");
        }
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
                UserDataValidation.validateDoubleValue(height, "Height", 280, 55) &&
                UserDataValidation.validateDoubleValue(weight, "Weight", 600,2) &&
                UserDataValidation.validateDoubleValue(stride, "Stride Length", 2.5,0.3);
    }

    /**
     * Prints the data extracted from the form for testing purposes.
     */
    private void printData() {
        System.out.println("Username: " + username);
        System.out.println("First Name: " + first);
        System.out.println("Last Name: " + last);
        System.out.println("Gender: " + gender);
        System.out.println("Birth Date: " + birthDate);
        System.out.println("Height: " + height);
        System.out.println("Weight: " + weight);
        System.out.println("Stride Length: " + stride);
    }

}
