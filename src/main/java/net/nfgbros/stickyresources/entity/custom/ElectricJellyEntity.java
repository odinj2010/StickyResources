package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.damagesource.DamageSource; // Import DamageSource
import net.nfgbros.stickyresources.entity.ai.goal.JellyGrazeGoal;

import java.util.List;

public class ElectricJellyEntity extends JellyEntity {

    private int tickCount;

    public ElectricJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
        this.tickCount = 0;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, RawIronJellyEntity.class, 5.0F, 1.5D, 1.3D));
        this.goalSelector.addGoal(3, new JellyGrazeGoal(this));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.15D, Ingredient.of(Items.SLIME_BALL), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 2.0F));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.35D));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
            level().addParticle(ParticleTypes.ELECTRIC_SPARK, getX() + random.nextDouble() - 0.5, getY() + random.nextDouble() - 0.5, getZ() + random.nextDouble() - 0.5, 0, 0, 0);

            tickCount++;
            if (tickCount >= 200) {
                tickCount = 0;
                applyShockDamage();
            }
        }
    }

    private void applyShockDamage() {
        //original range was 6
        double range = 1;
        AABB aabb = new AABB(
                this.getX() - range, this.getY() - range, this.getZ() - range,
                this.getX() + range, this.getY() + range, this.getZ() - range
        );
        List<LivingEntity> nearbyEntities = this.level().getEntitiesOfClass(LivingEntity.class, aabb,
                entity -> entity != this && !(entity instanceof ElectricJellyEntity) &&
                        !isWearingArmor(entity)); // Added armor check

        for (LivingEntity entity : nearbyEntities) {
            entity.hurt(this.damageSources().generic(), 5);
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0));
            this.level().playSound(null, entity.blockPosition(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.AMBIENT, 0.5F, 1.0F); // Play sound
        }
    }

    private boolean isWearingArmor(LivingEntity entity) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (!stack.isEmpty() && stack.getItem().getEquipmentSlot(stack) == slot && stack.getItem().canElytraFly(stack, entity)) {
                return true;
            }
        }
        return false;
    }
}
