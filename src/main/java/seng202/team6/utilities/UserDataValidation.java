package seng202.team6.utilities;

import java.time.LocalDate;
import java.util.Calendar;

import seng202.team6.controller.ApplicationManager;
import seng202.team6.controller.PopUpBoxController;

/**
 * UserDataValidation is responsible for all user validation before being inputted in the database.
 */
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

    /**
     * The maximum age of the User
     */
    private static final int MAX_AGE = 99;

    /**
     * An instance of PopUpBoxController
     */
    private static PopUpBoxController popUpBoxController = new PopUpBoxController();

    /**
     * A function that checks if the given parameter is a string.
     * @param string A String that is being checked by the function.
     * @return Returns true if the string contains all alpha and false if not.
     */
    public static boolean isAlpha(String string) {

        char[] chars = string.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * A function that validates the username of the user. Checks if the username length is valid.
     * @param username A String parameter that represents user's username.
     * @return Returns a boolean true if valid and false if not.
     */
    public static boolean validateUserName(String username) {
        boolean valid = false;
        if (username.length() == 0 || username.length() < MIN_USERNAME_LENGTH) {
            popUpBoxController.displayPopUP("Invalid Username", "Please provide a username that is at least " + MIN_USERNAME_LENGTH + " characters and is less than " + MAX_USERNAME_LENGTH + " characters long.", "error");
        } else if (username.length() > MAX_USERNAME_LENGTH) {
            popUpBoxController.displayPopUP("Invalid Username", "Your username is too long. Please ensure username is at least " + MIN_USERNAME_LENGTH + " characters and is less than " + MAX_USERNAME_LENGTH + " characters long.", "error");
        } else {
            valid = true;
            System.out.println("Username OK");
        }
        return valid;
    }

    /**
     * A function that validates the user's name type ensuring that all names must be String characters.
     * @param name A String that represents user's name.
     * @param nameType A String that represents the type of user's name.
     * @return Returns a boolean true if valid and false if not.
     */
    public static boolean validateName(String name, String nameType) {
        boolean valid = false;
        int count = 0;
        String errorTitle = "Invalid " + nameType;
        if (name.length() < MIN_NAME_LENGTH) {
            popUpBoxController.displayPopUP(errorTitle, nameType + " is too short. Please ensure that " + nameType + " is at least " + MIN_NAME_LENGTH + " characters and less than " + MAX_NAME_LENGTH + " characters long.", "error");
        } else if (name.length() > MAX_NAME_LENGTH) {
            popUpBoxController.displayPopUP(errorTitle, nameType + " is too long. Please ensure that " + nameType + " is at least " + MIN_NAME_LENGTH + " characters and less than " + MAX_NAME_LENGTH + " characters long.", "error");
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
                popUpBoxController.displayPopUP(errorTitle, "Please ensure that " + nameType + " is all letters and no numerical data.", "error");
            } else {
                System.out.println(nameType + " OK");
                valid = true;
            }

        } else if (!isAlpha(name)) {
            popUpBoxController.displayPopUP(errorTitle, "Please ensure that " + nameType + " is all letters and no numerical data.", "error");
        }
        return valid;
    }

    /**
     * A function that validates a type Double parameter based on the given parameters.
     * @param value A Double that is being validated.
     * @param valueName A String that represents the Double value name.
     * @param upper A Double that represents the upper bound of the Double.
     * @param lower A Double that represents the lower bound of the Double.
     * @param type A String that represents the Double parameter value type.
     * @return Returns a boolean true if valid and false if not.
     */
    public static boolean validateDoubleValue(Double value, String valueName, double upper, double lower, String type) {
        boolean valid = false;
        String errorTitle = "Invalid " + valueName;
        if (value > upper || value < lower) {
            String errorMessage = valueName + " is not in range. Please ensure that " + valueName + " is in the range of " + lower + " to " + upper + " " + type + ".";
            popUpBoxController.displayPopUP(errorTitle, errorMessage, "error");
        } else {
            System.out.println(valueName + " OK");
            valid = true;
        }
        return valid;
    }

    /**
     * A function that validates the user's date of birth. The user must be at least 5 years old and less than 100
     * years old.
     * @param dob A LocalDate that represents the user's date of birth.
     * @return Returns a boolean true if valid and false if not.
     */
    public static boolean validateBirthDate(LocalDate dob) {
       boolean valid = false;
       if (dob == null) {
           popUpBoxController.displayPopUP("Invalid Date of Birth", "Please provide a date of birth. Ensure that age is within " + MIN_AGE + " to " + MAX_AGE + " years old.", "error");
           return false;
       }
       int birthYear = dob.getYear();
       int currYear = Calendar.getInstance().get(Calendar.YEAR);
       if (birthYear > currYear - 5 || birthYear < currYear - 100) {
           popUpBoxController.displayPopUP("Invalid Date of Birth", "Year out of range. Ensure that age is within " + MIN_AGE + " to " + MAX_AGE + " years old.", "error");
       } else {
           System.out.println("Birthdate OK");
           valid = true;
       }
       return valid;
    }

    /**
     * A function that validates the user gender based on the given parameter.
     * @param gender A String parameter that is being validated.
     * @return Returns true if the data is valid and false if not.
     */
    public static boolean validateGender(String gender) {
        boolean valid = false;
        if (gender == "") {
            popUpBoxController.displayPopUP("Invalid Gender", "Please provide a gender and ensure it is either Female or Male.", "error");
        } else if (!(gender.equals("Female") || gender.equals("Male"))) {
            System.out.println("Gender: " + gender);
            popUpBoxController.displayPopUP("Invalid Gender", "Please ensure that gender is either Female or Male.", "error");
        } else {
            System.out.println("Gender OK");
            valid = true;
        }
        return valid;
     }

    /**
     * A function that validates the distance goal. Checks if it is witjin the 0 to 350 km range.
     * @param distanceGoal An Integer that represents the distance goal.
     * @return Returns a boolean true if valid and false if not.
     */
    public static boolean validDistanceGoal(int distanceGoal) {
        boolean valid = false;
        if (distanceGoal < 0 || distanceGoal > 350) {
            popUpBoxController.displayPopUP("Invalid Distance Goal", "Please ensure that distance goal is an integer value and is between 0 to 350 km.", "error");
        } else {
            System.out.println("Distance Goal OK");
            valid = true;
        }
        return valid;
    }

    /**
     * A function that validates the step goal. Checks if it is within the 0 to 500000 steps range.
     * @param stepGoal An Integer that represents the steps goal.
     * @return Returns a boolean true if valid and false if not.
     */
    public static boolean validStepGoal(int stepGoal) {
        boolean valid = false;
        if (stepGoal < 0 || stepGoal > 500000) {
            popUpBoxController.displayPopUP("Invalid Step Goal", "Please ensure the steps goal is an integer value and is between 0 and 500000 steps.", "error");
        } else {
            System.out.println("Step Goal OK");
            valid = true;
        }
        return valid;
    }
}