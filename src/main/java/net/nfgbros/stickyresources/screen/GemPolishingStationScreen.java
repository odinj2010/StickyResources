package net.nfgbros.stickyresources.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.nfgbros.stickyresources.StickyResources;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GemPolishingStationScreen extends AbstractContainerScreen<GemPolishingStationMenu> {

    // Texture for the Gem Polishing Station GUI
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(StickyResources.MOD_ID, "textures/gui/gem_polishing_station_gui.png");

    // Constructor
    public GemPolishingStationScreen(GemPolishingStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    // Initialize screen components
    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000; // Push inventory label off-screen
        this.titleLabelY = 10000;    // Push title label off-screen
    }

    // Render the background layer of the screen
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        // Setup shader and texture
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        // Calculate center position for the GUI
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        // Draw the background texture
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        // Render the progress arrow
        renderProgressArrow(guiGraphics, x, y);
    }

    // Render the crafting progress arrow
    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 85, y + 30, 176, 0, 8, menu.getScaledProgress());
        }
    }

    // Render the screen, including tooltips
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics); // Render the background
        super.render(guiGraphics, mouseX, mouseY, delta); // Render the GUI components
        renderTooltip(guiGraphics, mouseX, mouseY); // Render tooltips
    }
}