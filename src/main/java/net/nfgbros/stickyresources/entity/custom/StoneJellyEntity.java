package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;


public class StoneJellyEntity extends JellyEntity {

    public StoneJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }


    @Override
    public void tick() {
        super.tick(); // Call super to handle base JellyEntity logic

    }

    private boolean isFireImmune() {
        return true;
    }
}