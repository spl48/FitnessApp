package seng202.team6.models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Angelica Dela Cruz on 29 Aug 2018.
 */
public class UserTest {

    @org.junit.Test
    public void setName()
    {
        User user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");

        user1.setName("");
        assertEquals("invalid", user1.getName());

        user1.setName("Q");
        assertEquals("invalid", user1.getName());

        user1.setName("Abby");
        assertEquals("Abby", user1.getName());
    }

    @org.junit.Test
    public void getName()
    {
        User user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");

        assertEquals("Angelica", user1.getName());
    }

    @org.junit.Test
    public void setDOB()
    {
        User user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");

        user1.setDOB("02/04");
        assertEquals("DD/MM/YY", user1.getDOB());

        user1.setDOB("02/02/98");
        assertEquals("02/02/98", user1.getDOB());
    }

    @org.junit.Test
    public void getDOB()
    {
        User user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");

        assertEquals("16/09/97", user1.getDOB());
    }

    @org.junit.Test
    public void setAge()
    {
        User user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");

        user1.setAge(3);
        assertEquals(0, user1.getAge());

        user1.setAge(20);
        assertEquals(20, user1.getAge());
    }

    @org.junit.Test
    public void getAge()
    {
        User user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");

        assertEquals(16, user1.getAge());
    }

    @org.junit.Test
    public void setHeight()
    {
        User user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");

        user1.setHeight(-170.0);
        assertEquals(0.0, user1.getHeight(), 0.1);

        user1.setHeight(170.0);
        assertEquals(170.0, user1.getHeight(), 0.1);
    }

    @org.junit.Test
    public void getHeight()
    {
        User user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");

        assertEquals(166.5, user1.getHeight(), 0.1);
    }

    @org.junit.Test
    public void setWeight()
    {
        User user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");

        user1.setWeight(-80.0);
        assertEquals(0.0, user1.getWeight(), 0.1);

        user1.setWeight(80.0);
        assertEquals(80.0, user1.getWeight(), 0.1);
    }

    @org.junit.Test
    public void getWeight()
    {
        User user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");

        assertEquals(55.0, user1.getWeight(), 0.1);
    }

    @org.junit.Test
    public void setStrideLength()
    {
        User user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");

        user1.setStrideLength(-2.0);
        assertEquals(0.0, user1.getStrideLength(), 0.1);

        user1.setStrideLength(3.0);
        assertEquals(3.0, user1.getStrideLength(), 0.1);
    }

    @org.junit.Test
    public void getStrideLength()
    {
        User user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");

        assertEquals(2.2, user1.getStrideLength(), 0.1);
    }

    @org.junit.Test
    public void setUsername()
    {
        User user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");

        user1.setUsername("aq");
        assertEquals("invalid", user1.getUsername());

        user1.setUsername("Doublelift");
        assertEquals("Doublelift", user1.getUsername());
    }

    @org.junit.Test
    public void getUsername()
    {
        User user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");

        assertEquals("acilegna", user1.getUsername());
    }

}