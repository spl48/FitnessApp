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

public class NavigationController {

    public void changeScreen(Event event, String screen) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource(screen));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }

    @FXML
    public void toLoginScreen(Event event) throws IOException {
        changeScreen(event, "loginScreen.fxml");
    }

    @FXML
    public void toRegisterScreen(Event event) throws IOException {
        changeScreen(event, "registerScreen.fxml");
    }

    @FXML
    public void toHomeScreen(Event event) throws IOException {
        changeScreen(event, "HomeScreen.fxml");
    }

    @FXML
    public void toProfileScreen(Event event) throws IOException {
        changeScreen(event, "profileScreen.fxml");
    }

    @FXML
        public void toWorkoutsScreen(Event event) throws IOException {
            changeScreen(event, "WorkoutsScreenSplash.fxml");
    }

    @FXML
    public void toGoalsScreen(Event event) throws IOException {
        changeScreen(event, "GoalsScreen.fxml");
    }

    @FXML
    public void toCalendarScreen(Event event) throws IOException {
        changeScreen(event, "CalendarScreen.fxml");
    }

    @FXML
    public void toHealthScreen(Event event) throws IOException {
        changeScreen(event, "HealthScreen.fxml");
    }

    @FXML
    public void toRawDataVeiwer(Event event) throws IOException {
        changeScreen(event, "RawDataVeiwer.fxml");
    }

    @FXML
    public void toWorkoutAnalysis(Event event) throws IOException {
        changeScreen(event, "WorkoutAnalysis.fxml");
    }

    @FXML
    public void toAddWorkout(Event event) throws IOException {
        changeScreen(event, "AddWorkout.fxml");
    }

    @FXML
    public void toWorkoutManualEntry(Event event) throws IOException {
        changeScreen(event, "WorkoutManualEntry.fxml");
    }

    @FXML
    public void toUploadWorkoutFile(Event event) throws IOException {
        changeScreen(event, "WorkoutUpload.fxml");
    }
    
    
    @FXML
    public void darkenButton(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color:#85ab97;");
    }

    @FXML
    public void lightenButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color:#b2e4ca;");
    }
}
