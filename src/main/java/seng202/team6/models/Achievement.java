package seng202.team6.models;

/**
 * This class implements Achievement and sets the required information about the Achievement
 * of the User from a specific Goal.
 */
public class Achievement {

    private static final int MIN_NAME_LENGTH = 6;

    /**
     * The name of the Achievement
     */
    private String name;

    /**
     * The constructor for Achievement that sets the name of the Achievement.
     * @param name A String parameter used as the Achievement name.
     */
    Achievement(String name)
    {
        if (name.length() >= MIN_NAME_LENGTH) {
            this.name = name;
        } else {
            this.name = "invalid";
        }

    }

    /**
     * A function that stes the name of the Achievement to the given String parameter
     * name. Checks if the name is valid.
     * @param name A String parameter used as the name of the Achievement.
     */
    public void setName(String name)
    {
        if (name.length() >= MIN_NAME_LENGTH) {
            this.name = name;
        } else {
            this.name = "invalid";
        }
    }

    /**
     * A function that returns the name of the Achievement
     * @return Returns a String that represents the Achievement's name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * The template for when the Achievement is printed out:
     * Lastest Achievement is: ["Achievement Name"]
     * @return Returns a String representation of the template for Achievement object.
     */
    @Override
    public String toString()
    {
        return "Lastest Achievement is: [" + name + "]";
    }

}
