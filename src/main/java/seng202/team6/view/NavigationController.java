package seng202.team6.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

    public void changeScreen(ActionEvent event, String screen) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource(screen));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }

    public void changeScreenImg(MouseEvent event, String screen) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource(screen));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }

    @FXML
    public void toLoginScreen(ActionEvent event) throws IOException {
        changeScreen(event, "loginScreen.fxml");
    }

    @FXML
    public void toRegisterScreen(ActionEvent event) throws IOException {
        changeScreen(event, "registerScreen.fxml");
    }

    @FXML
    public void toHomeScreen(ActionEvent event) throws IOException {
        changeScreen(event, "HomeScreen.fxml");
    }

    @FXML
    public void toProfileScreen(ActionEvent event) throws IOException {
        changeScreen(event, "profileScreen.fxml");
    }

    @FXML
    public void toWorkoutsScreen(ActionEvent event) throws IOException {
        changeScreen(event, "WorkoutsScreenSplash.fxml");
    }

    @FXML
    public void toGoalsScreen(ActionEvent event) throws IOException {
        changeScreen(event, "GoalsScreen.fxml");
    }

    @FXML
    public void toCalendarScreen(ActionEvent event) throws IOException {
        changeScreen(event, "CalendarScreen.fxml");
    }

    @FXML
    public void toHealthScreen(ActionEvent event) throws IOException {
        changeScreen(event, "HealthScreen.fxml");
    }

    @FXML
    public void toRawDataVeiwer(MouseEvent event) throws IOException {
        changeScreenImg(event, "RawDataVeiwer.fxml");
    }

    @FXML
    public void toWorkoutAnalysis(MouseEvent event) throws IOException {
        changeScreenImg(event, "WorkoutAnalysis.fxml");
    }

    @FXML
    public void toAddWorkout(MouseEvent event) throws IOException {
        changeScreenImg(event, "AddWorkout.fxml");
    }

    @FXML
    public void toWorkoutManualEntry(MouseEvent event) throws IOException {
        changeScreenImg(event, "WorkoutManualEntry.fxml");
    }

    @FXML
    public void toUploadWorkoutFile(MouseEvent event) throws IOException {
        changeScreenImg(event, "WorkoutUpload.fxml");
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
