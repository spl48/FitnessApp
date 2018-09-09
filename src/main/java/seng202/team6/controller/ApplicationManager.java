package seng202.team6.controller;

public class ApplicationManager {

    private static String currentUserID;

    public static void setCurrentUser(String username) {
        currentUserID = username;
    }

    public static String getCurrentUserID() {
        return currentUserID;
    }

}