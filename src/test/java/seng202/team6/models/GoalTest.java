package seng202.team6.models;

import junit.framework.TestCase;

import java.time.LocalDate;

public class GoalTest extends TestCase {

    LocalDate date = LocalDate.of(2018, 9, 01);
    LocalDate date2 = LocalDate.of(2018, 9, 16);
    LocalDate date3 = LocalDate.of(2018, 9, 29);
    Goal goal = new Goal("Lose 10kg", date);

    public void testSetGoalName() {
        // Invalid input
        goal.setGoalName("Eat");
        assertEquals("invalid", goal.getGoalName());

        // Valid input
        goal.setGoalName("Bulking");
        assertEquals("Bulking", goal.getGoalName());
    }

    public void testGetGoalName() {
        assertEquals("Lose 10kg", goal.getGoalName());
    }

    public void testSetDateSet() {
        goal.setDateSet(date2);
        assertEquals(date2, goal.getDateSet());
    }

    public void testGetDateSet() {
        assertEquals(date, goal.getDateSet());
    }

    public void testSetDateAchieved() {
        goal.setDateAchieved(date2);
        assertEquals(date2, goal.getDateAchieved());
    }

    public void testGetDateAchieved() {
        goal.setDateAchieved(date3);
        assertEquals(date3, goal.getDateAchieved());
    }

    public void testSetGoalReached() {
        goal.setGoalReached(true);
        assertTrue(goal.getGoalReached());

        goal.setGoalReached(false);
        assertFalse(goal.getGoalReached());
    }

    public void testGetGoalReached() {
        assertFalse(goal.getGoalReached());
    }
}