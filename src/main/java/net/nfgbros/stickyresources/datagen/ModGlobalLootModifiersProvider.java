package net.nfgbros.stickyresources.datagen;

import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.item.ModItems;
import net.nfgbros.stickyresources.loot.AddItemModifier;
import net.nfgbros.stickyresources.loot.AddSusSandItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, StickyResources.MOD_ID);
    }

    @Override
    protected void start() {
        // Adding Pine Cone drops from Grass Blocks with 35% chance
        add("pine_cone_from_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.35f).build()
        }, ModItems.PINE_CONE.get()));

        // Adding Pine Cone drops from Creeper loot table
        add("pine_cone_from_creeper", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/creeper")).build()
        }, ModItems.PINE_CONE.get()));

        // Adding Metal Detector drops from Jungle Temple loot chests
        add("metal_detector_from_jungle_temples", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/jungle_temple")).build()
        }, ModItems.METAL_DETECTOR.get()));

        // Adding Metal Detector drops from Suspicious Sand in Desert Pyramid
        add("metal_detector_from_suspicious_sand", new AddSusSandItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("archaeology/desert_pyramid")).build()
        }, ModItems.METAL_DETECTOR.get()));
    }
}
