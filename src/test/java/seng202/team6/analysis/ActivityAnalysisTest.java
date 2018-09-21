package seng202.team6.analysis;

import junit.framework.TestCase;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import java.time.LocalDate;
import java.time.LocalTime;


public class ActivityAnalysisTest extends TestCase {


    Activity testActivity;

    public void testFindMaximumHeartRate() {
        LocalDate testDate = LocalDate.of(2018, 10, 9);
        LocalTime testTime = LocalTime.of(5, 30);

        ActivityDataPoint p1 = new ActivityDataPoint(testTime, -20, -43.530029, 172.582520, 88);
        ActivityDataPoint p2 = new ActivityDataPoint(testTime, 1, -43.523584, 172.579179, 100);
        ActivityDataPoint p3 = new ActivityDataPoint(testTime, 96, -43.519975, 172.579222, 94);
        ActivityDataPoint p4 = new ActivityDataPoint(testTime, 96, -43.522371, 172.589474, 88);
        ActivityDataPoint p5 = new ActivityDataPoint(testTime, 200, -43.530834, 172.586771, 88);

        testActivity = new Activity(12345, "Running", "Run around the block", testDate, testDate, testTime, testTime);

        assertEquals(0, ActivityAnalysis.findMaximumHeartRate(testActivity));         //No data points in activity
        testActivity.addActivityData(p1);
        assertEquals(0, ActivityAnalysis.findMaximumHeartRate(testActivity));         //Negative heart rate is 'maximum'
        testActivity.addActivityData(p2);
        assertEquals(1, ActivityAnalysis.findMaximumHeartRate(testActivity));          // small maximum heart rate
        testActivity.addActivityData(p3);
        assertEquals(96, ActivityAnalysis.findMaximumHeartRate(testActivity));         // Normal range heart rate
        testActivity.addActivityData(p4);
        assertEquals(96, ActivityAnalysis.findMaximumHeartRate(testActivity));         // Two of the same heart rates
        testActivity.addActivityData(p5);
        assertEquals(200, ActivityAnalysis.findMaximumHeartRate(testActivity));         // Large heart rate
    }



    public void testFindMinimumHeartRate() {
        LocalDate testDate = LocalDate.of(2018, 10, 9);
        LocalTime testTime = LocalTime.of(5, 30);

        ActivityDataPoint p1 = new ActivityDataPoint(testTime, 200, -43.530029, 172.582520, 88);
        ActivityDataPoint p2 = new ActivityDataPoint(testTime, 200, -43.523584, 172.579179, 100);
        ActivityDataPoint p3 = new ActivityDataPoint(testTime, 85, -43.519975, 172.579222, 94);
        ActivityDataPoint p4 = new ActivityDataPoint(testTime, 1, -43.522371, 172.589474, 88);

        testActivity = new Activity(12345, "Running", "Run around the block", testDate, testDate, testTime, testTime);

        assertEquals(0, ActivityAnalysis.findMinimumHeartRate(testActivity));         //No data points in activity
        testActivity.addActivityData(p1);
        assertEquals(200, ActivityAnalysis.findMinimumHeartRate(testActivity));       // large heart rate is Minimum
        testActivity.addActivityData(p2);
        assertEquals(200, ActivityAnalysis.findMinimumHeartRate(testActivity));        // two of the same minimum heart rates
        testActivity.addActivityData(p3);
        assertEquals(85, ActivityAnalysis.findMinimumHeartRate(testActivity));          // Normal range minimum heart rate
        testActivity.addActivityData(p4);
        assertEquals(1, ActivityAnalysis.findMinimumHeartRate(testActivity));         // small minimum heart rate

    }



    public void testFindStepCount() {
        LocalDate testDate = LocalDate.of(2018, 10, 9);
        LocalTime testTime = LocalTime.of(5, 30);


        testActivity = new Activity(12345, "Cycling", "Cycle around the block", testDate, testDate, testTime, testTime);
        ActivityDataPoint p1 = new ActivityDataPoint(testTime, 85, -43.530029, 172.582520, 88);
        ActivityDataPoint p2 = new ActivityDataPoint(testTime, 120, -43.54650, 172.583520, 88);
        testActivity.addActivityData(p1);
        testActivity.addActivityData(p2);
        assertEquals(0.0, ActivityAnalysis.findStepCount(testActivity, 1.5));           // Cycling activity data type

        testActivity = new Activity(12345, "Other", "Cycle around the block", testDate, testDate, testTime, testTime);
        testActivity.addActivityData(p1);
        testActivity.addActivityData(p2);
        assertEquals(0.0, ActivityAnalysis.findStepCount(testActivity, 1.5));           // Other activity data type


        testActivity = new Activity(12345, "Walking", "Cycle around the block", testDate, testDate, testTime, testTime);
        p1 = new ActivityDataPoint(testTime, 85, -43.530029, 172.582520, 88);
        p2 = new ActivityDataPoint(testTime, 120, -43.54650, 172.583520, 88);
        testActivity.addActivityData(p1);
        testActivity.addActivityData(p2);

        assertEquals(20048, ActivityAnalysis.findStepCount(testActivity, 0.3), 0.5);          // Minimum stride length (distance of 1.8km)
        assertEquals(2406, ActivityAnalysis.findStepCount(testActivity, 2.5), 0.5);            // Maximum Stride length
        assertEquals(4296, ActivityAnalysis.findStepCount(testActivity, 1.4), 0.5);            // Maximum Stride length


        testActivity = new Activity(12345, "Running", "Run around the block", testDate, testDate, testTime, testTime);
        p1 = new ActivityDataPoint(testTime, 85, -43.530029, 172.582520, 88);
        p2 = new ActivityDataPoint(testTime, 120, -43.530030, 172.583521, 88);
        testActivity.addActivityData(p1);
        testActivity.addActivityData(p2);
        assertEquals(132, ActivityAnalysis.findStepCount(testActivity, 2), 0.5);           // Small distance of 0.08km covered


        testActivity = new Activity(12345, "Running", "Run around the block", testDate, testDate, testTime, testTime);
        p1 = new ActivityDataPoint(testTime, 85, -43.530029, 172.582520, 88);
        p2 = new ActivityDataPoint(testTime, 120, -44.0, 173.0, 88);
        testActivity.addActivityData(p1);
        testActivity.addActivityData(p2);
        assertEquals(101844, ActivityAnalysis.findStepCount(testActivity, 2), 0.5);           // Large distance of 62.1km covered
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

        assertEquals(0.0, ActivityAnalysis.findDistanceFromStart(testActivity, 1));  //No distance moved
        assertEquals(2.3, ActivityAnalysis.findDistanceFromStart(testActivity, 3), 0.05);  //Moved in a circle
        assertEquals(2.4, ActivityAnalysis.findDistanceFromStart(testActivity, 4), 0.05);  //Moved only in latitude
        assertEquals(3.4, ActivityAnalysis.findDistanceFromStart(testActivity, 5), 0.05);  //Moved only in longitude
        assertEquals(78.6, ActivityAnalysis.findDistanceFromStart(testActivity, 6), 0.05);  //Moved large distance
    }



    public void testFindCaloriesBurnedFromStart() {
        assertEquals(0.0, ActivityAnalysis.findCaloriesBurnedFromStart(0, "Walking", 65));      // No activity time
        assertEquals(0.0, ActivityAnalysis.findCaloriesBurnedFromStart(-50, "Walking", 65));       //Negative activity time

        assertEquals(17, ActivityAnalysis.findCaloriesBurnedFromStart(5, "Walking", 65), 0.5); // Small activity time
        assertEquals(1365, ActivityAnalysis.findCaloriesBurnedFromStart(400, "Walking", 65), 0.05); // Large activity time

        assertEquals(472, ActivityAnalysis.findCaloriesBurnedFromStart(60, "Walking", 150), 0.5); // Large weight User
        assertEquals(31, ActivityAnalysis.findCaloriesBurnedFromStart(60, "Walking", 10), 0.5); // Small weight User

        assertEquals(307, ActivityAnalysis.findCaloriesBurnedFromStart(60, "", 65), 0.5); // Unknown activity type
        assertEquals(307, ActivityAnalysis.findCaloriesBurnedFromStart(60, "Other", 65), 0.5); // Other activity type
        assertEquals(512, ActivityAnalysis.findCaloriesBurnedFromStart(60, "Running", 65), 0.5); // Running activity type
        assertEquals(409, ActivityAnalysis.findCaloriesBurnedFromStart(60, "Cycling", 65), 0.5); // Cycling activity type
    }



    public void testGetActivityType() {
        LocalDate testDate = LocalDate.of(2018, 10, 9);
        LocalTime testTime = LocalTime.of(5, 30);



        testActivity = new Activity(12345, "Running", "Run around the block", testDate, testDate, testTime, testTime);
        assertEquals("Running", ActivityAnalysis.getActivityType(testActivity));

        testActivity = new Activity(12345, "Running", "RUNNING IN THE CITY", testDate, testDate, testTime, testTime);
        assertEquals("Running", ActivityAnalysis.getActivityType(testActivity));

        testActivity = new Activity(12345, "Running", "jog", testDate, testDate, testTime, testTime);
        assertEquals("Running", ActivityAnalysis.getActivityType(testActivity));

        testActivity = new Activity(12345, "Running", "joGGinG in the park", testDate, testDate, testTime, testTime);
        assertEquals("Running", ActivityAnalysis.getActivityType(testActivity));



        testActivity = new Activity(12345, "Cycling", "small BIKE ride", testDate, testDate, testTime, testTime);
        assertEquals("Cycling", ActivityAnalysis.getActivityType(testActivity));

        testActivity = new Activity(12345, "Cycling", "biking with friends", testDate, testDate, testTime, testTime);
        assertEquals("Cycling", ActivityAnalysis.getActivityType(testActivity));

        testActivity = new Activity(12345, "Cycling", "out cyclInG", testDate, testDate, testTime, testTime);
        assertEquals("Cycling", ActivityAnalysis.getActivityType(testActivity));

        testActivity = new Activity(12345, "Cycling", "   cycle   ", testDate, testDate, testTime, testTime);
        assertEquals("Cycling", ActivityAnalysis.getActivityType(testActivity));



        testActivity = new Activity(12345, "Walking", "  dog walking", testDate, testDate, testTime, testTime);
        assertEquals("Walking", ActivityAnalysis.getActivityType(testActivity));

        testActivity = new Activity(12345, "Walking", "WALK WITH FRIENDS", testDate, testDate, testTime, testTime);
        assertEquals("Walking", ActivityAnalysis.getActivityType(testActivity));

        testActivity = new Activity(12345, "Walking", "hIkE in the woods", testDate, testDate, testTime, testTime);
        assertEquals("Walking", ActivityAnalysis.getActivityType(testActivity));

        testActivity = new Activity(12345, "Walking", "  hiking", testDate, testDate, testTime, testTime);
        assertEquals("Walking", ActivityAnalysis.getActivityType(testActivity));



        testActivity = new Activity(12345, "Other", "  ", testDate, testDate, testTime, testTime);
        assertEquals("Other", ActivityAnalysis.getActivityType(testActivity));

        testActivity = new Activity(12345, "Other", "w alk", testDate, testDate, testTime, testTime);
        assertEquals("Other", ActivityAnalysis.getActivityType(testActivity));

        testActivity = new Activity(12345, "Other", "walkingwithfriends", testDate, testDate, testTime, testTime);
        assertEquals("Other", ActivityAnalysis.getActivityType(testActivity));


    }
}