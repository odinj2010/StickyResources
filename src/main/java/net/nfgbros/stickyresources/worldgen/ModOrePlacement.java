package net.nfgbros.stickyresources.worldgen;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModOrePlacement {
    public static List<PlacementModifier> orePlacement(PlacementModifier pCountModifier, PlacementModifier pHeightModifier) {
        return List.of(pCountModifier, InSquarePlacement.spread(), pHeightModifier, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightModifier) {
        return orePlacement(CountPlacement.of(pCount), pHeightModifier);
    }

    public static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightModifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightModifier);
    }
}