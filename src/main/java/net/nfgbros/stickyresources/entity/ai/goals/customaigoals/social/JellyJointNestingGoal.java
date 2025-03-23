package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.social;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

public class JellyJointNestingGoal extends Goal {
    private final JellyEntity jelly;
    private final JellyEntity partner;
    private BlockPos nestPos;
    private int nestBuildCooldown = 0;
    private static final int NEST_BUILD_COOLDOWN_MAX = 100; // 5 seconds

    public JellyJointNestingGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.partner = jelly.getMate();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Only start nesting if the jelly is in love mode, has a partner, and is not already nesting
        return jelly.isInLove() && jelly.hasPartner() && !jelly.isNesting() && partner.isInLove() && !partner.isNesting();
    }

    @Override
    public boolean canContinueToUse() {
        // Continue nesting if both jellies are still in love mode, have a partner, and are nesting
        return jelly.isInLove() && jelly.hasPartner() && jelly.isNesting() && partner.isInLove() && partner.isNesting();
    }

    @Override
    public void start() {
        // Find a suitable nest location when starting the goal
        findNestLocation();
    }

    @Override
    public void tick() {
        if (nestPos == null) {
            findNestLocation();
            return;
        }

        // If either jelly is not at the nest, move towards it
        if (!jelly.blockPosition().equals(nestPos) || !partner.blockPosition().equals(nestPos)) {
            jelly.getNavigation().moveTo(nestPos.getX(), nestPos.getY(), nestPos.getZ(), 1.0);
            partner.getNavigation().moveTo(nestPos.getX(), nestPos.getY(), nestPos.getZ(), 1.0);
        } else {
            // If both jellies are at the nest, create the nest block
            if (nestBuildCooldown <= 0) {
                createNest();
                nestBuildCooldown = NEST_BUILD_COOLDOWN_MAX;
            } else {
                nestBuildCooldown--;
            }
        }
    }

    @Override
    public void stop() {
        // Reset nest position when the goal stops
        nestPos = null;
    }

    private void findNestLocation() {
        Level level = jelly.level();
        BlockPos jellyPos = jelly.blockPosition();

        // Search for a suitable location within a 5x5x5 area around the jelly
        for (int y = -2; y <= 2; y++) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos potentialNestPos = jellyPos.offset(x, y, z);
                    BlockState blockState = level.getBlockState(potentialNestPos);

                    // Check if the block below is air and the block above is air
                    if (level.isEmptyBlock(potentialNestPos.below()) && level.isEmptyBlock(potentialNestPos)) {
                        nestPos = potentialNestPos.below();
                        return;
                    }
                }
            }
        }
        // If no suitable location is found, set nestPos to null
        nestPos = null;
    }

    private void createNest() {
        Level level = jelly.level();
        if (nestPos != null && level.getBlockState(nestPos).isAir()) {
            // Replace the block below the jelly with a slime block (nest)
            level.setBlock(nestPos, Blocks.SLIME_BLOCK.defaultBlockState(), 3);
            jelly.setNesting(true);
            partner.setNesting(true);
            jelly.nestPos = nestPos;
            partner.nestPos = nestPos;
        }
    }
}