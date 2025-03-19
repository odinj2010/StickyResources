package net.nfgbros.stickyresources.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.minecraft.core.particles.ParticleTypes;

import java.util.EnumSet;
import java.util.Map;
import java.util.HashMap;

public class JellyGrazeGoal extends Goal {

    private final JellyEntity jelly;
    private BlockPos targetBlock;
    private int grazeTime;
    private static final int GRAZE_DURATION = 100; // 5 seconds at 20 ticks per second
    private static final double SEARCH_RADIUS = 6.0;

    // Map Jelly Types to Blocks they Graze
    private static final Map<String, Block> GRAZEABLE_BLOCKS = new HashMap<>();

    static {
        GRAZEABLE_BLOCKS.put("DEFAULT", Blocks.RED_MUSHROOM);
        GRAZEABLE_BLOCKS.put("SAND", Blocks.SAND);
        GRAZEABLE_BLOCKS.put("GRAVEL", Blocks.GRAVEL);
        GRAZEABLE_BLOCKS.put("DIRT", Blocks.DIRT);
        GRAZEABLE_BLOCKS.put("GRASS", Blocks.GRASS_BLOCK);
    }

    public JellyGrazeGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        String jellyType = jelly.getJellyType().name(); // Get Jelly Type as String
        Block grazeBlock = GRAZEABLE_BLOCKS.get(jellyType);
        if (grazeBlock == null) return false; // Only certain Jellies graze

        Level world = jelly.level();
        BlockPos jellyPos = jelly.blockPosition();

        BlockPos closestBlock = null;
        double closestDistance = Double.MAX_VALUE;

        // Search for nearest grazeable block
        for (BlockPos pos : BlockPos.betweenClosed(jellyPos.offset(-6, -2, -6), jellyPos.offset(6, 2, 6))) {
            if (world.getBlockState(pos).is(grazeBlock)) {
                double distance = jellyPos.distSqr(pos);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestBlock = pos.immutable();
                }
            }
        }

        if (closestBlock != null) {
            this.targetBlock = closestBlock;
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        grazeTime = 0;
        if (targetBlock != null) {
            jelly.getMoveControl().setWantedPosition(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ(), 1.0D);
        }
    }

    @Override
    public void tick() {
        if (targetBlock == null) return;

        double distance = jelly.blockPosition().distSqr(targetBlock);
        if (distance < 1.5) {
            grazeTime++;

            // Create particle effect while grazing
            if (jelly.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, jelly.getX(), jelly.getY() + 0.5, jelly.getZ(), 5, 0.2, 0.2, 0.2, 0.1);
            }

            if (grazeTime >= GRAZE_DURATION) {
                jelly.level().removeBlock(targetBlock, false); // Remove block

                // Small healing effect when grazing is complete
                jelly.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 0)); // Heals over 3 seconds

                stop();
            }
        } else {
            jelly.getMoveControl().setWantedPosition(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ(), 1.0D);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return targetBlock != null && jelly.level().getBlockState(targetBlock).is(GRAZEABLE_BLOCKS.get(jelly.getJellyType().name())) && grazeTime < GRAZE_DURATION;
    }

    @Override
    public void stop() {
        targetBlock = null;
        grazeTime = 0;
    }
}
