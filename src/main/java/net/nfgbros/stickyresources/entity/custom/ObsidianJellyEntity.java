package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;


public class ObsidianJellyEntity extends JellyEntity {

    public ObsidianJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }



    private boolean isFireImmune() {
        return true;
    }
}