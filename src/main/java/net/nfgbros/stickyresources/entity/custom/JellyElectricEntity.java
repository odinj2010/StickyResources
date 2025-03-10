package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.*;
import net.minecraft.core.registries.Registries;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JellyElectricEntity extends JellyEntity {

    public int dropTime;
    private int shockCooldown = 0; // Add shock cooldown

    public JellyElectricEntity(EntityType<? extends JellyEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.dropTime = this.random.nextInt(200) + 200;
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }

        if (!this.level().isClientSide && shockCooldown > 0) {
            shockCooldown--;
        }

        if (!this.level().isClientSide && shockCooldown == 0) {
            shockNearbyEntities();
        }
    }
    private boolean isWearingArmor(LivingEntity entity) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR && entity.getItemBySlot(slot) != ItemStack.EMPTY) {
                return true;
            }
        }
        return false;
    }

    private void shockNearbyEntities() {
        List<LivingEntity> nearbyEntities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.0D), entity -> entity != this);

        if (!nearbyEntities.isEmpty()) {
            for (LivingEntity entity : nearbyEntities) {
                // Check if the entity is NOT an electric jelly AND is NOT wearing armor
                if (!(entity instanceof JellyElectricEntity) && !isWearingArmor(entity) && !(entity instanceof JellyCobblestoneEntity) &&
                        !(entity instanceof JellyIronEntity)) {
                    entity.hurt(this.damageSources().magic(), 0.0F + StickyResourcesConfig.ELECTRIC_JELLY_SHOCK_DAMAGE.get());
                    this.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 1.0F, 1.0F);

                    double dx = entity.getX() - this.getX();
                    double dy = entity.getY() - this.getY();
                    double dz = entity.getZ() - this.getZ();
                    this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), dx, dy, dz);
                }
            }
            shockCooldown = 20;
        }
    }

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
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<JellyCobblestoneEntity>(this, JellyCobblestoneEntity.class, 5.0F, 1.5D, 1.3D));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIME_BALL), false));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ARMOR_TOUGHNESS, 0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0f)
                .add(Attributes.ATTACK_DAMAGE, 2f);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        // Check if the entity is in water
        if (this.isInWater()) {
            this.hurt(this.damageSources().drown(), 0.0F + StickyResourcesConfig.WATER_DAMAGE_ELECTRIC_JELLY.get()); // Adjust damage value as needed
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.JELLY_ELECTRIC.get().create(pLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.SLIME_BALL);
    }

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