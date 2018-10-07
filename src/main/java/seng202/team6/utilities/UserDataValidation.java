package seng202.team6.utilities;

import java.time.LocalDate;
import java.util.Calendar;

import seng202.team6.controller.ApplicationManager;
import seng202.team6.controller.PopUpBoxController;

public class UserDataValidation {

    /**
     * The minimum length of the User's first name
     */
    private static final int MIN_NAME_LENGTH = 2;

    /**
     * The maximum length of User's last name.
     * According to the record of maximum last name, Wolfeschlegelsteinhausenbergerdorff.
     */
    private static final int MAX_NAME_LENGTH = 45;

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

    private static PopUpBoxController popUpBoxController = new PopUpBoxController();

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
            popUpBoxController.displayPopUP("Username Entry Invalid", "Please provide a username.", "error");
        } else if (username.length() > MAX_USERNAME_LENGTH) {
            popUpBoxController.displayPopUP("Username Entry Invalid", "Username too long\nPlease ensure username is less than " + MAX_USERNAME_LENGTH + " characters.", "error");
        } else {
            valid = true;
            System.out.println("Username OK");
        }
        return valid;
    }

    public static boolean validateName(String name, String nameType) {
        boolean valid = false;
        int count = 0;
        String errorTitle = nameType + " Entry Invalid";
        if (name.length() < MIN_NAME_LENGTH) {
            popUpBoxController.displayPopUP(errorTitle, nameType + " is too short.\nPlease ensure " + nameType + " is more than " + MIN_NAME_LENGTH + " characters.", "error");
        } else if (name.length() > MAX_NAME_LENGTH) {
            popUpBoxController.displayPopUP(errorTitle, nameType + " is too long.\nPlease ensure " + nameType + " is less than " + MAX_NAME_LENGTH + " characters.", "error");
        } else if (isAlpha(name)) {
            System.out.println(nameType + " OK");
            valid = true;
        } else if (name.contains(" ")) {

            for (int i = 0; i < name.length(); i++) {
                char c = name.charAt(i);
                if ((!(Character.isAlphabetic(c))) && (c != ' ')) {
                    count += 1;
                }
            }

            if (count > 0) {
                popUpBoxController.displayPopUP(errorTitle, nameType + " is of invalid type.", "error");
            } else {
                System.out.println(nameType + " OK");
                valid = true;
            }

        } else if (!isAlpha(name)) {
            popUpBoxController.displayPopUP(errorTitle, nameType + " is of invalid type.", "error");
        }
        return valid;
    }

    public static boolean validateDoubleValue(Double value, String valueName, double upper, double lower, String type) {
        boolean valid = false;
        String errorTitle = valueName + " Entry Invalid";
        if (value > upper || value < lower) {
            String errorMessage = valueName + " is not in range.\nPlease ensure " + valueName + " is in the range of " + lower + " to " + upper + " " + type + ".";
            popUpBoxController.displayPopUP(errorTitle, errorMessage, "error");
        } else {
            System.out.println(valueName + " OK");
            valid = true;
        }
        return valid;
    }

    public static boolean validateBirthDate(LocalDate dob) {
       boolean valid = false;
       if (dob == null) {
           popUpBoxController.displayPopUP("Invalid Date of Birth", "Please provide a date of birth.", "error");
           return false;
       }
       int birthYear = dob.getYear();
       int currYear = Calendar.getInstance().get(Calendar.YEAR);
       if (birthYear > currYear - 5 || birthYear < currYear - 100) {
           popUpBoxController.displayPopUP("Invalid Date of Birth", "Year out of range.", "error");
       } else {
           System.out.println("Birthdate OK");
           valid = true;
       }
       return valid;
    }

    public static boolean validateGender(String gender) {
        boolean valid = false;
        if (gender == "") {
            popUpBoxController.displayPopUP("Empty Gender Field", "Please provide a gender.", "error");
        } else if (!(gender.equals("Female") || gender.equals("Male"))) {
            System.out.println("Gender: " + gender);
            ApplicationManager.displayPopUp("Incorrect Gender", "Please make sure the gender is valid!", "error");
        } else {
            System.out.println("Gender OK");
            valid = true;
        }
        return valid;
     }

    public static boolean validDistanceGoal(int distanceGoal) {
        boolean valid = false;
        if (distanceGoal < 0 || distanceGoal > 350) {
            popUpBoxController.displayPopUP("Invalid Distance Goal", "Please ensure the goal is between 0 and 350 km", "error");
        } else {
            System.out.println("Distance Goal OK");
            valid = true;
        }
        return valid;
    }

    public static boolean validStepGoal(int stepGoal) {
        boolean valid = false;
        if (stepGoal < 0 || stepGoal > 500000) {
            popUpBoxController.displayPopUP("Invalid Distance Goal", "Please ensure the goal is between 0 and 500000 steps", "error");
        } else {
            System.out.println("Step Goal OK");
            valid = true;
        }
        return valid;
    }
}