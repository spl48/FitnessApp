package seng202.team6.models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class implements Activity and sets the required information about an Activity
 * the User has done.
 * @author Angelica Dela Cruz
 * @version 1.2, Aug 2018.
 */
public class Activity
{
    /**
     * The ID of the Activity
     */
    private int activityid;

    /**
     * The type of the Activity
     */
    private String type;

    /**
     * The type of the Activity
     */
    private String description;

    /**
     * The date of when the Activity occurred
     */
    private LocalDate date;

    /**
     * The date of when the Activity ended
     */
    private LocalDate endDate;

    /**
     * The distance in km
     */
    private double distance;

    /**
     * The minimum heart rate of the User during the Activity in bpm
     */
    private int minHeartRate;

    /**
     * The maximum heart rate of the User during the Activity in bpm
     */
    private int maxHeartRate;

    /**
     * The start time of the Activity
     */
    private LocalTime startTime;

    /**
     * The end time of the Activity
     */
    private LocalTime endTime;

    /**
     * The total time of the Activity in minutes
     */
    private long totalTime;

    /**
     * The activity data of the Activity
     */
    private ArrayList<ActivityDataPoint> activityData = new ArrayList<ActivityDataPoint>();

    /**
     * The only types of Activities allowed
     */
    private static final ArrayList<String> activities = new ArrayList<String>(Arrays.asList("Running", "Walking", "Biking"));

    /**
     * The constructor for the Activity that takes the parameters type, date, start time, end time, distance, minimum
     * heart rate, maximum heart rate and the total time of the  Activity.
     * @param activityid A String parameter used to set Activity type.
     * @param type A String parameter used to set Activity type.
     * @param description A String parameter used to set the Activity description.
     * @param date A LocalDate parameter used to set the Activity date.
     * @param endDate A LocalDate parameter used to set the Activity end date.
     * @param startTime A LocalTime parameter used to set the start time of Activity.
     * @param endTime A LocalTime parameter used to set the end time of the Activity.
     //* @param distance A Double parameter used to set the distance of the Activity from the starting point to end location in km.
     //* @param minHeartRate A Double parameter used to set User's minimum heart rate in bpm.
     //* @param maxHeartRate A Double parameter used to set User's maximum heart rate in bpm.
     */
    public Activity(int activityid, String type, String description, LocalDate date, LocalDate endDate, LocalTime startTime, LocalTime endTime)
    {
        if (activities.contains(type)) {
            this.type = type;
        } else {
            this.type = "invalid";
        }
        this.description = description;
        this.activityid = activityid;
        this.date = date;
        this.endDate = endDate;
        if (startTime.isBefore(endTime)) {
            this.startTime = startTime;
            this.endTime = endTime;
            totalTime = Duration.between(startTime, endTime).toMinutes();
        }
/*
        if (distance > 0.0) {
            this.distance = distance;
        } else {
            this.distance = 0.0;
        }
*/
/*
        if (minHeartRate > 0) {
            this.minHeartRate = minHeartRate;
        } else {
            this.minHeartRate = 0;
        }

        if (maxHeartRate > 0) {
            this.maxHeartRate = maxHeartRate;
        } else {
            this.maxHeartRate = 0;
        }
*/
    }

    /**
     * A function that takes the String parameter type and sets the type of Activity to the given
     * String parameter if the type is within the valid types of Activities.
     * @param type A String that is used to set the type of Activity.
     */
    public void setType(String type)
    {
        if (activities.contains(type)) {
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
     * A function that takes a LocalDate parameter date and sets the date of the Activity
     * to the given parameter.
     * @param date A LocalDate parameter used to set the Activity date.
     */
    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    /**
     * A function that returns the date of the occurrence of the Activity.
     * @return Returns a LocalDate that represents when the Activity date is.
     */
    public LocalDate getDate()
    {
        return date;
    }

    /**
     * A function that takes the total distance travelled in the Activity in km and
     * sets the distance to the given Double parameter. Checks if the distance is greater
     * than 0 to be valid. Otherwise, distance is invalid and is set to 0.0.
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
     * A function that takes the minimum and maximum heart rate and sets the minimum and maximum heart
     * rate of the User based on the given Double parameter minHeartRate and maxHeartRate in bpm.
     * Checks if the min heart rate is less than max heart rate and both must be greater than 0 to be valid.
     * Otherwise, invalid and the heart rates are set to 0.
     * @param minHeartRate A Integer that is used to set the User's minimum heart rate.
     * @param maxHeartRate A Integer that is used to set the User's maximum heart rate.
     */
    public void setHeartRate(int minHeartRate, int maxHeartRate)
    {
        if ((minHeartRate < 0) || (maxHeartRate < 0)) {
        this.minHeartRate = 0;
        this.maxHeartRate = 0;
        }
        if (minHeartRate < maxHeartRate) {
            if ((minHeartRate > 0) && (maxHeartRate > 0)) {
                this.minHeartRate = minHeartRate;
                this.maxHeartRate = maxHeartRate;
            }
        } else {
            this.minHeartRate = 0;
            this.maxHeartRate = 0;
        }
    }

    /**
     * A function that returns the minimum heart rate of the User during the Activity in bpm.
     * @return Returns a Integer that represents the User's minimum heart rate in the
     * Activity.
     */
    public int getMinHeartRate()
    {
        return minHeartRate;
    }

    /**
     * A function that returns the maximum heart rate of the User during the Activity in bpm.
     * @return Returns a Integer that represents the User's maximum heart rate in the
     * Activity.
     */
    public int getMaxHeartRate()
    {
        return maxHeartRate;
    }

    /**
     * A function that takes the parameters the start time and end time and determines
     * the duration of the Activity from the given times. Checks if the start time is
     * before the end time to be valid input. Otherwise, invalid.
     * @param startTime A LocalTime parameter used to set the start time of Activity.
     * @param endTime A LocalTime parameter used to set the end time of Activity.
     */
    public void setTime(LocalTime startTime, LocalTime endTime)
    {
        if (startTime.isBefore(endTime)) {
            this.startTime = startTime;
            this.endTime = endTime;
            totalTime = Duration.between(startTime, endTime).toMinutes();
        }
    }

    /**
     * A function that returns the start time of the Activity.
     * @return Returns a LocalTime that represents the Activity start time.
     */
    public LocalTime getStartTime()
    {
        return startTime;
    }

    /**
     * A function that returns the end time of the Activity.
     * @return Returns a LocalTime that represents the Activity end time.
     */
    public LocalTime getEndTime()
    {
        return endTime;
    }

    /**
     * A function that returns the total duration of the Activity in minutes.
     * @return Returns a Long that represents the total Activity time.
     */
    public long getTotalTime()
    {
        return totalTime;
    }

    /**
     * A function that takes a parameter activity data point and adds activity data point
     * to an ArrayList of ActivityDataPoint.
     * @param activityData An ActivityDataPoint that is added into the ArrayList for the
     * Activity Data Point
     */
    public void addActivityData(ActivityDataPoint activityData)
    {
        this.activityData.add(activityData);
    }

    /**
     * A function that returns an ArrayList of type ActivityDataPoint of the Activity.
     * @return Returns an ArrayList that represents the Activity data points.
     */
    public ArrayList<ActivityDataPoint> getActivityData()
    {
        return activityData;
    }

    public int getActivityid(){
        return activityid;
    }

    public void updateMinHeartRate(){
        minHeartRate = 1000;
        for (ActivityDataPoint dataPoint : activityData){
            if(dataPoint.getHeartRate() < minHeartRate){
                minHeartRate = dataPoint.getHeartRate();
            }
        }
    }
    public void updateMaxHeartRate(){
        maxHeartRate = 1000;
        for (ActivityDataPoint dataPoint : activityData){
            if(dataPoint.getHeartRate() > maxHeartRate){
                maxHeartRate = dataPoint.getHeartRate();
            }
        }
    }
}
