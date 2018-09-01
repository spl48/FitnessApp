package seng202.team6.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
     * The minimum age of the User
     */
    private static final int MIN_AGE = 5;

    /**
     * The name of the User
     */
    private String name;

    /**
     * The date of birth of the User
     */
    private LocalDate dob;

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
     * The constructor for the User that takes the parameter name, dob, age height, weight, stride length and username.
     * @param name A String parameter that is used to set the name of the User.
     * @param dob A LocalDate parameter that is used to set the User's dob.
     * @param age An Integer parameter used to set the User's age.
     * @param height A type Double parameter that is used to set the height of the User.
     * @param weight A type Double parameter that is used to set the weight of the User.
     * @param strideLength A type Double parameter that is used to set the length of the stride of the User.
     * @param username A String parameter that is used to set the username of the User.
     */
    public User(String name, LocalDate dob, int age, double height, double weight, double strideLength, String username)
    {
        if (name.length() >= MIN_NAME_LENGTH) {
            this.name = name;
        } else {
            this.name = "invalid";
        }

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        LocalDate invalid = LocalDate.now().plus(-5, ChronoUnit.YEARS);

        if ((year - dob.getYear()) >= MIN_AGE && (month - dob.getMonthValue()) >= 0) {
            this.dob = dob;
            this.age = age;
        } else {
            this.age = MIN_AGE;
            this.dob = invalid;
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
     * A function that takes a String parameter name and sets the name of the User to
     * the given String parameter. Checks that the name is valid if the length of the name
     * is greater or equal to minimum name length. Otherwise, name is set to invalid.
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
     * A function that takes an Integer parameter age and a LocalDate parameter dob
     * and sets the age of the User to the given parameter in years and dob of the User.
     * Checks if age and dob is valid if the User's age and dob coincide with the
     * minimum age. If not age is set to min age and date is set to a default date.
     * A date that is 5 years before.
     * @param age An Integer parameter used as the age of the User.
     * @param dob A LocalDate parameter that is used to set the User's dob.
     */
    public void setAgeAndDOB(int age, LocalDate dob)
    {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        LocalDate invalid = LocalDate.now().plus(-5, ChronoUnit.YEARS);

        if ((year - dob.getYear()) >= MIN_AGE && (month - dob.getMonthValue()) >= 0) {
            this.dob = dob;
            this.age = age;
        } else {
            this.age = MIN_AGE;
            this.dob = invalid;
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
     * A function that takes the Double parameter height and sets the height of
     * the User to the given parameter height in cm. Checks if height is
     * greater than 0, otherwise invalid and is set to 0.0.
     * @param height A Double parameter that is used as the height of the User.
     */
    public void setHeight(double height)
    {
        if (height < 0.0) {
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
     * A function that takes the Double parameter weight and sets the weight of the
     * User to the given parameter in kgs. Checks if weight is greater than 0.
     * Otherwise, weight is invalid and set to 0.0.
     * @param weight A Double parameter that is used as the weight of the User in kg.
     */
    public void setWeight(double weight)
    {
        if (weight < 0.0) {
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
     * A function that takes the Double parameter stride length and sets the length
     * of the stride of the User to the given Double parameter in feet. Checks if
     * stride length is greater than 0. Otherwise, invalid stride length and is set to
     * 0.0.
     * @param strideLength A Double parameter that is used as the stride length of the User
     * in feet.
     */
    public void setStrideLength(double strideLength)
    {
        if (strideLength < 0.0) {
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
     * A function that takes a String parameter username and sets the username of
     * the User to the given String parameter. Checks if the username is valid if the
     * length is greater than the minimum username length, otherwise invalid username
     * and is set to 'invalid'.
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
}

