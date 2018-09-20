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
        activity1.setStartDate(date2);
        assertEquals(date2, activity1.getStartDate());
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
}