package seng202.team6.utilities;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GeneralUtilities {


    // Code for internet for testing the database output. Note: TEMPORARY
    final public static void printResultSet(ResultSet rs) throws SQLException
    {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(" | ");
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
    }

    public static void writeToErrorFile(Exception e) {
        String errorToWrite = e.toString();

    }

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate);
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static boolean isValidTime(String inTime) {
        //SimpleDateFormat dateFormat = new DateTimeFormatter("dd/MM/yyyy");
        DateTimeFormatter strictTimeFormatter = DateTimeFormatter.ofPattern("H:mm:ss")
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalTime.parse(inTime, strictTimeFormatter);
        } catch (DateTimeParseException pe) {
            return false;
        }
        return true;
    }
    public static boolean isValidInt(String inInt) {
        try {
            Integer.parseInt(inInt);
        } catch (NumberFormatException pe) {
            return false;
        }
        return true;
    }
    public static boolean isValidDouble(String inDouble) {
        try {
            Double.parseDouble(inDouble);
        } catch (NumberFormatException pe) {
            return false;
        }
        return true;
    }
    public static boolean hasNoDuplicates(ArrayList<String> listContainingDuplicates) {
        final Set<String> set1 = new HashSet<>();
        for (String yourInt : listContainingDuplicates) {
            if (!set1.add(yourInt)) {
                return false;
            }
        }
        return true;
    }
}
