package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

public class JellyDefenseGoal extends Goal {
    private final JellyEntity jelly;
    private LivingEntity threat;
    private final TargetingConditions targetingConditions;

    public JellyDefenseGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.targetingConditions = TargetingConditions.forCombat().range(10.0);
    }

    @Override
    public boolean canUse() {
        // Check for nearby threats (e.g., mobs, players)
        threat = jelly.level().getNearestEntity(LivingEntity.class, targetingConditions, jelly, jelly.getX(), jelly.getY(), jelly.getZ(), jelly.getBoundingBox().inflate(10.0));
        return threat != null && (threat instanceof Player || threat.getType().toString().contains("mob"));
    }

    @Override
    public void start() {
        jelly.getNavigation().moveTo(threat, 1.0);
    }

    @Override
    public void tick() {
        if (jelly.distanceToSqr(threat) <= 2.0) {
            // Attack the threat
            threat.hurt(jelly.level().damageSources().mobAttack(jelly), 2.0F);
            stop();
        }
    }

    @Override
    public void stop() {
        threat = null;
    }
}
