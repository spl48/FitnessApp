package seng202.team6.models;

import seng202.team6.analysis.ActivityAnalysis;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class implements Activity and sets the required information about an Activity
 * the User has done.
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
     * The description of the Activity
     */
    private String description;

    /**
     * The Notes for the Activity made by the User
     */
    private String notes;

    /**
     * The date of when the Activity started
     */
    private LocalDate startDate;

    /**
     * The date of when the Activity ended
     */
    private LocalDate endDate;

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
     * The distance of the Activity
     */
    private double distance;

    /**
     * The minimum heart rate of the User
     */
    private int minHeartRate = 0;

    /**
     * The maximum heart rate of the User
     */
    private int maxHeartRate = 0;

    /**
     * The activity data of the Activity
     */
    private ArrayList<ActivityDataPoint> activityData = new ArrayList<ActivityDataPoint>();

    /**
     * The only types of Activities allowed
     */
    private static final ArrayList<String> activities = new ArrayList<String>(Arrays.asList("Running", "Walking", "Biking", "Other"));

    /**
     * The constructor for the Activity that takes the parameters type, date, start time, end time, distance, minimum
     * heart rate, maximum heart rate and the total time of the  Activity.
     * @param activityid A String parameter used to set Activity type.
     * @param type A String parameter used to set Activity type.
     * @param description A String parameter used to set the Activity description.
     * @param startDate A LocalDate parameter used to set the Activity date.
     * @param endDate A LocalDate parameter used to set the Activity end date.
     * @param startTime A LocalTime parameter used to set the start time of Activity.
     * @param endTime A LocalTime parameter used to set the end time of the Activity.
     */
    public Activity(int activityid, String type, String description, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, String notes)
    {
        if (activities.contains(type)) {
            this.type = type;
        } else {
            this.type = "invalid";
        }
        this.description = description;
        this.activityid = activityid;
        this.startDate = startDate;
        this.endDate = endDate;
        if (startTime.isBefore(endTime)) {
            this.startTime = startTime;
            this.endTime = endTime;
            totalTime = Duration.between(startTime, endTime).toMinutes();
        }
        this.notes = notes;
    }

    /**
     * The constructor for the Activity that takes the parameters type, date, start time, end time, distance, minimum
     * heart rate, maximum heart rate and the total time of the  Activity.
     * @param activityid A String parameter used to set Activity type.
     * @param type A String parameter used to set Activity type.
     * @param description A String parameter used to set the Activity description.
     * @param startDate A LocalDate parameter used to set the Activity date.
     * @param endDate A LocalDate parameter used to set the Activity end date.
     * @param startTime A LocalTime parameter used to set the start time of Activity.
     * @param endTime A LocalTime parameter used to set the end time of the Activity.
     */
    public Activity(int activityid, String type, String description, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime)
    {
        if (activities.contains(type)) {
            this.type = type;
        } else {
            this.type = "invalid";
        }
        this.description = description;
        this.activityid = activityid;
        this.startDate = startDate;
        this.endDate = endDate;
        if (startTime.isBefore(endTime)) {
            this.startTime = startTime;
            this.endTime = endTime;
            totalTime = Duration.between(startTime, endTime).toMinutes();
        }
    }

    /**
     * A function that sets the Activity ID to the given Integer parameter
     * activityid.
     * @param activityid An Integer parameter used to set as the activity ID.
     */
    public void setActivityid(int activityid)
    {
        this.activityid = activityid;
    }

    /**
     * Returns the Activity ID of the particular Activity.
     * @return Returns an Integer representation of the Activity ID for the User.
     */
    public int getActivityid()
    {
        return activityid;
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
     * A function that updates the Activity type to the given type.
     */
    public void updateType() {
        type = ActivityAnalysis.getActivityType(this);
    }


    /**
     * A function that sets the Activity Description to the given String parameter
     * description.
     * @param description A String parameter used to set the description of the Activity.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * A function that returns the Activity description.
     * @return Returns the description of the Activity in String representation.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * A function that sets the Notes of the Activity to the given String
     * parameter Activity.
     * @param notesToSet A String parameter used to set Notes of a particular Activity.
     */
    public void setNotes(String notesToSet) {
        notes = notesToSet;
    }

    /**
     * A function that returns the Notes in the Activity.
     * @return Returns a String representation of Activity Notes made by the User.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * A function that takes a LocalDate parameter date and sets the start date of the Activity
     * to the given parameter.
     * @param startDate A LocalDate parameter used to set the Activity date.
     */
    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    /**
     * A function that takes a LocalDate parameter date and sets the end date of the Activity
     * to the given parameter.
     * @param endDate A LocalDate parameter used to set the Activity date.
     */
    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    /**
     * A function that returns the start date of the occurrence of the Activity.
     * @return Returns a LocalDate that represents when the Activity date is.
     */
    public LocalDate getStartDate()
    {
        return startDate;
    }

    /**
     * A function that returns the start date of the occurrence of the Activity.
     * @return Returns a LocalDate that represents when the Activity date is.
     */
    public LocalDate getEndDate()
    {
        return endDate;
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
     * A function that sets the Activity distance to the given Double parameter
     * in km.
     * @param distance A Double parameter used to as the distance travelled during
     * the Activity
     */
    public void setDistance(double distance)
    {
        if (distance > 0) {
            this.distance = distance;
        } else {
            this.distance = 0.0;
        }
    }

    /**
     * A function that returns the distance travelled by the User during the particular
     * Activity in km.
     * @return A Double parameter that represents distance travelled by the User.
     */
    public double getDistance()
    {
        return distance;
    }

    /**
     * A function that updates the minimum heart rate of the User.
     */
    public void updateMinHeartRate(){
        minHeartRate = 1000;
        for (ActivityDataPoint dataPoint : activityData){
            if(dataPoint.getHeartRate() < minHeartRate){
                minHeartRate = dataPoint.getHeartRate();
            }
        }
    }

    /**
     * A function that updates the maximum heart rate of the User.
     */
    public void updateMaxHeartRate(){
        for (ActivityDataPoint dataPoint : activityData){
            if(dataPoint.getHeartRate() > maxHeartRate){
                maxHeartRate = dataPoint.getHeartRate();
            }
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
}

