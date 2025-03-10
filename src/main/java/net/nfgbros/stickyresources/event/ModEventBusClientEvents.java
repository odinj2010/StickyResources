package net.nfgbros.stickyresources.event;

import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.entity.client.JellyModel;
import net.nfgbros.stickyresources.entity.client.ModModelLayers;
import net.nfgbros.stickyresources.entity.client.RhinoModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// Event handler for client-specific mod events
@Mod.EventBusSubscriber(modid = StickyResources.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    // Registers custom layer definitions for entities
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Register Rhino layer
        event.registerLayerDefinition(ModModelLayers.RHINO_LAYER, RhinoModel::createBodyLayer);
        // Register Jelly layer
        event.registerLayerDefinition(ModModelLayers.JELLY_LAYER, JellyModel::createBodyLayer);
    }
}
