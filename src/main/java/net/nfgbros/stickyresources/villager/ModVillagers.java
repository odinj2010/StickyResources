package net.nfgbros.stickyresources.villager;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.ModBlocks;

import java.util.Set;
import java.util.function.Supplier;

public class ModVillagers {
    // Deferred register for POI types
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, StickyResources.MOD_ID);

    // Register custom POI types
    //public static final RegistryObject<PoiType> STICKY_WORKBENCH_POI = registerPOI("sticky_workbench_poi",
    //        () -> ModBlocks.STICKY_WORKBENCH.get(), 1, 1);

    // Helper method to register a POI type
    private static RegistryObject<PoiType> registerPOI(String name, Supplier<Block> block, int maxTickets, int validRange) {
        return POI_TYPES.register(name, () -> new PoiType(Set.copyOf(block.get().getStateDefinition().getPossibleStates()), maxTickets, validRange));
    }

    // Register POI types with the event bus
    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
    }

    // Register POIs with the game
    public static void registerPOIs() {
       // PoiType.registerBlockStates(STICKY_WORKBENCH_POI.get());
    }
}
     