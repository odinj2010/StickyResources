package net.nfgbros.stickyresources.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        // Add your recipes here

        // Sapphire
        oreSmelting(pWriter, List.of(ModItems.RAW_SAPPHIRE.get()), RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 200, "sapphire");
        oreBlasting(pWriter, List.of(ModItems.RAW_SAPPHIRE.get()), RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 100, "sapphire");

        // Sapphire Block
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SAPPHIRE_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.SAPPHIRE.get())
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 9)
                .requires(ModBlocks.SAPPHIRE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SAPPHIRE_BLOCK.get()), has(ModBlocks.SAPPHIRE_BLOCK.get()))
                .save(pWriter);


        //Sticky cobblestone
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STICKY_COBBLESTONE.get())
                .pattern("ss")
                .pattern("sc")
                .define('c', Blocks.COBBLESTONE)
                .define('s', Items.SLIME_BALL)
                .unlockedBy(getHasName(ModBlocks.STICKY_COBBLESTONE.get()), has(ModBlocks.STICKY_COBBLESTONE.get()))
                .save(pWriter);
        // Sticky Dirt
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STICKY_DIRT.get())
                .pattern("ss")
                .pattern("sd")
                .define('d', Blocks.DIRT)
                .define('s', Items.SLIME_BALL)
                .unlockedBy(getHasName(ModBlocks.STICKY_DIRT.get()), has(ModBlocks.STICKY_DIRT.get()))
                .save(pWriter);

        // 2. Sticky Glass
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STICKY_GLASS.get())
                .pattern("ss")
                .pattern("sg")
                .define('g', Blocks.GLASS)
                .define('s', Items.SLIME_BALL)
                .unlockedBy(getHasName(ModBlocks.STICKY_GLASS.get()), has(ModBlocks.STICKY_GLASS.get()))
                .save(pWriter);

        // 3. Sticky Gravel
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STICKY_GRAVEL.get())
                .pattern("ss")
                .pattern("sg")
                .define('g', Blocks.GRAVEL)
                .define('s', Items.SLIME_BALL)
                .unlockedBy(getHasName(ModBlocks.STICKY_GRAVEL.get()), has(ModBlocks.STICKY_GRAVEL.get()))
                .save(pWriter);

        // 4. Sticky Oak Log
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STICKY_OAK_LOG.get())
                .pattern("ss")
                .pattern("sl")
                .define('l', Blocks.OAK_LOG)
                .define('s', Items.SLIME_BALL)
                .unlockedBy(getHasName(ModBlocks.STICKY_OAK_LOG.get()), has(ModBlocks.STICKY_OAK_LOG.get()))
                .save(pWriter);

        // 5. Sticky Obsidian
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STICKY_OBSIDIAN.get())
                .pattern("ss")
                .pattern("so")
                .define('o', Blocks.OBSIDIAN)
                .define('s', Items.SLIME_BALL)
                .unlockedBy(getHasName(ModBlocks.STICKY_OBSIDIAN.get()), has(ModBlocks.STICKY_OBSIDIAN.get()))
                .save(pWriter);

        // 6. Sticky Sand
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STICKY_SAND.get())
                .pattern("ss")
                .pattern("sa")
                .define('a', Blocks.SAND)
                .define('s', Items.SLIME_BALL)
                .unlockedBy(getHasName(ModBlocks.STICKY_SAND.get()), has(ModBlocks.STICKY_SAND.get()))
                .save(pWriter);

        // 7. Pine Cone
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.PINE_CONE.get())
                .requires(Items.STICK)
                .requires(Blocks.SPRUCE_LEAVES)
                .unlockedBy(getHasName(ModItems.PINE_CONE.get()), has(ModItems.PINE_CONE.get()))
                .save(pWriter);

        // 8. Metal Detector
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.METAL_DETECTOR.get())
                .pattern("rir")
                .pattern("rsr")
                .pattern(" i ")
                .define('i', Items.IRON_INGOT)
                .define('r', Items.REDSTONE)
                .define('s', Items.STICK)
                .unlockedBy(getHasName(ModItems.METAL_DETECTOR.get()), has(ModItems.METAL_DETECTOR.get()))
                .save(pWriter);

        // 9. Sapphire Block from Raw Sapphire Block (Gem Polishing Station)
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.RAW_SAPPHIRE_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SAPPHIRE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.RAW_SAPPHIRE_BLOCK.get()), has(ModBlocks.RAW_SAPPHIRE_BLOCK.get()))
                .save(pWriter, new ResourceLocation(StickyResources.MOD_ID, "sapphire_block_from_raw_sapphire_block_stonecutting"));

        // 10. Corn
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CORN.get())
                .requires(ModItems.CORN_SEEDS.get())
                .unlockedBy(getHasName(ModItems.CORN_SEEDS.get()), has(ModItems.CORN_SEEDS.get()))
                .save(pWriter);

        // 11. Strawberry
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.STRAWBERRY.get())
                .requires(ModItems.STRAWBERRY_SEEDS.get())
                .unlockedBy(getHasName(ModItems.STRAWBERRY_SEEDS.get()), has(ModItems.STRAWBERRY_SEEDS.get()))
                .save(pWriter);

        // 12. Bar Brawl Music Disc
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BAR_BRAWL_MUSIC_DISC.get())
                .requires(Items.MUSIC_DISC_OTHERSIDE)
                .requires(ModItems.SAPPHIRE.get())
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        // Add more recipes for other items (except spawn eggs)
    }

    // Helper methods for smelting and blasting recipes
    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            String recipeName = StickyResources.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike);
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                            pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, new ResourceLocation(recipeName));
        }
    }
}