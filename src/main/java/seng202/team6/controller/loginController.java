package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import seng202.team6.datahandling.DatabaseManager;


import java.sql.SQLException;
import java.util.ArrayList;


/**
 * <h1>Login Controller</h1>
 * <p>Sets up the login screen and dynamically sets the user profile display depending on the users
 * currently within the database. Logs the user in when desired user selected.</p>
 */
public class loginController extends GUIUtilities{

    /**
     * The grid which the profiles from the database will be displayed.
     */
    @FXML
    private GridPane profileGrid;

    /**
     * The currently selected user profile button.
     */
    private static Button selected;

    /**
     * The Application's database manager.
     */
    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();


    /**
     * Initialises the profile selection screen by adding profiles into grid and setting the selected formatting.
     * @throws SQLException When there is an error in the database when getting usernames.
     * @throws ClassNotFoundException Error when connecting when getting usernames.
     */
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        // Populates the grid with user profiles from the database.
        int index = 0;
        ArrayList<String> usernames = databaseManager.getUsernames();
        for (String user : usernames) {
            addProfile(profileGrid, index++, user);
        }

        // Sets the style of the selected user profile.
        selected.setStyle(getButtonStyle("4"));
        selected.setFont(Font.font("Nexa Bold", 23));
    }

    /**
     * Sets the current user property of ApplicationManager and directs the user to the Home Screen.
     * @param event When the login button is clicked.
     */
    @FXML
    public void login(ActionEvent event) {

        // Sets the current user profile.
        String userProfile = selected.getText();
        ApplicationManager.setCurrentUser(userProfile);

        // Directs to the Home Screen.
        changeScreen(event, "/seng202/team6/view/HomeScreen.fxml");
    }


    /**
     * Redirects user back to the start screen.
     * @param event When the user clicks the back button.
     */
    @FXML
    public void toStartScreen(Event event) {
        changeScreen(event, "/seng202/team6/view/startScreen2.fxml");
    }


    /**
     * Changes the selected user profile when clicked and formats accordingly.
     * @param event When a user profile is clicked.
     */
    @FXML
    private void changeSelected(Event event) {

        // Reverts previously selected back to normal formatting.
        selected.setStyle(getButtonStyle("1"));
        selected.setFont(Font.font("Nexa Light", 23));

        // Sets new selected profile and its new formatting.
        selected = (Button) event.getSource();
        selected.setStyle(getButtonStyle("4"));
        selected.setFont(Font.font("Nexa Bold", 23));
    }


    /**
     * Given a border width returns a formatting string for the user profile selection button.
     * @param borderWidth The width of the bottom border.
     * @return A formatting style string for a profile selection button.
     */
    private String getButtonStyle(String borderWidth) {
        return "-fx-border-width:" + borderWidth + ";" +
        "-fx-border-color: transparent transparent rgb(200,200,200) transparent;" +
        "-fx-background-radius: 0;" +
        "-fx-background-color: transparent;";
    }


    /**
     * Gets the column constraints for a user profile column.
     * @return The column constrains for a user profile column.
     */
    private ColumnConstraints getColumnConstraints() {
        ColumnConstraints colConstraint = new ColumnConstraints();
        colConstraint.setHgrow(Priority.SOMETIMES);
        colConstraint.setMaxWidth(Double.NEGATIVE_INFINITY);
        colConstraint.setMinWidth(10.0);
        colConstraint.setPrefWidth(210.0);
        return colConstraint;
    }


    /**
     * Adds a profile image to the grid in a specified column index.
     * @param grid The profile selection grid.
     * @param columnInd The desired column index for the profile image to be placed.
     */
    private void addProfilePic(GridPane grid, int columnInd) {

        // Makes the circle
        Circle profileCircle = new Circle();
        profileCircle.setFill(Paint.valueOf("rgb(210,219,234)"));
        profileCircle.setRadius(70.0);
        profileCircle.setStroke(Paint.valueOf("#8a8a8a00"));
        profileCircle.setStrokeType(StrokeType.INSIDE);
        GridPane.setHalignment(profileCircle, HPos.CENTER);
        GridPane.setValignment(profileCircle, VPos.CENTER);
        grid.add(profileCircle, columnInd, 0); // Adds the circle to the grid.

        // Gets the image and sets constraints to go on top of the circle.
        ImageView imgView = new ImageView();
        imgView.setFitHeight(86.0);
        imgView.setFitWidth(70.0);
        GridPane.setHalignment(imgView, HPos.CENTER);
        GridPane.setValignment(imgView, VPos.CENTER);
        Image img = new Image(getClass().getResource("/seng202/team6/resources/pics/userIcon.png").toExternalForm());
        imgView.setImage(img);
        grid.add(imgView, columnInd, 0); // Adds the image to the grid.
    }


    /**
     * Adds the selection button for the user profile to the grid.
     * @param grid The user profile grid.
     * @param username The username associated with the profile.
     * @param columnInd The desired column for the button to be placed.
     */
    private void addButton(GridPane grid, String username, int columnInd) {

        // Initialises a new selection button and sets it to the selected default only if it is in the first column.
        Button selectProfileButton = new Button();
        if (columnInd == 0) {
            selected = selectProfileButton;
        }

        // Sets the formatting of the selection button.
        selectProfileButton.setAlignment(Pos.BOTTOM_CENTER);
        selectProfileButton.setMnemonicParsing(false);
        selectProfileButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                loginController.this.changeSelected(event);
            }
        });
        selectProfileButton.setPrefHeight(226.0);
        selectProfileButton.setPrefWidth(180.0);
        selectProfileButton.setStyle(getButtonStyle("1"));
        selectProfileButton.setText(username);
        selectProfileButton.setTextFill(Paint.valueOf("#727272"));
        selectProfileButton.setFont(new Font("Nexa Bold", 23.0));
        selectProfileButton.setCursor(Cursor.CLOSED_HAND);

        // Sets the layout constraints of the button.
        GridPane.setHalignment(selectProfileButton, HPos.CENTER);
        GridPane.setValignment(selectProfileButton, VPos.CENTER);
        GridPane.setRowSpan(selectProfileButton, 2);

        // Adds the button to the grid.
        grid.add(selectProfileButton, columnInd, 0);
    }


    /**
     * Adds a full profile selection box to the grid.
     * @param grid The profile selection grid.
     * @param columnInd The desired column index where the profile is placed.
     * @param username The username assiciated with the profile.
     */
    private void addProfile(GridPane grid, int columnInd, String username) {
        //Adds the new column, setting the constraints and formatting.
        grid.addColumn(columnInd);
        grid.getColumnConstraints().add(getColumnConstraints());

        // Adds the profile image.
        addProfilePic(grid, columnInd);

        // Adds the selection button.
        addButton(grid, username, columnInd);
    }



}
