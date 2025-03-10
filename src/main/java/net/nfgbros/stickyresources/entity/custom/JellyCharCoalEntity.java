package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
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
import net.nfgbros.stickyresources.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class JellyCharCoalEntity extends JellyEntity {

    // Field declarations
    public int dropTime; // Time until the entity drops charcoal
    public final AnimationState idleAnimationState = new AnimationState(); // Animation state for idle behavior
    private int idleAnimationTimeout = 0; // Idle animation timeout

    // Constructor
    public JellyCharCoalEntity(EntityType<? extends JellyEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.dropTime = this.random.nextInt(200) + 200; // Initialize dropTime to a random value between 200 and 400 ticks
    }

    // Core logic methods

    @Override
    public void tick() {
        super.tick();

        // Setup animation states on the client side
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            // Set a new random timeout between 80 and 120 ticks and start idle animation
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout; // Decrement the timeout
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float walkSpeed;

        // Adjust walk animation based on pose
        if (this.getPose() == Pose.STANDING) {
            walkSpeed = Math.min(pPartialTick * 6F, 1f); // If standing, set animation speed based on tick
        } else {
            walkSpeed = 0f; // No walk animation if not standing
        }

        this.walkAnimation.update(walkSpeed, 0.2f); // Update walk animation state
    }

    @Override
    protected void registerGoals() {
        // Add AI goals to the entity
        this.goalSelector.addGoal(0, new FloatGoal(this)); // Prevent drowning
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D)); // Allow breeding
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false)); // Tempt with slime balls
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f)); // Look at nearby players
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this)); // Randomly look around
    }

    public static AttributeSupplier.Builder createAttributes() {
        // Define the entity's attributes
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2D) // Low health
                .add(Attributes.FOLLOW_RANGE, 24D) // Follow range
                .add(Attributes.MOVEMENT_SPEED, 0.25D) // Slow movement
                .add(Attributes.ARMOR_TOUGHNESS, 0f) // No armor toughness
                .add(Attributes.ATTACK_KNOCKBACK, 0f) // No attack knockback
                .add(Attributes.ATTACK_DAMAGE, 1f); // Low attack damage
    }

    @Override
    public void aiStep() {
        super.aiStep();

        // Handle item-dropping logic on the server side
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(new ItemStack(ModItems.STICKY_CHARCOAL.get(), StickyResourcesConfig.STICKY_CHARCOAL_DROP_AMOUNT.get())); // Drop charcoal
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.dropTime = this.random.nextInt(200) + StickyResourcesConfig.STICKY_CHARCOAL_DROP_TIME.get(); // Reset dropTime
        }
    }

    // Breeding logic
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.JELLY_CHARCOAL.get().create(pLevel); // Create a new JellyCharCoalEntity as offspring
    }

    // Feeding behavior
    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.SLIME_BALL); // Entity is fed by slime balls
    }

    // Sound effects

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SLIME_HURT; // Hurt sound
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH_SMALL; // Death sound
    }

    // Persistence (saving/loading)

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("ItemLayTime")) {
            this.dropTime = pCompound.getInt("ItemLayTime"); // Read dropTime from NBT
        }
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ItemLayTime", this.dropTime); // Write dropTime to NBT
    }
}