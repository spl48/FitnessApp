package seng202.team6.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng202.team6.models.User;

import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class profileController {

    @FXML
    private Label nameLabel, usernameLabel, birthDateLabel, genderLabel, heightLabel, weightLabel, strideLabel;

    public void initialize() {
        User testUser = new User("Joe Bloggs", LocalDate.of(2011, 5,8), 31, 1.65, 70, 2.5, "jbl32andmore"); //Test instance
        User currUser = testUser; //Replace with database current user.

        nameLabel.setText(currUser.getName());
        usernameLabel.setText(currUser.getUsername());
        genderLabel.setText("Male");
        heightLabel.setText(Double.toString(currUser.getHeight()));
        weightLabel.setText(Double.toString(currUser.getWeight()));
        strideLabel.setText(Double.toString(currUser.getStrideLength()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        birthDateLabel.setText(currUser.getDOB().format(formatter));
    }

    public void toEditProfile() {


    }
}
