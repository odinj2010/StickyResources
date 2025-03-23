package net.nfgbros.stickyresources.entity.ai.goals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.nfgbros.stickyresources.entity.ai.goals.customaigoals.*;
import net.nfgbros.stickyresources.entity.ai.goals.customaigoals.graze.*;
import net.nfgbros.stickyresources.entity.ai.goals.customaigoals.social.JellyBreedGoal;
import net.nfgbros.stickyresources.entity.ai.goals.customaigoals.social.JellyBreedingRitualGoal;
import net.nfgbros.stickyresources.entity.ai.goals.customaigoals.social.JellyCommunicationGoal;
import net.nfgbros.stickyresources.entity.ai.goals.customaigoals.social.JellyNestingGoal;
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
        if (StickyResourcesConfig.JELLY_MIGRATION_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyMigrationGoal(jellyEntity), 1));
        }
        if (StickyResourcesConfig.JELLY_NESTING_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyNestingGoal(jellyEntity), 2));
        }
        if (StickyResourcesConfig.JELLY_SWARMS_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellySwarmGoal(jellyEntity, 0.35), 3));
        }
        if (StickyResourcesConfig.JELLY_DEFENSE_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyDefenseGoal(jellyEntity), 3));
        }
        if (StickyResourcesConfig.JELLY_GRAZE_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyGrazeGoal(jellyEntity), 3));
        }
        if (StickyResourcesConfig.JELLY_COMMUNICATION_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyCommunicationGoal(jellyEntity), 3));
        }
        if (StickyResourcesConfig.JELLY_PREDATION_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyPredationGoal(jellyEntity), 4));
        }
        if (StickyResourcesConfig.JELLY_ENVIRONMENT_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyEnvironmentGoal(jellyEntity), 5));
        }
        if (StickyResourcesConfig.JELLY_BREEDING_RITUAL_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyBreedingRitualGoal(jellyEntity), 6));
        }
        if (StickyResourcesConfig.JELLY_PANIC_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyPanicGoal(jellyEntity), 7));
        }
        if (StickyResourcesConfig.JELLY_CURIOSITY_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyCuriosityGoal(jellyEntity), 8));
        }
        if (StickyResourcesConfig.JELLY_BREEDING_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyBreedGoal(jellyEntity, 1, 10), 9)); // Priority 9 (after nesting and breeding ritual)
        }
        if (StickyResourcesConfig.JELLY_RESOURCE_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyResourceGoal(jellyEntity), 9));
        }
        if (StickyResourcesConfig.JELLY_SEASONAL_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellySeasonalGoal(jellyEntity), 10));
        }
        if (StickyResourcesConfig.JELLY_MIMICRY_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyMimicryGoal(jellyEntity), 11));
        }
        if (StickyResourcesConfig.JELLY_SYMBIOSIS_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellySymbiosisGoal(jellyEntity), 12));
        }
        if (StickyResourcesConfig.JELLY_PLAY_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyPlayGoal(jellyEntity), 14));
        }
        if (StickyResourcesConfig.JELLY_EXPLORATION_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyExplorationGoal(jellyEntity), 15));
        }
        if (StickyResourcesConfig.JELLY_EVOLUTION_ACTIVE.get()) {
            customGoals.add(new PriorityGoal(new JellyEvolutionGoal(jellyEntity), 16));
        }


        // Sort goals by priority
        customGoals.sort(Comparator.comparingInt(PriorityGoal::getPriority));
    }

    @Override
    public boolean canUse() {
        // Check if any of the custom goals can be used
        return customGoals.stream().anyMatch(priorityGoal -> priorityGoal.getGoal().canUse());
    }

    @Override
    public boolean canContinueToUse() {
        // Check if any of the custom goals should continue
        return customGoals.stream().anyMatch(priorityGoal -> priorityGoal.getGoal().canContinueToUse());
    }

    @Override
    public void start() {
        // Start all goals that can be used, in priority order
        customGoals.stream()
                .filter(priorityGoal -> priorityGoal.getGoal().canUse())
                .forEach(priorityGoal -> priorityGoal.getGoal().start());
    }

    @Override
    public void stop() {
        // Stop all goals, in priority order
        customGoals.forEach(priorityGoal -> priorityGoal.getGoal().stop());
    }

    @Override
    public void tick() {
        // Tick all goals that are active, in priority order
        customGoals.stream()
                .filter(priorityGoal -> priorityGoal.getGoal().canContinueToUse())
                .forEach(priorityGoal -> priorityGoal.getGoal().tick());
    }
}
