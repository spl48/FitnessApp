package seng202.team6.datahandling;

import seng202.team6.controller.ApplicationManager;
import seng202.team6.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseUserReader {
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
    public DatabaseUserReader(Connection con, DatabaseManager db) {
        connection = con;
        databaseManager = db;
    }


    /**
     * Returns a result set of users in the database.
     * @return A result set of the users
     * @throws SQLException
     */
    public ResultSet displayUsers() throws SQLException {
        Statement state = connection.createStatement();
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
        // Trys to query the database for a user.
        Statement statement = connection.createStatement();
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
        // Tries to query the database for a user.
        Statement statement = connection.createStatement();
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

    /**
     * Gets all the usernames from the database.
     * @return An arraylist of usernames.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<String> getUsernames() {
        ArrayList<String> usernames = new ArrayList<String>();
        try {
            Statement state = connection.createStatement();
            ResultSet res = state.executeQuery("SELECT * FROM user");
            while (res.next()) {
                usernames.add(res.getString("username"));
            }

        } catch (Exception e) {
            ApplicationManager.displayErrorPopUp(e);
        }
        return usernames;
    }

    public int getLoginCount() throws SQLException {
        System.out.println("In login functions");
        int logins = 0;
        Statement state = connection.createStatement();
        System.out.println("Before query" + logins);
        ResultSet res = state.executeQuery("select logins from user where userid = " + ApplicationManager.getCurrentUserID());
        System.out.println("After query" + logins);
        logins = res.getInt("logins");
        return logins;
    }
}
