package net.nfgbros.stickyresources.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.entity.ModEntities.JellyType;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumMap;
import java.util.Map;

public class JellyRenderer extends MobRenderer<JellyEntity, SlimeModel<JellyEntity>> {

    private static final Map<JellyType, ResourceLocation> JELLY_TEXTURES = new EnumMap<>(JellyType.class);

    static {
        for (JellyType type : JellyType.values()) {
            JELLY_TEXTURES.put(type, new ResourceLocation(StickyResources.MOD_ID, "textures/entity/jelly/jelly_" + type.name().toLowerCase() + ".png"));
        }
    }

    public JellyRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel<>(context.bakeLayer(ModelLayers.SLIME)), 0.5f);
        this.addLayer(new SlimeOuterLayer<>(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(JellyEntity entity) {
        return JELLY_TEXTURES.getOrDefault(entity.getJellyType(), JELLY_TEXTURES.get(JellyType.DEFAULT));
    }

    @Override
    public void render(JellyEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       net.minecraft.client.renderer.MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose(); // Save the current transformation state

        if (entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);

        poseStack.popPose(); // Restore the previous transformation state
    }
}