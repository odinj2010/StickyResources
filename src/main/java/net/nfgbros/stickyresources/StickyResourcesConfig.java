package net.nfgbros.stickyresources;

import io.netty.util.IntSupplier;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class StickyResourcesConfig {
    ///
    ///Standard Jelly Settings---------------------------------------------------------------------------------------
    ///
    public static ForgeConfigSpec.IntValue JELLY_INTERACTION_BLOCK_DISTANCE;
    public static ForgeConfigSpec.IntValue JELLY_MERGE_THRESHOLD;
    public static ForgeConfigSpec.IntValue JELLY_MERGE_COOLDOWN;
    ///
    ///Jelly Custom AI Activate Settings-----------------------------------------------------------------------------
    ///
    public static ForgeConfigSpec.BooleanValue JELLY_CUSTOM_AI_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_SWARMS_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_GRAZING_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_MIGRATION_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_NESTING_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_DEFENSE_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_GRAZE_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_COMMUNICATION_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_PREDATION_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_ENVIRONMENT_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_BREEDING_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_BREEDING_RITUAL_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_PANIC_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_CURIOSITY_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_RESOURCE_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_SEASONAL_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_MIMICRY_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_SYMBIOSIS_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_PLAY_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_EXPLORATION_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_EVOLUTION_ACTIVE;
    ///
    ///Swarms--------------------------------------------------------------------------------------------------------
    ///
    public static ForgeConfigSpec.IntValue JELLY_SWARM_LEADER_SELECTION_RADIUS;
    public static ForgeConfigSpec.DoubleValue JELLY_SWARM_LEADER_HEALTH_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue JELLY_SWARM_LEADER_SPEED_BONUS;
    public static ForgeConfigSpec.IntValue JELLY_SWARM_MERGE_RADIUS;
    public static ForgeConfigSpec.IntValue JELLY_SWARM_MIN_SIZE;
    public static ForgeConfigSpec.IntValue JELLY_SWARM_SIGNAL_RADIUS;
    public static ForgeConfigSpec.IntValue JELLY_SWARM_EVASION_RADIUS;
    public static ForgeConfigSpec.IntValue JELLY_SWARM_RETREATING_HEALTH_PERCENT;
    public static ForgeConfigSpec.IntValue JELLY_SWARM_LEADER_PATH_RECALC_INTERVAL;
    public static ForgeConfigSpec.IntValue JELLY_SWARM_LEADER_COOLDOWN;
    ///
    ///grazing--------------------------------------------------------------------------------------------------------
    ///
    public static ForgeConfigSpec.IntValue JELLY_GRAZE_DURATION;
    public static ForgeConfigSpec.IntValue JELLY_NEXT_GRAZE_TIME;
    public static ForgeConfigSpec.DoubleValue JELLY_GRAZE_SEARCH_RADIUS;
    public static ForgeConfigSpec.IntValue JELLY_GRAZE_MAX_HOLE_DEPTH;

    ///
    ///Jelly Movement Settings---------------------------------------------------------------------------------------
    ///
    public static ForgeConfigSpec.DoubleValue JELLY_MOVEMENT_SPEED;
    public static ForgeConfigSpec.DoubleValue JELLY_JUMP_STRENGTH;
    public static ForgeConfigSpec.DoubleValue JELLY_SWIM_SPEED;

    ///
    ///Jelly Health Settings-----------------------------------------------------------------------------------------
    ///
    public static ForgeConfigSpec.DoubleValue JELLY_MAX_HEALTH;
    public static ForgeConfigSpec.DoubleValue JELLY_HEALTH_REGEN_RATE;
    public static ForgeConfigSpec.DoubleValue JELLY_DAMAGE_RESISTANCE;

    ///
    ///Jelly Interaction Settings------------------------------------------------------------------------------------
    ///
    public static ForgeConfigSpec.IntValue JELLY_INTERACTION_COOLDOWN;
    public static ForgeConfigSpec.IntValue JELLY_INTERACTION_RANGE;

    ///
    ///Jelly Environmental Settings----------------------------------------------------------------------------------
    ///
    public static ForgeConfigSpec.BooleanValue JELLY_WEATHER_EFFECTS;
    public static ForgeConfigSpec.DoubleValue JELLY_TEMPERATURE_TOLERANCE;

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, SPEC, "stickyresources-common.toml");
    }

    /// Defines the specification for the configuration--------------------------------------------------------------
    public static final ForgeConfigSpec SPEC;
    private static final StickyResourcesConfig COMMON;

    static {
        final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        ///
        ///Jelly Custom AI Settings-----------------------------------------------------------------------------------
        ///
        ///Comment for Custom AI Settings-----------------------------------------------------------------------------
        COMMON_BUILDER.comment("\n\n\bJelly Custom AI Activation:\b\n\n").push("jelly_custom_ai_activation");
        COMMON_BUILDER.comment("Activate the jellies custom AI. Use this is you want custom AI settings enabled for the Jellies instead of just being resource makers.\n");
        JELLY_CUSTOM_AI_ACTIVE = COMMON_BUILDER.comment("With this set to 'true' custom AI settings will be enabled.\nDefault: 'false'")
                .define("jelly_custom_ai_active", false);
        JELLY_BREEDING_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_breeding_ritual_active", true);
        JELLY_BREEDING_RITUAL_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_breeding_ritual_active", true);
        JELLY_COMMUNICATION_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_communication_active", true);
        JELLY_CURIOSITY_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_curiosity_active", true);
        JELLY_DEFENSE_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_defense_active", true);
        JELLY_ENVIRONMENT_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_environment_active", true);
        JELLY_EVOLUTION_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_evolution_active", true);
        JELLY_EXPLORATION_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_exploration_active", true);
        JELLY_GRAZE_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_graze_active", true);
        JELLY_GRAZING_ACTIVE = COMMON_BUILDER.comment("Activate Jelly Grazing Goals.\n" +
                        "Normally: 'true'")
                .define("jelly_grazing_active", true);
        JELLY_MIGRATION_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_migration_active", true);
        JELLY_MIMICRY_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_mimicry_active", true);
        JELLY_NESTING_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_nesting_active", true);
        JELLY_PANIC_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_panic_active", true);
        JELLY_PLAY_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_play_active", true);
        JELLY_PREDATION_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_predation_active", true);
        JELLY_RESOURCE_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_resource_active", true);
        JELLY_SEASONAL_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_seasonal_active", true);
        JELLY_SWARMS_ACTIVE = COMMON_BUILDER.comment("Jelly swarms attempts to add a complex layer of ai tasks and movements as a jelly collective.\n" +
                        "* Change this to 'true' to activate Jelly Swarms\n" +
                        "** WARNING: Jelly Swarms is still a WIP. Using it will have bugs!.. But have fun with it!\n" +
                        "Narmally: 'false'")
                .define("jelly_swarms_active", false);
        JELLY_SYMBIOSIS_ACTIVE = COMMON_BUILDER.comment("\n" +
                        "Normally: 'true'")
                .define("jelly_symbiosis_active", true);

        // Inside the static block
        COMMON_BUILDER.comment("\nSwarm Settings:\n").push("swarm_settings");

        JELLY_SWARM_LEADER_SELECTION_RADIUS = COMMON_BUILDER.comment("The radius (in blocks) within which a jelly can be chosen as the leader. Default: 10.")
                .defineInRange("jellySwarmLeaderSelectionRadius", 10, 1, 50);

        JELLY_SWARM_LEADER_HEALTH_MULTIPLIER = COMMON_BUILDER.comment("The multiplier for the leader's health. Default: 1.5.")
                .defineInRange("jellySwarmLeaderHealthMultiplier", 1.5, 1.0, 5.0);

        JELLY_SWARM_LEADER_SPEED_BONUS = COMMON_BUILDER.comment("The speed bonus for the leader. Default: 0.2.")
                .defineInRange("jellySwarmLeaderSpeedBonus", 0.2, 0.1, 1.0);

        JELLY_SWARM_MERGE_RADIUS = COMMON_BUILDER.comment("The radius (in blocks) within which jellies can merge into a swarm. Default: 5.")
                .defineInRange("jellySwarmMergeRadius", 5, 1, 20);

        JELLY_SWARM_MIN_SIZE = COMMON_BUILDER.comment("The minimum number of jellies required to form a swarm. Default: 5.")
                .defineInRange("jellySwarmMinSize", 5, 2, 20);

        JELLY_SWARM_SIGNAL_RADIUS = COMMON_BUILDER.comment("The radius (in blocks) within which the leader's signals can reach. Default: 20.")
                .defineInRange("jellySwarmSignalRadius", 20, 5, 50);

        JELLY_SWARM_EVASION_RADIUS = COMMON_BUILDER.comment("The radius (in blocks) within which the swarm will start evading enemies. Default: 10.")
                .defineInRange("jellySwarmEvasionRadius", 10, 5, 30);

        JELLY_SWARM_RETREATING_HEALTH_PERCENT = COMMON_BUILDER.comment("The health percentage at which the swarm will retreat. Default: 20.")
                .defineInRange("jellySwarmRetreatingHealthPercent", 20, 1, 100);

        JELLY_SWARM_LEADER_PATH_RECALC_INTERVAL = COMMON_BUILDER.comment("The interval (in ticks) at which the leader recalculates its path. Default: 100.")
                .defineInRange("jellySwarmLeaderPathRecalcInterval", 100, 10, 1000);

        JELLY_SWARM_LEADER_COOLDOWN = COMMON_BUILDER.comment("The cooldown time (in ticks) before a new leader can be chosen. Default: 24000 (1 Minecraft day).")
                .defineInRange("jellySwarmLeaderCooldown", 24000, 0, Integer.MAX_VALUE);

        COMMON_BUILDER.pop();
        ///
        ///Grazing Settings-------------------------------------------------------------------------------------------
        ///
        COMMON_BUILDER.comment("\nJelly Grazing Settings:\n").push("jelly_grazing_settings");
        JELLY_GRAZE_DURATION = COMMON_BUILDER.comment("The duration of the grazing action in ticks.")
                .defineInRange("jellyGrazeDuration", 100, 1, 72000);
        JELLY_NEXT_GRAZE_TIME = COMMON_BUILDER.comment("The duration of time between grazing action in ticks.")
                .defineInRange("jellyNextGrazeDuration", 60, 1, 72000);
        JELLY_GRAZE_SEARCH_RADIUS = COMMON_BUILDER.comment("The radius in blocks to search for grazeable blocks.")
                .defineInRange("jellyGrazeSearchRadius", 6.0, 1.0, 50.0);
        JELLY_GRAZE_MAX_HOLE_DEPTH = COMMON_BUILDER.comment("The maximum depth a jelly will go to graze a block.")
                .defineInRange("jellyGrazeMaxHoleDepth", 3, 1, 10);
        COMMON_BUILDER.pop();
        ///End of Jelly AI Settings-----------------------------------------------------------------------------------
        ///
        ///Jelly Settings---------------------------------------------------------------------------------------------
        COMMON_BUILDER.comment("\n\nJelly Settings:\n\n").push("jelly_settings");
        ///
        ///Jelly Settings---------------------------------------------------------------------------------------------
        ///
        JELLY_INTERACTION_BLOCK_DISTANCE = COMMON_BUILDER.comment("How close does the jelly entities need to be? If they are within 2 blocks of each other, they can interact with each other. (default = 1, min = 0, max = 10) (in blocks)")
                .defineInRange("jellyInteractionBlockDistance", 1, 0, 10);
        JELLY_MERGE_THRESHOLD = COMMON_BUILDER.comment("The number of ticks it takes to merge two Jellies together. (default = 100 (5 seconds), min = 1, max = 72000) (in ticks)")
                .defineInRange("jellyMergeThreshold", 100, 1, 72000);
        JELLY_MERGE_COOLDOWN = COMMON_BUILDER.comment("")
                .defineInRange("jellyMergeThreshold", 100, 1, 72000);
        COMMON_BUILDER.pop();
        ///
        ///Jelly Movement Settings-----------------------------------------------------------------------------------
        ///
        COMMON_BUILDER.comment("\nJelly Movement Settings:\n").push("jelly_movement_settings");
        JELLY_MOVEMENT_SPEED = COMMON_BUILDER.comment("The base movement speed of jelly entities. (default = 0.7, min = 0.1, max = 2.0)")
                .defineInRange("jellyMovementSpeed", 0.7, 0.1, 2.0);
        JELLY_JUMP_STRENGTH = COMMON_BUILDER.comment("The strength of the jelly's jump. (default = 0.5, min = 0.1, max = 1.5)")
                .defineInRange("jellyJumpStrength", 0.5, 0.1, 1.5);
        JELLY_SWIM_SPEED = COMMON_BUILDER.comment("The speed at which jelly entities swim. (default = 1.0, min = 0.1, max = 2.0)")
                .defineInRange("jellySwimSpeed", 1.0, 0.1, 2.0);
        COMMON_BUILDER.pop();
        ///
        ///Jelly Health Settings-------------------------------------------------------------------------------------
        ///
        COMMON_BUILDER.comment("\nJelly Health Settings:\n").push("jelly_health_settings");
        JELLY_MAX_HEALTH = COMMON_BUILDER.comment("The maximum health of a jelly entity. (default = 20.0, min = 1.0, max = 100.0)")
                .defineInRange("jellyMaxHealth", 20.0, 1.0, 100.0);
        JELLY_HEALTH_REGEN_RATE = COMMON_BUILDER.comment("The rate at which jelly entities regenerate health. (default = 0.1, min = 0.0, max = 1.0)")
                .defineInRange("jellyHealthRegenRate", 0.1, 0.0, 1.0);
        JELLY_DAMAGE_RESISTANCE = COMMON_BUILDER.comment("The amount of damage resistance jelly entities have. (default = 0.0, min = 0.0, max = 1.0)")
                .defineInRange("jellyDamageResistance", 0.0, 0.0, 1.0);
        COMMON_BUILDER.pop();
        ///
        ///Jelly Interaction Settings--------------------------------------------------------------------------------
        ///
        COMMON_BUILDER.comment("\nJelly Interaction Settings:\n").push("jelly_interaction_settings");
        JELLY_INTERACTION_COOLDOWN = COMMON_BUILDER.comment("The cooldown time between interactions with blocks or entities. (default = 20, min = 1, max = 72000) (in ticks)")
                .defineInRange("jellyInteractionCooldown", 20, 1, 72000);
        JELLY_INTERACTION_RANGE = COMMON_BUILDER.comment("The range at which jelly entities can interact with blocks or entities. (default = 4, min = 1, max = 16) (in blocks)")
                .defineInRange("jellyInteractionRange", 4, 1, 16);
        COMMON_BUILDER.pop();
        ///
        ///Jelly Environmental Settings------------------------------------------------------------------------------
        ///
        COMMON_BUILDER.comment("\nJelly Environmental Settings:\n").push("jelly_environmental_settings");
        JELLY_WEATHER_EFFECTS = COMMON_BUILDER.comment("Whether jelly entities are affected by weather conditions. (default = true)")
                .define("jellyWeatherEffects", true);
        JELLY_TEMPERATURE_TOLERANCE = COMMON_BUILDER.comment("The temperature range within which jelly entities can survive. (default = 10.0, min = -50.0, max = 50.0)")
                .defineInRange("jellyTemperatureTolerance", 10.0, -50.0, 50.0);
        COMMON_BUILDER.pop();
        /// ---------------------------------------------------------------------------------------------------------
        COMMON = new StickyResourcesConfig(COMMON_BUILDER);
        SPEC = COMMON_BUILDER.build();
    }

    public StickyResourcesConfig(ForgeConfigSpec.Builder builder) {
        builder.build();
    }
}
