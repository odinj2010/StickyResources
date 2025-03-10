package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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

import static net.nfgbros.stickyresources.entity.ModEntities.JELLY_LAVA;

public class JellySandEntity extends JellyEntity {

    // Time until the next item drop
    public int dropTime;

    // Animation state and timeout for idle animations
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    // Constructor
    public JellySandEntity(EntityType<? extends JellyEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.dropTime = this.random.nextInt(200) + 200;
    }

    @Override
    public void tick() {
        super.tick();

        // Client-side only: update animation states
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    // Setup idle animation states with randomized delay
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
        float factor;
        // Update walk animation only when standing
        if (this.getPose() == Pose.STANDING) {
            factor = Math.min(pPartialTick * 6F, 1f);
        } else {
            factor = 0f;
        }

        this.walkAnimation.update(factor, 0.2f);
    }

    // Register goals for AI behaviors
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this)); // Floating in liquid
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D)); // Breeding
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false)); // Tempt with slime balls
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f)); // Look at nearby players
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this)); // Look around randomly
    }

    // Define attributes for the entity
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2D) // Low health
                .add(Attributes.FOLLOW_RANGE, 24D) // Detection range for following
                .add(Attributes.MOVEMENT_SPEED, 0.25D) // Movement speed
                .add(Attributes.ARMOR_TOUGHNESS, 0f) // No armor toughness
                .add(Attributes.ATTACK_KNOCKBACK, 0f) // No knockback on attack
                .add(Attributes.ATTACK_DAMAGE, 1f); // Minimal attack damage
    }

    @Override
    public void aiStep() {
        super.aiStep();

        // Handle item drops on the ground
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0) {
            // Play egg-laying sound effect
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

            // Spawn sticky sand item based on configuration
            this.spawnAtLocation(new ItemStack(ModBlocks.STICKY_SAND.get(), StickyResourcesConfig.STICKY_SAND_DROP_AMOUNT.get()));
            this.gameEvent(GameEvent.ENTITY_PLACE);

            // Reset drop time with randomized delay
            this.dropTime = this.random.nextInt(200) + StickyResourcesConfig.STICKY_SAND_DROP_TIME.get();
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        // Determines offspring type based on parent's environment
        if (pOtherParent.isInWater()) {
            return JELLY_LAVA.get().create(pLevel); // Produces Jelly Lava entity in water
        }
        return ModEntities.JELLY_SAND.get().create(pLevel); // Produces another Jelly Sand entity
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.SLIME_BALL); // Slime balls are food for this entity
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SLIME_HURT; // Hurt sound effect
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH_SMALL; // Death sound effect
    }

    // Save additional data for this entity
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("ItemLayTime")) {
            this.dropTime = pCompound.getInt("ItemLayTime"); // Load drop time
        }
    }

    // Write additional data for this entity
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ItemLayTime", this.dropTime); // Save drop time
    }
}
