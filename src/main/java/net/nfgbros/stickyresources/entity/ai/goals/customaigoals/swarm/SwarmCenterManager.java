package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.swarm;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.Comparator;
import java.util.List;

public class SwarmCenterManager {
    private final JellyEntity jelly;
    private final SwarmState swarmState;

    public SwarmCenterManager(JellyEntity jelly, SwarmState swarmState) {
        this.jelly = jelly;
        this.swarmState = swarmState;
    }

    public void updateSwarmCenter(List<JellyEntity> nearby) {
        if (nearby.isEmpty()) {
            swarmState.reset();
            return;
        }

        if (nearby.size() < StickyResourcesConfig.JELLY_SWARM_MIN_SIZE.get()) {
            swarmState.reset();
            return;
        }

        if (swarmState.swarmLeader != null && swarmState.swarmLeader.isAlive() && nearby.contains(swarmState.swarmLeader)) {
            swarmState.hasLeader = true;
            return;
        }

        swarmState.hasLeader = false;
        chooseNewLeader(nearby);
    }

    private void chooseNewLeader(List<JellyEntity> nearby) {
        double selectionRadius = StickyResourcesConfig.JELLY_SWARM_LEADER_SELECTION_RADIUS.get();
        nearby.sort(Comparator.comparingDouble(JellyEntity::getHealth).reversed());

        JellyEntity newLeader = nearby.stream()
                .filter(j -> j.distanceTo(jelly) <= selectionRadius)
                .findFirst()
                .orElse(nearby.get(0));

        if (swarmState.swarmLeader != null) {
            swarmState.swarmLeader.removeEffect(MobEffects.GLOWING);
        }

        newLeader.setSwarmLeader(newLeader);
        swarmState.swarmLeader = newLeader;
        newLeader.addEffect(new MobEffectInstance(MobEffects.GLOWING, Integer.MAX_VALUE, 0, false, false));
        swarmState.hasLeader = true;

        double healthMultiplier = StickyResourcesConfig.JELLY_SWARM_LEADER_HEALTH_MULTIPLIER.get();
        float additionalHealth = newLeader.getHealth() * (float) (healthMultiplier / 100.0);
        newLeader.setHealth(newLeader.getHealth() + additionalHealth);

        double speedBonus = StickyResourcesConfig.JELLY_SWARM_LEADER_SPEED_BONUS.get();
        newLeader.setSpeed(newLeader.getSpeed() + (float) speedBonus);

        System.out.println("New leader selected: " + newLeader.getUUID());
    }

    public Vec3 calculateSwarmCenter(List<JellyEntity> nearby) {
        if (swarmState.hasLeader) {
            return new Vec3(swarmState.swarmLeader.getX(), swarmState.swarmLeader.getY(), swarmState.swarmLeader.getZ());
        } else {
            double avgX = 0, avgY = 0, avgZ = 0;
            for (JellyEntity j : nearby) {
                avgX += j.getX();
                avgY += j.getY();
                avgZ += j.getZ();
            }
            return new Vec3(avgX / nearby.size(), avgY / nearby.size(), avgZ / nearby.size());
        }
    }
}