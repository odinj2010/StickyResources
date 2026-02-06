package net.nfgbros.stickyresources.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.item.ModItems;

import java.util.HashMap;
import java.util.Map;

public class JellySummoningUtils {

    private static final Map<ModEntities.JellyType, SummoningRequirement> SUMMONING_REQUIREMENTS = new HashMap<>();

    static {
        SUMMONING_REQUIREMENTS.put(ModEntities.JellyType.DEFAULT, new SummoningRequirement(
                Blocks.SLIME_BLOCK,
                Blocks.DIRT,
                ModItems.STICKY_CATALYST.get(),
                null
        ));
        /* Add More:

        SUMMONING_REQUIREMENTS.put(ModEntities.JellyType.AMETHYST, new SummoningRequirement(
                Blocks.AMETHYST_BLOCK,
                ModBlocks.STICKY_STONE.get(),
                ModItems.STICKY_AMETHYST.get(),
                null
        ));
         */
    }

    public static boolean isJellySummoningStructure(Level level, BlockPos pos, ItemStack catalyst) {
        BlockState base1 = level.getBlockState(pos.below());
        BlockState base2 = level.getBlockState(pos.below().below());
        BlockState top = level.getBlockState(pos);

        ModEntities.JellyType expectedType = null;
        for (Map.Entry<ModEntities.JellyType, SummoningRequirement> entry : SUMMONING_REQUIREMENTS.entrySet()) {
            if (entry.getValue().topBlock == top.getBlock()) {
                expectedType = entry.getKey();
                break;
            }
        }

        if (expectedType == null) {
            return false;
        }

        SummoningRequirement requirement = SUMMONING_REQUIREMENTS.get(expectedType);

        if (base1.getBlock() != requirement.baseBlock ||
                base2.getBlock() != requirement.baseBlock) {
            return false;
        }

        int pillarDistance = 3;
        BlockPos pillar1Pos = pos.offset(pillarDistance, -1, pillarDistance);
        BlockPos pillar2Pos = pos.offset(-pillarDistance, -1, pillarDistance);
        BlockPos pillar3Pos = pos.offset(pillarDistance, -1, pillarDistance);
        BlockPos pillar4Pos = pos.offset(pillarDistance, -1, -pillarDistance);

        BlockState pillar1 = level.getBlockState(pillar1Pos);
        BlockState pillar2 = level.getBlockState(pillar2Pos);
        BlockState pillar3 = level.getBlockState(pillar3Pos);
        BlockState pillar4 = level.getBlockState(pillar4Pos);

        if (
                pillar1.getBlock() != Blocks.STONE
                        ||
                        pillar2.getBlock() != Blocks.STONE
                        ||
                        pillar3.getBlock() != Blocks.STONE
                        ||
                        pillar4.getBlock() != Blocks.STONE
        ) {
            return false;
        }

        if (catalyst.isEmpty() || !catalyst.is(requirement.catalystItem)) {
            return false;
        }

        return true;
    }

    private static void scheduleDelayedTick(Level level, BlockPos pos, ItemStack catalyst, int tick, int delay) {
        ((ServerLevel) level).getServer().execute(() -> {
            if(level.getGameTime() % delay == 0) {
                animateSummoning(level, pos, catalyst, tick + 1);
            } else {
                scheduleDelayedTick(level, pos, catalyst, tick, delay);
            }
        });
    }

    public static void startJellySummoning(Level level, BlockPos pos, ItemStack catalyst) {
        if (!level.isClientSide()) {
            LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            lightning.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            ((ServerLevel) level).addFreshEntity(lightning);

            level.playSound(null, pos, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.BLOCKS, 1f, 1f);


            level.getServer().execute(() -> animateSummoning(level, pos, catalyst, 0));
        }
    }

    private static void animateSummoning(Level level, BlockPos pos, ItemStack catalyst, int tick) {
        if (!level.isClientSide()) {
            if (tick < 60) {
                // Gradually increase the glow
                level.levelEvent(2001, pos, Block.getId(Blocks.GLOWSTONE.defaultBlockState()));

                scheduleDelayedTick(level, pos, catalyst, tick, 5); // delay of 5 ticks
            } else if (tick < 80) {
                // Beacon of light
                level.levelEvent(2002, pos, 0);

                scheduleDelayedTick(level, pos, catalyst, tick, 60); // delay of 5 ticks
            } else {
                completeJellySummoning(level, pos, catalyst);
            }
        }
    }

    private static void completeJellySummoning(Level level, BlockPos pos, ItemStack catalyst) {
        if (!level.isClientSide()) {
            ModEntities.JellyType typeToSummon = null;
            for (Map.Entry<ModEntities.JellyType, SummoningRequirement> entry : SUMMONING_REQUIREMENTS.entrySet()) {
                if (entry.getValue().topBlock == level.getBlockState(pos).getBlock()) {
                    typeToSummon = entry.getKey();
                    break;
                }
            }
            if (typeToSummon == null) return;

            JellyEntity jelly = ModEntities.JELLY_ENTITIES.get(typeToSummon).get().create((ServerLevel) level);
            if (jelly != null) {
                jelly.moveTo(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, 0, 0);
                level.addFreshEntity(jelly);
                catalyst.shrink(1);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }

            level.levelEvent(2001, pos, Block.getId(ModBlocks.STICKY_DIRT.get().defaultBlockState()));
            level.playSound(null, pos, SoundEvents.SLIME_BLOCK_PLACE, SoundSource.BLOCKS, 1f, 1f);
        }
    }

    public static Map<ModEntities.JellyType, SummoningRequirement> getSummoningRequirements() {
        return SUMMONING_REQUIREMENTS;
    }

    public static class SummoningRequirement {
        public Block topBlock;
        public Block baseBlock;
        public Item catalystItem;
        public AdditionalStructureCheck additionalCheck;

        public SummoningRequirement(Block topBlock, Block baseBlock, Item catalystItem, AdditionalStructureCheck additionalCheck) {
            this.topBlock = topBlock;
            this.baseBlock = baseBlock;
            this.catalystItem = catalystItem;
            this.additionalCheck = additionalCheck;
        }
    }

    public interface AdditionalStructureCheck {
        boolean check(Level level, BlockPos pos);
    }

    /**
     * NOTE: The current implementation of scheduleDelayedTick uses a busy-wait approach via server execution.
     * This may lead to performance overhead on high-traffic servers and will not persist across server restarts.
     * If targeting stable server environments, consider refactoring this to use a BlockEntity or a more robust tick-based system.
     */
}