package net.nfgbros.stickyresources.datagen;

import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
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
        stickyItem(ModItems.SAPPHIRE_SWORD, "item/sapphire_sword");
        stickyItem(ModItems.SAPPHIRE_PICKAXE, "item/sapphire_pickaxe");
        stickyItem(ModItems.SAPPHIRE_AXE, "item/sapphire_axe");
        stickyItem(ModItems.SAPPHIRE_SHOVEL, "item/sapphire_shovel");
        stickyItem(ModItems.SAPPHIRE_HOE, "item/sapphire_hoe");

        stickyItem(ModItems.SAPPHIRE_HELMET, "item/sapphire_helmet");
        stickyItem(ModItems.SAPPHIRE_CHESTPLATE, "item/sapphire_chestplate");
        stickyItem(ModItems.SAPPHIRE_LEGGINGS, "item/sapphire_leggings");
        stickyItem(ModItems.SAPPHIRE_BOOTS, "item/sapphire_boots");

        for (ModEntities.JellyType type : ModEntities.JellyType.values()) {
            withExistingParent("jelly_" + type.name().toLowerCase() + "_spawn_egg", mcLoc("item/template_spawn_egg"));
        }
    }

    private ItemModelBuilder stickyItem(RegistryObject<Item> item, String texture) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(StickyResources.MOD_ID, texture));
    }
}
