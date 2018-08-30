package seng202.team6.models;

import java.util.ArrayList;

/**
 * This class implements Goal and sets the required information about a Goal specified by
 * the User.
 * @author Angelica Dela Cruz
 * @version 1.1, Aug 2018.
 */
public class Goal
{
    /**
     * The date length
     */
    private int DATE_LENGTH = 8;

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
    private String dateSet;

    /**
     * The date that the Goal is achieved.
     */
    private String dateAchieved;

    /**
     * The status if the Goal has been reached (true) or not (false)
     */
    private boolean goalReached = false;

    /**
     * The ArrayList of the reminders for the Goal
     */
    private ArrayList<String> reminders = new ArrayList<String>();

    /**
     * The achievements earned by the User
     */
    private ArrayList<Achievement> achievementEarned = new ArrayList<Achievement>();

    /**
     * The congratulatory message for the User once the Goal is achieved
     */
    private String congratulationMessage;

    /**
     * The constructor for the Goal that sets the date created and name for the specific
     * Goal.
     * @param dateSet A String parameter that is used to set the date the Goal is created.
     * @param goalName A String parameter that is used to set the name of the Goal.
     */
    public Goal(String goalName, String dateSet)
    {
        if (dateSet.length() == DATE_LENGTH) {
            this.dateSet = dateSet;
        } else {
            this.dateSet = "DD/MM/YY";
        }

        if (goalName.length() >= MIN_GOAL_NAME_LENGTH) {
            this.goalName = goalName;
        } else {
            this.goalName = "invalid";
        }
    }

    /**
     * A function that sets the name of the Goal to the given String parameter goalName.
     * Checks if the name of the Goal is valid.
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
     * A function that returns the name of the Goal.
     * @return Returns a String that represents the name of the Goal.
     */
    public String getGoalName()
    {
        return goalName;
    }

    /**
     * A function that sets the date of when the Goal is created to the given String
     * parameter dateSet of the form DD/MM/YY. Checks if the date is valid if the date
     * is equal to the date length.
     * @param dateSet A String parameter used to set the date Goal is created by the User.
     */
    public void setDateSet(String dateSet)
    {
        if (dateSet.length() == DATE_LENGTH) {
            this.dateSet = dateSet;
        } else {
            this.dateSet = "DD/MM/YY";
        }
    }

    /**
     * A function that returns the date that the Goal is created.
     * @return Returns a String that represents the date the Goal is set.
     */
    public String getDateSet()
    {
        return dateSet;
    }

    /**
     * A function that sets the date of when the Goal is achieved of the form DD/MM/YY.
     * Checks if the parameter dateAchieved is valid if the length is equal to date length.
     * @param dateAchieved A String parameter used to set when the Goal is achieved.
     */
    public void setDateAchieved(String dateAchieved)
    {
        if (dateAchieved.length() == DATE_LENGTH) {
            this.dateAchieved = dateAchieved;
        } else {
            this.dateAchieved = "DD/MM/YY";
        }
    }

    /**
     * A function that returns when the date of the Goal is achieved.
     * @return Returns a String that represents when the Goal is achieved.
     */
    public String getDateAchieved()
    {
        return dateAchieved;
    }

    /**
     * A function that sets the status of the Goal's progress if the Goal has been
     * achieve (true) or not (false).
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

    /**
     * A function that adds a reminder in the ArrayList of Strings containing the
     * reminders for the specific Goal.
     * @param reminder A String parameter added to the ArrayList reminders.
     */
    public void addReminders(String reminder)
    {
        reminders.add(reminder);
    }

    /**
     * A function that returns the ArrayList of reminders specific to the Goal.
     * @return Returns an ArrayList that represents the Goal's reminders.
     */
    public ArrayList<String> getReminders()
    {
        return reminders;
    }

    /**
     * A function that adds the Achievement earned by the User to its achievements.
     * @param achievementEarned An Achievement parameter that represents User achievement.
     */
    public void addAchievementEarned(Achievement achievementEarned)
    {
        this.achievementEarned.add(achievementEarned);
    }

    /**
     * A function that returns the Achievementd earned by the User from the Goal.
     * @return Returns an Achievement that represents the earned achievements of the
     * User from their Goal.
     */
    public ArrayList<Achievement> getAchievementEarned()
    {
        return achievementEarned;
    }

    /**
     * A function that sets the congratulatory message for the User once the Goal is
     * accomplished.
     * @param congratulationMessage A String that represents an acknowledgement of user
     * Achievement.
     */
    public void setCongratulationMessage(String congratulationMessage)
    {
        this.congratulationMessage = congratulationMessage;
    }

    /**
     * A function that returns the congratulatory message to the User from the
     * Goal.
     * @return Returns a String that shows a message as an acknowledgement of
     * User Achievement.
     */
    public String getCongratulationMessage()
    {
        return congratulationMessage;
    }

    public static void main(String[] args)
    {
        Goal goal = new Goal("Lose 5 kgs", "30/08/18");
        goal.setGoalName("Lose 1 kg");
        System.out.println(goal.getGoalName());

        goal.setDateSet("15/09/18");
        System.out.println(goal.getDateSet());

        goal.setDateAchieved("20/09/18");
        System.out.println(goal.getDateAchieved());

        goal.setGoalReached(true);
        System.out.println(goal.getGoalReached());

        goal.addReminders("Loss 0.5 more kg to achieve your Goal!");
        System.out.println(goal.getReminders());

        Achievement achievement = new Achievement("Weight Loss of 1 kg");
        goal.addAchievementEarned(achievement);
        System.out.println(goal.getAchievementEarned());

        goal.setCongratulationMessage("Congratulations on losing 1 kg!");
        System.out.println(goal.getCongratulationMessage());
    }
}
