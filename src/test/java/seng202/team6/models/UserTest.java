package seng202.team6.models;

import junit.framework.TestCase;

import java.time.LocalDate;

public class UserTest extends TestCase {

    LocalDate dob = LocalDate.of(2011, 5, 8);
    LocalDate dob2 = LocalDate.of(2001, 1, 9);
    LocalDate dob3 = LocalDate.of(2001, 12, 9);
    LocalDate now = LocalDate.now();
    private User user = new User("Bob", "Wortsy", dob, "Male", 166.5, 55.0,  "bob101", 56778);

    public void testSetName() {
        user.setName("Mike", "Wobs");
        assertEquals("Mike", user.getFirstName());
        assertEquals("Wobs", user.getLastName());
    }

    public void testGetFirstName() {
        assertEquals("Bob", user.getFirstName());
    }

    public void testGetLastName() {
        assertEquals("Wortsy", user.getLastName());
    }

    public void testSetAgeAndDOB() {
        user.setAgeAndDOB(dob2);
        assertEquals(17, user.getAge());
        assertEquals(dob2, user.getDOB());

        user.setAgeAndDOB(dob3);
        assertEquals(16, user.getAge());
        assertEquals(dob3, user.getDOB());
    }

    public void testGetAge() {
        assertEquals(7, user.getAge());
    }

    public void testGetDOB() {
        assertEquals(dob, user.getDOB());
    }

    public void testSetGender() {
        user.setGender("Female");
        assertEquals("Female", user.getGender());

        user.setGender("Male");
        assertEquals("Male", user.getGender());
    }

    public void testGetGender() {
        assertEquals("Male", user.getGender());
    }

    public void testSetHeight() {
        user.setHeight(170.0);
        assertEquals(170.0, user.getHeight(), 0.1);
    }

    public void testGetHeight() {
        assertEquals(166.5, user.getHeight(), 0.1);
    }

    public void testSetWeight() {
        user.setWeight(60.0);
        assertEquals(60.0, user.getWeight(), 0.1);
    }

    public void testGetWeight() {
        assertEquals(55.0, user.getWeight(), 0.1);
    }

    public void testSetStrideLength() {
        user.setWalkingStrideLength(3.0);
        assertEquals(3.0, user.getWalkingStrideLength(), 0.1);
    }

    public void testGetStrideLength() {
        assertEquals(2.2, user.getWalkingStrideLength(), 0.1);
    }

    public void testSetUsername() {
        user.setUsername("Doublelift");
        assertEquals("Doublelift", user.getUsername());
    }

    public void testGetUsername() {
        assertEquals("bob101", user.getUsername());
    }

    public void testGetFullName() {
        assertEquals("Bob Wortsy", user.getFullName());
    }

    public void testSetUserID() {
        user.setUserID(89001);
        assertEquals(89001, user.getUserID());
    }

    public void testGetUserID() {
        assertEquals(56778, user.getUserID());
    }
}