package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.graze;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;
import java.util.Map;
import java.util.function.Predicate;

public class JellyGrazeGoal extends Goal {
    private static final Map<ModEntities.JellyType, Predicate<BlockState>> VALID_BLOCKS;

    static {
        VALID_BLOCKS = Map.ofEntries(
                Map.entry(ModEntities.JellyType.DEFAULT, state -> !state.isAir() && !state.is(Blocks.BEDROCK)), // Default jelly can graze on any block except air and bedrock
                Map.entry(ModEntities.JellyType.SAND, state -> state.is(Blocks.SAND)),
                Map.entry(ModEntities.JellyType.GRAVEL, state -> state.is(Blocks.GRAVEL)),
                Map.entry(ModEntities.JellyType.DIRT, state -> state.is(Blocks.DIRT)),
                Map.entry(ModEntities.JellyType.LOGOAK, state -> state.is(BlockTags.LOGS)),
                Map.entry(ModEntities.JellyType.REDMUSHROOM, state -> state.is(Blocks.RED_MUSHROOM)),
                Map.entry(ModEntities.JellyType.BONE, state -> state.is(Blocks.BONE_BLOCK)),
                Map.entry(ModEntities.JellyType.HONEY, state -> state.is(BlockTags.FLOWERS)),
                Map.entry(ModEntities.JellyType.LAVA, state -> state.is(BlockTags.BASE_STONE_OVERWORLD)),
                Map.entry(ModEntities.JellyType.WATER, state -> state.is(BlockTags.ICE)),
                Map.entry(ModEntities.JellyType.AMETHYST, state -> state.is(Blocks.AMETHYST_BLOCK)),
                Map.entry(ModEntities.JellyType.CHARCOAL, state -> state.is(Blocks.COAL_BLOCK)),
                Map.entry(ModEntities.JellyType.CAKE, state -> state.is(Blocks.CAKE)),
                Map.entry(ModEntities.JellyType.COAL, state -> state.is(Blocks.COAL_ORE)),
                Map.entry(ModEntities.JellyType.COBBLESTONE, state -> state.is(Blocks.COBBLESTONE)),
                Map.entry(ModEntities.JellyType.COW, state -> state.is(Blocks.GRASS_BLOCK)),
                Map.entry(ModEntities.JellyType.DIAMOND, state -> state.is(Blocks.DIAMOND_BLOCK)),
                Map.entry(ModEntities.JellyType.EMERALD, state -> state.is(Blocks.EMERALD_BLOCK)),
                Map.entry(ModEntities.JellyType.ENDERPEARL, state -> state.is(Blocks.END_STONE)),
                Map.entry(ModEntities.JellyType.GLASS, state -> state.is(Blocks.GLASS)),
                Map.entry(ModEntities.JellyType.GRASS, state -> state.is(Blocks.GRASS_BLOCK)),
                Map.entry(ModEntities.JellyType.ICE, state -> state.is(Blocks.ICE)),
                Map.entry(ModEntities.JellyType.LAPIS, state -> state.is(Blocks.LAPIS_BLOCK)),
                Map.entry(ModEntities.JellyType.OBSIDIAN, state -> state.is(Blocks.OBSIDIAN)),
                Map.entry(ModEntities.JellyType.PRISMERINE, state -> state.is(Blocks.PRISMARINE)),
                Map.entry(ModEntities.JellyType.PUMPKIN, state -> state.is(Blocks.PUMPKIN)),
                Map.entry(ModEntities.JellyType.RAWCOPPER, state -> state.is(Blocks.RAW_COPPER_BLOCK)),
                Map.entry(ModEntities.JellyType.RAWGOLD, state -> state.is(Blocks.RAW_GOLD_BLOCK)),
                Map.entry(ModEntities.JellyType.RAWIRON, state -> state.is(Blocks.RAW_IRON_BLOCK)),
                Map.entry(ModEntities.JellyType.REDSTONEDUST, state -> state.is(Blocks.REDSTONE_BLOCK)),
                Map.entry(ModEntities.JellyType.RAWSAPPHIRE, state -> state.is(ModBlocks.SAPPHIRE_BLOCK.get())),
                Map.entry(ModEntities.JellyType.STONE, state -> state.is(Blocks.STONE)),
                Map.entry(ModEntities.JellyType.STRAWBERRY, state -> state.is(Blocks.SWEET_BERRY_BUSH) || state.is(ModBlocks.STRAWBERRY_CROP.get()))
        );
    }
    private final JellyEntity jelly;
    private final GrazeState grazeState;
    private final GrazeBlockFinder blockFinder;
    private final GrazeActionHandler actionHandler;

    public JellyGrazeGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.grazeState = new GrazeState();
        this.blockFinder = new GrazeBlockFinder(jelly, StickyResourcesConfig.JELLY_GRAZE_SEARCH_RADIUS.get(), StickyResourcesConfig.JELLY_GRAZE_MAX_HOLE_DEPTH.get(), VALID_BLOCKS);
        this.actionHandler = new GrazeActionHandler(jelly, grazeState);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!StickyResourcesConfig.JELLY_GRAZING_ACTIVE.get() || jelly.getHealth() >= jelly.getMaxHealth()) {
            return false; // Do not activate if grazing is disabled or the jelly is at full health
        }

        grazeState.targetBlock = blockFinder.findClosestGrazeableBlock(); // Find a new target block
        return grazeState.targetBlock != null; // Activate if a target block is found
    }

    @Override
    public boolean canContinueToUse() {
        if (grazeState.targetBlock == null) return false; // Stop if no target block is found
        Predicate<BlockState> isValidBlock = VALID_BLOCKS.get(jelly.getJellyType());
        return isValidBlock != null && isValidBlock.test(jelly.level().getBlockState(grazeState.targetBlock)) &&
                grazeState.grazeTime < StickyResourcesConfig.JELLY_GRAZE_DURATION.get() && jelly.isAlive(); // Continue if the target block is valid and the jelly is alive
    }

    @Override
    public void start() {
        grazeState.grazeTime = 0; // Reset graze time
        if (grazeState.targetBlock != null) {
            jelly.getNavigation().moveTo(grazeState.targetBlock.getX(), grazeState.targetBlock.getY(), grazeState.targetBlock.getZ(), 1.0D); // Move to the target block
        }
    }

    @Override
    public void tick() {
        if (grazeState.targetBlock == null) {
            actionHandler.stopGrazing(); // Stop grazing if there is no target block
            return;
        }

        // Check if the jelly is close enough to the target block
        if (blockFinder.isCloseEnoughToGraze(grazeState.targetBlock)) {
            actionHandler.handleGrazing(); // Handle grazing behavior
        } else {
            // Re-evaluate the target block if the jelly is too far away
            grazeState.targetBlock = blockFinder.findClosestGrazeableBlock();
            if (grazeState.targetBlock != null) {
                jelly.getNavigation().moveTo(grazeState.targetBlock.getX(), grazeState.targetBlock.getY(), grazeState.targetBlock.getZ(), 1.0D); // Move to the new target block
            } else {
                actionHandler.stopGrazing(); // Stop grazing if no valid target block is found
            }
        }
    }

    @Override
    public void stop() {
        grazeState.targetBlock = null; // Clear the target block
        grazeState.grazeTime = 0; // Reset graze time
        jelly.getNavigation().stop(); // Stop movement
        actionHandler.stopGrazing(); // Stop grazing when the goal is stopped
    }
}

