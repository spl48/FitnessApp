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

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MapsController extends WorkoutsNavigator {

    @FXML
    private WebView mapWebView;

    @FXML
    private ChoiceBox<String> activitySelection;

    private WebEngine webEngine;
    private ArrayList<Activity> activities = new ArrayList();
    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();



    @FXML
    public void initialize() throws SQLException {
        activities = databaseManager.getActivities(ApplicationManager.getCurrentUserID());
        if (activities.size() >= 1) {
	        ObservableList<String> availableActivities = FXCollections.observableArrayList();
	        for (Activity activity : activities){
	            availableActivities.add(activity.getDate().toString());
	        }
	        activitySelection.setItems(availableActivities);
	        activitySelection.getSelectionModel().select(activities.get(0).getDate().toString());
        }

        webEngine = mapWebView.getEngine();
        webEngine.load(MapsController.class.getResource("map.html").toExternalForm());
    }

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

    private void displayRoute(Route newRoute) {
        String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
        webEngine.executeScript(scriptToExecute);
    }
}
