package seng202.team6.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import seng202.team6.analysis.HealthConcernChecker;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class HealthController extends GeneralScreenController {
    /**
     * Text that represent each different potential health concern
     */
    @FXML
    Text tachycardiaText, bradycardiaText, cardioVascularText;

    /**
     * Buttons that take a user to a search page specific to each
     * potential health concern
     */
    @FXML
    Button tachycardiaButton, bradycardiaButton, cardiovascularButton;

    /**
     * The type for the button that is pushed.
     * 0 : No button pushed
     * 1 : tachycardia button
     * 2 : bradycardia button
     * 3 : cardiovascular button
     */
    private static int type = 0;

    /**
     * The age of the current user
     */
    int age;

    /**
     * The current users activities
     */
    ArrayList<Activity> activities;


    /**
     * Initializes the health concern screen by displaying the text and button
     * corresponding to a particular health concerns if they are at risk for it.
     * @throws SQLException When there is an error in the database when getting usernames and/or activities.
     */
    public void initialize() throws SQLException{
        getUserDetails();

        if (HealthConcernChecker.checkTachycardia(activities, age)) {
            System.out.println(" ");
            tachycardiaText.setText("Tachycardia");
            tachycardiaButton.setVisible(true);
        }

        if(HealthConcernChecker.checkBradycardia(activities, age)) {
            bradycardiaText.setText("Bradycardia");
            bradycardiaButton.setVisible(true);
        }

        if (HealthConcernChecker.checkCardiovascularMortality(activities, age)) {
            cardioVascularText.setText("Cardiovascular Disease");
            cardiovascularButton.setVisible(true);
        }

    }


    /**
     * Gets a the current users age and activities
     * @throws SQLException When there is an error in the database when getting usernames and/or activities.
     */
    private void getUserDetails() throws SQLException{
        DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();
        String userName = ApplicationManager.getCurrentUsername();
        User user = databaseManager.getUser(userName);
        age = user.getAge();
        activities = databaseManager.getActivitiesWithRecords(ApplicationManager.getCurrentUserID());

    }

    /**
     * Sets the type of the button pushed to 1 (tachycardia button)
     * Redirects user to the web search screen.
     * @param event When the user clicks the tachycardiaButton.
     */
    public void toTachycardiaWebSearchScreen(ActionEvent event) {
        type = 1;
        changeScreen(event, "/seng202/team6/view/WebSearch.fxml");
    }


    /**
     * Sets the type of the button pushed to 2 (bradycardia button)
     * Redirects user to the web search screen.
     * @param event When the user clicks the bradycardiaButton.
     */
    public void toBradycardiaWebSearchScreen(ActionEvent event) {
        type = 2;
        changeScreen(event, "/seng202/team6/view/WebSearch.fxml");
    }


    /**
     * Sets the type of the button pushed to 3 (cardiovascular button)
     * Redirects user to the web search screen.
     * @param event When the user clicks the cardiovascularButton.
     */
    public void toCardiovascularWebSearchScreen(ActionEvent event) {
        type = 3;
        changeScreen(event, "/seng202/team6/view/WebSearch.fxml");
    }


    /**
     * Returns the type of the button pushed
     * @return an int indicating the button that was pushed
     */
    public static int getType() {
        return type;
    }
}
