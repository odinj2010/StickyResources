package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.swarm;

import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class SwarmState {
    public JellyEntity swarmLeader;
    public double targetX, targetY, targetZ;
    public boolean isPanicking = false;
    public int panicTicks = 0;
    public int pathRecalcInterval = 0;
}
