package net.nfgbros.stickyresources.entity.client;

import net.nfgbros.stickyresources.StickyResources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {

    // ModelLayerLocation for the Rhino entity
    public static final ModelLayerLocation RHINO_LAYER = new ModelLayerLocation(
            new ResourceLocation(StickyResources.MOD_ID, "rhino_layer"), "main");

    // ModelLayerLocation for the Jelly entity
    public static final ModelLayerLocation JELLY_LAYER = new ModelLayerLocation(
            new ResourceLocation(StickyResources.MOD_ID, "jelly_layer"), "main");
}
