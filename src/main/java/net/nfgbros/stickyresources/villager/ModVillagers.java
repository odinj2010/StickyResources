package net.nfgbros.stickyresources.villager;

import com.google.common.collect.ImmutableSet;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {

    // Deferred Registers for POI Types and Villager Professions
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, StickyResources.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, StickyResources.MOD_ID);

    // Point of Interest (POI) for the custom 'sound' block
    public static final RegistryObject<PoiType> SOUND_POI = POI_TYPES.register("sound_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.SOUND_BLOCK.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    // Custom Villager Profession: Sound Master
    public static final RegistryObject<VillagerProfession> SOUND_MASTER =
            VILLAGER_PROFESSIONS.register("soundmaster",
                    () -> new VillagerProfession("soundmaster",
                            holder -> holder.get() == SOUND_POI.get(), // Workstation POI condition
                            holder -> holder.get() == SOUND_POI.get(), // Nearby POI condition
                            ImmutableSet.of(), // Gatherable items
                            ImmutableSet.of(), // Secondary priority items
                            SoundEvents.VILLAGER_WORK_ARMORER)); // Work sound

    // Method to register the POI types and Villager professions
    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
