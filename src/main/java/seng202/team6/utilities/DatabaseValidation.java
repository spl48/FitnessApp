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
            if(line.length != 6){
                System.out.println("Invalid line length detected!");
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
            System.out.println("Invalid first line detected!");
            return false;
        }
    }

    public static boolean validateNotEmpty(ArrayList<String[]> data){
        if(data.size() > 0){
            return true;
        }
        else {
            System.out.println("Empty csv file detected!");
            return false;
        }
    }

    public static boolean validateLongitude(String inLongitude){
        if(!GeneralUtilities.isValidDouble(inLongitude)){
            System.out.println("Invalid longitude detected!");
            return false;
        }
        else{
            double longitude = Double.parseDouble(inLongitude);
            if(!(longitude >= -180 && longitude <= 180)){
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
            System.out.println("Invalid longitude detected!");
            return false;
        }
        else{
            double latitude = Double.parseDouble(inLatitude);
            if(!(latitude >= -90 && latitude <= 90)){
                System.out.println("Invalid latitude detected! Latitude out of range.");
                return false;
            }
            else{
                return true;
            }
        }
    }
    public static boolean validateHeartRate(String inHeartRate){
        if(!GeneralUtilities.isValidInt(inHeartRate)){
            System.out.println("Invalid heart rate detected!");
            return false;
        }
        else{
            int heartRate = Integer.parseInt(inHeartRate);
            if(!(heartRate >= 0 && heartRate <= 400)){
                System.out.println("Invalid heart rate detected! Heart rate out of range.");
                return false;
            }
            else{
                return true;
            }
        }
    }

    public static boolean validateElevation(String inElevation){
        if(!GeneralUtilities.isValidDouble(inElevation)){
            System.out.println("Invalid elevation detected!");
            return false;
        }
        else{
            double elevation = Double.parseDouble(inElevation);
            if(!(elevation >= -420 && elevation <= 9000)){
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
            return false;
        }
        else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            LocalDate newDate = LocalDate.parse(date, formatter);
            if(newDate.isAfter(LocalDate.now())){
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
            System.out.println("Invalid time detected!");
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean validateNonDuplicateData(ArrayList<String[]> data) throws SQLException {
        ArrayList<Activity> activities = ApplicationManager.getDatabaseManager().getActivities(ApplicationManager.getCurrentUserID());
        for (String[] line : data){
            if(!line[0].equalsIgnoreCase("#start")){
                for (Activity activity : activities){
                    LocalTime recordTime = LocalTime.parse(line[1]);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                    LocalDate recordDate = LocalDate.parse(line[0], formatter);
                    if(recordTime.isAfter(activity.getStartTime()) && recordTime.isBefore(activity.getEndTime())
                        && (recordDate.isEqual(activity.getStartDate()) || recordDate.isEqual(activity.getEndDate()))){
                        System.out.println("Duplicate data detected!");
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
