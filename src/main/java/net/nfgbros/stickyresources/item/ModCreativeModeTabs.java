package net.nfgbros.stickyresources.item;

import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, StickyResources.MOD_ID);
    public static final RegistryObject<CreativeModeTab> STICKY_RESOURCES_SAPPHIRE_TAB = CREATIVE_MODE_TABS.register("sticky_resources_sapphire_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SAPPHIRE.get()))
                    .title(Component.translatable("creativetab.sticky_resources_sapphire_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.SAPPHIRE.get());
                        pOutput.accept(ModItems.RAW_SAPPHIRE.get());
                        pOutput.accept(ModItems.SAPPHIRE_STAFF.get());
                        pOutput.accept(ModItems.SAPPHIRE_SWORD.get());
                        pOutput.accept(ModItems.SAPPHIRE_PICKAXE.get());
                        pOutput.accept(ModItems.SAPPHIRE_AXE.get());
                        pOutput.accept(ModItems.SAPPHIRE_SHOVEL.get());
                        pOutput.accept(ModItems.SAPPHIRE_HOE.get());
                        pOutput.accept(ModItems.SAPPHIRE_HELMET.get());
                        pOutput.accept(ModItems.SAPPHIRE_CHESTPLATE.get());
                        pOutput.accept(ModItems.SAPPHIRE_LEGGINGS.get());
                        pOutput.accept(ModItems.SAPPHIRE_BOOTS.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_BLOCK.get());
                        pOutput.accept(ModBlocks.RAW_SAPPHIRE_BLOCK.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_ORE.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get());
                        pOutput.accept(ModBlocks.NETHER_SAPPHIRE_ORE.get());
                        pOutput.accept(ModBlocks.END_STONE_SAPPHIRE_ORE.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_STAIRS.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_SLAB.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_BUTTON.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_PRESSURE_PLATE.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_FENCE.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_FENCE_GATE.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_WALL.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_DOOR.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_TRAPDOOR.get());

                    })
                    .build());
    public static final RegistryObject<CreativeModeTab> STICKY_RESOURCES_DEVICES_TAB = CREATIVE_MODE_TABS.register("sticky_resources_devices_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.METAL_DETECTOR.get()))
                    .title(Component.translatable("creativetab.sticky_resources_devices_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.METAL_DETECTOR.get());
                        pOutput.accept(ModBlocks.GEM_POLISHING_STATION.get());
                    })
                    .build());
    public static final RegistryObject<CreativeModeTab> STICKY_RESOURCES_FOOD_TAB = CREATIVE_MODE_TABS.register("sticky_resources_food_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.STRAWBERRY.get()))
                    .title(Component.translatable("creativetab.sticky_resources_food_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.STRAWBERRY_SEEDS.get());
                        pOutput.accept(ModItems.STRAWBERRY.get());
                        pOutput.accept(ModItems.CORN_SEEDS.get());
                        pOutput.accept(ModItems.CORN.get());
                    })
                    .build());
    public static final RegistryObject<CreativeModeTab> STICKY_RESOURCES_SPAWN_EGG_TAB = CREATIVE_MODE_TABS.register("sticky_resources_spawn_egg_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.JELLY_SPAWN_EGG.get()))
                    .title(Component.translatable("creativetab.sticky_resources_spawn_egg_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.RHINO_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_SPAWN_EGG.get());
                        /// ///////
                        /// ///////
                        pOutput.accept(ModItems.JELLY_BONE_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_COAL_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_CHARCOAL_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_COBBLESTONE_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_COPPER_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_DIAMOND_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_DIRT_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_ELECTRIC_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_EMERALD_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_ENDER_PEARL_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_GLASS_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_GOLD_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_GRAVEL_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_IRON_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_LAPIS_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_LAVA_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_OAK_LOG_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_OBSIDIAN_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_PRISMERINE_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_REDSTONE_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_SAND_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_SAPPHIRE_SPAWN_EGG.get());
                        pOutput.accept(ModItems.JELLY_WATER_SPAWN_EGG.get());

                    })
                    .build());
    public static final RegistryObject<CreativeModeTab> STICKY_RESOURCES_MISC_TAB = CREATIVE_MODE_TABS.register("sticky_resources_misc_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.SOUND_BLOCK.get()))
                    .title(Component.translatable("creativetab.sticky_resources_misc_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.PINE_CONE.get());
                        pOutput.accept(ModBlocks.SOUND_BLOCK.get());
                    })
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
