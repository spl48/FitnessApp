package seng202.team6.analysis;

import seng202.team6.controller.ApplicationManager;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.models.Activity;
import seng202.team6.models.Profile;
import seng202.team6.models.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class implements HealthConcernChecker and checks if a user has tachycardia, Bradycardia and
 * cardiovascular mortality.
 * @author
 * @version 1.1, Aug 2018.
 */
public class HealthConcernChecker {


    /**
     *A function which checks if a user is at risk for Tachycardia. Returns true if so, false otherwise.
//!!     * @param profile The users profile.
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


    private static boolean checkTachycardiaThreshold(int heartRateThreshold, ArrayList<Activity> activities) {
        for(Activity activity : activities) {
            if (activity.getType() == "Walking" && activity.getMinHeartRate() >= heartRateThreshold) { // User is at risk for Tachycardia
                return true;
            }
        }
        return false;
    }


    /**
     * A function which checks if a user is at risk for Bradycardia. Returns true if so, false otherwise.
//!!     * @param profile The users profile.
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

    private static boolean determineBradycardiaOutcome (int heartRateThreshold, ArrayList<Activity> activities) {
        for (Activity activity : activities) {
            if (activity.getType() == "Walking" && activity.getMinHeartRate() < heartRateThreshold) { // User is at risk for Bradycardia
                return true;
            }
        }
        return false;
    }


    /**
     * A function which checks if a user is at risk for Cardiovascular Mortaility. Returns true if so, false otherwise.
//!!     * @param profile The users profile.
     * @return A boolean expression for if a user is at risk for Bradycardia.
     */
    public static boolean checkCardiovascularMortality(ArrayList<Activity> activities, int age) {
        if (age >= 18) { // User is a child
            for (Activity activity : activities) {
                if (activity.getMinHeartRate() > 83) { // User is at risk for Cardiovascular mortality
                    return true;
                }
            }
        }
        return false;
    }

}
