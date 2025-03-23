package net.nfgbros.stickyresources.item;

import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.item.custom.FuelItem;
import net.nfgbros.stickyresources.item.custom.MetalDetectorItem;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nfgbros.stickyresources.item.custom.StickyCatalystItem;

import java.util.EnumMap;
import java.util.Map;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, StickyResources.MOD_ID);

    public static final RegistryObject<Item> PINE_CONE = ITEMS.register("pine_cone",
            () -> new FuelItem(new Item.Properties(), 200));


    public static final RegistryObject<Item> STICKY_CATALYST = ITEMS.register("sticky_catalyst",
            () -> new StickyCatalystItem(new Item.Properties()));


    // Sticky Items
    public static final RegistryObject<Item> STICKY_AMETHYST = ITEMS.register("sticky_amethyst",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_BEEF = ITEMS.register("sticky_beef",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_BONE_MEAL = ITEMS.register("sticky_bone_meal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> JELLY_CAKE = ITEMS.register("jelly_cake",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_COAL = ITEMS.register("sticky_coal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_CHARCOAL = ITEMS.register("sticky_charcoal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_DIAMOND = ITEMS.register("sticky_diamond",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_EMERALD = ITEMS.register("sticky_emerald",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_ENDER_PEARL = ITEMS.register("sticky_ender_pearl",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_GRASS = ITEMS.register("sticky_grass",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_LAPIS_LAZULI = ITEMS.register("sticky_lapis_lazuli",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_PRISMERINE_CRYSTALS = ITEMS.register("sticky_prismerine_crystals",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_RAW_COPPER = ITEMS.register("sticky_raw_copper",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_RAW_GOLD = ITEMS.register("sticky_raw_gold",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_RAW_IRON = ITEMS.register("sticky_raw_iron",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_RAW_SAPPHIRE = ITEMS.register("sticky_raw_sapphire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_RED_MUSHROOM = ITEMS.register("sticky_red_mushroom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_REDSTONE_DUST = ITEMS.register("sticky_redstone_dust",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_ROTTON_FLESH = ITEMS.register("sticky_rotton_flesh",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STICKY_STRAWBERRY = ITEMS.register("sticky_strawberry",
            () -> new Item(new Item.Properties()));

    // Utility Items
    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties().durability(100)));

    // Seeds
    public static final RegistryObject<Item> CORN_SEEDS = ITEMS.register("corn_seeds",
            () -> new ItemNameBlockItem(ModBlocks.CORN_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORN = ITEMS.register("corn", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STRAWBERRY_SEEDS = ITEMS.register("strawberry_seeds",
            () -> new ItemNameBlockItem(ModBlocks.STRAWBERRY_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry", () -> new Item(new Item.Properties()));

    // Sapphire Gem
    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_SAPPHIRE = ITEMS.register("raw_sapphire",
            () -> new Item(new Item.Properties()));

    // Sapphire Tools
    public static final RegistryObject<Item> SAPPHIRE_SWORD = ITEMS.register("sapphire_sword",
            () -> new SwordItem(ModToolTiers.SAPPHIRE, 4, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_PICKAXE = ITEMS.register("sapphire_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SAPPHIRE, 2, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_AXE = ITEMS.register("sapphire_axe",
            () -> new AxeItem(ModToolTiers.SAPPHIRE, 6, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_SHOVEL = ITEMS.register("sapphire_shovel",
            () -> new ShovelItem(ModToolTiers.SAPPHIRE, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_HOE = ITEMS.register("sapphire_hoe",
            () -> new HoeItem(ModToolTiers.SAPPHIRE, -3, 0.0F, new Item.Properties()));

    public static final RegistryObject<Item> SAPPHIRE_STAFF = ITEMS.register("sapphire_staff",
            () -> new Item(new Item.Properties()));

    // Sapphire Armor
    public static final RegistryObject<Item> SAPPHIRE_HELMET = ITEMS.register("sapphire_helmet",
            () -> new ArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_CHESTPLATE = ITEMS.register("sapphire_chestplate",
            () -> new ArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_LEGGINGS = ITEMS.register("sapphire_leggings",
            () -> new ArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_BOOTS = ITEMS.register("sapphire_boots",
            () -> new ArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> BAR_BRAWL_MUSIC_DISC = ITEMS.register("bar_brawl_music_disc",
            () -> new Item(new Item.Properties()));

    // Spawn Egg Registration (Corrected)
    private static final Map<ModEntities.JellyType, Integer[]> JELLY_SPAWN_EGG_COLORS = new EnumMap<>(ModEntities.JellyType.class);
    static {
        //Jelly Spawn Eggs                             //Jelly Type             //Color Codes
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.AMETHYST, new Integer[]{0x9966cc, 0x663399}); // Purple
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.BONE, new Integer[]{0xf2f2f2, 0xd9d9d9}); // Bone white
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.CAKE, new Integer[]{0xffe5b4, 0xd2691e}); // Cake-like colors
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.CHARCOAL, new Integer[]{0x3c3c3c, 0x262626}); // Dark grey
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.COAL, new Integer[]{0x1c1c1c, 0x0d0d0d}); // Black
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.COBBLESTONE, new Integer[]{0x7d7d7d, 0x5c5c5c}); // Stone grey
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.COW, new Integer[]{0x4c322e, 0x8b5a2b}); // Cowhide brown
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.DEFAULT, new Integer[]{0x00ff00, 0x008000}); // Green
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.DIAMOND, new Integer[]{0x5decf4, 0x2ab5e4}); // Light blue
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.DIRT, new Integer[]{0x9b7653, 0x745637}); // Brown
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.ELECTRIC, new Integer[]{0xffff00, 0xffd700}); // Bright yellow
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.EMERALD, new Integer[]{0x17f275, 0x0fb058}); // Green
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.ENDERPEARL, new Integer[]{0x160f29, 0x4b0082}); // Deep purple
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.FIRE, new Integer[]{0xff4500, 0xff0000}); // Fiery red
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.GLASS, new Integer[]{0xcfdbe6, 0xa1b3ba}); // Light transparent blue
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.GRASS, new Integer[]{0x228B22, 0x6B8E23}); // Green
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.GRAVEL, new Integer[]{0xa39d93, 0x827e76}); // Grey-brown
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.HONEY, new Integer[]{0xffc30f, 0xffa500}); // Honey yellow
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.ICE, new Integer[]{0xa0e9ff, 0x6fcff7}); // Icy blue
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.LAPIS, new Integer[]{0x2661c2, 0x1e4599}); // Blue
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.LAVA, new Integer[]{0xff4500, 0xd43c00}); // Molten red-orange
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.LOGOAK, new Integer[]{0x8B4513, 0x5A3E1A}); // Oak brown
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.MAGNET, new Integer[]{0x555555, 0xff0000}); // Grey and red
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.OBSIDIAN, new Integer[]{0x1c1b29, 0x3b3a4b}); // Deep purple-black
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.PUMPKIN, new Integer[]{0xff7518, 0xffa500}); // Pumpkin orange
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.PRISMERINE, new Integer[]{0x69a49b, 0x4fa194}); // Greenish blue
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.RAWCOPPER, new Integer[]{0xe67300, 0xc06000}); // Copper orange
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.RAWGOLD, new Integer[]{0xffd700, 0xe5be76}); // Gold yellow
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.RAWIRON, new Integer[]{0xd1a382, 0xb87333}); // Iron brown
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.RAWSAPPHIRE, new Integer[]{0x4169e1, 0x4682b4}); // Deep blue
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.REDMUSHROOM, new Integer[]{0xff0000, 0xb22222}); // Mushroom red
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.REDSTONEDUST, new Integer[]{0xff0000, 0xcc0000}); // Bright red
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.ROTTENFLESH, new Integer[]{0x556b2f, 0x6b8e23}); // Greenish brown
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.SAND, new Integer[]{0xf4a460, 0xffd27f}); // Sandy tan
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.STONE, new Integer[]{0x7d7d7d, 0x5c5c5c}); // Stone grey
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.STRAWBERRY, new Integer[]{0xff4c4c, 0xd62d2d}); // Strawberry red
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.WATER, new Integer[]{0x3dd5ff, 0x0077be}); // Ocean blue
        // Add other JellyTypes and their colors here...
    }
    //Register spawn eggs as items
    static {for (ModEntities.JellyType type : ModEntities.JellyType.values()) {Integer[] colors = JELLY_SPAWN_EGG_COLORS.get(type);
        if (colors != null) {
            ITEMS.register("jelly_" + type.name().toLowerCase() + "_spawn_egg", () -> new ForgeSpawnEggItem(
                    () -> ModEntities.JELLY_ENTITIES.get(type).get(), colors[0], colors[1], new Item.Properties()));
        }
    }
    }
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}