package seng202.team6.datahandling;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

import seng202.team6.controller.ApplicationManager;
import seng202.team6.models.Activity;
import seng202.team6.models.ActivityDataPoint;
import seng202.team6.models.User;

public class DatabaseManager {
    private Connection con;
    private boolean hasData = false;
    private ActivityManager activityManager;

    public DatabaseManager() {
       getConnection();
       activityManager = new ActivityManager(con);
    }

    public ActivityManager getActivityManager() {
        return activityManager;
    }

    public ResultSet displayUsers() throws SQLException {
        if(con == null) {
            getConnection();
        }

        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM user");
        return res;
    }

    /**
     * Given a username, finds the details of that user in the data base and 
     * returns a User object with these details.
     * @param aUsername The username used to lookup the user in the database.
     * @return A user object with the details of the user with the given username.
     * @throws SQLException Occurs when there is an error in the query process.
     */
    public User getUser(String aUsername) throws SQLException {
        // Checks the connection to the database.
        if(con == null) {
            getConnection();
        }

        // Trys to query the database for a user.
        Statement statement = con.createStatement();
        ResultSet userData = statement.executeQuery("SELECT * FROM user WHERE username LIKE '" + aUsername + "'");

        // Gets data from the database.
        int id = userData.getInt("userid");
        String firstName = userData.getString("firstname");
        String lastName = userData.getString("lastname");
        String dobString = userData.getString("dateofbirth");
        String gender = userData.getString("gender");
        Double height = userData.getDouble("height");
        Double weight = userData.getDouble("weight");
        Double stridelength = userData.getDouble("stridelength");
        int stepgoal = userData.getInt("stepgoal");
        int distancegoal = userData.getInt("distancegoal");
        LocalDate dob = LocalDate.parse(dobString);

        // Creates a User model using database data.
        User user = new User(firstName, lastName, dob, gender, height, weight, aUsername, id, stepgoal, distancegoal);
        return user;
    }
    public User getUserFromID(int userid) throws SQLException {
        // Checks the connection to the database.
        if(con == null) {
            getConnection();
        }

        // Trys to query the database for a user.
        Statement statement = con.createStatement();
        String sqlString = "SELECT * FROM user WHERE userid = " + userid;
        ResultSet userData = statement.executeQuery(sqlString);

        // Gets data from the database.
        String username = userData.getString("username");
        String firstName = userData.getString("firstname");
        String lastName = userData.getString("lastname");
        String dobString = userData.getString("dateofbirth");
        String gender = userData.getString("gender");
        Double height = userData.getDouble("height");
        Double weight = userData.getDouble("weight");
        Double stridelength = userData.getDouble("stridelength");
        int stepgoal = userData.getInt("stepgoal");
        int distancegoal = userData.getInt("distancegoal");
        LocalDate dob = LocalDate.parse(dobString);

        // Creates a User model using database data.
        User user = new User(firstName, lastName, dob, gender, height, weight, username, userid, stepgoal, distancegoal);
        return user;
    }

    public ArrayList<ActivityDataPoint> getDataPoints(Activity activity) throws SQLException {
        if(con == null) {
            getConnection();
        }
        int activityid = activity.getActivityid();
        Statement state = con.createStatement();
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

    public ResultSet displayActivities() throws SQLException, ClassNotFoundException {
        if(con == null) {
            getConnection();
        }
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM activity");
        return res;
    }

    public ResultSet displayRecords() throws SQLException, ClassNotFoundException {
        if(con == null) {
            getConnection();
        }
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM record");
        return res;
    }

    /**
     * Gets all the usernames from the database.
     * @return An arraylist of usernames.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<String> getUsernames() throws SQLException, ClassNotFoundException {
        if(con == null) {
            getConnection();
        }

        ArrayList<String> users = new ArrayList<String>();
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM user");
        while(res.next()){
            users.add(res.getString("username"));
        }
        return users;
    }

    /**
     * Gets the database connection and displays corresponding errors.
     */
    private void getConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:Data.db");
            initialiseDatabase();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Database Error", "There is a problem with the database. It may not exist!", "error");
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Database Error", "Unfortunately, there is a problem the database connection.", "error");
        }
    }

    private void initialiseDatabase() throws SQLException {
        if(!hasData) {
            hasData = true;
            Statement state = con.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='user'");
            if(!res.next()) {
                System.out.println("Building the User table...");
                //Build the tables
                //Create users table
                Statement userTableStatement = con.createStatement();
                String userTablesql = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY,"
                        + "username text UNIQUE NOT NULL,"
                        + "dateofbirth text,"
                        + "firstname text,"
                        + "lastname text,"
                        + "gender text,"
                        + "height REAL,"
                        + "weight REAL,"
                        + "stridelength REAL,"
                        + "stepgoal INTEGER,"
                        + "distancegoal INTEGER,"
                        + "logins INTEGER);";
                userTableStatement.execute(userTablesql);
                //Create activities table
                Statement activityTableStatement = con.createStatement();
                String activityTablesql = "CREATE TABLE IF NOT EXISTS activity(activityid INTEGER PRIMARY KEY," + "userid INTEGER,"
                        + "description text,"
                        + "start text,"
                        + "end text,"
                        + "distance REAL,"
                        + "workout text,"
                        + "notes text,"
                        + "FOREIGN KEY(userid) REFERENCES user(userid));";
                activityTableStatement.execute(activityTablesql);
                //Create workouts table

                //Create records table
                Statement recordTableStatement = con.createStatement();
                String recordTablesql = "CREATE TABLE IF NOT EXISTS record(activityid INTEGER,"
                        + "datetime text,"
                        + "heartrate INTEGER,"
                        + "latitude REAL,"
                        + "longitude REAL,"
                        + "elevation REAL,"
                        + "FOREIGN KEY(activityid) REFERENCES activity(activityid));";
                recordTableStatement.execute(recordTablesql);
                //inserting some sample data
//                String sqlprep1 = "INSERT INTO user(username, dateofbirth, firstname, lastname, gender, height, weight, stridelength) VALUES(?,?,?,?,?,?,?,?)";
//                PreparedStatement prep = con.prepareStatement(sqlprep1);
//                prep.setString(1, "Billythekidzz");
//                prep.setString(2, "1998-08-23");
//                prep.setString(3, "Gavin");
//                prep.setString(4, "Ong");
//                prep.setString(5, "Male");
//                prep.setDouble(6, 170.0);
//                prep.setDouble(7, 65.0);
//                prep.setDouble(8, 2.0);
//                prep.execute();
                System.out.println("User tables built");
            }
        }
    }

    public Connection getCon(){
        return con;
    }

    public void addUser(String username, String dob, String firstname, String lastname, String gender, double height, double weight, double stridelength, int stepGoal, int distanceGoal) throws SQLException, ClassNotFoundException {
        if(con == null) {
            getConnection();
        }
        String sqlprep1 = "INSERT INTO user(username,dateofbirth,firstname,lastname,gender,height,weight,stridelength,stepgoal,distancegoal,logins) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prep = con.prepareStatement(sqlprep1);
        prep.setString(1, username);
        prep.setString(2, dob);
        prep.setString(3, firstname);
        prep.setString(4, lastname);
        prep.setString(5, gender);
        prep.setDouble(6, height);
        prep.setDouble(7, weight);
        prep.setDouble(8, stridelength);
        prep.setInt(9, stepGoal);
        prep.setInt(10, distanceGoal);
        prep.setInt(11, 0);
        prep.execute();
    }

    /**
     * A function that deletes the User from the database given the username.
     * @param username A username of the User to be deleted.
     * @throws SQLException
     */
    public void removeUser(String username) throws SQLException {
        if(con == null) {
            getConnection();
        }
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("DELETE FROM user WHERE username = " + username);
    }


    public void addActivity(int userid, String description, String start, String end, String workout, double distance, String notes) throws SQLException {
        if(con == null) {
            getConnection();
        }
        String sqlprep1 = "INSERT INTO activity(userid,description,start,end,distance,workout,notes) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement prep = con.prepareStatement(sqlprep1);
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
        if(con == null) {
            getConnection();
        }

        ArrayList<Integer> activities = new ArrayList<Integer>();
        Statement state = con.createStatement();
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
        if(con == null) {
            getConnection();
        }
        ArrayList<Activity> activities = new ArrayList<>();
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM activity WHERE userid = " + userid);
        while(res.next()){

//            Activity activity = extractActivity(res);
//            ArrayList<ActivityDataPoint> dataPoints = this.getDataPoints(activity);
//            for (ActivityDataPoint dataPoint : dataPoints) {
//                activity.addActivityData(dataPoint);
//            }
//
//            activity.updateMaxHeartRate();
//            activity.updateMinHeartRate();
            Activity activity = getActivity(res.getInt("activityid"));
            activities.add(activity);
        }
        return activities;
    }

    /**
     *Takes a userid and date with format YYYY-MM-DD and returns a list of activities associated with the user from a certain date
     * @param userid The user id used to look up the user in the database.
     * @param date The date used to retrieve activities from.
     * @return An array list of activities associated with the user id.
     * @throws SQLException
     */
    public ArrayList<Activity> getActivitiesByDate(int userid, String date) throws SQLException {
        if(con == null) {
            getConnection();
        }
        ArrayList<Activity> activities = new ArrayList<>();
        Statement state = con.createStatement();
        String nowDate = convertToDBDateFormat(LocalDate.now().plusDays(1));
        ResultSet res = state.executeQuery("SELECT * FROM activity WHERE userid = " + userid + " AND start BETWEEN '"+ date + "' AND '" + nowDate + "'");
        while(res.next()){
//            Activity activity = extractActivity(res);
//            ArrayList<ActivityDataPoint> dataPoints = this.getDataPoints(activity);
//            for (ActivityDataPoint dataPoint : dataPoints) {
//                activity.addActivityData(dataPoint);
//            }
//
//            activity.updateMaxHeartRate();
//            activity.updateMinHeartRate();
//            activities.add(activity);
            Activity activity = getActivity(res.getInt("activityid"));
            activities.add(activity);
        }
        return activities;
    }

    /**
     *
     * @param userid the user id
     * @param workout the workout
     * @return the list of activities with the specified workout and user
     * @throws SQLException
     */
    public ArrayList<Activity> getActivitiesByWorkout(int userid, String workout) throws SQLException {
        if(con == null) {
            getConnection();
        }
        ArrayList<Activity> activities = new ArrayList<>();
        Statement state = con.createStatement();
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
        // Checks the connection to the database.
        if(con == null) {
            getConnection();
        }

        // Tries to query the database for a user.
        Statement statement = con.createStatement();
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
        // Checks the connection to the database.
        if(con == null) {
            getConnection();
        }
        // Tries to query the database for a user.
        Statement statement = con.createStatement();
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


    /**
     * Gets an activity object based on an id.
     * @param activityID The activity id.
     * @return The Activity Object relating to the id.
     * @throws SQLException
     */
    public Activity getActivity(int activityID) throws SQLException {
        // Checks the connection to the database.
        if(con == null) {
            getConnection();
        }
        // Tries to query the database for a user.
        Statement statement = con.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM activity WHERE activityID = " + activityID );
        Activity activity = extractActivity(res);
        ArrayList<ActivityDataPoint> dataPoints = this.getDataPoints(activity);
        for (ActivityDataPoint dataPoint : dataPoints) {
            activity.addActivityData(dataPoint);
        }
        activity.setDistance(res.getDouble("distance"));
        activity.updateType();
        activity.updateMaxHeartRate();
        activity.updateMinHeartRate();

        return activity;
    }

    public void updateFirstName(String firstName) throws SQLException {
        // Checks the connection to the database.
        if(con == null) {
            getConnection();
        }
        String sql = "UPDATE user SET firstname = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateFirstName = con.prepareStatement(sql);
        updateFirstName.setString(1, firstName);
        updateFirstName.execute();
    }
    public void updateLastName(String lastName) throws SQLException {
        if(con == null) {
            getConnection();
        }
        String sql = "UPDATE user SET lastname = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateLastName = con.prepareStatement(sql);
        updateLastName.setString(1, lastName);
        updateLastName.execute();

    }
    public void updateUsername(String userName) throws SQLException {
        if(con == null) {
            getConnection();
        }
        String sql = "UPDATE user SET username = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateUsername = con.prepareStatement(sql);
        updateUsername.setString(1, userName);
        updateUsername.execute();
        ApplicationManager.setCurrentUsername(userName);
    }
    public void updateDateOfBirth(LocalDate dateOfBirth) throws SQLException {
        if(con == null) {
            getConnection();
        }
        String sql = "UPDATE user SET dateofbirth = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateUsername = con.prepareStatement(sql);
        updateUsername.setString(1, dateOfBirth.toString());
        updateUsername.execute();
    }
    public void updateGender(String gender) throws SQLException {
        if(con == null) {
            getConnection();
        }
        String sql = "UPDATE user SET gender = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateGender = con.prepareStatement(sql);
        updateGender.setString(1, gender);
        updateGender.execute();

    }
    public void updateHeight(double height) throws SQLException {
        if(con == null) {
            getConnection();
        }
        String sql = "UPDATE user SET height = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateHeight = con.prepareStatement(sql);
        updateHeight.setDouble(1, height);
        updateHeight.execute();
    }
    public void updateWeight(double weight) throws SQLException {
        if(con == null) {
            getConnection();
        }
        String sql = "UPDATE user SET weight = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateWeight = con.prepareStatement(sql);
        updateWeight.setDouble(1, weight);
        updateWeight.execute();
    }
    public void updateStrideLength(double strideLength) throws SQLException {
        if(con == null) {
            getConnection();
        }
        String sql = "UPDATE user SET stridelength = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateStrideLength = con.prepareStatement(sql);
        updateStrideLength.setDouble(1, strideLength);
        updateStrideLength.execute();
    }

    /**
     * Updates the login count by adding 1
     * @param logins An Integer parameter used to set the login count of the User
     * @throws SQLException
     */
    public void updateLoginCount(int logins) throws SQLException {
        if (con == null) {
            getConnection();
        }
        logins++;
        String sql = "UPDATE user SET logins = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateLoginCount = con.prepareStatement(sql);
        updateLoginCount.setInt(1, logins);
        updateLoginCount.execute();
    }

    public int getLoginCount() throws SQLException {
        System.out.println("In login functions");
        if (con == null) {
            getConnection();
        }
        int logins = 0;
        Statement state = con.createStatement();
        System.out.println("Before query" + logins);
        ResultSet res = state.executeQuery("select logins from user where userid = " + ApplicationManager.getCurrentUserID());
        System.out.println("After query" + logins);
        logins = res.getInt("logins");
        return logins;
    }

    public ArrayList<Activity> getActivitiesWithoutRecords(int userid) throws SQLException {
        if(con == null) {
            getConnection();
        }
        ArrayList<Activity> activities = new ArrayList<>();
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM activity WHERE userid = "
                + userid
                + " AND NOT EXISTS (SELECT * FROM record WHERE activity.activityid = record.activityid);");
        while(res.next()){
//            Activity activity = extractActivity(res);
//            ArrayList<ActivityDataPoint> dataPoints = this.getDataPoints(activity);
//            for (ActivityDataPoint dataPoint : dataPoints) {
//                activity.addActivityData(dataPoint);
//            }
//            activity.updateType();
//            activity.updateMaxHeartRate();
//            activity.updateMinHeartRate();
//
            Activity activity = getActivity(res.getInt("activityid"));
            activities.add(activity);
        }
        return activities;
    }
    public ArrayList<Activity> getActivitiesWithRecords(int userid) throws SQLException {
        if(con == null) {
            getConnection();
        }
        ArrayList<Activity> activities = new ArrayList<>();
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM activity WHERE userid = "
                + userid
                + " AND EXISTS (SELECT * FROM record WHERE activity.activityid = record.activityid);");
        while(res.next()){
            Activity activity = extractActivity(res);
            ArrayList<ActivityDataPoint> dataPoints = this.getDataPoints(activity);
            for (ActivityDataPoint dataPoint : dataPoints) {
                activity.addActivityData(dataPoint);
            }
            activity.updateType();
            activity.updateMaxHeartRate();
            activity.updateMinHeartRate();
            activities.add(activity);
        }
        return activities;
    }

    public void updateActivityType(String type, int activityID) {

        try {
            if (con == null) {
                getConnection();
            }
            String sql = "UPDATE activity SET workout = ? WHERE activityid = '" + activityID + "'";
            PreparedStatement updateType = con.prepareStatement(sql);
            updateType.setString(1, type);
            updateType.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Database Update Error", "Could not update activity type!", "error");
        }
    }

    public void updateNotes(String notes, int activityID) {
        try {
            if (con == null) {
                getConnection();
            }
            String sql = "UPDATE activity SET notes = ? WHERE activityid = '" + activityID + "'";
            PreparedStatement updateNotes = con.prepareStatement(sql);
            updateNotes.setString(1, notes);
            updateNotes.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Database Update Error", "Could not update activity notes!", "error");
        }
    }

    public void updateDescription(String description, int activityID) {
        updateActivityProperty(description, activityID, "description");
    }

    public void updateStartDate(String start, int activityID) {
        updateActivityProperty(start, activityID, "start");
    }

    public void updateActivityProperty(String notes, int activityID, String property) {
        try {
            if (con == null) {
                getConnection();
            }
            String sql = "UPDATE activity SET " + property + " = ? WHERE activityid = '" + activityID + "'";
            PreparedStatement updateNotes = con.prepareStatement(sql);
            updateNotes.setString(1, notes);
            updateNotes.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Database Update Error", "Could not update activity " + property + "s!", "error");
        }
    }

    public void setDistanceGoal(int userid, int newGoal) throws SQLException {
        String sql = "UPDATE user SET distancegoal = ? WHERE userid = '" + userid + "'";
        PreparedStatement updateDistanceGoal = con.prepareStatement(sql);
        updateDistanceGoal.setInt(1, newGoal);
        updateDistanceGoal.execute();
    }
    public void setStepGoal(int userid, int newGoal) throws SQLException {
        String sql = "UPDATE user SET stepgoal = ? WHERE userid = '" + userid + "'";
        PreparedStatement updateStepGoal = con.prepareStatement(sql);
        updateStepGoal.setInt(1, newGoal);
        updateStepGoal.execute();
    }

    public double getUpdatedDistanceGoal(int userid) throws SQLException {
        LocalDate ld = LocalDate.now();
        ld = ld.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        String lastMondayDate = convertToDBDateFormat(ld);
        ArrayList<Activity> activities = getActivitiesByDate(userid, lastMondayDate);
        double totalDistance = 0;
        for (Activity activity : activities) {
            double currentDistance = activity.getDistance();          // Finds the step count for 1 activity
            totalDistance += currentDistance;
        }
        return totalDistance;
    }
    public double getUpdatedStepGoal(int userid) throws SQLException {
        double strideLength = getUserFromID(ApplicationManager.getCurrentUserID()).getWalkingStrideLength();
        LocalDate ld = LocalDate.now();
        ld = ld.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        String lastMondayDate = convertToDBDateFormat(ld);
        ArrayList<Activity> activities = getActivitiesByDate(userid, lastMondayDate);
        System.out.println(activities.size());
        double totalStepCount = 0;
        for (Activity activity : activities) {
            double currentStepCount = activity.findStepCount(strideLength);          // Finds the step count for 1 activity
            totalStepCount += currentStepCount;
        }
        return totalStepCount;
    }

    public String convertToDBDateFormat(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = date.format(formatter);
        return formattedString;
    }

    /**
     *Takes a an sql query and returns a list of activities based on the restult
     * @param sqlString The SQL String
     * @return An array list of activities based on the query result.
     * @throws SQLException
     */
    public ArrayList<Activity> getActivitiesbyQuery(String sqlString) throws SQLException {
        if(con == null) {
            getConnection();
        }
        ArrayList<Activity> activities = new ArrayList<>();
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery(sqlString);
        while(res.next()){
            Activity activity = getActivity(res.getInt("activityid"));
            activities.add(activity);
        }
        return activities;
    }
}
