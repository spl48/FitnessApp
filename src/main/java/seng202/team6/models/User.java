package seng202.team6.models;

import seng202.team6.controller.ApplicationManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class implements User and sets the information regarding the User.
 * @author Angelica Dela Cruz
 * @version 1.1, Aug 2018.
 */

public class User
{
    /**
     * The first name of the User
     */
    private String firstName;

    /**
     * The last name of the User
     */
    private String lastName;

    /**
     * The date of birth of the User
     */
    private LocalDate dob;

    /**
     * The age of the User
     */
    private int age;

    /**
     * The gender of User
     */
    private String gender;

    /**
     * The height of the User in cm
     */
    private double height;

    /**
     * The weight of the User in kg
     */
    private double weight;

    /**
     * The length of the walking stride length of the User in feet
     */
    private double walkingStrideLength;

    /**
     * The length of the running stride length of the user in feet
     */
    private double runningStrideLength;

    /**
     * The username chosen by the User
     */
    private String username;

    /**
     * The userID used by the database.
     */
    private int userID;

    /**
     * The Goals of the User that the Profile is associated with
     */
    private ArrayList<Goal> goals = new ArrayList<Goal>();
    /**
     * A function that takes a Goal parameter and adds the goal to an ArrayList
     * of Goal.
     * @param goal A Goal parameter that is added to an ArrayList of Goal.
     */
    public void addGoals(Goal goal)
    {
        goals.add(goal);
    }

    /**
     * A function that returns an ArrayList of User's Goals.
     * @return Returns an ArrayList representing User Goals.
     */
    public ArrayList<Goal> getGoals()
    {
        return goals;
    }


    /**
     * Goal object thast holds the goal number of steps per week
     */
    public int stepGoal;

    public int distanceGoal;



    /**
     * A function that takes a Boolean parameter and sets the status of the Goal's progress
     * if the Goal has been achieved (true) or not (false) based on the given parameter.
     * @param goal A Goal parameter
     * @param status A Boolean parameter used to set the status of the Goal.
     */
    public void setGoalStatus(Goal goal, boolean status)
    {
        goal.setGoalReached(status);
    }

    /**
     * The constructor for the User that takes the parameter name, dob, age height, weight, stride length and username.
     * @param firstName A String parameter that is used to set the first name of the User.
     * @param lastName A String parameter that is used to set the last name of the User.
     * @param dob A LocalDate parameter that is used to set the User's dob.
     * @param height A type Double parameter that is used to set the height of the User.
     * @param weight A type Double parameter that is used to set the weight of the User.
     * @param username A String parameter that is used to set the username of the User.
     * @param userID The user ID.
     */
    public User(String firstName, String lastName, LocalDate dob, String gender, double height, double weight, String username, int userID, int stepGoal, int distanceGoal)
    {
        this.firstName = firstName;
        this.lastName = lastName;

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();

        int yearBirth = year - dob.getYear();
        int monthBirth = month - dob.getMonthValue();

        if (monthBirth >= 0) {
            this.dob = dob;
            this.age = yearBirth;
        } else {
            this.age = yearBirth - 1;
            this.dob = dob;
        }

        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.username = username;

        this.userID = userID;
        this.stepGoal = stepGoal;
        this.distanceGoal = distanceGoal;

        walkingStrideLength = estimateWalkingStrideLength(height, gender);
        runningStrideLength = estimateRunningStrideLength(height, gender);
    }

    /**
     * A function that takes a String parameter first name and last name and sets the name of the User to
     * the given String parameters.
     * @param firstName A String parameter that is used to set the first name of the User.
     * @param lastName A String parameter that is used to set the last name of the User.
     */
    public void setName(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * A function that returns the first name of the User.
     * @return Returns a String that represents the User's first name.
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * A function that returns the last name of the User.
     * @return Returns a String that represents the User's last name.
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * A function that takes a LocalDate parameter dob and sets the age of the User
     * to the given parameter in years and dob of the User based on the date of birth.
     * @param dob A LocalDate parameter that is used to set the User's dob.
     */
    public void setAgeAndDOB(LocalDate dob)
    {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();

        int yearBirth = year - dob.getYear();
        int monthBirth = month - dob.getMonthValue();

        if (monthBirth >= 0) {
            this.dob = dob;
            this.age = yearBirth;
        } else {
            this.age = yearBirth - 1;
            this.dob = dob;
        }
    }

    /**
     * A function that returns the age of the User.
     * @return Returns an Integer that represents the User's age in years.
     */
    public int getAge()
    {
        return age;
    }

    /**
     * A function that returns the date of birth of the User.
     * @return Returns a LocalDate that represents the date of birth of the User.
     */
    public LocalDate getDOB()
    {
        return dob;
    }

    /**
     * A function that takes a String parameter gender and sets the Gender of the User to
     * given parameter.
     * @param gender A String that is used as the gender of the User.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * A function that returns the User gender (Female or Male).
     * @return Returns a String representing the User gender.
     */
    public String getGender()
    {
        return gender;
    }

    /**
     * A function that takes the Double parameter height and sets the height of
     * the User to the given parameter height in cm.
     * @param height A Double parameter that is used as the height of the User.
     */
    public void setHeight(double height)
    {
        this.height = height;
    }

    /**
     * A function that returns the height of the User in cm.
     * @return Returns a Double that represents the User's height in cm.
     */
    public double getHeight()
    {
        return height;
    }

    /**
     * A function that takes the Double parameter weight and sets the weight of the
     * User to the given parameter in kgs.
     * @param weight A Double parameter that is used as the weight of the User in kg.
     */
    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    /**
     * A function that returns the weight of the User in kg.
     * @return Returns a Double that represents the User's weight in kg.
     */
    public double getWeight()
    {
        return weight;
    }

    /**
     * A function that estimates the wqalking stride length of the user given the
     * Double parameter height and sets the user stride length to the
     * estimated stride length.
     * @param height A Double parameter used to estimate User Stride Length
     * @param gender A String parameter that represents gender of the User
     */
    public double estimateWalkingStrideLength(double height, String gender) {
        double foot = 0.0328084;
        if (gender == "Female") {
            double strideLengthFemale = height * 0.413;
            walkingStrideLength = strideLengthFemale * foot; // estimated stride length for female
        } else if (gender == "Male") {
            double strideLengthMale = height * 0.415;
            walkingStrideLength = strideLengthMale * foot; // estimated stride length for male
        }
        return walkingStrideLength;
    }

    /**
     * A function that takes the Double parameter stride length and sets the length
     * of the stride of the User to the given Double parameter in feet.
     * @param walkingStrideLength A Double parameter that is used as the walking stride length of the User
     * in feet.
     */
    public void setWalkingStrideLength(double walkingStrideLength)
    {
       this.walkingStrideLength = walkingStrideLength;
    }

    /**
     * A function that returns the walking stride length of the User in feet.
     * @return Returns a Double that represents the User's stride length in feet.
     */
    public double getWalkingStrideLength()
    {
        return walkingStrideLength;
    }

    /**
     * A function that estimates the running stride length of the user given the
     * Double parameter height and sets the user stride length to the
     * estimated stride length.
     * @param height A Double parameter used to estimate User Stride Length
     */
    public double estimateRunningStrideLength(double height, String gender) {
        double inch = 0.393701;
        double heightInches = height * inch;

        if (gender == "Female") {
            double runningStrideLengthInches = heightInches * 1.14;
            runningStrideLength = runningStrideLengthInches / 12;
        } else if (gender == "Male") {
            double runningStrideLengthInches = heightInches * 1.35;
            runningStrideLength = runningStrideLengthInches / 12;
        }
        return runningStrideLength;
    }

    /**
     * A function that takes the Double parameter stride length and sets the length of the
     * stride of the user to the given Double parameter in feet.
     * @param runningStrideLength A Double parameter that is used as the running stride length of the User
     * in feet.
     */
    public void setRunningStrideLength(double runningStrideLength)
    {
        this.runningStrideLength = runningStrideLength;
    }

    /**
     * A function that returns the running stride length of the User in feet.
     * @return Returns a Double that represents the User's stride length in feet.
     */
    public double getRunningStrideLength()
    {
        return runningStrideLength;
    }

    /**
     * A function that takes a String parameter username and sets the username of
     * the User to the given String parameter. Checks if the username is valid if the
     * length is greater than the minimum username length, otherwise invalid username
     * and is set to 'invalid'.
     * @param username A String parameter that is used as the username of the User.
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * A function that returns the username chosen by the User. No username can
     * be identical.
     * @return Returns a String that represents the username of the User.
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Returns the String representation of Full Name of the User.
     * @return Returns the Full Name of the User.
     */
    public String getFullName() 
    {
        return firstName + " " + lastName;
    }

    /**
     * A function that takes an Integer id and sets the User ID to the given
     * parameter.
     * @param id An Integer parameter that is used as the User ID.
     */
    public void setUserID(int id) {
        userID = id;
    }

    /**
     * A function that returns the user ID of the User.
     * @return Returns an Integer that represents the User ID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Returns the current steps per week goal of the user
     * @return current steps per week goal of the user
     */
    public int getStepGoal() { return stepGoal; }

    /**
     * sets the step goal for the user
     * @param newStepCount integer of new step count per week goal
     */
<<<<<<< HEAD
    public void setStepGoal(int newStepCount) {
        stepGoal.setStepGoal(newStepCount);
        System.out.println("new step goal" + newStepCount);
=======
    public void setStepGoal(int newStepCount) throws SQLException {
        this.stepGoal = newStepCount;
        ApplicationManager.getDatabaseManager().setStepGoal(userID, stepGoal);
    }

    public int getDistanceGoal() {
        return distanceGoal;
    }

    public void setDistanceGoal(int distanceGoal) throws SQLException {
        this.distanceGoal = distanceGoal;
        ApplicationManager.getDatabaseManager().setDistanceGoal(userID, distanceGoal);
>>>>>>> 0ecbec675a0633df36e223ba63eb081663ec5eb0
    }

    public void printUser() {
        System.out.println("-----------------------------------------");
        System.out.println("User: " + username);
        System.out.println("-----------------------------------------");
        System.out.println("User ID: " + userID);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Date of Birth: " + dob.toString());
        System.out.println("Gender: " + gender);
        System.out.println("Height: " + height);
        System.out.println("Weight: " + weight);
    }

}

