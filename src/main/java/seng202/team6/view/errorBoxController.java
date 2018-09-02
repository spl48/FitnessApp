package seng202.team6.view;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class errorBoxController {

    public static void displayErrorPopUP(String title, String message) {
        Stage errorWindow = new Stage();

        errorWindow.initModality(Modality.APPLICATION_MODAL);
        errorWindow.setTitle(title);
        errorWindow.setMinWidth(400);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("OK");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                errorWindow.close();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        errorWindow.setScene(scene);
        errorWindow.showAndWait();
    }

}
