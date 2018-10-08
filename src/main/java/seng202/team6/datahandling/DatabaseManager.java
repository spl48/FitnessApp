package seng202.team6.datahandling;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seng202.team6.controller.ApplicationManager;

/**
 * A DatabaseManager that handles all the database information and methods.
 */

public class DatabaseManager {
    private Connection con;
    private boolean hasData = false;
    private ActivityManager activityManager;
    private DatabaseUserReader databaseUserReader;
    private DatabaseUserWriter databaseUserWriter;

    /**
     * A constructor for the Database Manager that gets the database connection.
     * @param testOrMain
     */
    public DatabaseManager(String testOrMain) {
        if(testOrMain.equalsIgnoreCase("main")) {
            getConnection();
            activityManager = new ActivityManager(con, this);
            databaseUserReader = new DatabaseUserReader(con, this);
            databaseUserWriter = new DatabaseUserWriter(con, this);
        }
        if(testOrMain.equalsIgnoreCase("test")) {
            getConnectionTest();
            activityManager = new ActivityManager(con, this);
            databaseUserReader = new DatabaseUserReader(con, this);
            databaseUserWriter = new DatabaseUserWriter(con, this);
        }
    }

    /**
     * Establishes the database connection and displays corresponding errors.
     */
    private void getConnection() {
        try {
            if(con == null) {
                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection("jdbc:sqlite:Data.db");
                initialiseDatabase();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Database Error", "There is a problem with the database. It may not exist!", "error");
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Database Error", "Unfortunately, there is a problem the database connection.", "error");
        }
    }
    /**
     * Establishes the database connection to the test file
     */
    public void getConnectionTest() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:Test.db");
            initialiseDatabase();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Database Error", "There is a problem with the database. It may not exist.", "error");
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
                System.out.println("User tables built");
            }
        }
    }

    /**
     * A function that gets the connection.
     * @return Returns the connection.
     */
    public Connection getCon(){
        return con;
    }

    /**
     * A function that converts the Date to the format yyyy-MM-dd.
     * @param date A LocalDate parameter date that is being formatted
     * @return Returns a String that represents the date.
     */
    public String convertToDBDateFormat(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = date.format(formatter);
        return formattedString;
    }
}
