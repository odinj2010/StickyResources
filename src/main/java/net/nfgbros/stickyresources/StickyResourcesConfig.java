package net.nfgbros.stickyresources;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class StickyResourcesConfig {
    public static ForgeConfigSpec.BooleanValue JELLY_CUSTOM_AI_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_RESOURCE_ONLY_MODE;

    // AI & Behavior Settings
    public static ForgeConfigSpec.BooleanValue JELLY_AUTONOMOUS_BREEDING;
    public static ForgeConfigSpec.IntValue JELLY_WILD_BREED_CHANCE;
    public static ForgeConfigSpec.BooleanValue JELLY_ENVIRONMENTAL_TRANSFORMATIONS;
    public static ForgeConfigSpec.BooleanValue JELLY_PLAYER_TRANSFORMATIONS;
    public static ForgeConfigSpec.BooleanValue JELLY_EMOTION_TRIGGERS_ACTIVE;

    // Drop Settings
    public static ForgeConfigSpec.IntValue JELLY_DROP_MIN_TICKS;
    public static ForgeConfigSpec.IntValue JELLY_DROP_MAX_TICKS;
    public static ForgeConfigSpec.DoubleValue JELLY_DROP_CHANCE_MODIFIER;

    // Attribute Modifiers
    public static ForgeConfigSpec.DoubleValue JELLY_HEALTH_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue JELLY_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue JELLY_SPEED_MULTIPLIER;

    // Movement Settings
    public static ForgeConfigSpec.BooleanValue JELLY_CAN_FLOAT;
    public static ForgeConfigSpec.DoubleValue JELLY_JUMP_VELOCITY;
    public static ForgeConfigSpec.DoubleValue JELLY_FOLLOW_SPEED;
    public static ForgeConfigSpec.DoubleValue JELLY_MATE_SPEED;

    // Safe Getters to prevent crashes during early loading
    public static boolean safeGet(ForgeConfigSpec.BooleanValue value, boolean defaultValue) {
        try { return value.get(); } catch (Exception e) { return defaultValue; }
    }
    public static double safeGet(ForgeConfigSpec.DoubleValue value, double defaultValue) {
        try { return value.get(); } catch (Exception e) { return defaultValue; }
    }
    public static int safeGet(ForgeConfigSpec.IntValue value, int defaultValue) {
        try { return value.get(); } catch (Exception e) { return defaultValue; }
    }

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, SPEC, "sticky_resources-common.toml");
    }

    public static final ForgeConfigSpec SPEC;
    static {
        final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        BUILDER.comment("Jelly Settings").push("jelly_settings");
        JELLY_CUSTOM_AI_ACTIVE = BUILDER.comment("Activate the jellies custom AI. Default: true").define("jelly_custom_ai_active", true);
        JELLY_RESOURCE_ONLY_MODE = BUILDER.comment("If true, jellies will not have complex behaviors and only drop items. Default: false").define("jelly_resources_only_mode", false);
        JELLY_EMOTION_TRIGGERS_ACTIVE = BUILDER.comment("Allow jellies to change emotions based on environment. Default: true").define("jelly_emotion_triggers_active", true);
        BUILDER.pop();

        BUILDER.comment("Breeding & Transformation Settings").push("breeding_and_transformation");
        JELLY_AUTONOMOUS_BREEDING = BUILDER.comment("Allow jellies to breed on their own in the wild. Default: true").define("jelly_autonomous_breeding", true);
        JELLY_WILD_BREED_CHANCE = BUILDER.comment("Chance per tick for a jelly to enter the 'Horny' state (1 in X). Default: 1200").defineInRange("jelly_wild_breed_chance", 1200, 1, 100000);
        JELLY_ENVIRONMENTAL_TRANSFORMATIONS = BUILDER.comment("Allow jellies to transform based on biome/blocks. Default: true").define("jelly_environmental_transformations", true);
        JELLY_PLAYER_TRANSFORMATIONS = BUILDER.comment("Allow players to transform jellies by feeding them items. Default: true").define("jelly_player_transformations", true);
        BUILDER.pop();

        BUILDER.comment("Drop Settings").push("drops");
        JELLY_DROP_MIN_TICKS = BUILDER.comment("Minimum time between item drops (in ticks). Default: 600").defineInRange("jelly_drop_min_ticks", 600, 1, 100000);
        JELLY_DROP_MAX_TICKS = BUILDER.comment("Maximum extra random time between item drops (in ticks). Default: 600").defineInRange("jelly_drop_max_ticks", 600, 1, 100000);
        JELLY_DROP_CHANCE_MODIFIER = BUILDER.comment("Multiplier for drop rates. Default: 1.0").defineInRange("jelly_drop_chance_modifier", 1.0, 0.0, 100.0);
        BUILDER.pop();

        BUILDER.comment("Attribute Modifiers").push("attributes");
        JELLY_HEALTH_MULTIPLIER = BUILDER.comment("Multiplier for Jelly Max Health. Default: 1.0").defineInRange("jelly_health_multiplier", 1.0, 0.1, 100.0);
        JELLY_DAMAGE_MULTIPLIER = BUILDER.comment("Multiplier for Jelly Attack Damage. Default: 1.0").defineInRange("jelly_damage_multiplier", 1.0, 0.0, 100.0);
        JELLY_SPEED_MULTIPLIER = BUILDER.comment("Multiplier for Jelly Movement Speed. Default: 1.0").defineInRange("jelly_speed_multiplier", 1.0, 0.1, 10.0);
        BUILDER.pop();

        BUILDER.comment("Movement Settings").push("movement");
        JELLY_CAN_FLOAT = BUILDER.comment("Can Jellies float in water. Default: true").define("jelly_can_float", true);
        JELLY_JUMP_VELOCITY = BUILDER.comment("How high Jellies jump. Default: 0.65").defineInRange("jelly_jump_velocity", 0.65, 0.1, 2.0);
        JELLY_FOLLOW_SPEED = BUILDER.comment("How fast Jellies move when following a player holding food. Default: 0.5").defineInRange("jelly_follow_speed", 0.5, 0.1, 5.0);
        JELLY_MATE_SPEED = BUILDER.comment("How fast Jellies move when heading towards their mate. Default: 0.6").defineInRange("jelly_mate_speed", 0.6, 0.1, 5.0);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}