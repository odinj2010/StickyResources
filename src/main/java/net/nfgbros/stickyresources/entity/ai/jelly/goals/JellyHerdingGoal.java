package net.nfgbros.stickyresources.entity.ai.jelly.goals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;
import java.util.List;

public class JellyHerdingGoal extends Goal {
    private final JellyEntity jelly;
    private JellyEntity leader;
    private final double speed;
    private int timeToRecalcPath;

    public JellyHerdingGoal(JellyEntity jelly, double speed) {
        this.jelly = jelly;
        this.speed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_HERDING_ENABLED, true)) {
            return false;
        }

        if (this.jelly.isAlpha() || this.jelly.isBaby()) {
            return false;
        }

        double range = StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_HERDING_RANGE, 16.0);
        List<JellyEntity> nearby = this.jelly.level().getEntitiesOfClass(JellyEntity.class, this.jelly.getBoundingBox().inflate(range));
        
        for (JellyEntity potentialLeader : nearby) {
            if (potentialLeader != this.jelly && potentialLeader.isAlpha() && potentialLeader.getJellyType() == this.jelly.getJellyType()) {
                this.leader = potentialLeader;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (!StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_HERDING_ENABLED, true)) {
            return false;
        }
        if (this.leader == null || !this.leader.isAlive() || !this.leader.isAlpha()) {
            return false;
        }
        double range = StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_HERDING_RANGE, 16.0);
        double distSq = this.jelly.distanceToSqr(this.leader);
        return distSq >= 9.0 && distSq <= (range * range);
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.leader = null;
    }

    @Override
    public void tick() {
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10 + this.jelly.getRandom().nextInt(20);
            this.jelly.getNavigation().moveTo(this.leader, this.speed);
        }
    }
}
