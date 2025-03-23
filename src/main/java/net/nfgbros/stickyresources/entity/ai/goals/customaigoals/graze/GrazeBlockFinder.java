package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.graze;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.Map;
import java.util.function.Predicate;

public class GrazeBlockFinder {
    private final JellyEntity jelly;
    private final double searchRadius;
    private final int maxHoleDepth;
    private final Map<ModEntities.JellyType, Predicate<BlockState>> VALID_BLOCKS;

    public GrazeBlockFinder(JellyEntity jelly, double searchRadius, int maxHoleDepth, Map<ModEntities.JellyType, Predicate<BlockState>> validBlocks) {
        this.jelly = jelly;
        this.searchRadius = searchRadius;
        this.maxHoleDepth = maxHoleDepth;
        this.VALID_BLOCKS = validBlocks;
    }

    /**
     * Finds the closest grazeable block within the search radius.
     *
     * @return The position of the closest grazeable block, or null if none is found.
     */
    public BlockPos findClosestGrazeableBlock() {
        Level world = jelly.level();
        BlockPos jellyPos = jelly.blockPosition();
        Predicate<BlockState> isValidBlock = VALID_BLOCKS.get(jelly.getJellyType());
        if (isValidBlock == null) return null;

        BlockPos closestBlock = null;
        double closestDistance = Double.MAX_VALUE;

        for (BlockPos pos : BlockPos.betweenClosed(jellyPos.offset((int) -searchRadius, -2, (int) -searchRadius), jellyPos.offset((int) searchRadius, 2, (int) searchRadius))) {
            BlockState state = world.getBlockState(pos);
            if (isValidBlock.test(state) && isSafeGrazingLocation(world, pos, jellyPos)) {
                double distance = jellyPos.distSqr(pos);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestBlock = pos.immutable();
                }
            }
        }
        return closestBlock;
    }

    /**
     * Checks if the target block is a safe location for grazing.
     *
     * @param world   The world where the block is located.
     * @param pos     The position of the target block.
     * @param jellyPos The position of the jelly.
     * @return True if the location is safe for grazing, false otherwise.
     */
    private boolean isSafeGrazingLocation(Level world, BlockPos pos, BlockPos jellyPos) {
        if (pos.getY() < jellyPos.getY() - maxHoleDepth) {
            return false; // Not safe: Block is too deep.
        }
        // Check for sufficient space *above* the target block.
        for (int yOffset = 1; yOffset <= 2; yOffset++) { // Check 2 blocks above.
            BlockState aboveState = world.getBlockState(pos.above(yOffset));
            if (!aboveState.isAir() && !aboveState.is(BlockTags.LEAVES)) {
                return false; // Not safe: Something solid above.
            }
        }
        return true;
    }

    /**
     * Checks if the jelly is close enough to the target block to graze.
     *
     * @param targetBlock The position of the target block.
     * @return True if the jelly is close enough to graze, false otherwise.
     */
    public boolean isCloseEnoughToGraze(BlockPos targetBlock) {
        if (targetBlock == null) return false;
        return jelly.blockPosition().distSqr(targetBlock) <= searchRadius * searchRadius;
    }
}
