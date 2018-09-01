package seng202.team6.models;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * MATES AI Entity
 */
public class MatesAI
{
    /**
     * The name of MATES AI
     */
    private String name;

    /**
     * The image of MATES AI
     */
    private BufferedImage buddyImage;

    /**
     * The greeting for the User
     */
    private String greeting;

    /**
     * Motivational Statements of MATES AI for the User
     */
    private ArrayList<String> motivations = new ArrayList<String>();

    /**
     * Reminders of MATES AI for the User
     */
    private ArrayList<String> reminders = new ArrayList<String>();

    /**
     * The congratulatory message for the User once the Goal is achieved
     */
    private ArrayList<String> congratulationMessage = new ArrayList<String>();

    /**
     * The constructor for MATES AI that takes the parameter name.
     * @param name A String used as the name for MATES AI
     */
    public MatesAI(String name) {
        this.name = name;
    }

    /**
     * A function that takes the parameter user and sets the greeting of
     * MATES AI specifically for the given user.
     * @param user A User of the application.
     */
    public void setGreeting(User user)
    {
        greeting = "Hello " + user.getUsername() + "!";
    }

    /**
     * A function that returns the greeting of MATES AI
     * @return Returns a String representing the MATES AI greetings.
     */
    public String getGreeting()
    {
        return greeting;
    }

    /**
     * A function that takes a String parameter motivation and adds the motivation in the
     * ArrayList of Strings containing the motivational statements for the User.
     * @param motivation A String parameter added to the ArrayList motivational comments.
     */
    public void addMotivations(String motivation)
    {
        motivations.add(motivation);
    }

    /**
     * A function that returns the ArrayList of motivational statements.
     * @return Returns an ArrayList that shows motivation comments.
     */
    public ArrayList<String> getMotivations()
    {
        return motivations;
    }

    /**
     * A function that takes a String parameter reminder and adds the reminder in the
     * ArrayList of Strings containing the reminders for the User.
     * @param reminder A String parameter added to the ArrayList reminders.
     */
    public void addReminders(String reminder)
    {
        reminders.add(reminder);
    }

    /**
     * A function that returns the ArrayList of reminders for the User.
     * @return Returns an ArrayList that shows reminders.
     */
    public ArrayList<String> getReminders()
    {
        return reminders;
    }

    /**
     * A function that takes a String parameter message and sets the congratulatory message
     * for the User to the given parameter.
     * @param message A String that represents an acknowledgement of user
     * Achievement.
     */
    public void addCongratulationMessage(String message)
    {
        congratulationMessage.add(message);
    }

    /**
     * A function that returns ArrayList of congratulatory messages to the User.
     * @return Returns a String that shows a message as an acknowledgement of
     * User Achievement.
     */
    public ArrayList<String> getCongratulationMessage()
    {
        return congratulationMessage;
    }

}