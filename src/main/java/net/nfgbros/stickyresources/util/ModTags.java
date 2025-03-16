package net.nfgbros.stickyresources.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.item.ModItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.nfgbros.stickyresources.item.ModItems;
import net.nfgbros.stickyresources.block.ModBlocks;

public class ModTags {
    public static class Blocks {
        public static final net.minecraft.tags.TagKey<Block> NEEDS_SAPPHIRE_TOOL
                = tag("needs_sapphire_tool");
        public static final net.minecraft.tags.TagKey<Block> METAL_DETECTOR_VALUABLES
                = tag("metal_detector_valuables");

        private static net.minecraft.tags.TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(StickyResources.MOD_ID, name));
        }

        private static net.minecraft.tags.TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Items {
        public static final net.minecraft.tags.TagKey<Item> NEEDS_SAPPHIRE_TOOL = tag("needs_sapphire_tool");
        public static final net.minecraft.tags.TagKey<Item> METAL_DETECTOR_VALUABLES = tag("metal_detector_valuables");

        public static final net.minecraft.tags.TagKey<Item> STICKY_ITEMS = tag("sticky_items"); // New tag for STICKY items

        private static net.minecraft.tags.TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(StickyResources.MOD_ID, name));
        }

        private static net.minecraft.tags.TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }

        // Helper method to get the non-sticky version of an item
        public static Item getNonStickyItem(Item item) {
            if (item == ModItems.STICKY_RAW_SAPPHIRE.get()) {
                return ModItems.RAW_SAPPHIRE.get();
            } else if (item == ModItems.STICKY_BONE_MEAL.get()) {
                return net.minecraft.world.item.Items.BONE_MEAL;
            } else if (item == ModItems.STICKY_COAL.get()) {
                return net.minecraft.world.item.Items.COAL;
            } else if (item == ModItems.STICKY_CHARCOAL.get()) {
                return net.minecraft.world.item.Items.CHARCOAL;
            } else if (item == ModItems.STICKY_RAW_COPPER.get()) {
                return net.minecraft.world.item.Items.RAW_COPPER;
            } else if (item == ModItems.STICKY_DIAMOND.get()) {
                return net.minecraft.world.item.Items.DIAMOND;
            } else if (item == ModItems.STICKY_EMERALD.get()) {
                return net.minecraft.world.item.Items.EMERALD;
            } else if (item == ModItems.STICKY_ENDER_PEARL.get()) {
                return net.minecraft.world.item.Items.ENDER_PEARL;
            } else if (item == ModItems.STICKY_RAW_GOLD.get()) {
                return net.minecraft.world.item.Items.RAW_GOLD;
            } else if (item == ModItems.STICKY_RAW_IRON.get()) {
                return net.minecraft.world.item.Items.RAW_IRON;
            } else if (item == ModItems.STICKY_LAPIS_LAZULI.get()) {
                return net.minecraft.world.item.Items.LAPIS_LAZULI;
            } else if (item == ModItems.STICKY_PRISMERINE_CRYSTALS.get()) {
                return net.minecraft.world.item.Items.PRISMARINE_CRYSTALS;
            } else if (item == ModItems.STICKY_REDSTONE_DUST.get()) {
                return net.minecraft.world.item.Items.REDSTONE;
            } else if (item == ModItems.STICKY_WATER_BUCKET.get()) {
                return net.minecraft.world.item.Items.WATER_BUCKET;
            } else if (item == ModItems.STICKY_LAVA_BUCKET.get()) {
                return net.minecraft.world.item.Items.LAVA_BUCKET;
            } else if (item == ModBlocks.STICKY_COBBLESTONE.get().asItem()) {
                return net.minecraft.world.level.block.Blocks.COBBLESTONE.asItem();
            } else if (item == ModBlocks.STICKY_DIRT.get().asItem()) {
                return net.minecraft.world.level.block.Blocks.DIRT.asItem();
            } else if (item == ModBlocks.STICKY_GLASS.get().asItem()) {
                return net.minecraft.world.level.block.Blocks.GLASS.asItem();
            } else if (item == ModBlocks.STICKY_GRAVEL.get().asItem()) {
                return net.minecraft.world.level.block.Blocks.GRAVEL.asItem();
            } else if (item == ModBlocks.STICKY_OAK_LOG.get().asItem()) {
                return net.minecraft.world.level.block.Blocks.OAK_LOG.asItem();
            } else if (item == ModBlocks.STICKY_OBSIDIAN.get().asItem()) {
                return net.minecraft.world.level.block.Blocks.OBSIDIAN.asItem();
            } else if (item == ModBlocks.STICKY_SAND.get().asItem()) {
                return net.minecraft.world.level.block.Blocks.SAND.asItem();
            }
            return item; // If it's not a sticky item, return the item itself
        }
    }
}