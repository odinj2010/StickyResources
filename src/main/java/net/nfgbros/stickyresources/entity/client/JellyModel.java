package net.nfgbros.stickyresources.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class JellyModel<T extends Entity> extends HierarchicalModel<T> {

    // Define the layer location for this model
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "jellyentity"), "main");

    // Model parts
    private final ModelPart root; // The root part of the model
    private final ModelPart jelly_inner; // Inner part of the jelly entity
    private final ModelPart jelly_outer; // Outer part of the jelly entity

    // Constructor to initialize model parts
    public JellyModel(ModelPart root) {
        this.root = root;
        this.jelly_inner = root.getChild("jelly_inner");
        this.jelly_outer = root.getChild("jelly_outer");
    }

    // Create the layer definition for the model
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition(); // Create the mesh definition
        PartDefinition partdefinition = meshdefinition.getRoot(); // Get the root part definition

        // Define the inner part of the jelly
        partdefinition.addOrReplaceChild("jelly_inner", CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-3.0F, 1.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)) // Main cube
                        .texOffs(32, 0).addBox(-3.3F, 4.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)) // Detail cube 1
                        .texOffs(32, 4).addBox(1.3F, 4.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)) // Detail cube 2
                        .texOffs(32, 8).addBox(0.0F, 2.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), // Detail cube 3
                PartPose.offset(0.0F, 1.0F, 0.0F)); // No offset

        // Define the outer part of the jelly (only the main cube)
        partdefinition.addOrReplaceChild("jelly_outer", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, 1.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), // Main cube
                PartPose.offset(0.0F, 0.0F, 0.0F)); // No offset

        return LayerDefinition.create(meshdefinition, 64, 32); // Create the layer definition with texture size 64x32
    }

    // Set up animations for the model (currently empty)
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose); // Reset all poses
        // Add animation logic here if needed
    }

    // Render the model to the buffer
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        jelly_inner.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha); // Render inner part
        jelly_outer.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha); // Render outer part
    }

    // Get the root part of the model
    @Override
    public ModelPart root() {
        return this.root;
    }
}