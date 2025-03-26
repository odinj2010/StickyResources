package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.StickyResourcesConfig;


public class RawIronJellyEntity extends JellyEntity {

    public RawIronJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }
}