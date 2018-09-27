package seng202.team6.utilities;

import seng202.team6.controller.ApplicationManager;
import seng202.team6.models.Activity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DatabaseValidation {

    public static boolean validate(ArrayList<String[]> data) throws SQLException {
        if(!validateNotEmpty(data)) {
            return false;
        }else {
            if (!(validateLineLength(data) && validateFirstLine(data))) {
                return false;
            } else {
                for (String[] line : data) {
                    if (!line[0].equalsIgnoreCase("#start")) {
                        if (!(validateDate(line[0])
                                && validateTime(line[1])
                                && validateHeartRate(line[2])
                                && validateLatitude(line[3])
                                && validateLongitude(line[4])
                                && validateElevation(line[5]))) {
                            return false;
                        } else {
                            if (!validateNonDuplicateData(data)) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
        }
    }

    public static boolean validateLineLength(ArrayList<String[]> data){
        for (String[] line : data){
            if(line.length < 6){
                System.out.println("Invalid line length detected!");
                ApplicationManager.displayPopUp("Invalid Data", "Invalid number of data fields detected!", "error");
                return false;
            }
        }
        return true;
    }

    public static boolean validateFirstLine(ArrayList<String[]> data){
        String[] firstLine = data.get(0);
        if(firstLine[0].equalsIgnoreCase("#start")){
            return true;
        }
        else {
            ApplicationManager.displayPopUp("Invalid Data", "Invalid first line detected!", "error");
            System.out.println("Invalid first line detected!");
            return false;
        }
    }

    public static boolean validateNotEmpty(ArrayList<String[]> data){
        if(data.size() > 0){
            return true;
        }
        else {
            ApplicationManager.displayPopUp("Invalid Data", "Empty CSV file detected!", "error");
            System.out.println("Empty csv file detected!");
            return false;
        }
    }

    public static boolean validateLongitude(String inLongitude){
        if(!GeneralUtilities.isValidDouble(inLongitude)){
            ApplicationManager.displayPopUp("Invalid Data", "Make sure longitude is a numeric value!", "error");
            System.out.println("Invalid longitude detected!");
            return false;
        }
        else{
            double longitude = Double.parseDouble(inLongitude);
            if(!(longitude >= -180 && longitude <= 180)){
                ApplicationManager.displayPopUp("Invalid Data", "Make sure longitude is in range (-180 to 180)!", "error");
                System.out.println("Invalid longitude detected! Longitude out of range.");
                return false;
            }
            else{
                return true;
            }
        }

    }

    public static boolean validateLatitude(String inLatitude){
        if(!GeneralUtilities.isValidDouble(inLatitude)){
            ApplicationManager.displayPopUp("Invalid Data", "Make sure latitude is a numeric value!", "error");
            System.out.println("Invalid latitude detected!");
            return false;
        }
        else{
            double latitude = Double.parseDouble(inLatitude);
            if(!(latitude >= -90 && latitude <= 90)){
                System.out.println("Invalid latitude detected! Latitude out of range.");
                ApplicationManager.displayPopUp("Invalid Data", "Make sure latitude is in range (-90 to 90)!", "error");
                return false;
            }
            else{
                return true;
            }
        }
    }

    public static boolean validateDistance(double distance){
        if(!(distance >= 0 && distance <= 1000)){
            System.out.println("Invalid distance detected! Distance out of range (maximum 1000 km!).");
            ApplicationManager.displayPopUp("Invalid Data", "Make sure distance is in range (0 to 1000)!", "error");
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean validateHeartRate(String inHeartRate){
        if(!GeneralUtilities.isValidInt(inHeartRate)){
            ApplicationManager.displayPopUp("Invalid Data", "Make sure heart rate is a numeric value!", "error");
            System.out.println("Invalid heart rate detected!");
            return false;
        }
        else{
            int heartRate = Integer.parseInt(inHeartRate);
            if(!(heartRate >= 0 && heartRate <= 400)){
                System.out.println("Invalid heart rate detected! Heart rate out of range.");
                ApplicationManager.displayPopUp("Invalid Data", "Make sure heart rate is in range (0 to 400)!", "error");
                return false;
            }
            else{
                return true;
            }
        }
    }

    public static boolean validateElevation(String inElevation){
        if(!GeneralUtilities.isValidDouble(inElevation)){
            ApplicationManager.displayPopUp("Invalid Data", "Make sure elevation is a numeric value!", "error");
            System.out.println("Invalid elevation detected!");
            return false;
        }
        else{
            double elevation = Double.parseDouble(inElevation);
            if(!(elevation >= -420 && elevation <= 9000)){
                ApplicationManager.displayPopUp("Invalid Data", "Make sure elevation is in range (-420 to 9000)!", "error");
                System.out.println("Invalid elevation detected! Elevation out of range.");
                return false;
            }
            else{
                return true;
            }
        }
    }

    public static boolean validateDate(String date){
        if(!GeneralUtilities.isValidDate(date)){
            System.out.println("Invalid date detected!");
            ApplicationManager.displayPopUp("Invalid Data", "Make sure date is of the form DD/MM/YY!", "error");
            return false;
        }
        else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            LocalDate newDate = LocalDate.parse(date, formatter);
            if(newDate.isAfter(LocalDate.now())){
                ApplicationManager.displayPopUp("Invalid Data", "Future date detected!", "error");
                System.out.println("Future date detected!");
                return false;
            }
            else{
                return true;
            }
        }
    }

    public static boolean validateDateWithFormat(String date){
        if(!GeneralUtilities.isValidDateWithFormat(date)){
            System.out.println("Invalid date detected!");
            ApplicationManager.displayPopUp("Invalid Data", "Make sure date is of the form DD/MM/YY!", "error");
            return false;
        }
        else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            LocalDate newDate = LocalDate.parse(date, formatter);
            if(newDate.isAfter(LocalDate.now())){
                ApplicationManager.displayPopUp("Invalid Data", "Future date detected!", "error");
                System.out.println("Future date detected!");
                return false;
            }
            else{
                return true;
            }
        }
    }

    public static boolean validateTime(String time){
        if(!GeneralUtilities.isValidTime(time)){
            ApplicationManager.displayPopUp("Invalid Data", "Make sure time is of the form HH:mm:ss!", "error");
            System.out.println("Invalid time detected!");
            return false;
        }
        else{
            return true;
        }
    }
    public static boolean validateStartEndDate(LocalDate startDate, LocalDate endDate){
        if(!(startDate.isBefore(endDate) || startDate.isEqual(endDate))){
            ApplicationManager.displayPopUp("Invalid Data", "Make sure start date is before end date!", "error");
            System.out.println("Invalid date detected!");
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Validates if the given record is within the start and end date times of another activity.
     * @param data A record in a csv file
     * @return True if the record is not a duplicate, false otherwise.
     * @throws SQLException
     */
    public static boolean validateNonDuplicateData(ArrayList<String[]> data) throws SQLException {
        ArrayList<Activity> activities = ApplicationManager.getDatabaseManager().getActivities(ApplicationManager.getCurrentUserID());
        //ArrayList<String> duplicateList = new ArrayList<>();
        for (String[] line : data){
            if(!line[0].equalsIgnoreCase("#start")){
                //duplicateList.add(line[0] + " " + line[1]);
                for (Activity activity : activities){
                    LocalTime recordTime = LocalTime.parse(line[1]);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                    LocalDate recordDate = LocalDate.parse(line[0], formatter);
                    if(recordTime.isAfter(activity.getStartTime()) && recordTime.isBefore(activity.getEndTime())
                        && (recordDate.isEqual(activity.getStartDate()) || recordDate.isEqual(activity.getEndDate()))){
                        ApplicationManager.displayPopUp("Invalid Data", "Duplicate activity data detected!", "error");
                        System.out.println("Duplicate data detected!");
                        return false;
                    }
                }
            }
        }
        /*
        if(!GeneralUtilities.hasNoDuplicates(duplicateList)){
            ApplicationManager.displayPopUp("Invalid Data", "Duplicate activity records detected!", "error");
            System.out.println("Duplicate records detected!");
            return false;
        }
        else {
        */
            return true;
        //}
    }
    public static boolean validateNonDuplicateActivity(LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate) throws SQLException {
        ArrayList<Activity> activities = ApplicationManager.getDatabaseManager().getActivities(ApplicationManager.getCurrentUserID());
                for (Activity activity : activities){
                    if(startTime.isAfter(activity.getStartTime()) && endTime.isBefore(activity.getEndTime())
                            && (startDate.isEqual(activity.getStartDate()) || endDate.isEqual(activity.getEndDate()))){
                        ApplicationManager.displayPopUp("Invalid Data", "Duplicate activity data detected!", "error");
                        System.out.println("Duplicate data detected!");
                        return false;
                    }
                }
        return true;
    }
}
