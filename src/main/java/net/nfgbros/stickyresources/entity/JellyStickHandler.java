package net.nfgbros.stickyresources.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class JellyStickHandler {

    private static final Map<EntityPair, Integer> STICKING_DURATION = new ConcurrentHashMap<>();
    private static final int MERGE_THRESHOLD = 40; // Ticks before merging

    public static void handleJellyInteraction(ServerLevel world, Mob firstJelly, Mob secondJelly) {
        double distance = firstJelly.distanceTo(secondJelly);

        if (distance <= 2.0) {
            EntityPair pairKey = new EntityPair(firstJelly, secondJelly);
            stickJellies(firstJelly, secondJelly, distance);

            // Track sticking duration
            STICKING_DURATION.put(pairKey, STICKING_DURATION.getOrDefault(pairKey, 0) + 1);

            if (STICKING_DURATION.get(pairKey) >= MERGE_THRESHOLD) {
                mergeJellies(world, firstJelly, secondJelly);
                STICKING_DURATION.remove(pairKey);
            }
        } else {
            STICKING_DURATION.remove(new EntityPair(firstJelly, secondJelly));
        }
    }

    private static void stickJellies(Mob firstJelly, Mob secondJelly, double distance) {
        Vec3 firstPos = firstJelly.position();
        Vec3 secondPos = secondJelly.position();

        Vec3 direction = secondPos.subtract(firstPos).normalize().scale(0.05); // Smooth movement

        firstJelly.setDeltaMovement(direction);
        secondJelly.setDeltaMovement(direction.reverse());
    }

    private static void mergeJellies(ServerLevel world, Mob firstJelly, Mob secondJelly) {
        EntityType<? extends Entity> newEntityType = getMergedEntityType(firstJelly.getType(), secondJelly.getType());

        if (newEntityType != null) {
            Entity mergedEntity = newEntityType.create(world);
            if (mergedEntity != null) {
                Vec3 midpoint = firstJelly.position().add(secondJelly.position()).scale(0.5);
                mergedEntity.setPos(midpoint.x, midpoint.y, midpoint.z);
                world.addFreshEntity(mergedEntity);

                firstJelly.discard();
                secondJelly.discard();
            }
        }
    }

    private static EntityType<? extends Entity> getMergedEntityType(EntityType<?> firstType, EntityType<?> secondType) {
        if ((firstType == ModEntities.JELLY_ELECTRIC.get() && secondType == ModEntities.JELLY_IRON.get()) ||
                (firstType == ModEntities.JELLY_IRON.get() && secondType == ModEntities.JELLY_ELECTRIC.get())) {
            return ModEntities.JELLY_GRAVEL.get();
        }
        return null;
    }

    private static class EntityPair {
        private final int id1;
        private final int id2;

        public EntityPair(Entity e1, Entity e2) {
            this.id1 = Math.min(e1.getId(), e2.getId());
            this.id2 = Math.max(e1.getId(), e2.getId());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EntityPair that = (EntityPair) o;
            return id1 == that.id1 && id2 == that.id2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id1, id2);
        }
    }
}
