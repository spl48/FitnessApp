package seng202.team6.controller;

import com.jfoenix.controls.JFXButton;
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
    private JFXButton homeButton;

    /**
     * The selected menu item on the side bar.
     */

    @FXML
    private JFXButton profileButton;

    @FXML
    private JFXButton workoutsButton;

    @FXML
    private JFXButton calendarButton;

    @FXML
    private JFXButton goalsButton;

    @FXML
    private JFXButton healthButton;

    /**
     * Initialising the selected menu item to home by default when first loaded and applying formatting.
     */
    @FXML
    void initialize() {

    }


    /**
     * Directs the user to the Home Screen.
     * @param event When the HOME menu item on the side bar is pressed.
     */
    @FXML
    public void toHomeScreen(ActionEvent event) {
        changeScreen(event, "/seng202/team6/view/HomeScreen.fxml");
    }

    /**
     * Directs the user to the Profile Screen.
     * @param event When the PROFILE menu item on the side bar is pressed.
     */
    @FXML
    public void toProfileScreen(ActionEvent event) {
        changeScreen(event, "/seng202/team6/view/profileScreen.fxml");
    }

    /**
     * Directs the user to the Workouts Splash Screen.
     * @param event When the WORKOUTS menu item on the side bar is pressed.
     */
    @FXML
        public void toWorkoutsScreen(ActionEvent event) {
        changeScreen(event, "/seng202/team6/view/WorkoutsScreenSplash.fxml");
    }

    /**
     * Directs the user to the Goals Screen.
     * @param event When the GOALS menu item on the side bar is pressed.
     */
    @FXML
    public void toGoalsScreen(ActionEvent event) {
        changeScreen(event, "/seng202/team6/view/GoalsScreen.fxml");

    }

    /**
     * Directs the user to the Calendar Screen.
     * @param event When the CALENDAR menu item on the side bar is pressed.
     */
    @FXML
    public void toCalendarScreen(ActionEvent event) {
        changeScreen(event, "/seng202/team6/view/CalendarScreen.fxml");

    }

    /**
     * Directs the user to the Health Screen.
     * @param event When the HEALTH menu item on the side bar is pressed.
     */
    @FXML
    public void toHealthScreen(ActionEvent event) {
        changeScreen(event, "/seng202/team6/view/HealthScreen.fxml");
 ;
    }
}
