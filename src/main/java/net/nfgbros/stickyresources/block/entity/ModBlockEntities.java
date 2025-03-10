package net.nfgbros.stickyresources.block.entity;

import ca.weblite.objc.Proxy;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    // Create a DeferredRegister for registering block entities
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, StickyResources.MOD_ID);

    // Register the Gem Polishing Station Block Entity
    public static final RegistryObject<BlockEntityType<GemPolishingStationBlockEntity>> GEM_POLISHING_BE =
            BLOCK_ENTITIES.register("gem_polishing_be", () ->
                    BlockEntityType.Builder.of(GemPolishingStationBlockEntity::new,
                            ModBlocks.GEM_POLISHING_STATION.get()).build(null));

    // Register the Jelly Storage Block Entity
    public static final RegistryObject<BlockEntityType<GemPolishingStationBlockEntity>> JELLY_STORAGE_BE =
            BLOCK_ENTITIES.register("jelly_storage_be", () ->
                    BlockEntityType.Builder.of(GemPolishingStationBlockEntity::new,
                            ModBlocks.JELLY_STORAGE_BE.get()).build(null));

    // Method to register block entities to the event bus
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}