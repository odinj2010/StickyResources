package net.nfgbros.stickyresources.entity.custom;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;


public class FireJellyEntity extends JellyEntity {


    public FireJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        // Force the entity to appear as if it's always on fire.
        this.setSecondsOnFire(2000);  // Resets the fire ticks every tick

        // Damage from rain: if it’s raining and the sky is visible, apply custom damage.
        if (this.level().isRaining() && this.level().canSeeSky(this.blockPosition())) {
            this.hurt(this.level().damageSources().inFire(), 1.0F);
        }

        // Damage from water: check if the entity is in water.
        if (this.isInWater()) {
            this.hurt(this.level().damageSources().drown(), 4.0F);
        }

        // Avoid going underground:
        // For example, if the entity’s Y-coordinate is below a threshold (say 64), apply an upward force.
        if (this.getY() < 64) {
            // A simple way to “nudge” the entity upward
            this.setDeltaMovement(this.getDeltaMovement().x, 0.1, this.getDeltaMovement().z);
        }

        if (!this.level().isClientSide && this.tickCount % 20 == 0) {
            cookNearbyItems();
        }
    }

    private void cookNearbyItems() {
        java.util.List<net.minecraft.world.entity.item.ItemEntity> items = this.level().getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class, this.getBoundingBox().inflate(1.2D));
        for (net.minecraft.world.entity.item.ItemEntity itemEntity : items) {
            net.minecraft.world.item.ItemStack stack = itemEntity.getItem();
            net.minecraft.world.item.ItemStack cooked = getCookedVersion(stack);
            if (!cooked.isEmpty() && this.random.nextFloat() < 0.15f) { // Slightly lower chance than lava
                net.minecraft.world.item.ItemStack result = cooked.copy();
                result.setCount(stack.getCount());
                itemEntity.setItem(result);
                if (this.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                    serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SMOKE, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), 3, 0.1, 0.1, 0.1, 0);
                }
                this.level().playSound(null, this.blockPosition(), net.minecraft.sounds.SoundEvents.FIRE_EXTINGUISH, net.minecraft.sounds.SoundSource.BLOCKS, 0.3f, 1.2f);
            }
        }
    }

    private net.minecraft.world.item.ItemStack getCookedVersion(net.minecraft.world.item.ItemStack stack) {
        if (stack.is(net.minecraft.world.item.Items.BEEF)) return new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.COOKED_BEEF);
        if (stack.is(net.minecraft.world.item.Items.CHICKEN)) return new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.COOKED_CHICKEN);
        if (stack.is(net.minecraft.world.item.Items.PORKCHOP)) return new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.COOKED_PORKCHOP);
        if (stack.is(net.minecraft.world.item.Items.MUTTON)) return new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.COOKED_MUTTON);
        if (stack.is(net.minecraft.world.item.Items.RABBIT)) return new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.COOKED_RABBIT);
        if (stack.is(net.minecraft.world.item.Items.COD)) return new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.COOKED_COD);
        if (stack.is(net.minecraft.world.item.Items.SALMON)) return new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.COOKED_SALMON);
        if (stack.is(net.minecraft.world.item.Items.POTATO)) return new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.BAKED_POTATO);
        return net.minecraft.world.item.ItemStack.EMPTY;
    }
    private boolean isFireImmune() {
        return true;
    }
    }

