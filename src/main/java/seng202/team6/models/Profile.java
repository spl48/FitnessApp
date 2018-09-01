package seng202.team6.models;

import java.util.ArrayList;

/**
 * Profile
 */
public class Profile
{
    /**
     * The User the Profile is associated with
     */
    private User user;

    /**
     * The username of the User that the Profile is associated with
     */
    private String username;

    /**
     * The Goals of the User that the Profile is associated with
     */
    private ArrayList<Goal> goals = new ArrayList<Goal>();

    /**
     * Activity List for Running Data
     */
    private ActivityList runningData;

    /**
     * Activity List for Walking Data
     */
    private ActivityList walkingData;

    /**
     * Activity List for Biking Data
     */
    private ActivityList bikingData;

    /**
     * The achievements earned by the User
     */
    private ArrayList<Achievement> achievements = new ArrayList<Achievement>();

    /**
     * The possible Health Concern for the User
     */
    private ArrayList<String> healthConcerns = new ArrayList<String>();


    /**
     * The constructor for Profile that takes the parameter user and sets the Profile
     * to be associated with the given user.
     * @param user A User parameter set to be the Profile User.
     */
    public Profile(User user) {
        this.user = user;
    }

    /**
     * A function that takes the parameter user and sets the Profile user to the given
     * parameter.
     * @param user A User parameter set to be the Profile User.
     */
    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * A function that returns the User associated with the Profile
     * @return Returns a type User representing the Profile User.
     */
    public User getUser() {
        return user;
    }

    /**
     * A function that takes the parameter user and sets the username of the
     * Profile to the User's chosen username.
     * @param user A User parameter that is the Profile User.
     */
    public void setUsername(User user)
    {
        username = user.getUsername();
    }

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
     * A function that takes an ActivityList running data parameter and sets this as
     * the running data for the particular User Profile.
     * @param runningData An ActivityList that is used as the running data of the User.
     */
    public void setRunningData(ActivityList runningData)
    {
        this.runningData = runningData;
    }

    /**
     * A function that returns the Running Data of the User Profile.
     * @return Returns an ActivityList that represents running data of the User.
     */
    public ActivityList getRunningData()
    {
        return runningData;
    }

    /**
     * A function that takes an ActivityList walking data parameter and sets this as
     * the walking data for the particular User Profile.
     * @param walkingData An ActivityList that is used as the walking data of the User.
     */
    public void setWalkingData(ActivityList walkingData)
    {
        this.walkingData = walkingData;
    }

    /**
     * A function that returns the Walking Data of the User Profile.
     * @return Returns an ActivityList that represents walking data of the User.
     */
    public ActivityList getWalkingData() {
        return walkingData;
    }

    /**
     * A function that takes an ActivityList biking data parameter and sets this as
     * the biking data for the particular User Profile.
     * @param bikingData An ActivityList that is used as the biking data of the User.
     */
    public void setBikingData(ActivityList bikingData)
    {
        this.bikingData = bikingData;
    }

    /**
     * A function that returns the Biking Data of the User Profile.
     * @return Returns an ActivityList that represents biking data of the User.
     */
    public ActivityList getBikingData()
    {
        return bikingData;
    }

    /**
     * A function that takes an Achievement parameter achievement earned and adds the
     * Achievement earned by the User to its ArrayList of Achievements.
     * @param achievement An Achievement parameter that represents User achievement.
     */
    public void addAchievements(Achievement achievement)
    {
        this.achievements.add(achievement);
    }

    /**
     * A function that returns an ArrayList of Achievements of the User from their Goal.
     * @return Returns ArrayList of Achievement that represents the earned achievements of the
     * User from their Goal.
     */
    public ArrayList<Achievement> getAchievements()
    {
        return achievements;
    }

    /**
     * A function that takes a String parameter health concern and add it to the
     * ArrayList of Health Concerns for the User.
     * @param healthConcern A String that is added into the ArrayList of Health
     * Concern for the User.
     */
    public void addHealthConcerns(String healthConcern)
    {
        healthConcerns.add(healthConcern);
    }

    /**
     * A function that returns an ArrayList of Health Concerns for the User
     * @return Returns a ArrayList of String representing the possible Health Concerns for the User.
     */
    public ArrayList<String> getHealthConcerns()
    {
        return healthConcerns;
    }
}

