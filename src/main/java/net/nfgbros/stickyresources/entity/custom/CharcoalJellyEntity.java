package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;


public class CharcoalJellyEntity extends JellyEntity {

    public CharcoalJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick(); // Call super to handle base JellyEntity logic

    }
}