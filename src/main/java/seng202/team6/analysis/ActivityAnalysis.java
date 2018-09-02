package seng202.team6.analysis;

import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import java.util.ArrayList;

import static java.lang.Math.abs;


public class ActivityAnalysis {

    public double findMaximumHeartRate(Activity activity) {
        double maxHeartRate = 0;
        double currentHeartRate;

        for (ActivityDataPoint dataPoint : activity.getActivityData()) {
            currentHeartRate = dataPoint.getHeartRate();
            if (currentHeartRate > maxHeartRate) {
                maxHeartRate = currentHeartRate;
            }
        }

        return maxHeartRate;
    }

    public double findMaximumStepCount(Activity activity) {

        return 1;
    }

    public double findTotalDistance(Activity activity) {
        double latitudeDist;
        double longitudeDist;
        double totalDistance = 0;
        ArrayList<ActivityDataPoint> dataPoints = activity.getActivityData();
        int arrayLength = activity.getActivityData().size();
        int currentIndex = 0;

        for (currentIndex = 0; currentIndex < arrayLength; currentIndex++) {
            latitudeDist = abs(dataPoints.get(currentIndex + 1).getLatitude() - dataPoints.get(currentIndex).getLatitude());
            longitudeDist = abs(dataPoints.get(currentIndex + 1).getLongitude() - dataPoints.get(currentIndex).getLongitude());

            totalDistance = totalDistance + latitudeDist + longitudeDist;
        }

        return totalDistance;
    }

    public double findTotalTime(Activity activity) {

        return 1;
    }
}
