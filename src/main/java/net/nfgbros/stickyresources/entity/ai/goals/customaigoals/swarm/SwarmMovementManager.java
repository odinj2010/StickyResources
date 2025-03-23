package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.swarm;

import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.List;
import java.util.Random;

public class SwarmMovementManager {
    private final JellyEntity jelly;
    private final SwarmState swarmState;
    private final SwarmCenterManager centerManager;
    private static final Random random = new Random();

    public SwarmMovementManager(JellyEntity jelly, SwarmState swarmState, SwarmCenterManager centerManager) {
        this.jelly = jelly;
        this.swarmState = swarmState;
        this.centerManager = centerManager;
    }

    public void moveSwarm(double speedModifier, List<JellyEntity> nearby) {
        if (swarmState.pathRecalcInterval <= 0) {
            setNewSwarmDestination(nearby);
            swarmState.pathRecalcInterval = StickyResourcesConfig.JELLY_SWARM_LEADER_PATH_RECALC_INTERVAL.get();
        } else {
            swarmState.pathRecalcInterval--;
        }

        Vec3 swarmCenter = centerManager.calculateSwarmCenter(nearby);
        double distanceToCenter = jelly.distanceToSqr(swarmCenter.x, swarmCenter.y, swarmCenter.z);

        double adjustedSpeed = speedModifier;
        if (swarmState.hasLeader) {
            double distance = jelly.distanceTo(swarmState.swarmLeader);
            adjustedSpeed = speedModifier * (0.5 + (distance / 15.0));
            adjustedSpeed = Math.min(adjustedSpeed, speedModifier * 1.3);
        }

        Vec3 directionToCenter = swarmCenter.subtract(jelly.getX(), jelly.getY(), jelly.getZ()).normalize();
        Vec3 targetPos = new Vec3(swarmState.targetX, swarmState.targetY, swarmState.targetZ);
        Vec3 directionToTarget = targetPos.subtract(jelly.getX(), jelly.getY(), jelly.getZ()).normalize();

        Vec3 finalDirection = directionToCenter.add(directionToTarget).normalize();

        jelly.getMoveControl().setWantedPosition(jelly.getX() + finalDirection.x, jelly.getY() + finalDirection.y, jelly.getZ() + finalDirection.z, adjustedSpeed);
    }

    private void setNewSwarmDestination(List<JellyEntity> nearby) {
        if (swarmState.hasLeader) {
            double signalRadius = StickyResourcesConfig.JELLY_SWARM_SIGNAL_RADIUS.get();
            swarmState.targetX = swarmState.swarmLeader.getX() + (random.nextDouble() * signalRadius * 2 - signalRadius);
            swarmState.targetY = swarmState.swarmLeader.getY();
            swarmState.targetZ = swarmState.swarmLeader.getZ() + (random.nextDouble() * signalRadius * 2 - signalRadius);
        } else {
            Vec3 center = centerManager.calculateSwarmCenter(nearby);
            double signalRadius = StickyResourcesConfig.JELLY_SWARM_SIGNAL_RADIUS.get();
            swarmState.targetX = center.x + (random.nextDouble() * signalRadius * 2 - signalRadius);
            swarmState.targetY = center.y;
            swarmState.targetZ = center.z + (random.nextDouble() * signalRadius * 2 - signalRadius);
        }
    }
}