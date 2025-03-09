package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

public class JellyEntity extends Animal implements net.nfgbros.stickyresources.entity.custom.LookableEntity {

    public int dropTime;
    private float lookingYRot;

    public JellyEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.dropTime = this.random.nextInt(200) + 200;
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            // Calculate the angle towards the nearest player
            Player nearestPlayer = this.level().getNearestPlayer(this, 24.0D);
            if (nearestPlayer != null) {
                double dx = nearestPlayer.getX() - this.getX();
                double dz = nearestPlayer.getZ() - this.getZ();
                float targetYRot = (float) (Math.atan2(dz, dx) * (180F / Math.PI)) - 90F;

                // Calculate the relative rotation needed
                float deltaYRot = targetYRot - this.lookingYRot;

                // Ensure deltaYRot is within -180 to 180 degrees
                deltaYRot = (deltaYRot + 540) % 360 - 180;

                // Update lookingYRot with the relative rotation
                this.lookingYRot += deltaYRot * 0.15F; // Adjust the multiplier for smoother turning
            }
        } else {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false));

        // Modified LookAtPlayerGoal with null check
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f) {
            @Override
            public boolean canUse() {
                // Check if a player to look at has been found
                if (this.lookAt != null) {
                    return this.lookAt.isAlive();
                }
                return false; // Skip if no player is found
            }
        });
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
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(1.0f, 0.5f); // Adjust the 0.5f for desired height
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0 && this.getType() == ModEntities.JELLY.get()) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(Items.SLIME_BALL);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.dropTime = this.random.nextInt(200) + StickyResourcesConfig.ITEM_DROP_TIME.get();
        }
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        if (otherAnimal instanceof JellyOakLogEntity && this.isInLove() && otherAnimal.isInLove()) {
            return true;
        }
        return super.canMate(otherAnimal);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.SLIME_BALL);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        boolean thisInWater = this.isInWater();
        boolean otherInWater = pOtherParent.isInWater();

        if (this.getType() == ModEntities.JELLY.get() && pOtherParent.getType() == ModEntities.JELLY.get()) {
            return ModEntities.JELLY.get().create(pLevel);
        } else if (this.getType() == ModEntities.JELLY.get() && pOtherParent.getType() == ModEntities.JELLY.get() && thisInWater && otherInWater) {
            return ModEntities.JELLY_WATER.get().create(pLevel);
        } else if (this.getType() == ModEntities.JELLY.get() && pOtherParent.getType() == ModEntities.JELLY_WATER.get() && thisInWater){
            return ModEntities.JELLY_WATER.get().create(pLevel);
        }else if (this.getType() == ModEntities.JELLY.get() && pOtherParent.getType() == ModEntities.JELLY_OAK_LOG.get()) {
            return ModEntities.JELLY_OAK_LOG.get().create(pLevel);
        }else {
            return ModEntities.JELLY.get().create(pLevel);
        }
    }

    @Override
    public float getLookingYRot() {
        return lookingYRot;
    }
}