package net.nfgbros.stickyresources.item;

import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.item.custom.FuelItem;
import net.nfgbros.stickyresources.item.custom.MetalDetectorItem;
import net.nfgbros.stickyresources.item.custom.ModArmorItem;
import net.nfgbros.stickyresources.item.custom.jellyentity.JellyEntityItem;
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

    // Fuel Items
    public static final RegistryObject<Item> PINE_CONE = ITEMS.register("pine_cone",
            () -> new FuelItem(new Item.Properties(), 400));

    /// Sticky Items
    public static final RegistryObject<Item> STICKY_BONE_MEAL = ITEMS.register("sticky_bone_meal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_COAL = ITEMS.register("sticky_coal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_CHARCOAL = ITEMS.register("sticky_charcoal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_RAW_COPPER = ITEMS.register("sticky_raw_copper",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_DIAMOND = ITEMS.register("sticky_diamond",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_EMERALD = ITEMS.register("sticky_emerald",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_ENDER_PEARL = ITEMS.register("sticky_ender_pearl",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_RAW_GOLD = ITEMS.register("sticky_raw_gold",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_RAW_IRON = ITEMS.register("sticky_raw_iron",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_LAPIS_LAZULI = ITEMS.register("sticky_lapis_lazuli",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_PRISMERINE_CRYSTALS = ITEMS.register("sticky_prismerine_crystals",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_RAW_SAPPHIRE = ITEMS.register("sticky_raw_sapphire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_REDSTONE_DUST = ITEMS.register("sticky_redstone_dust",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_WATER_BUCKET = ITEMS.register("sticky_water_bucket",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_LAVA_BUCKET = ITEMS.register("sticky_lava_bucket",
            () -> new Item(new Item.Properties()));

    /// Utility Items
    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties().durability(100)));

    /// Sapphire Gems
    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_SAPPHIRE = ITEMS.register("raw_sapphire",
            () -> new Item(new Item.Properties()));

    /// Sapphire Tools and Weapons
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

    /// Sapphire Armor
    public static final RegistryObject<Item> SAPPHIRE_HELMET = ITEMS.register("sapphire_helmet",
            () -> new ModArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_CHESTPLATE = ITEMS.register("sapphire_chestplate",
            () -> new ArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_LEGGINGS = ITEMS.register("sapphire_leggings",
            () -> new ArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_BOOTS = ITEMS.register("sapphire_boots",
            () -> new ArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.BOOTS, new Item.Properties()));

    /// Seed Items and Crops
    public static final RegistryObject<Item> STRAWBERRY_SEEDS = ITEMS.register("strawberry_seeds",
            () -> new ItemNameBlockItem(ModBlocks.STRAWBERRY_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORN_SEEDS = ITEMS.register("corn_seeds",
            () -> new ItemNameBlockItem(ModBlocks.CORN_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORN = ITEMS.register("corn",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry",
            () -> new Item(new Item.Properties().food(ModFoods.STRAWBERRY)));

    /// Music and Audio
    public static final RegistryObject<Item> BAR_BRAWL_MUSIC_DISC = ITEMS.register("bar_brawl_music_disc",
            () -> new RecordItem(6, ModSounds.BAR_BRAWL, new Item.Properties().stacksTo(1), 2440));

    public static final RegistryObject<Item> RHINO_SPAWN_EGG = ITEMS.register("rhino_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.RHINO, 0x7e9680, 0xc5d1c5,
                    new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> JELLY_SPAWN_EGG = ITEMS.register("jelly_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY, 0x7E9680, 0x7E9680, new Item.Properties()));

    /// Custom Jellies Spawn Eggs
    public static final RegistryObject<Item> JELLY_BONE_SPAWN_EGG = ITEMS.register("jelly_bone_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_BONE, 0xE7E0D8, 0x8B8B8B, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_COAL_SPAWN_EGG = ITEMS.register("jelly_coal_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_COAL, 0x3A3A3A, 0x1E1E1E, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_CHARCOAL_SPAWN_EGG = ITEMS.register("jelly_charcoal_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_CHARCOAL, 0x4A4A4A, 0x2B2B2B, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_COBBLESTONE_SPAWN_EGG = ITEMS.register("jelly_cobblestone_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_COBBLESTONE, 0x7D7D7D, 0x5E5E5E, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_COPPER_SPAWN_EGG = ITEMS.register("jelly_copper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_COPPER, 0xB87333, 0x8C6239, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_DIAMOND_SPAWN_EGG = ITEMS.register("jelly_diamond_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_DIAMOND, 0x4EF9F9, 0x3ABCBC, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_DIRT_SPAWN_EGG = ITEMS.register("jelly_dirt_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_DIRT, 0x8B5A2B, 0x603913, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_ELECTRIC_SPAWN_EGG = ITEMS.register("jelly_electric_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_ELECTRIC, 0xFFD700, 0xFFC100, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_EMERALD_SPAWN_EGG = ITEMS.register("jelly_emerald_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_EMERALD, 0x50C878, 0x3E9653, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_ENDER_PEARL_SPAWN_EGG = ITEMS.register("jelly_ender_pearl_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_ENDER_PEARL, 0x1C0E38, 0x3E3A69, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_GLASS_SPAWN_EGG = ITEMS.register("jelly_glass_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_GLASS, 0xD9D9D9, 0xBFBFBF, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_GOLD_SPAWN_EGG = ITEMS.register("jelly_gold_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_GOLD, 0xFFD700, 0xB79F32, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_GRAVEL_SPAWN_EGG = ITEMS.register("jelly_gravel_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_GRAVEL, 0x808080, 0x4F4F4F, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_IRON_SPAWN_EGG = ITEMS.register("jelly_iron_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_IRON, 0xD8D8E8, 0xA3A3B5, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_LAPIS_SPAWN_EGG = ITEMS.register("jelly_lapis_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_LAPIS, 0x2448D8, 0x1A3AA0, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_LAVA_SPAWN_EGG = ITEMS.register("jelly_lava_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_LAVA, 0xFF4500, 0x8B0000, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_OAK_LOG_SPAWN_EGG = ITEMS.register("jelly_oak_log_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_OAK_LOG, 0x8B4500, 0x5A3220, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_OBSIDIAN_SPAWN_EGG = ITEMS.register("jelly_obsidian_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_OBSIDIAN, 0x1A1A2F, 0x0D0D1F, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_PRISMERINE_SPAWN_EGG = ITEMS.register("jelly_prismerine_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_PRISMERINE, 0x79D2B0, 0x4AA385, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_REDSTONE_SPAWN_EGG = ITEMS.register("jelly_redstone_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_REDSTONE, 0xFF0000, 0x8B0000, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_SAND_SPAWN_EGG = ITEMS.register("jelly_sand_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_SAND, 0xFEDF8F, 0xE3C57D, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_SAPPHIRE_SPAWN_EGG = ITEMS.register("jelly_sapphire_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_SAPPHIRE, 0x305DD8, 0x203C8B, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_WATER_SPAWN_EGG = ITEMS.register("jelly_water_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JELLY_WATER, 0x3399FF, 0x2477C8, new Item.Properties()));

    // JellyEntity Items
    public static final RegistryObject<Item> JELLY_ENTITY_ITEM = ITEMS.register("jelly_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY.get()));
    public static final RegistryObject<Item> JELLY_BONE_ENTITY_ITEM = ITEMS.register("jelly_bone_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_BONE.get()));
    public static final RegistryObject<Item> JELLY_COAL_ENTITY_ITEM = ITEMS.register("jelly_coal_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_COAL.get()));
    public static final RegistryObject<Item> JELLY_CHARCOAL_ENTITY_ITEM = ITEMS.register("jelly_charcoal_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_CHARCOAL.get()));
    public static final RegistryObject<Item> JELLY_COBBLESTONE_ENTITY_ITEM = ITEMS.register("jelly_cobblestone_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_COBBLESTONE.get()));
    public static final RegistryObject<Item> JELLY_COPPER_ENTITY_ITEM = ITEMS.register("jelly_copper_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_COPPER.get()));
    public static final RegistryObject<Item> JELLY_DIAMOND_ENTITY_ITEM = ITEMS.register("jelly_diamond_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_DIAMOND.get()));
    public static final RegistryObject<Item> JELLY_DIRT_ENTITY_ITEM = ITEMS.register("jelly_dirt_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_DIRT.get()));
    public static final RegistryObject<Item> JELLY_ELECTRIC_ENTITY_ITEM = ITEMS.register("jelly_electric_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_ELECTRIC.get()));
    public static final RegistryObject<Item> JELLY_EMERALD_ENTITY_ITEM = ITEMS.register("jelly_emerald_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_EMERALD.get()));
    public static final RegistryObject<Item> JELLY_ENDER_PEARL_ENTITY_ITEM = ITEMS.register("jelly_ender_pearl_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_ENDER_PEARL.get()));
    public static final RegistryObject<Item> JELLY_GLASS_ENTITY_ITEM = ITEMS.register("jelly_glass_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_GLASS.get()));
    public static final RegistryObject<Item> JELLY_GOLD_ENTITY_ITEM = ITEMS.register("jelly_gold_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_GOLD.get()));
    public static final RegistryObject<Item> JELLY_GRAVEL_ENTITY_ITEM = ITEMS.register("jelly_gravel_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_GRAVEL.get()));
    public static final RegistryObject<Item> JELLY_IRON_ENTITY_ITEM = ITEMS.register("jelly_iron_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_IRON.get()));
    public static final RegistryObject<Item> JELLY_LAPIS_ENTITY_ITEM = ITEMS.register("jelly_lapis_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_LAPIS.get()));
    public static final RegistryObject<Item> JELLY_LAVA_ENTITY_ITEM = ITEMS.register("jelly_lava_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_LAVA.get()));
    public static final RegistryObject<Item> JELLY_OAK_LOG_ENTITY_ITEM = ITEMS.register("jelly_oak_log_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_OAK_LOG.get()));
    public static final RegistryObject<Item> JELLY_OBSIDIAN_ENTITY_ITEM = ITEMS.register("jelly_obsidian_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_OBSIDIAN.get()));
    public static final RegistryObject<Item> JELLY_PRISMERINE_ENTITY_ITEM = ITEMS.register("jelly_prismerine_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_PRISMERINE.get()));
    public static final RegistryObject<Item> JELLY_REDSTONE_ENTITY_ITEM = ITEMS.register("jelly_redstone_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_REDSTONE.get()));
    public static final RegistryObject<Item> JELLY_SAND_ENTITY_ITEM = ITEMS.register("jelly_sand_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_SAND.get()));
    public static final RegistryObject<Item> JELLY_SAPPHIRE_ENTITY_ITEM = ITEMS.register("jelly_sapphire_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_SAPPHIRE.get()));
    public static final RegistryObject<Item> JELLY_WATER_ENTITY_ITEM = ITEMS.register("jelly_water_entity_item",
            () -> new JellyEntityItem(new Item.Properties(), ModEntities.JELLY_WATER.get()));

    // Register items
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
