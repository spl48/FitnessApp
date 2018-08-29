package seng202.team6.models;

/**
 * This class implements User and sets the information regarding the User.
 * @author Angelica Dela Cruz
 * @version 1.1, Aug 2018.
 */
public class User
{
    /**
     * The minimum length of the User's name
     */
    private static final int MIN_NAME_LENGTH = 2;

    /**
     * The minimum length of the User's username
     */
    private static final int MIN_USERNAME_LENGTH = 6;

    /**
     * The length of the User's date of birth
     */
    private static final int DOB_LENGTH = 8;

    /**
     * The name of the User
     */
    private String name;

    /**
     * The date of birth of the User of the form DD/MM/YY
     */
    private String dob;

    /**
     * The age of the User
     */
    private int age;

    /**
     * The height of the User
     */
    private double height;

    /**
     * The weight of the User
     */
    private double weight;

    /**
     * The length of the stride of the User
     */
    private double strideLength;

    /**
     * The username chosen by the User
     */
    private String username;

    /**
     * The User for the User that sets the name, date of birth, height, weight
     * stride length and username for the specific User.
     * @param name A String parameter that is used to set the name of the User.
     * @param dob A String parameter that is used to set the User's dob.
     * @param height A type Double parameter that is used to set the height of the User.
     * @param weight A type Double parameter that is used to set the weight of the User.
     * @param strideLength A type Double parameter that is used to set the length of the stride of the User.
     * @param username A String parameter that is used to set the username of the User.
     */
    public User(String name, String dob, double height, double weight, double strideLength, String username)
    {
        if (name.length() >= MIN_NAME_LENGTH) {
            this.name = name;
        } else {
            this.name = "invalid";
        }

        if (dob.length() == DOB_LENGTH) {
            this.dob = dob;
        } else {
            this.dob = "DD/MM/YY";
        }

        if (height < 0) {
            this.height = 0.0;
        } else {
            this.height = height;
        }

        if (weight < 0) {
            this.weight = 0.0;
        } else {
            this.weight = weight;
        }

        if (strideLength < 0) {
            this.strideLength = 0.0;
        } else {
            this.strideLength = strideLength;
        }

        if ((username.length()) >= MIN_USERNAME_LENGTH) {
            this.username = username;
        } else {
            this.username = "invalid";
        }
    }

    /**
     * A function that sets the name of the User to the given String parameter
     * name. Checks that the length of the name of the User must be greater or equal
     * to minimum name length. Otherwise, invalid name.
     * @param name A String parameter that is used to set the name of the User.
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
     * A function that returns the name of the User.
     * @return Returns a String that represents the User's name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * A function that sets the date of birth of the User to the given String parameter
     * dob of the form DD/MM/YY. Checks if the DOB length is equal to dob_length.
     * Otherwise, not valid.
     * @param dob A String parameter that is used as the date of birth of the User.
     */
    public void setDOB(String dob)
    {
        if (dob.length() == DOB_LENGTH) {
            this.dob = dob;
        } else {
            this.dob = "DD/MM/YY";
        }
    }

    /**
     * A function that returns the date of birth of the User.
     * @return Returns a String that represents the date of birth of the User of
     * the form DD/MM/YY.
     */
    public String getDOB()
    {
        return dob;
    }

    /**
     * A function that sets the height of the User to the given String parameter
     * height in cm. Checks if height is greater than 0, otherwise invalid.
     * @param height A Double parameter that is used as the height of the User.
     */
    public void setHeight(double height)
    {
        if (height < 0) {
            this.height = 0.0;
        } else {
            this.height = height;
        }
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
     * A function that sets the weight of the User to the given Double parameter
     * weight in kg. Checks if weight is greater than 0. Otherwise, weight is
     * invalid.
     * @param weight A Double parameter that is used as the weight of the User in kg.
     */
    public void setWeight(double weight)
    {
        if (weight < 0) {
            this.weight = 0.0;
        } else {
            this.weight = weight;
        }
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
     * A function that sets the length of stride of the User to the given Double parameter
     * strideLength in feet. Checks if stride length is greater than 0. Otherwise,
     * invalid stride length.
     * @param strideLength A Double parameter that is used as the stride length of the User
     * in feet.
     */
    public void setStrideLength(double strideLength)
    {
        if (strideLength < 0) {
            this.strideLength = 0.0;
        } else {
            this.strideLength = strideLength;
        }
    }

    /**
     * A function that returns the length of stride of the User in feet.
     * @return Returns a Double that represents the User's stride length in feet.
     */
    public double getStrideLength()
    {
        return strideLength;
    }

    /**
     * A function that sets the username of the User to the given String parameter
     * username. Checks if the username length is greater than the minimum
     * username length, otherwise invalid username.
     * @param username A String parameter that is used as the username of the User.
     */
    public void setUsername(String username)
    {
        if ((username.length()) < MIN_USERNAME_LENGTH) {
            this.username = "invalid";
        } else {
            this.username = username;
        }
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

    public static void main(String[] args)
    {
        User user1 = new User("Angelica", "16/09/97", 166.5, 55.0, 2.2, "angelicadelacruz");
        System.out.println(user1.getName());
        System.out.println(user1.getDOB());
        System.out.println(user1.getHeight());
        System.out.println(user1.getWeight());
        System.out.println(user1.getStrideLength());
        System.out.println(user1.getUsername());
    }

}