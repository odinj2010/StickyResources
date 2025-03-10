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
import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
        // Initialize dropTime to a random value between 200 and 400 ticks
        this.dropTime = this.random.nextInt(200) + 200;
    }

    @Override
    public void tick() {
        super.tick();

        // Setup animation states on the client side
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }
    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        // Check if player is holding a sapphire hoe
        if (!this.level().isClientSide && player.getItemInHand(hand).is(ModItems.SAPPHIRE_HOE.get())) {
            // Drop the entity as an item into the world
            this.spawnAtLocation(new ItemStack(ModItems.JELLY_BONE_SPAWN_EGG.get())); // Replace with the correct registry item
            this.remove(RemovalReason.KILLED); // Remove entity from the world
            return InteractionResult.SUCCESS;
        }
        return super.interactAt(player, vec, hand);
    }

    // Sets up the idle animation state
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            // Set a new random timeout between 80 and 120 ticks
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            // Start the idle animation
            this.idleAnimationState.start(this.tickCount);
        } else {
            // Decrement the timeout
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        // Adjust walk animation based on pose
        if (this.getPose() == Pose.STANDING) {
            // If standing, set animation speed based on partial tick
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            // If not standing, no walk animation
            f = 0f;
        }

        // Update the walk animation state
        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void registerGoals() {
        // Add AI goals to the entity
        this.goalSelector.addGoal(0, new FloatGoal(this)); // Prevent drowning
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D)); // Allow breeding
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false)); // Tempt with slime balls
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f)); // Look at nearby players
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this)); // Randomly look around
    }

    // Creates attribute supplier for the entity
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2D) // Low health
                .add(Attributes.FOLLOW_RANGE, 24D) // Follow range
                .add(Attributes.MOVEMENT_SPEED, 0.25D) // Slow movement
                .add(Attributes.ARMOR_TOUGHNESS, 0f) // No armor toughness
                .add(Attributes.ATTACK_KNOCKBACK, 0f) // No attack knockback
                .add(Attributes.ATTACK_DAMAGE, 1f); // Low attack damage
    }

    @Override
    public void aiStep() {
        super.aiStep();

        // Handle item dropping logic on the server side
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.dropTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            // Use config values for slime ball drop time and amount
            this.spawnAtLocation(new ItemStack(ModItems.STICKY_BONE_MEAL.get(), StickyResourcesConfig.STICKY_BONE_MEAL_DROP_AMOUNT.get()));
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.dropTime = this.random.nextInt(200) + StickyResourcesConfig.STICKY_BONE_MEAL_DROP_TIME.get();
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
        // Entity is fed by slime balls
        return pStack.is(Items.SLIME_BALL);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        // Hurt sound
        return SoundEvents.SLIME_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        // Death sound
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