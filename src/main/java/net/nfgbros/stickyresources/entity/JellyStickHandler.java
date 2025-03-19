package net.nfgbros.stickyresources.entity;

import net.minecraft.core.particles.ParticleTypes;
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
    private static final int MERGE_THRESHOLD = StickyResourcesConfig.JELLY_MERGE_THRESHOLD.get(); // Ticks before merging

    public static void handleJellyInteraction(ServerLevel world, Mob firstJelly, Mob secondJelly) {
        double distance = firstJelly.distanceTo(secondJelly);

        if (distance <= StickyResourcesConfig.JELLY_INTERACTION_BLOCK_DISTANCE.get()) {
            stickJellies(firstJelly, secondJelly);
            spawnBreakingParticles(world, firstJelly);
            spawnBreakingParticles(world, secondJelly);

            Entity combinedKey = getCombinedKey(firstJelly, secondJelly);
            STICKING_DURATION.put(combinedKey, STICKING_DURATION.getOrDefault(combinedKey, 0) + 1);

            if (STICKING_DURATION.get(combinedKey) >= StickyResourcesConfig.JELLY_MERGE_THRESHOLD.get()) {
                mergeJellies(world, firstJelly, secondJelly);
                STICKING_DURATION.remove(combinedKey);
            }
        } else {
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

    private static void spawnBreakingParticles(ServerLevel world, Entity entity) {
        world.sendParticles(ParticleTypes.ELECTRIC_SPARK, entity.getX(), entity.getY(), entity.getZ(), 5, 0.5, 0.5, 0.5, 0.1);
    }

    private static void mergeJellies(ServerLevel world, Mob firstJelly, Mob secondJelly) {
        EntityType<? extends Entity> newEntityType = getMergedEntityType(firstJelly.getType(), secondJelly.getType());

        if (newEntityType != null) {
            Entity mergedEntity = newEntityType.create(world);
            if (mergedEntity != null) {
                Vec3 midpoint = firstJelly.position().add(secondJelly.position()).scale(0.5).add(0, 0.5, 0);

                firstJelly.setDeltaMovement(0, 0, 0);
                secondJelly.setDeltaMovement(0, 0, 0);
                mergedEntity.setPos(midpoint.x, midpoint.y, midpoint.z);
                firstJelly.discard();
                secondJelly.discard();
                world.addFreshEntity(mergedEntity);
            }
        }
    }

    private static EntityType<? extends Entity> getMergedEntityType(EntityType<?> firstType, EntityType<?> secondType) {
        if ((firstType == ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.ELECTRIC).get() && secondType == ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.RAWIRON).get() ||
                (firstType == ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.RAWIRON).get() && secondType == ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.ELECTRIC).get()))) {
            return ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.MAGNET).get();
        }
        return null;
    }

    private static Entity getCombinedKey(Entity first, Entity second) {
        return first.getId() < second.getId() ? first : second;
    }
}
