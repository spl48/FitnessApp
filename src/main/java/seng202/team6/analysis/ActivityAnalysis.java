package seng202.team6.analysis;

import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import seng202.team6.models.User;

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

    public double findStepCount(Activity activity, double strideLength) {
        //todo change this to only count run and walk steps!
        System.out.println(activity.getType());
        int finalIndex = activity.getActivityData().size();
        double distance = findDistanceFromStart(activity, finalIndex - 1);
        return (distance * 1000 / strideLength * 30.48);
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

    public double findDistanceFromStart(Activity activity, int index) {
        double latitudeDist;
        double longitudeDist;
        double totalDistance = 0;
        ArrayList<ActivityDataPoint> dataPoints = activity.getActivityData();
        int currentIndex = 0;

        for (currentIndex = 0; currentIndex < index; currentIndex++) {
            latitudeDist = abs(dataPoints.get(currentIndex + 1).getLatitude() - dataPoints.get(currentIndex).getLatitude());
            longitudeDist = abs(dataPoints.get(currentIndex + 1).getLongitude() - dataPoints.get(currentIndex).getLongitude());

            totalDistance = totalDistance + latitudeDist + longitudeDist;
        }

        return totalDistance*100;
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

    public double findCaloriesBurnedFromStart(long activityTime, double heartRate, User user) {

        double calories = 0;
        double userWeight = user.getWeight();
        String gender = user.getGender();
        double userAge = user.getAge();

        if (gender == "Male") {
            calories = ((userAge * 0.2017) - (userWeight * 0.09036) + (heartRate * 0.6309) - 55.0969) * activityTime / 4.184;
        } else {
            calories = ((userAge * 0.074) - (userWeight * 0.05741) + (heartRate * 0.4472) - 20.4022) * activityTime / 4.184;
        }

        return calories;
    }


    public String getActivityType (Activity activity){
        double averageSpeed = (activity.getTotalTime() / 60) / (activity.getDistance()); // in km per hour
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
