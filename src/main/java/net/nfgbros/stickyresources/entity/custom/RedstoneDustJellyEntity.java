package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.state.BlockState;


public class RedstoneDustJellyEntity extends JellyEntity {

    private final java.util.Set<BlockPos> poweredBlocks = new java.util.HashSet<>();

    public RedstoneDustJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.isAlive()) {
            // Run every tick to maintain power and prevent vanilla logic from flickering wires
            activateNearbyRedstone();
            
            if (this.tickCount % 100 == 0 && !poweredBlocks.isEmpty()) {
                this.playSound(net.minecraft.sounds.SoundEvents.REDSTONE_TORCH_BURNOUT, 0.1f, 1.2f);
            }
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
        int radius = 2; 
        Level level = this.level();
        java.util.Set<BlockPos> nextPoweredBlocks = new java.util.HashSet<>();
        
        for (BlockPos targetPos : BlockPos.betweenClosed(pos.offset(-radius, -1, -radius), pos.offset(radius, 1, radius))) {
            BlockState state = level.getBlockState(targetPos);
            BlockPos immutablePos = targetPos.immutable();
            
            if (state.is(Blocks.REDSTONE_WIRE)) {
                int currentPower = state.getValue(RedStoneWireBlock.POWER);
                // Power it if it's low or if WE are the ones currently powering it
                if (currentPower < 15 || poweredBlocks.contains(immutablePos)) {
                    level.setBlock(immutablePos, state.setValue(RedStoneWireBlock.POWER, 15), 2);
                    nextPoweredBlocks.add(immutablePos);
                }
            } 
            else if (state.is(Blocks.REDSTONE_LAMP)) {
                // Force lamp on if it's off or if WE are powering it
                if (!state.getValue(RedstoneLampBlock.LIT) || poweredBlocks.contains(immutablePos)) {
                    // Only override if it doesn't have a natural signal from elsewhere
                    if (!level.hasNeighborSignal(immutablePos)) {
                        level.setBlock(immutablePos, state.setValue(RedstoneLampBlock.LIT, true), 2);
                        nextPoweredBlocks.add(immutablePos);
                    }
                }
            }
            // For other blocks (Doors, TNT, etc.), just trigger an update
            else if (isRedstoneResponsive(state)) {
                if (this.tickCount % 5 == 0) {
                    level.updateNeighborsAt(immutablePos, state.getBlock());
                }
            }
        }

        // Revert any blocks that are no longer in our "power zone"
        for (BlockPos oldPos : poweredBlocks) {
            if (!nextPoweredBlocks.contains(oldPos)) {
                revertBlock(oldPos);
            }
        }

        poweredBlocks.clear();
        poweredBlocks.addAll(nextPoweredBlocks);

        if (!poweredBlocks.isEmpty() && this.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            if (this.random.nextInt(10) == 0) {
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), 1, 0.1, 0.1, 0.1, 0.02);
            }
        }
    }

    private void revertBlock(BlockPos pos) {
        BlockState state = this.level().getBlockState(pos);
        if (state.is(Blocks.REDSTONE_WIRE)) {
            this.level().setBlock(pos, state.setValue(RedStoneWireBlock.POWER, 0), 3);
        } else if (state.is(Blocks.REDSTONE_LAMP)) {
            // Only turn off if it doesn't have a real redstone signal
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

}