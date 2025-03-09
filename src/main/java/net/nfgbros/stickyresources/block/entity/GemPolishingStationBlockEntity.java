package net.nfgbros.stickyresources.block.entity;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.nfgbros.stickyresources.screen.GemPolishingStationMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GemPolishingStationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public GemPolishingStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GEM_POLISHING_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> GemPolishingStationBlockEntity.this.progress;
                    case 1 -> GemPolishingStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }
            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> GemPolishingStationBlockEntity.this.progress = pValue;
                    case 1 -> GemPolishingStationBlockEntity.this.maxProgress = pValue;
                }
            }
            @Override
            public int getCount() {
                return 2;
            }
        };
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.sticky_resources.gem_polishing_station");
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new GemPolishingStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("gem_polishing_station.progress", progress);
        super.saveAdditional(pTag);
    }
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("gem_polishing_station.progress");
    }
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);
            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }
    private void resetProgress() {
        progress = 0;
    }
    private void craftItem() {
        ItemStack input = itemHandler.getStackInSlot(INPUT_SLOT);
        ItemStack output = ItemStack.EMPTY;

        // Define your recipes here
        if (input.getItem() == ModItems.STICKY_RAW_SAPPHIRE.get()) {
            output = new ItemStack(ModItems.RAW_SAPPHIRE.get(), 1);
        } else if (input.getItem() == ModItems.STICKY_BONE_MEAL.get()) {
            output = new ItemStack(Items.BONE_MEAL, 1);
        } else if (input.getItem() == ModItems.STICKY_COAL.get()) {
            output = new ItemStack(Items.COAL, 1);
        } else if (input.getItem() == ModItems.STICKY_CHARCOAL.get()) {
            output = new ItemStack(Items.CHARCOAL, 1);
        } else if (input.getItem() == ModItems.STICKY_RAW_COPPER.get()) {
            output = new ItemStack(Items.RAW_COPPER, 1);
        } else if (input.getItem() == ModItems.STICKY_DIAMOND.get()) {
            output = new ItemStack(Items.DIAMOND, 1);
        } else if (input.getItem() == ModItems.STICKY_EMERALD.get()) {
            output = new ItemStack(Items.EMERALD, 1);
        } else if (input.getItem() == ModItems.STICKY_ENDER_PEARL.get()) {
            output = new ItemStack(Items.ENDER_PEARL, 1);
        } else if (input.getItem() == ModItems.STICKY_RAW_GOLD.get()) {
            output = new ItemStack(Items.RAW_GOLD, 1);
        } else if (input.getItem() == ModItems.STICKY_RAW_IRON.get()) {
            output = new ItemStack(Items.RAW_IRON, 1);
        } else if (input.getItem() == ModItems.STICKY_LAPIS_LAZULI.get()) {
            output = new ItemStack(Items.LAPIS_LAZULI, 1);
        } else if (input.getItem() == ModItems.STICKY_PRISMERINE_CRYSTALS.get()) {
            output = new ItemStack(Items.PRISMARINE_CRYSTALS, 1);
        } else if (input.getItem() == ModItems.STICKY_REDSTONE_DUST.get()) {
            output = new ItemStack(Items.REDSTONE, 1);
        } else if (input.getItem() == ModItems.STICKY_WATER_BUCKET.get()) {
            output = new ItemStack(Items.WATER_BUCKET, 1);
        } else if (input.getItem() == ModItems.STICKY_LAVA_BUCKET.get()) {
            output = new ItemStack(Items.LAVA_BUCKET, 1);
        } else if (input.getItem() == ModBlocks.STICKY_COBBLESTONE.get().asItem()) {
            output = new ItemStack(Blocks.COBBLESTONE, 1);
        } else if (input.getItem() == ModBlocks.STICKY_DIRT.get().asItem()) {
            output = new ItemStack(Blocks.DIRT, 1);
        } else if (input.getItem() == ModBlocks.STICKY_GLASS.get().asItem()) {
            output = new ItemStack(Blocks.GLASS, 1);
        } else if (input.getItem() == ModBlocks.STICKY_GRAVEL.get().asItem()) {
            output = new ItemStack(Blocks.GRAVEL, 1);
        } else if (input.getItem() == ModBlocks.STICKY_OAK_LOG.get().asItem()) {
            output = new ItemStack(Blocks.OAK_LOG, 1);
        } else if (input.getItem() == ModBlocks.STICKY_OBSIDIAN.get().asItem()) {
            output = new ItemStack(Blocks.OBSIDIAN, 1);
        } else if (input.getItem() == ModBlocks.STICKY_SAND.get().asItem()) {
            output = new ItemStack(Blocks.SAND, 1);
        }

        if (!output.isEmpty()) {
            itemHandler.extractItem(INPUT_SLOT, 1, false);
            itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                    itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
        }
    }

    private boolean hasRecipe() {
        ItemStack input = itemHandler.getStackInSlot(INPUT_SLOT);

        // Check if any recipe matches the input
        if (input.getItem() == ModItems.STICKY_RAW_SAPPHIRE.get() ||
                input.getItem() == ModItems.STICKY_BONE_MEAL.get() ||
                input.getItem() == ModItems.STICKY_COAL.get() ||
                input.getItem() == ModItems.STICKY_CHARCOAL.get() ||
                input.getItem() == ModItems.STICKY_RAW_COPPER.get() ||
                input.getItem() == ModItems.STICKY_DIAMOND.get() ||
                input.getItem() == ModItems.STICKY_EMERALD.get() ||
                input.getItem() == ModItems.STICKY_ENDER_PEARL.get() ||
                input.getItem() == ModItems.STICKY_RAW_GOLD.get() ||
                input.getItem() == ModItems.STICKY_RAW_IRON.get() ||
                input.getItem() == ModItems.STICKY_LAPIS_LAZULI.get() ||
                input.getItem() == ModItems.STICKY_PRISMERINE_CRYSTALS.get() ||
                input.getItem() == ModItems.STICKY_REDSTONE_DUST.get() ||
                input.getItem() == ModItems.STICKY_WATER_BUCKET.get() ||
                input.getItem() == ModItems.STICKY_LAVA_BUCKET.get() ||
                input.getItem() == ModBlocks.STICKY_COBBLESTONE.get().asItem() ||
                input.getItem() == ModBlocks.STICKY_DIRT.get().asItem() ||
                input.getItem() == ModBlocks.STICKY_GLASS.get().asItem() ||
                input.getItem() == ModBlocks.STICKY_GRAVEL.get().asItem() ||
                input.getItem() == ModBlocks.STICKY_OAK_LOG.get().asItem() ||
                input.getItem() == ModBlocks.STICKY_OBSIDIAN.get().asItem() ||
                input.getItem() == ModBlocks.STICKY_SAND.get().asItem()) {

            // Determine the output based on the input
            ItemStack output = ItemStack.EMPTY;
            if (input.getItem() == ModItems.STICKY_RAW_SAPPHIRE.get()) output = new ItemStack(ModItems.RAW_SAPPHIRE.get());
            else if (input.getItem() == ModItems.STICKY_BONE_MEAL.get()) output = new ItemStack(Items.BONE_MEAL);
            else if (input.getItem() == ModItems.STICKY_COAL.get()) output = new ItemStack(Items.COAL);
            else if (input.getItem() == ModItems.STICKY_CHARCOAL.get()) output = new ItemStack(Items.CHARCOAL);
            else if (input.getItem() == ModItems.STICKY_RAW_COPPER.get()) output = new ItemStack(Items.RAW_COPPER);
            else if (input.getItem() == ModItems.STICKY_DIAMOND.get()) output = new ItemStack(Items.DIAMOND);
            else if (input.getItem() == ModItems.STICKY_EMERALD.get()) output = new ItemStack(Items.EMERALD);
            else if (input.getItem() == ModItems.STICKY_ENDER_PEARL.get()) output = new ItemStack(Items.ENDER_PEARL);
            else if (input.getItem() == ModItems.STICKY_RAW_GOLD.get()) output = new ItemStack(Items.RAW_GOLD);
            else if (input.getItem() == ModItems.STICKY_RAW_IRON.get()) output = new ItemStack(Items.RAW_IRON);
            else if (input.getItem() == ModItems.STICKY_LAPIS_LAZULI.get()) output = new ItemStack(Items.LAPIS_LAZULI);
            else if (input.getItem() == ModItems.STICKY_PRISMERINE_CRYSTALS.get()) output = new ItemStack(Items.PRISMARINE_CRYSTALS);
            else if (input.getItem() == ModItems.STICKY_REDSTONE_DUST.get()) output = new ItemStack(Items.REDSTONE);
            else if (input.getItem() == ModItems.STICKY_WATER_BUCKET.get()) output = new ItemStack(Items.WATER_BUCKET);
            else if (input.getItem() == ModItems.STICKY_LAVA_BUCKET.get()) output = new ItemStack(Items.LAVA_BUCKET);
            else if (input.getItem() == ModBlocks.STICKY_COBBLESTONE.get().asItem()) output = new ItemStack(Blocks.COBBLESTONE);
            else if (input.getItem() == ModBlocks.STICKY_DIRT.get().asItem()) output = new ItemStack(Blocks.DIRT);
            else if (input.getItem() == ModBlocks.STICKY_GLASS.get().asItem()) output = new ItemStack(Blocks.GLASS);
            else if (input.getItem() == ModBlocks.STICKY_GRAVEL.get().asItem()) output = new ItemStack(Blocks.GRAVEL);
            else if (input.getItem() == ModBlocks.STICKY_OAK_LOG.get().asItem()) output = new ItemStack(Blocks.OAK_LOG);
            else if (input.getItem() == ModBlocks.STICKY_OBSIDIAN.get().asItem()) output = new ItemStack(Blocks.OBSIDIAN);
            else if (input.getItem() == ModBlocks.STICKY_SAND.get().asItem()) output = new ItemStack(Blocks.SAND);

            return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output.getItem());
        }

        return false; // No matching recipe found
    }
    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }
    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }
    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }
    private void increaseCraftingProgress() {
        progress++;
    }
}