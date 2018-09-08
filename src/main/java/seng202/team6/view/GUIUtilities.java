package seng202.team6.view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import static javafx.scene.paint.Color.rgb;

public class GUIUtilities {

    @FXML
    public void darkenButton(Event event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color:rgb(51,145,133);");
    }

    @FXML
    public void lightenButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color:rgb(63,179,164);");
    }

    @FXML
    public void darkenCircle(Event event) {
        Circle circle = (Circle) event.getSource();
        circle.setFill(rgb(63,179,164));
    }

    @FXML
    public void lightenCircle(MouseEvent event){
        Circle circle = (Circle) event.getSource();
        circle.setFill(rgb(51,145,133));
    }
}
