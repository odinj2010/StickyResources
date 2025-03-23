package net.nfgbros.stickyresources;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.block.entity.ModBlockEntities;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.client.JellyRenderer;
import net.nfgbros.stickyresources.item.ModCreativeModeTabs;
import net.nfgbros.stickyresources.item.ModItems;
import net.nfgbros.stickyresources.loot.ModLootModifiers;
import net.nfgbros.stickyresources.screen.ModMenuTypes;
import net.nfgbros.stickyresources.screen.GemPolishingStationScreen;
import net.nfgbros.stickyresources.sound.ModSounds;
import net.nfgbros.stickyresources.villager.ModVillagers;
import org.slf4j.Logger;

@Mod(StickyResources.MOD_ID) // Marks this class as the main mod class
public class StickyResources {
    public static final String MOD_ID = "sticky_resources"; // Unique mod ID
    private static final Logger LOGGER = LogUtils.getLogger(); // Logger for debugging

    public StickyResources() {
        // Get the mod event bus for registering events
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register all mod content
        ModCreativeModeTabs.register(modEventBus); // Creative mode tabs
        ModItems.register(modEventBus); // Items
        ModBlocks.register(modEventBus); // Blocks
        ModLootModifiers.register(modEventBus); // Loot modifiers
        ModVillagers.register(modEventBus); // Villagers
        ModSounds.register(modEventBus); // Sounds
        ModEntities.register(modEventBus); // Entities
        ModBlockEntities.register(modEventBus); // Block entities
        ModMenuTypes.register(modEventBus); // Menu types

        // Register the mod's configuration
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, StickyResourcesConfig.SPEC);

        // Register event listeners
        modEventBus.addListener(this::commonSetup); // Common setup (runs on both client and server)
        modEventBus.addListener(this::addCreative); // Add items to creative tabs

        // Register Forge events
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Common setup method. Runs on both the client and server during mod initialization.
     * Use this for tasks that need to happen before the game starts.
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
        // Add custom plants to the flower pot
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CATMINT.getId(), ModBlocks.POTTED_CATMINT);
        });

        // Register villager POIs (Points of Interest)
        event.enqueueWork(ModVillagers::registerPOIs);
    }

    /**
     * Adds items to the creative mode tabs.
     */
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // Add items to the "Ingredients" tab
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.STICKY_AMETHYST);
            event.accept(ModItems.STICKY_COAL);
            // Add more items as needed
        }
    }

    /**
     * Client setup method. Runs only on the client during mod initialization.
     * Use this for tasks that are specific to the client, such as rendering.
     */
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Register custom screens for GUIs
            event.enqueueWork(() -> {
                MenuScreens.register(ModMenuTypes.GEM_POLISHING_MENU.get(), GemPolishingStationScreen::new);
            });

            // Register entity renderers for custom entities
            for (ModEntities.JellyType type : ModEntities.JellyType.values()) {
                EntityRenderers.register(ModEntities.JELLY_ENTITIES.get(type).get(), JellyRenderer::new);
            }
        }
    }
}
