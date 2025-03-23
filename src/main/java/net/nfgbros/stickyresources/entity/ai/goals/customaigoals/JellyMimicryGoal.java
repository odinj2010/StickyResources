package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

public class JellyMimicryGoal extends Goal {
    private final JellyEntity jelly;

    public JellyMimicryGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return jelly.getJellyType() == ModEntities.JellyType.DEFAULT; // Only Default Jelly can mimic
    }

    @Override
    public void tick() {
        // Mimic the appearance of nearby blocks
        BlockState state = jelly.level().getBlockState(jelly.blockPosition().below());
        if (!state.isAir()) {
            jelly.setCustomName(state.getBlock().getName()); // Mimic the block's name
        }
    }
}
