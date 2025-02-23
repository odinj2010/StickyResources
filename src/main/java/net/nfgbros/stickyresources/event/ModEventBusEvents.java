package net.nfgbros.stickyresources.event;

import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.entity.custom.RhinoEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StickyResources.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.RHINO.get(), RhinoEntity.createAttributes().build());

        event.put(ModEntities.JELLY.get(), JellyEntity.createAttributes().build());

        event.put(ModEntities.JELLY_BONE.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_COAL.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_COBBLESTONE.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_COPPER.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_DIAMOND.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_DIRT.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_ELECTRIC.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_EMERALD.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_ENDER_PEARL.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_GLASS.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_GOLD.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_GRAVEL.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_IRON.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_LAPIS.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_LAVA.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_OAK_LOG.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_OBSIDIAN.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_PRISMERINE.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_REDSTONE.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_SAND.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_SAPPHIRE.get(), JellyEntity.createAttributes().build());
        event.put(ModEntities.JELLY_WATER.get(), JellyEntity.createAttributes().build());


    }
}
