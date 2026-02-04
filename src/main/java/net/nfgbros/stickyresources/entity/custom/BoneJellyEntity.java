package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;


public class BoneJellyEntity extends JellyEntity {

    public BoneJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide && this.tickCount % 20 == 0 && !this.isBaby()) {
            fertilizeCrops();
        }
    }

    private void fertilizeCrops() {
        // Check block jelly is in and block below
        BlockPos pos = this.blockPosition();
        if (!tryFertilize(pos)) {
            tryFertilize(pos.below());
        }
    }

    private boolean tryFertilize(BlockPos pos) {
        BlockState state = this.level().getBlockState(pos);
        if (state.getBlock() instanceof BonemealableBlock bonemealable) {
            if (bonemealable.isValidBonemealTarget(this.level(), pos, state, this.level().isClientSide)) {
                // 5% chance to grow per check (once a second)
                if (this.random.nextFloat() < 0.05f) {
                    if (this.level() instanceof ServerLevel serverLevel) {
                        if (bonemealable.isBonemealSuccess(this.level(), this.random, pos, state)) {
                            bonemealable.performBonemeal(serverLevel, this.random, pos, state);
                            this.level().levelEvent(2005, pos, 0); // Bonemeal particles
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}