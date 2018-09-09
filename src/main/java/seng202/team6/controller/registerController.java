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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import seng202.team6.controller.ApplicationManager;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.utilities.DataValidation;
import seng202.team6.utilities.GeneralUtilities;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static javafx.scene.paint.Color.rgb;

/**
 * <h1>Register Controller</h1>
 * <p>Initialises and applies functionality to the Register screen allowing users to make profiles in the database</p>
 */
public class registerController extends GUIUtilities {

    /**
     * User details textual fields.
     */
    @FXML
    private TextField usernameEntry, firstNameEntry, lastNameEntry, heightEntry, weightEntry, strideEntry;

    /**
     * Date Picker for user to choose thier birth date.
     */
    @FXML
    private DatePicker birthDateEntry;

    /**
     *
     */
    @FXML
    private ComboBox<String> genderComboBox;

    private String username, first, last, gender;
    private double height, weight, stride;
    private LocalDate birthDate;
    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Male", "Female");
        genderComboBox.setItems(availableChoices);
    }

    @FXML
    public void toStartScreen(Event event) throws IOException {
        System.out.println("Changing to the login screen!!!!");
        Parent loginParent = FXMLLoader.load(getClass().getResource("../view/startScreen2.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }

    @FXML
    public void createNewUser(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        GeneralUtilities.printResultSet(databaseManager.displayUsers());
        setEnteredData();
        if (validEnteredData()) {
            System.out.println("Created a new user!!");
            printData();
            System.out.println(birthDate.toString());
            databaseManager.addUser(username, birthDate.toString(), first, last, gender, height, weight);
            databaseManager.displayUsers();
            toStartScreen(event);
            //Enter into database
        }
    }

    public void setEnteredData() {
        username = usernameEntry.getText();
        first = firstNameEntry.getText();
        last = lastNameEntry.getText();
        gender = genderComboBox.getValue();
        birthDate = birthDateEntry.getValue();
        try {
            height = Double.parseDouble(heightEntry.getText());
            weight = Double.parseDouble(weightEntry.getText());
            stride = Double.parseDouble(strideEntry.getText());
        } catch (NumberFormatException e) {
            // Error Pop Up
        }
    }

    public boolean validEnteredData() throws IOException {
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
