package net.nfgbros.stickyresources;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class StickyResourcesConfig {

    //public static ForgeConfigSpec.IntValue PINE_CONE_BURN_TIME;
    public static ForgeConfigSpec.IntValue JELLY_INTERACTION_BLOCK_DISTANCE;
    public static ForgeConfigSpec.IntValue JELLY_MERGE_THRESHOLD;
    public static ForgeConfigSpec.BooleanValue JELLY_SWARMS_ACTIVE;
    //public static ForgeConfigSpec.IntValue JELLY_MAX_HEALTH;
    //public static ForgeConfigSpec.DoubleValue JELLY_MOVEMENT_SPEED;
    //public static ForgeConfigSpec.IntValue JELLY_ATTACK_DAMAGE;

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, SPEC, "stickyresources-common.toml");
    }
    // Defines the specification for the configuration
    public static final ForgeConfigSpec SPEC;
    private static final StickyResourcesConfig COMMON;
    static {
        final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        //Jelly Stick Settings
        COMMON_BUILDER.comment("Jelly Settings").push("jelly_settings");
        //
        JELLY_INTERACTION_BLOCK_DISTANCE = COMMON_BUILDER.comment("How close does the jelly entities need to be? If they are within 2 blocks of each other, they can interact with each other. (default = 1, min = 0, max = 10) (in blocks)")
                .defineInRange("jellyInteractionBlockDistance", 1, 0, 10);
        JELLY_MERGE_THRESHOLD = COMMON_BUILDER.comment("The number of ticks it takes to merge two Jellies together. (default = 100 (5 seconds), min = 1, max = 72000) (in ticks)")
                .defineInRange("jellyMergeThreshold", 100, 1, 72000);
        JELLY_SWARMS_ACTIVE = COMMON_BUILDER.comment("Activate Jelly Swarms\nChange this to 'true' to activate Jelly Swarms\nWARNING: Jelly Swarms is still a WIP. Using it will have bugs!")
                .define("jellySwarmsActive", false);
        COMMON_BUILDER.pop();
        ///
        COMMON_BUILDER.comment("Misc Settings").push("sticky_resources_misc_settings");

        COMMON_BUILDER.pop();
        ///
        COMMON_BUILDER.comment("Jelly Graze Settings").push("jelly_graze_settings");
        COMMON_BUILDER.pop();
        COMMON = new StickyResourcesConfig(COMMON_BUILDER);
        SPEC = COMMON_BUILDER.build();
    }
    public StickyResourcesConfig(ForgeConfigSpec.Builder builder) {
        builder.build();
    }
}