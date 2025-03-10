package net.nfgbros.stickyresources;
//Imports
import ca.weblite.objc.Proxy;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;


public class StickyResourcesConfig {
    //
    // Interactions
    //
    // General Jelly Entity
    public static ForgeConfigSpec.IntValue JELLY_INTERACTION_BLOCK_DISTANCE;
    // Electric Jelly Entity
    public static ForgeConfigSpec.IntValue WATER_DAMAGE_ELECTRIC_JELLY;
    public static ForgeConfigSpec.IntValue ELECTRIC_JELLY_SHOCK_DAMAGE;
    //
    // Drop Rates and Drop Amount
    //
    // All Jellies Drop Time and Amounts
    public static ForgeConfigSpec.IntValue JELLY_SLIME_BALL_DROP_TIME; // Added
    public static ForgeConfigSpec.IntValue JELLY_SLIME_BALL_DROP_AMOUNT; // Added
    public static ForgeConfigSpec.IntValue STICKY_COBBLESTONE_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_COBBLESTONE_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_DIRT_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_DIRT_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_GLASS_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_GLASS_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_GRAVEL_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_GRAVEL_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_OAK_LOG_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_OAK_LOG_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_OBSIDIAN_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_OBSIDIAN_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_SAND_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_SAND_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_BONE_MEAL_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_BONE_MEAL_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_COAL_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_COAL_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_CHARCOAL_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_CHARCOAL_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_RAW_COPPER_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_RAW_COPPER_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_DIAMOND_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_DIAMOND_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_EMERALD_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_EMERALD_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_ENDER_PEARL_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_ENDER_PEARL_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_RAW_GOLD_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_RAW_GOLD_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_RAW_IRON_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_RAW_IRON_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_LAPIS_LAZULI_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_LAPIS_LAZULI_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_PRISMERINE_CRYSTALS_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_PRISMERINE_CRYSTALS_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_RAW_SAPPHIRE_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_RAW_SAPPHIRE_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_REDSTONE_DUST_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_REDSTONE_DUST_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_WATER_BUCKET_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_WATER_BUCKET_DROP_AMOUNT;
    public static ForgeConfigSpec.IntValue STICKY_LAVA_BUCKET_DROP_TIME;
    public static ForgeConfigSpec.IntValue STICKY_LAVA_BUCKET_DROP_AMOUNT;



    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, SPEC, "stickyresources-common.toml");
    }
    // Defines the specification for the configuration
    public static final ForgeConfigSpec SPEC;
    private static final StickyResourcesConfig COMMON;
    static {
        final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        //
        //Interaction Settings
        //
        COMMON_BUILDER.comment("Jelly Interaction Settings").push("jelly_interaction_settings");
        //Jelly Entity Interaction Settings
        JELLY_INTERACTION_BLOCK_DISTANCE = COMMON_BUILDER.comment("How close does the jelly entities need to be? If they are within 2 blocks of each other, they can interact with each other. (default = 2, min = 0, max = 10) (in blocks)")
                .defineInRange("jellyInteractionBlockDistance", 2, 0, 10);
        //
        //Electric Jelly Entity Settings
        //
        //How much damage the Electric Jelly recieves from being in water
        WATER_DAMAGE_ELECTRIC_JELLY = COMMON_BUILDER.comment("How much damage the Electric Jelly takes from water (default = 1, min = 0, max = 9999) (in damage points)")
                .defineInRange("waterDamageElectricJelly", 1, 0, 9999);
        //How much damage the Electric Jellies shock is capable of doing.
        ELECTRIC_JELLY_SHOCK_DAMAGE = COMMON_BUILDER.comment("How much damage the Electric Jelly takes from water (default = 1, min = 0, max = 9999) (in damage points)")
                .defineInRange("electricJellyShockDamage", 1, 0, 9999);
        COMMON_BUILDER.pop();
        //
        // Jelly Drop Time and Amount Settings
        //
        COMMON_BUILDER.comment("Jelly Drop Settings").push("jelly_drop_settings");
        // Sticky Slime Ball Settings
        JELLY_SLIME_BALL_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between slime ball drops for Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("jellySlimeBallDropTime", 600, 1, 72000);
        JELLY_SLIME_BALL_DROP_AMOUNT = COMMON_BUILDER.comment("Number of slime balls dropped by Jelly (default = 1, min = 1, max = 64)")
                .defineInRange("jellySlimeBallDropAmount", 1, 1, 64);
        // Sticky Cobblestone
        STICKY_COBBLESTONE_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Cobblestone Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyCobblestoneDropTime", 600, 1, 72000);
        STICKY_COBBLESTONE_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky cobblestone blocks dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyCobblestoneDropAmount", 1, 1, 64);
        // Sticky Dirt
        STICKY_DIRT_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Dirt Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyDirtDropTime", 600, 1, 72000);
        STICKY_DIRT_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky dirt blocks dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyDirtDropAmount", 1, 1, 64);
        // Sticky Glass
        STICKY_GLASS_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Glass Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyGlassDropTime", 600, 1, 72000);
        STICKY_GLASS_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky glass blocks dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyGlassDropAmount", 1, 1, 64);
        // Sticky Gravel
        STICKY_GRAVEL_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Gravel Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyGravelDropTime", 600, 1, 72000);
        STICKY_GRAVEL_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky gravel blocks dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyGravelDropAmount", 1, 1, 64);
        // Sticky Oak Log
        STICKY_OAK_LOG_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Oak Log Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyOakLogDropTime", 600, 1, 72000);
        STICKY_OAK_LOG_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky oak log blocks dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyOakLogDropAmount", 1, 1, 64);
        // Sticky Obsidian
        STICKY_OBSIDIAN_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Obsidian Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyObsidianDropTime", 600, 1, 72000);
        STICKY_OBSIDIAN_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky obsidian blocks dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyObsidianDropAmount", 1, 1, 64);
        // Sticky Sand
        STICKY_SAND_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Sand Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickySandDropTime", 600, 1, 72000);
        STICKY_SAND_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky sand blocks dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickySandDropAmount", 1, 1, 64);
        // Sticky Bone Meal
        STICKY_BONE_MEAL_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Bone Meal Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyBoneMealDropTime", 600, 1, 72000);
        STICKY_BONE_MEAL_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky bone meal dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyBoneMealDropAmount", 1, 1, 64);
        // Sticky Coal
        STICKY_COAL_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Coal Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyCoalDropTime", 600, 1, 72000);
        STICKY_COAL_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky coal dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyCoalDropAmount", 1, 1, 64);
        // Sticky Charcoal
        STICKY_CHARCOAL_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Charcoal Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyCharcoalDropTime", 600, 1, 72000);
        STICKY_CHARCOAL_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky charcoal dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyCharcoalDropAmount", 1, 1, 64);
        // Sticky Raw Copper
        STICKY_RAW_COPPER_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Raw Copper Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyRawCopperDropTime", 600, 1, 72000);
        STICKY_RAW_COPPER_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky raw copper dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyRawCopperDropAmount", 1, 1, 64);
        // Sticky Diamond
        STICKY_DIAMOND_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Diamond Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyDiamondDropTime", 600, 1, 72000);
        STICKY_DIAMOND_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky diamond dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyDiamondDropAmount", 1, 1, 64);
        // Sticky Emerald
        STICKY_EMERALD_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Emerald Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyEmeraldDropTime", 600, 1, 72000);
        STICKY_EMERALD_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky emerald dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyEmeraldDropAmount", 1, 1, 64);
        // Sticky Ender Pearl
        STICKY_ENDER_PEARL_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Ender Pearl Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyEnderPearlDropTime", 600, 1, 72000);
        STICKY_ENDER_PEARL_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky ender pearl dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyEnderPearlDropAmount", 1, 1, 64);
        // Sticky Raw Gold
        STICKY_RAW_GOLD_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Raw Gold Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyRawGoldDropTime", 600, 1, 72000);
        STICKY_RAW_GOLD_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky raw gold dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyRawGoldDropAmount", 1, 1, 64);
        // Sticky Raw Iron
        STICKY_RAW_IRON_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Raw Iron Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyRawIronDropTime", 600, 1, 72000);
        STICKY_RAW_IRON_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky raw iron dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyRawIronDropAmount", 1, 1, 64);
        // Sticky Lapis Lazuli
        STICKY_LAPIS_LAZULI_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Lapis Lazuli Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyLapisLazuliDropTime", 600, 1, 72000);
        STICKY_LAPIS_LAZULI_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky lapis lazuli dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyLapisLazuliDropAmount", 1, 1, 64);
        // Sticky Prismarine Crystals
        STICKY_PRISMERINE_CRYSTALS_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Prismarine Crystals Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyPrismarineCrystalsDropTime", 600, 1, 72000);
        STICKY_PRISMERINE_CRYSTALS_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky prismarine crystals dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyPrismarineCrystalsDropAmount", 1, 1, 64);
        // Sticky Raw Sapphire
        STICKY_RAW_SAPPHIRE_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Raw Sapphire Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyRawSapphireDropTime", 600, 1, 72000);
        STICKY_RAW_SAPPHIRE_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky raw sapphire dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyRawSapphireDropAmount", 1, 1, 64);
        // Sticky Redstone Dust
        STICKY_REDSTONE_DUST_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Redstone Dust Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyRedstoneDustDropTime", 600, 1, 72000);
        STICKY_REDSTONE_DUST_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky redstone dust dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyRedstoneDustDropAmount", 1, 1, 64);
        // Sticky Water Bucket
        STICKY_WATER_BUCKET_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Water Bucket Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyWaterBucketDropTime", 600, 1, 72000);
        STICKY_WATER_BUCKET_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky water bucket dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyWaterBucketDropAmount", 1, 1, 64);
        // Sticky Lava Bucket
        STICKY_LAVA_BUCKET_DROP_TIME = COMMON_BUILDER.comment("Time (in ticks) between item drops for Sticky Lava Bucket Jelly (default = 600, min = 1, max = 72000)")
                .defineInRange("stickyLavaBucketDropTime", 600, 1, 72000);
        STICKY_LAVA_BUCKET_DROP_AMOUNT = COMMON_BUILDER.comment("Number of sticky lava bucket dropped (default = 1, min = 1, max = 64)")
                .defineInRange("stickyLavaBucketDropAmount", 1, 1, 64);
        COMMON_BUILDER.pop();

        COMMON = new StickyResourcesConfig(COMMON_BUILDER);
        SPEC = COMMON_BUILDER.build();

    }

    public StickyResourcesConfig(ForgeConfigSpec.Builder builder) {
        builder.build();
    }
}