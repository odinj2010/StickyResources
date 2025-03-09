package net.nfgbros.stickyresources;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class StickyResourcesConfig {
    public static ForgeConfigSpec.IntValue ITEM_DROP_TIME;

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, StickyResourcesConfig.SPEC, "stickyresources-common.toml");
    }

    public static final ForgeConfigSpec SPEC;
    private static final StickyResourcesConfig COMMON;

    static {
        final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.comment("General Settings").push("general");
        ITEM_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops (default = 600 (30 Seconds), min = 1, max = 72000)")
                .defineInRange("itemDropTime", 600, 1, 72000); // Default: 600 ticks (30 seconds)
        COMMON_BUILDER.pop();

        COMMON = new StickyResourcesConfig(COMMON_BUILDER);
        SPEC = COMMON_BUILDER.build();
    }

    public StickyResourcesConfig(ForgeConfigSpec.Builder builder) {
        builder.build();
    }
}