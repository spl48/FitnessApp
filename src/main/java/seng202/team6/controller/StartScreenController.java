package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import seng202.team6.datahandling.DatabaseManager;

import java.sql.SQLException;

/**
 * <h1>Start Screen Controller</h1>
 * <p>Initialises and applies functionality to the Start screen allowing the user to navigate to login or register.</p>
 */
public class startScreenController extends GeneralScreenController {

    /**
     * Directs the user to the login screen.
     * @param event When the login button is clicked.
     */
    @FXML
    public void toLoginScreen(ActionEvent event) throws SQLException, ClassNotFoundException {
        DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();
        if (databaseManager.getUserReader().getUsernames().size() > 0) {
            changeScreen(event, "/seng202/team6/view/loginScreen.fxml", "LOGIN");
        } else {
            ApplicationManager.displayPopUp("No Users", "It seems like there are no users in the database.\nPlease register first.", "error");
        }
    }

    /**
     * Directs the user to the register screen.
     * @param event When the user clicks the Register button.
     */
    @FXML
    public void toRegisterScreen(ActionEvent event) {
        changeScreen(event, "/seng202/team6/view/registerScreen2.fxml", "REGISTER");
    }

    /**
     * Darkens the button.
     * @param event User enters button area with mouse.
     */
    @FXML
    public void darkenButton(Event event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color:rgb(51,145,133);");
    }

    /**
     * Lightens the button.
     * @param event User exits button area with mouse.
     */
    @FXML
    public void lightenButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color:rgb(63,179,164);");
    }
}
