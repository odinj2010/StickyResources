package net.nfgbros.stickyresources.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.nfgbros.stickyresources.StickyResources;

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
        public static final net.minecraft.tags.TagKey<Item> NEEDS_SAPPHIRE_TOOL
                = tag("needs_sapphire_tool");
        public static final net.minecraft.tags.TagKey<Item> METAL_DETECTOR_VALUABLES
                = tag("metal_detector_valuables");

        public static final net.minecraft.tags.TagKey<Item> STICKY_ITEMS
                = tag("sticky_items"); // New tag for STICKY items

        private static net.minecraft.tags.TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(StickyResources.MOD_ID, name));
        }

        private static net.minecraft.tags.TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}