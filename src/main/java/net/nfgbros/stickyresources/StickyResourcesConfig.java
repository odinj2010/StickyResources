package net.nfgbros.stickyresources;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class StickyResourcesConfig {

    // Interaction Settings
    public static ForgeConfigSpec.IntValue JELLY_INTERACTION_BLOCK_DISTANCE; // Jelly entity interaction distance
    public static ForgeConfigSpec.IntValue WATER_DAMAGE_ELECTRIC_JELLY; // Damage taken by Electric Jelly in water
    public static ForgeConfigSpec.IntValue ELECTRIC_JELLY_SHOCK_DAMAGE; // Damage caused by Electric Jelly shock

    // Jelly Drop Time and Amount Settings
    public static ForgeConfigSpec.IntValue JELLY_SLIME_BALL_DROP_TIME; // Drop time for slime balls
    public static ForgeConfigSpec.IntValue JELLY_SLIME_BALL_DROP_AMOUNT; // Drop amount for slime balls
    public static ForgeConfigSpec.IntValue STICKY_COBBLESTONE_DROP_TIME; // Drop time for sticky cobblestone
    public static ForgeConfigSpec.IntValue STICKY_COBBLESTONE_DROP_AMOUNT; // Drop amount for sticky cobblestone
    public static ForgeConfigSpec.IntValue STICKY_DIRT_DROP_TIME; // Drop time for sticky dirt
    public static ForgeConfigSpec.IntValue STICKY_DIRT_DROP_AMOUNT; // Drop amount for sticky dirt
    public static ForgeConfigSpec.IntValue STICKY_GLASS_DROP_TIME; // Drop time for sticky glass
    public static ForgeConfigSpec.IntValue STICKY_GLASS_DROP_AMOUNT; // Drop amount for sticky glass
    public static ForgeConfigSpec.IntValue STICKY_GRAVEL_DROP_TIME; // Drop time for sticky gravel
    public static ForgeConfigSpec.IntValue STICKY_GRAVEL_DROP_AMOUNT; // Drop amount for sticky gravel
    public static ForgeConfigSpec.IntValue STICKY_OAK_LOG_DROP_TIME; // Drop time for sticky oak logs
    public static ForgeConfigSpec.IntValue STICKY_OAK_LOG_DROP_AMOUNT; // Drop amount for sticky oak logs
    public static ForgeConfigSpec.IntValue STICKY_OBSIDIAN_DROP_TIME; // Drop time for sticky obsidian
    public static ForgeConfigSpec.IntValue STICKY_OBSIDIAN_DROP_AMOUNT; // Drop amount for sticky obsidian
    public static ForgeConfigSpec.IntValue STICKY_SAND_DROP_TIME; // Drop time for sticky sand
    public static ForgeConfigSpec.IntValue STICKY_SAND_DROP_AMOUNT; // Drop amount for sticky sand
    public static ForgeConfigSpec.IntValue STICKY_BONE_MEAL_DROP_TIME; // Drop time for sticky bone meal
    public static ForgeConfigSpec.IntValue STICKY_BONE_MEAL_DROP_AMOUNT; // Drop amount for sticky bone meal
    public static ForgeConfigSpec.IntValue STICKY_COAL_DROP_TIME; // Drop time for sticky coal
    public static ForgeConfigSpec.IntValue STICKY_COAL_DROP_AMOUNT; // Drop amount for sticky coal
    public static ForgeConfigSpec.IntValue STICKY_CHARCOAL_DROP_TIME; // Drop time for sticky charcoal
    public static ForgeConfigSpec.IntValue STICKY_CHARCOAL_DROP_AMOUNT; // Drop amount for sticky charcoal
    public static ForgeConfigSpec.IntValue STICKY_RAW_COPPER_DROP_TIME; // Drop time for sticky raw copper
    public static ForgeConfigSpec.IntValue STICKY_RAW_COPPER_DROP_AMOUNT; // Drop amount for sticky raw copper
    public static ForgeConfigSpec.IntValue STICKY_DIAMOND_DROP_TIME; // Drop time for sticky diamonds
    public static ForgeConfigSpec.IntValue STICKY_DIAMOND_DROP_AMOUNT; // Drop amount for sticky diamonds
    public static ForgeConfigSpec.IntValue STICKY_EMERALD_DROP_TIME; // Drop time for sticky emeralds
    public static ForgeConfigSpec.IntValue STICKY_EMERALD_DROP_AMOUNT; // Drop amount for sticky emeralds
    public static ForgeConfigSpec.IntValue STICKY_ENDER_PEARL_DROP_TIME; // Drop time for sticky ender pearls
    public static ForgeConfigSpec.IntValue STICKY_ENDER_PEARL_DROP_AMOUNT; // Drop amount for sticky ender pearls
    public static ForgeConfigSpec.IntValue STICKY_RAW_GOLD_DROP_TIME; // Drop time for sticky raw gold
    public static ForgeConfigSpec.IntValue STICKY_RAW_GOLD_DROP_AMOUNT; // Drop amount for sticky raw gold
    public static ForgeConfigSpec.IntValue STICKY_RAW_IRON_DROP_TIME; // Drop time for sticky raw iron
    public static ForgeConfigSpec.IntValue STICKY_RAW_IRON_DROP_AMOUNT; // Drop amount for sticky raw iron
    public static ForgeConfigSpec.IntValue STICKY_LAPIS_LAZULI_DROP_TIME; // Drop time for sticky lapis lazuli
    public static ForgeConfigSpec.IntValue STICKY_LAPIS_LAZULI_DROP_AMOUNT; // Drop amount for sticky lapis lazuli
    public static ForgeConfigSpec.IntValue STICKY_PRISMERINE_CRYSTALS_DROP_TIME; // Drop time for sticky prismarine crystals
    public static ForgeConfigSpec.IntValue STICKY_PRISMERINE_CRYSTALS_DROP_AMOUNT; // Drop amount for sticky prismarine crystals
    public static ForgeConfigSpec.IntValue STICKY_RAW_SAPPHIRE_DROP_TIME; // Drop time for sticky raw sapphire
    public static ForgeConfigSpec.IntValue STICKY_RAW_SAPPHIRE_DROP_AMOUNT; // Drop amount for sticky raw sapphire
    public static ForgeConfigSpec.IntValue STICKY_REDSTONE_DUST_DROP_TIME; // Drop time for sticky redstone dust
    public static ForgeConfigSpec.IntValue STICKY_REDSTONE_DUST_DROP_AMOUNT; // Drop amount for sticky redstone dust
    public static ForgeConfigSpec.IntValue STICKY_WATER_BUCKET_DROP_TIME; // Drop time for sticky water buckets
    public static ForgeConfigSpec.IntValue STICKY_WATER_BUCKET_DROP_AMOUNT; // Drop amount for sticky water buckets
    public static ForgeConfigSpec.IntValue STICKY_LAVA_BUCKET_DROP_TIME; // Drop time for sticky lava buckets
    public static ForgeConfigSpec.IntValue STICKY_LAVA_BUCKET_DROP_AMOUNT; // Drop amount for sticky lava buckets

    // Register configuration
    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, SPEC, "stickyresources-common.toml");
    }

    // Configuration Specification
    public static final ForgeConfigSpec SPEC;
    private static final StickyResourcesConfig COMMON;

    static {
        final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        // Interaction Settings Configuration
        COMMON_BUILDER.comment("Jelly Interaction Settings").push("jelly_interaction_settings");
        JELLY_INTERACTION_BLOCK_DISTANCE = COMMON_BUILDER
                .comment("Minimum block distance for Jelly interactions (default = 2, min = 0, max = 10)")
                .defineInRange("jellyInteractionBlockDistance", 2, 0, 10);

        WATER_DAMAGE_ELECTRIC_JELLY = COMMON_BUILDER
                .comment("Damage Electric Jelly takes from water (default = 1, min = 0, max = 9999)")
                .defineInRange("waterDamageElectricJelly", 1, 0, 9999);

        ELECTRIC_JELLY_SHOCK_DAMAGE = COMMON_BUILDER
                .comment("Electric Jelly shock damage (default = 1, min = 0, max = 9999)")
                .defineInRange("electricJellyShockDamage", 1, 0, 9999);

        COMMON_BUILDER.pop();

        // Drop Settings Configuration
        COMMON_BUILDER.comment("Jelly Drop Settings").push("jelly_drop_settings");
        JELLY_SLIME_BALL_DROP_TIME = COMMON_BUILDER
                .comment("Time between slime ball drops (default = 600, min = 1, max = 72000)")
                .defineInRange("jellySlimeBallDropTime", 600, 1, 72000);
        JELLY_SLIME_BALL_DROP_AMOUNT = COMMON_BUILDER
                .comment("Number of slime balls dropped (default = 1, min = 1, max = 64)")
                .defineInRange("jellySlimeBallDropAmount", 1, 1, 64);

        // Add all other drop configurations here (e.g., Cobblestone, Dirt, etc., similar to above)

        COMMON_BUILDER.pop();

        COMMON = new StickyResourcesConfig(COMMON_BUILDER);
        SPEC = COMMON_BUILDER.build();
    }

    public StickyResourcesConfig(ForgeConfigSpec.Builder builder) {
        builder.build();
    }
}