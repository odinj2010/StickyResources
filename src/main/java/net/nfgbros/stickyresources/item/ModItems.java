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
    public static final RegistryObject<Item> STICKY_BONE_MEAL = ITEMS.register("sticky_bone_meal",
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
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.AMETHYST, new Integer[]{0xeeeeee, 0xcccccc}); // Off-white
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.BONE, new Integer[]{0xeeeeee, 0xcccccc}); // Off-white
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.CHARCOAL, new Integer[]{0x222222, 0x444444}); // Dark grey
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.COAL, new Integer[]{0x111111, 0x222222}); // Very dark grey
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.COBBLESTONE, new Integer[]{0x999999, 0x777777}); // Grey
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.DEFAULT, new Integer[]{0x00ff00, 0x008000}); // Greenish
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.DIAMOND, new Integer[]{0x5decf4, 0x2ab5e4}); // Light blue
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.DIRT, new Integer[]{0x9b7653, 0x745637}); // Brown
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.ELECTRIC, new Integer[]{0xffff00, 0xffd700}); // Yellow, gold
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.EMERALD, new Integer[]{0x17f275, 0x0fb058}); // Bright green
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.ENDERPEARL, new Integer[]{0x160f29, 0xbb64d1}); // Dark purple with lighter purple
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.FIRE, new Integer[]{0xcc4633, 0x9e3526}); // Red
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.GLASS, new Integer[]{0xcfdbe6, 0xa1b3ba}); // Light blue-grey
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.GRAVEL, new Integer[]{0xa39d93, 0x827e76}); // Grey-brown
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.LAPIS, new Integer[]{0x2661c2, 0x1e4599}); // Blue
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.LAVA, new Integer[]{0xff4500, 0xd43c00}); // Red-orange
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.LOGOAK, new Integer[]{0x694e27, 0x513c1c}); // Darker brown
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.MAGNET, new Integer[]{0x555555, 0x666666}); // Darker grey
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.OBSIDIAN, new Integer[]{0x14141e, 0x292944}); // Very dark bluish grey
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.PRISMERINE, new Integer[]{0x69a49b, 0x4fa194}); // Greenish blue
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.RAWCOPPER, new Integer[]{0xeca674, 0xd38b4b}); // Orange-ish
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.RAWGOLD, new Integer[]{0xe5be76, 0xd1a555}); // Yellow-ish
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.RAWIRON, new Integer[]{0xe0a8a1, 0xc98d85}); // Light reddish grey
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.RAWSAPPHIRE, new Integer[]{0x89cff0, 0x5cb3e4}); // Lighter blue
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.REDMUSHROOM, new Integer[]{0xcc4633, 0x9e3526}); // Red
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.REDSTONEDUST, new Integer[]{0xcc4633, 0x9e3526}); // Red
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.SAND, new Integer[]{0xe0cd98, 0xcbbba7}); // Light yellowish brown
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.STONE, new Integer[]{0x999999, 0x777777}); // Grey
        JELLY_SPAWN_EGG_COLORS.put(ModEntities.JellyType.WATER, new Integer[]{0x3dd5ff, 0x39a7e1});// Bright blue
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