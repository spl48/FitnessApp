package seng202.team6.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import java.io.IOException;

public class loginController {

    @FXML
    private ChoiceBox<String> signInSelection;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("lem72", "rch141", "gon12", "dla72", "spl8");
        signInSelection.setItems(availableChoices);
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        String userProfile = signInSelection.getSelectionModel().getSelectedItem();
        System.out.println("User signed in: " + userProfile);
        Parent loginParent = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }


    @FXML
    public void toStartScreen(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("startScreen.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }

}
