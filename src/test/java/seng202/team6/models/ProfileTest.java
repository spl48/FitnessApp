package seng202.team6.models;

import junit.framework.TestCase;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class ProfileTest extends TestCase {
    LocalDate now = LocalDate.now();
    LocalDate date = LocalDate.of(2018, 1, 16);
    LocalTime startTime = LocalTime.of(11, 30);
    LocalTime endTime = LocalTime.of(12, 30);
    long totalTime = Duration.between(startTime, endTime).toMinutes();

    LocalDate dob = LocalDate.of(1997, 1, 9);
    private User user = new User("Mike", "Wobs", dob, "Male",180.0, 80.0, 3.2, "mike26", 67890);
    private Profile profile = new Profile(user);

    LocalDate dob2 = LocalDate.of(2011, 5,8);
    private User user2 = new User("Bob", "Wortsy", dob2, "Male",166.5, 55.0, 2.2, "bob101", 56778);

    public void testSetUser() {
        profile.setUser(user2);
        assertEquals(user2, profile.getUser());
    }

    public void testGetUser() {
        assertEquals(user, profile.getUser());
    }

    public void testAddGoals() {
        LocalDate date = LocalDate.of(2018, 9, 01);
        Goal goal = new Goal("Lose 10kg", date);
        Goal goal2 = new Goal("1000 Steps", date);
        profile.addGoals(goal);
        profile.addGoals(goal2);
        ArrayList<Goal> expected = new ArrayList<Goal>(Arrays.asList(goal, goal2));
        assertEquals(expected, profile.getGoals());
    }

    public void testGetGoals() {
        LocalDate date = LocalDate.of(2018, 9, 01);
        Goal goal = new Goal("Lose 10kg", date);
        profile.addGoals(goal);
        ArrayList<Goal> expected = new ArrayList<Goal>(Arrays.asList(goal));
        assertEquals(expected, profile.getGoals());
    }

    public void testSetGoalStatus() {
        LocalDate date = LocalDate.of(2018, 9, 01);
        Goal goal = new Goal("Lose 10kg", date);
        profile.setGoalStatus(goal, true);
        assertTrue(goal.getGoalReached());
    }

    public void testSetRunningData() {
        Activity activity1 = new Activity( 123,"Running", "Running in the Hills", date, date, startTime, endTime);
        Activity activity2 = new Activity(124, "Running", "Running in the Park", now, now, startTime, endTime);

        ActivityList runList = new ActivityList("Running");
        runList.addActivity(activity1);
        runList.addActivity(activity2);
        profile.setRunningData(runList);
        assertEquals(runList, profile.getRunningData());
    }

    public void testGetRunningData() {
        Activity activity1 = new Activity( 123,"Running", "Running in the Hills", date, date, startTime, endTime);
        ActivityList runList = new ActivityList("Running");
        runList.addActivity(activity1);
        profile.setRunningData(runList);
        assertEquals(runList, profile.getRunningData());
    }

    public void testSetWalkingData() {
        Activity activity1 = new Activity( 125,"Walking", "Walking in the Hills", date, date, startTime, endTime);
        Activity activity2 = new Activity(126, "Walking", "Walking in the Park", now, now, startTime, endTime);

        ActivityList walkList = new ActivityList("Walking");
        walkList.addActivity(activity1);
        walkList.addActivity(activity2);
        profile.setWalkingData(walkList);
        assertEquals(walkList, profile.getWalkingData());
    }

    public void testGetWalkingData() {
        Activity activity1 = new Activity( 127,"Walking", "Walking in the Hills", date, date, startTime, endTime);
        ActivityList walkList = new ActivityList("Walking");
        walkList.addActivity(activity1);
        profile.setWalkingData(walkList);
        assertEquals(walkList, profile.getWalkingData());
    }

    public void testSetBikingData() {
        Activity activity1 = new Activity( 128,"Biking", "Biking in the Hills", date, date, startTime, endTime);
        Activity activity2 = new Activity(129, "Biking", "Biking in the Park", now, now, startTime, endTime);
        ActivityList bikeList = new ActivityList("Biking");
        bikeList.addActivity(activity1);
        bikeList.addActivity(activity2);
        profile.setBikingData(bikeList);
        assertEquals(bikeList, profile.getBikingData());
    }

    public void testGetBikingData() {
        Activity activity1 = new Activity( 128,"Biking", "Biking in the Hills", date, date, startTime, endTime);
        ActivityList bikeList = new ActivityList("Biking");
        bikeList.addActivity(activity1);
        profile.setBikingData(bikeList);
        assertEquals(bikeList, profile.getBikingData());
    }

    public void testAddAchievements() {
        Achievement achievement1 = new Achievement("Lost 1 kg");
        profile.addAchievements(achievement1);
        Achievement achievement2 = new Achievement("1000 Steps");
        profile.addAchievements(achievement2);
        ArrayList<Achievement> expected = new ArrayList<Achievement>(Arrays.asList(achievement1, achievement2));
        assertEquals(expected.size(), profile.getAchievements().size());
    }

    public void testGetAchievements() {
        Achievement achievement1 = new Achievement("Lost 1 kg");
        profile.addAchievements(achievement1);
        ArrayList<Achievement> expected = new ArrayList<Achievement>(Arrays.asList(achievement1));
        assertEquals(expected.size(), profile.getAchievements().size());
    }

    public void testAddHealthConcerns() {
        profile.addHealthConcerns("Tachycardia");
        profile.addHealthConcerns("Cardiovascular Mortality");
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("Tachycardia", "Cardiovascular Mortality"));
        assertEquals(expected, profile.getHealthConcerns());
    }

    public void testGetHealthConcerns() {
        profile.addHealthConcerns("Tachycardia");
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("Tachycardia"));
        assertEquals(expected, profile.getHealthConcerns());
    }
}