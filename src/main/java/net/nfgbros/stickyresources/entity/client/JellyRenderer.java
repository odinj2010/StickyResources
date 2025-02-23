package net.nfgbros.stickyresources.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class JellyRenderer extends MobRenderer<JellyEntity, JellyModel<JellyEntity>> {
    public JellyRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new JellyModel<>(pContext.bakeLayer(ModModelLayers.JELLY_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(JellyEntity pEntity) {
        return new ResourceLocation(StickyResources.MOD_ID, "textures/entity/jelly.png");
    }

    @Override
    public void render(JellyEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
