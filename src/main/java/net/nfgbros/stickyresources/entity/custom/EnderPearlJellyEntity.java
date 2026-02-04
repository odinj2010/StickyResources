package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EnderPearlJellyEntity extends JellyEntity {

    public EnderPearlJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.isInWaterOrRain()) {
            this.teleportRandomly(this);
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isInvulnerableTo(pSource)) {
            return false;
        } else {
            boolean flag = super.hurt(pSource, pAmount);
            if (!this.level().isClientSide && flag && this.random.nextDouble() < 0.5D) { // 50% chance to teleport on hit
                this.teleportRandomly(this);
            }
            return flag;
        }
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.is(Items.CHORUS_FRUIT)) {
            if (!this.level().isClientSide) {
                if (this.teleportRandomly(pPlayer)) { // Teleport the PLAYER
                    if (!pPlayer.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                    this.playSound(SoundEvents.PLAYER_BURP, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.CONSUME;
        }
        return super.mobInteract(pPlayer, pHand);
    }

    private boolean teleportRandomly(LivingEntity target) {
        if (this.level().isClientSide) {
            return true;
        }
        double oldX = target.getX();
        double oldY = target.getY();
        double oldZ = target.getZ();

        for (int i = 0; i < 16; ++i) { // Smaller range (approx 1/4 Enderman)
            double targetX = target.getX() + (this.random.nextDouble() - 0.5D) * 16.0D;
            double targetY = target.getY() + (double) (this.random.nextInt(16) - 8);
            double targetZ = target.getZ() + (this.random.nextDouble() - 0.5D) * 16.0D;
            
            if (teleportTo(target, targetX, targetY, targetZ)) {
                this.level().playSound(null, oldX, oldY, oldZ, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
                target.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                return true;
            }
        }
        return false;
    }

    private boolean teleportTo(LivingEntity target, double pX, double pY, double pZ) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(pX, pY, pZ);

        while (blockpos$mutableblockpos.getY() > this.level().getMinBuildHeight() && !this.level().getBlockState(blockpos$mutableblockpos).blocksMotion()) {
            blockpos$mutableblockpos.move(0, -1, 0);
        }

        BlockState blockstate = this.level().getBlockState(blockpos$mutableblockpos);
        if (blockstate.blocksMotion()) {
            boolean flag = target.randomTeleport(pX, pY, pZ, true);
            if (flag) {
                this.level().gameEvent(net.minecraft.world.level.gameevent.GameEvent.TELEPORT, target.position(), net.minecraft.world.level.gameevent.GameEvent.Context.of(target));
                if (!target.isSilent()) {
                    this.level().playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.ENDERMAN_TELEPORT, target.getSoundSource(), 1.0F, 1.0F);
                    target.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }
            }
            return flag;
        } else {
            return false;
        }
    }
}