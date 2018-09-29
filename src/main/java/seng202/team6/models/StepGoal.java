package seng202.team6.models;

import java.time.LocalDate;

public class StepGoal extends Goal {

    private int goalStepsNum;

    public StepGoal(int goalStepsNum) {
        super("Step Goal", LocalDate.now());
        this.goalStepsNum = goalStepsNum;
    }

    public int getGoalStepNum() { return goalStepsNum; }

    public void setStepGoal(int newGoalSteps) { goalStepsNum = newGoalSteps; }
}
