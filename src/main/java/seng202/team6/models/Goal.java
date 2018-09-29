package seng202.team6.models;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class implements Goal and sets the required information about a Goal specified by
 * the User.
 * @author Angelica Dela Cruz
 * @version 1.2, Aug 2018.
 */
public class Goal
{
    /**
     * The minimum goal name length
     */
    private int MIN_GOAL_NAME_LENGTH = 6;

    /**
     * The name of the Goal
     */
    private String goalName;

    /**
     * The date that the Goal is created.
     */
    private LocalDate dateSet;

    /**
     * The date that the Goal is achieved.
     */
    private LocalDate dateAchieved;

    /**
     * The status if the Goal has been reached (true) or not (false)
     */
    private boolean goalReached = false;

    private int goalStepsNum;

    /**
     * The constructor for the Goal that takes the parameter date set and goal name and sets
     * the date created and goal name for the specific Goal.
     * @param dateSet A LocalDate parameter that is used to set the date the Goal is created.
     * @param goalName A String parameter that is used to set the name of the Goal.
     */
    public Goal(String goalName, LocalDate dateSet)
    {
        this.dateSet = dateSet;

        if (goalName.length() >= MIN_GOAL_NAME_LENGTH) {
            this.goalName = goalName;
        } else {
            this.goalName = "invalid";
        }
    }

    /**
     * Construct the goal number of steps per week
     */
    public Goal(int goalSteps)
    {
        goalStepsNum = goalSteps;
    }

    /**
     * A function that takes the parameter name and sets the name of the Goal
     * to the given String parameter goalName. Checks if the name of the Goal
     * is valid if the name length is greater or equal to the min name length.
     * @param goalName A String parameter used to set the Goal's name.
     */
    public void setGoalName(String goalName)
    {
        if (goalName.length() >= MIN_GOAL_NAME_LENGTH) {
            this.goalName = goalName;
        } else {
            this.goalName = "invalid";
        }
    }

    /**
     * A function that returns the name of the specific Goal.
     * @return Returns a String that represents the name of the Goal.
     */
    public String getGoalName()
    {
        return goalName;
    }

    /**
     * A function that takes the parameter date set and sets the date of when the Goal
     * is created to the given LocalDate parameter.
     * @param dateSet A LocalDate parameter used to set the date Goal is created by the User.
     */
    public void setDateSet(LocalDate dateSet)
    {
        this.dateSet = dateSet;
    }

    /**
     * A function that returns the date that the Goal is created.
     * @return Returns a LocalDate that represents the date the Goal is set.
     */
    public LocalDate getDateSet()
    {
        return dateSet;
    }

    /**
     * A function that takes the parameter date achieved and sets the date of when the Goal
     * is achieved to the given parameter.
     * @param dateAchieved A LocalDate parameter used to set the date of when the Goal is achieved.
     */
    public void setDateAchieved(LocalDate dateAchieved)
    {
        this.dateAchieved = dateAchieved;
    }

    /**
     * A function that returns the date of when the Goal is achieved.
     * @return Returns a LocalDate that represents when the Goal is achieved.
     */
    public LocalDate getDateAchieved()
    {
        return dateAchieved;
    }

    /**
     * A function that takes a Boolean parameter and sets the status of the Goal's progress
     * if the Goal has been achieved (true) or not (false) based on the given parameter.
     * @param goalReached A Boolean parameter used to set the status of the Goal.
     */
    public void setGoalReached(boolean goalReached)
    {
        this.goalReached = goalReached;
    }

    /**
     * A function that returns the status of the Goal's progress.
     * True if the Goal has been reached, otherwise False.
     * @return Returns a Boolean that represents if the Goal has been reached.
     */
    public boolean getGoalReached()
    {
        return goalReached;
    }

    public int getGoalStepNum() { return goalStepsNum; }

    public void setStepGoal(int newGoalSteps) { goalStepsNum = newGoalSteps; }
}
