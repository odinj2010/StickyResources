package net.nfgbros.stickyresources.entity.ai.jelly.goals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;
import java.util.List;

public class JellyForageGoal extends Goal {
    private final JellyEntity jelly;
    private final double speed;
    private ItemEntity targetItem;
    private int scanTick = 0;

    public JellyForageGoal(JellyEntity jelly, double speed) {
        this.jelly = jelly;
        this.speed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (scanTick > 0) {
            scanTick--;
            return false;
        }
        scanTick = 20;

        if (this.jelly.isBaby()) return false;

        List<ItemEntity> list = this.jelly.level().getEntitiesOfClass(ItemEntity.class, this.jelly.getBoundingBox().inflate(10.0D, 3.0D, 10.0D));
        if (list.isEmpty()) return false;

        ModEntities.JellyType type = this.jelly.getJellyType();
        for (ItemEntity itemEntity : list) {
            ItemStack stack = itemEntity.getItem();
            if (type.isPreferredFood(stack) || type.isLoveFood(stack) || type.getTransformation(stack.getItem()).isPresent()) {
                this.targetItem = itemEntity;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (targetItem == null || !targetItem.isAlive()) return false;
        return this.jelly.distanceToSqr(targetItem) < 100.0D;
    }

    @Override
    public void start() {
        if (targetItem != null) {
            this.jelly.getNavigation().moveTo(this.targetItem, this.speed);
        }
    }

    @Override
    public void tick() {
        if (targetItem != null && this.jelly.distanceToSqr(targetItem) < 2.0D) {
            // The actual consumption logic is still in JellyFeedingAI.tick() 
            // which runs every 20 ticks in JellyCustomAI.
            // This goal just gets them there.
            this.jelly.getNavigation().stop();
        }
    }

    @Override
    public void stop() {
        this.targetItem = null;
    }
}
