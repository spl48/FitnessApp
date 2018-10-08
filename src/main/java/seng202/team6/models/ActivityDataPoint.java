package seng202.team6.models;

import java.time.LocalTime;

/**
 * This class implements ActivityDataPoint which are the Data Points during a User Activity.
 * Sets the required information for the given Activity Data Point.
 * @version 1.2, Aug 2018.
 */
public class ActivityDataPoint {

    /**
     * The maximum value for longitude in degrees
     */
    private static final double MAX_LONGITUDE = 180.0;

    /**
     * The minimum value for the longitude in degrees
     */
    private static final double MIN_LONGITUDE = -180.0;

    /**
     * The maximum value for latitude in degrees
     */
    private static final double MAX_LATITUDE = 90.0;

    /**
     * The minimum value for the latitude in degrees
     */
    private static final double MIN_LATITUDE = -90.0;

    /**
     * The time at the Activity Data Point
     */
    private LocalTime time;

    /**
     * The heart rate of the User at the Activity Data Point in bpm
     */
    private int heartRate;

    /**
     * The latitude of the Activity Data Point in degrees
     */
    private double latitude;

    /**
     * The longitude of the Activity Data Point in degrees
     */
    private double longitude;

    /**
     * The elevation at the Activity Data Point
     */
    private double elevation;

    /**
     * The constructor for the ActivityDataPoint that takes the parameters time, heart rate of the User,
     * latitude, longitude and elevation of the location of the Activity Data Point.
     * @param time A LocalTime parameter used as the time for the Activity Data Point.
     * @param heartRate An Double parameter used as the heart rate of the User at the Data Point.
     * @param latitude A Double parameter used as the latitude for the Activity Data Point.
     * @param longitude A Double parameter used as the longitude for the Activity Data Point.
     * @param elevation A Double parameter used as the elevation for the Activity Data Point.
     */
    public ActivityDataPoint(LocalTime time, int heartRate, double latitude, double longitude, double elevation)
    {
        this.time = time;

        if (heartRate > 0) {
            this.heartRate = heartRate;
        } else {
            this.heartRate = 0;
        }

        if ((latitude <= MAX_LATITUDE) && (latitude >= MIN_LATITUDE)){
            this.latitude = latitude;
        } else {
            this.latitude = 0.0;
        }

        if ((longitude <= MAX_LONGITUDE) && (longitude >= MIN_LONGITUDE)){
            this.longitude = longitude;
        } else {
            this.longitude = 0.0;
        }

        if (elevation > 0) {
            this.elevation = elevation;
        } else {
            this.elevation = 0.0;
        }
    }

    /**
     * A function that takes the LocalTime parameter time and sets the time of the Activity Data
     * Point to the given LocalTime parameter.
     * @param time A LocalTime parameter used to set as the time for the Activity Data Point.
     */
    public void setTime(LocalTime time)
    {
        this.time = time;
    }

    /**
     * A function that returns the time for the Activity Data Point.
     * @return Returns a LocalTime that represents the time at the Activity Data Point in
     * minutes.
     */
    public LocalTime getTime()
    {
        return time;
    }

    /**
     * A function that takes the parameter heart rate and sets the heart rate of the User at
     * the Activity Data Point to the given Integer parameter measured in bpm.
     * @param heartRate An Integer parameter used to set the heart rate of the User in bpm.
     */
    public void setHeartRate(int heartRate)
    {
        if (heartRate > 0) {
            this.heartRate = heartRate;
        } else {
            this.heartRate = 0;
        }
    }

    /**
     * A function that returns the heart rate of the User at the Activity Data Point in bpm.
     * @return Returns a Integer that represents the heart rate of the User.
     */
    public int getHeartRate()
    {
        return heartRate;
    }

    /**
     * A function that takes the parameter latitude and sets the latitude for the Activity
     * Data Point in degrees to the given Double parameter latitude.
     * @param latitude A Double parameter used as the Activity Data Point latitude.
     */
    public void setLatitude(double latitude)
    {
        if (latitude >= MIN_LATITUDE && latitude <= MAX_LATITUDE) {
            this.latitude = latitude;
        } else {
            this.latitude = 0.0;
        }
    }

    /**
     * A function that returns the latitude of the Activity Data Point in degrees.
     * @return Returns a Double that represents the latitude of the Data Point.
     */
    public double getLatitude()
    {
        return latitude;
    }

    /**
     * A function that takes the parameter the longitude and sets the longitude for the Activity
     * Data Point in degrees to the given Double parameter.
     * @param longitude A Double parameter used as the Activity Data Point longitude.
     */
    public void setLongitude(double longitude)
    {
        if (longitude >= MIN_LONGITUDE && longitude <= MAX_LONGITUDE) {
            this.longitude = longitude;
        } else {
            this.longitude = 0.0;
        }
    }

    /**
     * A function that returns the longitude of the Activity Data Point in degrees.
     * @return Returns a Double that represents the longitude  of the Data Point.
     */
    public double getLongitude()
    {
        return longitude;
    }

    /**
     * A function that takes the parameter elevation and sets the elevation of the Activity
     * Data Point to the given Double parameter.
     * @param elevation A Double parameter used as the Activity Data Point elevation.
     */
    public void setElevation(double elevation)
    {
        if (elevation > 0) {
            this.elevation = elevation;
        } else {
            this.elevation = 0.0;
        }
    }

    /**
     * A function that returns the elevation of the Activity Data Point.
     * @return Returns a Double that represents the elevation of the Data Point.
     */
    public double getElevation()
    {
        return elevation;
    }

}
