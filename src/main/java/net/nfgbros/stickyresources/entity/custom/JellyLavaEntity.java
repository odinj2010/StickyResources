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
import org.jetbrains.annotations.Nullable;

public class JellyLavaEntity extends JellyEntity {

    public int dropTime;
    public final AnimationState idleAnimationState = new AnimationState(); // Handle idle animation state
    private int idleAnimationTimeout = 0; // Timeout for idle animation

    // Constructor
    public JellyLavaEntity(EntityType<? extends JellyEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.dropTime = this.random.nextInt(200) + 200; // Random initial drop time
    }

    // Ticking behavior
    @Override
    public void tick() {
        super.tick();

        // Only handle client-side animations
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    // Setup idle animation states logic
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80; // Timeout for next idle animation
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout; // Decrement timeout
        }
    }

    // Updates walking animation based on the pose
    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float animationSpeed;
        if (this.getPose() == Pose.STANDING) {
            animationSpeed = Math.min(pPartialTick * 6F, 1f);
        } else {
            animationSpeed = 0f;
        }

        this.walkAnimation.update(animationSpeed, 0.2f);
    }

    // Register AI behavior goals
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this)); // Floating goal
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D)); // Breeding goal
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false)); // Follow slime balls
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f)); // Look at player
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this)); // Random look around
    }

    // Define attributes for the entity
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2D) // Health points
                .add(Attributes.FOLLOW_RANGE, 24D) // Detection range
                .add(Attributes.MOVEMENT_SPEED, 0.25D) // Movement speed
                .add(Attributes.ARMOR_TOUGHNESS, 0f) // No armor toughness
                .add(Attributes.ATTACK_KNOCKBACK, 0f) // No knockback
                .add(Attributes.ATTACK_DAMAGE, 1f); // Small attack damage
    }

    // AI behavior for stepping logic
    @Override
    public void aiStep() {
        super.aiStep();

        // Access configuration values
        int configDropTime = StickyResourcesConfig.STICKY_RAW_GOLD_DROP_TIME.get();
        int configDropAmount = StickyResourcesConfig.STICKY_RAW_GOLD_DROP_AMOUNT.get();

        // Handle dropping items logic
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(Items.LAVA_BUCKET); // Drop item (Lava Bucket)
            this.gameEvent(GameEvent.ENTITY_PLACE); // Trigger game event
            this.dropTime = this.random.nextInt(200) + 200; // Reset drop time
        }
    }

    // Breeding logic
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.JELLY_LAVA.get().create(pLevel); // Create offspring
    }

    // Check if the entity considers the item stack as food
    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.SLIME_BALL); // Slime balls are food for JellyLavaEntity
    }

    // Sound for taking damage
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SLIME_HURT; // Hurt sound
    }

    // Sound for entity's death
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH_SMALL; // Death sound
    }

    // Read entity data from NBT
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("ItemLayTime")) {
            this.dropTime = pCompound.getInt("ItemLayTime"); // Load drop time
        }
    }

    // Save entity data to NBT
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ItemLayTime", this.dropTime); // Save drop time
    }
}
