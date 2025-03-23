package net.nfgbros.stickyresources;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class StickyResourcesConfig {
    ///
    /// Standard Jelly Settings
    ///
    public static ForgeConfigSpec.IntValue JELLY_INTERACTION_BLOCK_DISTANCE;
    public static ForgeConfigSpec.IntValue JELLY_MERGE_THRESHOLD;
    public static ForgeConfigSpec.IntValue JELLY_MERGE_COOLDOWN;

    ///
    /// Jelly Custom AI Activation Settings
    ///
    public static ForgeConfigSpec.BooleanValue JELLY_CUSTOM_AI_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_SWARMS_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_GRAZING_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_MIGRATION_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_JOINT_NESTING_ACTIVE;
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

    public static ForgeConfigSpec.BooleanValue JELLY_FOLLOW_FOOD_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_SWIM_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_CHILD_PROTECTION_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_EMOTIONAL_ACTIVE;
    public static ForgeConfigSpec.BooleanValue JELLY_WONDER_ACTIVE;

    ///
    /// Swarm Settings
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
    /// Grazing Settings
    ///
    public static ForgeConfigSpec.IntValue JELLY_GRAZE_DURATION;
    public static ForgeConfigSpec.IntValue JELLY_NEXT_GRAZE_TIME;
    public static ForgeConfigSpec.DoubleValue JELLY_GRAZE_SEARCH_RADIUS;
    public static ForgeConfigSpec.IntValue JELLY_GRAZE_MAX_HOLE_DEPTH;

    ///
    /// Jelly Movement Settings
    ///
    public static ForgeConfigSpec.DoubleValue JELLY_MOVEMENT_SPEED;
    public static ForgeConfigSpec.DoubleValue JELLY_JUMP_STRENGTH;
    public static ForgeConfigSpec.DoubleValue JELLY_SWIM_SPEED;

    ///
    /// Jelly Health Settings
    ///
    public static ForgeConfigSpec.DoubleValue JELLY_MAX_HEALTH;
    public static ForgeConfigSpec.DoubleValue JELLY_HEALTH_REGEN_RATE;
    public static ForgeConfigSpec.DoubleValue JELLY_DAMAGE_RESISTANCE;

    ///
    /// Jelly Interaction Settings
    ///
    public static ForgeConfigSpec.IntValue JELLY_INTERACTION_COOLDOWN;
    public static ForgeConfigSpec.IntValue JELLY_INTERACTION_RANGE;

    ///
    /// Jelly Environmental Settings
    ///
    public static ForgeConfigSpec.BooleanValue JELLY_WEATHER_EFFECTS;
    public static ForgeConfigSpec.DoubleValue JELLY_TEMPERATURE_TOLERANCE;

    ///
    /// New Configs
    ///
    public static ForgeConfigSpec.IntValue JELLY_BREEDING_COOLDOWN;
    public static ForgeConfigSpec.IntValue JELLY_LOVE_MODE_DURATION;
    public static ForgeConfigSpec.IntValue JELLY_HUNGER_DECREASE_INTERVAL;
    public static ForgeConfigSpec.IntValue JELLY_HUNGER_THRESHOLD;
    public static ForgeConfigSpec.IntValue JELLY_MAX_HUNGER;
    public static ForgeConfigSpec.BooleanValue JELLY_CAN_DROP_ITEMS;
    public static ForgeConfigSpec.IntValue JELLY_ITEM_DROP_INTERVAL;
    public static ForgeConfigSpec.BooleanValue JELLY_CAN_ABSORB_ITEMS;
    public static ForgeConfigSpec.IntValue JELLY_ITEM_ABSORB_INTERVAL;
    public static ForgeConfigSpec.BooleanValue JELLY_CAN_FORM_FAMILIES;
    public static ForgeConfigSpec.IntValue JELLY_FAMILY_SIZE_LIMIT;
    public static ForgeConfigSpec.BooleanValue JELLY_CAN_NEST;
    public static ForgeConfigSpec.IntValue JELLY_NESTING_COOLDOWN;
    public static ForgeConfigSpec.BooleanValue JELLY_CAN_EVOLVE;
    public static ForgeConfigSpec.IntValue JELLY_EVOLUTION_CHANCE;
    public static ForgeConfigSpec.BooleanValue JELLY_CAN_MIMIC;
    public static ForgeConfigSpec.IntValue JELLY_MIMICRY_COOLDOWN;
    public static ForgeConfigSpec.BooleanValue JELLY_CAN_PLAY;
    public static ForgeConfigSpec.IntValue JELLY_PLAY_DURATION;
    public static ForgeConfigSpec.BooleanValue JELLY_CAN_EXPLORE;
    public static ForgeConfigSpec.IntValue JELLY_EXPLORATION_RADIUS;

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, SPEC, "sticky_resources-common.toml");
    }

    public static final ForgeConfigSpec SPEC;
    private static final StickyResourcesConfig COMMON;

    static {
        final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        ///
        /// Jelly Custom AI Activation Settings
        ///
        COMMON_BUILDER.comment("\nJelly Custom AI Activation Settings:\n").push("jelly_custom_ai_activation");
        JELLY_CUSTOM_AI_ACTIVE = COMMON_BUILDER.comment("Activate the jellies custom AI. Default: false")
                .define("jelly_custom_ai_active", false);
        JELLY_BREEDING_ACTIVE = COMMON_BUILDER.comment("Activate jelly breeding. Default: true")
                .define("jelly_breeding_active", true);
        JELLY_BREEDING_RITUAL_ACTIVE = COMMON_BUILDER.comment("Activate jelly breeding rituals. Default: true")
                .define("jelly_breeding_ritual_active", true);
        JELLY_COMMUNICATION_ACTIVE = COMMON_BUILDER.comment("Activate jelly communication. Default: true")
                .define("jelly_communication_active", true);
        JELLY_CURIOSITY_ACTIVE = COMMON_BUILDER.comment("Activate jelly curiosity. Default: true")
                .define("jelly_curiosity_active", true);
        JELLY_DEFENSE_ACTIVE = COMMON_BUILDER.comment("Activate jelly defense. Default: true")
                .define("jelly_defense_active", true);
        JELLY_ENVIRONMENT_ACTIVE = COMMON_BUILDER.comment("Activate jelly environmental interactions. Default: true")
                .define("jelly_environment_active", true);
        JELLY_EVOLUTION_ACTIVE = COMMON_BUILDER.comment("Activate jelly evolution. Default: true")
                .define("jelly_evolution_active", true);
        JELLY_EXPLORATION_ACTIVE = COMMON_BUILDER.comment("Activate jelly exploration. Default: true")
                .define("jelly_exploration_active", true);
        JELLY_GRAZE_ACTIVE = COMMON_BUILDER.comment("Activate jelly grazing. Default: true")
                .define("jelly_graze_active", true);
        JELLY_GRAZING_ACTIVE = COMMON_BUILDER.comment("Activate jelly grazing goals. Default: true")
                .define("jelly_grazing_active", true);
        JELLY_MIGRATION_ACTIVE = COMMON_BUILDER.comment("Activate jelly migration. Default: true")
                .define("jelly_migration_active", true);
        JELLY_MIMICRY_ACTIVE = COMMON_BUILDER.comment("Activate jelly mimicry. Default: true")
                .define("jelly_mimicry_active", true);
        JELLY_JOINT_NESTING_ACTIVE = COMMON_BUILDER.comment("Activate jelly joint nesting. Default: true")
                .define("jelly_joint_nesting_active", true);
        JELLY_PANIC_ACTIVE = COMMON_BUILDER.comment("Activate jelly panic behavior. Default: true")
                .define("jelly_panic_active", true);
        JELLY_PLAY_ACTIVE = COMMON_BUILDER.comment("Activate jelly play behavior. Default: true")
                .define("jelly_play_active", true);
        JELLY_PREDATION_ACTIVE = COMMON_BUILDER.comment("Activate jelly predation. Default: true")
                .define("jelly_predation_active", true);
        JELLY_RESOURCE_ACTIVE = COMMON_BUILDER.comment("Activate jelly resource interactions. Default: true")
                .define("jelly_resource_active", true);
        JELLY_SEASONAL_ACTIVE = COMMON_BUILDER.comment("Activate jelly seasonal behavior. Default: true")
                .define("jelly_seasonal_active", true);
        JELLY_SWARMS_ACTIVE = COMMON_BUILDER.comment("Activate jelly swarms. Default: false")
                .define("jelly_swarms_active", false);
        JELLY_SYMBIOSIS_ACTIVE = COMMON_BUILDER.comment("Activate jelly symbiosis. Default: true")
                .define("jelly_symbiosis_active", true);
        JELLY_FOLLOW_FOOD_ACTIVE = COMMON_BUILDER.comment("Activate jelly symbiosis. Default: true")
                .define("jelly_follow_food_active", true);
        JELLY_SWIM_ACTIVE = COMMON_BUILDER.comment("Activate jelly symbiosis. Default: true")
                .define("jelly_swim_active", true);
        JELLY_CHILD_PROTECTION_ACTIVE = COMMON_BUILDER.comment("Activate jelly symbiosis. Default: true")
                .define("jelly_child_protection_active", true);
        JELLY_EMOTIONAL_ACTIVE = COMMON_BUILDER.comment("Activate jelly symbiosis. Default: true")
                .define("jelly_emotional_active", true);
        JELLY_WONDER_ACTIVE = COMMON_BUILDER.comment("Activate jelly symbiosis. Default: true")
                .define("jelly_wonder_active", true);

        COMMON_BUILDER.pop();

        ///
        /// Swarm Settings
        ///
        COMMON_BUILDER.comment("\nSwarm Settings:\n").push("swarm_settings");
        JELLY_SWARM_LEADER_SELECTION_RADIUS = COMMON_BUILDER.comment("The radius (in blocks) within which a jelly can be chosen as the leader. Default: 10")
                .defineInRange("jellySwarmLeaderSelectionRadius", 10, 1, 50);
        JELLY_SWARM_LEADER_HEALTH_MULTIPLIER = COMMON_BUILDER.comment("The multiplier for the leader's health. Default: 1.5")
                .defineInRange("jellySwarmLeaderHealthMultiplier", 1.5, 1.0, 5.0);
        JELLY_SWARM_LEADER_SPEED_BONUS = COMMON_BUILDER.comment("The speed bonus for the leader. Default: 0.2")
                .defineInRange("jellySwarmLeaderSpeedBonus", 0.2, 0.1, 1.0);
        JELLY_SWARM_MERGE_RADIUS = COMMON_BUILDER.comment("The radius (in blocks) within which jellies can merge into a swarm. Default: 5")
                .defineInRange("jellySwarmMergeRadius", 5, 1, 20);
        JELLY_SWARM_MIN_SIZE = COMMON_BUILDER.comment("The minimum number of jellies required to form a swarm. Default: 5")
                .defineInRange("jellySwarmMinSize", 5, 2, 20);
        JELLY_SWARM_SIGNAL_RADIUS = COMMON_BUILDER.comment("The radius (in blocks) within which the leader's signals can reach. Default: 20")
                .defineInRange("jellySwarmSignalRadius", 20, 5, 50);
        JELLY_SWARM_EVASION_RADIUS = COMMON_BUILDER.comment("The radius (in blocks) within which the swarm will start evading enemies. Default: 10")
                .defineInRange("jellySwarmEvasionRadius", 10, 5, 30);
        JELLY_SWARM_RETREATING_HEALTH_PERCENT = COMMON_BUILDER.comment("The health percentage at which the swarm will retreat. Default: 20")
                .defineInRange("jellySwarmRetreatingHealthPercent", 20, 1, 100);
        JELLY_SWARM_LEADER_PATH_RECALC_INTERVAL = COMMON_BUILDER.comment("The interval (in ticks) at which the leader recalculates its path. Default: 100")
                .defineInRange("jellySwarmLeaderPathRecalcInterval", 100, 10, 1000);
        JELLY_SWARM_LEADER_COOLDOWN = COMMON_BUILDER.comment("The cooldown time (in ticks) before a new leader can be chosen. Default: 24000 (1 Minecraft day)")
                .defineInRange("jellySwarmLeaderCooldown", 24000, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        ///
        /// Grazing Settings
        ///
        COMMON_BUILDER.comment("\nGrazing Settings:\n").push("jelly_grazing_settings");
        JELLY_GRAZE_DURATION = COMMON_BUILDER.comment("The duration of the grazing action in ticks. Default: 100")
                .defineInRange("jellyGrazeDuration", 100, 1, 72000);
        JELLY_NEXT_GRAZE_TIME = COMMON_BUILDER.comment("The duration of time between grazing actions in ticks. Default: 60")
                .defineInRange("jellyNextGrazeDuration", 60, 1, 72000);
        JELLY_GRAZE_SEARCH_RADIUS = COMMON_BUILDER.comment("The radius in blocks to search for grazeable blocks. Default: 6.0")
                .defineInRange("jellyGrazeSearchRadius", 6.0, 1.0, 50.0);
        JELLY_GRAZE_MAX_HOLE_DEPTH = COMMON_BUILDER.comment("The maximum depth a jelly will go to graze a block. Default: 3")
                .defineInRange("jellyGrazeMaxHoleDepth", 3, 1, 10);
        COMMON_BUILDER.pop();

        ///
        /// Jelly Movement Settings
        ///
        COMMON_BUILDER.comment("\nJelly Movement Settings:\n").push("jelly_movement_settings");
        JELLY_MOVEMENT_SPEED = COMMON_BUILDER.comment("The base movement speed of jelly entities. Default: 0.7")
                .defineInRange("jellyMovementSpeed", 0.7, 0.1, 2.0);
        JELLY_JUMP_STRENGTH = COMMON_BUILDER.comment("The strength of the jelly's jump. Default: 0.5")
                .defineInRange("jellyJumpStrength", 0.5, 0.1, 1.5);
        JELLY_SWIM_SPEED = COMMON_BUILDER.comment("The speed at which jelly entities swim. Default: 1.0")
                .defineInRange("jellySwimSpeed", 1.0, 0.1, 2.0);
        COMMON_BUILDER.pop();

        ///
        /// Jelly Health Settings
        ///
        COMMON_BUILDER.comment("\nJelly Health Settings:\n").push("jelly_health_settings");
        JELLY_MAX_HEALTH = COMMON_BUILDER.comment("The maximum health of a jelly entity. Default: 20.0")
                .defineInRange("jellyMaxHealth", 20.0, 1.0, 100.0);
        JELLY_HEALTH_REGEN_RATE = COMMON_BUILDER.comment("The rate at which jelly entities regenerate health. Default: 0.1")
                .defineInRange("jellyHealthRegenRate", 0.1, 0.0, 1.0);
        JELLY_DAMAGE_RESISTANCE = COMMON_BUILDER.comment("The amount of damage resistance jelly entities have. Default: 0.0")
                .defineInRange("jellyDamageResistance", 0.0, 0.0, 1.0);
        COMMON_BUILDER.pop();

        ///
        /// Jelly Interaction Settings
        ///
        COMMON_BUILDER.comment("\nJelly Interaction Settings:\n").push("jelly_interaction_settings");
        JELLY_INTERACTION_COOLDOWN = COMMON_BUILDER.comment("The cooldown time between interactions with blocks or entities. Default: 20")
                .defineInRange("jellyInteractionCooldown", 20, 1, 72000);
        JELLY_INTERACTION_RANGE = COMMON_BUILDER.comment("The range at which jelly entities can interact with blocks or entities. Default: 4")
                .defineInRange("jellyInteractionRange", 4, 1, 16);
        COMMON_BUILDER.pop();

        ///
        /// Jelly Environmental Settings
        ///
        COMMON_BUILDER.comment("\nJelly Environmental Settings:\n").push("jelly_environmental_settings");
        JELLY_WEATHER_EFFECTS = COMMON_BUILDER.comment("Whether jelly entities are affected by weather conditions. Default: true")
                .define("jellyWeatherEffects", true);
        JELLY_TEMPERATURE_TOLERANCE = COMMON_BUILDER.comment("The temperature range within which jelly entities can survive. Default: 10.0")
                .defineInRange("jellyTemperatureTolerance", 10.0, -50.0, 50.0);
        COMMON_BUILDER.pop();

        ///
        /// New Configs
        ///
        COMMON_BUILDER.comment("\nNew Configs:\n").push("new_configs");
        JELLY_BREEDING_COOLDOWN = COMMON_BUILDER.comment("The cooldown time (in ticks) between breeding attempts. Default: 6000")
                .defineInRange("jellyBreedingCooldown", 6000, 0, Integer.MAX_VALUE);
        JELLY_LOVE_MODE_DURATION = COMMON_BUILDER.comment("The duration (in ticks) of love mode. Default: 600")
                .defineInRange("jellyLoveModeDuration", 600, 0, Integer.MAX_VALUE);
        JELLY_HUNGER_DECREASE_INTERVAL = COMMON_BUILDER.comment("The interval (in ticks) at which hunger decreases. Default: 20")
                .defineInRange("jellyHungerDecreaseInterval", 20, 1, 72000);
        JELLY_HUNGER_THRESHOLD = COMMON_BUILDER.comment("The hunger level at which the jelly starts to suffer. Default: 5")
                .defineInRange("jellyHungerThreshold", 5, 0, 20);
        JELLY_MAX_HUNGER = COMMON_BUILDER.comment("The maximum hunger level. Default: 20")
                .defineInRange("jellyMaxHunger", 20, 1, 100);
        JELLY_CAN_DROP_ITEMS = COMMON_BUILDER.comment("Whether jellies can drop items. Default: true")
                .define("jellyCanDropItems", true);
         JELLY_CAN_ABSORB_ITEMS = COMMON_BUILDER.comment("Whether jellies can absorb items. Default: true")
                .define("jellyCanAbsorbItems", true);
        JELLY_ITEM_ABSORB_INTERVAL = COMMON_BUILDER.comment("The interval (in ticks) at which jellies absorb items. Default: 600")
                .defineInRange("jellyItemAbsorbInterval", 600, 1, 72000);
        JELLY_CAN_FORM_FAMILIES = COMMON_BUILDER.comment("Whether jellies can form families. Default: true")
                .define("jellyCanFormFamilies", true);
        JELLY_FAMILY_SIZE_LIMIT = COMMON_BUILDER.comment("The maximum size of a jelly family. Default: 10")
                .defineInRange("jellyFamilySizeLimit", 10, 1, 50);
        JELLY_CAN_NEST = COMMON_BUILDER.comment("Whether jellies can nest. Default: true")
                .define("jellyCanNest", true);
        JELLY_NESTING_COOLDOWN = COMMON_BUILDER.comment("The cooldown time (in ticks) between nesting attempts. Default: 24000")
                .defineInRange("jellyNestingCooldown", 24000, 0, Integer.MAX_VALUE);
        JELLY_CAN_EVOLVE = COMMON_BUILDER.comment("Whether jellies can evolve. Default: true")
                .define("jellyCanEvolve", true);
        JELLY_EVOLUTION_CHANCE = COMMON_BUILDER.comment("The chance (in percent) for a jelly to evolve. Default: 1")
                .defineInRange("jellyEvolutionChance", 1, 0, 100);
        JELLY_CAN_MIMIC = COMMON_BUILDER.comment("Whether jellies can mimic other entities. Default: true")
                .define("jellyCanMimic", true);
        JELLY_MIMICRY_COOLDOWN = COMMON_BUILDER.comment("The cooldown time (in ticks) between mimicry attempts. Default: 6000")
                .defineInRange("jellyMimicryCooldown", 6000, 0, Integer.MAX_VALUE);
        JELLY_CAN_PLAY = COMMON_BUILDER.comment("Whether jellies can play. Default: true")
                .define("jellyCanPlay", true);
        JELLY_PLAY_DURATION = COMMON_BUILDER.comment("The duration (in ticks) of play behavior. Default: 600")
                .defineInRange("jellyPlayDuration", 600, 0, Integer.MAX_VALUE);
        JELLY_CAN_EXPLORE = COMMON_BUILDER.comment("Whether jellies can explore. Default: true")
                .define("jellyCanExplore", true);
        JELLY_EXPLORATION_RADIUS = COMMON_BUILDER.comment("The radius (in blocks) within which jellies explore. Default: 20")
                .defineInRange("jellyExplorationRadius", 20, 1, 100);
        COMMON_BUILDER.pop();

        ///
        /// Finalize the config
        ///
        COMMON = new StickyResourcesConfig(COMMON_BUILDER);
        SPEC = COMMON_BUILDER.build();
    }

    public StickyResourcesConfig(ForgeConfigSpec.Builder builder) {
        builder.build();
    }
}