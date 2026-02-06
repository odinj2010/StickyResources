package net.nfgbros.stickyresources.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.List;

public class SapphireStaffItem extends Item {
    public SapphireStaffItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (!pLevel.isClientSide()) {
            // Sticky Pulse logic
            AABB area = pPlayer.getBoundingBox().inflate(6.0D);
            List<LivingEntity> targets = pLevel.getEntitiesOfClass(LivingEntity.class, area, 
                entity -> entity != pPlayer && !(entity instanceof JellyEntity));

            for (LivingEntity target : targets) {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4)); // Slowness V for 3s
            }

            // Visual and Sound effects
            ((ServerLevel) pLevel).sendParticles(ParticleTypes.SNOWFLAKE, pPlayer.getX(), pPlayer.getY() + 1, pPlayer.getZ(), 50, 1.0, 0.5, 1.0, 0.1);
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 0.5F);

            stack.hurtAndBreak(5, pPlayer, p -> p.broadcastBreakEvent(pHand));
            pPlayer.getCooldowns().addCooldown(this, 100); // 5s cooldown
        }

        return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide());
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pHand) {
        if (pInteractionTarget instanceof JellyEntity jelly) {
            if (!pPlayer.level().isClientSide()) {
                jelly.forceDrop();
                pStack.hurtAndBreak(10, pPlayer, p -> p.broadcastBreakEvent(pHand));
                pPlayer.getCooldowns().addCooldown(this, 200); // 10s cooldown for instant drop
            }
            return InteractionResult.SUCCESS;
        }
        return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pHand);
    }
}
