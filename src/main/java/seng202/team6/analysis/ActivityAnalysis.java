package seng202.team6.analysis;

import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import seng202.team6.models.User;


import java.util.ArrayList;


import static java.lang.Math.*;


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

    public double findStepCount(Activity activity, double strideLength) {
        //todo change this to only count run and walk steps!
        System.out.println(activity.getType());
        int finalIndex = activity.getActivityData().size();
        double distance = findDistanceFromStart(activity, finalIndex - 1);
        System.out.println(distance / (strideLength * 0.0003048));
        return (distance / (strideLength * 0.0003048));
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

    /** Finds the total distance covered from the start of an activity
     * to a particular activity point at an index in that same activity
     * and returns this as a double.
     * @param activity
     * @param index the index for the activity point the distance is being calculated up to.
     * @return a double represting the distance covered
     */
    public double findDistanceFromStart(Activity activity, int index) {

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

    public double findCaloriesBurned(Activity activity, User user) {
        double metValue;
        double calories;
        double userWeight = user.getWeight();
        long activityTime = activity.getTotalTime();
        String activityType = activity.getType();

        if (activityType == "Walking") {
            metValue = 4.3;
        } else if (activityType == "Running") {
            metValue = 9.8;
        } else {
            metValue = 8;
        }

        calories = metValue * 3.5 * userWeight / 200 * activityTime / 1000;

        return calories;
    }

    public double findCaloriesBurnedFromStart(double time, double heartRate, User user) {

        double calories = 0;
        double userWeight = user.getWeight();
        String gender = user.getGender();
        double userAge = user.getAge();

        if (gender == "Male") {
            calories = ((userAge * 0.2017) - (userWeight * 0.09036) + (heartRate * 0.6309) - 55.0969) * time / 4.184;
        } else {
            calories = ((userAge * 0.074) - (userWeight * 0.05741) + (heartRate * 0.4472) - 20.4022) * time / 4.184;
        }

        return calories;
    }

    public static double calculateAverageSpeed(Activity activity) {
        return (activity.getTotalTime() / 60) / (activity.getDistance());
    }


    public static String getActivityType (Activity activity){
        double averageSpeed = calculateAverageSpeed(activity);
        int brisk_walking_pace = 5;
        int fast_running_pace = 12;

        if (averageSpeed <= brisk_walking_pace) {
            return "Walking";
        } else if (averageSpeed <= fast_running_pace) {
            return "Running";
        } else {
            return "Cycling";
        }



    }
}
