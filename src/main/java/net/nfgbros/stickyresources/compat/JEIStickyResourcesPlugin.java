package net.nfgbros.stickyresources.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.item.ModItems;
import net.nfgbros.stickyresources.recipe.ModRecipes;
import net.nfgbros.stickyresources.recipe.WashingStationRecipe;
import net.nfgbros.stickyresources.util.JellySummoningUtils;

import java.util.List;

@JeiPlugin
public class JEIStickyResourcesPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(StickyResources.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new WashingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new JellySummoningRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new JellyTransformationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<WashingStationRecipe> washingRecipes = recipeManager.getAllRecipesFor(ModRecipes.WASHING_STATION_TYPE.get());
        registration.addRecipes(WashingRecipeCategory.WASHING_TYPE, washingRecipes);

        // Summoning Recipes
        List<JellySummoningRecipeCategory.SummoningRecipe> summoningRecipes = JellySummoningUtils.getSummoningRequirements().entrySet().stream()
                .map(entry -> new JellySummoningRecipeCategory.SummoningRecipe(entry.getKey(), entry.getValue()))
                .toList();
        registration.addRecipes(JellySummoningRecipeCategory.TYPE, summoningRecipes);

        // Transformation Recipes
        List<JellyTransformationRecipeCategory.TransformationRecipe> transformationRecipes = new java.util.ArrayList<>();
        for (ModEntities.JellyType type : ModEntities.JellyType.values()) {
            type.getTransformations().forEach((item, data) -> {
                transformationRecipes.add(new JellyTransformationRecipeCategory.TransformationRecipe(type, item, data));
            });
        }
        registration.addRecipes(JellyTransformationRecipeCategory.TYPE, transformationRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WASHING_STATION.get()), WashingRecipeCategory.WASHING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.STICKY_CATALYST.get()), JellySummoningRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(Items.SLIME_BALL), JellyTransformationRecipeCategory.TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        // You could register click areas for the progress arrow here later
    }
}
