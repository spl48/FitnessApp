package seng202.team6.models;

import java.time.LocalDate;

public class DistanceGoal extends Goal {

    private int goalDistance;

    public DistanceGoal(int goalDistance) {
        super("Kilometer", LocalDate.now());
        this.goalDistance = goalDistance;
    }

    public int getGoalDistance() {
        return goalDistance;
    }

    public void setGoalDistance(int goalDistance) {
        this.goalDistance = goalDistance;
    }
}
