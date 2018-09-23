package seng202.team6.analysis;

import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;

import java.util.ArrayList;
import java.util.Arrays;


import static java.lang.Math.*;

/**
 * This class contains methods to analyse a
 * users single activity
 */
public class ActivityAnalysis {

    /**
     * Key words that indicate an activity is of type "Running"
     */
    private static ArrayList<String> runningWords = new ArrayList<String>(Arrays.asList("run", "running", "jog", "jogging"));

    /**
     * Key words that indicate an activity is of type "Walking"
     */
    private static ArrayList<String> walkingWords = new ArrayList<String>(Arrays.asList("walk", "walking", "hike", "hiking"));

    /**
     * Key words that indicate an activity is of type "Cycling"
     */
    private static ArrayList<String> cyclingWords = new ArrayList<String>(Arrays.asList("bike", "biking", "cycle", "cycling"));



    /**
     * Finds and returns the maximum heart rate recorded in
     * an activity.
     * @param activity the activity that is being analysed
     * @return an int representing the maximum heart rate
     */
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



    /**
     * Finds and returns the minimum heart rate recorded in
     * an activity.
     * @param activity the activity that is being analysed
     * @return an int representing the minimum heart rate
     */
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



    /**
     * Finds the total steps taken in a given activity, if
     * that activity is of type "Running" or "Walking", otherwise
     * returns a step count of 0
     * @param activity the activity for which the step count is being calculated.
     * @param strideLength the user of the activities stride length in feet
     * @return a double representing the number of steps taken
     */
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
     * @param activity the activity for which the total distance is being calculated
     * @param index the index for the activity point the distance is being calculated up to.
     * @return a double representing the distance covered
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
            double distance = sin(deg2rad(currentLatitude)) * sin(deg2rad(nextLatitude)) + cos(deg2rad(currentLatitude)) * cos(deg2rad(nextLatitude)) * cos(deg2rad(theta));
            distance = acos(distance);
            distance = (distance * 180 / Math.PI);
            distance = distance * 60 * 1.1515;
            distance = distance * 1.609344;
            totalDistance += distance;

        }

        return totalDistance;
    }

    /**
     * Converts a given angle in degrees, to radians
     * @param degrees the angle in degrees
     * @return a double representing the angle in radians
     */
    private static double deg2rad(double degrees) {
        return (degrees * Math.PI / 180.0);
    }



    /**
     * Finds the calories burned during an activity of a given activity time,
     * using the met value for that activities type.
     * @param activityTime the duration of the activity
     * @param activityType the type of the activity
     * @param userWeight the weight of the user who participated in the activity
     * @return a double representing the calories burned in the activity
     */
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


    /**
     * Calculates and returns the average speed
     * of a users activity in km per hour.
     * @param activity the activity for which the speed is being calculated
     * @return a double representing the activities average speed
     */
    public static double findAverageSpeed(Activity activity) {
        double activityTime = activity.getTotalTime();
        int activityLength = activity.getActivityData().size();
        double activityDistance = ActivityAnalysis.findDistanceFromStart(activity , activityLength - 1);

        return (activityDistance / (activityTime / 60));
    }



    /**
     * Determines an activities type by checking if it contains a
     * 'key word'. If no key word is found the activity type
     * is "other".
     * @param activity the activity for which the type is being found
     * @return a string representing the activity type
     */
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


    /**
     * Determines an activities type by checking if it contains a
     * 'key word'. If no key word is found the activity type
     * is "other".
     * @param activityDescription the description of the activity.
     * @return a string representing the activity type
     */
    public static String getActivityTypeFromDescription (String activityDescription){

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
