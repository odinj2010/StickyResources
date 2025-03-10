package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
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
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

public class JellyOakLogEntity extends JellyEntity {

    // Constants for timing and animation
    private static final int MIN_DROP_TIME = 200; // Minimum ticks before dropping an oak log
    private static final int MAX_DROP_TIME_VARIATION = 200; // Maximum random variation in drop time
    private static final int IDLE_ANIMATION_MIN_TIMEOUT = 80; // Minimum ticks for idle animation timeout
    private static final int IDLE_ANIMATION_MAX_TIMEOUT_VARIATION = 40; // Maximum random variation in idle animation timeout

    // Instance variables
    private int dropTime; // Time until the entity drops an oak log
    private final AnimationState idleAnimationState = new AnimationState(); // Handles idle animation behavior
    private int idleAnimationTimeout; // Timeout for idle animation

    // Constructor
    public JellyOakLogEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
        // Initialize drop time with a random value
        this.dropTime = this.random.nextInt(MAX_DROP_TIME_VARIATION) + MIN_DROP_TIME;
    }

    // Handles transformation into Charcoal Jelly when certain conditions are met (e.g., fire damage)
    private void transformToCharcoalJelly() {
        if (this.level() instanceof ServerLevel) {
            JellyCharCoalEntity charcoalJelly = ModEntities.JELLY_CHARCOAL.get().create((ServerLevel) this.level());
            if (charcoalJelly != null) {
                // Preserve position and orientation of the current entity
                charcoalJelly.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                charcoalJelly.finalizeSpawn((ServerLevel) this.level(), this.level().getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.CONVERSION, null, null);
                // Add the new entity and remove the current one
                this.level().addFreshEntity(charcoalJelly);
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    // Regular tick updates
    @Override
    public void tick() {
        super.tick();
        // Only update animations on the client side
        if (this.level().isClientSide()) {
            updateAnimationStates();
        }
    }

    // Updates animation states for idle behavior
    private void updateAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(IDLE_ANIMATION_MAX_TIMEOUT_VARIATION) + IDLE_ANIMATION_MIN_TIMEOUT;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    // Responsible for handling logic during each AI update step
    @Override
    public void aiStep() {
        super.aiStep();

        // Drop oak logs at regular intervals
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0) {
            // Play a sound and spawn the configured items
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(new ItemStack(ModBlocks.STICKY_OAK_LOG.get(), StickyResourcesConfig.STICKY_OAK_LOG_DROP_AMOUNT.get()));
            this.gameEvent(GameEvent.ENTITY_PLACE);
            // Reset the drop time using configured values
            this.dropTime = this.random.nextInt(200) + StickyResourcesConfig.STICKY_OAK_LOG_DROP_TIME.get();
        }
    }

    // Adjusts the walk animation based on the current pose
    @Override
    protected void updateWalkAnimation(float partialTick) {
        float animationSpeed = (this.getPose() == Pose.STANDING) ? Math.min(partialTick * 6F, 1f) : 0f;
        this.walkAnimation.update(animationSpeed, 0.2f);
    }

    // Configures AI goals for the entity
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this)); // Prevents drowning
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D)); // Enable breeding behavior
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false)); // Follow players holding slime balls
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f)); // Look at nearby players
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this)); // Randomly look around
    }

    // Defines attributes for this entity
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10D) // Defines low health
                .add(Attributes.FOLLOW_RANGE, 24D) // Moderate follow range
                .add(Attributes.MOVEMENT_SPEED, 0.25D) // Slow movement speed
                .add(Attributes.ARMOR_TOUGHNESS, 1f) // Lightweight armor
                .add(Attributes.ATTACK_KNOCKBACK, 0f) // No knockback effect
                .add(Attributes.ATTACK_DAMAGE, 1f); // Low damage
    }

    // Handles damage logic (e.g., fire damage triggers transformation)
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypeTags.IS_FIRE)) {
            transformToCharcoalJelly();
            return false; // Ignore fire damage once transformed
        }
        return super.hurt(source, amount);
    }

    // Determines whether this entity can mate with another animal
    @Override
    public boolean canMate(Animal otherAnimal) {
        return otherAnimal instanceof JellyEntity && this.isInLove() && otherAnimal.isInLove() || super.canMate(otherAnimal);
    }

    // Handles breeding logic and offspring creation
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        if (this.getType() == ModEntities.JELLY_OAK_LOG.get() && otherParent.getType() == ModEntities.JELLY.get()) {
            return ModEntities.JELLY_OAK_LOG.get().create(level);
        } else if (this.getType() == ModEntities.JELLY_OAK_LOG.get() && otherParent.getType() == ModEntities.JELLY_OAK_LOG.get()) {
            return ModEntities.JELLY_OAK_LOG.get().create(level);
        }
        return null; // No offspring for other combinations
    }

    // Determines the food item for this entity
    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SLIME_BALL); // Slime balls are the food source
    }

    // Sound effects
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SLIME_HURT; // Play this sound when hurt
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH_SMALL; // Play this sound when dying
    }

    // Serialization: Reads additional save data
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("ItemLayTime")) {
            this.dropTime = tag.getInt("ItemLayTime");
        }
    }

    // Serialization: Writes additional save data
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("ItemLayTime", this.dropTime);
    }
}