package net.nfgbros.stickyresources.screen;

import net.nfgbros.stickyresources.StickyResources;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.core.BlockPos;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nfgbros.stickyresources.block.entity.JellyStorageBlockEntity;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, StickyResources.MOD_ID);

    public static final RegistryObject<MenuType<GemPolishingStationMenu>> GEM_POLISHING_MENU =
            registerMenuType("gem_polishing_menu", GemPolishingStationMenu::new);

    // Update the constructor here to correctly pass parameters for JellyStorageBlockContainer
    public static final RegistryObject<MenuType<JellyStorageBlockContainer>> JELLY_STORAGE_BLOCK_CONTAINER =
            registerMenuType("jelly_storage_block_container", (windowId, inventory, data) -> {
                BlockPos blockPos = data.readBlockPos();
                JellyStorageBlockEntity blockEntity = (JellyStorageBlockEntity) inventory.player.level().getBlockEntity(blockPos);
                return new JellyStorageBlockContainer(windowId, blockEntity, inventory);
            });

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
