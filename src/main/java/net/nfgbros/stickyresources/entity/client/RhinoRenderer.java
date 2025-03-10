package net.nfgbros.stickyresources.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.entity.custom.RhinoEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for the Rhino entity, defining its model, texture, and render behavior.
 */
public class RhinoRenderer extends MobRenderer<RhinoEntity, RhinoModel<RhinoEntity>> {

    /**
     * Constructor for the RhinoRenderer.
     *
     * @param pContext The rendering context provided by Minecraft.
     */
    public RhinoRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new RhinoModel<>(pContext.bakeLayer(ModModelLayers.RHINO_LAYER)), 2f); // Scale factor for rendering
    }

    /**
     * Gets the texture location for the Rhino entity.
     *
     * @param pEntity The Rhino entity.
     * @return The resource location of the texture.
     */
    @Override
    public ResourceLocation getTextureLocation(RhinoEntity pEntity) {
        return new ResourceLocation(StickyResources.MOD_ID, "textures/entity/rhino.png");
    }

    /**
     * Renders the Rhino entity, applying scaling for baby entities.
     *
     * @param pEntity       The Rhino entity to render.
     * @param pEntityYaw    The yaw of the entity.
     * @param pPartialTicks Partial tick time.
     * @param pMatrixStack  The PoseStack for transformations.
     * @param pBuffer       The buffer for rendering.
     * @param pPackedLight  Packed light information.
     */
    @Override
    public void render(RhinoEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f); // Scale down for baby size
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
