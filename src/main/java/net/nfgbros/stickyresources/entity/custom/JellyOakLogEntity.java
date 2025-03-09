package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.nfgbros.stickyresources.StickyResources;
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

    private int dropTime; // Time until the entity drops an oak log
    private final AnimationState idleAnimationState = new AnimationState(); // Animation state for idle behavior
    private int idleAnimationTimeout; // Timeout for idle animation

    public JellyOakLogEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
        this.dropTime = this.random.nextInt(MAX_DROP_TIME_VARIATION) + MIN_DROP_TIME; // Initialize drop time
    }

    private void transformToCharcoalJelly() {
        if (this.level() instanceof ServerLevel) {
            // Create the CharcoalJelly entity
            JellyCharCoalEntity charcoalJelly = ModEntities.JELLY_CHARCOAL.get().create((ServerLevel) this.level());
            if (charcoalJelly != null) {
                charcoalJelly.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                charcoalJelly.finalizeSpawn((ServerLevel) this.level(), this.level().getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.CONVERSION, null, null);

                // Remove the current entity and add the charcoal jelly entity
                this.level().addFreshEntity(charcoalJelly);
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            updateAnimationStates(); // Update animation states on client side
        } else {
            handleItemDrop(); // Handle item dropping logic on server side
        }
    }

    // Updates the animation states of the entity
    private void updateAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(IDLE_ANIMATION_MAX_TIMEOUT_VARIATION) + IDLE_ANIMATION_MIN_TIMEOUT;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    // Handles the logic for dropping oak logs
    private void handleItemDrop() {
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            // Use config values for slime ball drop time and amount
            this.spawnAtLocation(new ItemStack(ModBlocks.STICKY_OAK_LOG.get(), StickyResourcesConfig.STICKY_OAK_LOG_DROP_AMOUNT.get()));
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.dropTime = this.random.nextInt(200) + StickyResourcesConfig.STICKY_OAK_LOG_DROP_TIME.get();
        }
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        // Adjust the speed of the walk animation based on the entity's pose
        float animationSpeed = (this.getPose() == Pose.STANDING) ? Math.min(partialTick * 6F, 1f) : 0f;
        this.walkAnimation.update(animationSpeed, 0.2f);
    }

    @Override
    protected void registerGoals() {
        // Register the AI goals for this entity
        this.goalSelector.addGoal(0, new FloatGoal(this)); // Prevent the entity from drowning
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D)); // Allow breeding
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false)); // Tempt with slime balls
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f)); // Look at nearby players
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this)); // Randomly look around
    }

    // Creates the attribute modifiers for this entity
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10D) // Low health
                .add(Attributes.FOLLOW_RANGE, 24D) // Moderate follow range
                .add(Attributes.MOVEMENT_SPEED, 0.25D) // Slow movement speed
                .add(Attributes.ARMOR_TOUGHNESS, 1f) // No armor toughness
                .add(Attributes.ATTACK_KNOCKBACK, 0f) // No attack knockback
                .add(Attributes.ATTACK_DAMAGE, 1f); // Low attack damage
    }


    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypeTags.IS_FIRE)) {
            transformToCharcoalJelly();
            return false; // Ignore fire damage
        }
        return super.hurt(source, amount);
    }

    // Called when the entity is set on fire
    public void setOnFire(int fireTicks) {
        if (((Class<?>) this.getClass()) != JellyCharCoalEntity.class) { // Prevent infinite transformations
            JellyCharCoalEntity jellyCharCoalEntity = ModEntities.JELLY_CHARCOAL.get().create(this.level());
            if (jellyCharCoalEntity != null) {
                // Copy properties from the old entity to the new one
                jellyCharCoalEntity.copyPosition(this);
                jellyCharCoalEntity.setDeltaMovement(this.getDeltaMovement());
                jellyCharCoalEntity.setYRot(this.getYRot());
                jellyCharCoalEntity.setXRot(this.getXRot());
                jellyCharCoalEntity.setHealth(this.getHealth());

                this.level().addFreshEntity(jellyCharCoalEntity); // Add the new entity to the world
                this.level().playSound(null, this.blockPosition(), SoundEvents.GENERIC_BURN, SoundSource.NEUTRAL, 1.0f, 1.0f); // Play a burn sound
                this.discard(); // Remove the old entity
            }
        }
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        // Determines if this entity can mate with the given animal
        return otherAnimal instanceof JellyEntity && this.isInLove() && otherAnimal.isInLove() || super.canMate(otherAnimal);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        // Handles breeding logic, determining the type of offspring produced
        if (this.getType() == ModEntities.JELLY_OAK_LOG.get() && otherParent.getType() == ModEntities.JELLY.get()) {
            return ModEntities.JELLY_OAK_LOG.get().create(level); // Breeding with a regular Jelly produces another JellyOakLog
        } else if (this.getType() == ModEntities.JELLY_OAK_LOG.get() && otherParent.getType() == ModEntities.JELLY_OAK_LOG.get()) {
            return ModEntities.JELLY_OAK_LOG.get().create(level); // Breeding with another JellyOakLog produces another JellyOakLog
        }
        return null; // No offspring for other combinations
    }

    @Override
    public boolean isFood(ItemStack stack) {
        // Checks if the given item stack is considered food for this entity
        return stack.is(Items.SLIME_BALL); // Slime balls are food for this entity
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        // Returns the sound to play when this entity is hurt
        return SoundEvents.SLIME_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        // Returns the sound to play when this entity dies
        return SoundEvents.SLIME_DEATH_SMALL;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        // Reads additional data from the given NBT tag
        if (tag.contains("ItemLayTime")) {
            this.dropTime = tag.getInt("ItemLayTime"); // Read the remaining time until the next item drop
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        // Writes additional data to the given NBT tag
        tag.putInt("ItemLayTime", this.dropTime); // Save the remaining time until the next item drop
    }
}