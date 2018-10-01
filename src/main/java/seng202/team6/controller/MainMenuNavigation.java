package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 * <h1>Main Menu Navigation Controller</h1>
 * <p>Main application navigation using side bar.</p>
 */
public class MainMenuNavigation extends GeneralScreenController {

    /**
     * The home button on the side bar.
     */
    @FXML
    private Button homeButton;

    /**
     * The home button on the side bar.
     */
    @FXML
    private Button profileButton, goalsButton, calendarButton, workoutsButton, healthButton;

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
        System.out.println("Selected ID: " + selected.getId());
        selected.setStyle("-fx-background-color:#85ab97; -fx-background-radius: 0;");
        selected.setVisible(true);
    }

    public void tutorial() {
        if (selected.getId().equals(homeButton.getId())) {
            ApplicationManager.displayPopUp("MATES Help", "'HOME'\n\n" +
                    "The top left corner of the Home Screen shows your BMI and potential health issues.", "tutorialsmall");
            ApplicationManager.displayPopUp("MATES Help", "'HOME'\n\n" +
                    "The top right corner of the Home Screen shows the steps taken and the remaining steps to reach your goal.", "tutorialsmall");
            ApplicationManager.displayPopUp("MATES Help", "'HOME'\n\n" +
                    "The bottom right corner shows your latest activity analysis assuming that you have uploaded an activity data already.", "tutorialbig");
            ApplicationManager.displayPopUp("MATES Help", "'HOME'\n\n" +
                    "The bottom left corner is where you will see me. I will be giving you motivational quotes because I believe in you. Not because I’m programmed to but because I want to.", "tutorialbig");
            ApplicationManager.displayPopUp("MATES Help", "If you want to get help from me again, feel free to click the photo on the sidebar menu" +
                    " and I will give you a detailed tutorial for the page you are on.\n\n"
                    + "I hope I helped!", "tutorialbig");
        } else if (selected.getId().equals(profileButton.getId())) {
            ApplicationManager.displayPopUp("MATES Help", "'PROFILE'\n\n" +
                    "This is where you will see your personal information and body measurements.", "tutorialsmall");
            ApplicationManager.displayPopUp("MATES Help", "'PROFILE'\n\n" +
                    "On the bottom right will be the edit button where I will help you change your information.", "tutorialsmall");
            ApplicationManager.displayPopUp("MATES Help", "If you want to get help from me again, feel free to click the photo on the sidebar menu" +
                    " and I will give you a detailed tutorial for the page you are on.\n\n"
                    + "I hope I helped!", "tutorialbig");
        } else if (selected.getId().equals(workoutsButton.getId())) {
            ApplicationManager.displayPopUp("MATES Help", "'WORKOUTS'\n\n" +
                    "ADD WORKOUT is where you can upload your activity data manually through a form or through a file.", "tutorialsmall");
            ApplicationManager.displayPopUp("MATES Help", "'WORKOUTS'\n\n" +
                    "Raw data viewer shows all your activity log and allows you to update specific information.", "tutorialsmall");
            ApplicationManager.displayPopUp("MATES Help", "'WORKOUTS'\n\n" +
                    "Analysis is where I show you your activity analysis and lets me help you compare multiple activities at the same time.", "tutorialsmall");
            ApplicationManager.displayPopUp("MATES Help", "If you want to get help from me again, feel free to click the photo on the sidebar menu" +
                    " and I will give you a detailed tutorial for the page you are on.\n\n"
                    + "I hope I helped!", "tutorialbig");
        } else if (selected.getId().equals(calendarButton.getId())) {
            ApplicationManager.displayPopUp("MATES Help", "'CALENDAR'\n\n" +
                    "Displays the calendar view of the activities.\nYou can view specific activities you have done on each day by clicking on the day in the calendar.", "tutorialbig");
            ApplicationManager.displayPopUp("MATES Tutorial", "If you want to get help from me again, feel free to click the photo on the sidebar menu" +
                    " and I will give you a detailed tutorial for the page you are on.\n\n"
                    + "I hope I helped!", "tutorialbig");
        } else if (selected.getId().equals(goalsButton.getId())) {
            ApplicationManager.displayPopUp("MATES Help", "'GOALS'\n\n" +
                    "is where I can help you set specific target goals for yourself by clicking on the edit button at the bottom left of the specific goal.", "tutorialbig");
            ApplicationManager.displayPopUp("MATES Help", "If you want to get help from me again, feel free to click the photo on the sidebar menu" +
                    " and I will give you a detailed tutorial for the page you are on.\n\n"
                    + "I hope I helped!", "tutorialbig");
        } else if (selected.getId().equals(healthButton.getId())) {
            ApplicationManager.displayPopUp("MATES Help", "'HEALTH'\n\n" +
                    "displays your potential health issues based on your activity data.", "tutorialsmall");
            ApplicationManager.displayPopUp("MATES Help", "'HEALTH'\n\n" +
                    "You can also search health related questions in the search box and press enter after.\nI will direct you to the answers of your inquiry.", "tutorialbig");
            ApplicationManager.displayPopUp("MATES Help", "If you want to get help from me again, feel free to click the photo on the sidebar menu" +
                    " and I will give you a detailed tutorial for the page you are on.\n\n"
                    + "I hope I helped!", "tutorialbig");
        }
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
        changeMenuScreen(event, "/seng202/team6/view/GoalsScreen2.fxml");
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
//        System.out.println(selected.getScene());
//        Scene selectedScene = (Scene) selected.getParent();
//        selectedScene.();

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
