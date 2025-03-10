package net.nfgbros.stickyresources.entity.custom;

import net.nfgbros.stickyresources.entity.ModEntities;
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
import org.jetbrains.annotations.Nullable;

public class RhinoEntity extends Animal {
    // Animation-related fields
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    // Constructor
    public RhinoEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    // Tick method to handle animations
    @Override
    public void tick() {
        super.tick();

        // Setup animations for client side
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    // Method to manage idle animation states
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    // Updates walk animation based on pose
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

    // Register AI goals
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this)); // Priority 0: Float on water
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D)); // Priority 1: Breeding
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.COOKED_BEEF), false)); // Priority 2: Temptation with food
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D)); // Priority 3: Follow parent
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.1D)); // Priority 4: Random strolling
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f)); // Priority 5: Look at player
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this)); // Priority 6: Random look around
    }

    // Define entity attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20D) // Health
                .add(Attributes.FOLLOW_RANGE, 24D) // Follow range
                .add(Attributes.MOVEMENT_SPEED, 0.25D) // Movement speed
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f) // Armor toughness
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f) // Knockback resistance
                .add(Attributes.ATTACK_DAMAGE, 2f); // Attack damage
    }

    // Handle breeding
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.RHINO.get().create(pLevel);
    }

    // Determine if an item is food for this entity
    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.COOKED_BEEF);
    }

    // Sound methods
    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.HOGLIN_AMBIENT; // Ambient sound
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.RAVAGER_HURT; // Hurt sound
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH; // Death sound
    }
}
