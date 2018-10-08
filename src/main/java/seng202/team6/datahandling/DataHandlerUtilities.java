package seng202.team6.datahandling;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DataHandlerUtilities handle all the utilities for the Database.
 */

public class DataHandlerUtilities {


    /**
     * Adds padded 0's to the date if required.
     * @param date The date to fix up.
     * @return The date with relevant padded 0.
     */
    public static LocalDate parseDate(String date) {
        String[] dateBits = date.split("-");
        if (dateBits[1].length() == 1) {
            dateBits[1] = "0" + dateBits[1];
        } else if (dateBits[2].length() == 1) {
            dateBits[2] = "0" + dateBits[2];
        }

        String dateFull = String.join("-", dateBits);
        return LocalDate.parse(dateFull);
    }

    /**
     * Adds padded 0's to the time if required.
     * @param time The date to fix up.
     * @return The time with relevant padded 0.
     */
    public static LocalTime parseTime(String time) {
        if(time.length() == 7){
            time =  "0" + time;
        }
        return LocalTime.parse(time);
    }

}
