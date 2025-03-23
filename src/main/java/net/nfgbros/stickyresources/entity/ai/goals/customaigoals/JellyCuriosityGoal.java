package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

/**
 * A custom AI goal for the JellyEntity. This goal allows the jelly to explore its surroundings by moving to a nearby block and investigating it.
 *
 * @author NFGBros
 * @since 1.0.0
 */
public class JellyCuriosityGoal extends Goal {
    private final JellyEntity jelly;
    private BlockPos targetPos;

    /**
     * Constructs a new JellyCuriosityGoal for the given jelly entity.
     *
     * @param jelly the jelly entity for which this goal is created
     */
    public JellyCuriosityGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Find a nearby block that is not air
        targetPos = jelly.blockPosition().offset(jelly.getRandom().nextInt(5) - 2, 0, jelly.getRandom().nextInt(5) - 2);
        BlockState state = jelly.level().getBlockState(targetPos);
        return !state.isAir();
    }

    @Override
    public void start() {
        jelly.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.0);
    }

    @Override
    public void tick() {
        if (jelly.blockPosition().distSqr(targetPos) <= 1.5) {
            // Investigate the block (e.g., emit particles)
            if (jelly.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, jelly.getX(), jelly.getY() + 1, jelly.getZ(), 1, 0.5, 0.5, 0.5, 0.1);
            }
            stop();
        }
    }

    @Override
    public void stop() {
        targetPos = null;
    }
}
