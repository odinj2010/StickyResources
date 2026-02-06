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
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.item.ModItems;

public class JellyTransformationRecipeCategory implements IRecipeCategory<JellyTransformationRecipeCategory.TransformationRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(StickyResources.MOD_ID, "jelly_transformation");
    public static final RecipeType<TransformationRecipe> TYPE = new RecipeType<>(UID, TransformationRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public JellyTransformationRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(120, 40);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.SLIME_BALL));
    }

    @Override
    public RecipeType<TransformationRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.sticky_resources.jelly_transformation");
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
    public void setRecipe(IRecipeLayoutBuilder builder, TransformationRecipe recipe, IFocusGroup focuses) {
        // Input Jelly
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 12).addItemStack(getSpawnEgg(recipe.input()));
        
        // Item
        builder.addSlot(RecipeIngredientRole.CATALYST, 50, 12).addItemStack(new ItemStack(recipe.item(), recipe.data().cost()));

        // Result Jelly
        builder.addSlot(RecipeIngredientRole.OUTPUT, 90, 12).addItemStack(getSpawnEgg(recipe.data().result()));
    }

    private ItemStack getSpawnEgg(ModEntities.JellyType type) {
        return ModItems.ITEMS.getEntries().stream()
                .filter(item -> item.getId().getPath().equals("jelly_" + type.name().toLowerCase() + "_spawn_egg"))
                .map(reg -> new ItemStack(reg.get()))
                .findFirst().orElse(ItemStack.EMPTY);
    }

    @Override
    public void draw(TransformationRecipe recipe, mezz.jei.api.gui.ingredient.IRecipeSlotsView recipeSlotsView, net.minecraft.client.gui.GuiGraphics stack, double mouseX, double mouseY) {
        stack.drawString(net.minecraft.client.Minecraft.getInstance().font, "+", 35, 15, 0xFF808080);
        stack.drawString(net.minecraft.client.Minecraft.getInstance().font, "->", 75, 15, 0xFF808080);
    }

    public record TransformationRecipe(ModEntities.JellyType input, net.minecraft.world.item.Item item, ModEntities.JellyType.TransformationData data) {}
}
