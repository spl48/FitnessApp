package seng202.team6.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class workoutManualEntryController {

    @FXML
    private TextField startTime_E, endTime_E, maxHR_E, minHR_E, sessionName_E;

    @FXML
    private TextArea notes_E;

    @FXML
    private DatePicker sessionDate_E;

    @FXML
    private ChoiceBox<String> sessionType_E;

    // Note start and end time will be time objects of sort when research best way to represent and get input from using
    // gui.
    private String sessionName, sessionType, notes, startTime, endTime;
    private double maxHR, minHR;
    private LocalDate sessionDate;


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Walking", "Running", "Biking");
        sessionType_E.setItems(availableChoices);
    }

    @FXML
    public void toWorkOutScreen(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("WorkoutsScreenSplash.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }

    @FXML
    public void createActivity(ActionEvent event) throws IOException {
        setEnteredData();
        if (validEnteredData()) {
            System.out.println("Created a new activity!!");
            printData();
            toWorkOutScreen(event);
            //Enter into database
        } else {
            //Error pop up
        }
    }

    public void setEnteredData() {
        sessionName = sessionName_E.getText();
        startTime = startTime_E.getText();
        endTime = endTime_E.getText();
        sessionType = sessionType_E.getValue();
        sessionDate = sessionDate_E.getValue();
        notes = notes_E.getText();
        try {
            minHR = Double.parseDouble(minHR_E.getText());
            maxHR = Double.parseDouble(maxHR_E.getText());
        } catch (NumberFormatException e) {
            // Error Pop Up
        }
    }

public boolean validEnteredData() {
//        return DataValidation.validateUserName(username) &&
//                DataValidation.validateName(first, "First Name") &&
//                DataValidation.validateName(last, "Last Name") &&
//                DataValidation.validateBirthDate(birthDate) &&
//                DataValidation.validateGender(gender) &&
//                DataValidation.validateDoubleValue(height, "Height", 280, 55) &&
//                DataValidation.validateDoubleValue(weight, "Weight", 600,2) &&
//                DataValidation.validateDoubleValue(stride, "Stride Length", 2.5,0.3);
    return true;
}

    public void printData() {
        System.out.println("Session Name: " + sessionName);
        System.out.println("Session Type: " + sessionType);
        System.out.println("Session Date: " + sessionDate);
        System.out.println("Start Time: " + startTime);
        System.out.println("Finish Time: " + endTime);
        System.out.println("Minimum Heart Rate: " + minHR);
        System.out.println("Maximum Heart Rate: " + maxHR);
        System.out.println("Notes: \n" + notes);
    }


}
