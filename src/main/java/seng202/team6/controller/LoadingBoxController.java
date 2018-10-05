package seng202.team6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;


/**
 * <h1>Error Box Controller</h1>
 * <p>Contains methods which initialise and display error pop ups.</p>
 */
public class LoadingBoxController extends PopUpBoxController {


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

    /**
     * Sets the maximum value for loading.
     * @param maximum The maximum loading value.
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }


    /**
     * Updates the loading progress of the progress bar.
     * @param newProgress The new data value of progress made.
     */
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


    /**
     * Displays the loading pop up.
     */
    public void display() {
        this.displayPopUP("", "", "loader");
    }
    
}
