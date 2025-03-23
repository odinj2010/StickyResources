package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.swarm;

import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class SwarmState {
    public JellyEntity swarmLeader;
    public boolean isPanicking = false;
    public int panicTicks = 0;
    public double targetX, targetY, targetZ;
    public int pathRecalcInterval = 0;
    public boolean hasLeader = false;

    public void reset() {
        swarmLeader = null;
        isPanicking = false;
        panicTicks = 0;
        targetX = 0;
        targetY = 0;
        targetZ = 0;
        pathRecalcInterval = 0;
        hasLeader = false;
    }
}