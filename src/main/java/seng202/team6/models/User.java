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
     * @param firstName A String parameter that is used to set the first name of the User.
     * @param lastName A String parameter that is used to set the last name of the User.
     * @param dob A LocalDate parameter that is used to set the User's dob.
     * @param height A type Double parameter that is used to set the height of the User.
     * @param weight A type Double parameter that is used to set the weight of the User.
     * @param strideLength A type Double parameter that is used to set the length of the stride of the User.
     * @param username A String parameter that is used to set the username of the User.
     */
    public User(String firstName, String lastName, LocalDate dob, String gender, double height, double weight, double strideLength, String username)
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
        this.strideLength = strideLength;
        this.username = username;
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
     * A function that takes the Double parameter stride length and sets the length
     * of the stride of the User to the given Double parameter in feet.
     * @param strideLength A Double parameter that is used as the stride length of the User
     * in feet.
     */
    public void setStrideLength(double strideLength)
    {
       this.strideLength = strideLength;
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
}

