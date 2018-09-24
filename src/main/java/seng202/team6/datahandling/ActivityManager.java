package seng202.team6.datahandling;

import seng202.team6.controller.ApplicationManager;
import seng202.team6.models.Activity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ActivityManager {

    /**
     * The database connection
     */
    private Connection connection;

    /**
     * Initialises a new activity manager.
     * @param con The database connection.
     */
    public ActivityManager(Connection con) {
        connection= con;
    }

    /**
     * Gets all the years in the activities table.
     * @return An array list of all the years in string format.
     */
    public ArrayList<String> getPossibleYears() {
        ArrayList<String> years = new ArrayList<String>();

        try {
            Statement state = connection.createStatement();
            String sqlString = "SELECT DISTINCT STRFTIME('%Y', start) FROM ACTIVITY";
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

        HashMap<String, Integer> filteredActivities = new HashMap<String, Integer>();

        try {
            Statement state = connection.createStatement();
            String sqlString = "select * from activity where " +
                    "strftime(\"%Y\", start) LIKE '" + year + "' AND " +
                    "strftime(\"%m\", start) LIKE '" + month + "' AND " +
                    "strftime(\"%d\", start) LIKE '" + day + "' AND " +
                    "workout LIKE '" + type + "'";
            System.out.println(sqlString);
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
            System.out.println("Count " + count);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Database Error", "Cannot get the activity count!", "error");
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
                    "strftime(\"%Y\", start) LIKE '" + year + "' AND " +
                    "strftime(\"%m\", start) LIKE '" + month + "' AND " +
                    "strftime(\"%d\", start) LIKE '" + day + "' AND " +
                    "workout LIKE '" + type + "'";
            System.out.println(sqlString);
            ResultSet filteredActivityResult = state.executeQuery(sqlString);

            while (filteredActivityResult.next()) {
                filteredActivities.add(dbManager.getActivity(filteredActivityResult.getInt("activityid")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Activity Access Error", "Cannot filter the database for activities!", "error");
        }
        return filteredActivities;
    }

}
