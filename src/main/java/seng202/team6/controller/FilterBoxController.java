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
    private ObservableList<String> activityTypes = FXCollections.observableArrayList("All", "Walking", "Running", "Biking");

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
        ArrayList<String> years = activityManager.getPossibleYears();
        ObservableList<String> yearOptions = FXCollections.observableArrayList(years);
        yearSelect.setItems(yearOptions);
        yearSelect.getSelectionModel().select(yearOptions.get(0));

        monthSelect.setItems(monthChoices);
        monthSelect.getSelectionModel().select(monthChoices.get(0));
        daySelect.setItems(dayChoices);
        daySelect.getSelectionModel().select(dayChoices.get(0));
        typeSelect.setItems(activityTypes);
        typeSelect.getSelectionModel().select(activityTypes.get(0));

    }

    @FXML
    public void setRawDataFilters() {
        String dayFilter = (String) daySelect.getSelectionModel().getSelectedItem();
        String monthFilter = padMonth(Integer.toString(monthSelect.getSelectionModel().getSelectedIndex()));
        String yearFilter = (String) yearSelect.getSelectionModel().getSelectedItem();
        String typeFilter = (String) typeSelect.getSelectionModel().getSelectedItem();
        RawDataController2.setFilters(dayFilter, monthFilter, yearFilter, typeFilter);
        closeWindow();
    }

    private String padMonth(String monthNum) {
        if (monthNum.equals("0")) {
            return "All";
        } else if (monthNum.length() == 1) {
            return "0" + monthNum;
        }
        return monthNum;
    }
    
}
