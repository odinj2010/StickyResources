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
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class JellyWaterEntity extends JellyEntity {

    public int dropTime;

    public JellyWaterEntity(EntityType<? extends JellyEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.dropTime = this.random.nextInt(200) + 200; // Initialize drop time randomly between 200-400
    }

    @Override
    protected void registerGoals() {
        // Register behaviors for the entity's AI, ordered by priority
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this)); // Highest priority: find water
        this.goalSelector.addGoal(1, new FloatGoal(this)); // Floating behavior in water
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.15D)); // Breeding behavior
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0D, 10)); // Random swimming activity
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false)); // Temptation with slime balls
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f)); // Look at nearby players
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this)); // Random head movement
    }

    public static AttributeSupplier.Builder createAttributes() {
        // Define entity's attributes such as health, movement speed, etc.
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2D) // Maximum health
                .add(Attributes.FOLLOW_RANGE, 24D) // Range to detect other entities
                .add(Attributes.MOVEMENT_SPEED, 0.25D) // Base movement speed
                .add(Attributes.ARMOR_TOUGHNESS, 0f) // Armor toughness (unused here)
                .add(Attributes.ATTACK_KNOCKBACK, 0f) // Resistance to being knocked back
                .add(Attributes.ATTACK_DAMAGE, 1f); // Base attack damage
    }

    @Override
    public void aiStep() {
        // Called every tick to handle entity updates
        super.aiStep();

        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0) {
            // Play sound and spawn a water bucket when drop timer reaches zero
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F,
                    (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(Items.WATER_BUCKET);
            this.gameEvent(GameEvent.ENTITY_PLACE);

            // Reset drop timer to a random value between 200-400
            this.dropTime = this.random.nextInt(200) + 200;
        }
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        // Customize movement behavior depending on whether the entity is in water
        if (this.isInWater()) {
            this.moveRelative(0.01F, pTravelVector); // Move relative to travel vector
            this.move(MoverType.SELF, this.getDeltaMovement()); // Update position
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D)); // Reduce movement speed in water
            if (this.getTarget() == null) {
                // Slight downward movement if no target present
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(pTravelVector); // Default movement behavior
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        // Sound played when entity gets hurt
        return SoundEvents.TROPICAL_FISH_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        // Sound played when entity dies
        return SoundEvents.TROPICAL_FISH_DEATH;
    }

    @Override
    public boolean isPushedByFluid() {
        // Prevent the entity from being pushed by fluids
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        // Allow the entity to breathe underwater
        return true;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        // Specify that the entity can be fed slime balls
        return pStack.is(Items.SLIME_BALL);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        // Determine offspring type based on parent types and conditions
        boolean thisInWater = this.isInWater();
        boolean thisInLava = this.isInLava();
        boolean otherInWater = pOtherParent.isInWater();
        boolean otherInLava = pOtherParent.isInLava();

        if (this.getType() == ModEntities.JELLY_WATER.get() && pOtherParent.getType() == ModEntities.JELLY_WATER.get()) {
            // Both parents are water-based
            return ModEntities.JELLY_WATER.get().create(pLevel);
        } else if (this.getType() == ModEntities.JELLY_WATER.get() && pOtherParent.getType() == ModEntities.JELLY.get() && otherInWater) {
            // Water jelly and regular jelly (in water)
            return ModEntities.JELLY_WATER.get().create(pLevel);
        } else if (this.getType() == ModEntities.JELLY_WATER.get() && pOtherParent.getType() == ModEntities.JELLY_LAVA.get()) {
            // Water jelly and lava jelly
            return ModEntities.JELLY_COBBLESTONE.get().create(pLevel);
        } else if (this.getType() == ModEntities.JELLY_WATER.get() && pOtherParent.getType() == ModEntities.JELLY_LAVA.get() && otherInLava) {
            // Water jelly and lava jelly (in lava)
            return ModEntities.JELLY_OBSIDIAN.get().create(pLevel);
        } else {
            // No valid breeding condition
            return null;
        }
    }
}