package seng202.team6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.team6.controller.ApplicationManager;

import java.io.IOException;
import java.sql.SQLException;

public class CSVDemo {
    public static class Main extends Application {

        @Override
        public void start(Stage primaryStage) throws IOException, SQLException, ClassNotFoundException {
            Parent root = FXMLLoader.load(getClass().getResource("/seng202/team6/view/startScreen2.fxml"));
            primaryStage.setTitle("MATES");
            primaryStage.setScene(new Scene(root, 1366, 768));
            primaryStage.show();
            ApplicationManager.initializeApplication();
        }
        public static void main(String[] args) {
            launch(args);
        }
    }

    public static class MainMenu extends Application {
        public static void main(String[] args) {
            launch(args);
        }


        @Override
        public void start(Stage stage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setTitle("MATES");

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
