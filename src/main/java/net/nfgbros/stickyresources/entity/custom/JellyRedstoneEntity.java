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

public class JellyRedstoneEntity extends JellyEntity {

    public int dropTime;
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    // Constructor
    public JellyRedstoneEntity(EntityType<? extends JellyEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.dropTime = this.random.nextInt(200) + 200;
    }

    // Main tick method
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    // Set up animation states for idle behavior
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

    // Define attributes for the entity
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ARMOR_TOUGHNESS, 0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0f)
                .add(Attributes.ATTACK_DAMAGE, 1f);
    }

    // Main AI step method
    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0) {
            // Play sound and drop items at intervals
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(new ItemStack(ModItems.STICKY_REDSTONE_DUST.get(), StickyResourcesConfig.STICKY_REDSTONE_DUST_DROP_AMOUNT.get()));
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.dropTime = this.random.nextInt(200) + StickyResourcesConfig.STICKY_REDSTONE_DUST_DROP_TIME.get();
        }
    }

    // Behavior for breeding offspring
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.JELLY_REDSTONE.get().create(pLevel);
    }

    // Define what the entity considers as food
    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.SLIME_BALL);
    }

    // Sounds for hurt and death events
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

    // Save and load custom data
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("ItemLayTime")) {
            this.dropTime = pCompound.getInt("ItemLayTime");
        }
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ItemLayTime", this.dropTime);
    }
}
