package net.nfgbros.stickyresources.entity.ai.goals;

import net.minecraft.world.entity.ai.goal.Goal;

/**
 * Represents a priority-based goal wrapper. This class is used to associate a {@link Goal} with a priority value.
 * The priority value determines the order in which the goals are executed. Higher priority goals will be executed first.
 */
public class PriorityGoal {
    private final Goal goal;
    private final int priority;

    /**
     * Constructs a new instance of PriorityGoal.
     *
     * @param goal     The goal to be wrapped.
     * @param priority The priority value of the goal. Higher values indicate higher priority.
     */
    public PriorityGoal(Goal goal, int priority) {
        this.goal = goal;
        this.priority = priority;
    }

    /**
     * Returns the wrapped goal.
     *
     * @return The wrapped goal.
     */
    public Goal getGoal() {
        return goal;
    }

    /**
     * Returns the priority value of the goal.
     *
     * @return The priority value of the goal. Higher values indicate higher priority.
     */
    public int getPriority() {
        return priority;
    }
}
