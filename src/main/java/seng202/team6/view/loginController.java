package seng202.team6.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import seng202.team6.controller.ApplicationManager;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.view.GUIUtilities;

import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class loginController extends GUIUtilities{

    @FXML
    private Button startUser;

    @FXML
    private GridPane profileGrid;

    private static Button selected;
    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();
    


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException, ClassNotFoundException {
        //selected = startUser;
        int index = 0;
        ArrayList<String> usernames = databaseManager.getUsernames();
        for (String user : usernames) {
            addProfile(profileGrid, index++, user);
        }
        
        //ObservableList<String> availableChoices = FXCollections.observableArrayList("lem72", "rch141", "gon12", "dla72", "spl8");
       // signInSelection.setItems(availableChoices);
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        String userProfile = selected.getText();
        System.out.println("User signed in: " + userProfile);
        ApplicationManager.setCurrentUser(userProfile);
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
    
    private String getButtonStyle(String borderWidth) {
        return "-fx-border-width:{}; " +
        "-fx-border-color: transparent transparent rgb(200,200,200) transparent;" +
        "-fx-background-radius: 0;" +
        "-fx-background-color: transparent;".format(borderWidth);
    }

    private ColumnConstraints getColumnConstraints() {
        ColumnConstraints colConstraint = new ColumnConstraints();
        colConstraint.setHgrow(Priority.SOMETIMES);
        colConstraint.setMaxWidth(Double.NEGATIVE_INFINITY);
        colConstraint.setMinWidth(10.0);
        colConstraint.setPrefWidth(210.0);
        return colConstraint;
    }

    private void addProfilePic(GridPane grid, int columnInd) {
        Circle profileCircle = new Circle();
        profileCircle.setFill(Paint.valueOf("rgb(210,219,234)"));
        profileCircle.setRadius(70.0);
        profileCircle.setStroke(Paint.valueOf("#8a8a8a00"));
        profileCircle.setStrokeType(StrokeType.INSIDE);
        grid.setHalignment(profileCircle, HPos.CENTER);
        grid.setValignment(profileCircle, VPos.CENTER);
        grid.add(profileCircle, columnInd, 0);

        ImageView imgView = new ImageView();
        imgView.setFitHeight(86.0);
        imgView.setFitWidth(70.0);
        grid.setHalignment(imgView, HPos.CENTER);
        grid.setValignment(imgView, VPos.CENTER);
        Image img = new Image(getClass().getResource("/seng202/team6/resources/pics/userIcon.png").toExternalForm());
        imgView.setImage(img);
        grid.add(imgView, columnInd, 0);
    }

    private void addButton(GridPane grid, String username, int columnInd) {
        Button selectProfileButton = new Button();
        if (columnInd == 1) {
            selected = selectProfileButton;
        }

        selectProfileButton.setAlignment(Pos.BOTTOM_CENTER);
        selectProfileButton.setMnemonicParsing(false);
        selectProfileButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                loginController.this.changeSelected(e);
            }
        });
        selectProfileButton.setPrefHeight(226.0);
        selectProfileButton.setPrefWidth(180.0);
        selectProfileButton.setStyle("-fx-border-width:1; " +
                "-fx-border-color: transparent transparent rgb(200,200,200) transparent;" +
                "-fx-background-radius: 0;" +
                "-fx-background-color: transparent;");
        selectProfileButton.setText(username);
        selectProfileButton.setTextFill(Paint.valueOf("#727272"));

        selectProfileButton.setFont(new Font("Nexa Bold", 23.0));
        selectProfileButton.setCursor(Cursor.CLOSED_HAND);

        grid.setHalignment(selectProfileButton, HPos.CENTER);
        grid.setValignment(selectProfileButton, VPos.CENTER);
        grid.setRowSpan(selectProfileButton, 2);
        grid.add(selectProfileButton, columnInd, 0);
    }


    
    private void addProfile(GridPane grid, int columnInd, String username) {
        grid.addColumn(columnInd);
        grid.getColumnConstraints().add(getColumnConstraints());
        addProfilePic(grid, columnInd);
        addButton(grid, username, columnInd);
        //Label testLabel = new Label("Testing");
        //grid.add(testLabel, 1, 0);
    }



}
