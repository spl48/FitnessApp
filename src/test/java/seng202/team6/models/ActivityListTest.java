package seng202.team6.models;

import junit.framework.TestCase;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class ActivityListTest extends TestCase {

    private ActivityList activity = new ActivityList("Running");

    LocalDate date = LocalDate.of(2018, 1, 16);
    LocalTime startTime = LocalTime.of(11, 30);
    LocalTime endTime = LocalTime.of(12, 30);
    long totalTime = Duration.between(startTime, endTime).toMinutes();
    Activity activity1 = new Activity( 123,"Running", "Running in the Hills", date, date, startTime, endTime);
    Activity activity2 = new Activity( 123,"Walking", "Walking in the Hills", date, date, startTime, endTime);
    Activity activity3 = new Activity( 128,"Biking", "Biking in the Hills", date, date, startTime, endTime);

    public void testSetActivityCategory() {
        // Invalid input
        activity.setActivityCategory("Swimming");
        assertEquals("invalid", activity.getActivityCategory());

        // Valid input
        activity.setActivityCategory("Walking");
        assertEquals("Walking", activity.getActivityCategory());
    }

    public void testGetActivityCategory() {
        assertEquals("Running", activity.getActivityCategory());
    }

    public void testAddActivity() {
        ArrayList<Activity> expected = new ArrayList<Activity>(Arrays.asList(activity1, activity2));
        activity.addActivity(activity1);
        activity.addActivity(activity2);
        assertTrue(activity.getActivities().equals(expected));
    }

    public void testRemoveActivity() {
        ArrayList<Activity> expected = new ArrayList<Activity>(Arrays.asList(activity2));
        activity.addActivity(activity1);
        activity.addActivity(activity2);
        activity.removeActivity(activity1);
        assertTrue(activity.getActivities().equals(expected));
    }

    public void testGetActivities() {
        ArrayList<Activity> expected = new ArrayList<Activity>(Arrays.asList(activity1, activity2, activity3));
        activity.addActivity(activity1);
        activity.addActivity(activity2);
        activity.addActivity(activity3);
        assertTrue(activity.getActivities().equals(expected));
    }
}