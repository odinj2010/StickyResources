package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.util.Magnetism;

public class MagnetJellyEntity extends JellyEntity {
    private final Magnetism magnetism;

    public MagnetJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
        this.magnetism = new Magnetism(this);
        this.xpReward = 5;
    }

    @Override
    public void tick() {
        super.tick();
        magnetism.tick();
    }
}
