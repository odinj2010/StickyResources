package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.nfgbros.stickyresources.StickyResourcesConfig;

import java.util.List;

public class ElectricJellyEntity extends JellyEntity {

    private int tickCount;

    public ElectricJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
        this.tickCount = 0;
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
