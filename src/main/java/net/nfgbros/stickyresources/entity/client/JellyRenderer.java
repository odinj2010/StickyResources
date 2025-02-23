package net.nfgbros.stickyresources.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.nfgbros.stickyresources.entity.custom.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class JellyRenderer<T extends JellyEntity> extends MobRenderer<T, JellyModel<T>> {

    private final ResourceLocation defaultTexture;
    private final ResourceLocation boneTexture;
    private final ResourceLocation coalTexture;
    private final ResourceLocation cobblestoneTexture;
    private final ResourceLocation copperTexture;
    private final ResourceLocation diamondTexture;
    private final ResourceLocation dirtTexture;
    private final ResourceLocation electricTexture;
    private final ResourceLocation emeraldTexture;
    private final ResourceLocation enderpearlTexture;
    private final ResourceLocation glassTexture;
    private final ResourceLocation goldTexture;
    private final ResourceLocation gravelTexture;
    private final ResourceLocation ironTexture;
    private final ResourceLocation lapisTexture;
    private final ResourceLocation lavaTexture;
    private final ResourceLocation oakTexture;
    private final ResourceLocation obsidianTexture;
    private final ResourceLocation prismerineTexture;
    private final ResourceLocation redstoneTexture;
    private final ResourceLocation sandTexture;
    private final ResourceLocation sapphireTexture;
    private final ResourceLocation waterTexture;

    public JellyRenderer(EntityRendererProvider.Context pContext, ResourceLocation defaultTexture, ResourceLocation boneTexture,
                         ResourceLocation coalTexture,ResourceLocation cobblestoneTexture,ResourceLocation copperTexture,
                         ResourceLocation diamondTexture,ResourceLocation dirtTexture,ResourceLocation electricTexture,
                         ResourceLocation emeraldTexture,ResourceLocation enderpearlTexture,ResourceLocation glassTexture,
                         ResourceLocation goldTexture,ResourceLocation gravelTexture,ResourceLocation ironTexture,
                         ResourceLocation lapisTexture,ResourceLocation obsidianTexture, ResourceLocation prismerineTexture,
                         ResourceLocation redstoneTexture,ResourceLocation sandTexture,ResourceLocation sapphireTexture,
                         ResourceLocation waterTexture, ResourceLocation lavaTexture, ResourceLocation oakTexture) {
        super(pContext, new JellyModel<>(pContext.bakeLayer(ModModelLayers.JELLY_LAYER)), 0.5f);
        this.defaultTexture = defaultTexture;
        this.boneTexture = boneTexture;
        this.coalTexture = coalTexture;
        this.cobblestoneTexture = cobblestoneTexture;
        this.copperTexture = copperTexture;
        this.diamondTexture = diamondTexture;
        this.dirtTexture = dirtTexture;
        this.electricTexture = electricTexture;
        this.emeraldTexture = emeraldTexture;
        this.enderpearlTexture = enderpearlTexture;
        this.glassTexture = glassTexture;
        this.goldTexture = goldTexture;
        this.gravelTexture = gravelTexture;
        this.ironTexture = ironTexture;
        this.lapisTexture = lapisTexture;
        this.lavaTexture = lavaTexture;
        this.oakTexture = oakTexture;
        this.obsidianTexture = obsidianTexture;
        this.prismerineTexture = prismerineTexture;
        this.redstoneTexture = redstoneTexture;
        this.sandTexture = sandTexture;
        this.sapphireTexture = sapphireTexture;
        this.waterTexture = waterTexture;
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        if (pEntity instanceof JellyBoneEntity) {
            return boneTexture;
        } else if(pEntity instanceof JellyCoalEntity){
            return coalTexture;
        }else if(pEntity instanceof JellyCobblestoneEntity){
            return cobblestoneTexture;
        }else if(pEntity instanceof JellyCopperEntity){
            return copperTexture;
        }else if(pEntity instanceof JellyDiamondEntity){
            return diamondTexture;
        }else if(pEntity instanceof JellyDirtEntity){
            return dirtTexture;
        }else if(pEntity instanceof JellyElectricEntity){
            return electricTexture;
        }else if(pEntity instanceof JellyEmeraldEntity){
            return emeraldTexture;
        }else if(pEntity instanceof JellyEnderPearlEntity){
            return enderpearlTexture;
        }else if(pEntity instanceof JellyGlassEntity){
            return glassTexture;
        }else if(pEntity instanceof JellyGoldEntity){
            return goldTexture;
        }else if(pEntity instanceof JellyGravelEntity){
            return gravelTexture;
        }else if(pEntity instanceof JellyIronEntity){
            return ironTexture;
        }else if(pEntity instanceof JellyLapisEntity){
            return lapisTexture;
        }else if(pEntity instanceof JellyLavaEntity){
            return lavaTexture;
        }else if(pEntity instanceof JellyOakLogEntity){
            return oakTexture;
        }else if(pEntity instanceof JellyObsidianEntity){
            return obsidianTexture;
        }else if(pEntity instanceof JellyPrismerineEntity){
            return prismerineTexture;
        }else if(pEntity instanceof JellyRedstoneEntity){
            return redstoneTexture;
        }else if(pEntity instanceof JellySandEntity){
            return sandTexture;
        }else if(pEntity instanceof JellySapphireEntity){
            return sapphireTexture;
        }else if(pEntity instanceof JellyWaterEntity){
            return waterTexture;
        }else{
            return defaultTexture;
        }

    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}