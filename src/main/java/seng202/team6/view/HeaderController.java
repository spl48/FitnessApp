package seng202.team6.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class HeaderController {

    @FXML
    Node profileImg;

    @FXML
    public void toStartScreen(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("startScreen2.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage appStage = (Stage) (profileImg.getScene().getWindow());
        appStage.setScene(loginScene);
        appStage.show();
    }
}
