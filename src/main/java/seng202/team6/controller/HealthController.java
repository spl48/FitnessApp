package seng202.team6.controller;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import seng202.team6.analysis.ActivityAnalysis;
import seng202.team6.analysis.HealthConcernChecker;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.User;

import java.sql.SQLException;

public class HealthController extends GUIUtilities{
    public int currentUserId = ApplicationManager.getCurrentUserID();

    public DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();

    @FXML
    Text tachycardiaText;
    @FXML
    Text bradycardiaText;
    @FXML
    Text cardioVascularText;
    @FXML
    Button tachycardiaButton;
    @FXML
    Button bradycardiaButton;
    @FXML
    Button cardiovascularButton;

    private static int type = 0;

    @FXML
    public void toTachycardiaWebSearchScreen(ActionEvent event) {
        type = 1;
        changeScreen(event, "/seng202/team6/view/WebSearch.fxml");
    }

    public void toBradycardiaWebSearchScreen(ActionEvent event) {
        type = 2;
        changeScreen(event, "/seng202/team6/view/WebSearch.fxml");
    }

    public void toCardiovascularWebSearchScreen(ActionEvent event) {
        type = 3;
        changeScreen(event, "/seng202/team6/view/WebSearch.fxml");
    }

    public void initialize() throws SQLException{
        if (HealthConcernChecker.checkTachycardia()) {
            tachycardiaText.setText("Tachycardia");
            tachycardiaButton.setVisible(true);
        }
        if(HealthConcernChecker.checkBradycardia()) {
            bradycardiaText.setText("Bradycardia");
            bradycardiaButton.setVisible(true);
        }
        if (HealthConcernChecker.checkCardiovascularMortality()) {
            cardioVascularText.setText("Cardiovascular Disease");
            cardiovascularButton.setVisible(true);
        }
    }

    public static int getType() {
        return type;
    }
}
