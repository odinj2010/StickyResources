package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

/**
 * Custom AI goal for Jelly entities. This goal handles environmental interactions, such as extinguishing fire for Water Jelly
 * and melting ice for Lava Jelly.
 */
public class JellyEnvironmentGoal extends Goal {
    private final JellyEntity jelly;

    /**
     * Constructs a new JellyEnvironmentGoal for the given jelly entity.
     *
     * @param jelly The jelly entity for which this goal is created.
     */
    public JellyEnvironmentGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Determines whether this goal can be used at the moment.
     *
     * @return Always returns true, indicating that this goal is always active.
     */
    @Override
    public boolean canUse() {
        return true;
    }

    /**
     * Performs the logic of this goal. In this case, it checks the environment around the jelly entity and performs specific actions
     * based on its type (Water or Lava).
     */
    @Override
    public void tick() {
        BlockPos jellyPos = jelly.blockPosition();
        Level world = jelly.level();

        // Water Jelly extinguishes fire
        if (jelly.getJellyType() == ModEntities.JellyType.WATER) {
            BlockPos firePos = jellyPos.above();
            BlockState fireState = world.getBlockState(firePos);
            if (fireState.is(Blocks.FIRE)) {
                world.setBlockAndUpdate(firePos, Blocks.AIR.defaultBlockState());
            }
        }

        // Lava Jelly melts ice
        if (jelly.getJellyType() == ModEntities.JellyType.LAVA) {
            BlockPos icePos = jellyPos.below();
            BlockState iceState = world.getBlockState(icePos);
            if (iceState.is(Blocks.ICE)) {
                world.setBlockAndUpdate(icePos, Blocks.WATER.defaultBlockState());
            }
        }
    }
}
