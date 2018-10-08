package seng202.team6.utilities;

import seng202.team6.controller.ApplicationManager;

/**
 * ActivityValidation handles all the Activity validation before being inputted in the database.
 */
public class ActivityValidation {
    /**
     * Validates if the given description is too long
     * @param description The string to become the description
     * @return True if the length of the description is 30 or less.
     */
    public static boolean validateDescription(String description) {
        Boolean result = false;
        if (description.length() > 30) {
            if(ApplicationManager.getCurrentUserID() != 0) {
                ApplicationManager.displayPopUp("Invalid Data", "Please ensure the description is less than 30 characters", "error");
            }
        } else {
            result = true;
        }
        return result;
    }

    /**
     * Validates if the given note is too long
     * @param notes The string to become the activity note
     * @return True if the length of the note is less than 111.
     */
    public static boolean validateNotes(String notes) {
        boolean valid = true;
        if (notes != null && notes.length() >= 111) {
            if(ApplicationManager.getCurrentUserID() != 0) {
                ApplicationManager.displayPopUp("Invalid Data", "Make sure that notes is not longer than 110 characters.", "error");
            }
                System.out.println("Notes is too long");
                valid = false;
        }
        return valid;
    }

    /**
     * Validates if the given distance is too long or short
     * @param distance The distance double to be validated.
     * @return True if the distance is greater than 0 and less than 1000 km.
     */
    public static boolean validateDistance(double distance){
        if(!(distance >= 0 && distance <= 1000)){
            System.out.println("Invalid distance detected! Distance out of range (maximum 1000 km!).");
            if(ApplicationManager.getCurrentUserID() != 0) {
                ApplicationManager.displayPopUp("Invalid Data", "Make sure distance is in range (0 to 1000)!", "error");
            }
            return false;
        }
        else{
            return true;
        }
    }
}
