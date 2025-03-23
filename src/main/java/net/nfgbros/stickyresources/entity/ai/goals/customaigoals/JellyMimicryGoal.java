package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

/**
 * A custom AI goal for the Jelly entity, allowing it to mimic the appearance of nearby blocks.
 * This goal is only available to Default Jelly entities.
 */
public class JellyMimicryGoal extends Goal {
    private final JellyEntity jelly;

    /**
     * Constructs a new instance of JellyMimicryGoal.
     *
     * @param jelly The Jelly entity that will perform this goal.
     */
    public JellyMimicryGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /**
     * Determines whether this goal can be used at the moment.
     *
     * @return {@code true} if the Jelly is a Default Jelly, {@code false} otherwise.
     */
    @Override
    public boolean canUse() {
        return jelly.getJellyType() == ModEntities.JellyType.DEFAULT; // Only Default Jelly can mimic
    }

    /**
     * Performs the goal's action. In this case, the Jelly mimics the appearance of nearby blocks.
     */
    @Override
    public void tick() {
        // Mimic the appearance of nearby blocks
        BlockState state = jelly.level().getBlockState(jelly.blockPosition().below());
        if (!state.isAir()) {
            jelly.setCustomName(state.getBlock().getName()); // Mimic the block's name
        }
    }
}
