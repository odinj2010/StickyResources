package net.nfgbros.stickyresources.screen;

import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.block.entity.GemPolishingStationBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class GemPolishingStationMenu extends AbstractContainerMenu {
    public final GemPolishingStationBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    // Constructor for initializing the menu with the player's inventory and block entity data
    public GemPolishingStationMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public GemPolishingStationMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.GEM_POLISHING_MENU.get(), pContainerId);
        checkContainerSize(inv, 2);

        this.blockEntity = ((GemPolishingStationBlockEntity) entity);
        this.level = inv.player.level();
        this.data = data;

        // Add the player's inventory and hotbar slots
        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        // Add the block entity's item handler slots
        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 11)); // Slot for input
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 80, 59)); // Slot for output
        });

        // Add data slots to synchronize client-side progress
        addDataSlots(data);
    }

    // Checks if the crafting process is ongoing
    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    // Calculates the scaled progress for the GUI progress bar
    public int getScaledProgress() {
        int progress = this.data.get(0); // Current progress
        int maxProgress = this.data.get(1); // Maximum progress

        int progressArrowSize = 26; // Height in pixels of the progress arrow
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    // Slot mapping constants
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 2; // Number of TileEntity slots

    // Handles quick item transfer behavior
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Handle transfers between the player's inventory and the block entity's inventory
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // Transfer from player inventory to block entity inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX,
                    TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // Transfer from block entity inventory to player inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX,
                    VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            // Invalid slot index
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }

        // Update the slot with the remaining items or set it to empty
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    // Checks if the player can interact with the block entity
    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.GEM_POLISHING_STATION.get());
    }

    // Adds the player's main inventory slots to the container
    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < PLAYER_INVENTORY_ROW_COUNT; ++i) {
            for (int l = 0; l < PLAYER_INVENTORY_COLUMN_COUNT; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    // Adds the player's hotbar slots to the container
    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < HOTBAR_SLOT_COUNT; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}