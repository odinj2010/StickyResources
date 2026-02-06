package net.nfgbros.stickyresources.datagen;

import net.minecraftforge.common.Tags;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.nfgbros.stickyresources.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, StickyResources.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(
                        ModItems.SAPPHIRE_HELMET.get(),
                        ModItems.SAPPHIRE_CHESTPLATE.get(),
                        ModItems.SAPPHIRE_LEGGINGS.get(),
                        ModItems.SAPPHIRE_BOOTS.get());

        this.tag(ModTags.Items.STICKY_ITEMS)
                .add(
                        ModItems.STICKY_AMETHYST.get(),
                        ModItems.STICKY_BEEF.get(),
                        ModItems.STICKY_BONE_MEAL.get(),
                        ModItems.JELLY_CAKE.get(),
                        ModItems.STICKY_CHARCOAL.get(),
                        ModItems.STICKY_COAL.get(),
                        ModItems.STICKY_DIAMOND.get(),
                        ModItems.STICKY_EMERALD.get(),
                        ModItems.STICKY_ENDER_PEARL.get(),
                        ModItems.STICKY_GRASS.get(),
                        ModItems.STICKY_LAPIS_LAZULI.get(),
                        ModItems.STICKY_PRISMERINE_CRYSTALS.get(),
                        ModItems.STICKY_RAW_COPPER.get(),
                        ModItems.STICKY_RAW_GOLD.get(),
                        ModItems.STICKY_RAW_IRON.get(),
                        ModItems.STICKY_RAW_SAPPHIRE.get(),
                        ModItems.STICKY_RED_MUSHROOM.get(),
                        ModItems.STICKY_REDSTONE_DUST.get(),
                        ModItems.STICKY_ROTTEN_FLESH.get(),
                        ModItems.STICKY_STRAWBERRY.get()
                );


        this.tag(ItemTags.MUSIC_DISCS)
                .add(
                        ModItems.BAR_BRAWL_MUSIC_DISC.get()
                );

        this.tag(ItemTags.CREEPER_DROP_MUSIC_DISCS)
                .add(
                        ModItems.BAR_BRAWL_MUSIC_DISC.get()
                );

    }
}
