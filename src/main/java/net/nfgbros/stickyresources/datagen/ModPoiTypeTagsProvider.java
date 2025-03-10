package net.nfgbros.stickyresources.datagen;

import net.nfgbros.stickyresources.StickyResources;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModPoiTypeTagsProvider extends PoiTypeTagsProvider {

    // Constructor for the custom POI Type Tags Provider
    public ModPoiTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider,
                                  @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, StickyResources.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // Adding the custom POI type tag under ACQUIRABLE_JOB_SITE
        tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .addOptional(new ResourceLocation(StickyResources.MOD_ID, "sound_poi"));
    }
}
