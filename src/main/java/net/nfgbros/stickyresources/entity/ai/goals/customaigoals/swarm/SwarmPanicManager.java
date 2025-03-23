package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.swarm;

import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.Random;

public class SwarmPanicManager {
    private final JellyEntity jelly;
    private final SwarmState swarmState;
    private static final Random random = new Random();

    public SwarmPanicManager(JellyEntity jelly, SwarmState swarmState) {
        this.jelly = jelly;
        this.swarmState = swarmState;
    }

    public void startPanic() {
        swarmState.isPanicking = true;
        swarmState.panicTicks = 80;
    }

    public void handlePanic(double speedModifier) {
        swarmState.panicTicks--;
        if (swarmState.panicTicks <= 0) {
            swarmState.isPanicking = false;
        }

        jelly.getMoveControl().setWantedPosition(
                jelly.getX() + (random.nextDouble() * 6 - 3),
                jelly.getY(),
                jelly.getZ() + (random.nextDouble() * 6 - 3),
                speedModifier * 1.5
        );
    }

    public boolean shouldRetreat() {
        return swarmState.swarmLeader != null &&
                swarmState.swarmLeader.getHealth() < swarmState.swarmLeader.getMaxHealth() * (StickyResourcesConfig.JELLY_SWARM_RETREATING_HEALTH_PERCENT.get() / 100.0f);
    }
}