package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;

public class LavaJellyEntity extends JellyEntity {

    private int cobblestoneDropDelay = 0;
    private static final int COBBLESTONE_DELAY = 120; // Ticks
    private int glassDropDelay = 0;
    private static final int GLASS_DELAY = 120; // Ticks
    private int copperIngotDropDelay = 0;
    private static final int COPPERINGOT_DELAY = 120; // Ticks
    private int goldIngotDropDelay = 0;
    private static final int GOLDINGOT_DELAY = 120; // Ticks
    private int ironIngotDropDelay = 0;
    private static final int IRONINGOT_DELAY = 120; // Ticks

    public LavaJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }
    @Override
    public boolean hurt(DamageSource source, float amount) {
        // Check if the damage source is fire or lava
        if (source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.LAVA)) {
            return false; // Prevent the entity from taking damage or dying
        }
        return super.hurt(source, amount); // Allow other damage sources to proceed normally
    }
    @Override
    public void tick() {
        super.tick();
        checkWaterJellyCollision();
        if (cobblestoneDropDelay > 0) {
            cobblestoneDropDelay--;
        }
        checkSandJellyCollision();
        if (glassDropDelay > 0) {
            glassDropDelay--;
        }
        checkRawCopperJellyCollision();
        if (copperIngotDropDelay > 0) {
            copperIngotDropDelay--;
        }
        checkRawGoldJellyCollision();
        if (goldIngotDropDelay > 0) {
            goldIngotDropDelay--;
        }
        checkRawIronJellyCollision();
        if (ironIngotDropDelay > 0) {
            ironIngotDropDelay--;
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide && hand == InteractionHand.MAIN_HAND) {
            BlockPos pos = this.getOnPos();
            BlockState state = this.level().getBlockState(pos);

            if (state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.COARSE_DIRT)
                    || state.is(Blocks.SAND) || state.is(Blocks.GRAVEL)) {
                this.level().setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
                this.remove(RemovalReason.KILLED); // Remove the entity
                return InteractionResult.SUCCESS;
            } else if (state.is(Blocks.STONE) || state.is(Blocks.COBBLESTONE)) {
                this.level().setBlockAndUpdate(pos, Blocks.MAGMA_BLOCK.defaultBlockState());
                this.remove(RemovalReason.KILLED); // Remove the entity
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }
    private void checkWaterJellyCollision() {
        if (!this.level().isClientSide) {
            for (Entity entity : this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(0.5D))) {
                if (entity instanceof WaterJellyEntity) {
                    if (cobblestoneDropDelay == 0 && this.level().dimension() == Level.OVERWORLD) {
                        dropCobblestoneItem((ServerLevel) this.level(), this.getOnPos());
                        cobblestoneDropDelay = COBBLESTONE_DELAY;
                    }
                    return;
                }
            }
        }
    }
    private void checkSandJellyCollision() {
        if (!this.level().isClientSide) {
            for (Entity entity : this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(0.5D))) {
                if (entity instanceof SandJellyEntity) {
                    if (glassDropDelay == 0 && this.level().dimension() == Level.OVERWORLD) {
                        dropGlassItem((ServerLevel) this.level(), this.getOnPos());
                        glassDropDelay = GLASS_DELAY;
                    }
                    return;
                }
            }
        }
    }
    private void checkRawIronJellyCollision() {
        if (!this.level().isClientSide) {
            for (Entity entity : this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(0.5D))) {
                if (entity instanceof RawIronJellyEntity) {
                    if (ironIngotDropDelay == 0 && this.level().dimension() == Level.OVERWORLD) {
                        dropironIngotItem((ServerLevel) this.level(), this.getOnPos());
                        ironIngotDropDelay = IRONINGOT_DELAY;
                    }
                    return;
                }
            }
        }
    }
    private void checkRawCopperJellyCollision() {
        if (!this.level().isClientSide) {
            for (Entity entity : this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(0.5D))) {
                if (entity instanceof RawCopperJellyEntity) {
                    if (copperIngotDropDelay == 0 && this.level().dimension() == Level.OVERWORLD) {
                        dropcopperIngotItem((ServerLevel) this.level(), this.getOnPos());
                        copperIngotDropDelay = COPPERINGOT_DELAY;
                    }
                    return;
                }
            }
        }
    }
    private void checkRawGoldJellyCollision() {
        if (!this.level().isClientSide) {
            for (Entity entity : this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(0.5D))) {
                if (entity instanceof RawGoldJellyEntity) {
                    if (goldIngotDropDelay == 0 && this.level().dimension() == Level.OVERWORLD) {
                        dropgoldIngotItem((ServerLevel) this.level(), this.getOnPos());
                        goldIngotDropDelay = GOLDINGOT_DELAY;
                    }
                    return;
                }
            }
        }
    }

    private void dropCobblestoneItem(ServerLevel level, BlockPos pos) {
        ItemStack cobblestone = new ItemStack(Blocks.COBBLESTONE);
        ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, cobblestone);
        level.addFreshEntity(itemEntity);
    }
    private void dropGlassItem(ServerLevel level, BlockPos pos) {
        ItemStack glass = new ItemStack(Blocks.GLASS_PANE);
        ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, glass);
        level.addFreshEntity(itemEntity);
    }
    private void dropironIngotItem(ServerLevel level, BlockPos pos) {
        ItemStack ironIngot = new ItemStack(Items.IRON_INGOT);
        ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, ironIngot);
        level.addFreshEntity(itemEntity);
    }
    private void dropcopperIngotItem(ServerLevel level, BlockPos pos) {
        ItemStack copperIngot = new ItemStack(Items.COPPER_INGOT);
        ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, copperIngot);
        level.addFreshEntity(itemEntity);
    }
    private void dropgoldIngotItem(ServerLevel level, BlockPos pos) {
        ItemStack goldIngot = new ItemStack(Items.GOLD_INGOT);
        ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, goldIngot);
        level.addFreshEntity(itemEntity);
    }


    private boolean isFireImmune() {
        return true;
    }
}