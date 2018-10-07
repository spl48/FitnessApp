package seng202.team6.analysis;

import junit.framework.TestCase;

import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import seng202.team6.utilities.HealthConcernChecker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class HealthConcernCheckerTest extends TestCase {

    ArrayList<Activity> testActivities = new ArrayList<Activity>();

    public void testCheckTachycardia() {

        LocalDate testDate2 = LocalDate.of(2018, 10, 10);
        LocalTime time1 = LocalTime.of(4, 15);


        assertEquals(false, HealthConcernChecker.checkTachycardia(testActivities, 15));  // No activities


        Activity testActivity2 = new Activity(124, "Walking", "Walk up the hill", testDate2, testDate2, time1, time1);
        ActivityDataPoint point = new ActivityDataPoint(time1, 132, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(false, HealthConcernChecker.checkTachycardia(testActivities, 7)); // 7 year old with maximum heart rate without Tachycardia

        point = new ActivityDataPoint(time1, 133, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(true, HealthConcernChecker.checkTachycardia(testActivities, 7)); // 7 year old with minimum heart rate for Tachycardia



        testActivity2 = new Activity(124, "Walking", "Walk up the hill", testDate2, testDate2, time1, time1);
        point = new ActivityDataPoint(time1, 129, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(false, HealthConcernChecker.checkTachycardia(testActivities, 11)); // 11 year old with maximum heart rate without Tachycardia

        point = new ActivityDataPoint(time1, 130, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(true, HealthConcernChecker.checkTachycardia(testActivities, 11)); // 11 year old with minimum heart rate for Tachycardia



        testActivity2 = new Activity(124, "Walking", "Walk up the hill", testDate2, testDate2, time1, time1);
        point = new ActivityDataPoint(time1, 118, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(false, HealthConcernChecker.checkTachycardia(testActivities, 14)); // 14 year old with maximum heart rate without Tachycardia

        point = new ActivityDataPoint(time1, 119, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(true, HealthConcernChecker.checkTachycardia(testActivities, 14)); // 14 year old with minimum heart rate for Tachycardia



        testActivity2 = new Activity(124, "Walking", "Walk up the hill", testDate2, testDate2, time1, time1);
        point = new ActivityDataPoint(time1, 99, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(false, HealthConcernChecker.checkTachycardia(testActivities, 15)); // 15 year old with maximum heart rate without Tachycardia

        point = new ActivityDataPoint(time1, 100, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(true, HealthConcernChecker.checkTachycardia(testActivities, 15)); // 15 year old with minimum heart rate for Tachycardia



        testActivity2 = new Activity(124, "Running", "Run up the hill", testDate2, testDate2, time1, time1);
        point = new ActivityDataPoint(time1, 200, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(false, HealthConcernChecker.checkTachycardia(testActivities, 15)); // Running Activity


        Activity testActivity3 = new Activity(124, "Cycling", "Bike up the hill", testDate2, testDate2, time1, time1);
        point = new ActivityDataPoint(time1, 200, -43.522371, 172.589474, 88);
        testActivity3.addActivityData(point);
        assertEquals(false, HealthConcernChecker.checkTachycardia(testActivities, 15)); // Cycling activity
    }




    public void testCheckBradycardia() {

        LocalDate testDate2 = LocalDate.of(2018, 10, 10);
        LocalTime time1 = LocalTime.of(4, 15);


        assertEquals(false, HealthConcernChecker.checkBradycardia(testActivities, 15));  // No activities


        Activity testActivity2 = new Activity(124, "Walking", "Walk up the hill", testDate2, testDate2, time1, time1);
        ActivityDataPoint point = new ActivityDataPoint(time1, 70, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(false, HealthConcernChecker.checkBradycardia(testActivities, 9)); // 9 year old with minimum heart rate without Bradycardia

        point = new ActivityDataPoint(time1, 69, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(true, HealthConcernChecker.checkBradycardia(testActivities, 9)); // 9 year old with maximum heart rate for Bradycardia



        testActivity2 = new Activity(124, "Walking", "Walk up the hill", testDate2, testDate2, time1, time1);
        point = new ActivityDataPoint(time1, 60, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(false, HealthConcernChecker.checkBradycardia(testActivities, 17)); // 17 year old with minimum heart rate without Bradycardia

        point = new ActivityDataPoint(time1, 59, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(true, HealthConcernChecker.checkBradycardia(testActivities, 17)); // 17 year old with maximum heart rate for Bradycardia



        testActivity2 = new Activity(124, "Walking", "Walk up the hill", testDate2, testDate2, time1, time1);
        point = new ActivityDataPoint(time1, 50, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(false, HealthConcernChecker.checkBradycardia(testActivities, 18)); // 18 year old with maximum heart rate without tachycardia

        point = new ActivityDataPoint(time1, 49, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(true, HealthConcernChecker.checkBradycardia(testActivities, 18)); // 18 year old with minimum heart rate for tachycardia



        testActivity2 = new Activity(124, "Running", "Run up the hill", testDate2, testDate2, time1, time1);
        point = new ActivityDataPoint(time1, 10, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(false, HealthConcernChecker.checkBradycardia(testActivities, 15)); // Running Activity


        Activity testActivity3 = new Activity(124, "Cycling", "Bike up the hill", testDate2, testDate2, time1, time1);
        point = new ActivityDataPoint(time1, 10, -43.522371, 172.589474, 88);
        testActivity3.addActivityData(point);
        assertEquals(false, HealthConcernChecker.checkBradycardia(testActivities, 15)); // Cycling activity
    }

    public void testCheckCardiovascularMortality() {

        LocalDate testDate2 = LocalDate.of(2018, 10, 10);
        LocalTime time1 = LocalTime.of(4, 15);


        assertEquals(false, HealthConcernChecker.checkCardiovascularMortality(testActivities, 50));  // No activities


        Activity testActivity2 = new Activity(124, "Walking", "Walk up the hill", testDate2, testDate2, time1, time1);
        ActivityDataPoint point = new ActivityDataPoint(time1, 200, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(false, HealthConcernChecker.checkCardiovascularMortality(testActivities, 17)); // Child


        testActivity2 = new Activity(124, "Walking", "Walk up the hill", testDate2, testDate2, time1, time1);
        point = new ActivityDataPoint(time1, 83, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(false, HealthConcernChecker.checkCardiovascularMortality(testActivities, 18)); // Adult with the maximum heartrate without cardiovasular concerns

        point = new ActivityDataPoint(time1, 84, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(true, HealthConcernChecker.checkCardiovascularMortality(testActivities, 18)); // Adult with the minimum heartrate with cardiovasular concerns



        testActivity2 = new Activity(124, "Running", "Run up the hill", testDate2, testDate2, time1, time1);
        point = new ActivityDataPoint(time1, 200, -43.522371, 172.589474, 88);
        testActivity2.addActivityData(point);
        testActivities = new ArrayList<Activity>(Arrays.asList(testActivity2));
        assertEquals(false, HealthConcernChecker.checkCardiovascularMortality(testActivities, 50)); // Running Activity


        Activity testActivity3 = new Activity(124, "Cycling", "Bike up the hill", testDate2, testDate2, time1, time1);
        point = new ActivityDataPoint(time1, 200, -43.522371, 172.589474, 88);
        testActivity3.addActivityData(point);
        assertEquals(false, HealthConcernChecker.checkCardiovascularMortality(testActivities, 50)); // Cycling activity
    }
}