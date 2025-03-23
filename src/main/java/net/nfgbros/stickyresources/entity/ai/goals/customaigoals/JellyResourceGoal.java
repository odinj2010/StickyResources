package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

/**
 * A custom AI goal for the JellyEntity, which allows the jelly to collect nearby resources (water or lava).
 *
 * @author NFGBros
 * @since 1.0.0
 */
public class JellyResourceGoal extends Goal {
    private final JellyEntity jelly;
    private BlockPos resourcePos;

    /**
     * Constructs a new JellyResourceGoal for the given jelly entity.
     *
     * @param jelly the jelly entity for which this goal is created
     */
    public JellyResourceGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Find a nearby resource block (e.g., water for Water Jelly, lava for Lava Jelly)
        resourcePos = jelly.blockPosition().offset(jelly.getRandom().nextInt(5) - 2, 0, jelly.getRandom().nextInt(5) - 2);
        BlockState state = jelly.level().getBlockState(resourcePos);

        if (jelly.getJellyType() == ModEntities.JellyType.WATER) {
            return state.is(Blocks.WATER);
        } else if (jelly.getJellyType() == ModEntities.JellyType.LAVA) {
            return state.is(Blocks.LAVA);
        }
        return false;
    }

    @Override
    public void start() {
        jelly.getNavigation().moveTo(resourcePos.getX(), resourcePos.getY(), resourcePos.getZ(), 1.0);
    }

    @Override
    public void tick() {
        if (jelly.blockPosition().distSqr(resourcePos) <= 1.5) {
            // Collect the resource (e.g., replace water or lava with air)
            jelly.level().setBlock(resourcePos, Blocks.AIR.defaultBlockState(), 3);
            stop();
        }
    }

    @Override
    public void stop() {
        resourcePos = null;
    }
}
