package net.nfgbros.stickyresources;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.block.entity.ModBlockEntities;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.client.JellyRenderer;
import net.nfgbros.stickyresources.entity.client.RhinoRenderer;
import net.nfgbros.stickyresources.item.ModCreativeModeTabs;
import net.nfgbros.stickyresources.item.ModItems;
import net.nfgbros.stickyresources.loot.ModLootModifiers;
import net.nfgbros.stickyresources.screen.GemPolishingStationScreen;
import net.nfgbros.stickyresources.screen.ModMenuTypes;
import net.nfgbros.stickyresources.sound.ModSounds;

import net.nfgbros.stickyresources.villager.ModVillagers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(StickyResources.MOD_ID)
public class StickyResources {
    public static final String MOD_ID = "sticky_resources";
    public static final Logger LOGGER = LogUtils.getLogger();

    public StickyResources() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModLootModifiers.register(modEventBus);
        ModVillagers.register(modEventBus);

        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CATMINT.getId(), ModBlocks.POTTED_CATMINT);
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.RAW_SAPPHIRE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            //
            //Rhino
            //
            EntityRenderers.register(ModEntities.RHINO.get(), RhinoRenderer::new);
            //
            //Jellies
            //
            ResourceLocation jellyTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly.png");
            ResourceLocation jellyBoneTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_bone.png");
            ResourceLocation jellyCoalTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_coal.png");
            ResourceLocation jellyCharCoalTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_charcoal.png");
            ResourceLocation jellyCobblestoneTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_cobblestone.png");
            ResourceLocation jellyCopperTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_copper.png");
            ResourceLocation jellyDiamondTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_diamond.png");
            ResourceLocation jellyDirtTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_dirt.png");
            ResourceLocation jellyElectricTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_electric.png");
            ResourceLocation jellyEmeraldTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_emerald.png");
            ResourceLocation jellyEnderPearlTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_enderpearl.png");
            ResourceLocation jellyGlassTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_glass.png");
            ResourceLocation jellyGoldTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_gold.png");
            ResourceLocation jellyGravelTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_gravel.png");
            ResourceLocation jellyIronTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_iron.png");
            ResourceLocation jellyLapisTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_lapis.png");
            ResourceLocation jellyLavaTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_lava.png");
            ResourceLocation jellyOakLogTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_oak.png");
            ResourceLocation jellyObsidianTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_obsidian.png");
            ResourceLocation jellyPrismerineTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_prismerine.png");
            ResourceLocation jellyRedstoneTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_redstone.png");
            ResourceLocation jellySandTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_sand.png");
            ResourceLocation jellySapphireTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_sapphire.png");
            ResourceLocation jellyWaterTexture = new ResourceLocation(MOD_ID, "textures/entity/jelly/jelly_water.png");

            EntityRenderers.register(ModEntities.JELLY.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_BONE.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_COAL.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_CHARCOAL.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_COBBLESTONE.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_COPPER.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_DIAMOND.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_DIRT.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_ELECTRIC.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_EMERALD.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_ENDER_PEARL.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_GLASS.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_GOLD.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_GRAVEL.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_IRON.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_LAPIS.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_LAVA.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_OAK_LOG.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_OBSIDIAN.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_PRISMERINE.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_REDSTONE.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_SAND.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_SAPPHIRE.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            EntityRenderers.register(ModEntities.JELLY_WATER.get(), (context) -> new JellyRenderer<>(context, jellyTexture, jellyBoneTexture, jellyCoalTexture, jellyCharCoalTexture, jellyCobblestoneTexture,jellyCopperTexture,jellyDiamondTexture,jellyDirtTexture,jellyElectricTexture,jellyEmeraldTexture,jellyEnderPearlTexture,jellyGlassTexture,jellyGoldTexture,jellyGravelTexture,jellyIronTexture,jellyLapisTexture,jellyObsidianTexture,jellyPrismerineTexture,jellyRedstoneTexture,jellySandTexture, jellySapphireTexture, jellyWaterTexture, jellyLavaTexture, jellyOakLogTexture));
            //
            //Gem Polishing
            //
            MenuScreens.register(ModMenuTypes.GEM_POLISHING_MENU.get(), GemPolishingStationScreen::new);

        }
    }
}