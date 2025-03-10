package net.nfgbros.stickyresources.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.nfgbros.stickyresources.StickyResources;

public class JellyStorageBlockScreen extends AbstractContainerScreen<JellyStorageBlockContainer> {
    // Texture for the GUI background
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(StickyResources.MOD_ID, "textures/gui/jelly_storage_block_gui.png");

    public JellyStorageBlockScreen(JellyStorageBlockContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        // Move inventory and title labels off-screen
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        // Set up shaders and texture for the background rendering
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        // Calculate position for centering the background
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        // Render the background texture
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        // Render the background
        renderBackground(guiGraphics);
        // Render the screen components
        super.render(guiGraphics, mouseX, mouseY, delta);
        // Render tooltips if necessary
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}