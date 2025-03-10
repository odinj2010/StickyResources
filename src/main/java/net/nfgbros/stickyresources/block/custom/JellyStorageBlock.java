package net.nfgbros.stickyresources.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkHooks;
import net.nfgbros.stickyresources.block.entity.JellyStorageBlockEntity;
import net.nfgbros.stickyresources.block.entity.ModBlockEntities;

import javax.annotation.Nullable;
import java.util.List;

public class JellyStorageBlock extends BaseEntityBlock {

    // Define the block shape
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    // Constructor
    public JellyStorageBlock(Properties pProperties) {
        super(pProperties);
    }

    // Block state when placed
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState();
    }

    // Block shape
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    // Block render shape
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    // Called when the block is removed
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof JellyStorageBlockEntity) {
                List<Entity> jellies = ((JellyStorageBlockEntity) blockEntity).getJellies();
                // Spawn each jelly entity at the given position
                for (int i = 0; i < jellies.size(); i++) {
                    Entity jelly = jellies.get(i);
                    jelly.setPos(pPos.getX() + i * 0.2, pPos.getY(), pPos.getZ());
                    pLevel.addFreshEntity(jelly);
                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    // Called when a player interacts with the block
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof JellyStorageBlockEntity) {
                // Open the container screen for the player
                NetworkHooks.openScreen(((ServerPlayer) pPlayer), (MenuProvider) entity, buf -> buf.writeBlockPos(pPos));
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    // Create a new block entity for this block
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new JellyStorageBlockEntity(pPos, pState);
    }

    // Return the ticker for the block entity
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.JELLY_STORAGE_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}
