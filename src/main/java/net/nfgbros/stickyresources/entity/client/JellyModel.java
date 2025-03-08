package net.nfgbros.stickyresources.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.nfgbros.stickyresources.entity.custom.LookableEntity;

public class JellyModel<T extends Entity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("sticky_resources", "jellyentity"), "main");
    private final ModelPart root;
    private final ModelPart jelly_inner;
    private final ModelPart jelly_outer;

    public JellyModel(ModelPart root) {
        this.root = root;
        this.jelly_inner = root.getChild("jelly_inner");
        this.jelly_outer = root.getChild("jelly_outer");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("jelly_inner", CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-3.0F, 1.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 0).addBox(-3.3F, 4.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 4).addBox(1.3F, 4.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 8).addBox(0.0F, 2.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 1.0F, 0.0F));

        partdefinition.addOrReplaceChild("jelly_outer", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, 1.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        float scaleFactor = 1.1f + 0.2f * Mth.sin(ageInTicks * 0.6f);

        this.jelly_inner.xScale = scaleFactor;
        this.jelly_inner.yScale = 1.0f / scaleFactor;
        this.jelly_outer.xScale = scaleFactor;
        this.jelly_outer.yScale = 1.0f / scaleFactor;

        if (entity instanceof LookableEntity) {
            this.root.yRot = ((LookableEntity) entity).getLookingYRot() * ((float) Math.PI / 180F);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root().render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}