package seng202.team6.models;

import java.time.LocalDate;

public class KilometerGoal extends Goal {

    private int goalDistance;

    private LocalDate endDate;

    public KilometerGoal(int goalDistance, LocalDate endDate) {
        super("Kilometer", LocalDate.now());
        this.goalDistance = goalDistance;
        this.endDate = endDate;
    }

    public int getGoalDistance() {
        return goalDistance;
    }

    public void setGoalDistance(int goalDistance) {
        this.goalDistance = goalDistance;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
