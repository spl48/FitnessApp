package seng202.team6.models;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class implements ActivityList and sets the required information about the list of
 * Activities the User has done.
 * @author Angelica Dela Cruz
 * @version 1.1, Aug 2018.
 */
public class ActivityList {

    /**
     * The category of the Activity
     */
    private String activityCategory;

    /**
     * The ArrayList of activities
     */
    private ArrayList<Activity> activities = new ArrayList<Activity>();

    /**
     * The allowed Activity types
     */
    private static final ArrayList<String> allowedActivities = new ArrayList<String>(Arrays.asList("Running", "Walking", "Biking"));

    /**
     * The constructor for ActivityList that takes the parameter category and sets the activity
     * category for the list.
     * @param activityCategory A String parameter used to set the category of the Activity
     */
    ActivityList(String activityCategory)
    {
        if (allowedActivities.contains(activityCategory)) {
            this.activityCategory = activityCategory;
        } else {
            this.activityCategory = "invalid";
        }
    }

    /**
     * A function that takes the parameter category and sets the category of the activity
     * to the given String parameter activityCategory. Checks if the given category is within
     * the allowed activities, otherwise it is set to invalid.
     * @param activityCategory A String parameter that is used to set the activity category.
     */
    public void setActivityCategory(String activityCategory)
    {
        if (allowedActivities.contains(activityCategory)) {
            this.activityCategory = activityCategory;
        } else {
            this.activityCategory = "invalid";
        }
    }

    /**
     * A function that returns the activity category.
     * @return Returns a String that represents the category of the specific activity.
     */
    public String getActivityCategory()
    {
        return activityCategory;
    }

    /**
     * A function that takes the parameter activity and adds the given activity to the list
     * of User Activities.
     * @param activity An Activity parameter which is added to the activity list.
     */
    public void addActivity(Activity activity)
    {
        activities.add(activity);
    }

    /**
     * A function that takes the parameter activity and removes the specific activity from
     * the Activity list.
     * @param activity An Activity parameter that is removed from the list of Activities.
     */
    public void removeActivity(Activity activity)
    {
        int index = activities.indexOf(activity);
        activities.remove(index);
    }

    /**
     * A function that returns ArrayList of activities done by the User.
     * @return Returns an ArrayList of Activity.
     */
    public ArrayList<Activity> getActivities()
    {
        return activities;
    }

}

