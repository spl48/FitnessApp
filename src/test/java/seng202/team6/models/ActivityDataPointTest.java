package seng202.team6.models;

import junit.framework.TestCase;

import java.time.LocalTime;

public class ActivityDataPointTest extends TestCase {

    LocalTime time = LocalTime.of(11, 30);
    private ActivityDataPoint point = new ActivityDataPoint(time, 78, 40.9, 174.0, 50.0);

    public void testSetTime() {
        LocalTime time1 = LocalTime.of(15, 58);
        point.setTime(time1);
        assertEquals(time1, point.getTime());
    }

    public void testGetTime() {
        assertEquals(time, point.getTime());
    }

    public void testSetHeartRate() {
        // Invalid input
        point.setHeartRate(-100);
        assertEquals(0, point.getHeartRate());

        // Valid input
        point.setHeartRate(100);
        assertEquals(100, point.getHeartRate());
    }

    public void testGetHeartRate() {
        assertEquals(78, point.getHeartRate());
    }

    public void testSetLatitude() {
        // Boundary invalid input
        point.setLatitude(-91.0);
        assertEquals(0.0, point.getLatitude(), 0.1);

        point.setLatitude(91.0);
        assertEquals(0.0, point.getLatitude(), 0.1);

        // Boundary valid input
        point.setLatitude(-90.0);
        assertEquals(-90.0, point.getLatitude(), 0.1);

        point.setLatitude(90.0);
        assertEquals(90.0, point.getLatitude(), 0.1);

        // Boundary valid input
        point.setLatitude(-89.0);
        assertEquals(-89.0, point.getLatitude(), 0.1);

        point.setLatitude(89.0);
        assertEquals(89.0, point.getLatitude(), 0.1);
    }

    public void testGetLatitude() {
        assertEquals(40.9, point.getLatitude(), 0.1);
    }

    public void testSetLongitude() {
        // Boundary invalid input
        point.setLongitude(-181.0);
        assertEquals(0.0, point.getLongitude(), 0.1);

        point.setLongitude(181.0);
        assertEquals(0.0, point.getLongitude(), 0.1);

        // Boundary valid input
        point.setLongitude(-180.0);
        assertEquals(-180.0, point.getLongitude(), 0.1);

        point.setLongitude(180.0);
        assertEquals(180.0, point.getLongitude(), 0.1);

        // Boundary valid input
        point.setLongitude(-179.0);
        assertEquals(-179.0, point.getLongitude(), 0.1);

        point.setLongitude(179.0);
        assertEquals(179.0, point.getLongitude(), 0.1);
    }

    public void testGetLongitude() {
        assertEquals(174.0, point.getLongitude(), 0.1);
    }

    public void testSetElevation() {
        point.setElevation(-1.0);
        assertEquals(0.0, point.getElevation(), 0.1);

        // Boundary valid input
        point.setElevation(1.0);
        assertEquals(1.0, point.getElevation(), 0.1);
    }

    public void testGetElevation() {
        assertEquals(50.0, point.getElevation(), 0.1);
    }
}