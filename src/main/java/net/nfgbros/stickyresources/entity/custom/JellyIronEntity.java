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

public class JellyIronEntity extends JellyEntity {

    // Fields
    public int dropTime; // Time until the entity drops an iron item
    public final AnimationState idleAnimationState = new AnimationState(); // Animation state for idle behavior
    private int idleAnimationTimeout = 0; // Timeout for idle animations

    // Constructor
    public JellyIronEntity(EntityType<? extends JellyEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.dropTime = this.random.nextInt(200) + 200; // Randomize initial drop time
    }

    // Tick method
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates(); // Setup animations on the client side
        }
    }

    // Setup idle animation states
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80; // Random timeout
            this.idleAnimationState.start(this.tickCount); // Start idle animation
        } else {
            --this.idleAnimationTimeout; // Decrement timeout
        }
    }

    // Update walk animation
    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f); // Animation speed when standing
        } else {
            f = 0f; // No animation if not standing
        }
        this.walkAnimation.update(f, 0.2f);
    }

    // Register entity goals
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this)); // Prevent drowning
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D)); // Allow breeding
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false)); // Tempt with slime balls
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f)); // Look at nearby players
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this)); // Randomly look around
    }

    // Define entity attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2D) // Low health
                .add(Attributes.FOLLOW_RANGE, 24D) // Follow range
                .add(Attributes.MOVEMENT_SPEED, 0.25D) // Slow movement
                .add(Attributes.ARMOR_TOUGHNESS, 0f) // No armor toughness
                .add(Attributes.ATTACK_KNOCKBACK, 0f) // No attack knockback
                .add(Attributes.ATTACK_DAMAGE, 1f); // Low attack damage
    }

    // AI step logic
    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F); // Play sound
            this.spawnAtLocation(new ItemStack(ModItems.STICKY_RAW_IRON.get(), StickyResourcesConfig.STICKY_RAW_IRON_DROP_AMOUNT.get())); // Drop item
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.dropTime = this.random.nextInt(200) + StickyResourcesConfig.STICKY_RAW_IRON_DROP_TIME.get(); // Reset drop time
        }
    }

    // Breeding behavior
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.JELLY_IRON.get().create(pLevel); // Create offspring
    }

    // Check if item is food
    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.SLIME_BALL); // Fed by slime balls
    }

    // Get hurt sound
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SLIME_HURT;
    }

    // Get death sound
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH_SMALL;
    }

    // Read additional save data
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("ItemLayTime")) {
            this.dropTime = pCompound.getInt("ItemLayTime"); // Read drop time from NBT
        }
    }

    // Write additional save data
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ItemLayTime", this.dropTime); // Save drop time to NBT
    }
}