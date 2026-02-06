package net.nfgbros.stickyresources.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.item.ModItems;
import net.nfgbros.stickyresources.recipe.ModRecipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        // Sapphire
        oreSmelting(pWriter, List.of(ModItems.RAW_SAPPHIRE.get()), RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 200, "sapphire");
        oreBlasting(pWriter, List.of(ModItems.RAW_SAPPHIRE.get()), RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 100, "sapphire");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 9)
                .requires(ModBlocks.SAPPHIRE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SAPPHIRE_BLOCK.get()), has(ModBlocks.SAPPHIRE_BLOCK.get()))
                .save(pWriter);


        //
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STICKY_CATALYST.get())
                .pattern("EPE")
                .pattern("PSP")
                .pattern("EPE")
                .define('E', Items.ECHO_SHARD)
                .define('S', Items.SLIME_BALL)
                .define('P', Items.PRISMARINE_SHARD)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.SLIME_BALL))
                .save(pWriter);

        //Sapphire Armor
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SAPPHIRE_HELMET.get())
                .pattern("SSS")
                .pattern("S S")
                .define('S', ModItems.SAPPHIRE.get())
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SAPPHIRE_CHESTPLATE.get())
                .pattern("S S")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.SAPPHIRE.get())
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SAPPHIRE_LEGGINGS.get())
                .pattern("SSS")
                .pattern("S S")
                .pattern("S S")
                .define('S', ModItems.SAPPHIRE.get())
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SAPPHIRE_BOOTS.get())
                .pattern("S S")
                .pattern("S S")
                .define('S', ModItems.SAPPHIRE.get())
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        // Sapphire Tools
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SAPPHIRE_PICKAXE.get())
                .pattern("SSS")
                .pattern(" s ")
                .pattern(" s ")
                .define('S', ModItems.SAPPHIRE.get())
                .define('s', Items.STICK)
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SAPPHIRE_STAFF.get())
                .pattern(" SS")
                .pattern(" sS")
                .pattern("s  ")
                .define('S', ModItems.SAPPHIRE.get())
                .define('s', Items.STICK)
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SAPPHIRE_AXE.get())
                .pattern("SS")
                .pattern("Ss")
                .pattern(" s")
                .define('S', ModItems.SAPPHIRE.get())
                .define('s', Items.STICK)
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SAPPHIRE_SWORD.get())
                .pattern("S")
                .pattern("S")
                .pattern("s")
                .define('S', ModItems.SAPPHIRE.get())
                .define('s', Items.STICK)
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SAPPHIRE_SHOVEL.get())
                .pattern("S")
                .pattern("s")
                .pattern("s")
                .define('S', ModItems.SAPPHIRE.get())
                .define('s', Items.STICK)
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SAPPHIRE_HOE.get())
                .pattern("SS")
                .pattern(" s")
                .pattern(" s")
                .define('S', ModItems.SAPPHIRE.get())
                .define('s', Items.STICK)
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        //Utility
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.WASHING_STATION.get())///Item Washing Station**
                .pattern("OOO")
                .pattern("SWS")
                .pattern("I I")
                .define('O', Items.OBSIDIAN)
                .define('S', ModItems.SAPPHIRE.get())
                .define('I', Items.IRON_BLOCK)
                .define('W', Items.WATER_BUCKET)
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.METAL_DETECTOR.get())
                .pattern("gsi")
                .pattern(" sr")
                .pattern("isi")
                .define('i', Items.IRON_INGOT)
                .define('s', Items.STICK)
                .define('g', Items.GLASS_PANE)
                .define('r', Items.REDSTONE)
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(pWriter);

        //Food Items
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.STRAWBERRY_SEEDS.get(), 1)
                .requires(ModItems.STRAWBERRY.get())
                .unlockedBy(getHasName(ModItems.STRAWBERRY.get()), has(ModItems.STRAWBERRY.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CORN_SEEDS.get(), 1)
                .requires(ModItems.CORN.get())
                .unlockedBy(getHasName(ModItems.CORN.get()), has(ModItems.CORN.get()))
                .save(pWriter);

        // Washing Recipes
        washing(pWriter, Ingredient.of(ModItems.STICKY_AMETHYST.get()), new ItemStack(Items.AMETHYST_SHARD), "sticky_amethyst");
        washing(pWriter, Ingredient.of(ModItems.STICKY_BONE_MEAL.get()), new ItemStack(Items.BONE_MEAL), "sticky_bone_meal");
        washing(pWriter, Ingredient.of(ModItems.JELLY_CAKE.get()), new ItemStack(Items.CAKE), "sticky_cake");
        washing(pWriter, Ingredient.of(ModItems.STICKY_CHARCOAL.get()), new ItemStack(Items.CHARCOAL), "sticky_charcoal");
        washing(pWriter, Ingredient.of(ModItems.STICKY_COAL.get()), new ItemStack(Items.COAL), "sticky_coal");
        washing(pWriter, Ingredient.of(ModItems.STICKY_BEEF.get()), new ItemStack(Items.BEEF), "sticky_beef");
        washing(pWriter, Ingredient.of(ModItems.STICKY_DIAMOND.get()), new ItemStack(Items.DIAMOND), "sticky_diamond");
        washing(pWriter, Ingredient.of(ModItems.STICKY_EMERALD.get()), new ItemStack(Items.EMERALD), "sticky_emerald");
        washing(pWriter, Ingredient.of(ModItems.STICKY_ENDER_PEARL.get()), new ItemStack(Items.ENDER_PEARL), "sticky_ender_pearl");
        washing(pWriter, Ingredient.of(ModItems.STICKY_GRASS.get()), new ItemStack(Items.GRASS), "sticky_grass");
        washing(pWriter, Ingredient.of(ModItems.STICKY_RAW_COPPER.get()), new ItemStack(Items.RAW_COPPER), "sticky_raw_copper");
        washing(pWriter, Ingredient.of(ModItems.STICKY_RAW_GOLD.get()), new ItemStack(Items.RAW_GOLD), "sticky_raw_gold");
        washing(pWriter, Ingredient.of(ModItems.STICKY_RAW_IRON.get()), new ItemStack(Items.RAW_IRON), "sticky_raw_iron");
        washing(pWriter, Ingredient.of(ModItems.STICKY_RAW_SAPPHIRE.get()), new ItemStack(ModItems.RAW_SAPPHIRE.get()), "sticky_raw_sapphire");
        washing(pWriter, Ingredient.of(ModItems.STICKY_LAPIS_LAZULI.get()), new ItemStack(Items.LAPIS_LAZULI), "sticky_lapis_lazuli");
        washing(pWriter, Ingredient.of(ModItems.STICKY_PRISMERINE_CRYSTALS.get()), new ItemStack(Items.PRISMARINE_CRYSTALS), "sticky_prismerine_crystals");
        washing(pWriter, Ingredient.of(ModItems.STICKY_RED_MUSHROOM.get()), new ItemStack(Items.RED_MUSHROOM), "sticky_red_mushroom");
        washing(pWriter, Ingredient.of(ModItems.STICKY_REDSTONE_DUST.get()), new ItemStack(Items.REDSTONE), "sticky_redstone_dust");
        washing(pWriter, Ingredient.of(ModItems.STICKY_STRAWBERRY.get()), new ItemStack(ModItems.STRAWBERRY.get()), "sticky_strawberry");
        washing(pWriter, Ingredient.of(ModItems.STICKY_ROTTEN_FLESH.get()), new ItemStack(Items.ROTTEN_FLESH), "sticky_rotten_flesh");
        washing(pWriter, Ingredient.of(ModBlocks.STICKY_COBBLESTONE.get()), new ItemStack(Blocks.COBBLESTONE), "sticky_cobblestone");
        washing(pWriter, Ingredient.of(ModBlocks.STICKY_DIRT.get()), new ItemStack(Blocks.DIRT), "sticky_dirt");
        washing(pWriter, Ingredient.of(ModBlocks.STICKY_GLASS.get()), new ItemStack(Blocks.GLASS), "sticky_glass");
        washing(pWriter, Ingredient.of(ModBlocks.STICKY_GRAVEL.get()), new ItemStack(Blocks.GRAVEL), "sticky_gravel");
        washing(pWriter, Ingredient.of(ModBlocks.STICKY_LOG_OAK.get()), new ItemStack(Blocks.OAK_LOG), "sticky_oak_log");
        washing(pWriter, Ingredient.of(ModBlocks.STICKY_OBSIDIAN.get()), new ItemStack(Blocks.OBSIDIAN), "sticky_obsidian");
        washing(pWriter, Ingredient.of(ModBlocks.STICKY_PUMPKIN.get()), new ItemStack(Blocks.PUMPKIN), "sticky_pumpkin");
        washing(pWriter, Ingredient.of(ModBlocks.STICKY_SAND.get()), new ItemStack(Blocks.SAND), "sticky_sand");
        washing(pWriter, Ingredient.of(ModBlocks.STICKY_STONE.get()), new ItemStack(Blocks.STONE), "sticky_stone");

    }

    private void washing(Consumer<FinishedRecipe> pWriter, Ingredient ingredient, ItemStack result, String name) {
        pWriter.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(JsonObject pJson) {
                JsonArray jsonarray = new JsonArray();
                jsonarray.add(ingredient.toJson());
                pJson.add("ingredients", jsonarray);

                JsonObject jsonobject = new JsonObject();
                jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(result.getItem()).toString());
                if (result.getCount() > 1) {
                    jsonobject.addProperty("count", result.getCount());
                }
                pJson.add("output", jsonobject);
            }

            @Override
            public ResourceLocation getId() {
                return new ResourceLocation(StickyResources.MOD_ID, "washing/" + name);
            }

            @Override
            public RecipeSerializer<?> getType() {
                return ModRecipes.WASHING_STATION_SERIALIZER.get();
            }

            @Nullable
            @Override
            public JsonObject serializeAdvancement() {
                return null;
            }

            @Nullable
            @Override
            public ResourceLocation getAdvancementId() {
                return null;
            }
        });
    }
}