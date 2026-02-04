package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ElectricJellyEntity extends JellyEntity {

    public ElectricJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            if (this.random.nextInt(10) == 0) {
                this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                        this.getX() + (this.random.nextDouble() - 0.5D) * (double)this.getBbWidth(),
                        this.getY() + this.random.nextDouble() * (double)this.getBbHeight(),
                        this.getZ() + (this.random.nextDouble() - 0.5D) * (double)this.getBbWidth(),
                        0.0D, 0.0D, 0.0D);
            }
        } else {
            // Discharge if wet
            if (this.isInWaterOrRain()) {
                discharge(5.0D);
            }
            
            // Charge nearby creepers
            chargeNearbyCreepers();
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!this.level().isClientSide) {
            discharge(3.0D);
        }
        return super.hurt(pSource, pAmount);
    }

    private void chargeNearbyCreepers() {
        if (this.tickCount % 20 != 0) return; // Run once a second

        List<Creeper> creepers = this.level().getEntitiesOfClass(Creeper.class, this.getBoundingBox().inflate(2.0D));
        for (Creeper creeper : creepers) {
            if (!creeper.isPowered() && this.level() instanceof ServerLevel serverLevel) {
                net.minecraft.world.entity.LightningBolt lightning = net.minecraft.world.entity.EntityType.LIGHTNING_BOLT.create(serverLevel);
                if (lightning != null) {
                    lightning.moveTo(creeper.position());
                    creeper.thunderHit(serverLevel, lightning);
                    this.playSound(SoundEvents.TRIDENT_THUNDER, 1.0F, 1.0F);
                }
            }
        }
    }

    private void discharge(double range) {
        List<LivingEntity> nearbyEntities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(range),
                entity -> entity != this && !(entity instanceof ElectricJellyEntity));

        if (!nearbyEntities.isEmpty()) {
             this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.NEUTRAL, 0.5F, 2.0F);
        }

        for (LivingEntity entity : nearbyEntities) {
            float damage = 4.0F;

            // Bonus damage if target is wet or wearing metal armor
            if (entity.isInWaterOrRain() || hasMetalArmor(entity)) {
                damage *= 2.0F;
                this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(), 0, 0, 0);
            }

            if (entity.hurt(this.damageSources().lightningBolt(), damage)) {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
            }
        }
    }

    private boolean hasMetalArmor(LivingEntity entity) {
        for (ItemStack stack : entity.getArmorSlots()) {
            // Simple check for iron, gold, or chainmail in the item name/tags
            // Ideally use tags, but this covers vanilla logic quickly
            String name = stack.getDescriptionId();
            if (name.contains("iron") || name.contains("gold") || name.contains("chainmail") || name.contains("netherite")) {
                return true;
            }
        }
        return false;
    }
}
