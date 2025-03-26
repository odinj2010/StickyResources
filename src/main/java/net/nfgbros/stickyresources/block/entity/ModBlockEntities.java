package net.nfgbros.stickyresources.block.entity;

import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, StickyResources.MOD_ID);

    public static final RegistryObject<BlockEntityType<WashingStationBlockEntity>> WASHING_STATION_BE =
            BLOCK_ENTITIES.register("washing_station_be", () ->
                    BlockEntityType.Builder.of(WashingStationBlockEntity::new,
                            ModBlocks.WASHING_STATION.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}