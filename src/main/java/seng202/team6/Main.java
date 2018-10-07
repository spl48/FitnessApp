package seng202.team6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.team6.controller.ApplicationManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("view/StartScreen.fxml"));
        primaryStage.setTitle("MATES");
        primaryStage.setScene(new Scene(root, 1366, 768));
        primaryStage.show();
        primaryStage.setResizable(false);
        ApplicationManager.initializeApplication();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
