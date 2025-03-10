package net.nfgbros.stickyresources.loot;

import com.mojang.serialization.Codec;
import net.nfgbros.stickyresources.StickyResources;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {

    // Register for global loot modifier serializers
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, StickyResources.MOD_ID);

    // Registry object for the "add_item" loot modifier
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM =
            LOOT_MODIFIER_SERIALIZERS.register("add_item", AddItemModifier.CODEC);

    // Registry object for the "add_sus_sand_item" loot modifier
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_SUS_SAND_ITEM =
            LOOT_MODIFIER_SERIALIZERS.register("add_sus_sand_item", AddSusSandItemModifier.CODEC);

    // Register the loot modifier serializers to the event bus
    public static void register(IEventBus eventBus) {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
