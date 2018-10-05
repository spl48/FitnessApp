package seng202.team6.datahandling;

import com.opencsv.CSVReader;
import seng202.team6.controller.ApplicationManager;
import seng202.team6.controller.LoadingBoxController;
import seng202.team6.models.Activity;
import seng202.team6.utilities.DatabaseValidation;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FileDataLoader {
    int FIRST_ENTRY = 0;
    /*
    public static void main(String[] args){
        try {
            //Change this to local at some point
            CSVReader reader = new CSVReader(new FileReader("c:/Users/Gavin/Desktop/SENG202/sample_data.csv"));
            String[]nextline;
            while((nextline = reader.readNext()) != null){
                if(nextline != null)
                {
                    System.out.println(Arrays.toString(nextline));
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        System.out.println("CSV read complete");
    }
    */

    public boolean importDataFromCSV(int userid, String CSVLocation, DatabaseManager databaseManager) throws IOException, SQLException {
        int numLines = countLines(CSVLocation);
        System.out.println("Num Lines "+ numLines);
        LoadingBoxController loadBox = new LoadingBoxController();
        loadBox.setMaximum(numLines);
        //loadBox.display();
            //Change this to local at some point
        try {
            CSVReader reader = new CSVReader(new FileReader(CSVLocation));
            String[] nextLine;
            String[] previousLine = {};
            String activityDescription;
            String activityStartDate;
            String activityStartTime;
            String activityEndDate;
            String activityEndTime;
            String start;
            String end;
            ArrayList<String[]> rawData = new ArrayList<>();
            while ((nextLine = reader.readNext()) != null) {
                rawData.add(nextLine);
            }
            if (!DatabaseValidation.validate(rawData)) {
                System.out.println("validation failed");
                return false;
            } else {
                databaseManager.getCon().setAutoCommit(false);
                System.out.println("validation passed");
                int activityid = 0;
                nextLine = rawData.get(0);
                activityDescription = nextLine[1];
                if ((rawData.size() > 1)) {
                    nextLine = rawData.get(1);
                    activityStartDate = nextLine[0];
                    activityStartTime = nextLine[1];
                    start = convertToDateTimeFormat(activityStartDate, activityStartTime);
                    String sqlprep1 = "INSERT INTO activity(userid,description,start,workout) VALUES(?,?,?,?)";
                    PreparedStatement prep = databaseManager.getCon().prepareStatement(sqlprep1, Statement.RETURN_GENERATED_KEYS);
                    prep.setInt(1, userid);
                    prep.setString(2, activityDescription);
                    prep.setString(3, start);
                    prep.setString(4, Activity.getActivityType(activityDescription));
                    prep.execute();
                    ResultSet generatedKeys = prep.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        activityid = generatedKeys.getInt(1);
                        //System.out.println(activityid);
                    }
                    previousLine = nextLine;
                }
                for (int x = 2; x < rawData.size(); x++) {
                    nextLine = rawData.get(x);
                    if (nextLine != null) {
                        if (nextLine[0].equalsIgnoreCase("#start")) {
                            activityEndDate = previousLine[0];
                            activityEndTime = previousLine[1];
                            end = convertToDateTimeFormat(activityEndDate, activityEndTime);
                            String sql = "UPDATE activity SET end = ? WHERE activityid = " + activityid;
                            PreparedStatement updateEnd = databaseManager.getCon().prepareStatement(sql);
                            updateEnd.setString(1, end);
                            updateEnd.execute();
                            activityDescription = nextLine[1];
                            //loadBox.updateLoadingProgress(lineCount);
                            if ((x = x + 1) < rawData.size()) {
                                nextLine = rawData.get(x);
                                activityStartDate = nextLine[0];
                                activityStartTime = nextLine[1];
                                start = convertToDateTimeFormat(activityStartDate, activityStartTime);
                                String sqlprep1 = "INSERT INTO activity(userid,description,start,workout) VALUES(?,?,?,?)";
                                PreparedStatement prep = databaseManager.getCon().prepareStatement(sqlprep1, Statement.RETURN_GENERATED_KEYS);
                                prep.setInt(1, userid);
                                prep.setString(2, activityDescription);
                                prep.setString(3, start);
                                prep.setString(4, Activity.getActivityType(activityDescription));
                                prep.execute();
                                ResultSet generatedKeys = prep.getGeneratedKeys();
                                if (generatedKeys.next()) {
                                    activityid = generatedKeys.getInt(1);
                                    System.out.println(activityid);
                                }
                                previousLine = nextLine;
                            }
                        } else {
                            String dateTime = convertToDateTimeFormat(nextLine[0], nextLine[1]);
                            String sql = "INSERT INTO record(activityid,datetime,heartrate,latitude,longitude,elevation) VALUES(?,?,?,?,?,?)";
                            PreparedStatement insertRecord = databaseManager.getCon().prepareStatement(sql);
                            insertRecord.setInt(1, activityid);
                            insertRecord.setString(2, dateTime);
                            insertRecord.setDouble(3, Double.parseDouble(nextLine[2]));
                            insertRecord.setDouble(4, Double.parseDouble(nextLine[3]));
                            insertRecord.setDouble(5, Double.parseDouble(nextLine[4]));
                            insertRecord.setDouble(6, Double.parseDouble(nextLine[5]));
                            insertRecord.execute();
                            previousLine = nextLine;
                        }
                        //if(nextLine[0])
                    }

                }
                activityEndDate = previousLine[0];
                activityEndTime = previousLine[1];
                end = convertToDateTimeFormat(activityEndDate, activityEndTime);
                String sql = "UPDATE activity SET end = ? WHERE activityid = " + activityid;
                PreparedStatement updateEnd = databaseManager.getCon().prepareStatement(sql);
                updateEnd.setString(1, end);
                updateEnd.execute();
                databaseManager.getCon().commit();
                databaseManager.getCon().setAutoCommit(true);
                return true;
            }
        } catch (Exception e) {
                try{
                    if (databaseManager.getCon() != null) {
                        databaseManager.getCon().rollback();
                    }
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            e.printStackTrace();
            return false;
            //ApplicationManager.displayPopUp("Database Error" , "Could not load csv into database", "error");
        }
    }

    public String convertToDateTimeFormat(String date, String time) {
        String[] parts = date.split("/");
        String combined = parts[2] + "-" + parts[1] + "-" + parts[0] + "T" + time;
        return combined;
    }

    public int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();

        }
    }
}
