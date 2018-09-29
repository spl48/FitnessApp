package seng202.team6.models;

import junit.framework.TestCase;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class ActivityTest extends TestCase {
    LocalDate date1 = LocalDate.of(2018, 1, 16);
    LocalTime startTime1 = LocalTime.of(11, 30);
    LocalTime endTime1 = LocalTime.of(12, 30);
    long totalTime = Duration.between(startTime1, endTime1).toMinutes();
    Activity activity1 = new Activity( 123,"Running", "Running in the Hills", date1, date1, startTime1, endTime1);


    LocalDate date2 = LocalDate.of(2018, 02, 16);
    LocalTime startTime2 = LocalTime.of(12, 30);
    LocalTime endTime2 = LocalTime.of(14, 00);

    ActivityDataPoint point1 = new ActivityDataPoint(startTime1, 78, 40.9, 174.0, 50.0);
    ActivityDataPoint point2 = new ActivityDataPoint(startTime2, 90, 50.9, 154.0, 30.0);
    ActivityDataPoint point3 = new ActivityDataPoint(startTime1, 63, 50.9, 144.0, 23.0);


   Activity testActivity;


    public void testSetActivityid() {
        activity1.setActivityid(123456);
        assertEquals(123456, activity1.getActivityid());
    }

    public void testGetActivityid() {
        assertEquals(123, activity1.getActivityid());
    }

    public void testSetType() {
        // Invalid input
        activity1.setType("Run");
        assertEquals("invalid", activity1.getType());

        // Valid input
        activity1.setType("Walking");
        assertEquals("Walking", activity1.getType());

    }

    public void testGetType() {
        assertEquals("Running", activity1.getType());
    }

    public void testSetDescription() {
        activity1.setDescription("Running in the Beach");
        assertEquals("Running in the Beach", activity1.getDescription());
    }

    public void testGetDescription() {
        assertEquals("Running in the Hills", activity1.getDescription());
    }

    public void testSetNotes() {
        activity1.setNotes("It was a hot beautiful day.");
        assertEquals("It was a hot beautiful day.", activity1.getNotes());
    }

    public void testGetNotes() {
        activity1.setNotes("It was a bright day.");
        assertEquals("It was a bright day.", activity1.getNotes());
    }

    public void testSetStartDate() {
        activity1.setStartDate(date2);
        assertEquals(date2, activity1.getStartDate());
    }

    public void testSetEndDate() {
        activity1.setEndDate(date2);
        assertEquals(date2, activity1.getEndDate());
    }

    public void testGetStartDate() {
        assertEquals(date1, activity1.getStartDate());
    }

    public void testGetEndDate() {
        assertEquals(date1, activity1.getEndDate());
    }

    public void testSetTime() {
        long totalTime2 = Duration.between(startTime2, endTime2).toMinutes();
        activity1.setTime(startTime2, endTime2);
        assertEquals(startTime2, activity1.getStartTime());
        assertEquals(endTime2, activity1.getEndTime());
        assertEquals(totalTime2, activity1.getTotalTime());
    }

    public void testGetStartTime() {
        assertEquals(startTime1, activity1.getStartTime());
    }

    public void testGetEndTime() {
        assertEquals(endTime1, activity1.getEndTime());
    }

    public void testGetTotalTime() {
        assertEquals(totalTime, activity1.getTotalTime());
    }

    public void testSetDistance() {
        // Invalid Input
        activity1.setDistance(-10.0);
        assertEquals(0.0, activity1.getDistance(), 0.1);
        // Valid Input
        activity1.setDistance(30.0);
        assertEquals(30.0, activity1.getDistance(), 0.1);
    }

    public void testGetDistance() {
        activity1.setDistance(30.0);
        assertEquals(30.0, activity1.getDistance(), 0.1);
    }

    public void testUpdateMinHeartRate() {
        activity1.addActivityData(point1);
        activity1.addActivityData(point2);

        activity1.updateMinHeartRate();
        assertEquals(78, activity1.getMinHeartRate());

    }

    public void testUpdateMaxHeartRate() {
        activity1.addActivityData(point1);
        activity1.addActivityData(point2);

        activity1.updateMaxHeartRate();
        assertEquals(90, activity1.getMaxHeartRate());
    }

    public void testGetMinHeartRate() {
        activity1.addActivityData(point1);
        activity1.addActivityData(point3);

        activity1.updateMinHeartRate();
        assertEquals(63, activity1.getMinHeartRate());
    }

    public void testGetMaxHeartRate() {
        activity1.addActivityData(point1);
        activity1.addActivityData(point3);
        activity1.updateMaxHeartRate();
        assertEquals(78, activity1.getMaxHeartRate());

    }

    public void testAddActivityData() {
        LocalTime time1 = LocalTime.of(9, 8);
        LocalTime time2 = LocalTime.of(11, 25);
        ActivityDataPoint point1 = new ActivityDataPoint(time1, 80, 40.9, 174.5, 20.5);
        ActivityDataPoint point2 = new ActivityDataPoint(time2, 75, 49.5, 176.0, 30.5);
        ArrayList<ActivityDataPoint> expected = new ArrayList<ActivityDataPoint>(Arrays.asList(point1, point2));
        activity1.addActivityData(point1);
        activity1.addActivityData(point2);
        assertEquals(expected.size(), activity1.getActivityData().size());
    }

    public void testGetActivityData() {
        LocalTime time1 = LocalTime.of(9, 8);
        ActivityDataPoint point1 = new ActivityDataPoint(time1, 80, 40.9, 174.5, 20.5);
        ArrayList<ActivityDataPoint> expected = new ArrayList<ActivityDataPoint>(Arrays.asList(point1));
        activity1.addActivityData(point1);
        assertEquals(expected.size(), activity1.getActivityData().size());
    }


    public void testFindStepCount() {
    LocalDate testDate = LocalDate.of(2018, 10, 9);
    LocalTime testTime = LocalTime.of(5, 30);


    testActivity = new Activity(12345, "Cycling", "Cycle around the block", testDate, testDate, testTime, testTime);
    ActivityDataPoint p1 = new ActivityDataPoint(testTime, 85, -43.530029, 172.582520, 88);
    ActivityDataPoint p2 = new ActivityDataPoint(testTime, 120, -43.54650, 172.583520, 88);
    testActivity.addActivityData(p1);
    testActivity.addActivityData(p2);
    assertEquals(0.0, testActivity.findStepCount(1.5));           // Cycling activity data type

    testActivity = new Activity(12345, "Other", "Cycle around the block", testDate, testDate, testTime, testTime);
    testActivity.addActivityData(p1);
    testActivity.addActivityData(p2);
    assertEquals(0.0, testActivity.findStepCount(1.5));           // Other activity data type


    testActivity = new Activity(12345, "Walking", "Cycle around the block", testDate, testDate, testTime, testTime);
    p1 = new ActivityDataPoint(testTime, 85, -43.530029, 172.582520, 88);
    p2 = new ActivityDataPoint(testTime, 120, -43.54650, 172.583520, 88);
    testActivity.addActivityData(p1);
    testActivity.addActivityData(p2);

    assertEquals(20048, testActivity.findStepCount(0.3), 0.5);          // Minimum stride length (distance of 1.8km)
    assertEquals(2406, testActivity.findStepCount(2.5), 0.5);            // Maximum Stride length
    assertEquals(4296, testActivity.findStepCount(1.4), 0.5);            // Maximum Stride length


    testActivity = new Activity(12345, "Running", "Run around the block", testDate, testDate, testTime, testTime);
    p1 = new ActivityDataPoint(testTime, 85, -43.530029, 172.582520, 88);
    p2 = new ActivityDataPoint(testTime, 120, -43.530030, 172.583521, 88);
    testActivity.addActivityData(p1);
    testActivity.addActivityData(p2);
    assertEquals(132, testActivity.findStepCount(2), 0.5);           // Small distance of 0.08km covered


    testActivity = new Activity(12345, "Running", "Run around the block", testDate, testDate, testTime, testTime);
    p1 = new ActivityDataPoint(testTime, 85, -43.530029, 172.582520, 88);
    p2 = new ActivityDataPoint(testTime, 120, -44.0, 173.0, 88);
    testActivity.addActivityData(p1);
    testActivity.addActivityData(p2);
    assertEquals(101844, testActivity.findStepCount(2), 0.5);           // Large distance of 62.1km covered

    }

    public void testFindDistanceFromStart() {
    LocalDate testDate = LocalDate.of(2018, 10, 9);
    LocalTime testTime = LocalTime.of(5, 30);


    testActivity = new Activity(12345, "Running", "Run around the block", testDate, testDate, testTime, testTime);

    ActivityDataPoint p1 = new ActivityDataPoint(testTime, 85, -43.530029, 172.582520, 88);
    ActivityDataPoint p2 = new ActivityDataPoint(testTime, 120, -43.530029, 172.582520, 88);
    ActivityDataPoint p3 = new ActivityDataPoint(testTime, 111, -43.519975, 172.579222, 94);
    ActivityDataPoint p4 = new ActivityDataPoint(testTime, 104, -43.530029, 172.582520, 88);
    ActivityDataPoint p5 = new ActivityDataPoint(testTime, 101, -43.530834, 172.582520, 88);
    ActivityDataPoint p6 = new ActivityDataPoint(testTime, 98, -43.530834, 172.59563, 92);
    ActivityDataPoint p7 = new ActivityDataPoint(testTime, 98, -43.7, 173.5, 92);


    testActivity.addActivityData(p1);
    testActivity.addActivityData(p2);
    testActivity.addActivityData(p3);
    testActivity.addActivityData(p4);
    testActivity.addActivityData(p5);
    testActivity.addActivityData(p6);
    testActivity.addActivityData(p7);

    assertEquals(0.0, testActivity.findDistanceFromStart(1));  //No distance moved
    assertEquals(2.3, testActivity.findDistanceFromStart(3), 0.05);  //Moved in a circle
    assertEquals(2.4, testActivity.findDistanceFromStart(4), 0.05);  //Moved only in latitude
    assertEquals(3.4, testActivity.findDistanceFromStart(5), 0.05);  //Moved only in longitude
    assertEquals(78.6, testActivity.findDistanceFromStart(6), 0.05);  //Moved large distance
    }



    public void testFindCaloriesBurnedFromStart() {
        activity1.setType("Walking");

        assertEquals(0.0, activity1.findCaloriesBurnedFromStart(0,65));      // No activity time
        assertEquals(0.0, activity1.findCaloriesBurnedFromStart(-50, 65));       //Negative activity time

        assertEquals(17, activity1.findCaloriesBurnedFromStart(5, 65), 0.5); // Small activity time
        assertEquals(1365, activity1.findCaloriesBurnedFromStart(400, 65), 0.05); // Large activity time

        assertEquals(472, activity1.findCaloriesBurnedFromStart(60, 150), 0.5); // Large weight User
        assertEquals(31, activity1.findCaloriesBurnedFromStart(60, 10), 0.5); // Small weight User


        activity1.setType("");
        assertEquals(307, activity1.findCaloriesBurnedFromStart(60, 65), 0.5); // Unknown activity type.
        activity1.setType("Other");
        assertEquals(307, activity1.findCaloriesBurnedFromStart(60, 65), 0.5); // Other activity type
        activity1.setType("Running");
        assertEquals(512, activity1.findCaloriesBurnedFromStart(60,  65), 0.5); // Running activity type
        activity1.setType("Biking");
        assertEquals(409, activity1.findCaloriesBurnedFromStart(60, 65), 0.5); // Cycling activity type
    }




    public void testGetActivityTypeFromDescription() {
        LocalDate testDate = LocalDate.of(2018, 10, 9);
        LocalTime testTime = LocalTime.of(5, 30);



        testActivity = new Activity(12345, "Running", "Run around the block", testDate, testDate, testTime, testTime);
        assertEquals("Running", Activity.getActivityType(testActivity.getDescription()));

        testActivity = new Activity(12345, "Running", "RUNNING IN THE CITY", testDate, testDate, testTime, testTime);
        assertEquals("Running", Activity.getActivityType(testActivity.getDescription()));

        testActivity = new Activity(12345, "Running", "jog", testDate, testDate, testTime, testTime);
        assertEquals("Running", Activity.getActivityType(testActivity.getType()));

        testActivity = new Activity(12345, "Running", "joGGinG in the park", testDate, testDate, testTime, testTime);
        assertEquals("Running", Activity.getActivityType(testActivity.getType()));



        testActivity = new Activity(12345, "Cycling", "small BIKE ride", testDate, testDate, testTime, testTime);
        assertEquals("Biking", Activity.getActivityType(testActivity.getDescription()));

        testActivity = new Activity(12345, "Cycling", "biking with friends", testDate, testDate, testTime, testTime);
        assertEquals("Biking", Activity.getActivityType(testActivity.getDescription()));

        testActivity = new Activity(12345, "Cycling", "out cyclInG", testDate, testDate, testTime, testTime);
        assertEquals("Biking", Activity.getActivityType(testActivity.getDescription()));

        testActivity = new Activity(12345, "Cycling", "   cycle   ", testDate, testDate, testTime, testTime);
        assertEquals("Biking", Activity.getActivityType(testActivity.getDescription()));



        testActivity = new Activity(12345, "Walking", "  dog walking", testDate, testDate, testTime, testTime);
        assertEquals("Walking", Activity.getActivityType(testActivity.getDescription()));

        testActivity = new Activity(12345, "Walking", "WALK WITH FRIENDS", testDate, testDate, testTime, testTime);
        assertEquals("Walking", Activity.getActivityType(testActivity.getDescription()));

        testActivity = new Activity(12345, "Walking", "hIkE in the woods", testDate, testDate, testTime, testTime);
        assertEquals("Walking", Activity.getActivityType(testActivity.getDescription()));

        testActivity = new Activity(12345, "Walking", "  hiking", testDate, testDate, testTime, testTime);
        assertEquals("Walking", Activity.getActivityType(testActivity.getDescription()));



        testActivity = new Activity(12345, "Other", "  ", testDate, testDate, testTime, testTime);
        assertEquals("Other", Activity.getActivityType(testActivity.getDescription()));

        testActivity = new Activity(12345, "Other", "w alk", testDate, testDate, testTime, testTime);
        assertEquals("Other", Activity.getActivityType(testActivity.getDescription()));

        testActivity = new Activity(12345, "Other", "walkingwithfriends", testDate, testDate, testTime, testTime);
        assertEquals("Other", Activity.getActivityType(testActivity.getDescription()));

    }

    public void testFindAverageSpeed() {
        
    }

    public void testFindAverageSpeedManual() {

    }
}