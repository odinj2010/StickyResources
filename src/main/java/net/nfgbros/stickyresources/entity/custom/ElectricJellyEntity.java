package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;


public class ElectricJellyEntity extends JellyEntity {

    public ElectricJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick(); // Call super to handle base JellyEntity logic
        if (!level().isClientSide) {
            level().addParticle(ParticleTypes.ELECTRIC_SPARK, getX() + random.nextDouble() - 0.5, getY() + random.nextDouble() - 0.5, getZ() + random.nextDouble() - 0.5, 0, 0, 0);
        }

    }
}