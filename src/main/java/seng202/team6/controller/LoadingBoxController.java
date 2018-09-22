package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import seng202.team6.datahandling.ActivityManager;

import java.util.ArrayList;


/**
 * <h1>Error Box Controller</h1>
 * <p>Contains methods which initialise and display error pop ups.</p>
 */
public class LoadingBoxController extends ErrorBoxController {


    @FXML
    private ProgressBar loadingBar;

    @FXML
    private Label percentageCompleteLabel;

    private double percentComplete;

    private int maximum = 1;



    /**
     * Initialises the error pop by setting the title and message to the desired text.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        loadingBar.setProgress(0.0);
        percentComplete = 0.0;

    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public void updateLoadingProgress(int newProgress) {
        System.out.println("New Progress " + newProgress);
        percentComplete = newProgress / maximum;
        System.out.println(percentComplete);
        if (percentComplete > 1.0) {
            closeWindow();
        }
        loadingBar.setProgress(percentComplete);
        percentageCompleteLabel.setText(String.format("%.0f", percentComplete*100) + "%");
    }

    public void display() {
        this.displayErrorPopUP("", "", "loader");
    }
    
}
