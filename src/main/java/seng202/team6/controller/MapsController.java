package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import seng202.team6.models.Position;
import seng202.team6.models.Route;

import java.sql.SQLException;
import java.util.ArrayList;

public class MapsController extends WorkoutsNavigator {

    /**
     * The webview which contains the map incformation.
     */
    @FXML
    private WebView mapWebView;

    /**
     * A choice box to select the desired activity to be displayed on graph
     */
    @FXML
    private ChoiceBox<String> activitySelection;

    /**
     * --
     */
    private WebEngine webEngine;

    /**
     * Array that has all the activities the user can select to display on the graph
     */
    private ArrayList<Activity> activities = new ArrayList();

    /**
     * The Application Database Manager.
     */
    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();


    /**
     * Initialises the map view screen by setting up the activities to be selected from.
     * @throws SQLException
     */
    @FXML
    public void initialize() throws SQLException {
        activities = databaseManager.getActivities(ApplicationManager.getCurrentUserID());
        if (activities.size() >= 1) {
	        ObservableList<String> availableActivities = FXCollections.observableArrayList();
	        for (Activity activity : activities){
	            availableActivities.add(activity.getStartDate().toString());
	        }
	        activitySelection.setItems(availableActivities);
	        activitySelection.getSelectionModel().select(activities.get(0).getStartDate().toString());
        }

        webEngine = mapWebView.getEngine();
        webEngine.load(getClass().getResource("/seng202/team6/resources/map.html").toExternalForm());
    }

    /**
     * Returns the Route of the given activity
     * @param activity
     * @return Route object of given activity's latitude and longitude coordinates
     */
    private Route makeRoute(Activity activity) {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (ActivityDataPoint point : activity.getActivityData()) {
            double lat = point.getLatitude();
            double lng = point.getLongitude();
            Position p = new Position(lat, lng);
            positions.add(p);
        }
        Route route = new Route(positions);
        return route;
    }

    /**
     * Checks if user has any activities and displays the selected activity if yes, otherwise displays popup informing user to upload data
     */
    @FXML
    private void initMap() {
    	if (activities.size() >= 1) {
	        int activity = activitySelection.getSelectionModel().getSelectedIndex();
	        Activity desiredActivity = activities.get(activity);
	        Route route = makeRoute(desiredActivity);
	        displayRoute(route);
    	} else {
        	ApplicationManager.displayPopUp("YA DINGUSS!", "You have no uploaded activity data.\nGo to workouts to upload your activities.", "error");
        }
    }

    /**
     * Displays the given out
     * @param newRoute route to be displayed
     */
    private void displayRoute(Route newRoute) {
        String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
        webEngine.executeScript(scriptToExecute);
    }
}
