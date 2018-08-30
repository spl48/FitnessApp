package seng202.team6.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private User user1;

    @Before
    public void setUp() throws Exception {
        user1 = new User("Angelica", "16/09/97", 16, 166.5, 55.0, 2.2, "acilegna");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setName() {
        user1.setName("");
        assertEquals("invalid", user1.getName());

        user1.setName("Q");
        assertEquals("invalid", user1.getName());

        user1.setName("Abby");
        assertEquals("Abby", user1.getName());
    }

    @Test
    public void getName() {
        assertEquals("Angelica", user1.getName());
    }

    @Test
    public void setDOB() {
        user1.setDOB("02/04");
        assertEquals("DD/MM/YY", user1.getDOB());

        user1.setDOB("02/02/98");
        assertEquals("02/02/98", user1.getDOB());
    }

    @Test
    public void getDOB() {
        assertEquals("16/09/97", user1.getDOB());
    }

    @Test
    public void setAge() {
        user1.setAge(3);
        assertEquals(0, user1.getAge());

        user1.setAge(20);
        assertEquals(20, user1.getAge());
    }

    @Test
    public void getAge() {
        assertEquals(16, user1.getAge());
    }

    @Test
    public void setHeight() {
        user1.setHeight(-170.0);
        assertEquals(0.0, user1.getHeight(), 0.1);

        user1.setHeight(170.0);
        assertEquals(170.0, user1.getHeight(), 0.1);
    }

    @Test
    public void getHeight() {
        assertEquals(166.5, user1.getHeight(), 0.1);
    }

    @Test
    public void setWeight() {
        user1.setWeight(-80.0);
        assertEquals(0.0, user1.getWeight(), 0.1);

        user1.setWeight(80.0);
        assertEquals(80.0, user1.getWeight(), 0.1);
    }

    @Test
    public void getWeight() {
        assertEquals(55.0, user1.getWeight(), 0.1);
    }

    @Test
    public void setStrideLength() {
        user1.setStrideLength(-2.0);
        assertEquals(0.0, user1.getStrideLength(), 0.1);

        user1.setStrideLength(3.0);
        assertEquals(3.0, user1.getStrideLength(), 0.1);
    }

    @Test
    public void getStrideLength() {
        assertEquals(2.2, user1.getStrideLength(), 0.1);
    }

    @Test
    public void setUsername() {
        user1.setUsername("aq");
        assertEquals("invalid", user1.getUsername());

        user1.setUsername("Doublelift");
        assertEquals("Doublelift", user1.getUsername());
    }

    @Test
    public void getUsername() {
        assertEquals("acilegna", user1.getUsername());
    }
}