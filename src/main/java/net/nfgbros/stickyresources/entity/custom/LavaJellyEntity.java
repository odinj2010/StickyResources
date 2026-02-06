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
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.ModEntities;

import java.util.List;

public class LavaJellyEntity extends JellyEntity {

    private int smeltingDropDelay = 0;
    private static final int SMELTING_DELAY = 120; // Ticks

    public LavaJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.LAVA)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void tick() {
        super.tick();
        
        if (!this.level().isClientSide) {
            checkJellyCollisions();
            if (smeltingDropDelay > 0) {
                smeltingDropDelay--;
            }

            if (this.tickCount % 20 == 0) {
                cookNearbyItems();
            }
        }
    }

    private void checkJellyCollisions() {
        if (smeltingDropDelay > 0) return;

        List<JellyEntity> nearbyJellies = this.level().getEntitiesOfClass(JellyEntity.class, this.getBoundingBox().inflate(0.5D), entity -> entity != this);
        for (JellyEntity other : nearbyJellies) {
            ItemStack result = ModEntities.getSmeltingInteraction(other.getType());
            if (!result.isEmpty() && this.level().dimension() == Level.OVERWORLD) {
                dropSmeltedItem((ServerLevel) this.level(), this.getOnPos(), result.copy());
                smeltingDropDelay = SMELTING_DELAY;
                break;
            }
        }
    }

    private void dropSmeltedItem(ServerLevel level, BlockPos pos, ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, stack);
        level.addFreshEntity(itemEntity);
    }

    private void cookNearbyItems() {
        List<ItemEntity> items = this.level().getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(1.5D));
        for (ItemEntity itemEntity : items) {
            ItemStack stack = itemEntity.getItem();
            ItemStack cooked = getCookedVersion(stack);
            if (!cooked.isEmpty() && this.random.nextFloat() < 0.2f) {
                ItemStack result = cooked.copy();
                result.setCount(stack.getCount());
                itemEntity.setItem(result);
                ((ServerLevel)this.level()).sendParticles(net.minecraft.core.particles.ParticleTypes.SMOKE, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), 5, 0.1, 0.1, 0.1, 0);
                this.level().playSound(null, this.blockPosition(), net.minecraft.sounds.SoundEvents.FIRE_EXTINGUISH, net.minecraft.sounds.SoundSource.BLOCKS, 0.5f, 1f);
            }
        }
    }

    private ItemStack getCookedVersion(ItemStack stack) {
        if (stack.is(Items.BEEF)) return new ItemStack(Items.COOKED_BEEF);
        if (stack.is(Items.CHICKEN)) return new ItemStack(Items.COOKED_CHICKEN);
        if (stack.is(Items.PORKCHOP)) return new ItemStack(Items.COOKED_PORKCHOP);
        if (stack.is(Items.MUTTON)) return new ItemStack(Items.COOKED_MUTTON);
        if (stack.is(Items.RABBIT)) return new ItemStack(Items.COOKED_RABBIT);
        if (stack.is(Items.COD)) return new ItemStack(Items.COOKED_COD);
        if (stack.is(Items.SALMON)) return new ItemStack(Items.COOKED_SALMON);
        if (stack.is(Items.POTATO)) return new ItemStack(Items.BAKED_POTATO);
        return ItemStack.EMPTY;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide && hand == InteractionHand.MAIN_HAND) {
            if (StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_ENVIRONMENTAL_GRIEFING, true)) {
                BlockPos pos = this.getOnPos();
                BlockState state = this.level().getBlockState(pos);

                if (state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.COARSE_DIRT)
                        || state.is(Blocks.SAND) || state.is(Blocks.GRAVEL)) {
                    this.level().setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
                    this.remove(RemovalReason.KILLED);
                    return InteractionResult.SUCCESS;
                } else if (state.is(Blocks.STONE) || state.is(Blocks.COBBLESTONE)) {
                    this.level().setBlockAndUpdate(pos, Blocks.MAGMA_BLOCK.defaultBlockState());
                    this.remove(RemovalReason.KILLED);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.mobInteract(player, hand);
    }
}
