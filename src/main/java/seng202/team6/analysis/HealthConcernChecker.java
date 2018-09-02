package seng202.team6.analysis;

import seng202.team6.models.Activity;
import seng202.team6.models.Profile;

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
     * @param profile The users profile.
     * @return A boolean expression for if a user is at risk for Tachycardia.
     */
    public boolean checkTachycardia(Profile profile) {
        ArrayList<Activity> walkingData = profile.getWalkingData().getActivities();
        int age = profile.getUser().getAge();

//        double minHeartRateRisk = 208 - 0.7 * age;    // Lowest heart rate for which a user is at risk
//
//        for (Activity walk : walkingData) {
//            if (walk.getMinHeartRate() >= minHeartRateRisk); // User is at risk
//                return true;
//            }

        if ( age > 11 && age < 15) {
            for (Activity walk : walkingData) {
            if (walk.getMinHeartRate() >= 119); // User is at risk for Tachycardia
                return true;
            }
            return false;
        }

        if (age >= 15) {
            for (Activity walk : walkingData) {
                if (walk.getMinHeartRate() >= 100); // User is at risk for Tachycardia
                return true;
            }
            return false;
        }

        return false;
    }


    /**
     * A function which checks if a user is at risk for Bradycardia. Returns true if so, false otherwise.
     * @param profile The users profile.
     * @return A boolean expression for if a user is at risk for Bradycardia.
     */
    public boolean checkBradycardia(Profile profile) {

        ArrayList<Activity> walkingData = profile.getWalkingData().getActivities();
        int age = profile.getUser().getAge();

        if (age >= 18) { // User is an adult
            for (Activity walk : walkingData) {
                if (walk.getMinHeartRate() < 50) { // User is at risk for Bradycardia
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * A function which checks if a user is at risk for Cardiovascular Mortaility. Returns true if so, false otherwise.
     * @param profile The users profile.
     * @return A boolean expression for if a user is at risk for Bradycardia.
     */
    public boolean checkCardiovascularMortality(Profile profile) {
        if (profile.getUser().getAge() < 18) { // User is a child
            return false;
        }

        ArrayList<Activity> walkingData = profile.getWalkingData().getActivities();

        for (Activity walk : walkingData) {
            if (walk.getMinHeartRate() > 83) { // User is at risk for Cardiovascular mortality
                return true;
            }
        }

        return false;
    }
}
