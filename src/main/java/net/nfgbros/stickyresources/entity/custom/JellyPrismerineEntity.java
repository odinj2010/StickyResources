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

public class JellyPrismerineEntity extends JellyEntity {

    public int dropTime;
    public final AnimationState idleAnimationState = new AnimationState(); // Animation state for idle
    private int idleAnimationTimeout = 0; // Timeout for idle animations

    public JellyPrismerineEntity(EntityType<? extends JellyEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.dropTime = this.random.nextInt(200) + 200; // Initialize drop time
    }

    @Override
    public void tick() {
        super.tick();

        // Setup animation states on client-side
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80; // Set timeout for idle animation
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout; // Decrement the timeout
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;

        // Calculate walk animation speed based on current pose
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void registerGoals() {
        // Assign AI goals to this entity
        this.goalSelector.addGoal(0, new FloatGoal(this)); // Floating behavior
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D)); // Breeding
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false)); // Temptation behavior
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f)); // Look at player
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this)); // Random look around
    }

    public static AttributeSupplier.Builder createAttributes() {
        // Define entity attributes
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

        // Handle item drops if appropriate conditions are met
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

            // Drop sticky prismerine crystals based on configuration
            this.spawnAtLocation(new ItemStack(ModItems.STICKY_PRISMERINE_CRYSTALS.get(), StickyResourcesConfig.STICKY_PRISMERINE_CRYSTALS_DROP_AMOUNT.get()));
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.dropTime = this.random.nextInt(200) + StickyResourcesConfig.STICKY_PRISMERINE_CRYSTALS_DROP_TIME.get();
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        // Define offspring creation
        return ModEntities.JELLY_PRISMERINE.get().create(pLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        // Define valid food for the entity
        return pStack.is(Items.SLIME_BALL);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        // Sound when the entity is hurt
        return SoundEvents.SLIME_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        // Sound when the entity dies
        return SoundEvents.SLIME_DEATH_SMALL;
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        // Load drop time from saved data
        if (pCompound.contains("ItemLayTime")) {
            this.dropTime = pCompound.getInt("ItemLayTime");
        }
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        // Save drop time to compound tag
        pCompound.putInt("ItemLayTime", this.dropTime);
    }
}
