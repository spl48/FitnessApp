package seng202.team6.models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

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
     * Key words that indicate an activity is of type "Running"
     */
    private static ArrayList<String> runningWords = new ArrayList<String>(Arrays.asList("run", "running", "jog", "jogging"));

    /**
     * Key words that indicate an activity is of type "Walking"
     */
    private static ArrayList<String> walkingWords = new ArrayList<String>(Arrays.asList("walk", "walking", "hike", "hiking"));

    /**
     * Key words that indicate an activity is of type "Cycling"
     */
    private static ArrayList<String> cyclingWords = new ArrayList<String>(Arrays.asList("bike", "biking", "cycle", "cycling"));

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
        type = getActivityType(description);
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
     * A function that takes a parameter activity data point and adds activity data point
     * to an ArrayList of ActivityDataPoint.
     * @param activityData An ActivityDataPoint that is added into the ArrayList for the
     * Activity Data Point
     */
    public void addAllActivityData(ArrayList<ActivityDataPoint> activityData)
    {
        this.activityData = activityData;
    }

    /**
     * A function that returns an ArrayList of type ActivityDataPoint of the Activity.
     * @return Returns an ArrayList that represents the Activity data points.
     */
    public ArrayList<ActivityDataPoint> getActivityData()
    {
        return activityData;
    }


    /**
     * Finds the total steps taken in a given activity, if
     * that activity is of type "Running" or "Walking", otherwise
     * returns a step count of 0
     * @param strideLength the user of the activities stride length in feet
     * @return a double representing the number of steps taken
     */
    public double findStepCount(double strideLength) {
        if (type.equalsIgnoreCase("walking") || type.equalsIgnoreCase("running") || type.equalsIgnoreCase("other")) {
            int finalIndex = activityData.size();
            if (finalIndex != 0) {
                distance = findDistanceFromStart(finalIndex - 1);
            }
            return (distance / (strideLength * 0.0003048));
        }
        else{
            return 0;
        }
    }



    /**
     * Converts a given angle in degrees, to radians
     * @param degrees the angle in degrees
     * @return a double representing the angle in radians
     */
    private double deg2rad(double degrees) {
        return (degrees * Math.PI / 180.0);
    }


    /** Finds the total distance covered from the start of an activity
     * to a particular activity point at an index in that same activity
     * and returns this as a double.
     * @param index the index for the activity point the distance is being calculated up to.
     * @return a double representing the distance covered
     */
    public double findDistanceFromStart(int index) {

        double totalDistance = 0;
        int currentIndex = 0;

        ArrayList<ActivityDataPoint> dataPoints = getActivityData();

        if (dataPoints.size() > 0) {
            double currentLongitude;
            double nextLongitude;
            double currentLatitude;
            double nextLatitude;

            for (currentIndex = 0; currentIndex < index; currentIndex++) {
                currentLongitude = dataPoints.get(currentIndex).getLongitude();
                nextLongitude = dataPoints.get(currentIndex + 1).getLongitude();
                currentLatitude = dataPoints.get(currentIndex).getLatitude();
                nextLatitude = dataPoints.get(currentIndex + 1).getLatitude();

                double theta = currentLongitude - nextLongitude;
                double distance = sin(deg2rad(currentLatitude)) * sin(deg2rad(nextLatitude)) + cos(deg2rad(currentLatitude)) * cos(deg2rad(nextLatitude)) * cos(deg2rad(theta));
                distance = acos(distance);
                distance = (distance * 180 / Math.PI);
                distance = distance * 60 * 1.1515;
                distance = distance * 1.609344;
                totalDistance += distance;

            }
        } else {
            totalDistance = distance;
        }

        return totalDistance;
    }

    /**
     * Calculates and returns the average speed
     * of a users activity in km per hour.
     * @return a double representing the activities average speed
     */
    public double findAverageSpeed() {
        int activityLength = getActivityData().size();
        if (activityLength >= 1) {
            double activityTime = getTotalTime();
            double activityDistance = findDistanceFromStart(activityLength - 1);
            return (activityDistance / (activityTime / 60));
        } else {
            System.out.println("manual speed calulation");
            return findAverageSpeedManual();
        }
    }

    public double findAverageSpeedManual() {
        double time = getTotalTime();
        System.out.println("distance " + distance);
        System.out.println("time " + time);
        return distance / (time / 60);

    }

    /**
     * Finds the calories burned during an activity of a given activity time,
     * using the met value for that activities type.
     * @param activityTime the duration of the activity
     * @param userWeight the weight of the user who participated in the activity
     * @return a double representing the calories burned in the activity
     */
    public double findCaloriesBurnedFromStart(double activityTime, double userWeight) {
        double metValue;
        double calories;

        if (type == "Biking") {
            metValue = 6;
        } else if (type == "Walking") {
            metValue = 3;
        } else if (type == "Running") {
            metValue = 7.5;
        } else {
            metValue = 4.5;             // Unknown (other) activity, average met Value
        }

        calories = metValue * (3.5 * userWeight / 200 * activityTime);

        if (calories < 0) {
            return 0;
        }
        return calories;
    }

     /**
     * Determines an activities type by checking if it contains a
     * 'key word'. If no key word is found the activity type
     * is "other".
     * @param activityDescription the description of the activity.
     * @return a string representing the activity type
     */
    public static String getActivityType(String activityDescription){

        String [] words = activityDescription.split(" ");
        for (String word : words) {
            if (runningWords.contains(word.toLowerCase())) {
                return "Running";
            } else if (walkingWords.contains(word.toLowerCase())) {
                return "Walking";
            } else if (cyclingWords.contains(word.toLowerCase())) {
                return "Biking";
            }
        }
        return "Other";
    }
}

