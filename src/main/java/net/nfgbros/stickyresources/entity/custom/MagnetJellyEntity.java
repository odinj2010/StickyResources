package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.nfgbros.stickyresources.util.Magnetism;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import net.minecraft.core.particles.DustParticleOptions;

import java.util.List;

public class MagnetJellyEntity extends JellyEntity {
    private static final EntityDataAccessor<Boolean> ATTRACTING = SynchedEntityData.defineId(MagnetJellyEntity.class, EntityDataSerializers.BOOLEAN);
    private final Magnetism magnetism;
    private final ItemStackHandler inventory = new ItemStackHandler(5);
    private final LazyOptional<IItemHandler> inventoryHandler = LazyOptional.of(() -> inventory);

    public MagnetJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
        this.magnetism = new Magnetism(this);
        this.xpReward = 5;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTRACTING, true);
    }

    public boolean isAttracting() {
        return this.entityData.get(ATTRACTING);
    }

    public void setAttracting(boolean attracting) {
        this.entityData.set(ATTRACTING, attracting);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("Attracting", isAttracting());
        pCompound.put("Inventory", inventory.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setAttracting(pCompound.getBoolean("Attracting"));
        if (pCompound.contains("Inventory")) {
            inventory.deserializeNBT(pCompound.getCompound("Inventory"));
        }
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (!this.level().isClientSide) {
            ItemStack held = pPlayer.getItemInHand(pHand);

            // Toggle Polarity requires Redstone
            if (held.is(net.minecraft.world.item.Items.REDSTONE)) {
                boolean currentState = isAttracting();
                setAttracting(!currentState);

                // Visual Feedback
                float r = currentState ? 1.0f : 0.0f;
                float b = currentState ? 0.0f : 1.0f;

                ((net.minecraft.server.level.ServerLevel)this.level()).sendParticles(new DustParticleOptions(new Vector3f(r, 0.0f, b), 1.0f), this.getX(), this.getY() + 0.5, this.getZ(), 10, 0.5, 0.5, 0.5, 0.1);

                this.playSound(net.minecraft.sounds.SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }

            // Empty hand harvests items
            if (held.isEmpty() && pHand == InteractionHand.MAIN_HAND) {
                boolean droppedAnything = false;
                for (int i = 0; i < inventory.getSlots(); i++) {
                    ItemStack stack = inventory.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        this.spawnAtLocation(stack.copy());
                        inventory.setStackInSlot(i, ItemStack.EMPTY);
                        droppedAnything = true;
                    }
                }
                if (droppedAnything) {
                    this.playSound(net.minecraft.sounds.SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public void tick() {
        super.tick();
        magnetism.tick();
        
        if (!this.level().isClientSide && this.isAttracting() && this.tickCount % 10 == 0) {
            inhaleItems();
        }
    }

    private void inhaleItems() {
        List<ItemEntity> items = this.level().getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(0.5D));
        for (ItemEntity itemEntity : items) {
            ItemStack stack = itemEntity.getItem();
            ItemStack remainder = stack;
            
            for (int i = 0; i < inventory.getSlots(); i++) {
                remainder = inventory.insertItem(i, remainder, false);
                if (remainder.isEmpty()) break;
            }
            
            if (remainder.getCount() < stack.getCount()) {
                if (remainder.isEmpty()) {
                    itemEntity.discard();
                } else {
                    itemEntity.setItem(remainder);
                }
                this.playSound(net.minecraft.sounds.SoundEvents.ITEM_PICKUP, 0.2F, 1.5F);
            }
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return inventoryHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inventoryHandler.invalidate();
    }
}