package net.nfgbros.stickyresources.entity.ai.jelly.movement;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.entity.ModEntities;

import java.util.Random;

public class JellyMovement {
    private final JellyEntity jelly;
    private final Level level;
    private final PathNavigation navigation;
    private final Random random = new Random();
    private BlockPos wanderTarget = null;
    private int wanderCooldown = 0;

    public JellyMovement(JellyEntity jelly) {
        this.jelly = jelly;
        this.level = jelly.level();
        this.navigation = jelly.getNavigation();
        this.navigation.setCanFloat(true);
    }
    private int jumpCooldown = 0;

    private void jumpOverObstacle() {
        if (jumpCooldown > 0) {
            jumpCooldown--;
            return;
        }
        // Check if the jelly is on the ground and there's a block in front of it
        if (jelly.onGround() && isBlockInFront() && isMoving() && jumpCooldown == 0) {
            // Apply a vertical velocity to make the jelly jump
            Vec3 motion = jelly.getDeltaMovement();
            jelly.setDeltaMovement(motion.x, 0.65, motion.z); // Increased jump height
            // Set a cooldown to prevent repeated jumps
            jumpCooldown = 160; // 1 second cooldown (20 ticks)
        }
    }

    private boolean isBlockInFront() {
        // Check for blocks in a wider area in front of the jelly
        BlockPos pos = jelly.blockPosition().relative(jelly.getDirection());
        BlockState blockState = level.getBlockState(pos);
        if (!blockState.isAir() && blockState.isSolid()) {
            return true;
        }

        // Check for blocks diagonally in front of the jelly
        BlockPos posDiag = pos.relative(jelly.getDirection().getClockWise());
        BlockState blockStateDiag = level.getBlockState(posDiag);
        return !blockStateDiag.isAir() && blockStateDiag.isSolid();
    }

    private boolean isMoving() {
        // Check if the jelly is moving by checking its delta movement
        Vec3 motion = jelly.getDeltaMovement();
        return motion.lengthSqr() > 0.01; // Adjust the threshold as needed
    }
    public void tick() {
        ModEntities.JellyType type = jelly.getJellyType();
        switch (type.getSwimBehavior()) {
            case FISH:
                moveLikeFish();
                break;
            case DOLPHIN:
                moveLikeDolphin();
                break;
            case WATER_DAMAGE:
                avoidWater();
                break;
            case WATER_LETHAL:
                avoidWater();
                break;
            case FLOATING:
                floatInAir();
                break;
            case SURFACE_SWIMMING:
                swimOnSurface();
                break;
            case NONE:
                defaultMovement();
                break;
        }

        // Add wandering behavior
        wander();
        // Add jumping behavior
        jumpOverObstacle();
    }

    private void moveLikeFish() {
        // Implement fish-like movement (e.g., swimming in water)
        if (isInWater()) {
            Vec3 motion = jelly.getDeltaMovement();
            jelly.setDeltaMovement(motion.add(0, 0.01, 0)); // Move upwards slightly
        }
    }

    private void moveLikeDolphin() {
        // Implement dolphin-like movement (e.g., jumping out of water)
        if (isInWater()) {
            Vec3 motion = jelly.getDeltaMovement();
            jelly.setDeltaMovement(motion.add(0, 0.1, 0)); // Jump out of water
        }
    }

    private void avoidWater() {
        // Avoid water by moving away from it
        if (isInWater()) {
            Vec3 motion = jelly.getDeltaMovement();
            jelly.setDeltaMovement(motion.add(0, -0.1, 0)); // Move downwards to avoid water
        }
    }

    private void floatInAir() {
        // Float in the air (e.g., like a balloon)
        Vec3 motion = jelly.getDeltaMovement();
        jelly.setDeltaMovement(motion.add(0, 0.05, 0)); // Float upwards
    }

    private void swimOnSurface() {
        // Swim on the surface of the water
        if (isInWater()) {
            Vec3 motion = jelly.getDeltaMovement();
            jelly.setDeltaMovement(motion.add(0, 0.02, 0)); // Stay on the surface
        }
    }

    private void defaultMovement() {
        // Default movement (e.g., walking on land)
        if (!isInWater()) {
            Vec3 motion = jelly.getDeltaMovement();
            jelly.setDeltaMovement(motion.add(0, -0.1, 0)); // Move downwards to stay on the ground
        }
    }

    private boolean isInWater() {
        BlockPos pos = jelly.blockPosition();
        BlockState blockState = level.getBlockState(pos);
        return blockState.getBlock() == Blocks.WATER;
    }

    private void wander() {
        if (wanderCooldown > 0) {
            wanderCooldown--;
            return;
        }

        if (wanderTarget == null || jelly.distanceToSqr(Vec3.atCenterOf(wanderTarget)) < 1.0) {
            // Generate a new random target position within a 10-block radius
            int range = 10;
            int x = jelly.blockPosition().getX() + random.nextInt(range * 2) - range;
            int z = jelly.blockPosition().getZ() + random.nextInt(range * 2) - range;
            wanderTarget = new BlockPos(x, jelly.blockPosition().getY(), z);

            // Set a cooldown before the next wander target is chosen
            wanderCooldown = 100 + random.nextInt(100); // 5-10 seconds cooldown
        }

        // Move towards the wander target
        navigation.moveTo(wanderTarget.getX(), wanderTarget.getY(), wanderTarget.getZ(), 0.25);
    }
}
