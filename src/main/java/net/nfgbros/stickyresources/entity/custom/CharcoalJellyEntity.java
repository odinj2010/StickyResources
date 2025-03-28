package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;


public class CharcoalJellyEntity extends JellyEntity {

    public CharcoalJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // Prevent fire damage by checking if the damage source is fire
        if (source.is(DamageTypes.ON_FIRE)) {
            return false; // Prevent damage from fire
        }
        return super.hurt(source, amount); // Allow other damage sources to proceed normally
    }


}