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
    private final SwarmLeaderManager leaderManager;
    private final SwarmMovementManager movementManager;
    private final SwarmPanicManager panicManager;

    public JellySwarmGoal(JellyEntity jelly, double speedModifier) {
        this.jelly = jelly;
        this.speedModifier = speedModifier;
        this.swarmState = new SwarmState();
        this.leaderManager = new SwarmLeaderManager(jelly, swarmState);
        this.movementManager = new SwarmMovementManager(jelly, swarmState);
        this.panicManager = new SwarmPanicManager(jelly, swarmState);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (swarmState.isPanicking) {
            return true;
        }

        List<JellyEntity> nearby = jelly.level().getEntitiesOfClass(JellyEntity.class, jelly.getBoundingBox().inflate(StickyResourcesConfig.JELLY_SWARM_MERGE_RADIUS.get()));
        if (nearby.size() < StickyResourcesConfig.JELLY_SWARM_MIN_SIZE.get()) {
            return false;
        }

        leaderManager.updateSwarm(nearby); // Update the swarm and choose a new leader if necessary
        return leaderManager.isLeaderValid(nearby);
    }



    @Override
    public void tick() {
        if (swarmState.isPanicking) {
            panicManager.handlePanic(speedModifier);
            return;
        }

        List<JellyEntity> nearby = jelly.level().getEntitiesOfClass(JellyEntity.class, jelly.getBoundingBox().inflate(StickyResourcesConfig.JELLY_SWARM_MERGE_RADIUS.get()));
        if (!leaderManager.isLeaderValid(nearby) || panicManager.shouldRetreat()) { // Pass nearby jellies to isLeaderValid
            panicManager.startPanic();
            return;
        }

        movementManager.moveSwarm(speedModifier);
    }

    @Override
    public boolean canContinueToUse() {
        if (swarmState.isPanicking) {
            return true;
        }

        List<JellyEntity> nearby = jelly.level().getEntitiesOfClass(JellyEntity.class, jelly.getBoundingBox().inflate(StickyResourcesConfig.JELLY_SWARM_MERGE_RADIUS.get()));
        return leaderManager.isLeaderValid(nearby) && // Pass nearby jellies to isLeaderValid
                nearby.size() >= StickyResourcesConfig.JELLY_SWARM_MIN_SIZE.get();
    }


    @Override
    public void stop() {
        if (swarmState.swarmLeader != null) {
            swarmState.swarmLeader.removeEffect(MobEffects.GLOWING);
        }
        swarmState.swarmLeader = null;
    }
}
