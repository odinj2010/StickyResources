package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

/**
 * A custom AI goal for the JellyEntity that allows it to explore random locations within a 20x20 area around its current position.
 *
 * @author NFGBros
 * @since 1.0.0
 */
public class JellyExplorationGoal extends Goal {
    private final JellyEntity jelly;
    private BlockPos explorationTarget;

    /**
     * Constructs a new JellyExplorationGoal for the given JellyEntity.
     *
     * @param jelly The JellyEntity that this goal is associated with.
     */
    public JellyExplorationGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Find a random location to explore
        explorationTarget = jelly.blockPosition().offset(jelly.getRandom().nextInt(20) - 10, 0, jelly.getRandom().nextInt(20) - 10);
        return true;
    }

    @Override
    public void start() {
        jelly.getNavigation().moveTo(explorationTarget.getX(), explorationTarget.getY(), explorationTarget.getZ(), 1.0);
    }

    @Override
    public void tick() {
        if (jelly.blockPosition().distSqr(explorationTarget) <= 1.5) {
            // Mark the location (e.g., place a torch or emit particles)
            if (jelly.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, jelly.getX(), jelly.getY() + 1, jelly.getZ(), 5, 0.5, 0.5, 0.5, 0.1);
            }
            stop();
        }
    }

    @Override
    public void stop() {
        explorationTarget = null;
    }
}
