package net.nfgbros.stickyresources.item.custom;

import net.nfgbros.stickyresources.sound.ModSounds;
import net.nfgbros.stickyresources.util.ModTags;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide()) {
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            boolean foundBlock = false;

            // Search for valuable blocks below the clicked position
            for (int i = 0; i <= positionClicked.getY() + 64; i++) {
                BlockState state = pContext.getLevel().getBlockState(positionClicked.below(i));

                // Check if the current block is valuable
                if (isValuableBlock(state)) {
                    // Output valuable coordinates and play sound
                    outputValuableCoordinates(positionClicked.below(i), player, state.getBlock());
                    foundBlock = true;

                    pContext.getLevel().playSeededSound(
                            null,
                            positionClicked.getX(),
                            positionClicked.getY(),
                            positionClicked.getZ(),
                            ModSounds.METAL_DETECTOR_FOUND_ORE.get(),
                            SoundSource.BLOCKS,
                            1f,
                            1f,
                            0
                    );

                    break; // Stop searching after finding the first valuable block
                }
            }

            // Notify the player if no valuable blocks are found
            if (!foundBlock) {
                player.sendSystemMessage(Component.literal("No valuables Found!"));
            }
        }

        // Damage the item after use
        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        // Add tooltip to the item
        pTooltipComponents.add(Component.translatable("tooltip.StickyResources.metal_detector.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    // Outputs the coordinates of the valuable block to the player
    private void outputValuableCoordinates(BlockPos blockPos, Player player, Block block) {
        player.sendSystemMessage(Component.literal("Found " + I18n.get(block.getDescriptionId()) + " at " +
                "(" + blockPos.getX() + ", " + blockPos.getY() + "," + blockPos.getZ() + ")"));
    }

    // Checks if the block state represents a valuable block
    private boolean isValuableBlock(BlockState state) {
        return state.is(ModTags.Blocks.METAL_DETECTOR_VALUABLES);
    }
}
