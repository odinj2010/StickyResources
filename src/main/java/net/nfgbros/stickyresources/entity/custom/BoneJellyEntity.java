package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.item.ModItems;


public class BoneJellyEntity extends JellyEntity {

    public BoneJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick(); // Call super to handle base JellyEntity logic

    }
}