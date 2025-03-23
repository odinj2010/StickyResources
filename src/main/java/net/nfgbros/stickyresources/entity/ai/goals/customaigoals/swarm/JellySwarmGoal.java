package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.swarm;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.goal.Goal;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;
import java.util.List;

public class JellySwarmGoal extends Goal {
    private final JellyEntity jelly;
    private final double speedModifier;
    private final SwarmState swarmState;
    private final SwarmCenterManager centerManager;
    private final SwarmMovementManager movementManager;
    private final SwarmPanicManager panicManager;

    public JellySwarmGoal(JellyEntity jelly, double speedModifier) {
        this.jelly = jelly;
        this.speedModifier = speedModifier;
        this.swarmState = new SwarmState();
        this.centerManager = new SwarmCenterManager(jelly, swarmState);
        this.movementManager = new SwarmMovementManager(jelly, swarmState, centerManager);
        this.panicManager = new SwarmPanicManager(jelly, swarmState);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        List<JellyEntity> nearby = jelly.level().getEntitiesOfClass(JellyEntity.class, jelly.getBoundingBox().inflate(StickyResourcesConfig.JELLY_SWARM_MERGE_RADIUS.get()));
        if (nearby.size() < StickyResourcesConfig.JELLY_SWARM_MIN_SIZE.get()) {
            return false;
        }
        centerManager.updateSwarmCenter(nearby);
        return true;
    }

    @Override
    public void tick() {
        List<JellyEntity> nearby = jelly.level().getEntitiesOfClass(JellyEntity.class, jelly.getBoundingBox().inflate(StickyResourcesConfig.JELLY_SWARM_MERGE_RADIUS.get()));
        centerManager.updateSwarmCenter(nearby);

        if (swarmState.isPanicking) {
            panicManager.handlePanic(speedModifier);
            return;
        }

        if (panicManager.shouldRetreat()) {
            panicManager.startPanic();
            return;
        }

        movementManager.moveSwarm(speedModifier, nearby);
    }

    @Override
    public boolean canContinueToUse() {
        if (swarmState.isPanicking) {
            return true;
        }

        List<JellyEntity> nearby = jelly.level().getEntitiesOfClass(JellyEntity.class, jelly.getBoundingBox().inflate(StickyResourcesConfig.JELLY_SWARM_MERGE_RADIUS.get()));
        return nearby.size() >= StickyResourcesConfig.JELLY_SWARM_MIN_SIZE.get();
    }

    @Override
    public void stop() {
        if (swarmState.swarmLeader != null) {
            swarmState.swarmLeader.removeEffect(MobEffects.GLOWING);
        }
        swarmState.reset();
    }
}