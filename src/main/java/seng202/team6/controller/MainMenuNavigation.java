package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;


/**
 * <h1>Main Menu Navigation Controller</h1>
 * <p>Main application navigation using side bar.</p>
 */
public class MainMenuNavigation extends GUIUtilities {

    /**
     * The home button on the side bar.
     */
    @FXML
    private Button homeButton;

    /**
     * The selected menu item on the side bar.
     */
    private static Button selected;

    /**
     * Initialising the selected menu item to home by default when first loaded and applying formatting.
     */
    @FXML
    void initialize() {
        if (selected == null) {
            selected = homeButton;
        }
        selected.setStyle("-fx-background-color:#85ab97; -fx-background-radius: 0;");

        homeButton.setStyle("-fx-normal-background: #b2e4ca; -fx-hovered-background: #85ab97; -fx-background-radius: 0;");
    }

    /**
     * Changes the application screen and changes the currently selected menu item.
     * @param event When a menu item on the side bar is pressed.
     * @param screen The screen to change to.
     */
    private void changeMenuScreen(ActionEvent event, String screen) {
        changeSelected(event);
        changeScreen(event, screen);
    }

    /**
     * Directs the user to the Home Screen.
     * @param event When the HOME menu item on the side bar is pressed.
     */
    @FXML
    public void toHomeScreen(ActionEvent event) {
        changeMenuScreen(event, "/seng202/team6/view/HomeScreen.fxml");
        selected.setStyle("-fx-background-color:#85ab97; -fx-background-radius: 0;");
    }

    /**
     * Directs the user to the Profile Screen.
     * @param event When the PROFILE menu item on the side bar is pressed.
     */
    @FXML
    public void toProfileScreen(ActionEvent event) {
        changeMenuScreen(event, "/seng202/team6/view/profileScreen.fxml");
    }

    /**
     * Directs the user to the Workouts Splash Screen.
     * @param event When the WORKOUTS menu item on the side bar is pressed.
     */
    @FXML
        public void toWorkoutsScreen(ActionEvent event) {
        changeMenuScreen(event, "/seng202/team6/view/WorkoutsScreenSplash.fxml");
    }

    /**
     * Directs the user to the Goals Screen.
     * @param event When the GOALS menu item on the side bar is pressed.
     */
    @FXML
    public void toGoalsScreen(ActionEvent event) {
        changeMenuScreen(event, "/seng202/team6/view/GoalsScreen.fxml");
    }

    /**
     * Directs the user to the Calendar Screen.
     * @param event When the CALENDAR menu item on the side bar is pressed.
     */
    @FXML
    public void toCalendarScreen(ActionEvent event) {
        changeMenuScreen(event, "/seng202/team6/view/CalendarScreen.fxml");
    }

    /**
     * Directs the user to the Health Screen.
     * @param event When the HEALTH menu item on the side bar is pressed.
     */
    @FXML
    public void toHealthScreen(ActionEvent event) {
        changeMenuScreen(event, "/seng202/team6/view/HealthScreen.fxml");
        selected.setStyle("-fx-background-color:#85ab97; -fx-background-radius: 0;");
    }


    /**
     * Changes the selected menu item and formats it accordingly.
     * @param event When a new menu item is clicked.
     */
    @FXML
    public void changeSelected(ActionEvent event) {
        selected.setStyle("-fx-background-color:#b2e4ca; -fx-background-radius: 0;");
        selected = (Button) event.getSource();
        selected.setStyle("-fx-background-color:#85ab97; -fx-background-radius: 0;");
    }

    /**
     * Darkens the menu item in the side bar to indicate that the is on that item..
     * @param event User enters menu item area with mouse.
     */
    @FXML
    public void darkenButton(Event event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color:#85ab97; -fx-background-radius: 0;");
    }

    /**
     * Returns the menu item back to normal formatting ensures it does not change the selected item.
     * @param event User exits menu item area with mouse.
     */
    @FXML
    public void lightenButton(MouseEvent event){
        if (selected == null) {
            selected = homeButton;
        }
        Button btn = (Button) event.getSource();
        if (!btn.getId().equals(selected.getId())) {
            btn.setStyle("-fx-background-color:#b2e4ca; -fx-background-radius: 0;");
        }
    }
}
