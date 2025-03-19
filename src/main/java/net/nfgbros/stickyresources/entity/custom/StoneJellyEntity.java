package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.entity.ai.goal.JellyGrazeGoal;


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