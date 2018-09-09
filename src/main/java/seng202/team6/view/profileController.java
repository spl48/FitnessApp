package seng202.team6.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seng202.team6.controller.ApplicationManager;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.User;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class profileController {

    @FXML
    private Label firstNameLabel, lastNameLabel, usernameLabel, birthDateLabel, genderLabel, heightLabel, weightLabel, strideLabel;
    
    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();

    public void initialize() throws SQLException, ClassNotFoundException {


        //User testUser = new User("Joe","Bloggs", LocalDate.of(2011, 5,8), "male",31, 1.65, 70, "jbl74ddddddddd"); //Test instance
        User currUser = databaseManager.getUser(ApplicationManager.getCurrentUserID()); //Replace with database current user.
        currUser.printUser();

        firstNameLabel.setText(currUser.getFirstName());
        lastNameLabel.setText(currUser.getLastName());
        usernameLabel.setText(currUser.getUsername());
        genderLabel.setText(currUser.getGender());
        heightLabel.setText(Double.toString(currUser.getHeight()));
        weightLabel.setText(Double.toString(currUser.getWeight()));
        strideLabel.setText(Double.toString(currUser.getStrideLength()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        birthDateLabel.setText(currUser.getDOB().format(formatter));
    }

    public void toEditProfile(Event event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("EditProfileScreen.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }
}
