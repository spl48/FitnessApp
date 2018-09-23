package seng202.team6.datahandling;

import javafx.collections.ObservableList;
import seng202.team6.controller.ApplicationManager;
import seng202.team6.models.Activity;
import seng202.team6.utilities.GeneralUtilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ActivityManager {

    private Connection connection;

    public ActivityManager(Connection con) {
        connection= con;
    }

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

    private String setPossibleWildCard(String value) {
        if (value.equals("All")) {
            return "%";
        }
        return value;
    }

    public HashMap<String, Integer> getFilteredActivties(String year, String month, String day, String type) {
        year = setPossibleWildCard(year);
        month = setPossibleWildCard(month);
        day = setPossibleWildCard(day);
        type = setPossibleWildCard(type);

        HashMap<String, Integer> filteredActivities = new HashMap<String, Integer>();

        try {
            Statement state = connection.createStatement();
//            String sqlString = String.format("SELECT * FROM ACTIVITY " +
//                    "WHERE STRFTIME('%Y', start) = '%s' AND STRFTIME('%m', start) = '%s' AND STRFTIME('%d', start)= '%s' AND workout LIKE %s", year, month, day, type);
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

}
