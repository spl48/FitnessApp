package seng202.team6.view;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import javafx.scene.chart.CategoryAxis;

public class ActivityGraph extends Application {

    private Activity makeTestRun() {
        LocalDate inputDate = LocalDate.of(2018, 10, 9);
        LocalTime time1 = LocalTime.of(5, 30);
        LocalTime time2 = LocalTime.of(6, 00);
        LocalTime time3 = LocalTime.of(6, 30);
        Activity testActivity = new Activity("Running", inputDate, time1, time3, 4.00, 80, 120);
        ActivityDataPoint p1 = new ActivityDataPoint(time1, 85, -41.245120, 174.771260, 88);
        ActivityDataPoint p2 = new ActivityDataPoint(time2, 120, -32.244893, 148.608375, 100);
        ActivityDataPoint p3 = new ActivityDataPoint(time3, 111, -32.555990, 148.944830, 94);
        testActivity.addActivityData(p1);
        testActivity.addActivityData(p2);
        testActivity.addActivityData(p3);
        return testActivity;
    }


    @Override public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Time");
        //defining the axes
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Heart Rate (BPM)");
        //creating the chart
        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);

        lineChart.setTitle("Heart Rate");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data
        Activity testRun = makeTestRun();

        for (ActivityDataPoint point : testRun.getActivityData()) {

            series.getData().add(new XYChart.Data(point.getTime().toString(), point.getHeartRate()));
        }


        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

