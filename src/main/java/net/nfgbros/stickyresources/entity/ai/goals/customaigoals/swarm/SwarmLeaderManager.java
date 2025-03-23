package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.swarm;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.Comparator;
import java.util.List;

public class SwarmLeaderManager {
    private final JellyEntity jelly; // The jelly entity this manager is associated with
    private final SwarmState swarmState; // The state of the swarm (e.g., leader, panic status)

    /**
     * Constructs a new SwarmLeaderManager for the given jelly and swarm state.
     *
     * @param jelly      The jelly entity this manager is associated with.
     * @param swarmState The state of the swarm (e.g., leader, panic status).
     */
    public SwarmLeaderManager(JellyEntity jelly, SwarmState swarmState) {
        this.jelly = jelly;
        this.swarmState = swarmState;
    }

    /**
     * Chooses a new leader if the current leader is dead or invalid.
     *
     * @param nearby A list of nearby jelly entities to consider for leadership.
     */
    public void chooseNewLeader(List<JellyEntity> nearby) {
        if (nearby.isEmpty()) {
            return; // No nearby jellies, so no leader can be chosen
        }

        // If there is already a valid leader, do nothing
        if (swarmState.swarmLeader != null && swarmState.swarmLeader.isAlive() && nearby.contains(swarmState.swarmLeader)) {
            return;
        }

        // Use config value for leader selection radius
        double selectionRadius = StickyResourcesConfig.JELLY_SWARM_LEADER_SELECTION_RADIUS.get();
        nearby.sort(Comparator.comparingDouble(JellyEntity::getHealth).reversed());

        // Choose the healthiest jelly within the selection radius as the new leader
        JellyEntity newLeader = nearby.stream()
                .filter(j -> j.distanceTo(jelly) <= selectionRadius)
                .findFirst()
                .orElse(nearby.get(0));

        // Transfer leadership
        if (swarmState.swarmLeader != null) {
            swarmState.swarmLeader.removeEffect(MobEffects.GLOWING); // Remove glowing effect from the old leader
        }

        newLeader.setSwarmLeader(newLeader);
        swarmState.swarmLeader = newLeader;
        newLeader.addEffect(new MobEffectInstance(MobEffects.GLOWING, Integer.MAX_VALUE, 0, false, false));

        // Use config value for leader health multiplier
        double healthMultiplier = StickyResourcesConfig.JELLY_SWARM_LEADER_HEALTH_MULTIPLIER.get();
        float additionalHealth = newLeader.getHealth() * (float) (healthMultiplier / 100.0);
        newLeader.setHealth(newLeader.getHealth() + additionalHealth);

        // Use config value for leader speed bonus
        double speedBonus = StickyResourcesConfig.JELLY_SWARM_LEADER_SPEED_BONUS.get();
        newLeader.setSpeed(newLeader.getSpeed() + (float) speedBonus);

        // Debug log
        System.out.println("New leader selected: " + newLeader.getUUID());
    }

    /**
     * Checks if the current leader is still alive and part of the swarm.
     *
     * @param nearby A list of nearby jelly entities to check for the leader.
     * @return True if the leader is valid, false otherwise.
     */
    public boolean isLeaderValid(List<JellyEntity> nearby) {
        if (swarmState.swarmLeader == null || !swarmState.swarmLeader.isAlive()) {
            return false; // Leader is dead or null
        }

        // Check if the leader is still in the swarm
        return nearby.contains(swarmState.swarmLeader);
    }

    /**
     * Updates the swarm and ensures a valid leader is chosen.
     *
     * @param nearby A list of nearby jelly entities to update the swarm with.
     */
    public void updateSwarm(List<JellyEntity> nearby) {
        if (nearby.size() >= StickyResourcesConfig.JELLY_SWARM_MIN_SIZE.get()) {
            if (!isLeaderValid(nearby)) {
                chooseNewLeader(nearby); // Choose a new leader if the current one is dead
            }

            // Ensure all jellies in the group follow the leader
            for (JellyEntity jelly : nearby) {
                if (jelly.getGroupId() != null && jelly.getGroupId().equals(swarmState.swarmLeader.getGroupId())) {
                    jelly.setLeader(swarmState.swarmLeader);
                }
            }
        } else {
            // If the swarm is too small, disband the leader
            if (swarmState.swarmLeader != null) {
                swarmState.swarmLeader.removeEffect(MobEffects.GLOWING);
                swarmState.swarmLeader = null;
            }
        }
    }
}
