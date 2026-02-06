package net.nfgbros.stickyresources.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.item.ModItems;
import net.nfgbros.stickyresources.util.JellySummoningUtils;

public class JellySummoningRecipeCategory implements IRecipeCategory<JellySummoningRecipeCategory.SummoningRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(StickyResources.MOD_ID, "jelly_summoning");
    public static final RecipeType<SummoningRecipe> TYPE = new RecipeType<>(UID, SummoningRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public JellySummoningRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(120, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.STICKY_CATALYST.get()));
    }

    @Override
    public RecipeType<SummoningRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.sticky_resources.jelly_summoning");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SummoningRecipe recipe, IFocusGroup focuses) {
        // Catalyst
        builder.addSlot(RecipeIngredientRole.CATALYST, 10, 22).addItemStack(new ItemStack(recipe.requirement().catalystItem));
        
        // Structure Blocks
        builder.addSlot(RecipeIngredientRole.INPUT, 40, 10).addItemStack(new ItemStack(recipe.requirement().topBlock));
        builder.addSlot(RecipeIngredientRole.INPUT, 40, 28).addItemStack(new ItemStack(recipe.requirement().baseBlock));
        builder.addSlot(RecipeIngredientRole.INPUT, 40, 46).addItemStack(new ItemStack(Blocks.STONE)); // Pillars

        // Result
        ItemStack spawnEgg = ModItems.ITEMS.getEntries().stream()
                .filter(item -> item.getId().getPath().equals("jelly_" + recipe.type().name().toLowerCase() + "_spawn_egg"))
                .map(reg -> new ItemStack(reg.get()))
                .findFirst().orElse(ItemStack.EMPTY);
        
        builder.addSlot(RecipeIngredientRole.OUTPUT, 90, 22).addItemStack(spawnEgg);
    }

    @Override
    public void draw(SummoningRecipe recipe, mezz.jei.api.gui.ingredient.IRecipeSlotsView recipeSlotsView, net.minecraft.client.gui.GuiGraphics stack, double mouseX, double mouseY) {
        stack.drawString(net.minecraft.client.Minecraft.getInstance().font, "+", 30, 25, 0xFF808080);
        stack.drawString(net.minecraft.client.Minecraft.getInstance().font, "->", 70, 25, 0xFF808080);
    }

    public record SummoningRecipe(ModEntities.JellyType type, JellySummoningUtils.SummoningRequirement requirement) {}
}
