package seng202.team6.datahandling;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

public class FileDataLoader implements DataLoader {
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

    public void importDataFromCSV(int userid, String CSVLocation, DatabaseManager databaseManager){

        System.out.println("Here");
        try {
            //Change this to local at some point
            CSVReader reader = new CSVReader(new FileReader(CSVLocation));
            String[]nextLine;
            String[]previousLine = {};
            String activityDescription;
            String activityStartDate;
            String activityStartTime;
            String activityEndDate;
            String activityEndTime;
            String start;
            String end;
            int activityid = 0;
            if((nextLine = reader.readNext()) != null){
                activityDescription = nextLine[1];
                if((nextLine = reader.readNext()) != null){
                    activityStartDate = nextLine[0];
                    activityStartTime = nextLine[1];
                    start = convertToDateTimeFormat(activityStartDate, activityStartTime);
                    String sqlprep1 = "INSERT INTO activity(userid,description,start,workout) VALUES(?,?,?,?)";
                    PreparedStatement prep = databaseManager.getCon().prepareStatement(sqlprep1, Statement.RETURN_GENERATED_KEYS);
                    prep.setInt(1, userid);
                    prep.setString(2, activityDescription);
                    prep.setString(3, start);
                    prep.setString(4, "Other");
                    prep.execute();
                    ResultSet generatedKeys = prep.getGeneratedKeys();
                    if(generatedKeys.next()){
                        activityid = generatedKeys.getInt(1);
                        System.out.println(activityid);
                    }
                    previousLine = nextLine;
                }
            }
            while((nextLine = reader.readNext()) != null){
                if(nextLine != null)
                {
                    //System.out.println(Arrays.toString(nextLine));
                    if(nextLine[0].equalsIgnoreCase("#start")){
                        activityEndDate = previousLine[0];
                        activityEndTime = previousLine[1];
                        end = convertToDateTimeFormat(activityEndDate, activityEndTime);
                        String sql = "UPDATE activity SET end = ? WHERE activityid = " + activityid;
                        PreparedStatement updateEnd = databaseManager.getCon().prepareStatement(sql);
                        updateEnd.setString(1, end);
                        updateEnd.execute();
                        activityDescription = nextLine[1];
                        if((nextLine = reader.readNext()) != null){
                            activityStartDate = nextLine[0];
                            activityStartTime = nextLine[1];
                            start = convertToDateTimeFormat(activityStartDate, activityStartTime);
                            String sqlprep1 = "INSERT INTO activity(userid,description,start,workout) VALUES(?,?,?,?)";
                            PreparedStatement prep = databaseManager.getCon().prepareStatement(sqlprep1, Statement.RETURN_GENERATED_KEYS);
                            prep.setInt(1, userid);
                            prep.setString(2, activityDescription);
                            prep.setString(3, start);
                            prep.setString(4, "Other");
                            prep.execute();
                            ResultSet generatedKeys = prep.getGeneratedKeys();
                            if(generatedKeys.next()){
                                activityid = generatedKeys.getInt(1);
                                System.out.println(activityid);
                            }
                            previousLine = nextLine;
                        }
                    }
                    else{
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
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("CSV read complete");
    }

    public String convertToDateTimeFormat(String date, String time){
        String[] parts = date.split("/");
        String combined = parts[2] + "-" + parts[1] + "-" + parts[0] + "T" + time;
        return combined;
    }
}
