package net.nfgbros.stickyresources.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class StickyBlock extends Block {
    private final float stickiness;

    public StickyBlock(Properties properties, float stickiness) {
        super(properties);
        this.stickiness = stickiness;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            // Slow down the entity based on stickiness
            Vec3 motion = entity.getDeltaMovement();
            entity.setDeltaMovement(motion.multiply(1.0 - (stickiness * 0.5), 1.0, 1.0 - (stickiness * 0.5)));
            
            // If very sticky, allow "climbing" by reducing downward gravity effect
            if (stickiness > 0.5f && entity.horizontalCollision) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.08D, 0));
            }
        }
    }
}
