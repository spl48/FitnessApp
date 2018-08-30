package seng202.team6.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class SignInController {

    @FXML
    private ChoiceBox<String> signInSelection;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("lem72", "rch141", "gon12", "dla72", "spl8");
        signInSelection.setItems(availableChoices);
    }

    public void login() {
        String userProfile = signInSelection.getSelectionModel().getSelectedItem();
        System.out.println("User signed in: " + userProfile);
    }
}
