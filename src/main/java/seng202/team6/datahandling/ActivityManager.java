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

public class ActivityManager {

    private Connection connection;

    public ActivityManager(Connection con) {
        connection= con;
    }

    public ArrayList<Integer> getPossibleYears() {
        ArrayList<Integer> years = new ArrayList<Integer>();

        try {
            Statement state = connection.createStatement();
            String sqlString = "SELECT DISTINCT SUBSTR(start, 1, 4) FROM ACTIVITY";
            ResultSet yearsResult = state.executeQuery(sqlString);

            while (yearsResult.next()) {
                years.add(Integer.parseInt(yearsResult.getString(1)));

            }

        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Activity Access Error", "Cannot query years from database!", "error");
        }
        return years;
    }

    private String setPossibleWildCard(String value) {
        if (value == "All") {
            return "%";
        }
        return value;
    }

    public ArrayList<Activity> getFilteredActivties(String year, String month, String day, String type) {
        year = setPossibleWildCard(year);
        month = setPossibleWildCard(month);
        day = setPossibleWildCard(day);
        type = setPossibleWildCard(type);

        ArrayList<Activity> filteredActivities = new ArrayList<Activity>();

        try {
            Statement state = connection.createStatement();
            String sqlString = String.format("SELECT * FROM ACTIVITY " +
                    "WHERE YEAR(start) = %s AND MONTH(start) = %s AND DAY(start) = %s AND workout LIKE %s", year, month, day, type);
            ResultSet filteredActivityResult = state.executeQuery(sqlString);

            while (filteredActivityResult.next()) {
                filteredActivities.add(ApplicationManager.getDatabaseManager().extractActivity(filteredActivityResult));

            }

        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationManager.displayPopUp("Activity Access Error", "Cannot filter the database for activities!", "error");
        }
        return filteredActivities;
    }

}
