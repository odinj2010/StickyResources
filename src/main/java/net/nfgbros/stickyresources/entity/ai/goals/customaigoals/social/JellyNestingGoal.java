package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.social;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

public class JellyNestingGoal extends Goal {
    private final JellyEntity jelly;
    private BlockPos nestPos;

    public JellyNestingGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Only start nesting if the jelly is in love mode (ready for breeding)
        if (!jelly.isInLove()) {
            return false;
        }

        // Find a suitable location for the nest
        nestPos = jelly.blockPosition().offset(jelly.getRandom().nextInt(5) - 2, 0, jelly.getRandom().nextInt(5) - 2);
        return jelly.level().getBlockState(nestPos).isAir();
    }

    @Override
    public void start() {
        // Move toward the nest location
        jelly.getNavigation().moveTo(nestPos.getX(), nestPos.getY(), nestPos.getZ(), 1.0);
    }

    @Override
    public void tick() {
        // Check if the jelly has reached the nest location
        if (jelly.blockPosition().distSqr(nestPos) <= 1.5) {
            // Place a slime block at the nest location
            jelly.level().setBlock(nestPos, Blocks.SLIME_BLOCK.defaultBlockState(), 3);
            stop();
        }
    }

    @Override
    public void stop() {
        // Reset the nest position
        nestPos = null;
    }
}
