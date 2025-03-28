package net.nfgbros.stickyresources.recipe;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.item.ModItems;
import net.minecraft.world.item.Items;
import java.util.HashMap;
import java.util.Map;

public enum WashingStationRecipe {

    STICKY_AMETHYST(ModItems.STICKY_AMETHYST.get(), new ItemStack(Items.AMETHYST_SHARD)),
    STICKY_BONE_MEAL(ModItems.STICKY_BONE_MEAL.get(), new ItemStack(Items.BONE_MEAL)),
    STICKY_CAKE(ModItems.JELLY_CAKE.get(), new ItemStack(Items.CAKE)),
    STICKY_CHARCOAL(ModItems.STICKY_CHARCOAL.get(), new ItemStack(Items.CHARCOAL)),
    STICKY_COAL(ModItems.STICKY_COAL.get(), new ItemStack(Items.COAL)),
    STICKY_BEEF(ModItems.STICKY_BEEF.get(), new ItemStack(Items.BEEF)),
    STICKY_DIAMOND(ModItems.STICKY_DIAMOND.get(), new ItemStack(Items.DIAMOND)),
    STICKY_EMERALD(ModItems.STICKY_EMERALD.get(), new ItemStack(Items.EMERALD)),
    STICKY_ENDER_PEARL(ModItems.STICKY_ENDER_PEARL.get(), new ItemStack(Items.ENDER_PEARL)),
    STICKY_GRASS(ModItems.STICKY_GRASS.get(), new ItemStack(Items.GRASS)),
    STICKY_RAW_COPPER(ModItems.STICKY_RAW_COPPER.get(), new ItemStack(Items.RAW_COPPER)),
    STICKY_RAW_GOLD(ModItems.STICKY_RAW_GOLD.get(), new ItemStack(Items.RAW_GOLD)),
    STICKY_RAW_IRON(ModItems.STICKY_RAW_IRON.get(), new ItemStack(Items.RAW_IRON)),
    STICKY_RAW_SAPPHIRE(ModItems.STICKY_RAW_SAPPHIRE.get(), new ItemStack(ModItems.RAW_SAPPHIRE.get())),
    STICKY_LAPIS_LAZULI(ModItems.STICKY_LAPIS_LAZULI.get(), new ItemStack(Items.LAPIS_LAZULI)),
    STICKY_PRISMERINE_CRYSTALS(ModItems.STICKY_PRISMERINE_CRYSTALS.get(), new ItemStack(Items.PRISMARINE_CRYSTALS)),
    STICKY_RED_MUSHROOM(ModItems.STICKY_RED_MUSHROOM.get(), new ItemStack(Items.RED_MUSHROOM)),
    STICKY_REDSTONE_DUST(ModItems.STICKY_REDSTONE_DUST.get(), new ItemStack(Items.REDSTONE)),
    STICKY_COBBLESTONE(ModBlocks.STICKY_COBBLESTONE.get().asItem(), new ItemStack(Blocks.COBBLESTONE)),
    STICKY_DIRT(ModBlocks.STICKY_DIRT.get().asItem(), new ItemStack(Blocks.DIRT)),
    STICKY_GLASS(ModBlocks.STICKY_GLASS.get().asItem(), new ItemStack(Blocks.GLASS)),
    STICKY_GRAVEL(ModBlocks.STICKY_GRAVEL.get().asItem(), new ItemStack(Blocks.GRAVEL)),
    STICKY_OAK_LOG(ModBlocks.STICKY_LOG_OAK.get().asItem(), new ItemStack(Blocks.OAK_LOG)),
    STICKY_OBSIDIAN(ModBlocks.STICKY_OBSIDIAN.get().asItem(), new ItemStack(Blocks.OBSIDIAN)),
    STICKY_PUMPKIN(ModBlocks.STICKY_PUMPKIN.get().asItem(),  new ItemStack(Blocks.PUMPKIN)),
    STICKY_SAND(ModBlocks.STICKY_SAND.get().asItem(), new ItemStack(Blocks.SAND)),
    STICKY_STONE(ModBlocks.STICKY_STONE.get().asItem(), new ItemStack(Blocks.STONE))
    ;

    private static final Map<Item, ItemStack> recipeMap = new HashMap<>();

    static {
        for (WashingStationRecipe recipe : values()) {
            recipeMap.put(recipe.input, recipe.output);
        }
    }

    private final Item input;
    private final ItemStack output;

    WashingStationRecipe(Item input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public static ItemStack getOutputForInput(ItemStack inputStack) {
        return recipeMap.getOrDefault(inputStack.getItem(), ItemStack.EMPTY);
    }
}
