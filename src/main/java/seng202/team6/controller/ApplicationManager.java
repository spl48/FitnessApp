package seng202.team6.controller;

import seng202.team6.datahandling.DatabaseManager;

/**
 * <h1>Application Manager</h1>
 * <p>Contains static methods and properties which relate to the instance or session of the Application to be used
 * frequently by other classes throughout all packages. Provides initialisation methods as well as useful pop up methods.</p>
 */
public class ApplicationManager {

    /**
     * The current user ID for the user which is currently logged in.
     */
    private static int currentUserID;

    /**
     * The current username for the user which is logged in.
     */
    private static String currentUsername;

    /**
     * The Applications database manager which can accesses methods relating to the database.
     */
    private static DatabaseManager databaseManager;

    /**
     * The pop up box controller, used to make pop ups.
     */
    private static PopUpBoxController popUpBoxController = new PopUpBoxController();

    /**
     * The current activity number which is the id of the last record to be added into the database.
     */
    private static int currentActivityNumber = 0;

    /**
     * the current index of the quote list quote being displayed on the home page
     */
    private static int curQuoteIndex;


    /**
     * Initialises a session in the application by setting up the database and setting the
     * activity number.
     */
    public static void initializeApplication() {
        initializeDatabaseManager();
        currentActivityNumber = databaseManager.getActivityManager().getNumberActivities();
    }


    /**
     * Sets the current user details.
     * @param userid The user id.
     * @param username The username.
     */
    public static void setCurrentUser(int userid, String username) {
        currentUsername = username;
        currentUserID = userid;
    }

    /**
     * Sets the current user username.
     * @param username The username.
     */
    public static void setCurrentUsername(String username) {
        currentUsername = username;
    }


    /**
     * Gets the username of the user which is currently signed in.
     * @return The current user.
     */
    public static String getCurrentUsername() {
        return currentUsername;
    }


    /**
     * Gets the current users ID.
     * @return the current users ID.
     */
    public static int getCurrentUserID() {
        return currentUserID;
    }

    /**
     * Gets the database manager for this session in the application.
     * @return The database manager.
     */
    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }


    /**
     * Initialises an instance of the database manager.
     */
    private static void initializeDatabaseManager()  {
        databaseManager = new DatabaseManager();
    }


    /**
     * Displays a pop up of a certain type with a window title and message to be displayed.
     * @param title The title of the pop up.
     * @param message The message displayed by the pop up.
     * @param type The type of the pop up.
     */
    public static void displayPopUp(String title, String message, String type) {
        popUpBoxController.displayErrorPopUP(title, message, type);
    }

    /**
     * Displays an error pop up with the exception data
     * @param e The exception that occurred.
     */
    public static void displayErrorPopUp(Exception e) {
        e.printStackTrace();
        popUpBoxController.displayErrorPopUP(e.getClass().getSimpleName(), e.getMessage(), "error");
    }

    /**
     * Displays an error pop up with the exception data
     * @param e The exception that occurred.
     */
    public static void displayErrorPopUpCustom(String title, String message, Exception e) {
        e.printStackTrace();
        popUpBoxController.displayErrorPopUP(title, message, "error");
    }


    /**
     * Gets the current activity number.
     * @return The current activity number.
     */
    public static int getCurrentActivityNumber(){
        return currentActivityNumber;
    }


    /**
     * Sets the current activity number.
     * @param activityNumber The current activity number.
     */
    public static void setCurrentActivityNumber(int activityNumber) {
        currentActivityNumber = activityNumber;
    }

    /**
     * Sets the current index for the quote list
     * @param index to be set to
     */
    public static void setCurQuoteIndex(int index) { curQuoteIndex = index; }

    /**
     * Gets the current index for the quote list
     * @return current index for the quote list
     */
    public static int getCurQuoteIndex() { return curQuoteIndex; }

}

