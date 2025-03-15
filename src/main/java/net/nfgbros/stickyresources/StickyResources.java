package net.nfgbros.stickyresources;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.block.entity.ModBlockEntities;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.client.JellyRenderer;
import net.nfgbros.stickyresources.item.ModCreativeModeTabs;
import net.nfgbros.stickyresources.item.ModItems;
import net.nfgbros.stickyresources.loot.ModLootModifiers;
import net.nfgbros.stickyresources.screen.ModMenuTypes;
import net.nfgbros.stickyresources.sound.ModSounds;
import net.nfgbros.stickyresources.villager.ModVillagers;
import org.slf4j.Logger;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegisterEvent;

@Mod(StickyResources.MOD_ID)
public class StickyResources {
    public static final String MOD_ID = "sticky_resources";
    private static final Logger LOGGER = LogUtils.getLogger();

    public StickyResources() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register all content
        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        StickyResourcesConfig.register(ModLoadingContext.get());

        // Register event listeners
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CATMINT.getId(), ModBlocks.POTTED_CATMINT);
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            // Add creative items here when needed
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                for (ModEntities.JellyType type : ModEntities.JellyType.values()) {
                    EntityRenderers.register(ModEntities.JELLY_ENTITIES.get(type).get(), JellyRenderer::new);
                }
            });
        }
    }
}
