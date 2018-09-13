package seng202.team6.controller;

import java.sql.SQLException;

import seng202.team6.datahandling.DatabaseManager;

public class ApplicationManager {

    private static String currentUserID;

    private static DatabaseManager databaseManager;

    private static ErrorBoxController errorBoxController = new ErrorBoxController();

    public static void setCurrentUser(String username) {
        currentUserID = username;
    }

    public static String getCurrentUserID() {
        return currentUserID;
    }

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public static void initializeDatabaseManager()  throws ClassNotFoundException, SQLException  {
        databaseManager = new DatabaseManager();
    }

    public static void displayPopUp(String title, String message, String type) {
        errorBoxController.displayErrorPopUP(title, message, type);
    }

}