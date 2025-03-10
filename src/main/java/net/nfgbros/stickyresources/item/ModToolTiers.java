package net.nfgbros.stickyresources.item;

import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {

    // Define the Sapphire tool tier with its properties and required settings
    public static final Tier SAPPHIRE = TierSortingRegistry.registerTier(
            // Sapphire tool properties: level, durability, efficiency, attack damage, enchantability
            new ForgeTier(5, 1500, 5f, 4f, 25,
                    // Blocks requiring Sapphire tools
                    ModTags.Blocks.NEEDS_SAPPHIRE_TOOL,
                    // Repair material for Sapphire tools
                    () -> Ingredient.of(ModItems.SAPPHIRE.get())),
            // Unique identifier for the Sapphire tier
            new ResourceLocation(StickyResources.MOD_ID, "sapphire"),
            // Tiers it comes after
            List.of(Tiers.NETHERITE),
            // Tiers it comes before
            List.of()
    );

}
