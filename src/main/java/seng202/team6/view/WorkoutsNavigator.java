package seng202.team6.view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class WorkoutsNavigator {

    public void changeScreen(Event event, String screen) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource(screen));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }

    @FXML
        public void toWorkoutsScreen(Event event) throws IOException {
        changeScreen(event, "WorkoutsScreenSplash.fxml");
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

}
