package net.nfgbros.stickyresources.item;

import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.item.custom.FuelItem;
import net.nfgbros.stickyresources.item.custom.MetalDetectorItem;
import net.nfgbros.stickyresources.item.custom.ModArmorItem;
import net.nfgbros.stickyresources.sound.ModSounds;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, StickyResources.MOD_ID);

    public static final RegistryObject<Item> PINE_CONE = ITEMS.register("pine_cone",
            () -> new FuelItem(new Item.Properties(), 400));
    ///
    /// Utility Items ///
    ///
    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties().durability(100)));
    ///
    /// Sapphire Gem ///
    ///
    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_SAPPHIRE = ITEMS.register("raw_sapphire",
            () -> new Item(new Item.Properties()));
    ///
    /// Sapphire tools and weapons///
    ///
    public static final RegistryObject<Item> SAPPHIRE_SWORD = ITEMS.register("sapphire_sword",
            () -> new SwordItem(ModToolTiers.SAPPHIRE, 4, 2, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_STAFF = ITEMS.register("sapphire_staff",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SAPPHIRE_PICKAXE = ITEMS.register("sapphire_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SAPPHIRE, 1, 1, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_AXE = ITEMS.register("sapphire_axe",
            () -> new AxeItem(ModToolTiers.SAPPHIRE, 7, 1, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_SHOVEL = ITEMS.register("sapphire_shovel",
            () -> new ShovelItem(ModToolTiers.SAPPHIRE, 0, 0, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_HOE = ITEMS.register("sapphire_hoe",
            () -> new HoeItem(ModToolTiers.SAPPHIRE, 0, 0, new Item.Properties()));
    ///
    /// Sapphire Armor Items///
    ///
    public static final RegistryObject<Item> SAPPHIRE_HELMET = ITEMS.register("sapphire_helmet",
            () -> new ModArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_CHESTPLATE = ITEMS.register("sapphire_chestplate",
            () -> new ArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_LEGGINGS = ITEMS.register("sapphire_leggings",
            () -> new ArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_BOOTS = ITEMS.register("sapphire_boots",
            () -> new ArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.BOOTS, new Item.Properties()));
    ///
    /// Seeds ///
    ///
    public static final RegistryObject<Item> STRAWBERRY_SEEDS = ITEMS.register("strawberry_seeds",
            () -> new ItemNameBlockItem(ModBlocks.STRAWBERRY_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORN_SEEDS = ITEMS.register("corn_seeds",
            () -> new ItemNameBlockItem(ModBlocks.CORN_CROP.get(), new Item.Properties()));
    ///
    /// Seed Items///
    ///
    public static final RegistryObject<Item> CORN = ITEMS.register("corn",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry",
            () -> new Item(new Item.Properties().food(ModFoods.STRAWBERRY)));
    ///
    /// Music and Audio ///
    ///
    public static final RegistryObject<Item> BAR_BRAWL_MUSIC_DISC = ITEMS.register("bar_brawl_music_disc",
            () -> new RecordItem(6, ModSounds.BAR_BRAWL, new Item.Properties().stacksTo(1), 2440));
    ///
    /// Spawn Eggs ///
    ///
    public static final RegistryObject<Item> RHINO_SPAWN_EGG = ITEMS.register("rhino_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.RHINO, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_SPAWN_EGG = ITEMS.register("jelly_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    /// ///
    /// Custom Jellies Spawn Eggs///
    /// ///
    public static final RegistryObject<Item> JELLY_BONE_SPAWN_EGG = ITEMS.register("jelly_bone_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_BONE, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_COAL_SPAWN_EGG = ITEMS.register("jelly_coal_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_COAL, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_CHARCOAL_SPAWN_EGG = ITEMS.register("jelly_charcoal_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_CHARCOAL, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_COBBLESTONE_SPAWN_EGG = ITEMS.register("jelly_cobblestone_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_COBBLESTONE, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_COPPER_SPAWN_EGG = ITEMS.register("jelly_copper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_COPPER, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_DIAMOND_SPAWN_EGG = ITEMS.register("jelly_diamond_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_DIAMOND, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_DIRT_SPAWN_EGG = ITEMS.register("jelly_dirt_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_DIRT, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_ELECTRIC_SPAWN_EGG = ITEMS.register("jelly_electric_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_ELECTRIC, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_EMERALD_SPAWN_EGG = ITEMS.register("jelly_emerald_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_EMERALD, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_ENDER_PEARL_SPAWN_EGG = ITEMS.register("jelly_ender_pearl_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_ENDER_PEARL, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_GLASS_SPAWN_EGG = ITEMS.register("jelly_glass_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_GLASS, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_GOLD_SPAWN_EGG = ITEMS.register("jelly_gold_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_GOLD, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_GRAVEL_SPAWN_EGG = ITEMS.register("jelly_gravel_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_GRAVEL, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_IRON_SPAWN_EGG = ITEMS.register("jelly_iron_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_IRON, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_LAPIS_SPAWN_EGG = ITEMS.register("jelly_lapis_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_LAPIS, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_LAVA_SPAWN_EGG = ITEMS.register("jelly_lava_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_LAVA, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_OAK_LOG_SPAWN_EGG = ITEMS.register("jelly_oak_log_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_OAK_LOG, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_OBSIDIAN_SPAWN_EGG = ITEMS.register("jelly_obsidian_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_OBSIDIAN, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_PRISMERINE_SPAWN_EGG = ITEMS.register("jelly_prismerine_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_PRISMERINE, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_REDSTONE_SPAWN_EGG = ITEMS.register("jelly_redstone_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_REDSTONE, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_SAND_SPAWN_EGG = ITEMS.register("jelly_sand_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_SAND, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_SAPPHIRE_SPAWN_EGG = ITEMS.register("jelly_sapphire_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_SAPPHIRE, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_WATER_SPAWN_EGG = ITEMS.register("jelly_water_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_WATER, 0x7e9680, 0xc5d1c5, new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
