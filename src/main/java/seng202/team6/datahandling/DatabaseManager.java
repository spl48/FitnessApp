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
    private DatabaseUserReader databaseUserReader;
    private DatabaseUserWriter databaseUserWriter;

    public DatabaseManager() {
       getConnection();
       activityManager = new ActivityManager(con, this);
       databaseUserReader = new DatabaseUserReader(con, this);
       databaseUserWriter = new DatabaseUserWriter(con, this);
    }

    /**
     * Establishes the database connection and displays corresponding errors.
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

    /**
     * Gets the activity manager for the database
     * @return The activity manager
     */
    public ActivityManager getActivityManager() {
        return activityManager;
    }

    /**
     * Gets the user reader for the database
     * @return The user reader
     */
    public DatabaseUserReader getUserReader() {
        return databaseUserReader;
    }

    /**
     * Gets the user writer for the database
     * @return The user writer
     */
    public DatabaseUserWriter getUserWriter() {
        return databaseUserWriter;
    }

    /**
     * Initialises the database, creating users, activity and workouts table.
     * @throws SQLException
     */
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

    public String convertToDBDateFormat(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = date.format(formatter);
        return formattedString;
    }
}
