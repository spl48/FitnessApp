package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import seng202.team6.datahandling.ActivityManager;

import java.util.*;


/**
 * <h1>Filter Box Controller</h1>
 * <p>Contains methods which initialise and filter selection pop ups.</p>
 */
public class FilterBoxController extends PopUpBoxController {

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
    private ObservableList<String> activityTypes = FXCollections.observableArrayList("All", "Walking", "Running", "Biking", "Other");


    /**
     * Initialises the filter pop by setting the filter fields to the corresponding choices.
     */
    @FXML
    void initialize() {

        // Gets the activity data manager
        ActivityManager activityManager = ApplicationManager.getDatabaseManager().getActivityManager();

        // Gets the possible years to be selected based on data in database.
        ArrayList<String> years = activityManager.getPossibleYears();
        ObservableList<String> yearOptions = FXCollections.observableArrayList(years);
        yearOptions.add(0, "All");

        // Setting and initialising the filtering drop down options.
        yearSelect.setItems(yearOptions);
        yearSelect.getSelectionModel().select(yearOptions.get(0));
        monthSelect.setItems(monthChoices);
        monthSelect.getSelectionModel().select(monthChoices.get(0));
        daySelect.setItems(dayChoices);
        daySelect.getSelectionModel().select(dayChoices.get(0));
        typeSelect.setItems(activityTypes);
        typeSelect.getSelectionModel().select(activityTypes.get(0));
    }

    /**
     * Sets the filters of the class concerned and closes the pop up.
     */
    @FXML
    public void setRawDataFilters() {
        String dayFilter = (String) daySelect.getSelectionModel().getSelectedItem();
        String monthFilter = padMonth(Integer.toString(monthSelect.getSelectionModel().getSelectedIndex()));
        String yearFilter = (String) yearSelect.getSelectionModel().getSelectedItem();
        String typeFilter = (String) typeSelect.getSelectionModel().getSelectedItem();
        RawDataController.setFilters(dayFilter, monthFilter, yearFilter, typeFilter);
        WorkoutAnalysisController.setFilters(dayFilter, monthFilter, yearFilter, typeFilter);
        closeWindow();
    }

    /**
     * Takes in the index of the month selected and either identifies it as all, pads it with an extra zero for
     * a single character string to match the database or returns the index as is.
     * @param monthNum The index of the month in the month select array.
     * @return The newly formatted month value ready to query the database.
     */
    private String padMonth(String monthNum) {
        if (monthNum.equals("0")) {
            return "All";
        } else if (monthNum.length() == 1) {
            return "0" + monthNum;
        }
        return monthNum;
    }
    
}
