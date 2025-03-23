package net.nfgbros.stickyresources.entity.ai.goals;

import net.minecraft.world.entity.ai.goal.Goal;

public class PriorityGoal {
    private final Goal goal;
    private final int priority;

    public PriorityGoal(Goal goal, int priority) {
        this.goal = goal;
        this.priority = priority;
    }

    public Goal getGoal() {
        return goal;
    }

    public int getPriority() {
        return priority;
    }
}
