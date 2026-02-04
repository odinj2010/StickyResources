package net.nfgbros.stickyresources.entity;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class JellyDropManagement {
    private final JellyEntity jelly;

    public JellyDropManagement(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void dropJellyItem() {
        Level world = jelly.level();
        ModEntities.JellyType type = jelly.getJellyType();
        ItemStack dropStack = new ItemStack(type.getDropItem());

        if (!dropStack.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(world, jelly.getX(), jelly.getY(), jelly.getZ(), dropStack);
            world.addFreshEntity(itemEntity);
        }
    }
}