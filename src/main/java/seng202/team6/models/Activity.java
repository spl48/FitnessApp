package seng202.team6.models;

import java.util.ArrayList;

/**
 * This class implements Activity and sets the required information about an Activity
 * the User has done.
 * @author Angelica Dela Cruz
 * @version 1.1, Aug 2018.
 */
public class Activity
{
    /**
     * The minimum length of the Activity type
     */
    private static final int MIN_TYPE_LENGTH = 3;

    /**
     * The length of the Activity date
     */
    private static final int DATE_LENGTH = 8;

    /**
     * The type of the Activity
     */
    private String type;

    /**
     * The date of when the Activity occurred
     */
    private String date;

    /**
     * The distance in km
     */
    private double distance = 0.0;

    /**
     * The minimum heart rate of the User during the Activity in bpm
     */
    private double minHeartRate;

    /**
     * The maximum heart rate of the User during the Activity in bpm
     */
    private double maxHeartRate;

    /**
     * The total time of the Activity in minutes
     */
    private double totalTime;

    /**
     * The activity data of the Activity
     */
    private ArrayList<ActivityDataPoint> activityData = new ArrayList<ActivityDataPoint>();

    /**
     * The constructor for the Activity that sets the type, date, distance, minimum heart rate, maximum heart
     * rate and the total time of the  Activity.
     * @param type A String parameter used to set Activity type.
     * @param date A String parameter used to set the Activity date.
     * @param distance A Double parameter used to set the distance of the Activity from the starting point to end location in km.
     * @param minHeartRate A Double parameter used to set User's minimum heart rate in bpm.
     * @param maxHeartRate A Double parameter used to set User's maximum heart rate in bpm.
     * @param totalTime A Double parameter used to set the total Activity time in minutes.
     */
    public Activity(String type, String date, double distance, double minHeartRate, double maxHeartRate, double totalTime)
    {
        this.totalTime = totalTime;

        if (type.length() >= MIN_TYPE_LENGTH) {
            this.type = type;
        } else {
            this.type = "invalid";
        }

        if (date.length() == DATE_LENGTH) {
            this.date = date;
        } else {
            this.date = "DD/MM/YY";
        }

        if (distance > 0.0) {
            this.distance = distance;
        } else {
            this.distance = 0.0;
        }

        if (minHeartRate > 0.0) {
            this.minHeartRate = minHeartRate;
        } else {
            this.minHeartRate = 0.0;
        }

        if (maxHeartRate > 0.0) {
            this.maxHeartRate = maxHeartRate;
        } else {
            this.maxHeartRate = 0.0;
        }

        if (totalTime > 0.0) {
            this.totalTime = totalTime;
        } else {
            this.totalTime = 0.0;
        }

    }

    /**
     * A function that sets the type of Activity to the given String parameter type.
     * Checks if the type is valid.
     * @param type A String that is used to set the type of Activity.
     */
    public void setType(String type)
    {
        if (type.length() >= MIN_TYPE_LENGTH) {
            this.type = type;
        } else {
            this.type = "invalid";
        }
    }

    /**
     * A function that returns a String of the type of Activity.
     * @return Returns a String that represents the Activity type.
     */
    public String getType()
    {
        return type;
    }

    /**
     * A function that sets the date of when the Activity occurred of the form DD/MM/YY.
     * Checks if the date is valid or not.
     * @param date A String parameter used to set the Activity date.
     */
    public void setDate(String date)
    {
        if (date.length() == DATE_LENGTH) {
            this.date = date;
        } else {
            this.date = "DD/MM/YY";
        }
    }

    /**
     * A function that returns the date of the occurrence of the Activity of the form DD/MM/YY.
     * @return Returns a String that represents when the Activity date is.
     */
    public String getDate()
    {
        return date;
    }

    /**
     * A function that sets the total distance travelled in the Activity in km. Checks if
     * the distance is greater than 0 to be valid. Otherwise, distance is invalid.
     * @param distance A Double parameter used to set the distance of the Activity.
     */
    public void setDistance(double distance)
    {
        if (distance > 0.0) {
            this.distance = distance;
        } else {
            this.distance = 0.0;
        }
    }

    /**
     * A function that returns the distance travelled in the Activity in km.
     * @return Returns a Double that represents the total distance travelled in the Activity.
     */
    public double getDistance()
    {
        return distance;
    }

    /**
     * A function that sets the minimum heart rate of the User based on the given Double
     * parameter minHeartRate in bpm. Checks if the given heart rate is valid.
     * @param minHeartRate A Double that is used to set the User's minimum heart rate.
     */
    public void setMinHeartRate(double minHeartRate)
    {
        if (minHeartRate > 0.0) {
            this.minHeartRate = minHeartRate;
        } else {
            this.minHeartRate = 0.0;
        }
    }

    /**
     * A function that returns the minimum heart rate of the User during the Activity in bpm.
     * @return Returns a Double that represents the User's minimum heart rate in the
     * Activity.
     */
    public double getMinHeartRate()
    {
        return minHeartRate;
    }

    /**
     * A function that sets the maximum heart rate of the User based on the given Double
     * parameter maxHeartRate in bpm. Checks if the given heart rate is valid.
     * @param maxHeartRate A Double that is used to set the User's maximum heart rate.
     */
    public void setMaxHeartRate(double maxHeartRate)
    {
        if (maxHeartRate > 0.0) {
            this.maxHeartRate = maxHeartRate;
        } else {
            this.maxHeartRate = 0.0;
        }
    }

    /**
     * A function that returns the maximum heart rate of the User during the Activity in bpm.
     * @return Returns a Double that represents the User's maximum heart rate in the
     * Activity.
     */
    public double getMaxHeartRate()
    {
        return maxHeartRate;
    }

    /**
     * A function that sets the total duration of the Activity based on the given Double
     * parameter totalTime in minutes. Checks if the given total time is valid.
     * @param totalTime A Double used to set the total Activity time.
     */
    public void setTotalTime(double totalTime)
    {
        if (totalTime > 0.0) {
            this.totalTime = totalTime;
        } else {
            this.totalTime = 0.0;
        }
    }

    /**
     * A function that returns the total duration of the Activity in minutes.
     * @return Returns a Double that represents the total Activity time.
     */
    public double getTotalTime()
    {
        return totalTime;
    }

    /**
     * A function that adds activity data point of an Activity.
     * @param activityData An ActivityDataPoint that is added into the ArrayList for the
     * Activity Data Point
     */
    public void addActivityData(ActivityDataPoint activityData)
    {
        this.activityData.add(activityData);
    }

    /**
     * A function that returns an ArrayList of the ActivityDataPoint of the Activity
     * @return Returns an ArrayList that represents the Activity data points.
     */
    public ArrayList<ActivityDataPoint> getActivityData()
    {
        return activityData;
    }
}
