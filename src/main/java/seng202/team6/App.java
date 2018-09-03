package seng202.team6;

import seng202.team6.datahandling.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println("Testing Testing");
        System.out.println("lucy Test");
        System.out.println("rion test");
        System.out.println("Holdiday test push to see if VSCode breaks build");
        FileDataLoader loader = new FileDataLoader();
        DatabaseManager test = new DatabaseManager();
        ResultSet rs;
        try {
            rs = test.displayUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loader.importDataFromCSV(1, "c:/Users/Gavin/Desktop/SENG202/sample_data.csv", test);
        ResultSet rsa;
        ResultSet rsaa;
        try {
            rsa = test.displayActivities();
            while(rsa.next()) {
                System.out.println(rsa.getInt("activityid") + " " + rsa.getString("description") + " " + rsa.getString("start")+ " " + rsa.getString("end"));
            }
            rsaa = test.displayRecords();
            while(rsaa.next()) {
                System.out.println(rsaa.getInt("activityid") + " " + rsaa.getString("datetime") + " " + rsaa.getDouble("longitude")+ " " + rsaa.getDouble("latitude"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
