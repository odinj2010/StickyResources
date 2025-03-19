package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.entity.ModEntities;


public class CobblestoneJellyEntity extends JellyEntity {

    public CobblestoneJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.1D, Ingredient.of(Items.SLIME_BALL), false));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new FollowParentGoal(this, 1.1D));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // Check if the damage source is fire or lava
        if (source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.LAVA)) {
            if (!this.isRemoved()) { // Check if the entity is not already removed
                transformIntoStoneJelly(); // Transform the entity into "XXXX" Jelly
            }
            return false; // Prevent the entity from taking damage or dying
        }
        return super.hurt(source, amount); // Allow other damage sources to proceed normally
    }

    @Override
    public void tick() {
        super.tick(); // Call super to handle base JellyEntity logic

    }

    private void transformIntoStoneJelly() {
        if (!level().isClientSide) {
            this.discard();  // Remove the current Jelly entity
            JellyEntity stoneJelly = ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.STONE).get().create((ServerLevel) this.level());
            if (stoneJelly != null) {
                stoneJelly.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                this.level().addFreshEntity(stoneJelly);  // Spawn in the new Jelly
            }
        }
    }
}