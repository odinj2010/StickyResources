package net.nfgbros.stickyresources.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.nfgbros.stickyresources.StickyResources;

public class ModTags {
    // Block tags used in the mod
    public static class Blocks {
        // Tag specifying blocks that require a Sapphire tool to mine
        public static final net.minecraft.tags.TagKey<Block> NEEDS_SAPPHIRE_TOOL = tag("needs_sapphire_tool");
        // Tag for blocks that are valuable when using a metal detector
        public static final net.minecraft.tags.TagKey<Block> METAL_DETECTOR_VALUABLES = tag("metal_detector_valuables");

        // Helper method for creating mod-specific block tags
        private static net.minecraft.tags.TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(StickyResources.MOD_ID, name));
        }

        // Helper method for creating Forge block tags
        private static net.minecraft.tags.TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

    // Item tags used in the mod
    public static class Items {
        // Tag specifying items that require a Sapphire tool
        public static final net.minecraft.tags.TagKey<Item> NEEDS_SAPPHIRE_TOOL = tag("needs_sapphire_tool");
        // Tag for items that are valuable for a metal detector
        public static final net.minecraft.tags.TagKey<Item> METAL_DETECTOR_VALUABLES = tag("metal_detector_valuables");
        // Tag for items classified as "sticky" in the mod
        public static final net.minecraft.tags.TagKey<Item> STICKY_ITEMS = tag("sticky_items");

        // Helper method for creating mod-specific item tags
        private static net.minecraft.tags.TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(StickyResources.MOD_ID, name));
        }

        // Helper method for creating Forge item tags
        private static net.minecraft.tags.TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}