package seng202.team6.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.team6.models.User;
import seng202.team6.utilities.DataValidation;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditProfileController {

    @FXML
    private TextField fnameField, lnameField, usernameField, weightField, heightField, strideField;

    @FXML
    private ComboBox<String> genderField;

    @FXML
    private DatePicker dobField;

    private String username, first, last, gender;
    private double height, weight, stride;
    private LocalDate birthDate;

    public void initialize() {
        User testUser = new User("Joe","Bloggs", LocalDate.of(2011, 5,8), "male",31, 1.65, 70, "jbl74ddddddddd"); //Test instance
        User currUser = testUser; //Replace with database current user.

        ObservableList<String> availableChoices = FXCollections.observableArrayList("Male", "Female");
        genderField.setItems(availableChoices);

        fnameField.setText(currUser.getFirstName());
        lnameField.setText(currUser.getLastName());
        usernameField.setText(currUser.getUsername());
        genderField.setValue("Male");
        heightField.setText(Double.toString(currUser.getHeight()));
        weightField.setText(Double.toString(currUser.getWeight()));
        strideField.setText(Double.toString(currUser.getStrideLength()));
        dobField.setValue(currUser.getDOB());
    }

    public void toProfile(Event event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("profileScreen.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }


    public void updateProfile(ActionEvent event) throws IOException {
        setEnteredData();
        if (validEnteredData()) {
            System.out.println("Updated User Data!!");
            printData();
            toProfile(event);
            //To database
        }
    }

    public void setEnteredData() {
        username = usernameField.getText();
        first = fnameField.getText();
        last = lnameField.getText();
        gender = genderField.getValue();
        birthDate = dobField.getValue();
        try {
            height = Double.parseDouble(heightField.getText());
            weight = Double.parseDouble(weightField.getText());
            stride = Double.parseDouble(strideField.getText());
        } catch (NumberFormatException e) {
            // Error Pop Up
        }
    }

    public boolean validEnteredData() {
        return DataValidation.validateUserName(username) &&
                DataValidation.validateName(first, "First Name") &&
                DataValidation.validateName(last, "Last Name") &&
                DataValidation.validateBirthDate(birthDate) &&
                DataValidation.validateGender(gender) &&
                DataValidation.validateDoubleValue(height, "Height", 280, 55) &&
                DataValidation.validateDoubleValue(weight, "Weight", 600,2) &&
                DataValidation.validateDoubleValue(stride, "Stride Length", 2.5,0.3);
    }

    public void printData() {
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
