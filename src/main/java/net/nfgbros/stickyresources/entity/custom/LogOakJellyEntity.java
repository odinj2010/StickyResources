package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.entity.ModEntities;


public class LogOakJellyEntity extends JellyEntity {

    public LogOakJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // Check if the damage source is fire or lava
        if (source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.LAVA)) {
            if (!this.isRemoved()) { // Check if the entity is not already removed
                transformIntoCharcoalJelly(); // Transform the entity into charcoal Jelly
            }
            return false; // Prevent the entity from taking damage or dying
        }
        return super.hurt(source, amount); // Allow other damage sources to proceed normally
    }
    private void transformIntoCharcoalJelly() {
        if (!level().isClientSide) {
            this.discard();  // Remove the current Jelly entity
            JellyEntity charcoalJelly = ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.CHARCOAL).get().create((ServerLevel) this.level());
            if (charcoalJelly != null) {
                charcoalJelly.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                this.level().addFreshEntity(charcoalJelly);  // Spawn in the new Glass Jelly
            }
        }
    }
}