package seng202.team6.controller;

import seng202.team6.datahandling.DatabaseManager;

public class ApplicationManager {

    private static int currentUserID;

    private static String currentUsername;

    private static DatabaseManager databaseManager;

    private static ErrorBoxController errorBoxController = new ErrorBoxController();

    private static int currentActivityNumber = 0;

    public static void initializeApplication() {
        initializeDatabaseManager();
        currentActivityNumber = databaseManager.getActivityManager().getNumberActivities();
    }

    public static void setCurrentUser(int userid, String username) {
        currentUsername = username;
        currentUserID = userid;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static int getCurrentUserID() {
        return currentUserID;
    }

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    private static void initializeDatabaseManager()  {
        databaseManager = new DatabaseManager();
    }

    public static void displayPopUp(String title, String message, String type) {
        errorBoxController.displayErrorPopUP(title, message, type);
    }

    public static int getCurrentActivityNumber(){
        return currentActivityNumber;
    }

    public static void setCurrentActivityNumber(int activityNumber) {
        currentActivityNumber = activityNumber;
    }

}