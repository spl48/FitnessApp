//package seng202.team6.analysis;
//
//
//import seng202.team6.models.Activity;
//import seng202.team6.models.User;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.time.temporal.TemporalField;
//import java.time.temporal.WeekFields;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Locale;
//
///**
// * This class analyses a users profile. With methods that
// * analyse their BMI and overall step count
// */
//public class ProfileAnalysis {
//
//    /**
//     * A function that returns the body mass index of a user.
//     * @param user the user for which the BMI is being calculated.
//     * @return a double that is a users BMI.
//     */
//    public static double calculateBMI(User user) {
//        double weight = user.getWeight();
//        double height = user.getHeight() / 100;
//
//        return weight / (height * height);
//    }
//
//    /** A function that determines a users weight category,
//     * from there BMI.
//     * @param BMI the BMI of the user
//     * @return a string of the category for the users weight
//     */
//    public static String analyseBMI(double BMI) {
//        if (BMI < 18.5) {
//            return "UnderWeight";
//        } else if (BMI < 24.9) {
//            return "Healthy Weight";
//        } else if (BMI < 29.9) {
//            return "OverWeight";
//        } else {
//            return "Obese";
//        }
//    }

//    /** A function that determines the total steps across all
//     * walking and running activities of a user within the current week.
//     * @param activities an ArrayList of activities to be analysed
//     * @param strideLength the stride length of a user in feet
//     * @return a double for the number of steps taken
//     */
//    public static double findStepsThisWeek(ArrayList<Activity> activities, double strideLength) {
//        double totalStepCount = 0;
//        double currentStepCount;
//        LocalDate currentDate = LocalDate.now();
//        TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek();
//        LocalDate startOfWeek = currentDate.with(fieldISO, 1);
//        for (int i = activities.size() - 1; i >= 0 && activities.get(i).getStartDate().isAfter(startOfWeek); i--) {
//            Activity activity = activities.get(i);
//            currentStepCount = activity.findStepCount(strideLength);          // Finds the step count for 1 activity
//            totalStepCount += currentStepCount;
//        }
//        return totalStepCount;
//    }
//
//}


//
//    }
//
//
//    /**
//     * A function which calculates and returns a users maximum heart rate through all logged runs, walks and cycles.
//     * @param profile The profile of a user for which the maximum heart rate is being calculated.
//     * @return A integer that is the maximum heart rate of a user.
//     */
//    public int findMaximumHeartRate(Profile profile) {
//        int maxHeartRate = 0;
//        int currentHeartRate;
//
//         ArrayList<Activity> runningData = profile.getRunningData().getActivities();
//         ArrayList<Activity> walkingData = profile.getWalkingData().getActivities();
//         ArrayList<Activity> cyclingData = profile.getBikingData().getActivities();
//
//         for (Activity run : runningData) {
//            currentHeartRate = run.getMaxHeartRate();
//            if (currentHeartRate > maxHeartRate) {
//                maxHeartRate = currentHeartRate;
//            }
//         }
//
//         for (Activity walk : walkingData) {
//            currentHeartRate = walk.getMaxHeartRate();
//            if (currentHeartRate > maxHeartRate) {
//                maxHeartRate = currentHeartRate;
//            }
//         }
//
//         for (Activity cycle : cyclingData) {
//            currentHeartRate = cycle.getMaxHeartRate();
//            if (currentHeartRate > maxHeartRate) {
//                maxHeartRate = currentHeartRate;
//            }
//         }
//
//        return maxHeartRate;
//    }
//
//    /**
//     * A function which calculates and returns a users maximum heart rate through all logged runs, walks and cycles.
//     * @param profile The profile of a user for which the maximum heart rate is being calculated.
//     * @return A integer that is the maximum heart rate of a user.
//     */
//    public int findMinimumHeartRate(Profile profile) {
//        int minHeartRate = 0;
//        int currentHeartRate;
//
//         ArrayList<Activity> runningData = profile.getRunningData().getActivities();
//         ArrayList<Activity> walkingData = profile.getWalkingData().getActivities();
//         ArrayList<Activity> cyclingData = profile.getBikingData().getActivities();
//
//         minHeartRate = runningData.get(0).getMinHeartRate();
//
//         for (Activity run : runningData) {
//            currentHeartRate = run.getMinHeartRate();
//            if (currentHeartRate < minHeartRate) {
//                minHeartRate = currentHeartRate;
//            }
//         }
//
//         for (Activity walk : walkingData) {
//            currentHeartRate = walk.getMinHeartRate();
//            if (currentHeartRate > minHeartRate) {
//                minHeartRate = currentHeartRate;
//            }
//         }
//
//         for (Activity cycle : cyclingData) {
//            currentHeartRate = cycle.getMinHeartRate();
//            if (currentHeartRate > minHeartRate) {
//                minHeartRate = currentHeartRate;
//            }
//         }
//
//        return minHeartRate;
//    }
//
//
//    /**
//     * A function that calculates and returns the total distance a user has travelled through running, walking,
//     * and cycling.
//     * @param profile The profile for the user that the total distance is being calculated.
//     * @return A double which is the total distance a user has traveled.
//     */
//    public double findTotalDistance (Profile profile) {
//        int totalDistance = 0;
//
//        ArrayList<Activity> runningData = profile.getRunningData().getActivities();
//        ArrayList<Activity> walkingData = profile.getWalkingData().getActivities();
//        ArrayList<Activity> cyclingData = profile.getBikingData().getActivities();
//
//        for (Activity run : runningData) {
//            totalDistance += run.getDistance();
//         }
//
//         for (Activity walk : walkingData) {
//            totalDistance += walk.getDistance();
//         }
//
//         for (Activity cycle : cyclingData) {
//            totalDistance += cycle.getDistance();
//         }
//
//        return totalDistance;
//
//    }



//    /**
//     * A function that determines and returns the maximum step count from all a users activities.
//     * @param profile The profile for the user that the maximum step count is being calculated.
//     * @return A double which is a users maximum step count.
//     */
//    public double findMaximumStepCount (Profile profile){
//        return 1;
//    }