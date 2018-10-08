package seng202.team6.models;

import java.time.LocalDate;

public class DistanceGoal extends Goal {

    /**
     * An Integer that represents distance goal
     */
    private int goalDistance;

    /**
     * A constructor for the class Distance Goal that sets the distance goal into the given Integer parameter
     * goal Distance.
     * @param goalDistance An Integer parameter that represents the distance goal target.
     */
    public DistanceGoal(int goalDistance) {
        super("Kilometer", LocalDate.now());
        this.goalDistance = goalDistance;
    }

    /**
     * A function that returns an Integer that represents the distance goal.
     * @return Returns an Integer that is the distance goal.
     */
    public int getGoalDistance() {
        return goalDistance;
    }

    /**
     * A function that sets the distance goal into the given Integer parameter goal Distance.
     * @param goalDistance An Integer that is used as distance goal.
     */
    public void setGoalDistance(int goalDistance) {
        this.goalDistance = goalDistance;
    }
}
