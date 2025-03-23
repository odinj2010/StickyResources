package net.nfgbros.stickyresources.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.ModBlocks;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, StickyResources.MOD_ID);

    public static final RegistryObject<CreativeModeTab> STICKY_RESOURCES_BLOCKS_TAB = CREATIVE_MODE_TABS.register(
            "sticky_resources_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.STICKY_COBBLESTONE.get()))
                    .title(Component.translatable("creativetab.sticky_resources_blocks_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModBlocks.STICKY_COBBLESTONE.get());
                        pOutput.accept(ModBlocks.STICKY_DIRT.get());
                        pOutput.accept(ModBlocks.STICKY_GLASS.get());
                        pOutput.accept(ModBlocks.STICKY_GRAVEL.get());
                        pOutput.accept(ModBlocks.JELLY_HONEY.get());
                        pOutput.accept(ModBlocks.STICKY_ICE.get());
                        pOutput.accept(ModBlocks.STICKY_LOG_OAK.get());
                        pOutput.accept(ModBlocks.STICKY_OBSIDIAN.get());
                        pOutput.accept(ModBlocks.STICKY_PUMPKIN.get());
                        pOutput.accept(ModBlocks.STICKY_SAND.get());
                        pOutput.accept(ModBlocks.STICKY_STONE.get());
                    }).build()
    );

    public static final RegistryObject<CreativeModeTab> STICKY_RESOURCES_DEVICES_TAB = CREATIVE_MODE_TABS.register(
            "sticky_resources_devices_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.METAL_DETECTOR.get()))
                    .title(Component.translatable("creativetab.sticky_resources_devices_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.METAL_DETECTOR.get());
                        pOutput.accept(ModBlocks.GEM_POLISHING_STATION.get());
                        pOutput.accept(ModItems.STICKY_CATALYST.get());
                    }).build()
    );

    public static final RegistryObject<CreativeModeTab> STICKY_RESOURCES_FOOD_TAB = CREATIVE_MODE_TABS.register(
            "sticky_resources_food_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.STRAWBERRY.get()))
                    .title(Component.translatable("creativetab.sticky_resources_food_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.STRAWBERRY_SEEDS.get());
                        pOutput.accept(ModItems.STRAWBERRY.get());
                        pOutput.accept(ModItems.CORN_SEEDS.get());
                        pOutput.accept(ModItems.CORN.get());
                    }).build()
    );

    public static final RegistryObject<CreativeModeTab> STICKY_RESOURCES_ITEMS_TAB = CREATIVE_MODE_TABS.register(
            "sticky_resources_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.SLIME_BALL))
                    .title(Component.translatable("creativetab.sticky_resources_items_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        //Sticky Items
                        pOutput.accept(ModItems.STICKY_AMETHYST.get());
                        pOutput.accept(ModItems.STICKY_BEEF.get());
                        pOutput.accept(ModItems.STICKY_BONE_MEAL.get());
                        pOutput.accept(ModItems.JELLY_CAKE.get());
                        pOutput.accept(ModItems.STICKY_COAL.get());
                        pOutput.accept(ModItems.STICKY_CHARCOAL.get());
                        pOutput.accept(ModItems.STICKY_RAW_COPPER.get());
                        pOutput.accept(ModItems.STICKY_DIAMOND.get());
                        pOutput.accept(ModBlocks.STICKY_DIRT.get());
                        pOutput.accept(ModItems.STICKY_EMERALD.get());
                        pOutput.accept(ModItems.STICKY_ENDER_PEARL.get());
                        pOutput.accept(ModItems.STICKY_RAW_GOLD.get());
                        pOutput.accept(ModItems.STICKY_RAW_IRON.get());
                        pOutput.accept(ModItems.STICKY_LAPIS_LAZULI.get());
                        pOutput.accept(ModItems.STICKY_PRISMERINE_CRYSTALS.get());
                        pOutput.accept(ModItems.STICKY_RAW_SAPPHIRE.get());
                        pOutput.accept(ModItems.STICKY_RED_MUSHROOM.get());
                        pOutput.accept(ModItems.STICKY_REDSTONE_DUST.get());
                    }).build()
    );

    public static final RegistryObject<CreativeModeTab> STICKY_RESOURCES_MISC_TAB = CREATIVE_MODE_TABS.register(
            "sticky_resources_misc_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.SLIME_BALL))
                    .title(Component.translatable("creativetab.sticky_resources_misc_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.PINE_CONE.get());
                    }).build()
    );

    public static final RegistryObject<CreativeModeTab> STICKY_RESOURCES_SAPPHIRE_TAB = CREATIVE_MODE_TABS.register(
            "sticky_resources_sapphire_tab",
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
                    }).build()
    );

    public static final RegistryObject<CreativeModeTab> STICKY_RESOURCES_SPAWN_EGG_TAB = CREATIVE_MODE_TABS.register(
            "sticky_resources_spawn_egg_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> {
                        RegistryObject<Item> spawnEgg = ModItems.ITEMS.getEntries().stream()
                                .filter(item -> item.getId().getPath().startsWith("jelly_") && item.getId().getPath().endsWith("_spawn_egg"))
                                .findFirst().orElse(null);
                        if (spawnEgg != null && spawnEgg.get() != null) {
                            return new ItemStack(spawnEgg.get());
                        } else {
                            return ItemStack.EMPTY;
                        }
                    })
                    .title(Component.translatable("creativetab.sticky_resources_spawn_egg_tab"))
                    .displayItems((pParameters, pOutput) -> {ModItems.ITEMS.getEntries().stream()
                                .filter(item -> item.getId().getPath().startsWith("jelly_") && item.getId()
                                        .getPath().toLowerCase().endsWith("_spawn_egg"))
                                .forEach(spawnEgg -> {
                                    if(spawnEgg.get() != null){
                                        pOutput.accept(spawnEgg.get());
                                    }
                                });
                    }).build()
    );



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}