package net.nfgbros.stickyresources.block.entity;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.BlockItem;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.nfgbros.stickyresources.screen.GemPolishingStationMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Map;

public class GemPolishingStationBlockEntity extends BlockEntity implements MenuProvider {

    // Inventory handler for 2 slots: input and output
    private final ItemStackHandler itemHandler = new ItemStackHandler(2);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    // Tracking crafting progress
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    // Recipe map linking sticky items to their final outputs
    private static final Map<Item, Item> RECIPES = Map.ofEntries(
            Map.entry(ModItems.STICKY_RAW_SAPPHIRE.get(), ModItems.RAW_SAPPHIRE.get()),
            Map.entry(ModItems.STICKY_BONE_MEAL.get(), Items.BONE_MEAL),
            Map.entry(ModItems.STICKY_COAL.get(), Items.COAL),
            Map.entry(ModItems.STICKY_CHARCOAL.get(), Items.CHARCOAL),
            Map.entry(ModItems.STICKY_RAW_COPPER.get(), Items.RAW_COPPER),
            Map.entry(ModItems.STICKY_DIAMOND.get(), Items.DIAMOND),
            Map.entry(ModItems.STICKY_EMERALD.get(), Items.EMERALD),
            Map.entry(ModItems.STICKY_ENDER_PEARL.get(), Items.ENDER_PEARL),
            Map.entry(ModItems.STICKY_RAW_GOLD.get(), Items.RAW_GOLD),
            Map.entry(ModItems.STICKY_RAW_IRON.get(), Items.RAW_IRON),
            Map.entry(ModItems.STICKY_LAPIS_LAZULI.get(), Items.LAPIS_LAZULI),
            Map.entry(ModItems.STICKY_PRISMERINE_CRYSTALS.get(), Items.PRISMARINE_CRYSTALS),
            Map.entry(ModItems.STICKY_REDSTONE_DUST.get(), Items.REDSTONE),
            Map.entry(ModItems.STICKY_WATER_BUCKET.get(), Items.WATER_BUCKET),
            Map.entry(ModItems.STICKY_LAVA_BUCKET.get(), Items.LAVA_BUCKET),
            Map.entry(ModBlocks.STICKY_COBBLESTONE.get().asItem(), ((BlockItem) Blocks.COBBLESTONE.asItem())),
            Map.entry(ModBlocks.STICKY_DIRT.get().asItem(), ((BlockItem) Blocks.DIRT.asItem())),
            Map.entry(ModBlocks.STICKY_GLASS.get().asItem(), ((BlockItem) Blocks.GLASS.asItem())),
            Map.entry(ModBlocks.STICKY_GRAVEL.get().asItem(), ((BlockItem) Blocks.GRAVEL.asItem())),
            Map.entry(ModBlocks.STICKY_OAK_LOG.get().asItem(), ((BlockItem) Blocks.OAK_LOG.asItem())),
            Map.entry(ModBlocks.STICKY_OBSIDIAN.get().asItem(), ((BlockItem) Blocks.OBSIDIAN.asItem())),
            Map.entry(ModBlocks.STICKY_SAND.get().asItem(), ((BlockItem) Blocks.SAND.asItem()))
    );

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

    // Crafts an item from the defined recipes
    private void craftItem() {
        ItemStack input = itemHandler.getStackInSlot(INPUT_SLOT);
        Item output = RECIPES.get(input.getItem());

        if (output != null) {
            itemHandler.extractItem(INPUT_SLOT, 1, false);
            itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output,
                    itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + 1));
        }
    }

    // Checks if there is a valid recipe for the current input
    private boolean hasRecipe() {
        return RECIPES.containsKey(itemHandler.getStackInSlot(INPUT_SLOT).getItem());
    }

    // Drops contents of inventory when the block is broken
    public void dropContents(Level pLevel, BlockPos pPos) {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), itemHandler.getStackInSlot(i));
        }
    }

    // Handles crafting logic on tick
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (!level.isClientSide) {
            if (this.hasRecipe()) {
                if (this.progress < this.maxProgress) {
                    this.progress++;
                } else {
                    this.craftItem();
                    this.progress = 0;
                }
            } else {
                this.progress = 0; // Reset progress if no valid recipe
            }
        }
    }

    // Save and load inventory data
    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        progress = tag.getInt("Progress");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.putInt("Progress", progress);
    }

    // Initializes item handler on load
    @Override
    public void onLoad() {
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        lazyItemHandler.invalidate();
    }

    // Display name for the block UI
    @Override
    public Component getDisplayName() {
        return Component.translatable("container.sticky_resources.gem_polishing_station");
    }

    // Creates the container menu for this block entity
    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new GemPolishingStationMenu(id, inventory, this, this.data);
    }
}
