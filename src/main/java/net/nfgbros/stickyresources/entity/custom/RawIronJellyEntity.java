package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.entity.ai.goal.JellyGrazeGoal;


public class RawIronJellyEntity extends JellyEntity {

    public RawIronJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, ElectricJellyEntity.class, 5.0F, 1.5D, 1.3D));
        this.goalSelector.addGoal(3, new JellyGrazeGoal(this));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.15D, Ingredient.of(Items.SLIME_BALL), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 2.0F));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.35D));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick(); // Call super to handle base JellyEntity logic

    }
}