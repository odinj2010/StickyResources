package net.nfgbros.stickyresources;

import io.netty.util.Attribute;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class StickyResourcesConfig {
    ///-------------------------------------------------------------------------------------------------
    /// Jelly Custom AI Activation Settings
    ///
    public static ForgeConfigSpec.BooleanValue JELLY_CUSTOM_AI_ACTIVE;
    ///-------------------------------------------------------------------------------------------------
    /// Resource Mode Settings
    ///
    public static ForgeConfigSpec.BooleanValue JELLY_RESOURCE_ONLY_MODE;
    /// ------------------------------------------------------------------------------------------------
    /// Register the config
    ///
    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, SPEC, "sticky_resources-common.toml");
    }
    /// ------------------------------------------------------------------------------------------------
    ///
    ///
    public static final ForgeConfigSpec SPEC;
    private static final StickyResourcesConfig COMMON;
    static {
        final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        ///-------------------------------------------------------------------------------------------------
        /// Jelly Custom AI Activation Settings
        ///
        COMMON_BUILDER.comment("\nJelly Settings:\n").push("jelly_settings");
        JELLY_CUSTOM_AI_ACTIVE = COMMON_BUILDER.comment("Activate the jellies custom AI. Default: false")
                .define("jelly_custom_ai_active", false);
        JELLY_RESOURCE_ONLY_MODE = COMMON_BUILDER.comment("Activate resource only mode. Default: false")
                .define("jelly_resources_only_mode", false);
        //add more settings here
        COMMON_BUILDER.pop();
        ///-------------------------------------------------------------------------------------------------
        COMMON = new StickyResourcesConfig(COMMON_BUILDER);
        SPEC = COMMON_BUILDER.build();
    }

    public StickyResourcesConfig(ForgeConfigSpec.Builder builder) {
        builder.build();
    }
}