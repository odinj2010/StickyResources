package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;


public class IceJellyEntity extends JellyEntity {

    public IceJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.isAlive() && !this.isBaby()) {
            freezeWaterNearby();
        }
    }

    private void freezeWaterNearby() {
        net.minecraft.core.BlockPos pos = this.blockPosition();
        net.minecraft.world.level.block.state.BlockState frost = net.minecraft.world.level.block.Blocks.FROSTED_ICE.defaultBlockState();
        float radius = 2.0F;
        net.minecraft.core.BlockPos.MutableBlockPos mutablePos = new net.minecraft.core.BlockPos.MutableBlockPos();

        for(net.minecraft.core.BlockPos blockpos : net.minecraft.core.BlockPos.betweenClosed(pos.offset(-2, -1, -2), pos.offset(2, -1, 2))) {
            if (blockpos.closerToCenterThan(this.position(), 2.5D)) {
                mutablePos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                net.minecraft.world.level.block.state.BlockState stateAbove = this.level().getBlockState(mutablePos);
                if (stateAbove.isAir()) {
                    net.minecraft.world.level.block.state.BlockState state = this.level().getBlockState(blockpos);
                    if (state.getBlock() == net.minecraft.world.level.block.Blocks.WATER && state.getValue(net.minecraft.world.level.block.LiquidBlock.LEVEL) == 0 && frost.canSurvive(this.level(), blockpos) && this.level().isUnobstructed(frost, blockpos, net.minecraft.world.phys.shapes.CollisionContext.empty())) {
                        this.level().setBlockAndUpdate(blockpos, frost);
                        this.level().scheduleTick(blockpos, net.minecraft.world.level.block.Blocks.FROSTED_ICE, net.minecraft.util.Mth.nextInt(this.getRandom(), 60, 120));
                    }
                }
            }
        }
    }

}