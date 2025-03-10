package net.nfgbros.stickyresources.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import net.nfgbros.stickyresources.StickyResourcesConfig;

public class JellyStickHandler {

    private static final Map<Entity, Integer> STICKING_DURATION = new HashMap<>();
    private static final int MERGE_THRESHOLD = 40; // Ticks before merging

    public static void handleJellyInteraction(ServerLevel world, Mob firstJelly, Mob secondJelly) {
        // Calculate the distance between the two entities
        double distance = firstJelly.distanceTo(secondJelly);

        // If within 2 blocks, make them interact/stick
        if (distance <= 2.0) {
            stickJellies(firstJelly, secondJelly);

            // Track sticking duration
            Entity combinedKey = getCombinedKey(firstJelly, secondJelly);
            STICKING_DURATION.put(combinedKey, STICKING_DURATION.getOrDefault(combinedKey, 0) + 1);

            // Check if it's time to merge
            if (STICKING_DURATION.get(combinedKey) >= MERGE_THRESHOLD) {
                mergeJellies(world, firstJelly, secondJelly);
                STICKING_DURATION.remove(combinedKey); // Reset duration
            }

        } else {
            // If they are too far apart, reset the sticking duration
            STICKING_DURATION.remove(getCombinedKey(firstJelly, secondJelly));
        }
    }

    private static void stickJellies(Mob firstJelly, Mob secondJelly) {
        Vec3 midpoint = firstJelly.position().add(secondJelly.position()).scale(0.5);
        firstJelly.setPos(midpoint.x, midpoint.y, midpoint.z);
        secondJelly.setPos(midpoint.x, midpoint.y, midpoint.z);

        firstJelly.setDeltaMovement(0, 0, 0);
        secondJelly.setDeltaMovement(0, 0, 0);
    }

    private static void mergeJellies(ServerLevel world, Mob firstJelly, Mob secondJelly) {
        // Determine the new entity type based on the combination
        EntityType<? extends Entity> newEntityType = getMergedEntityType(firstJelly.getType(), secondJelly.getType());

        if (newEntityType != null) {
            // Create the new merged entity
            Entity mergedEntity = newEntityType.create(world);
            if (mergedEntity != null) {
                // Set the position of the merged entity
                Vec3 midpoint = firstJelly.position().add(secondJelly.position()).scale(0.5);
                mergedEntity.setPos(midpoint.x, midpoint.y, midpoint.z);

                // Add the new entity to the world
                world.addFreshEntity(mergedEntity);

                // Remove the original jellies
                firstJelly.discard();
                secondJelly.discard();
            }
        }
    }

    private static EntityType<? extends Entity> getMergedEntityType(EntityType<?> firstType, EntityType<?> secondType) {
        //Electric Jelly and Iron Jelly
        if ((firstType == ModEntities.JELLY_ELECTRIC.get() && secondType == ModEntities.JELLY_IRON.get()) ||
                (firstType == ModEntities.JELLY_IRON.get() && secondType == ModEntities.JELLY_ELECTRIC.get())) {
            // Example: Electric Jelly and Iron Jelly combine to make Gravel Jelly... Will be changed later, just testing right now.
            return ModEntities.JELLY_GRAVEL.get();
        }
        // Add more combinations as needed...
        return null; // Return null if no matching combination is found
    }

    private static Entity getCombinedKey(Entity first, Entity second) {
        // Ensure consistent key generation regardless of order
        return first.getId() < second.getId() ? first : second;
    }
}