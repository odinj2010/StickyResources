package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;
import java.util.Random;

public class JellySwimGoal extends Goal {
    private final JellyEntity jelly;
    private final double speedModifier;
    private final Random random;
    private int executionChance = 120; // 1 in 120 chance to start swimming (roughly every 6 seconds)
    private int swimDuration = 0;
    private static final int MAX_SWIM_DURATION = 200; // Maximum duration of swimming (10 seconds)

    public JellySwimGoal(JellyEntity jelly, double speedModifier) {
        this.jelly = jelly;
        this.speedModifier = speedModifier;
        this.random = new Random();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        // Only swim if in water, not a baby, and if the execution chance is met
        if (jelly.isBaby()) return false;
        if (!jelly.isInWater()) return false;
        if (swimDuration > 0) return true;
        return random.nextInt(executionChance) == 0 && jelly.getSwimBehavior() != ModEntities.JellyType.JellySwimBehavior.NONE && jelly.getSwimBehavior() != ModEntities.JellyType.JellySwimBehavior.WATER_DAMAGE && jelly.getSwimBehavior() != ModEntities.JellyType.JellySwimBehavior.WATER_LETHAL;
    }

    @Override
    public boolean canContinueToUse() {
        // Continue swimming if the duration is not over, in water, not a baby, and can swim
        return swimDuration > 0 && jelly.isInWater() && !jelly.isBaby() && jelly.getSwimBehavior() != ModEntities.JellyType.JellySwimBehavior.NONE && jelly.getSwimBehavior() != ModEntities.JellyType.JellySwimBehavior.WATER_DAMAGE && jelly.getSwimBehavior() != ModEntities.JellyType.JellySwimBehavior.WATER_LETHAL;
    }

    @Override
    public void start() {
        // Set a random swim duration
        swimDuration = random.nextInt(MAX_SWIM_DURATION);
        // Find a random position to swim to
        findRandomPosition();
    }

    @Override
    public void tick() {
        // Decrease the swim duration
        swimDuration--;
        // If the jelly is not moving, find a new random position
        PathNavigation navigation = jelly.getNavigation();
        if (navigation.isDone()) {
            findRandomPosition();
        }
    }

    @Override
    public void stop() {
        // Reset the swim duration
        swimDuration = 0;
    }

    private void findRandomPosition() {
        // Find a random position within a 10x10x10 area around the jelly
        BlockPos jellyPos = jelly.blockPosition();
        BlockPos randomPos = jellyPos.offset(random.nextInt(21) - 10, random.nextInt(5) - 2, random.nextInt(21) - 10);
        // Move the jelly to the random position
        PathNavigation navigation = jelly.getNavigation();
        switch (jelly.getSwimBehavior()) {
            case FISH:
                navigation.moveTo(randomPos.getX(), randomPos.getY(), randomPos.getZ(), speedModifier);
                break;
            case DOLPHIN:
                if (jelly.isSubmerged()) {
                    navigation.moveTo(randomPos.getX(), randomPos.getY(), randomPos.getZ(), speedModifier);
                } else {
                    navigation.moveTo(randomPos.getX(), jellyPos.getY(), randomPos.getZ(), speedModifier);
                }
                break;
            case FLOATING:
                navigation.moveTo(jellyPos.getX(), jellyPos.getY(), jellyPos.getZ(), speedModifier);
                break;
            case SURFACE_SWIMMING:
                if (!jelly.isAboveWater()) {
                    navigation.moveTo(randomPos.getX(), jellyPos.getY(), randomPos.getZ(), speedModifier);
                } else {
                    navigation.moveTo(randomPos.getX(), randomPos.getY(), randomPos.getZ(), speedModifier);
                }
                break;
            case NONE:
            case WATER_DAMAGE:
            case WATER_LETHAL:
            default:
                break;
        }
    }
}