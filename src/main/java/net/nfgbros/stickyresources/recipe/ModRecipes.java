package net.nfgbros.stickyresources.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nfgbros.stickyresources.StickyResources;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, StickyResources.MOD_ID);

    public static final RegistryObject<RecipeSerializer<WashingStationRecipe>> WASHING_STATION_SERIALIZER =
            SERIALIZERS.register("washing", () -> WashingStationRecipe.Serializer.INSTANCE);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, StickyResources.MOD_ID);

    public static final RegistryObject<RecipeType<WashingStationRecipe>> WASHING_STATION_TYPE =
            RECIPE_TYPES.register("washing", () -> new RecipeType<WashingStationRecipe>() {
                @Override
                public String toString() {
                    return "washing";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
