package net.nfgbros.stickyresources.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nfgbros.stickyresources.entity.custom.JellyCobblestoneEntity;
import net.nfgbros.stickyresources.entity.custom.JellyElectricEntity;
import net.nfgbros.stickyresources.entity.custom.JellyIronEntity;

import java.util.*;

@Mod.EventBusSubscriber
public class JellyStickEventHandler {
    private static final Map<Class<? extends Entity>, List<Class<? extends Entity>>> STICKY_PAIRS = new HashMap<>();
    private static final Set<String> PROCESSED_PAIRS = new HashSet<>();
    private static final int MERGE_THRESHOLD = 40; // Ticks before merging

    static {
        // Define your sticky pairs here
        STICKY_PAIRS.put(JellyElectricEntity.class, List.of(JellyCobblestoneEntity.class, JellyIronEntity.class));
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        Entity entity = event.getEntity();
        Level world = entity.getCommandSenderWorld();

        if (world instanceof ServerLevel serverLevel) {
            STICKY_PAIRS.forEach((firstType, secondTypes) -> {
                if (firstType.isInstance(entity)) {
                    secondTypes.forEach(secondType -> handleNearbyJellies(serverLevel, (Mob) entity, secondType));
                } else if (secondTypes.stream().anyMatch(t -> t.isInstance(entity))) {
                    STICKY_PAIRS.entrySet().stream()
                            .filter(entry -> entry.getValue().contains(entity.getClass()))
                            .findFirst()
                            .map(Map.Entry::getKey)
                            .ifPresent(targetType -> handleNearbyJellies(serverLevel, (Mob) entity, targetType));
                }
            });
        }
    }

    private static void handleNearbyJellies(ServerLevel serverLevel, Mob entity, Class<? extends Entity> targetType) {
        for (Entity nearbyEntity : serverLevel.getEntities(entity, entity.getBoundingBox().inflate(2.0), e -> targetType.isInstance(e))) {
            if (!(nearbyEntity instanceof Mob targetMob)) continue; // Ensure it's a Mob

            // Generate a unique key to prevent duplicate handling
            String pairKey = generatePairKey(entity, targetMob);
            if (PROCESSED_PAIRS.contains(pairKey)) continue;

            JellyStickHandler.handleJellyInteraction(serverLevel, entity, targetMob);
            PROCESSED_PAIRS.add(pairKey);
        }
    }

    private static String generatePairKey(Entity e1, Entity e2) {
        int id1 = Math.min(e1.getId(), e2.getId());
        int id2 = Math.max(e1.getId(), e2.getId());
        return id1 + "-" + id2;
    }
}
