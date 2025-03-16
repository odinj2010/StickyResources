package net.nfgbros.stickyresources.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nfgbros.stickyresources.entity.custom.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class JellyStickEventHandler {
    private static final Map<Class<? extends Entity>, List<Class<? extends Entity>>> STICKY_PAIRS = new HashMap<>();
    private static final int MERGE_THRESHOLD = 40; // Ticks before merging

    static {
        //Define your sticky pairs here
        STICKY_PAIRS.put(ElectricJellyEntity.class, List.of(CobblestoneJellyEntity.class, RawIronJellyEntity.class));
        STICKY_PAIRS.put(MagnetJellyEntity.class, List.of(RawIronJellyEntity.class));
        // Add more pairs as needed:
        // STICKY_PAIRS.put(JellyExampleEntity.class, List.of(JellyOtherExampleEntity.class, JellyAnotherExampleEntity.class));
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        Entity entity = event.getEntity();
        Level world = entity.getCommandSenderWorld();

        if (world instanceof ServerLevel serverLevel) {
            STICKY_PAIRS.forEach((firstType, secondTypes) -> {
                if (firstType.isInstance(entity)) {
                    secondTypes.forEach(secondType -> handleNearbyJellies(serverLevel, entity, secondType));
                } else if (secondTypes.stream().anyMatch(t -> t.isInstance(entity))) {
                    // Find the matching pair and handle it
                    STICKY_PAIRS.entrySet().stream()
                            .filter(entry -> entry.getValue().contains(entity.getClass()))
                            .findFirst()
                            .map(Map.Entry::getKey)
                            .ifPresent(targetType -> handleNearbyJellies(serverLevel, entity, targetType));
                }
            });
        }
    }

    private static void handleNearbyJellies(ServerLevel serverLevel, Entity entity, Class<? extends Entity> targetType) {
        for (Entity nearbyEntity : entity.getCommandSenderWorld().getEntities(entity, entity.getBoundingBox().inflate(2.0))) {
            if (targetType.isInstance(nearbyEntity)) {
                JellyStickHandler.handleJellyInteraction(
                        serverLevel,
                        (Mob) entity,
                        (Mob) nearbyEntity
                );
            }
        }
    }
}