package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class JellyWaterEntity extends JellyEntity {

    public int dropTime;

    public JellyWaterEntity(EntityType<? extends JellyEntity> pEntityType, Level pLevel) {

        super(pEntityType, pLevel);
        this.dropTime = this.random.nextInt(200) + 200;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ARMOR_TOUGHNESS, 0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0f)
                .add(Attributes.ATTACK_DAMAGE, 1f);
    }

    @Override
    public void aiStep(){
        super.aiStep();
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() &&  --this.dropTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(Items.WATER_BUCKET);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.dropTime = this.random.nextInt(200) + 200;
        }
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isInWater()) {
            this.moveRelative(0.01F, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(pTravelVector);
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.TROPICAL_FISH_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.TROPICAL_FISH_DEATH;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.SLIME_BALL);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        boolean thisInWater = this.isInWater();
        boolean thisInLava = this.isInLava();
        boolean otherInWater = pOtherParent.isInWater();
        boolean otherInLava = pOtherParent.isInLava();

        if (this.getType() == ModEntities.JELLY_WATER.get() && pOtherParent.getType() == ModEntities.JELLY_WATER.get()) {
            return ModEntities.JELLY_WATER.get().create(pLevel);
        } else if (this.getType() == ModEntities.JELLY_WATER.get() && pOtherParent.getType() == ModEntities.JELLY.get() && otherInWater) {
            return ModEntities.JELLY_WATER.get().create(pLevel);
        } else if (this.getType() == ModEntities.JELLY_WATER.get() && pOtherParent.getType() == ModEntities.JELLY_LAVA.get()) {
            return ModEntities.JELLY_COBBLESTONE.get().create(pLevel);
        } else if (this.getType() == ModEntities.JELLY_WATER.get() && pOtherParent.getType() == ModEntities.JELLY_LAVA.get() && otherInLava) {
            return ModEntities.JELLY_OBSIDIAN.get().create(pLevel);
        }else {
            return null;
        }

    }
}