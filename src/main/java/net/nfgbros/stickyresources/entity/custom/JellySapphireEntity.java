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

public class JellySapphireEntity extends JellyEntity {

    // Time for item drops
    public int dropTime;

    // Animation state for idle motion
    public final AnimationState idleAnimationState = new AnimationState();

    // Timeout for idle animation
    private int idleAnimationTimeout = 0;

    public JellySapphireEntity(EntityType<? extends JellyEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.dropTime = this.random.nextInt(200) + 200; // Initial drop time
    }

    @Override
    public void tick() {
        super.tick();

        // Setup animations only on client side
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    // Handles idle animation state
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80; // Randomize timeout
            this.idleAnimationState.start(this.tickCount); // Start animation
        } else {
            --this.idleAnimationTimeout; // Decrement timeout
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;

        // Update walk animation based on pose
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void registerGoals() {
        // Add AI goals
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    // Create and define entity attributes
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
    public void aiStep() {
        super.aiStep();

        // Handle item drop logic
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0) {
            // Play sound when dropping item
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

            // Spawn item at location (configurable amount)
            this.spawnAtLocation(new ItemStack(ModItems.STICKY_RAW_SAPPHIRE.get(), StickyResourcesConfig.STICKY_RAW_SAPPHIRE_DROP_AMOUNT.get()));

            // Trigger game event
            this.gameEvent(GameEvent.ENTITY_PLACE);

            // Reset drop time using config value
            this.dropTime = this.random.nextInt(200) + StickyResourcesConfig.STICKY_RAW_SAPPHIRE_DROP_TIME.get();
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        // Create offspring
        return ModEntities.JELLY_SAPPHIRE.get().create(pLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        // Check if the stack is slime ball (used for breeding or tempting)
        return pStack.is(Items.SLIME_BALL);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        // Sound for when entity gets hurt
        return SoundEvents.SLIME_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        // Sound for when entity dies
        return SoundEvents.SLIME_DEATH_SMALL;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        // Load saved drop time
        if (pCompound.contains("ItemLayTime")) {
            this.dropTime = pCompound.getInt("ItemLayTime");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        // Save drop time
        pCompound.putInt("ItemLayTime", this.dropTime);
    }
}
