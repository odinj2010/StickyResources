package net.nfgbros.stickyresources.datagen;

import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.item.ModItems;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, StickyResources.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "StickyResources Item Models";
    }

    @Override
    protected void registerModels() {

        // Sticky Items
        simpleItem(ModItems.STICKY_AMETHYST, "item/amethyst_shard", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_BONE_MEAL, "item/bone_meal", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_COAL, "item/coal", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_CHARCOAL, "item/charcoal", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_RAW_COPPER, "item/raw_copper", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_DIAMOND, "item/diamond", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_EMERALD, "item/emerald", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_ENDER_PEARL, "item/ender_pearl", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_RAW_GOLD, "item/raw_gold", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_RAW_IRON, "item/raw_iron", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_LAPIS_LAZULI, "item/lapis_lazuli", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_PRISMERINE_CRYSTALS, "item/prismarine_crystals", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_RAW_SAPPHIRE, "sticky_resources:item/raw_sapphire", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_RED_MUSHROOM, "block/red_mushroom", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_REDSTONE_DUST, "item/redstone", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_WATER_BUCKET, "item/water_bucket", "item/sticky/jelly_slime_ball");
        simpleItem(ModItems.STICKY_LAVA_BUCKET, "item/lava_bucket", "item/sticky/jelly_slime_ball");

        simpleItem(ModItems.SAPPHIRE, "sticky_resources:item/sapphire");
        simpleItem(ModItems.RAW_SAPPHIRE, "sticky_resources:item/raw_sapphire");

        simpleItem(ModItems.METAL_DETECTOR, "sticky_resources:item/metal_detector");
        simpleItem(ModItems.PINE_CONE, "sticky_resources:item/pine_cone");
        simpleItem(ModItems.STRAWBERRY, "sticky_resources:item/strawberry");
        simpleItem(ModItems.STRAWBERRY_SEEDS, "sticky_resources:item/strawberry_seeds");

        simpleItem(ModItems.CORN, "sticky_resources:item/corn");
        simpleItem(ModItems.CORN_SEEDS, "sticky_resources:item/corn_seeds");

        simpleItem(ModItems.BAR_BRAWL_MUSIC_DISC);

        simpleBlockItem(ModBlocks.SAPPHIRE_DOOR);

        fenceItem(ModBlocks.SAPPHIRE_FENCE, ModBlocks.SAPPHIRE_BLOCK);
        buttonItem(ModBlocks.SAPPHIRE_BUTTON, ModBlocks.SAPPHIRE_BLOCK);
        wallItem(ModBlocks.SAPPHIRE_WALL, ModBlocks.SAPPHIRE_BLOCK);

        evenSimplerBlockItem(ModBlocks.SAPPHIRE_STAIRS);
        evenSimplerBlockItem(ModBlocks.SAPPHIRE_SLAB);
        evenSimplerBlockItem(ModBlocks.SAPPHIRE_PRESSURE_PLATE);
        evenSimplerBlockItem(ModBlocks.SAPPHIRE_FENCE_GATE);

        trapdoorItem(ModBlocks.SAPPHIRE_TRAPDOOR);

        handheldItem(ModItems.SAPPHIRE_SWORD);
        handheldItem(ModItems.SAPPHIRE_PICKAXE);
        handheldItem(ModItems.SAPPHIRE_AXE);
        handheldItem(ModItems.SAPPHIRE_SHOVEL);
        handheldItem(ModItems.SAPPHIRE_HOE);

        trimmedArmorItem(ModItems.SAPPHIRE_HELMET);
        trimmedArmorItem(ModItems.SAPPHIRE_CHESTPLATE);
        trimmedArmorItem(ModItems.SAPPHIRE_LEGGINGS);
        trimmedArmorItem(ModItems.SAPPHIRE_BOOTS);

        simpleBlockItemBlockTexture(ModBlocks.CATMINT);

        // Jelly Spawn Eggs (Preserved from Original Code)
        for (ModEntities.JellyType type : ModEntities.JellyType.values()) {
            withExistingParent("jelly_" + type.name().toLowerCase() + "_spawn_egg", mcLoc("item/template_spawn_egg"));
        }
    }

    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = StickyResources.MOD_ID; // Change this to your mod id

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
                ResourceLocation trimResLoc = new ResourceLocation(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item, String... textureLayers) {
        ItemModelBuilder builder = withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated"));
        for (int i = 0; i < textureLayers.length; i++) {
            String texturePath = textureLayers[i];
            if (!texturePath.isEmpty()) { // Check if the texture path is not empty
                ResourceLocation textureLocation = new ResourceLocation(texturePath);

                // Always prepend the mod ID for the second layer (and any subsequent layers)
                if (i > 0) {
                    textureLocation = new ResourceLocation(StickyResources.MOD_ID, texturePath);
                }
                builder.texture("layer" + i, textureLocation);
            }
        }
        return builder;
    }
    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(StickyResources.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(StickyResources.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(StickyResources.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(StickyResources.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(StickyResources.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(StickyResources.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(StickyResources.MOD_ID,"block/" + item.getId().getPath()));
    }
}
