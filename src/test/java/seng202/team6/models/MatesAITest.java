package seng202.team6.models;

import junit.framework.TestCase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class MatesAITest extends TestCase {

    private MatesAI mates = new MatesAI("Baymax");
    LocalDate dob = LocalDate.of(2011, 5,8);
    private User user = new User("Bob", "Wortsy", dob, "Male",166.5, 55.0,  "bob101", 56778, 50, 100);

    LocalDate dob2 = LocalDate.of(1997, 1, 9);
    private User user2 = new User("Mike", "Wobs", dob2, "Male",180.0, 80.0,  "mike26", 67890, 50, 100);

    public void testSetName() {
        mates.setName("Fattie");
        assertEquals("Fattie", mates.getName());
    }

    public void testGetName() {
        assertEquals("Baymax", mates.getName());
    }

    public void testSetGreeting() {
        mates.setGreeting(user);
        assertEquals("Hello bob101!", mates.getGreeting());
    }

    public void testGetGreeting() {
        mates.setGreeting(user2);
        assertEquals("Hello mike26!", mates.getGreeting());
    }

    public void testAddMotivations() {
        mates.addMotivations("You can do it!");
        mates.addMotivations("Keep going!");
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("You can do it!", "Keep going!"));
        assertEquals(expected, mates.getMotivations());
    }

    public void testGetMotivations() {
        mates.addMotivations("You can do it!");
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("You can do it!"));
        assertEquals(expected, mates.getMotivations());
    }

    public void testAddReminders() {
        mates.addReminders("Lose 9 more kg!");
        mates.addReminders("Lose 5 more kg!");
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("Lose 9 more kg!", "Lose 5 more kg!"));
        assertEquals(expected, mates.getReminders());
    }

    public void testGetReminders() {
        mates.addReminders("Lose 9 more kg!");
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("Lose 9 more kg!"));
        assertEquals(expected, mates.getReminders());
    }

    public void testAddCongratulationMessage() {
        mates.addCongratulationMessage("Congratulations on achieving weight lose of 9 kgs!");
        mates.addCongratulationMessage("Congratulations on achieving 1000 steps!");
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("Congratulations on achieving weight lose of 9 kgs!", "Congratulations on achieving 1000 steps!"));
        assertEquals(expected, mates.getCongratulationMessage());
    }

    public void testGetCongratulationMessage() {
        mates.addCongratulationMessage("Congratulations on achieving 1000 steps!");
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("Congratulations on achieving 1000 steps!"));
        assertEquals(expected, mates.getCongratulationMessage());
    }
}