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
    }
    private boolean isFireImmune() {
        return true;
    }
    }

