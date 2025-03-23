package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.swarm;

import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.Random;

public class SwarmMovementManager {
    private final JellyEntity jelly;
    private final SwarmState swarmState;
    private static final Random random = new Random();

    public SwarmMovementManager(JellyEntity jelly, SwarmState swarmState) {
        this.jelly = jelly;
        this.swarmState = swarmState;
    }

    public void setNewSwarmDestination() {
        if (swarmState.swarmLeader != null) {
            // Use config value for signal radius
            double signalRadius = StickyResourcesConfig.JELLY_SWARM_SIGNAL_RADIUS.get();
            swarmState.targetX = swarmState.swarmLeader.getX() + (random.nextDouble() * signalRadius * 2 - signalRadius);
            swarmState.targetY = swarmState.swarmLeader.getY();
            swarmState.targetZ = swarmState.swarmLeader.getZ() + (random.nextDouble() * signalRadius * 2 - signalRadius);
        }
    }

    public void moveSwarm(double speedModifier) {
        if (swarmState.pathRecalcInterval <= 0) {
            setNewSwarmDestination();
            swarmState.pathRecalcInterval = StickyResourcesConfig.JELLY_SWARM_LEADER_PATH_RECALC_INTERVAL.get();
        } else {
            swarmState.pathRecalcInterval--;
        }

        double distance = jelly.distanceTo(swarmState.swarmLeader);
        double adjustedSpeed = speedModifier * (0.5 + (distance / 15.0));
        adjustedSpeed = Math.min(adjustedSpeed, speedModifier * 1.3);

        jelly.getMoveControl().setWantedPosition(swarmState.targetX, swarmState.targetY, swarmState.targetZ, adjustedSpeed);
    }
}
