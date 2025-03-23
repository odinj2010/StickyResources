package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;
import java.util.Random;

public class JellyWanderGoal extends Goal {
    private final JellyEntity jelly;
    private final double speedModifier;
    private final Random random;
    private int executionChance = 120; // 1 in 120 chance to start wandering (roughly every 6 seconds)
    private int wanderDuration = 0;
    private static final int MAX_WANDER_DURATION = 200; // Maximum duration of wandering (10 seconds)

    public JellyWanderGoal(JellyEntity jelly, double speedModifier) {
        this.jelly = jelly;
        this.speedModifier = speedModifier;
        this.random = new Random();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        // Only wander if not a baby and if the execution chance is met
        if (jelly.isBaby()) return false;
        if (wanderDuration > 0) return true;
        return random.nextInt(executionChance) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        // Continue wandering if the duration is not over and the jelly is not a baby
        return wanderDuration > 0 && !jelly.isBaby();
    }

    @Override
    public void start() {
        // Set a random wander duration
        wanderDuration = random.nextInt(MAX_WANDER_DURATION);
        // Find a random position to wander to
        findRandomPosition();
    }

    @Override
    public void tick() {
        // Decrease the wander duration
        wanderDuration--;
        // If the jelly is not moving, find a new random position
        PathNavigation navigation = jelly.getNavigation();
        if (navigation.isDone()) {
            findRandomPosition();
        }
    }

    @Override
    public void stop() {
        // Reset the wander duration
        wanderDuration = 0;
    }

    private void findRandomPosition() {
        // Find a random position within a 10x10x10 area around the jelly
        BlockPos jellyPos = jelly.blockPosition();
        BlockPos randomPos = jellyPos.offset(random.nextInt(21) - 10, random.nextInt(5) - 2, random.nextInt(21) - 10);
        // Move the jelly to the random position
        PathNavigation navigation = jelly.getNavigation();
        navigation.moveTo(randomPos.getX(), randomPos.getY(), randomPos.getZ(), speedModifier);
    }
}