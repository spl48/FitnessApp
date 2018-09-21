package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seng202.team6.datahandling.ActivityManager;

import java.io.IOException;
import java.util.*;


/**
 * <h1>Error Box Controller</h1>
 * <p>Contains methods which initialise and display error pop ups.</p>
 */
public class FilterBoxController extends ErrorBoxController {

    /** Year selection choice box. */
    @FXML
    private ChoiceBox yearSelect;

    /** Month selection choice box. */
    @FXML
    private ChoiceBox monthSelect;

    /** Day selection choice box. */
    @FXML
    private ChoiceBox daySelect;

    /** Type selection choice box. */
    @FXML
    private ChoiceBox typeSelect;

    /**
     * List of all months as strings
     */
    private ObservableList<String> monthChoices = FXCollections.observableArrayList("All", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    /**
     * List of days in a month as Strings up to day 29
     */
    private ObservableList<String> dayChoices = FXCollections.observableArrayList("All", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29");

    /**
     * List of activity types
     */
    private ObservableList<String> activityTypes = FXCollections.observableArrayList("Walking", "Running", "Biking");

    /**
     * Activity Manager
     */
    private ActivityManager activityManager;


    /**
     * Initialises the error pop by setting the title and message to the desired text.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        activityManager = ApplicationManager.getDatabaseManager().getActivityManager();

        // Initialises the filtering drop down options.
        ArrayList<Integer> years = activityManager.getPossibleYears();
        ObservableList<Integer> yearOptions = FXCollections.observableArrayList(years);
        yearSelect.setItems(yearOptions);

        monthSelect.setItems(monthChoices);
        daySelect.setItems(dayChoices);
        typeSelect.setItems(activityTypes);

    }
    
}
