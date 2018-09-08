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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;

public class loginController extends GUIUtilities{

    @FXML
    private Button startUser;

    private static Button selected;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        selected = startUser;
        //ObservableList<String> availableChoices = FXCollections.observableArrayList("lem72", "rch141", "gon12", "dla72", "spl8");
       // signInSelection.setItems(availableChoices);
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        String userProfile = selected.getText();
        System.out.println("User signed in: " + userProfile);
        Parent loginParent = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }


    @FXML
    public void toStartScreen(Event event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("startScreen2.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
        appStage.show();
    }

    @FXML
    public void changeSelected(Event event) {
        selected.setStyle("-fx-border-width:1; " +
                          "-fx-border-color: transparent transparent rgb(200,200,200) transparent;" +
                          "-fx-background-radius: 0;" +
                          "-fx-background-color: transparent;");
        selected.setFont(Font.font("Nexa Light", 23));
        selected = (Button) event.getSource();
        selected.setStyle("-fx-border-width:4; " +
                "-fx-border-color: transparent transparent rgb(200,200,200) transparent;" +
                "-fx-background-radius: 0;" +
                "-fx-background-color: transparent;");
        selected.setFont(Font.font("Nexa Bold", 23));
    }



}
