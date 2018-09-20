package seng202.team6;

import junit.framework.TestCase;
import seng202.team6.analysis.ProfileAnalysis;
import seng202.team6.models.User;

import java.time.LocalDate;

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
        testUser.setWeight(2);  //Maximum weight
        testUser.setHeight(280);   //Maximum Height
        assertEquals(testProfileAnalysis.calculateBMI(testUser), 0.3, 0.05);

        // Largest possible BMI (within the app)
        testUser.setWeight(600);  //Maximum weight
        testUser.setHeight(55);   //Maximum Height
        assertEquals(testProfileAnalysis.calculateBMI(testUser), 1983.5, 0.05);

    }

    public void testAnalyseBMI() {

        double BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals(testProfileAnalysis.analyseBMI(BMI), "Healthy Weight"); // Middle healthy Weight user - BMI of 22.1

        testUser.setWeight(53.5);                                 // Minimum Healthy weight user - BMI of 18.5
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals(testProfileAnalysis.analyseBMI(BMI), "Healthy Weight");

        testUser.setWeight(71.8);                                 // Maximum Healthy weight user - BMI of 24.8
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals(testProfileAnalysis.analyseBMI(BMI), "Healthy Weight");



        testUser.setWeight(30);                                 // Very Under weight user - BMI of 10.4
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals(testProfileAnalysis.analyseBMI(BMI), "UnderWeight");

        testUser.setWeight(40);                              // Middle Under weight user - BMI of 13.8
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals(testProfileAnalysis.analyseBMI(BMI), "UnderWeight");

        testUser.setWeight(53.2);                              // Maximum Under weight user - BMI of 18.4
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals(testProfileAnalysis.analyseBMI(BMI), "UnderWeight");



        testUser.setWeight(72);                                 // Minimum Over Weight user - BMI of 24.9
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals(testProfileAnalysis.analyseBMI(BMI), "OverWeight");

        testUser.setWeight(79.5);                                 // Middle Over Weight user - BMI of 27.5
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals(testProfileAnalysis.analyseBMI(BMI), "OverWeight");

        testUser.setWeight(86);                                 // Maximum Over Weight user - BMI of 29.8
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals(testProfileAnalysis.analyseBMI(BMI), "OverWeight");




        testUser.setWeight(86.5);                                 // Minimum Obese user - BMI of 29.9
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals(testProfileAnalysis.analyseBMI(BMI), "Obese");

        testUser.setWeight(94);                                 // Middle Obese user - BMI of 32.2
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals(testProfileAnalysis.analyseBMI(BMI), "Obese");

        testUser.setWeight(94);                                 // Very Obese user - BMI of 34.9
        BMI = testProfileAnalysis.calculateBMI(testUser);
        assertEquals(testProfileAnalysis.analyseBMI(BMI), "Obese");
    }
}
