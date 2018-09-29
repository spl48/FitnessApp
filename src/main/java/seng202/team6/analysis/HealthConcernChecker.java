package seng202.team6.analysis;


import seng202.team6.models.Activity;
import java.util.ArrayList;

/**
 * This class analyses an activity to check if the user
 * who participated in the activity is at risk for Tachycardia,
 * Bradycardia and cardiovascular concerns
 */
public class HealthConcernChecker {


    /**
      *A function which checks if a user is at risk for Tachycardia by analysing recorded
     * heart rates from their activities. Returns true if so, false otherwise.
     * @param activities an array list of users activities which are being analysed
     * @param age the age of the user
     * @return A boolean expression for if a user is at risk for Tachycardia.
     */
    public static boolean checkTachycardia(ArrayList<Activity> activities, int age) {
        if (age < 8) {
            return checkTachycardiaThreshold(133, activities);
        } else if (age < 12) {
            return checkTachycardiaThreshold(130, activities);
        } else if (age < 15) {
            return checkTachycardiaThreshold(119, activities);
        } else{
            return checkTachycardiaThreshold(100, activities);
        }
    }



    /**
     * A function that determines if the any maximum heart rate of a walking activity, from an
     * array list of activities is above the minimum threshold for Tachycardia. Returns true
     * if so, false otherwise.
     * @param heartRateThreshold the minimum heartRate in which a user is at risk for Tachycardia
     * @param activities an array list of a users activities
     * @return a boolean value indicating if a walking heart rate was above the threshold
     */
    private static boolean checkTachycardiaThreshold(int heartRateThreshold, ArrayList<Activity> activities) {
        for(Activity activity : activities) {
            double maxHeartRate = activity.getMaxHeartRate();
            if (activity.getType().equals("Walking") && maxHeartRate >= heartRateThreshold) { // User is at risk for Tachycardia
                return true;
            }
        }
        return false;
    }



    /**
     * A function which checks if a user is at risk for Bradycardia from their
     * activities. Returns true if so, false otherwise.
     * @param activities an array list of users activities which are being analysed
     * @param age the age of the user
     * @return A boolean expression for if a user is at risk for Bradycardia.
     */
    public static boolean checkBradycardia(ArrayList<Activity> activities, int age) {
        if (age < 10) { // User is a child
            return determineBradycardiaOutcome(70, activities);
        } else if (age < 18) { //User is a adolescent
            return determineBradycardiaOutcome(60, activities);
        } else { // User is an adult
            return determineBradycardiaOutcome(50, activities);
        }


    }



    /**
     * Checks if any of a users heart rates for walking activities are below a
     * given threshold, returning true if so, false otherwise.
     * @param heartRateThreshold the minimum heart rate indicating a user is at risk for Bradycardia
     * @param activities an array list of the users activities
     * @return a boolean value indicating if any recorded walking heart rates were above the threshold
     */
    private static boolean determineBradycardiaOutcome (double heartRateThreshold, ArrayList<Activity> activities) {
        for (Activity activity : activities) {
            double minimumHeartRate = activity.getMinHeartRate();
            if (activity.getType().equals("Walking") && minimumHeartRate < heartRateThreshold) { // User is at risk for Bradycardia
                return true;
            }
        }
        return false;
    }



    /**
     * A function which checks if a user may have cardiovascular concerns, buy
     * determining if any recorded heart rates from their activities are above
     * a threshold. Returns true if so, false otherwise.
     * @param activities an array list of users activities which are being analysed
     * @param age the age of the user
     * @return A boolean expression for if a user may have cardiovascular concerns.
     */
    public static boolean checkCardiovascularMortality(ArrayList<Activity> activities, int age) {
        if (age >= 18) { // User is an adult
            for (Activity activity : activities) {
                double maximumHeartRate = activity.getMaxHeartRate();
                if (activity.getType().equals("Walking") && maximumHeartRate > 83) { // User is at risk for Cardiovascular mortality
                    return true;
                }
            }
        }
        return false;
    }

}
