package net.nfgbros.stickyresources.entity.ai.goal;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.goal.Goal;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import java.util.List;
import java.util.EnumSet;
import java.util.Random;
import java.util.Comparator;

public class JellySwarmGoal extends Goal {

    private static final double LEADER_HEALTH_BONUS = 5;
    private static final double LEADER_SPEED_BONUS = 0.2f;
    private final JellyEntity jelly;
    private double speedModifier;
    private JellyEntity swarmLeader;
    private double targetX, targetY, targetZ;
    private static final Random random = new Random();
    private boolean isPanicking = false;
    private int panicTicks = 0;

    public JellySwarmGoal(JellyEntity jelly, double speedModifier) {
        this.jelly = jelly;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (isPanicking) {
            return true;
        }

        List<JellyEntity> nearby = jelly.level().getEntitiesOfClass(JellyEntity.class, jelly.getBoundingBox().inflate(20.0D));
        if (nearby.size() < 5) {
            return false;
        }

        if (swarmLeader == null || !swarmLeader.isAlive()) {
            chooseNewLeader(nearby);
        }

        return swarmLeader != null;
    }

    private void chooseNewLeader(List<JellyEntity> nearby) {
        if (nearby.isEmpty()) return;

        nearby.sort(Comparator.comparingDouble(JellyEntity::getHealth).reversed());
        JellyEntity newLeader = nearby.get(0);

        newLeader.setSwarmLeader(newLeader);
        this.swarmLeader = newLeader;
        swarmLeader.addEffect(new MobEffectInstance(MobEffects.GLOWING, Integer.MAX_VALUE, 0, false, false));

        int herdSize = nearby.size();

        float additionalHealth = Math.max(5, swarmLeader.getHealth() * 0.2f * herdSize);
        swarmLeader.setHealth(swarmLeader.getHealth() + additionalHealth);

        float additionalSpeed = Math.max(5, swarmLeader.getSpeed() * 0.2f * herdSize);
        swarmLeader.setSpeed(swarmLeader.getSpeed() + additionalSpeed);


    }

    private void setNewSwarmDestination() {
        if (this.swarmLeader != null) {
            this.targetX = swarmLeader.getX() + (random.nextDouble() * 8.0 - 4.0);
            this.targetY = swarmLeader.getY();
            this.targetZ = swarmLeader.getZ() + (random.nextDouble() * 8.0 - 4.0);
        }
    }

    @Override
    public void tick() {
        if (isPanicking) {
            panicTicks--;
            if (panicTicks <= 0) {
                isPanicking = false;
                chooseNewLeader(jelly.level().getEntitiesOfClass(JellyEntity.class, jelly.getBoundingBox().inflate(20.0D)));
            }
            jelly.getMoveControl().setWantedPosition(
                    jelly.getX() + (random.nextDouble() * 6 - 3),
                    jelly.getY(),
                    jelly.getZ() + (random.nextDouble() * 6 - 3),
                    speedModifier * 1.5
            );
            return;
        }

        if (this.swarmLeader == null || !this.swarmLeader.isAlive()) {
            startPanic();
            return;
        }

        double distance = this.jelly.distanceTo(this.swarmLeader);
        double adjustedSpeed = speedModifier * (0.5 + (distance / 15.0));
        adjustedSpeed = Math.min(adjustedSpeed, speedModifier * 1.3);

        if (this.jelly.distanceToSqr(targetX, targetY, targetZ) < 1.5) {
            setNewSwarmDestination();
        }

        this.jelly.getMoveControl().setWantedPosition(targetX, targetY, targetZ, adjustedSpeed);
    }

    private void startPanic() {
        isPanicking = true;
        panicTicks = 80;
    }

    @Override
    public boolean canContinueToUse() {
        if (isPanicking) {
            return true;
        }

        return this.swarmLeader != null && this.swarmLeader.isAlive() &&
                jelly.level().getEntitiesOfClass(JellyEntity.class, jelly.getBoundingBox().inflate(20.0D)).size() >= 5;
    }

    @Override
    public void stop() {
        if (swarmLeader != null) {
            swarmLeader.removeEffect(MobEffects.GLOWING);
        }
        this.swarmLeader = null;
    }
}
