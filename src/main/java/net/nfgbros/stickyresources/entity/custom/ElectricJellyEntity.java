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

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ElectricJellyEntity extends JellyEntity {
    private final Set<BlockPos> poweredBlocks = new HashSet<>();

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
            // High Voltage Redstone Activation
            activateNearbyRedstone();

            // Discharge if wet
            if (this.isInWaterOrRain()) {
                discharge(5.0D);
            }
            
            // Charge nearby creepers
            chargeNearbyCreepers();
        }
    }

    @Override
    public void remove(RemovalReason pReason) {
        if (!this.level().isClientSide) {
            for (BlockPos pos : poweredBlocks) {
                revertBlock(pos);
            }
            poweredBlocks.clear();
        }
        super.remove(pReason);
    }

    private void activateNearbyRedstone() {
        BlockPos pos = this.blockPosition();
        int radius = 4; // Twice the reach of Redstone Jelly
        Level level = this.level();
        Set<BlockPos> nextPoweredBlocks = new HashSet<>();
        
        for (BlockPos targetPos : BlockPos.betweenClosed(pos.offset(-radius, -2, -radius), pos.offset(radius, 2, radius))) {
            BlockState state = level.getBlockState(targetPos);
            BlockPos immutablePos = targetPos.immutable();
            
            if (state.is(Blocks.REDSTONE_WIRE)) {
                int currentPower = state.getValue(RedStoneWireBlock.POWER);
                if (currentPower < 15 || poweredBlocks.contains(immutablePos)) {
                    level.setBlock(immutablePos, state.setValue(RedStoneWireBlock.POWER, 15), 2);
                    nextPoweredBlocks.add(immutablePos);
                }
            } 
            else if (state.is(Blocks.REDSTONE_LAMP)) {
                if (!state.getValue(RedstoneLampBlock.LIT) || poweredBlocks.contains(immutablePos)) {
                    if (!level.hasNeighborSignal(immutablePos)) {
                        level.setBlock(immutablePos, state.setValue(RedstoneLampBlock.LIT, true), 2);
                        nextPoweredBlocks.add(immutablePos);
                    }
                }
            }
            else if (isRedstoneResponsive(state)) {
                // High voltage forces more frequent updates
                level.updateNeighborsAt(immutablePos, state.getBlock());
            }
        }

        for (BlockPos oldPos : poweredBlocks) {
            if (!nextPoweredBlocks.contains(oldPos)) {
                revertBlock(oldPos);
            }
        }

        poweredBlocks.clear();
        poweredBlocks.addAll(nextPoweredBlocks);

        if (!poweredBlocks.isEmpty() && this.tickCount % 5 == 0) {
            this.playSound(SoundEvents.BEE_LOOP, 0.05f, 2.0f); // Low electrical hum
        }
    }

    private void revertBlock(BlockPos pos) {
        BlockState state = this.level().getBlockState(pos);
        if (state.is(Blocks.REDSTONE_WIRE)) {
            this.level().setBlock(pos, state.setValue(RedStoneWireBlock.POWER, 0), 3);
        } else if (state.is(Blocks.REDSTONE_LAMP)) {
            if (!this.level().hasNeighborSignal(pos)) {
                this.level().setBlock(pos, state.setValue(RedstoneLampBlock.LIT, false), 3);
            }
        }
    }

    private boolean isRedstoneResponsive(BlockState state) {
        return state.is(Blocks.TNT) || state.is(Blocks.IRON_DOOR) || state.is(Blocks.IRON_TRAPDOOR) || 
               state.is(Blocks.PISTON) || state.is(Blocks.STICKY_PISTON) || state.is(Blocks.DROPPER) || 
               state.is(Blocks.DISPENSER) || state.is(Blocks.HOPPER);
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
                entity -> {
                    if (entity == this || entity instanceof JellyEntity) return false;
                    if (entity instanceof net.minecraft.world.entity.player.Player player) {
                        return hasMetalArmor(player);
                    }
                    return true; // Target all other living entities (monsters, etc)
                });

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
