package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

public class JellyEnvironmentGoal extends Goal {
    private final JellyEntity jelly;

    public JellyEnvironmentGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return true; // Always active
    }

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
