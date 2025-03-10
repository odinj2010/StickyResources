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

public class JellyEnderPearlEntity extends JellyEntity {

    // Time until the entity drops its item
    public int dropTime;

    // Idle animation state
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    // Constructor
    public JellyEnderPearlEntity(EntityType<? extends JellyEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        // Randomize the initial drop time
        this.dropTime = this.random.nextInt(200) + 200;
    }

    // Tick method
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    // Setup idle animation states
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    // Update walk animation
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

    // Register entity goals
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    // Define entity attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ARMOR_TOUGHNESS, 0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0f)
                .add(Attributes.ATTACK_DAMAGE, 1f);
    }

    // AI step method for handling behaviors
    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            // Drop item using config-defined values
            this.spawnAtLocation(new ItemStack(ModItems.STICKY_ENDER_PEARL.get(), StickyResourcesConfig.STICKY_ENDER_PEARL_DROP_AMOUNT.get()));
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.dropTime = this.random.nextInt(200) + StickyResourcesConfig.STICKY_ENDER_PEARL_DROP_TIME.get();
        }
    }

    // Define offspring for breeding
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.JELLY_ENDER_PEARL.get().create(pLevel);
    }

    // Check if an item is food for the entity
    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.SLIME_BALL);
    }

    // Sounds
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SLIME_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH_SMALL;
    }

    // Read entity data from NBT
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("ItemLayTime")) {
            this.dropTime = pCompound.getInt("ItemLayTime");
        }
    }

    // Save entity data to NBT
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ItemLayTime", this.dropTime);
    }
}
