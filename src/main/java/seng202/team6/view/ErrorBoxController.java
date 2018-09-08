package seng202.team6.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ErrorBoxController {

    private Stage errorWindow;

    @FXML
    private Label errorText, errorTitle;

    private static String errorMessage, errorTitleText;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        errorText.setText(errorMessage);
        errorTitle.setText(errorTitleText);
        errorText.setWrapText(true);
    }


    public void displayErrorPopUP(String title, String message) throws IOException {
        errorMessage = message.toUpperCase();
        errorTitleText = title.toUpperCase();
        errorWindow = new Stage();
        errorWindow.initModality(Modality.APPLICATION_MODAL);

        errorWindow.setMinWidth(400);
        errorWindow.initStyle(StageStyle.UNDECORATED);
//        System.out.println(message.toUpperCase())

//        Label label = new Label();
//        label.setText(message);
//        Button closeButton = new Button("OK");

//        closeButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                errorWindow.close();
//            }
//        });
//
//        VBox layout = new VBox(10);
//        layout.getChildren().addAll(label, closeButton);
//        layout.setAlignment(Pos.CENTER);

        Parent root = FXMLLoader.load(getClass().getResource("errorPopUp.fxml"));
        Scene scene = new Scene(root, 400, 350);
        errorWindow.setScene(scene);
        errorWindow.showAndWait();

    }

    @FXML
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) errorText.getScene().getWindow();
        stage.close();
    }


}
