package seng202.team6.utilities;

import com.opencsv.CSVReader;
import junit.framework.TestCase;
import seng202.team6.datahandling.DatabaseManager;
import seng202.team6.datahandling.FileDataLoader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DatabaseValidationTest extends TestCase {

    CSVReader reader;
    ArrayList<String[]> corruptedFieldData = new ArrayList<>();
    ArrayList<String[]> emptyCsvData = new ArrayList<>();
    ArrayList<String[]> futureDateData = new ArrayList<>();
    ArrayList<String[]> invalidActivityData = new ArrayList<>();
    ArrayList<String[]> invalidDateData = new ArrayList<>();
    ArrayList<String[]> invalidElevationData = new ArrayList<>();
    ArrayList<String[]> invalidFirstLineData = new ArrayList<>();
    ArrayList<String[]> invalidHeartRateData = new ArrayList<>();
    ArrayList<String[]> invalidLatitudeData = new ArrayList<>();
    ArrayList<String[]> invalidLongitudeData = new ArrayList<>();
    ArrayList<String[]> invalidTimeData = new ArrayList<>();
    ArrayList<String[]> missingFieldData = new ArrayList<>();
    ArrayList<String[]> validData = new ArrayList<>();
    int DATE_INDEX = 0;
    int TIME_INDEX = 1;
    int HEARTRATE_INDEX = 2;
    int LATITUDE_INDEX = 3;
    int LONGITUDE_INDEX = 4;
    int ELEVATION_INDEX = 5;

    public DatabaseValidationTest(){
        String[] nextLine;
        try {
            reader = new CSVReader(new FileReader("src/main/resources/seng202/team6/resources/tests/corrupted_field_test.csv"));
            while ((nextLine = reader.readNext()) != null) {
                corruptedFieldData.add(nextLine);
            }
            reader = new CSVReader(new FileReader("src/main/resources/seng202/team6/resources/tests/empty_csv_test.csv"));
            while ((nextLine = reader.readNext()) != null) {
                emptyCsvData.add(nextLine);
            }
            reader = new CSVReader(new FileReader("src/main/resources/seng202/team6/resources/tests/future_date_test.csv"));
            while ((nextLine = reader.readNext()) != null) {
                futureDateData.add(nextLine);
            }
            reader = new CSVReader(new FileReader("src/main/resources/seng202/team6/resources/tests/invalid_activity_line_test.csv"));
            while ((nextLine = reader.readNext()) != null) {
                invalidActivityData.add(nextLine);
            }
            reader = new CSVReader(new FileReader("src/main/resources/seng202/team6/resources/tests/invalid_date_test.csv"));
            while ((nextLine = reader.readNext()) != null) {
                invalidDateData.add(nextLine);
            }
            reader = new CSVReader(new FileReader("src/main/resources/seng202/team6/resources/tests/invalid_elevation_test.csv"));
            while ((nextLine = reader.readNext()) != null) {
                invalidElevationData.add(nextLine);
            }
            reader = new CSVReader(new FileReader("src/main/resources/seng202/team6/resources/tests/invalid_first_line_test.csv"));
            while ((nextLine = reader.readNext()) != null) {
                invalidFirstLineData.add(nextLine);
            }
            reader = new CSVReader(new FileReader("src/main/resources/seng202/team6/resources/tests/invalid_heartrate_test.csv"));
            while ((nextLine = reader.readNext()) != null) {
                invalidHeartRateData.add(nextLine);
            }
            reader = new CSVReader(new FileReader("src/main/resources/seng202/team6/resources/tests/invalid_latitude_test.csv"));
            while ((nextLine = reader.readNext()) != null) {
                invalidLatitudeData.add(nextLine);
            }
            reader = new CSVReader(new FileReader("src/main/resources/seng202/team6/resources/tests/invalid_longitude_test.csv"));
            while ((nextLine = reader.readNext()) != null) {
                invalidLongitudeData.add(nextLine);
            }
            reader = new CSVReader(new FileReader("src/main/resources/seng202/team6/resources/tests/invalid_time_test.csv"));
            while ((nextLine = reader.readNext()) != null) {
                invalidTimeData.add(nextLine);
            }
            reader = new CSVReader(new FileReader("src/main/resources/seng202/team6/resources/tests/missing_field_test.csv"));
            while ((nextLine = reader.readNext()) != null) {
                missingFieldData.add(nextLine);
            }
            reader = new CSVReader(new FileReader("src/main/resources/seng202/team6/resources/tests/valid_data_test.csv"));
            while ((nextLine = reader.readNext()) != null) {
                validData.add(nextLine);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testValidate() {
        DatabaseManager databaseManager = new DatabaseManager("test");
        Connection con = databaseManager.getCon();
        try {
            con.setAutoCommit(false);
            Statement userTableStatement = con.createStatement();
            String userTablesql = "DELETE FROM user";
            userTableStatement.execute(userTablesql);
            Statement activityTableStatement = con.createStatement();
            String activityTablesql = "DELETE FROM activity";
            activityTableStatement.execute(activityTablesql);
            Statement recordTableStatement = con.createStatement();
            String recordTablesql = "DELETE FROM record";
            recordTableStatement.execute(recordTablesql);
            con.commit();
            con.setAutoCommit(true);
            assertTrue(DatabaseValidation.validate(validData,databaseManager));
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testValidateLineLength() {
        assertFalse(DatabaseValidation.validateLineLength(missingFieldData));
    }

    public void testValidateFirstLine() {
        assertFalse(DatabaseValidation.validateFirstLine(invalidFirstLineData));
    }

    public void testValidateNotEmpty() {
        assertFalse(DatabaseValidation.validateNotEmpty(emptyCsvData));
    }

    public void testValidateLongitude() {
        String[] validLine = invalidLongitudeData.get(1);
        String[] invalidLine = invalidLongitudeData.get(5);
        assertTrue(DatabaseValidation.validateLongitude(validLine[LONGITUDE_INDEX]));
        assertFalse(DatabaseValidation.validateLongitude(invalidLine[LONGITUDE_INDEX]));
    }

    public void testValidateLatitude() {
        String[] validLine = invalidLatitudeData.get(1);
        String[] invalidLine = invalidLatitudeData.get(7);
        assertTrue(DatabaseValidation.validateLatitude(validLine[LATITUDE_INDEX]));
        assertFalse(DatabaseValidation.validateLatitude(invalidLine[LATITUDE_INDEX]));
        String[] corruptedLatitudeLine = corruptedFieldData.get(5);
        assertFalse(DatabaseValidation.validateLatitude(corruptedLatitudeLine[LATITUDE_INDEX]));
    }

    public void testValidateHeartRate() {
        String[] validLine = invalidHeartRateData.get(1);
        String[] invalidLine = invalidHeartRateData.get(7);
        assertTrue(DatabaseValidation.validateHeartRate(validLine[HEARTRATE_INDEX]));
        assertFalse(DatabaseValidation.validateHeartRate(invalidLine[HEARTRATE_INDEX]));
    }

    public void testValidateElevation() {
        String[] validLine = invalidElevationData.get(1);
        String[] invalidLine = invalidElevationData.get(6);
        assertTrue(DatabaseValidation.validateElevation(validLine[ELEVATION_INDEX]));
        assertFalse(DatabaseValidation.validateElevation(invalidLine[ELEVATION_INDEX]));
    }

    public void testValidateDate() {
        String[] validLine = futureDateData.get(1);
        String[] invalidLine = futureDateData.get(7);
        assertTrue(DatabaseValidation.validateDate(validLine[DATE_INDEX]));
        assertFalse(DatabaseValidation.validateDate(invalidLine[DATE_INDEX]));
        validLine = invalidDateData.get(1);
        invalidLine = invalidDateData.get(7);
        assertTrue(DatabaseValidation.validateDate(validLine[DATE_INDEX]));
        assertFalse(DatabaseValidation.validateDate(invalidLine[DATE_INDEX]));
    }

    public void testValidateDateWithFormat() {
        String invalidDate = "13/05/2015";
        String invalidDate2 = "32/13/2015";
        String invalidDate3 = "2025-05-13";
        String validDate = "2015-05-13";
        assertTrue(DatabaseValidation.validateDateWithFormat(validDate));
        assertFalse(DatabaseValidation.validateDateWithFormat(invalidDate));
        assertFalse(DatabaseValidation.validateDateWithFormat(invalidDate2));
        assertFalse(DatabaseValidation.validateDateWithFormat(invalidDate3));
    }

    public void testValidateTime() {
        String[] validLine = invalidTimeData.get(1);
        String[] invalidLine = invalidTimeData.get(4);
        assertTrue(DatabaseValidation.validateTime(validLine[TIME_INDEX]));
        assertFalse(DatabaseValidation.validateTime(invalidLine[TIME_INDEX]));
    }

    public void testValidateStartEndDate() {
        LocalDate beforeDate = LocalDate.of(2018, 9, 28);
        LocalDate afterDate = LocalDate.of(2018, 9, 29);
        assertTrue(DatabaseValidation.validateStartEndDate(beforeDate, afterDate));
        assertFalse(DatabaseValidation.validateStartEndDate(afterDate, beforeDate));
    }

    public void testValidateStartEndTime() {
        String beforeTime = "10:15:28";
        String afterTime = "11:09:23";
        LocalDate beforeDate = LocalDate.of(2018, 9, 28);
        LocalDate afterDate = LocalDate.of(2018, 9, 29);
        assertTrue(DatabaseValidation.validateStartEndTime(beforeTime, afterTime,beforeDate,beforeDate));
        assertFalse(DatabaseValidation.validateStartEndTime(afterTime, beforeTime,beforeDate,beforeDate));
        assertTrue(DatabaseValidation.validateStartEndTime(afterTime, beforeTime,beforeDate,afterDate));
    }

    public void testValidateNonDuplicateData() {
        FileDataLoader dataLoader = new FileDataLoader();
        DatabaseManager databaseManager = new DatabaseManager("test");
        Connection con = databaseManager.getCon();
        try {
            con.setAutoCommit(false);
            Statement userTableStatement = con.createStatement();
            String userTablesql = "DELETE FROM user";
            userTableStatement.execute(userTablesql);
            Statement activityTableStatement = con.createStatement();
            String activityTablesql = "DELETE FROM activity";
            activityTableStatement.execute(activityTablesql);
            Statement recordTableStatement = con.createStatement();
            String recordTablesql = "DELETE FROM record";
            recordTableStatement.execute(recordTablesql);
            con.commit();
            con.setAutoCommit(true);
            assertTrue(DatabaseValidation.validateNonDuplicateData(validData, databaseManager));
            dataLoader.importDataFromCSV(0, "src/main/resources/seng202/team6/resources/tests/valid_data_test.csv",databaseManager);
            assertFalse(DatabaseValidation.validateNonDuplicateData(validData, databaseManager));
            con.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testValidateNonDuplicateActivity() {
        DatabaseManager databaseManager = new DatabaseManager("test");
        Connection con = databaseManager.getCon();
        FileDataLoader dataLoader = new FileDataLoader();
        try {
            con.setAutoCommit(false);
            Statement userTableStatement = con.createStatement();
            String userTablesql = "DELETE FROM user";
            userTableStatement.execute(userTablesql);
            Statement activityTableStatement = con.createStatement();
            String activityTablesql = "DELETE FROM activity";
            activityTableStatement.execute(activityTablesql);
            Statement recordTableStatement = con.createStatement();
            String recordTablesql = "DELETE FROM record";
            recordTableStatement.execute(recordTablesql);
            con.commit();
            con.setAutoCommit(true);
            assertTrue(DatabaseValidation.validateNonDuplicateData(validData, databaseManager));
            dataLoader.importDataFromCSV(0, "src/main/resources/seng202/team6/resources/tests/valid_data_test.csv",databaseManager);
            LocalDate testInvalidStartDate = LocalDate.of(2015, 4, 10);
            LocalDate testInvalidEndDate = LocalDate.of(2015, 4, 10);
            LocalTime testInvalidEndTime = LocalTime.of(23,50,30);
            LocalDate testValidStartDate = LocalDate.of(2015, 4, 11);
            LocalDate testValidEndDate = LocalDate.of(2015, 4, 11);
            LocalTime testValidEndTime = LocalTime.of(23,50,30);
            assertTrue(DatabaseValidation.validateNonDuplicateActivity(testValidEndTime,testValidStartDate,testValidEndDate,databaseManager));
            assertFalse(DatabaseValidation.validateNonDuplicateActivity(testInvalidEndTime,testInvalidStartDate,testInvalidEndDate,databaseManager));
            con.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}