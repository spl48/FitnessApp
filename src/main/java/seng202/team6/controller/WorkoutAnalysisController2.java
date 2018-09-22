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
import seng202.team6.analysis.ActivityAnalysis;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkoutAnalysisController2 extends WorkoutsNavigator {

    private DatabaseManager databaseManager = ApplicationManager.getDatabaseManager();

    /**
     * ListView of activities
     */
    @FXML
    private ListView<String> activityList;

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
     * Button that opens popup to filter the Activities that show in activityList
     */
    @FXML
    private Button filterButton;

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
     * Populates the year listView with the yearSet data, month combobox with all months in year, and day combobox with all days in the month
     */

    /**
     * Array that has all the activities the user can select to display on the graph
     */
    private ArrayList<Activity> activities = new ArrayList();
    /**
     * Initializes chart to display latest activity.
     * @throws SQLException
     */

    /**
     * List of possible data types to be displayed on the graph
     */
    ObservableList<String> dataChoices = FXCollections.observableArrayList("Distance", "Heart Rate", "Elevation", "Calories");


    private int graphCount = 0;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException {
        activityTypeSelection.setItems(dataChoices);
        activityTypeSelection.getSelectionModel().select(0);
        activities = databaseManager.getActivities(ApplicationManager.getCurrentUserID());
        ObservableList<String> availableActivities = FXCollections.observableArrayList();
        for (Activity activity : activities) { // Add all activities to available activities initially
            availableActivities.add(activity.getStartDate().toString() + " " + activity.getDescription());
        }
        ObservableList<String> items = FXCollections.observableArrayList(availableActivities);
        activityList.setItems(items);

//        if (activities.size() >= 1) {
//            populateComboBoxes();
//            //activitySelection.getSelectionModel().select(0);
//        }
        activityList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        analysisGraph.setCreateSymbols(false);

        activityList.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            Node node = event.getPickResult().getIntersectedNode();
            while (node != null && node != activityList && !(node instanceof ListCell)) {
                node = node.getParent();
            }
                // if is part of a cell or the cell,
                // handle event instead of using standard handling
        if (node instanceof ListCell) {
            // prevent further handling
            event.consume();
            removeSeries();

            ListCell cell = (ListCell) node;
            ListView lv = cell.getListView();

            // focus the listview
            lv.requestFocus();

            if (!cell.isEmpty()) {
                // handle selection for non-empty cells
                int index = cell.getIndex();
                if (cell.isSelected()) {
                    lv.getSelectionModel().clearSelection(index);
                } else {
                    lv.getSelectionModel().select(index);
                }
            }
        }
        });

    }

//    private void populateComboBoxes() {
//        yearSet.add("All");
//        for (Activity activity : activities) {
//            LocalDate date = activity.getStartDate();
//            yearSet.add(Integer.toString(date.getYear()));
//        }
//        monthSelection.getSelectionModel().select(monthChoices.get(0));
//        daySelection.getSelectionModel().select(dayChoices.get(0));
//        ObservableList<Integer> yearOptions = FXCollections.observableArrayList();
//        List yearList = new ArrayList(yearSet);
//        yearOptions.addAll(yearList);
//        yearSelection.getSelectionModel().select(yearOptions.get(0));
//        yearSelection.getItems().setAll(yearOptions);
//        monthSelection.setItems(monthChoices);
//        daySelection.setItems(dayChoices);
//    }

    @FXML
    private void graphHandler() throws SQLException {
        if (graphCount == 0) {
            newGraph();
        } else if (graphCount >= 1) {
            addSeries();
        }
        graphCount += 1;
    }

    /**
     * Creates a new graph to be displayed in the chart.
     */
    @FXML
    private void newGraph() {
        if (activities.size() >= 1) {
            int activity = activityList.getSelectionModel().getSelectedIndex();
            Activity selectedActivity = activities.get(activity);
            String seriesType = activityTypeSelection.getSelectionModel().getSelectedItem();
            if (activityList.getFocusModel().toString().equals("No Activities")) {
                ApplicationManager.displayPopUp("YA DINGUSS", "No activities to display ya friggen frig with selected dates", "error");
//            } else if (currentSeriesTypes.size() == 1 && currentSeriesTypes.get(0) == selectedActivity && curSeriesType == seriesType) {
//                ApplicationManager.displayPopUp("YA DINGUSS!", "Already displaying selected graph", "error");
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
                String errorMessage = String.format("Already displaying data for %s ya dinguss", selectedActivity.getStartDate().toString());
                ApplicationManager.displayPopUp("YA DINGUSS!", errorMessage, "error");
            }
        } else {
            ApplicationManager.displayPopUp("YA DINGUSS!", "You have no uploaded activity data. Go to workouts to upload your activities.", "error");
        }
    }

    /**
     *Adds a series of data to the chart
     * @throws SQLException
     */
    @FXML
    private void addSeries() throws SQLException {
        if (activities.size() >= 1) {
            int activity = activityList.getSelectionModel().getSelectedIndex();
            Activity selectedActivity = activities.get(activity);
            String seriesType = activityTypeSelection.getSelectionModel().getSelectedItem();
            if (!currentSeriesTypes.contains(selectedActivity) && seriesType == curSeriesType) {
                //defining the axes
                xAxis.setLabel("Time (Minutes)");
                //populating the series with data
                addData(selectedActivity);
            }
        } else {
            ApplicationManager.displayPopUp("YA DINGUSS!", "You have no uploaded activity data.\nGo to workouts to upload your activities.", "error");
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
        series.setName(selectedActivity.getStartDate().toString() + " " + activityType);
        ActivityAnalysis activityAnalysis = new ActivityAnalysis();
        for (ActivityDataPoint point : selectedActivity.getActivityData()) {
            Duration duration = Duration.between(selectedActivity.getStartTime(), point.getTime());
            double time = duration.toMillis() / 60000.0;
            //time = time / 10;
            switch (activityType) {
                case ("Heart Rate"):
                    yAxis.setLabel("Heart Rate (BPM)");
                    series.getData().add(new XYChart.Data(time, point.getHeartRate()));
                    break;
                case ("Distance"):
                    yAxis.setLabel("Total Distance (KM)");

                    int index = selectedActivity.getActivityData().indexOf(point);
                    double distance = activityAnalysis.findDistanceFromStart(selectedActivity, index);
                    series.getData().add(new XYChart.Data(time, distance));
                    break;
                case ("Elevation"):
                    yAxis.setLabel("Elevation (M)");
                    series.getData().add(new XYChart.Data(time, point.getElevation()));
                    break;
                case ("Calories"):
                    String userName = ApplicationManager.getCurrentUsername();
                    yAxis.setLabel("Calories Burned");
                    double calories = activityAnalysis.findCaloriesBurnedFromStart(duration.toMinutes(), selectedActivity.getType(), databaseManager.getUser(userName).getWeight());
                    series.getData().add(new XYChart.Data(time, calories));
                    break;
            }
        }
        currentSeriesTypes.add(selectedActivity);
        analysisGraph.getData().add(series);
    }

    public void toFilter() {
        if (activities.size() >= 1) {
            ApplicationManager.displayPopUp("test", "test", "filter");
        } else {
            ApplicationManager.displayPopUp("Error", "There is nothing to filter.", "error");
        }
    }

    private void removeSeries() {

    }

}
