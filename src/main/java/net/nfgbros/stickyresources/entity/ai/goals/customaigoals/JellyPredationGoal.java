package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

/**
 * A custom AI goal for Jelly entities to hunt and consume nearby living entities.
 * This goal is only applicable to Lava and Water Jellies.
 */
public class JellyPredationGoal extends Goal {
    private final JellyEntity jelly;
    private LivingEntity target;
    private final TargetingConditions targetingConditions;

    /**
     * Constructs a new JellyPredationGoal for the given jelly entity.
     *
     * @param jelly the jelly entity for which this goal is created
     */
    public JellyPredationGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.targetingConditions = TargetingConditions.forCombat().range(10.0);
    }

    @Override
    public boolean canUse() {
        if (jelly.getJellyType() != ModEntities.JellyType.LAVA && jelly.getJellyType() != ModEntities.JellyType.WATER) {
            return false; // Only Lava and Water Jellies hunt
        }

        // Find a nearby target (e.g., a cow or another jelly)
        target = jelly.level().getNearestEntity(LivingEntity.class, targetingConditions, jelly, jelly.getX(), jelly.getY(), jelly.getZ(), jelly.getBoundingBox().inflate(10.0));
        return target != null;
    }

    @Override
    public void start() {
        jelly.getNavigation().moveTo(target, 1.0);
    }

    @Override
    public void tick() {
        if (target != null && jelly.distanceToSqr(target) <= 2.0) {
            target.hurt(jelly.level().damageSources().mobAttack(jelly), 2.0F); // Attack the target
            stop();
        }
    }

    @Override
    public void stop() {
        target = null;
    }
}
