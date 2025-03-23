package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

/**
 * A custom AI goal for the JellyEntity that causes the jelly to panic and run away from nearby threats (skeletons and creepers).
 *
 * @author NFGBros
 * @since 1.0.0
 */
public class JellyPanicGoal extends Goal {
    private final JellyEntity jelly;
    private LivingEntity threat;
    private final TargetingConditions targetingConditions;

    /**
     * Constructs a new JellyPanicGoal for the given jelly entity.
     *
     * @param jelly the jelly entity for which this goal is created
     */
    public JellyPanicGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.targetingConditions = TargetingConditions.forCombat().range(10.0);
    }

    @Override
    public boolean canUse() {
        // Check for nearby threats (e.g., skeletons, creepers)
        threat = jelly.level().getNearestEntity(LivingEntity.class, targetingConditions, jelly, jelly.getX(), jelly.getY(), jelly.getZ(), jelly.getBoundingBox().inflate(10.0));
        return threat != null && (threat.getType().toString().contains("skeleton") || threat.getType().toString().contains("creeper"));
    }

    @Override
    public void start() {
        // Run away from the threat
        double x = jelly.getX() + (jelly.getRandom().nextDouble() - 0.5) * 10;
        double z = jelly.getZ() + (jelly.getRandom().nextDouble() - 0.5) * 10;
        jelly.getNavigation().moveTo(x, jelly.getY(), z, 1.5);
    }

    @Override
    public boolean canContinueToUse() {
        return !jelly.getNavigation().isDone();
    }

    @Override
    public void stop() {
        threat = null;
    }
}
