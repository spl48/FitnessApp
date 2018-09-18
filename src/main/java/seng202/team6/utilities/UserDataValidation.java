package seng202.team6.utilities;

import java.time.LocalDate;
import java.util.Calendar;

import seng202.team6.controller.ErrorBoxController;

public class UserDataValidation {

    /**
     * The minimum length of the User's first name
     */
    private static final int MIN_NAME_LENGTH = 2;

    /**
     * The maximum length of the User's username
     */
    private static final int MAX_USERNAME_LENGTH = 12;

    /**
     * The minimum length of the User's username
     */
    private static final int MIN_USERNAME_LENGTH = 3;

    /**
     * The minimum age of the User
     */
    private static final int MIN_AGE = 5;

    private static ErrorBoxController errorBoxController = new ErrorBoxController();

    public static boolean isAlpha(String string) {

        char[] chars = string.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateUserName(String username) {
        boolean valid = false;
        if (username.length() == 0) {
            errorBoxController.displayErrorPopUP("Username Entry Invalid", "Please provide a username.", "error");
        } else if (username.length() > MAX_USERNAME_LENGTH) {
            errorBoxController.displayErrorPopUP("Username Entry Invalid", "Username too long\nPlease ensure username is less than " + MAX_USERNAME_LENGTH + " characters.", "error");
        } else {
            valid = true;
        }
        return valid;
    }

    public static boolean validateName(String name, String nameType) {
        boolean valid = false;
        String errorTitle = nameType + " Entry Invalid";
        if (name.length() < 2) {
            errorBoxController.displayErrorPopUP(errorTitle, nameType + " is too short.\nPlease ensure " + nameType + " is more than 2 characters.", "error");
        } else if (name.length() > 10) {
            errorBoxController.displayErrorPopUP(errorTitle, nameType + " is too long.\nPlease ensure " + nameType + " is less than 10 characters.", "error");
        } else if (!isAlpha(name)) {
            errorBoxController.displayErrorPopUP(errorTitle, nameType + " is of invalid type.", "error");
        } else {
            valid = true;
        }
        return valid;
    }

    public static boolean validateDoubleValue(Double value, String valueName, double upper, double lower) {
        boolean valid = false;
        String errorTitle = valueName + " Entry Invalid";
        if (value > upper || value < lower) {
            String errorMessage = valueName + " is not in range.\nPlease ensure " + valueName + " is in the range " + lower + " to " + upper;
            errorBoxController.displayErrorPopUP(errorTitle, errorMessage, "error");
        } else {
            valid = true;
        }
        return valid;
    }

    public static boolean validateBirthDate(LocalDate dob) {
       boolean valid = false;
       if (dob == null) {
           errorBoxController.displayErrorPopUP("Invalid Date of Birth", "Please provide a date of birth.", "error");
           return false;
       }
       int birthYear = dob.getYear();
       int currYear = Calendar.getInstance().get(Calendar.YEAR);
       if (birthYear > currYear - 5 || birthYear < currYear - 100) {
           errorBoxController.displayErrorPopUP("Invalid Date of Birth", "Year out of range.", "error");
       } else {
           valid = true;
       }
       return valid;
    }

    public static boolean validateGender(String gender) {
        if (gender == "") {
            errorBoxController.displayErrorPopUP("Empty Gender Field", "Please provide a gender.", "error");
            return false;
        } else {
            return gender == "Female" || gender == "Male";
        }
     }

    public static boolean validUserProfile(String userProfile) {
        boolean valid = false;
        if (userProfile == null) {
            errorBoxController.displayErrorPopUP("Error", "Please select a profile", "error");
        } else {
            valid = true;
        }
        return valid;
    }
}