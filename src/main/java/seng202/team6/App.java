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
    public static void main( String[] args ) throws ClassNotFoundException, SQLException
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
        loader.importDataFromCSV(1, "C:\\Users\\dhlam\\OneDrive - University of Canterbury\\2018\\Semester 2\\SENG202\\Deliverable 1\\Sample Data.csv", test);
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
