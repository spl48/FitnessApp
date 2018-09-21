package seng202.team6.analysis;

import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;

import java.util.ArrayList;
import java.util.Arrays;


import static java.lang.Math.*;


public class ActivityAnalysis {

    private static ArrayList<String> runningWords = new ArrayList<String>(Arrays.asList("run", "running", "jog", "jogging"));
    private static ArrayList<String> walkingWords = new ArrayList<String>(Arrays.asList("walk", "walking", "hike", "hiking"));
    private static ArrayList<String> cyclingWords = new ArrayList<String>(Arrays.asList("bike", "biking", "cycle", "cycling"));

    public static int findMaximumHeartRate(Activity activity) {
        int maxHeartRate = 0;
        int currentHeartRate;

        for (ActivityDataPoint dataPoint : activity.getActivityData()) {
            currentHeartRate = dataPoint.getHeartRate();
            if (currentHeartRate > maxHeartRate) {
                maxHeartRate = currentHeartRate;
            }
        }

        return maxHeartRate;
    }

    public static int findMinimumHeartRate(Activity activity) {
        if (activity.getActivityData().size() == 0) {
            return 0;
        }
        ArrayList<ActivityDataPoint> dataPoints = activity.getActivityData();
        int minHeartRate = dataPoints.get(0).getHeartRate();
        int currentHeartRate;

        for (ActivityDataPoint dataPoint : dataPoints) {
            currentHeartRate = dataPoint.getHeartRate();
            if (currentHeartRate < minHeartRate && currentHeartRate > 0) {
                minHeartRate = currentHeartRate;
            }
        }

        return minHeartRate;
    }


    public static double findStepCount(Activity activity, double strideLength) {
        if (activity.getType() == "Walking" || activity.getType() == "Running") {
            int finalIndex = activity.getActivityData().size();
            double distance = findDistanceFromStart(activity, finalIndex - 1);
            return (distance / (strideLength * 0.0003048));
        }

        return 0;
    }


    /** Finds the total distance covered from the start of an activity
     * to a particular activity point at an index in that same activity
     * and returns this as a double.
     * @param activity
     * @param index the index for the activity point the distance is being calculated up to.
     * @return a double represting the distance covered
     */
    public static double findDistanceFromStart(Activity activity, int index) {

        double totalDistance = 0;
        int currentIndex = 0;

        ArrayList<ActivityDataPoint> dataPoints = activity.getActivityData();

        double currentLongitude;
        double nextLongitude;
        double currentLatitude;
        double nextLatitude;

        for (currentIndex = 0; currentIndex < index; currentIndex++) {
            currentLongitude = dataPoints.get(currentIndex).getLongitude();
            nextLongitude = dataPoints.get(currentIndex + 1).getLongitude();
            currentLatitude = dataPoints.get(currentIndex).getLatitude();
            nextLatitude = dataPoints.get(currentIndex + 1).getLatitude();

            double theta = currentLongitude - nextLongitude;
            double dist = sin(deg2rad(currentLatitude)) * sin(deg2rad(nextLatitude)) + cos(deg2rad(currentLatitude)) * cos(deg2rad(nextLatitude)) * cos(deg2rad(theta));
            dist = acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            totalDistance += dist;

        }

        return totalDistance;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
        private static double rad2deg(double rad) {
            return (rad * 180 / Math.PI);
        }


    public static double findCaloriesBurnedFromStart(double activityTime, String activityType, double userWeight) {
        double metValue;
        double calories;

        if (activityType == "Cycling") {
            metValue = 6;
        } else if (activityType == "Walking") {
            metValue = 3;
        } else if (activityType == "Running") {
            metValue = 7.5;
        } else {
            metValue = 4.5;             // Unknown (other) activity, average met Value
        }

        calories = metValue * (3.5 * userWeight / 200 * activityTime);

        if (calories < 0) {
            return 0;
        }
        return calories;
    }




    public static String getActivityType (Activity activity){


        String activityDescription = activity.getDescription();
        String [] words = activityDescription.split(" ");


        for (String word : words) {
            if (runningWords.contains(word.toLowerCase())) {
                return "Running";
            } else if (walkingWords.contains(word.toLowerCase())) {
                return "Walking";
            } else if (cyclingWords.contains(word.toLowerCase())) {
                return "Cycling";
            }
        }

        return "Other";


    }
}
