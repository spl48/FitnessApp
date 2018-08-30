package seng202.team6.models;

/**
 * This class implements ActivityDataPoint which are the Data Points during a User Activity.
 * Sets the required information for the given Activity Data Point.
 * @author Angelica Dela Cruz
 * @version 1.1, Aug 2018.
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
     * The Activity time in minutes
     */
    private double time;

    /**
     * The heart rate of the User at the Activity Data Point in bpm
     */
    private double heartRate;

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
     * The constructor for the ActivityDataPoint that sets the time, heart Rate of the User,
     * latitude, longitude and elevation of the location of the Activity Data Point.
     * @param time A Double parameter used as the time for the Activity Data Point.
     * @param heartRate A Double parameter used as the heart rate of the User at the Data Point.
     * @param latitude A Double parameter used as the latitude for the Activity Data Point.
     * @param longitude A Double parameter used as the longitude for the Activity Data Point.
     * @param elevation A Double parameter used as the elevation for the Activity Data Point.
     */
    public ActivityDataPoint(double time, double heartRate, double latitude, double longitude, double elevation)
    {
        if (time > 0) {
            this.time = time;
        } else {
            this.time = 0.0;
        }

        if (heartRate > 0) {
            this.heartRate = heartRate;
        } else {
            this.heartRate = 0.0;
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
     * A function that sets the time of the Activity Data Point to the given Double parameter
     * time in minutes.
     * @param time A Double parameter used to set as the time for the Activity Data Point.
     */
    public void setTime(double time)
    {
        if (time > 0) {
            this.time = time;
        } else {
            this.time = 0.0;
        }
    }

    /**
     * A function that returns the time for the Activity Data Point in minutes.
     * @return Returns a Double that represents the time at the Activity Data Point in
     * minutes.
     */
    public double getTime()
    {
        return time;
    }

    /**
     * A function that sets the heart rate of the User at the Activity Data Point
     * to the given Double parameter heart rate in bpm.
     * @param heartRate A Double parameter used to set the heart rate of the User in bpm.
     */
    public void setHeartRate(double heartRate)
    {
        if (heartRate > 0) {
            this.heartRate = heartRate;
        } else {
            this.heartRate = 0.0;
        }
    }

    /**
     * A function that returns the heart rate of the User at the Activity Data Point in bpm.
     * @return Returns a Double that represents the heart rate of the User.
     */
    public double getHeartRate()
    {
        return heartRate;
    }

    /**
     * A function that sets the latitude for the Activity Data Point in degrees to the
     * given Double parameter latitude.
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
     * A function that sets the longitude for the Activity Data Point in degrees to the given
     * Double parameter longitude.
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
     * A function that sets the elevation of the Activity Data Point to the given Double
     * parameter elevation.
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
