package seng202.team6.analysis;

import seng202.team6.controller.ApplicationManager;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityList;
import seng202.team6.models.Profile;
import seng202.team6.models.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class implements ProfileAnalysis and analyses a users activities.
 * @author
 * @version 1.1, Aug 2018.
 */
public class ProfileAnalysis {

    /**
     * A function that returns the body mass index of a user.
     * @param user the user for which the BMI is being calculated.
     * @return a double that is a users BMI.
     */
    public double calculateBMI(User user) {
        double weight = user.getWeight();
        double height = user.getHeight() / 100;

        return weight / (height * height);
    }

    public String analyseBMI(double BMI) {
        if (BMI < 18.5) {
            return "UnderWeight";
        } else if (BMI < 24.9) {
            return "Healthy Weight";
        } else if (BMI < 29.9) {
            return "OverWeight";
        } else {
            return "Obese";
        }
    }

    public double findTotalStepCount(ArrayList<Activity> activities, double strideLength) throws SQLException{
        double stepCount = 0;

        ActivityAnalysis activityAnalysis = new ActivityAnalysis();

        for (Activity activity : activities) {
            stepCount += activityAnalysis.findStepCount(activity, strideLength);
        }

        return stepCount;

    }


    /**
     * A function which calculates and returns a users maximum heart rate through all logged runs, walks and cycles.
     * @param profile The profile of a user for which the maximum heart rate is being calculated.
     * @return A integer that is the maximum heart rate of a user.
     */
    public int findMaximumHeartRate(Profile profile) {
        int maxHeartRate = 0;
        int currentHeartRate;

         ArrayList<Activity> runningData = profile.getRunningData().getActivities();
         ArrayList<Activity> walkingData = profile.getWalkingData().getActivities();
         ArrayList<Activity> cyclingData = profile.getBikingData().getActivities();

         for (Activity run : runningData) {
            currentHeartRate = run.getMaxHeartRate();
            if (currentHeartRate > maxHeartRate) {
                maxHeartRate = currentHeartRate;
            }
         }

         for (Activity walk : walkingData) {
            currentHeartRate = walk.getMaxHeartRate();
            if (currentHeartRate > maxHeartRate) {
                maxHeartRate = currentHeartRate;
            }
         }

         for (Activity cycle : cyclingData) {
            currentHeartRate = cycle.getMaxHeartRate();
            if (currentHeartRate > maxHeartRate) {
                maxHeartRate = currentHeartRate;
            }
         }

        return maxHeartRate;
    }

    /**
     * A function which calculates and returns a users maximum heart rate through all logged runs, walks and cycles.
     * @param profile The profile of a user for which the maximum heart rate is being calculated.
     * @return A integer that is the maximum heart rate of a user.
     */
    public int findMinimumHeartRate(Profile profile) {
        int minHeartRate = 0;
        int currentHeartRate;

         ArrayList<Activity> runningData = profile.getRunningData().getActivities();
         ArrayList<Activity> walkingData = profile.getWalkingData().getActivities();
         ArrayList<Activity> cyclingData = profile.getBikingData().getActivities();

         minHeartRate = runningData.get(0).getMinHeartRate();

         for (Activity run : runningData) {
            currentHeartRate = run.getMinHeartRate();
            if (currentHeartRate < minHeartRate) {
                minHeartRate = currentHeartRate;
            }
         }

         for (Activity walk : walkingData) {
            currentHeartRate = walk.getMinHeartRate();
            if (currentHeartRate > minHeartRate) {
                minHeartRate = currentHeartRate;
            }
         }

         for (Activity cycle : cyclingData) {
            currentHeartRate = cycle.getMinHeartRate();
            if (currentHeartRate > minHeartRate) {
                minHeartRate = currentHeartRate;
            }
         }

        return minHeartRate;
    }


    /**
     * A function that calculates and returns the total distance a user has travelled through running, walking,
     * and cycling.
     * @param profile The profile for the user that the total distance is being calculated.
     * @return A double which is the total distance a user has traveled.
     */
    public double findTotalDistance (Profile profile) {
        int totalDistance = 0;

        ArrayList<Activity> runningData = profile.getRunningData().getActivities();
        ArrayList<Activity> walkingData = profile.getWalkingData().getActivities();
        ArrayList<Activity> cyclingData = profile.getBikingData().getActivities();

        for (Activity run : runningData) {
            totalDistance += run.getDistance();
         }

         for (Activity walk : walkingData) {
            totalDistance += walk.getDistance();
         }

         for (Activity cycle : cyclingData) {
            totalDistance += cycle.getDistance();
         }

        return totalDistance;

    }



    /**
     * A function that determines and returns the maximum step count from all a users activities.
     * @param profile The profile for the user that the maximum step count is being calculated.
     * @return A double which is a users maximum step count.
     */
    public double findMaximumStepCount (Profile profile){
        return 1;
    }

}
