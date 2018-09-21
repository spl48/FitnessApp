package seng202.team6.analysis;

import junit.framework.TestCase;
import seng202.team6.analysis.ProfileAnalysis;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import seng202.team6.models.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ProfileAnalysisTest extends TestCase{
    User testUser;
    ProfileAnalysis testProfileAnalysis;

    protected void setUp() {
        LocalDate date = LocalDate.of(1998, 5, 18);
        testUser = new User("Joe", "Bloggs",date,"Male", 170.0, 64, 2.0, "JBloggs", 12345);
        testProfileAnalysis = new ProfileAnalysis();
    }

    public void testCalculateBMI() {
        assertEquals(testProfileAnalysis.calculateBMI(testUser), 22.1, 0.05);   // Average BMI

        testUser.setWeight(2);   //Minimum weight
        testUser.setHeight(55);  //Minimum height
        assertEquals(testProfileAnalysis.calculateBMI(testUser), 6.6, 0.05);

        testUser.setWeight(600);  //Maximum weight
        testUser.setHeight(280);   //Maximum Height
        assertEquals(testProfileAnalysis.calculateBMI(testUser), 76.5, 0.05);

        // Smallest possible BMI (within the app)
        testUser.setWeight(2);
        testUser.setHeight(280);
        assertEquals(testProfileAnalysis.calculateBMI(testUser), 0.3, 0.05);

        // Largest possible BMI (within the app)
        testUser.setWeight(600);
        testUser.setHeight(55);
        assertEquals(testProfileAnalysis.calculateBMI(testUser), 1983.5, 0.05);

    }

    public void testAnalyseBMI() {

        double BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals("Healthy Weight", testProfileAnalysis.analyseBMI(BMI)); // Middle healthy Weight user - BMI of 22.1

        testUser.setWeight(53.5);                                 // Minimum Healthy weight user - BMI of 18.5
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals( "Healthy Weight", testProfileAnalysis.analyseBMI(BMI));

        testUser.setWeight(71.8);                                 // Maximum Healthy weight user - BMI of 24.8
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals( "Healthy Weight", testProfileAnalysis.analyseBMI(BMI));



        testUser.setWeight(30);                                 // Very Under weight user - BMI of 10.4
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals( "UnderWeight",testProfileAnalysis.analyseBMI(BMI) );

        testUser.setWeight(40);                              // Middle Under weight user - BMI of 13.8
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals("UnderWeight", testProfileAnalysis.analyseBMI(BMI));

        testUser.setWeight(53.2);                              // Maximum Under weight user - BMI of 18.4
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals( "UnderWeight", testProfileAnalysis.analyseBMI(BMI));



        testUser.setWeight(72);                                 // Minimum Over Weight user - BMI of 24.9
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals("OverWeight", testProfileAnalysis.analyseBMI(BMI));

        testUser.setWeight(79.5);                                 // Middle Over Weight user - BMI of 27.5
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals( "OverWeight", testProfileAnalysis.analyseBMI(BMI));

        testUser.setWeight(86);                                 // Maximum Over Weight user - BMI of 29.8
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals( "OverWeight", testProfileAnalysis.analyseBMI(BMI));




        testUser.setWeight(86.5);                                 // Minimum Obese user - BMI of 29.9
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals("Obese",testProfileAnalysis.analyseBMI(BMI));

        testUser.setWeight(94);                                 // Middle Obese user - BMI of 32.2
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals("Obese", testProfileAnalysis.analyseBMI(BMI));

        testUser.setWeight(94);                                 // Very Obese user - BMI of 34.9
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals("Obese", testProfileAnalysis.analyseBMI(BMI));
    }


    public void testFindTotalStepCount() {
        ArrayList<Activity> activities = new ArrayList<Activity>();
        assertEquals(0.0, testProfileAnalysis.findTotalStepCount(activities, 1.5));     //No activities

        LocalDate testDate = LocalDate.of(2018, 10, 9);
        LocalTime testTime = LocalTime.of(5, 30);

        Activity testActivity = new Activity(12345, "Cycling", "Cycle around the block", testDate, testDate, testTime, testTime);
        ActivityDataPoint p1 = new ActivityDataPoint(testTime, 85, -43.530029, 172.582520, 88);
        ActivityDataPoint p2 = new ActivityDataPoint(testTime, 120, -43.536039, 172.5825540, 88);
        testActivity.addActivityData(p1);
        testActivity.addActivityData(p2);
        activities.add(testActivity);
        assertEquals(0.0, testProfileAnalysis.findTotalStepCount(activities, 1.5));        // One activity - Cycling

        testActivity = new Activity(12345, "Running", "Run around the block", testDate, testDate, testTime, testTime);
        testActivity.addActivityData(p1);
        testActivity.addActivityData(p2);
        activities.add(testActivity);
        assertEquals(1462, testProfileAnalysis.findTotalStepCount(activities, 1.5), 0.5);         // 1 valid activity with step count of 11462


        testActivity = new Activity(12345, "Running", "Run around the block", testDate, testDate, testTime, testTime);
        p1 = new ActivityDataPoint(testTime, 85, -43.540029, 172.582520, 88);
        p2 = new ActivityDataPoint(testTime, 120, -43.549039, 172.3825540, 88);
        testActivity.addActivityData(p1);
        testActivity.addActivityData(p2);
        activities.add(testActivity);
        assertEquals(36779, testProfileAnalysis.findTotalStepCount(activities, 1.5), 0.5);          // 2 valid activities step counts of 11462 and 35318


        testActivity = new Activity(12345, "Other", "Cycle around the block", testDate, testDate, testTime, testTime);
        assertEquals(36779, testProfileAnalysis.findTotalStepCount(activities, 1.5), 0.5);         // Activity of type other

    }
}
