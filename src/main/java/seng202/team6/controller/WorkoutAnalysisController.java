package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.*;

import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

public class WorkoutAnalysisController extends WorkoutsNavigator {

    /**
     * The webview which contains the map incformation.
     */
    @FXML
    private WebView mapWebView;

    /**
     * Labels to display stats of an activity.
     */
    @FXML
    private Label distanceLabel, velocityLabel, stepsLabel, heartRateLabel;

    /**
     * The default selected tab.
     */
    private String selectedtab = "Graph";

    /**
     * The web engine for the maps.
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
     * ListView of activities
     */
    @FXML
    private ListView<String> activityList;

    /**
     * x axis of graph
     */
    @FXML
    private NumberAxis xAxis;

    /**
     * y axis of graph
     */
    @FXML
    private NumberAxis yAxis;
    /**
     * The line chart that displays the activity data
     */
    @FXML
    private LineChart<Number,Number> analysisGraph;

    /**
     * Array that stores the Activity's that are currently being displayed in the graph
     */
    private ArrayList<Activity> currentSeriesTypes = new ArrayList();

    /**
     * A string of the current data type being displayed on the graph. E.g "Heart rate", "Distance", etc.
     */
    private String curSeriesType;

    /**
     * A choice box to select the data type to be displayed on graph. E.g "Heart rate", "Distance", etc.
     */
    @FXML
    private ChoiceBox<String> activityTypeSelection;

    /**
     * Array list of current series being displayed on the plot area.
     */
    private ArrayList<XYChart.Series> seriesArrayList = new ArrayList();

    /**
     * Tracks the index of the last selected activity in the listView.
     */
    private int selectionIndex;

    /**
     * List of possible data types to be displayed on the graph
     */
    ObservableList<String> dataChoices = FXCollections.observableArrayList("Distance", "Heart Rate", "Elevation", "Calories");

    /**
     * A count of the number of graphs being displayed on the plot area.
     */
    private int graphCount = 0;

    /**
     * Current User
     */
    private User currUser;

    /**
     * The number of activities which the user has in the database under their id.
     */
    private int numActivities = ApplicationManager.getDatabaseManager().getActivityManager().getNumberUserActivities();

    /**
     * Initializes chart to display latest activity.
     * @throws SQLException
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException {

        // Sets the activity type selection
        activityTypeSelection.setItems(dataChoices);
        activityTypeSelection.getSelectionModel().select(0);
        currUser = databaseManager.getUserReader().getUserFromID(ApplicationManager.getCurrentUserID());

        // Resets the filter values.
        resetFilters();

        // Updates the list view.
        updateListView();

        // Initialises the graph component.
        initGraphs();

        webEngine = mapWebView.getEngine();
        webEngine.load(getClass().getResource("/seng202/team6/resources/map.html").toExternalForm());
    }


    private void updateListView() throws SQLException {

        // Sets the activity array and creates an array of strings for these
        activities = databaseManager.getActivityManager().getFilteredFullActivties(yearFilter, monthFilter, dayFilter, typeFilter);
        ObservableList<String> availableActivities = FXCollections.observableArrayList();

        // Checks if there are activities available
        if (activities.size() > 0) {
            for (Activity activity : activities) { // Add all activities to available activities initially
                availableActivities.add(activity.getStartDate().toString() + " " + activity.getDescription());
            }
            // Adds the activities to the list view.
            activityList.setItems(FXCollections.observableArrayList(availableActivities));;
            activityList.setMouseTransparent( false );
            activityList.setFocusTraversable( true );
            selectionIndex = 0;
            selectNewActivity();
        // If activities not available then locks up list view and clears the labels. Cleans the graph
        } else {
            // Sets the activity list to notify that there are no activities available.
            activityList.setItems(FXCollections.observableArrayList("No Activities Available"));

            // Clears the label text.
            distanceLabel.setText("");
            velocityLabel.setText("");
            heartRateLabel.setText("");
            stepsLabel.setText("");

            // Clears the graph and locks up the list view.
            clearGraph();
            activityList.setMouseTransparent( true );
            activityList.setFocusTraversable( false );
        }
    }

    /**
     * Upon selection, decides whether plot area is empty or has at least one graph.
     * Calls function accordingly.
     * @throws SQLException
     */
    @FXML
    private void selectNewActivity() throws SQLException {

        if (activities.size() > 0) {
            Activity selectedActivity = activities.get(selectionIndex);

            String distanceString = String.format("%.1f", selectedActivity.findDistanceFromStart(selectedActivity.getActivityData().size() - 1));
            String velocityString = String.format("%.1f", selectedActivity.findAverageSpeed());
            String stepsString = String.format("%.0f", selectedActivity.findStepCount(currUser.getWalkingStrideLength()));
            String hrString = String.format("%.0f", (double) selectedActivity.getMaxHeartRate());

            distanceLabel.setText(distanceString);
            velocityLabel.setText(velocityString);
            stepsLabel.setText(stepsString);
            heartRateLabel.setText(hrString);

            if (selectedtab == "Graph") {
                if (graphCount == 0) {
                    newGraph();
                    graphCount += 1;
                } else if (graphCount >= 1) {
                    addSeries();
                }
            } else {
                activityTypeSelection.setVisible(false);
                activityList.getSelectionModel().select(selectionIndex);
                Activity desiredActivity = activities.get(selectionIndex);
                Route route = makeRoute(desiredActivity);
                displayRoute(route);
            }
        }
    }

    /**
     * Clears the list view.
     */
    @FXML
    private void clearList() {
        activityList.getSelectionModel().clearSelection();
    }

    /**
     * Creates a new graph to be displayed in the chart.
     */
    @FXML
    private void newGraph() {
        if (numActivities >= 1) {
            int activity = selectionIndex;
            Activity selectedActivity = activities.get(selectionIndex);
            String seriesType = activityTypeSelection.getSelectionModel().getSelectedItem();
            if (activityList.getFocusModel().toString().equals("No Activities")) {
                ApplicationManager.displayPopUp("Oh Mate!", "You have no activities with the selected dates mate", "error");
            } else if (!currentSeriesTypes.contains(activity) || currentSeriesTypes.size() > 1) {
                currentSeriesTypes.clear();
                analysisGraph.getData().clear();
                curSeriesType = seriesType;
                try {
                    addSeries();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                String errorMessage = String.format("Already displaying data for %s mate", selectedActivity.getStartDate().toString());
                ApplicationManager.displayPopUp("Oh Mate!", errorMessage, "error");
            }
        } else {
            ApplicationManager.displayPopUp("Oh Mate!", "You have no uploaded activity data. Go to workouts to upload your activities.", "error");
        }
    }

    /**
     *Adds a series of data to the chart
     * @throws SQLException
     */
    @FXML
    private void addSeries() throws SQLException {
        if (numActivities >= 1) {
            int activity = selectionIndex;
            if (activity == -1) {
                System.out.println("-1");
                removeData(activities.get(selectionIndex));
                graphCount -= 1;
            } else if (currentSeriesTypes.contains(activities.get(activity))) {
                removeData(activities.get(selectionIndex));
                graphCount -= 1;
            } else {
                Activity selectedActivity = activities.get(activity);
                String seriesType = activityTypeSelection.getSelectionModel().getSelectedItem();

                if (!currentSeriesTypes.contains(selectedActivity) && seriesType == curSeriesType) {
                    //defining the axes
                    xAxis.setLabel("Time (Minutes)");
                    //populating the series with data
                    addData(selectedActivity);
                    graphCount += 1;
                }
            }
        } else {
            ApplicationManager.displayPopUp("Oh Mate!", "You have no uploaded activity data.\nGo to workouts to upload your activities.", "error");
        }
    }

    /**
     * Adds the required data to the current series to be displayed on the chart.
     * @param selectedActivity The activity whose data will be added to the chart
     * @throws SQLException
     */
    public void addData(Activity selectedActivity) throws SQLException {
        //defining a series
        XYChart.Series series = new XYChart.Series();
        String activityType = activityTypeSelection.getSelectionModel().getSelectedItem();
        series.setName(selectedActivity.getStartDate() + " " + selectedActivity.getDescription());
        for (ActivityDataPoint point : selectedActivity.getActivityData()) {
            Duration duration = Duration.between(selectedActivity.getStartTime(), point.getTime());
            double time = duration.toMillis() / 60000.0;

            switch (activityType) {
                case ("Heart Rate"):
                    yAxis.setLabel("Heart Rate (BPM)");
                    series.getData().add(new XYChart.Data(time, point.getHeartRate()));
                    break;
                case ("Distance"):
                    yAxis.setLabel("Total Distance (KM)");

                    int index = selectedActivity.getActivityData().indexOf(point);
                    double distance = selectedActivity.findDistanceFromStart(index);
                    series.getData().add(new XYChart.Data(time, distance));
                    break;
                case ("Elevation"):
                    yAxis.setLabel("Elevation (M)");
                    series.getData().add(new XYChart.Data(time, point.getElevation()));
                    break;
                case ("Calories"):
                    String userName = ApplicationManager.getCurrentUsername();
                    yAxis.setLabel("Calories Burned");
                    double calories = selectedActivity.findCaloriesBurnedFromStart(duration.toMinutes(),databaseManager.getUserReader().getUser(userName).getWeight());
                    series.getData().add(new XYChart.Data(time, calories));
                    break;
            }
        }
        currentSeriesTypes.add(selectedActivity);
        analysisGraph.getData().add(series);
        seriesArrayList.add(series);
    }

    /**
     * Opens filter pop up screen
     */
    public void toFilter() throws SQLException {
        if (numActivities >= 1) {
            ApplicationManager.displayPopUp("test", "test", "filter");
            updateListView();
            clearGraph();
        } else {
            ApplicationManager.displayPopUp("Error", "There is nothing to filter.", "error");
        }
    }

    /**
     * Sets the filter values.
     * @param newDayFilter The day filter value.
     * @param newMonthFilter The month filter value.
     * @param newYearFilter The year filter value.
     * @param newTypeFilter The type filter value.
     */
    public static void setFilters(String newDayFilter, String newMonthFilter, String  newYearFilter, String newTypeFilter) {
        dayFilter = newDayFilter;
        monthFilter = newMonthFilter;
        yearFilter = newYearFilter;
        typeFilter = newTypeFilter;
    }


    /**
     * Removes the selected activity from the plot area
     * @param selectedActivity
     */
    private void removeData(Activity selectedActivity) {
        for (int i = 0; i < currentSeriesTypes.size(); i++) {
            if ((seriesArrayList.get(i).getName()).equals(selectedActivity.getStartDate() + " " + selectedActivity.getDescription())) {
                analysisGraph.getData().remove(seriesArrayList.get(i));
                currentSeriesTypes.remove(selectedActivity);
                seriesArrayList.remove(seriesArrayList.get(i));
            }
        }
    }

    /**
     * Clears the plot area and deselects all the currently selected activities from the listView.
     */
    @FXML
    private void clearGraph() {
        analysisGraph.getData().clear();
        graphCount = 0;
        analysisGraph.getYAxis().setLabel("");
        analysisGraph.getXAxis().setLabel("");
        currentSeriesTypes.clear();
        seriesArrayList.clear();
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
        activityTypeSelection.setVisible(false);
        selectedtab = "Map";
        activityList.getSelectionModel().clearSelection();
        activityList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        if (activities.size() >= 1) {
            activityList.getSelectionModel().select(selectionIndex);
            Activity desiredActivity = activities.get(selectionIndex);
            Route route = makeRoute(desiredActivity);
            displayRoute(route);

        } else {
            ApplicationManager.displayPopUp("Oh Mate!", "You have no uploaded activity data.\nGo to workouts to upload your activities.", "error");
        }
    }

    /**
     * Checks if user has any activities and displays the selected activity if yes, otherwise displays popup informing user to upload data
     */
    @FXML
    private void initGraphs() throws SQLException {

        if (activities.size() > 0) {
            activityTypeSelection.setVisible(true);
            selectedtab = "Graph";
            clearGraph();
            activityList.getSelectionModel().select(selectionIndex);
            selectNewActivity();
            activityList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            analysisGraph.setCreateSymbols(false);
            activityList.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                Node node = event.getPickResult().getIntersectedNode();
                while (node != null && node != activityList && !(node instanceof ListCell)) {
                    node = node.getParent();
                }
                if (node instanceof ListCell) {
                    // prevent further handling
                    event.consume();
                    ListCell cell = (ListCell) node;
                    ListView lv = cell.getListView();
                    // focus the listview
                    lv.requestFocus();
                    if (!cell.isEmpty()) {
                        // handle selection for non-empty cells
                        int index = cell.getIndex();
                        if (cell.isSelected()) {
                            lv.getSelectionModel().clearSelection(index);
                            selectionIndex = index;
                        } else {
                            lv.getSelectionModel().select(index);
                            selectionIndex = index;
                        }
                    }
                }
            });
        }
    }


    /**
     * Displays the given out
     * @param newRoute route to be displayed
     */
    private void displayRoute(Route newRoute) {
        String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
        try {
            webEngine.executeScript(scriptToExecute);
        } catch(netscape.javascript.JSException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Oh Mate!", "You need an internet connection to use the maps feature","error");
        }
    }

    public void closeGraphs() {

    }

}
