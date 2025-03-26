package net.nfgbros.stickyresources.entity.ai.jelly.feeding;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.entity.ModEntities;

public class JellyFeedingAI {
    private final JellyEntity jelly;
    private final Level level;

    public JellyFeedingAI(JellyEntity jelly) {
        this.jelly = jelly;
        this.level = jelly.level();
    }

    public void tick() {
        if (!level.isClientSide) {
            // Check for nearby food items
            level.getEntitiesOfClass(ItemEntity.class, jelly.getBoundingBox().inflate(5), entity -> isPreferredFood(entity.getItem()) || isLoveFood(entity.getItem()))
                    .forEach(this::consumeFood);

            // Check for nearby food blocks
            BlockPos jellyPos = jelly.blockPosition();
            BlockState blockState = level.getBlockState(jellyPos);
            if (isPreferredBlock(blockState)) {
                consumeBlock(jellyPos, blockState);
            }

            // Check if the player is holding preferred or love food
            followPlayerIfHoldingFood();
        }
    }
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (isPreferredFood(itemStack) || isLoveFood(itemStack)) {
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1); // Reduce the item stack size by 1
            }

            if (isLoveFood(itemStack)) {
                jelly.setInLove(player); // Trigger love mode
            } else {
                // TODO: Add logic to satisfy the jelly's hunger (to be implemented in the survival AI class)
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return mobInteract(player, hand);
    }

    private boolean isPreferredFood(ItemStack itemStack) {
        ModEntities.JellyType type = jelly.getJellyType();
        // Define preferred food for each jelly type
        switch (type) {
            case AMETHYST -> {
                return itemStack.is(Items.AMETHYST_SHARD);
            }
            case BONE -> {
                return itemStack.is(Items.BONE_MEAL);
            }
            case CHARCOAL -> {
                return itemStack.is(Items.CHARCOAL);
            }
            // Add more cases for other jelly types
            default -> {
                return false;
            }
        }
    }

    private boolean isLoveFood(ItemStack itemStack) {
        ModEntities.JellyType type = jelly.getJellyType();
        // Define the love food for each jelly type
        switch (type) {
            case AMETHYST -> {
                return itemStack.is(Items.AMETHYST_SHARD); // Amethyst Jelly loves amethyst shards
            }
            case BONE -> {
                return itemStack.is(Items.BONE); // Bone Jelly loves bones
            }
            case CHARCOAL -> {
                return itemStack.is(Items.CHARCOAL); // Charcoal Jelly loves charcoal
            }
            case CAKE -> {
                return itemStack.is(Items.CAKE); // Cake Jelly loves cake
            }
            case LOGOAK -> {
                return itemStack.is(Items.OAK_LOG); // Logoak Jelly loves oak logs
            }
            // Add more cases for other jelly types
            default -> {
                return false;
            }
        }
    }
    private boolean isPreferredBlock(BlockState blockState) {
        ModEntities.JellyType type = jelly.getJellyType();
        // Define preferred blocks for each jelly type
        switch (type) {
            case BONE -> {
                return blockState.is(Blocks.BONE_BLOCK);
            }
            case CAKE -> {
                return blockState.is(Blocks.CAKE);
            }
            case LOGOAK -> {
                return blockState.is(Blocks.OAK_LOG);
            }
            // Add more cases for other jelly types
            default -> {
                return false;
            }
        }
    }
    private void consumeFood(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();
        if (!itemStack.isEmpty()) {
            // Check if the item is the love food
            if (isLoveFood(itemStack)) {
                jelly.setInLove(null); // Trigger love mode
            } else if (isPreferredFood(itemStack)) {
                // Reduce the item stack size by 1
                itemStack.shrink(1);
                // TODO: Add logic to satisfy the jelly's hunger (to be implemented in the survival AI class)
            }
        }
    }
    private void consumeBlock(BlockPos pos, BlockState blockState) {
        // Remove the block
        level.destroyBlock(pos, false);
        // TODO: Add logic to satisfy the jelly's hunger (to be implemented in the survival AI class)
    }

    private void followPlayerIfHoldingFood() {
        Player player = level.getNearestPlayer(jelly, 8); // Adjust the range as needed
        if (player != null) {
            ItemStack heldItem = player.getMainHandItem();
            if (isPreferredFood(heldItem) || isLoveFood(heldItem)) {
                // Add a temporary follow goal
                jelly.getNavigation().moveTo(player, 0.3); // Adjust the speed as needed
            }
        }
    }
}
