package seng202.team6.datahandling;

import seng202.team6.controller.ApplicationManager;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;

public class ActivityManager {

    /**
     * The database connection
     */
    private Connection connection;

    /**
     * The database manager
     */
    private DatabaseManager databaseManager;

    /**
     * Initialises a new activity manager.
     * @param con The database connection.
     */
    public ActivityManager(Connection con, DatabaseManager db) {
        databaseManager = db;
        connection= con;
    }

    /**
     * Gets all the years in the activities table.
     * @return An array list of all the years in string format.
     */
    public ArrayList<String> getPossibleYears() {
        ArrayList<String> years = new ArrayList<String>();
        int currUser = ApplicationManager.getCurrentUserID();

        try {
            Statement state = connection.createStatement();
            String sqlString = "SELECT DISTINCT STRFTIME('%Y', start) FROM ACTIVITY WHERE userid = " + currUser;
            ResultSet yearsResult = state.executeQuery(sqlString);

            while (yearsResult.next()) {
                years.add(yearsResult.getString(1));

            }

        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Activity Access Error", "Cannot query years from database!", "error");
        }
        return years;
    }

    /**
     * If the value is 'All', sets to the wild card character for query in SQLite.
     * @param value The value to be possibly changed to a wild card.
     * @return Either the wild card or the original character.
     */
    private String setPossibleWildCard(String value) {
        if (value.equals("All")) {
            return "%";
        }
        return value;
    }

    /**
     * Gets id and description fields of activities from the database based on a filter.
     * @param year The year filter
     * @param month The month filter
     * @param day The day filter
     * @param type The type filter.
     * @return A hash map of the descriptions and ids of the activities filtered
     */
    public HashMap<String, Integer> getFilteredActivties(String year, String month, String day, String type) {
        year = setPossibleWildCard(year);
        month = setPossibleWildCard(month);
        day = setPossibleWildCard(day);
        type = setPossibleWildCard(type);
        int userId = ApplicationManager.getCurrentUserID();

        HashMap<String, Integer> filteredActivities = new HashMap<String, Integer>();

        try {
            Statement state = connection.createStatement();
            String sqlString = "select * from activity where " +
                    "userid = " + userId + " AND " +
                    "strftime(\"%Y\", start) LIKE '" + year + "' AND " +
                    "strftime(\"%m\", start) LIKE '" + month + "' AND " +
                    "strftime(\"%d\", start) LIKE '" + day + "' AND " +
                    "workout LIKE '" + type + "'";
            ResultSet filteredActivityResult = state.executeQuery(sqlString);

            while (filteredActivityResult.next()) {
                String activityDescription = filteredActivityResult.getString("description");
                int activityid = filteredActivityResult.getInt("activityid");
                filteredActivities.put(activityDescription, activityid);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Activity Access Error", "Cannot filter the database for activities!", "error");
        }
        return filteredActivities;
    }

    /**
     * Gets the total number of entered activities into the database by executing an query.
     * @return The number of activities in the activities table.
     */
    public int getNumberActivities() {
        try {
            Statement state = connection.createStatement();
            String sqlString = "SELECT COUNT(*) as activityCount FROM ACTIVITY";
            ResultSet result = state.executeQuery(sqlString);
            int count = result.getInt("activityCount");
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Database Error", "Cannot get the activity count!", "error");
        }
        return 0;

    }

    /**
     * Gets the total number of entered activities into the database by executing an query.
     * @return The number of activities in the activities table.
     */
    public int getNumberUserActivities() {
        try {
            Statement state = connection.createStatement();
            String sqlString = "SELECT COUNT(*) as activityCount FROM ACTIVITY where userid = " + ApplicationManager.getCurrentUserID();
            ResultSet result = state.executeQuery(sqlString);
            int count = result.getInt("activityCount");
            return count;
        } catch (SQLException e) {
            ApplicationManager.displayErrorPopUp(e);
        }
        return 0;

    }


    /**
     * Gets activities from the database based on a filter.
     * @param year The year filter
     * @param month The month filter
     * @param day The day filter
     * @param type The type filter.
     * @return An arraylist of the filtered activities.
     */
    public ArrayList<Activity> getFilteredFullActivties(String year, String month, String day, String type) {
        year = setPossibleWildCard(year);
        month = setPossibleWildCard(month);
        day = setPossibleWildCard(day);
        type = setPossibleWildCard(type);

        ArrayList<Activity> filteredActivities = new ArrayList<Activity>();

        try {
            Statement state = connection.createStatement();
            DatabaseManager dbManager = ApplicationManager.getDatabaseManager();

            String sqlString = "select * from activity where " +
                    "userid = " + ApplicationManager.getCurrentUserID() + " AND " +
                    "strftime(\"%Y\", start) LIKE '" + year + "' AND " +
                    "strftime(\"%m\", start) LIKE '" + month + "' AND " +
                    "strftime(\"%d\", start) LIKE '" + day + "' AND " +
                    "workout LIKE '" + type + "'";
            System.out.println(sqlString);
            ResultSet filteredActivityResult = state.executeQuery(sqlString);

            while (filteredActivityResult.next()) {
                filteredActivities.add(getActivity(filteredActivityResult.getInt("activityid")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Activity Access Error", "Cannot filter the database for activities!", "error");
        }
        return filteredActivities;
    }

    /**
     * Returns an array list of activity data points associatted with a given activity.
     * @param activity the given activity
     * @return The array list of activity data points
     * @throws SQLException
     */
    public ArrayList<ActivityDataPoint> getDataPoints(Activity activity) throws SQLException {
        int activityid = activity.getActivityid();
        Statement state = connection.createStatement();
        ArrayList<ActivityDataPoint> dataPoints = new ArrayList<>();
        ResultSet res = state.executeQuery("SELECT * FROM record WHERE activityid = " + activityid);
        while(res.next()){
            String datetime = res.getString("datetime");
            String[] parts = datetime.split("T");
            String recordTime = parts[1];
            LocalTime localStartTime = DataHandlerUtilities.parseTime(recordTime);
            int heartRate = res.getInt("heartrate");
            Double latitude = res.getDouble("latitude");
            Double longitude = res.getDouble("longitude");
            Double elevation = res.getDouble("elevation");
            ActivityDataPoint dataPoint = new ActivityDataPoint(localStartTime, heartRate, latitude, longitude, elevation);
            dataPoints.add(dataPoint);
        }
        return dataPoints;
    }

    /**
     * Removes all records from a given user **FIX THIS**
     * @throws SQLException
     */
    public void removeRecords() throws SQLException {
        Statement state = connection.createStatement();
        state.executeUpdate("delete from record where exists(select * from activity where activity.activityid = record.activityid and activity.userid = " + ApplicationManager.getCurrentUserID() + ")");
    }

    /**
     *
     * @param userid the user id
     * @param workout the workout
     * @return the list of activities with the specified workout and user
     * @throws SQLException
     */
    public ArrayList<Activity> getActivitiesByWorkout(int userid, String workout) throws SQLException {
        ArrayList<Activity> activities = new ArrayList<>();
        Statement state = connection.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM activity WHERE userid = " + userid + " AND workout = " + workout);
        while(res.next()){

            Activity activity = extractActivity(res);
            ArrayList<ActivityDataPoint> dataPoints = this.getDataPoints(activity);
            for (ActivityDataPoint dataPoint : dataPoints) {
                activity.addActivityData(dataPoint);
            }
            activity.updateMaxHeartRate();
            activity.updateMinHeartRate();
            activities.add(activity);
        }
        return activities;
    }

    /**
     * Gets the records from an activity.
     * @param activityID The activity id to get records for.
     * @return An ArrayList of ActivityDataPoints (records)
     * @throws SQLException
     */
    public ArrayList<ActivityDataPoint>  getActivityRecords(int activityID) throws SQLException {
        // Tries to query the database for a user.
        Statement statement = connection.createStatement();
        ResultSet recordData = statement.executeQuery("SELECT * FROM record WHERE activityID = " + activityID );
        ArrayList<ActivityDataPoint> records = new ArrayList<ActivityDataPoint>();

        // Gets data from the database.
        while(recordData.next()) {
            ActivityDataPoint record = extractRecord(recordData);
            records.add(record);
        }

        // Creates a User model using database data.
        return records;
    }
    /**
     *Takes a userid and date with format YYYY-MM-DD and returns a list of activities associated with the user from a certain date
     * @param userid The user id used to look up the user in the database.
     * @param date The date used to retrieve activities from.
     * @return An array list of activities associated with the user id.
     * @throws SQLException
     */
    public ArrayList<Activity> getActivitiesByDate(int userid, String date) throws SQLException {
        ArrayList<Activity> activities = new ArrayList<>();
        Statement state = connection.createStatement();
        String nowDate = databaseManager.convertToDBDateFormat(LocalDate.now().plusDays(1));
        ResultSet res = state.executeQuery("SELECT * FROM activity WHERE userid = " + userid + " AND start BETWEEN '"+ date + "' AND '" + nowDate + "'");
        while(res.next()){
            Activity activity = getActivity(res.getInt("activityid"));
            activities.add(activity);
        }
        return activities;
    }

    /**
     * Extracts a single record given a result set.
     * @param res The result set for the record.
     * @return The AcvitityDataPoint which the result set gives.
     * @throws SQLException
     */
    public ActivityDataPoint extractRecord(ResultSet res) throws SQLException {

        String datetime = res.getString("datetime");
        String[] parts = datetime.split("T");
        String recordTime = parts[1];
        LocalTime localStartTime = DataHandlerUtilities.parseTime(recordTime);
        int heartRate = res.getInt("heartrate");
        Double latitude = res.getDouble("latitude");
        Double longitude = res.getDouble("longitude");
        Double elevation = res.getDouble("elevation");

        ActivityDataPoint dataPoint = new ActivityDataPoint(localStartTime, heartRate, latitude, longitude, elevation);
        return dataPoint;
    }

    /**
     * Gets a activity description given an id.
     * @param activityID The activity id
     * @return The activity description.
     * @throws SQLException
     */
    public String getActivityDescription(int activityID) throws SQLException {
        // Tries to query the database for a user.
        Statement statement = connection.createStatement();
        ResultSet description = statement.executeQuery("SELECT description FROM activity WHERE activityID = " + activityID );
        return description.getString(1);
    }

    /**
     * Given a result set extracts an Activity.
     * @param res The result set
     * @return The Activity object from the result set.
     * @throws SQLException
     */
    public Activity extractActivity(ResultSet res) throws SQLException {
        String activityDescription = res.getString("description");
        String activityStart = res.getString("start");
        String[] startParts = activityStart.split("T");
        String activityStartDate = startParts[0];
        String activityStartTime = startParts[1];
        String activityEnd = res.getString("end");
        String[] endParts = activityEnd.split("T");
        String activityEndDate = endParts[0];
        String activityEndTime = endParts[1];
        String activityWorkout = res.getString("workout");
        String activityNotes = res.getString("notes");
        int activityid = res.getInt("activityid");

        LocalDate localStartDate = DataHandlerUtilities.parseDate(activityStartDate);
        LocalDate localEndDate = DataHandlerUtilities.parseDate(activityEndDate);

        LocalTime localStartTime = DataHandlerUtilities.parseTime(activityStartTime);
        LocalTime localEndTime = DataHandlerUtilities.parseTime(activityEndTime);

        Activity activity = new Activity(activityid, activityWorkout, activityDescription, localStartDate, localEndDate, localStartTime, localEndTime, activityNotes);

        return activity;
    }

    public void addActivity(int userid, String description, String start, String end, String workout, double distance, String notes) throws SQLException {
        String sqlprep1 = "INSERT INTO activity(userid,description,start,end,distance,workout,notes) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement prep = connection.prepareStatement(sqlprep1);
        prep.setInt(1, userid);
        prep.setString(2, description);
        prep.setString(3, start);
        prep.setString(4, end);
        prep.setDouble(5, distance);
        prep.setString(6, workout);
        prep.setString(7, notes);
        prep.execute();
    }


    public ArrayList<Integer> getActivityIDs(int userid) throws SQLException {
        ArrayList<Integer> activities = new ArrayList<Integer>();
        Statement state = connection.createStatement();
        ResultSet res = state.executeQuery("SELECT activityid FROM activity WHERE userid = " + userid);
        while(res.next()){
            activities.add(res.getInt("activityid"));
        }
        return activities;
    }
    /**
     *Takes a userid and returns a list of activities associated with the user
     * @param userid The user id used to look up the user in the database.
     * @return An array list of activities associated with the user id.
     * @throws SQLException
     */
    public ArrayList<Activity> getActivities(int userid) throws SQLException {
        ArrayList<Activity> activities = new ArrayList<>();
        Statement state = connection.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM activity WHERE userid = " + userid);
        while(res.next()){
            Activity activity = getActivity(res.getInt("activityid"));
            activities.add(activity);
        }
        return activities;
    }


    /**
     * Gets an activity object based on an id.
     * @param activityID The activity id.
     * @return The Activity Object relating to the id.
     * @throws SQLException
     */
    public Activity getActivity(int activityID) throws SQLException {

        // Tries to query the database for a user.
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM activity WHERE activityID = " + activityID );
        Activity activity = extractActivity(res);
        ArrayList<ActivityDataPoint> dataPoints = this.getDataPoints(activity);
        for (ActivityDataPoint dataPoint : dataPoints) {
            activity.addActivityData(dataPoint);
        }
        activity.setDistance(res.getDouble("distance"));
        activity.updateMaxHeartRate();
        activity.updateMinHeartRate();

        return activity;
    }


    public ArrayList<Activity> getActivitiesWithRecords(int userid) throws SQLException {
        ArrayList<Activity> activities = new ArrayList<>();
        Statement state = connection.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM activity WHERE userid = "
                + userid
                + " AND EXISTS (SELECT * FROM record WHERE activity.activityid = record.activityid);");
        while(res.next()){
            Activity activity = getActivity(res.getInt("activityid"));
            activities.add(activity);
        }
        return activities;
    }


    public void updateActivityType(String type, int activityID) {
        updateActivityProperty(type, activityID, "workout");
    }


    public void updateNotes(String notes, int activityID) {
        updateActivityProperty(notes, activityID, "notes");
    }


    public void updateDescription(String description, int activityID) {
        updateActivityProperty(description, activityID, "description");
    }

    public void updateDistance(String distance, int activityID) {
        updateActivityProperty(distance, activityID, "distance");
    }

    public void updateStartDate(String start, int activityID) {
        updateActivityProperty(start, activityID, "start");
    }


    public void updateEndDate(String end, int activityID) {
        updateActivityProperty(end, activityID, "end");
    }


    public void updateActivityProperty(String notes, int activityID, String property) {
        try {
            String sql = "UPDATE activity SET " + property + " = ? WHERE activityid = '" + activityID + "'";
            PreparedStatement updateNotes = connection.prepareStatement(sql);
            updateNotes.setString(1, notes);
            updateNotes.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Database Update Error", "Could not update activity " + property + "s!", "error");
        }
    }


    public ArrayList<Activity> getActivitiesForWeeklyGoal(int userid) {
        ArrayList<Activity> activities = new ArrayList<Activity>();
        try {
            LocalDate ld = LocalDate.now();
            ld = ld.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            String lastMondayDate = databaseManager.convertToDBDateFormat(ld);
            activities = getActivitiesByDate(userid, lastMondayDate);
        } catch (SQLException e) {
            ApplicationManager.displayErrorPopUp(e);
        }
        return activities;
    }


    public double getUpdatedDistanceGoal(int userid) {

        ArrayList<Activity> activities = getActivitiesForWeeklyGoal(userid);

        double totalDistance = 0;
        for (Activity activity : activities) {
            double currentDistance = activity.getDistance();          // Finds the step count for 1 activity
            totalDistance += currentDistance;
        }
        return totalDistance;
    }


    public double getUpdatedStepGoal(int userid) {

        double totalStepCount = 0;

        try {
            double strideLength = databaseManager.getUserReader().getUserFromID(ApplicationManager.getCurrentUserID()).getWalkingStrideLength();
            ArrayList<Activity> activities = getActivitiesForWeeklyGoal(userid);

            for (Activity activity : activities) {
                double currentStepCount = activity.findStepCount(strideLength);          // Finds the step count for 1 activity
                totalStepCount += currentStepCount;
            }
        } catch (SQLException e) {
            ApplicationManager.displayErrorPopUp(e);
        }
        return totalStepCount;
    }

    /**
     *Takes a an sql query and returns a list of activities based on the restult
     * @param sqlString The SQL String
     * @return An array list of activities based on the query result.
     * @throws SQLException
     */
    public ArrayList<Activity> getActivitiesbyQuery(String sqlString) throws SQLException {
        ArrayList<Activity> activities = new ArrayList<>();
        Statement state = connection.createStatement();
        ResultSet res = state.executeQuery(sqlString);
        while(res.next()){
            Activity activity = getActivity(res.getInt("activityid"));
            activities.add(activity);
        }
        return activities;
    }
}
