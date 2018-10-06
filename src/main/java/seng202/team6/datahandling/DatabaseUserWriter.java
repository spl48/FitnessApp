package seng202.team6.datahandling;

import seng202.team6.controller.ApplicationManager;
import seng202.team6.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DatabaseUserWriter {
    /**
     * The database connection
     */
    private Connection connection;
    /**
     * The database manager
     */
    private DatabaseManager databaseManager;

    /**
     * Initialises a new DatabaseUserWriter.
     * @param con The database connection.
     */
    public DatabaseUserWriter(Connection con, DatabaseManager db) {
        connection = con;
        databaseManager = db;
    }

    /**
     * Adds a new user into the database.
     * @param username The username
     * @param dob The date of birth
     * @param firstname The user's first name
     * @param lastname The user's last name
     * @param gender The user's gender
     * @param height The user's height
     * @param weight The user's weight
     * @param stepGoal The user's step goal
     * @param distanceGoal The user's distance goal
     * @throws SQLException
     */
    public void addUser(String username, String dob, String firstname, String lastname, String gender, double height, double weight, int stepGoal, int distanceGoal) throws SQLException {
        LocalDate date = LocalDate.now();
        User user = new User(firstname,lastname,date,gender,height,weight,"aaa", 123,stepGoal,distanceGoal);
        String sqlprep1 = "INSERT INTO user(username,dateofbirth,firstname,lastname,gender,height,weight,stridelength,stepgoal,distancegoal,logins) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prep = connection.prepareStatement(sqlprep1);
        prep.setString(1, username);
        prep.setString(2, dob);
        prep.setString(3, firstname);
        prep.setString(4, lastname);
        prep.setString(5, gender);
        prep.setDouble(6, height);
        prep.setDouble(7, weight);
        prep.setDouble(8, user.getWalkingStrideLength());
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
    public void removeUser(String username) {
        try {
            Statement state = connection.createStatement();
            System.out.println("At the start of profile deletion");
            databaseManager.getActivityManager().removeRecords();
            state.executeUpdate("DELETE FROM activity where userid = " + ApplicationManager.getCurrentUserID());
            state.executeUpdate("DELETE FROM user WHERE username = '" + username + "'");
            System.out.println("At the end of profile deletion");
        } catch (SQLException e) {
            ApplicationManager.displayErrorPopUp(e);
        }
    }

    public void updateFirstName(String firstName) throws SQLException {
        String sql = "UPDATE user SET firstname = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateFirstName = connection.prepareStatement(sql);
        updateFirstName.setString(1, firstName);
        updateFirstName.execute();
    }
    public void updateLastName(String lastName) throws SQLException {
        String sql = "UPDATE user SET lastname = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateLastName = connection.prepareStatement(sql);
        updateLastName.setString(1, lastName);
        updateLastName.execute();

    }
    public void updateUsername(String userName) throws SQLException {
        String sql = "UPDATE user SET username = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateUsername = connection.prepareStatement(sql);
        updateUsername.setString(1, userName);
        updateUsername.execute();
        ApplicationManager.setCurrentUsername(userName);
    }
    public void updateDateOfBirth(LocalDate dateOfBirth) throws SQLException {
        String sql = "UPDATE user SET dateofbirth = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateUsername = connection.prepareStatement(sql);
        updateUsername.setString(1, dateOfBirth.toString());
        updateUsername.execute();
    }
    public void updateGender(String gender) throws SQLException {
        String sql = "UPDATE user SET gender = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateGender = connection.prepareStatement(sql);
        updateGender.setString(1, gender);
        updateGender.execute();

    }
    public void updateHeight(double height) throws SQLException {
        String sql = "UPDATE user SET height = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateHeight = connection.prepareStatement(sql);
        updateHeight.setDouble(1, height);
        updateHeight.execute();
    }
    public void updateWeight(double weight) throws SQLException {
        String sql = "UPDATE user SET weight = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateWeight = connection.prepareStatement(sql);
        updateWeight.setDouble(1, weight);
        updateWeight.execute();
    }
    public void updateStrideLength(double stridelength) throws SQLException {
        User user = databaseManager.getUserReader().getUser(ApplicationManager.getCurrentUsername());
        user.setWalkingStrideLength(stridelength);
        String sql = "UPDATE user SET stridelength = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateStrideLength = connection.prepareStatement(sql);
        updateStrideLength.setDouble(1, stridelength);
        updateStrideLength.execute();
    }

    /**
     * Updates the login count by adding 1
     * @param logins An Integer parameter used to set the login count of the User
     * @throws SQLException
     */
    public void updateLoginCount(int logins) throws SQLException {
        logins++;
        String sql = "UPDATE user SET logins = ? WHERE userid = " + ApplicationManager.getCurrentUserID();
        PreparedStatement updateLoginCount = connection.prepareStatement(sql);
        updateLoginCount.setInt(1, logins);
        updateLoginCount.execute();
    }

    public void setDistanceGoal(int userid, int newGoal) throws SQLException {
        String sql = "UPDATE user SET distancegoal = ? WHERE userid = '" + userid + "'";
        PreparedStatement updateDistanceGoal = connection.prepareStatement(sql);
        updateDistanceGoal.setInt(1, newGoal);
        updateDistanceGoal.execute();
    }

    public void setStepGoal(int userid, int newGoal) throws SQLException {
        String sql = "UPDATE user SET stepgoal = ? WHERE userid = '" + userid + "'";
        PreparedStatement updateStepGoal = connection.prepareStatement(sql);
        updateStepGoal.setInt(1, newGoal);
        updateStepGoal.execute();
    }
}