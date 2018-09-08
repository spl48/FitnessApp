package seng202.team6.datahandling;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager implements DataLoader {
    private static Connection con;
    private static boolean hasData = false;

    public static ResultSet displayUsers() throws SQLException, ClassNotFoundException {
        if(con == null) {
            getConnection();
        }

        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM user");
        return res;
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

    public static ArrayList<String> getUsernames() throws SQLException, ClassNotFoundException {
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

    private static void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:Data.db");
        initialiseDatabase();
    }

    private static void initialiseDatabase() throws SQLException {
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
                        + "weight REAL);";
                userTableStatement.execute(userTablesql);
                //Create activities table
                Statement activityTableStatement = con.createStatement();
                String activityTablesql = "CREATE TABLE IF NOT EXISTS activity(activityid INTEGER PRIMARY KEY," + "userid INTEGER,"
                        + "description text,"
                        + "start text,"
                        + "end text,"
                        + "workout text,"
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
                String sqlprep1 = "INSERT INTO user(username) VALUES(?)";
                PreparedStatement prep = con.prepareStatement(sqlprep1);
                prep.setString(1, "Billythekidzz");
                prep.execute();
                System.out.println("User tables built");
            }
        }
    }

    public Connection getCon(){
        return con;
    }

    public static void addUser(String username, String dob, String firstname, String lastname, String gender, double height, double weight) throws SQLException, ClassNotFoundException {
        if(con == null) {
            getConnection();
        }
        String sqlprep1 = "INSERT INTO user(username,dateofbirth,firstname,lastname,gender,height,weight) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement prep = con.prepareStatement(sqlprep1);
        prep.setString(1, username);
        prep.setString(2, dob);
        prep.setString(3, firstname);
        prep.setString(4, lastname);
        prep.setString(5, gender);
        prep.setDouble(6, height);
        prep.setDouble(7, weight);
        prep.execute();
    }

    public static void addActivity(int userid, String description, String start, String end, String workout) throws SQLException, ClassNotFoundException {
        if(con == null) {
            getConnection();
        }
        String sqlprep1 = "INSERT INTO activity(userid,description,start,end,workout) VALUES(?,?,?,?,?)";
        PreparedStatement prep = con.prepareStatement(sqlprep1);
        prep.setInt(1, userid);
        prep.setString(2, description);
        prep.setString(3, start);
        prep.setString(4, end);
        prep.setString(5, workout);
        prep.execute();
    }
}
