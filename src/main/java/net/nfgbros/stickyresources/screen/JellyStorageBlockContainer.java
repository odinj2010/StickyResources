package net.nfgbros.stickyresources.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.nfgbros.stickyresources.block.entity.JellyStorageBlockEntity;
import net.nfgbros.stickyresources.block.ModBlocks;

public class JellyStorageBlockContainer extends AbstractContainerMenu {
    private final JellyStorageBlockEntity blockEntity;
    private final IItemHandler itemHandler;

    // Constructor that accepts the JellyStorageBlockEntity
    public JellyStorageBlockContainer(int windowId, JellyStorageBlockEntity blockEntity, Inventory playerInventory) {
        super(ModMenuTypes.JELLY_STORAGE_BLOCK_CONTAINER.get(), windowId);
        this.blockEntity = blockEntity;

        // Retrieve the ITEM_HANDLER capability from the block entity
        this.itemHandler = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER)
                .orElseThrow(() -> new RuntimeException("Jelly Storage Block Entity has no ItemHandler capability!"));

        // Add slots for the jelly storage block
        addJellyStorageSlots();

        // Add player's inventory slots
        addPlayerInventorySlots(playerInventory);

        // Add player's hotbar slots
        addPlayerHotbarSlots(playerInventory);
    }

    // Add slots for the Jelly Storage Block inventory
    private void addJellyStorageSlots() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new SlotItemHandler(itemHandler, i * 9 + j, 8 + j * 18, 18 + i * 18));
            }
        }
    }

    // Add slots for the player's main inventory 
    private void addPlayerInventorySlots(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    // Add slots for the player's hotbar
    private void addPlayerHotbarSlots(Inventory playerInventory) {
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean stillValid(net.minecraft.world.entity.player.Player player) {
        // Ensure that the container is still valid if the player is interacting with the correct block
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()),
                player, ModBlocks.JELLY_STORAGE_BE.get());
    }

    @Override
    public ItemStack quickMoveStack(net.minecraft.world.entity.player.Player pPlayer, int pIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);

        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemStack = stackInSlot.copy();

            // If the slot is in the Jelly Storage Block (index 0 to 26)
            if (pIndex < 27) {
                // Move item to the player's inventory
                if (!this.moveItemStackTo(stackInSlot, 27, 63, true)) {
                    return ItemStack.EMPTY;
                }
            }
            // If the slot is in the player's inventory (index 27 to 62)
            else if (pIndex >= 27 && pIndex < 63) {
                // Move item to the Jelly Storage Block
                if (!this.moveItemStackTo(stackInSlot, 0, 27, false)) {
                    return ItemStack.EMPTY;
                }
            }

            // Update slot if the stack is empty
            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.set(stackInSlot);
            }
        }

        return itemStack;
    }
}
