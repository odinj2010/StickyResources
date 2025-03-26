package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.entity.ModEntities;


public class SandJellyEntity extends JellyEntity {

    public SandJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // Check if the damage source is fire or lava
        if (source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.LAVA)) {
            if (!this.isRemoved()) { // Check if the entity is not already removed
                transformIntoGlassJelly(); // Transform the entity into Glass Jelly
            }
            return false; // Prevent the entity from taking damage or dying
        }
        return super.hurt(source, amount); // Allow other damage sources to proceed normally
    }

    private void transformIntoGlassJelly() {
        if (!level().isClientSide) {
            this.discard();  // Remove the current Jelly entity
            JellyEntity glassJelly = ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.GLASS).get().create((ServerLevel) this.level());
            if (glassJelly != null) {
                glassJelly.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                this.level().addFreshEntity(glassJelly);  // Spawn in the new Glass Jelly
            }
        }
    }
}