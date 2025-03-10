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
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

public class JellyGlassEntity extends JellyEntity {

    private int dropTime; // Time until the entity drops an item
    private final AnimationState idleAnimationState = new AnimationState(); // Idle animation
    private int idleAnimationTimeout = 0; // Timeout between idle animations

    public JellyGlassEntity(EntityType<? extends JellyEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.dropTime = this.random.nextInt(200) + 200;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    // Updates the animation states for the entity
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
        float walkSpeed = (this.getPose() == Pose.STANDING) ? Math.min(pPartialTick * 6F, 1f) : 0f;
        this.walkAnimation.update(walkSpeed, 0.2f);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this)); // Allows the entity to swim in water
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D)); // Breeding behavior
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false)); // Attracts the entity to slime balls
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f)); // Looks at nearby players
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this)); // Randomly looks around
    }

    // Defines the entity's attributes
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
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0) {
            // Play sound on item drop
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

            // Drop sticky glass items using config values
            this.spawnAtLocation(new ItemStack(ModBlocks.STICKY_GLASS.get(), StickyResourcesConfig.STICKY_GLASS_DROP_AMOUNT.get()));
            this.gameEvent(GameEvent.ENTITY_PLACE);

            // Reset drop time based on config
            this.dropTime = this.random.nextInt(200) + StickyResourcesConfig.STICKY_GLASS_DROP_TIME.get();
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.JELLY_GLASS.get().create(pLevel); // Creates a JellyGlass offspring
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.SLIME_BALL); // Determines if the given item is food for this entity
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SLIME_HURT; // Sound when the entity is hurt
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH_SMALL; // Sound when the entity dies
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("ItemLayTime")) {
            this.dropTime = pCompound.getInt("ItemLayTime"); // Load dropTime from saved data
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ItemLayTime", this.dropTime); // Save dropTime to data
    }
}
