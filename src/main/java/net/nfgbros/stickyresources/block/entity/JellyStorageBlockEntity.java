package net.nfgbros.stickyresources.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.PacketDistributor;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.item.ModItems;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.nfgbros.stickyresources.item.custom.jellyentity.JellyEntityItem;
import net.nfgbros.stickyresources.network.JellyStorageBlockEntityUpdatePacket;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class JellyStorageBlockEntity extends BlockEntity {

    // Item handler for managing inventory
    private final ItemStackHandler itemHandler = createHandler();

    // Capability wrapper for item handler
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    // List of jelly entities associated with this block entity
    private final List<Entity> jellies = new ArrayList<>();

    // Constructor
    public JellyStorageBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.JELLY_STORAGE_BE.get(), pWorldPosition, pBlockState);
    }

    // Handle incoming update packets and synchronize jellies
    public void handleUpdatePacket(List<Entity> jellies) {
        this.jellies.clear();
        this.jellies.addAll(jellies);
    }

    // Get the current list of jellies
    public List<Entity> getJellies() {
        return jellies;
    }

    // Tick method to handle logic such as jelly actions and item generation
    public void tick() {
        if (level != null && !level.isClientSide()) {
            // Process each jelly entity
            for (int i = 0; i < jellies.size(); i++) {
                Entity jelly = jellies.get(i);
                if (jelly instanceof JellyEntity) {
                    JellyEntity jellyEntity = (JellyEntity) jelly;

                    // Tick the jelly entity
                    jellyEntity.tick();

                    // Check if the jelly is ready to produce an item
                    if (jellyEntity.getProductionTimer() <= 0) {
                        // Generate item based on the jelly
                        ItemStack generatedItem = jellyEntity.generateItem();

                        // Try inserting the item into output slots
                        for (int j = 9; j < itemHandler.getSlots(); j++) {
                            ItemStack remaining = itemHandler.insertItem(j, generatedItem, false);
                            if (remaining.isEmpty()) {
                                generatedItem = ItemStack.EMPTY;
                                break;
                            } else {
                                generatedItem = remaining;
                            }
                        }

                        // Drop the item into the world if output slots are full
                        if (!generatedItem.isEmpty()) {
                            level.addFreshEntity(new ItemEntity(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), generatedItem));
                        }

                        // Reset the jelly's production timer
                        jellyEntity.resetProductionTimer();
                    }
                }
            }
        }

        // Send an update packet to the client
        StickyResources.PACKET_HANDLER.send(
                PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)),
                new JellyStorageBlockEntityUpdatePacket(worldPosition, jellies));
    }

    // Load data from NBT
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag); // Load parent data

        // Load inventory
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));

        // Load jellies
        ListTag jelliesTag = pTag.getList("jellies", 10);
        jellies.clear();
        for (int i = 0; i < jelliesTag.size(); i++) {
            CompoundTag entityTag = jelliesTag.getCompound(i);
            Entity entity = EntityType.loadEntityRecursive(entityTag, level, ent -> {
                ent.setPos(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
                return ent;
            });
            if (entity != null) {
                jellies.add(entity);
            }
        }
    }

    // Save additional data to NBT
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        // Save inventory
        pTag.put("inventory", itemHandler.serializeNBT());

        // Save jellies
        ListTag jelliesTag = new ListTag();
        for (Entity jelly : jellies) {
            if (jelly instanceof INBTSerializable) {
                CompoundTag entityTag = ((INBTSerializable<CompoundTag>) jelly).serializeNBT();
                jelliesTag.add(entityTag);
            }
        }
        pTag.put("jellies", jelliesTag);

        super.saveAdditional(pTag);
    }

    // Create and configure the item handler
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(27) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged(); // Mark the block entity as changed
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                // Only allow JellyEntityItems to be inserted in storage slots
                return stack.getItem() instanceof JellyEntityItem;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                // Prevent inserting items into the output slots (slots 9-26)
                if (slot >= 9) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    // Provide capability for item handler
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull net.minecraftforge.common.capabilities.Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return handler.cast();
        }
        return super.getCapability(cap);
    }

    // Handle and send client-bound block entity data packets
    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithFullMetadata();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }
}