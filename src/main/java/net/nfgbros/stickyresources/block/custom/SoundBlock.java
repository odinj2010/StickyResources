package net.nfgbros.stickyresources.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoundBlock extends Block {
    // Constructor for creating the SoundBlock with given properties
    public SoundBlock(Properties pProperties) {
        super(pProperties);
    }

    /**
     * Called when the block is right-clicked by a player. Plays a sound.
     *
     * @param pState  Current block state
     * @param pLevel  Level where the block exists
     * @param pPos    Position of the block
     * @param pPlayer Player interacting with the block
     * @param pHand   Player's hand interacting with the block
     * @param pHit    Hit result of the interaction
     * @return Interaction result
     */
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        pLevel.playSound(
                pPlayer,
                pPos,
                SoundEvents.NOTE_BLOCK_DIDGERIDOO.get(),
                SoundSource.BLOCKS,
                1f,
                1f
        );
        return InteractionResult.SUCCESS;
    }

    /**
     * Adds a tooltip to the block when hovered in the inventory.
     *
     * @param pStack   The ItemStack of the block
     * @param pLevel   The block's context in the world (optional)
     * @param pTooltip List of components for the tooltip
     * @param pFlag    Provides additional information on the tooltip context
     */
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel,
                                List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.literal("Makes sweet sounds when right-clicked!"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
