package seng202.team6.models;

import junit.framework.TestCase;

public class AchievementTest extends TestCase {

    private Achievement achievement = new Achievement("1000 Steps");

    public void testSetName() {
        // Invalid Input
        achievement.setName("Step");
        assertEquals("invalid", achievement.getName());

        // Valid Input
        achievement.setName("10000 Steps");
        assertEquals("10000 Steps", achievement.getName());
    }

    public void testGetName() {
        assertEquals("1000 Steps", achievement.getName());
    }
}