package net.nfgbros.stickyresources.entity.ai.goals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.nfgbros.stickyresources.entity.ai.goals.customaigoals.*;
import net.nfgbros.stickyresources.entity.ai.goals.customaigoals.graze.*;
import net.nfgbros.stickyresources.entity.ai.goals.customaigoals.social.*;
import net.nfgbros.stickyresources.entity.ai.goals.customaigoals.swarm.*;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.StickyResourcesConfig;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JellyCustomAIGoal extends Goal {
    private final JellyEntity jellyEntity;
    private final List<PriorityGoal> customGoals;

    public JellyCustomAIGoal(JellyEntity jellyEntity) {
        this.jellyEntity = jellyEntity;
        this.customGoals = new ArrayList<>();

        // Add all custom AI goals with their priorities
        if (StickyResourcesConfig.JELLY_SWIM_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellySwimGoal(jellyEntity, 0.35), 0)); // Priority 0 (highest)
        }
        if (StickyResourcesConfig.JELLY_FOLLOW_FOOD_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyFollowFoodGoal(jellyEntity, 0.35), 1)); // Priority 1 (follow food)
        }
        if (StickyResourcesConfig.JELLY_MIGRATION_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyMigrationGoal(jellyEntity), 2));
        }
        if (StickyResourcesConfig.JELLY_JOINT_NESTING_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyJointNestingGoal(jellyEntity), 3));
        }
        if (StickyResourcesConfig.JELLY_SWARMS_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellySwarmGoal(jellyEntity, 0.35), 4));
        }
        if (StickyResourcesConfig.JELLY_DEFENSE_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyDefenseGoal(jellyEntity), 4));
        }
        if (StickyResourcesConfig.JELLY_GRAZE_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyGrazeGoal(jellyEntity), 4));
        }
        if (StickyResourcesConfig.JELLY_COMMUNICATION_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyCommunicationGoal(jellyEntity), 5));
        }
        if (StickyResourcesConfig.JELLY_PREDATION_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyPredationGoal(jellyEntity), 5));
        }
        if (StickyResourcesConfig.JELLY_ENVIRONMENT_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyEnvironmentGoal(jellyEntity), 6));
        }
        if (StickyResourcesConfig.JELLY_BREEDING_RITUAL_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyBreedingRitualGoal(jellyEntity), 7));
        }
        if (StickyResourcesConfig.JELLY_PANIC_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyPanicGoal(jellyEntity), 8));
        }
        if (StickyResourcesConfig.JELLY_CURIOSITY_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyCuriosityGoal(jellyEntity), 9));
        }
        if (StickyResourcesConfig.JELLY_BREEDING_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyBreedGoal(jellyEntity, 1, 10), 10)); // Priority 9 (after nesting and breeding ritual)
        }
        if (StickyResourcesConfig.JELLY_CHILD_PROTECTION_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyChildProtectionGoal(jellyEntity, 1.0D, 5.0F, 10.0F), 11));
        }
        if (StickyResourcesConfig.JELLY_EMOTIONAL_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyEmotionGoal(jellyEntity), 12));
        }
        if (StickyResourcesConfig.JELLY_WONDER_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyWanderGoal(jellyEntity, 0.35), 13));
        }
        if (StickyResourcesConfig.JELLY_RESOURCE_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyResourceGoal(jellyEntity), 15));
        }
        if (StickyResourcesConfig.JELLY_SEASONAL_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellySeasonalGoal(jellyEntity), 16));
        }
        if (StickyResourcesConfig.JELLY_MIMICRY_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyMimicryGoal(jellyEntity), 17));
        }
        if (StickyResourcesConfig.JELLY_SYMBIOSIS_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellySymbiosisGoal(jellyEntity), 18));
        }
        if (StickyResourcesConfig.JELLY_PLAY_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyPlayGoal(jellyEntity), 19));
        }
        if (StickyResourcesConfig.JELLY_EXPLORATION_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyExplorationGoal(jellyEntity), 20));
        }
        if (StickyResourcesConfig.JELLY_EVOLUTION_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyEvolutionGoal(jellyEntity), 21));
        }


        // Sort goals by priority
        customGoals.sort(Comparator.comparingInt(PriorityGoal::getPriority));
    }

    /**
     * Determines if any of the custom AI goals can be used.
     *
     * @return {@code true} if any of the custom goals can be used, {@code false} otherwise.
     */
    @Override
    public boolean canUse() {
        // Check if any of the custom goals can be used
        return customGoals.stream().anyMatch(priorityGoal -> priorityGoal.getGoal().canUse());
    }

    /**
     * Determines if any of the custom AI goals should continue to be used.
     *
     * @return {@code true} if any of the custom goals should continue to be used, {@code false} otherwise.
     */
    @Override
    public boolean canContinueToUse() {
        // Check if any of the custom goals should continue
        return customGoals.stream().anyMatch(priorityGoal -> priorityGoal.getGoal().canContinueToUse());
    }

    /**
     * Begins the execution of all custom AI goals that can be used, in priority order.
     *
     * This method iterates through the list of custom goals, checks if each goal can be used,
     * and starts the goal if it can. The goals are executed in priority order, with higher priority
     * goals being started before lower priority goals.
     *
     * @see PriorityGoal
     * @see Goal#canUse()
     * @see Goal#start()
     */
    @Override
    public void start() {
        // Start all goals that can be used, in priority order
        customGoals.stream()
                .filter(priorityGoal -> priorityGoal.getGoal().canUse())
                .forEach(priorityGoal -> priorityGoal.getGoal().start());
    }

    /**
     * Stops all custom AI goals associated with the jelly entity.
     * The goals are stopped in priority order, with higher priority goals being stopped first.
     *
     * @see PriorityGoal
     * @see Goal#stop()
     */
    @Override
    public void stop() {
        // Stop all goals, in priority order
        customGoals.forEach(priorityGoal -> priorityGoal.getGoal().stop());
    }

    /**
     * Ticks all custom AI goals that are currently active, in priority order.
     * This method iterates through the list of custom goals, checks if each goal can continue to be used,
     * and ticks the goal if it can. The goals are executed in priority order, with higher priority
     * goals being ticked before lower priority goals.
     *
     * @see PriorityGoal
     * @see Goal#canContinueToUse()
     * @see Goal#tick()
     */
    @Override
    public void tick() {
        // Tick all goals that are active, in priority order
        customGoals.stream()
                .filter(priorityGoal -> priorityGoal.getGoal().canContinueToUse())
                .forEach(priorityGoal -> priorityGoal.getGoal().tick());
    }
}