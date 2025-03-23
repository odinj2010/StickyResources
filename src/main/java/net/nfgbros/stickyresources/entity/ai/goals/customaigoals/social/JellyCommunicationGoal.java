package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.social;

import net.minecraft.world.entity.ai.goal.Goal;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;
import java.util.List;

public class JellyCommunicationGoal extends Goal {
    private final JellyEntity jelly;
    private JellyEntity targetJelly;
    private int communicationCooldown = 0;
    private static final int COMMUNICATION_COOLDOWN_MAX = 100; // 5 seconds

    public JellyCommunicationGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Only communicate if the jelly is not a baby and communication is enabled
        if (jelly.isBaby()) return false;
        if (communicationCooldown > 0) return false;
        // Find a nearby jelly to communicate with
        this.targetJelly = findNearbyJelly();
        return targetJelly != null;
    }

    @Override
    public boolean canContinueToUse() {
        // Continue communicating if the target jelly is still nearby and alive
        return targetJelly != null && targetJelly.isAlive() && communicationCooldown > 0;
    }

    @Override
    public void start() {
        communicationCooldown = COMMUNICATION_COOLDOWN_MAX;
    }

    @Override
    public void tick() {
        if (targetJelly == null || !targetJelly.isAlive()) {
            return;
        }
        // Look at the target jelly
        jelly.getLookControl().setLookAt(targetJelly, 10.0F, jelly.getMaxHeadXRot());
        // Move towards the target jelly
        jelly.getNavigation().moveTo(targetJelly, 1.0);
        // Decrease the communication cooldown
        communicationCooldown--;
    }

    @Override
    public void stop() {
        communicationCooldown = 0;
        targetJelly = null;
    }

    private JellyEntity findNearbyJelly() {
        // Find nearby jellies to communicate with
        List<JellyEntity> nearbyJellies = jelly.level().getEntitiesOfClass(JellyEntity.class, jelly.getBoundingBox().inflate(8.0));
        for (JellyEntity otherJelly : nearbyJellies) {
            if (otherJelly != jelly) {
                return otherJelly;
            }
        }
        return null;
    }
}