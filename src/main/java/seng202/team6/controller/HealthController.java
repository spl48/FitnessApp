package seng202.team6.controller;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class HealthController extends GUIUtilities{
    @FXML
    public void toWebSearchScreen(ActionEvent event) {
        changeScreen(event, "../view/WebSearch.fxml");
    }
}
