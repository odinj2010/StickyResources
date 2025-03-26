package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;

public class WaterJellyEntity extends JellyEntity {

    private int cobblestoneDropDelay = 0;
    private static final int COBBLESTONE_DELAY = 200; // Ticks

    public WaterJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide && hand == InteractionHand.MAIN_HAND) {
            BlockPos pos = this.getOnPos();
            BlockState state = this.level().getBlockState(pos);

            if (state.is(Blocks.FARMLAND) || state.is(Blocks.GRASS_BLOCK)) {
                this.level().setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
                this.remove(RemovalReason.KILLED);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }
}