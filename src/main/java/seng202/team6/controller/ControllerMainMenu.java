package seng202.team6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng202.team6.view.MainMenu;

public class ControllerMainMenu {
    @FXML private BorderPane borderPane;

    public void initialize() {

//        eyesView.setFitHeight(768);
//        eyesView.setFitWidth(220);
//        borderPane.setLeft(eyesView);
        borderPane.setLeft(Menu());
    }

    private VBox Menu() {
        VBox vbox = new VBox();
        Image eyes = new Image(MainMenu.class.getResource("/seng202/team6/resources/pics/eyes.png").toExternalForm());
        ImageView eyesView = new ImageView(eyes);
        vbox.getChildren().add(eyesView);
        for (int i = 1; i < 6; i++) {
            vbox.getChildren().add(Item(String.valueOf(i)));
        }
        vbox.setStyle("-fx-background-color:#b2e4ca");
        return vbox;
    }

    private HBox Item(String icon) {
        Image image = new Image(MainMenu.class.getResource("/seng202/team6/resources/mainmenu/" + icon + ".png").toExternalForm());
        ImageView imageView = new ImageView(image);
        Button btn = new Button();
        btn.setGraphic(imageView);
        btn.setPrefSize(220,80);
        btn.setStyle("-fx-background-color:#b2e4ca;" +
                     "-fx-background-radius: 0;");
        menuDecorator(btn);

        HBox hbox = new HBox(btn);
        return hbox;
    }

    private void menuDecorator(Button btn) {
        /*btn.setOnMouseEntered(value -> {
            btn.setStyle("-fx-background-color:#85ab97;" +
                    "-fx-background-radius: 0;");
        });
        btn.setOnMouseExited(value -> {
            btn.setStyle("-fx-background-color:#b2e4ca;" +
                    "-fx-background-radius: 0;");
        });*/
    }

}
