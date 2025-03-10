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

public class JellyBoneEntity extends JellyEntity {

    // Time until the entity drops bone meal
    public int dropTime;

    // Animation state for idle behavior
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public JellyBoneEntity(EntityType<? extends JellyEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        // Initialize dropTime using a fixed config value plus some randomness
        this.dropTime = StickyResourcesConfig.STICKY_BONE_MEAL_DROP_TIME.get() + this.random.nextInt(100);
    }

    @Override
    public void tick() {
        super.tick();
        // Only set up animations on the client side
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    // Handles idle animations
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0 && !this.idleAnimationState.isStarted()) {
            // Set a new timeout before restarting the animation
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        // Adjust walk animation only if the entity is standing
        float f = (this.getPose() == Pose.STANDING) ? Math.min(pPartialTick * 6F, 1f) : 0f;
        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void registerGoals() {
        // Define AI goals for movement and interaction
        this.goalSelector.addGoal(0, new FloatGoal(this)); // Prevent drowning
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D)); // Allow breeding
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false)); // Follow player with slime ball
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 3f)); // Look at nearby players
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this)); // Randomly look around
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2D) // Low health
                .add(Attributes.FOLLOW_RANGE, 24D) // Follow range
                .add(Attributes.MOVEMENT_SPEED, 0.25D) // Movement speed
                .add(Attributes.ARMOR_TOUGHNESS, 0f) // No armor toughness
                .add(Attributes.ATTACK_KNOCKBACK, 0f) // No attack knockback
                .add(Attributes.ATTACK_DAMAGE, 1f); // Low attack damage
    }

    @Override
    public void aiStep() {
        super.aiStep();
        // Handle item dropping logic
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(new ItemStack(ModItems.STICKY_BONE_MEAL.get(), StickyResourcesConfig.STICKY_BONE_MEAL_DROP_AMOUNT.get()));
            this.gameEvent(GameEvent.ENTITY_PLACE);
            // Reset dropTime with a new interval
            this.dropTime = StickyResourcesConfig.STICKY_BONE_MEAL_DROP_TIME.get() + this.random.nextInt(100);
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        // Create a new JellyBoneEntity as offspring
        return ModEntities.JELLY_BONE.get().create(pLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        // Entity is attracted to slime balls
        return Ingredient.of(Items.SLIME_BALL).test(pStack);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        // Sound effect when hurt
        return SoundEvents.SLIME_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        // Sound effect when it dies
        return SoundEvents.SLIME_DEATH_SMALL;
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        // Read dropTime from NBT
        if (pCompound.contains("ItemLayTime")) {
            this.dropTime = pCompound.getInt("ItemLayTime");
        }
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        // Write dropTime to NBT
        pCompound.putInt("ItemLayTime", this.dropTime);
    }
}