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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuNavigation {

    @FXML
    private Button homeButton;

    private static Button selected;

    public void changeScreen(Event event, String screen) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource(screen));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }

    public void changeMenuScreen(Event event, String screen) throws IOException {
        changeScreen(event, screen);
        changeSelected(event);
    }

    @FXML
    public void toHomeScreen(Event event) throws IOException {
        changeMenuScreen(event, "HomeScreen.fxml");
    }

    @FXML
    public void toProfileScreen(Event event) throws IOException {
        changeMenuScreen(event, "profileScreen.fxml");
    }

    @FXML
        public void toWorkoutsScreen(Event event) throws IOException {
        changeMenuScreen(event, "WorkoutsScreenSplash.fxml");
    }

    @FXML
    public void toGoalsScreen(Event event) throws IOException {
        changeMenuScreen(event, "GoalsScreen.fxml");
    }

    @FXML
    public void toCalendarScreen(Event event) throws IOException {
        changeMenuScreen(event, "CalendarScreen.fxml");
    }

    @FXML
    public void toHealthScreen(Event event) throws IOException {
        changeMenuScreen(event, "HealthScreen.fxml");
    }

    @FXML
    public void changeSelected(Event event) {
        selected.setStyle("-fx-background-color:#b2e4ca; -fx-background-radius: 0;");
        selected = (Button) event.getSource();
        selected.setStyle("-fx-background-color:#85ab97; -fx-background-radius: 0;");
    }
    
    @FXML
    public void darkenButton(Event event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color:#85ab97; -fx-background-radius: 0;");
    }

    @FXML
    public void lightenButton(MouseEvent event){
        if (selected == null) {
            selected = homeButton;
        }
        Button btn = (Button) event.getSource();
        if (!btn.getId().equals(selected.getId())) {
            btn.setStyle("-fx-background-color:#b2e4ca; -fx-background-radius: 0;");
        }
    }
}
