package seng202.team6.utilities;

import java.time.LocalDate;
import java.util.Calendar;

import seng202.team6.view.errorBoxController;

public class DataValidation {

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
            errorBoxController.displayErrorPopUP("Username Entry Invalid", "Please provide a username.");
        } else if (username.length() > 6) {
            errorBoxController.displayErrorPopUP("Username Entry Invalid", "Username too long\nPlease ensure username is less than 6 characters.");
        } else {
            valid = true;
        }
        return valid;
    }

    public static boolean validateName(String name, String nameType) {
        boolean valid = false;
        String errorTitle = nameType + " Entry Invalid";
        if (name.length() < 2) {
            errorBoxController.displayErrorPopUP(errorTitle, nameType + " is too short.\nPlease ensure " + nameType + " is more than 2 characters.");
        } else if (name.length() > 10) {
            errorBoxController.displayErrorPopUP(errorTitle, nameType + " is too long.\nPlease ensure " + nameType + " is less than 10 characters.");
        } else if (!isAlpha(name)) {
            errorBoxController.displayErrorPopUP(errorTitle, nameType + " is of invalid type.");
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
            errorBoxController.displayErrorPopUP(errorTitle, errorMessage);
        } else {
            valid = true;
        }
        return valid;
    }

    public static boolean validateBirthDate(LocalDate dob) {
       boolean valid = false;
       if (dob == null) {
           errorBoxController.displayErrorPopUP("Invalid Date of Birth", "Please provide a date of birth.");
           return false;
       }
       int birthYear = dob.getYear();
       int currYear = Calendar.getInstance().get(Calendar.YEAR);
       if (birthYear > currYear - 5 || birthYear < currYear - 100) {
           errorBoxController.displayErrorPopUP("Invalid Date of Birth", "Year out of range.");
       } else {
           valid = true;
       }
       return valid;
    }

    public static boolean validateGender(String gender) {
        if (gender == "") {
            errorBoxController.displayErrorPopUP("Empty Gender Field", "Please provide a gender.");
            return false;
        } else {
            return gender == "Female" || gender == "Male";
        }
     }

    public static boolean validUserProfile(String userProfile) {
        boolean valid = false;
        if (userProfile == null) {
            errorBoxController.displayErrorPopUP("Error", "Please select a profile");
        } else {
            valid = true;
        }
        return valid;
    }
}