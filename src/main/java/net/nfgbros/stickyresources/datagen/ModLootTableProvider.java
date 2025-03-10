package net.nfgbros.stickyresources.datagen;

import net.nfgbros.stickyresources.datagen.loot.ModBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class ModLootTableProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(
                output,
                Set.of(), // No required entries in the set
                List.of(
                        // Register block loot table provider
                        new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)
                )
        );
    }
}
