package net.nfgbros.stickyresources.entity.ai.jelly.movement;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.entity.ModEntities;

import java.util.Random;

public class JellyMovement {
    private static final int JUMP_COOLDOWN = 20;
    private static final int WANDER_COOLDOWN_MIN = 100;
    private static final int WANDER_COOLDOWN_RANDOM = 100;
    private static final int WANDER_RANGE = 10;
    private static final double WANDER_SPEED = 0.25;

    private final JellyEntity jelly;
    private final Level level;
    private final PathNavigation navigation;
    private final Random random = new Random();
    private BlockPos wanderTarget = null;
    private int wanderCooldown = 0;
    private int jumpCooldown = 0;

    public JellyMovement(JellyEntity jelly) {
        this.jelly = jelly;
        this.level = jelly.level();
        this.navigation = jelly.getNavigation();
        this.navigation.setCanFloat(StickyResourcesConfig.JELLY_CAN_FLOAT.get());
    }

    public void tick() {
        if (jelly.isNoAi()) return;

        handleMovement();
        wander();
        jumpOverObstacle();
    }

    private void handleMovement() {
        ModEntities.JellyType type = jelly.getJellyType();
        switch (type.getSwimBehavior()) {
            case FISH:
                moveLikeFish();
                break;
            case DOLPHIN:
                moveLikeDolphin();
                break;
            case WATER_DAMAGE:
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
    }

    private void jumpOverObstacle() {
        if (jumpCooldown > 0) {
            jumpCooldown--;
            return;
        }
        if (jelly.onGround() && isBlockInFront() && isTryingToMove()) {
            Vec3 motion = jelly.getDeltaMovement();
            jelly.setDeltaMovement(motion.x, StickyResourcesConfig.JELLY_JUMP_VELOCITY.get(), motion.z);
            jumpCooldown = JUMP_COOLDOWN;
        }
    }

    private boolean isBlockInFront() {
        BlockPos forward = jelly.blockPosition().relative(jelly.getDirection());
        return !level.getBlockState(forward).isAir() || !level.getBlockState(forward.above()).isAir();
    }

    private boolean isTryingToMove() {
        return !navigation.isDone();
    }

    private void moveLikeFish() {
        if (isInWater()) {
            // Randomly swim up or down
            if (random.nextInt(30) == 0) {
                double dy = (random.nextDouble() - 0.5) * 0.2;
                jelly.setDeltaMovement(jelly.getDeltaMovement().add(0, dy, 0));
            }
            // Move faster in water
            navigation.setSpeedModifier(1.5);
        } else {
            // Flop around on land
             if (jelly.onGround() && random.nextInt(10) == 0) {
                 jelly.getJumpControl().jump();
             }
        }
    }

    private void moveLikeDolphin() {
        if (isInWater()) {
            // Occasionally breach the surface
            if (random.nextFloat() < 0.02) {
                Vec3 motion = jelly.getDeltaMovement();
                jelly.setDeltaMovement(motion.x, 0.4 + random.nextDouble() * 0.4, motion.z);
            }
        }
    }

    private void avoidWater() {
        if (isInWater()) {
            // Panic jump to get out
            jelly.getJumpControl().jump();
            BlockPos safePos = findSafeGround();
            if (safePos != null) {
                navigation.moveTo(safePos.getX(), safePos.getY(), safePos.getZ(), WANDER_SPEED * 1.5);
            }
        }
    }

    private void floatInAir() {
        // Anti-gravity effect
        Vec3 motion = jelly.getDeltaMovement();
        // Counteract gravity (approx -0.08 per tick) slightly to float down slowly or hover
        if (!jelly.onGround() && motion.y < 0.0) {
            jelly.setDeltaMovement(motion.multiply(1.0, 0.75, 1.0)); // Slow fall
        }
        
        // Randomly float up slightly
        if (random.nextInt(40) == 0) {
            jelly.setDeltaMovement(motion.add(0, 0.1, 0));
        }
    }

    private void swimOnSurface() {
        if (isInWater()) {
            BlockPos currentPos = jelly.blockPosition();
            if (level.getFluidState(currentPos).is(FluidTags.WATER) && level.getFluidState(currentPos.above()).is(FluidTags.WATER)) {
                 // Force upward movement if deep underwater
                 jelly.setDeltaMovement(jelly.getDeltaMovement().add(0, 0.05, 0));
            } else if (level.getFluidState(currentPos).is(FluidTags.WATER)) {
                // Bob on surface
                if (jelly.getDeltaMovement().y < -0.01) {
                     jelly.setDeltaMovement(jelly.getDeltaMovement().multiply(1, 0.5, 1));
                }
            }
        }
    }

    private void defaultMovement() {
        // Standard ground movement is handled by vanilla AI, 
        // but we can add small hops here if we want a "slimy" feel
        if (jelly.onGround() && isTryingToMove() && random.nextInt(60) == 0) {
            jelly.getJumpControl().jump();
        }
    }

    private boolean isInWater() {
        return jelly.isInWater();
    }

    private void wander() {
        if (wanderCooldown > 0) {
            wanderCooldown--;
            return;
        }

        if (wanderTarget == null || jelly.distanceToSqr(Vec3.atCenterOf(wanderTarget)) < 2.0 || navigation.isDone()) {
            wanderTarget = findRandomGroundTarget();
            if (wanderTarget != null) {
                navigation.moveTo(wanderTarget.getX(), wanderTarget.getY(), wanderTarget.getZ(), WANDER_SPEED);
                wanderCooldown = WANDER_COOLDOWN_MIN + random.nextInt(WANDER_COOLDOWN_RANDOM);
            }
        }
    }

    private BlockPos findRandomGroundTarget() {
        for (int i = 0; i < 10; i++) {
            int x = jelly.blockPosition().getX() + random.nextInt(WANDER_RANGE * 2) - WANDER_RANGE;
            int z = jelly.blockPosition().getZ() + random.nextInt(WANDER_RANGE * 2) - WANDER_RANGE;
            BlockPos target = findGround(new BlockPos(x, jelly.blockPosition().getY(), z));
            if (target != null) {
                return target;
            }
        }
        return null;
    }

    private BlockPos findSafeGround() {
        for (int i = 0; i < 10; i++) {
            int x = jelly.blockPosition().getX() + random.nextInt(WANDER_RANGE * 2) - WANDER_RANGE;
            int z = jelly.blockPosition().getZ() + random.nextInt(WANDER_RANGE * 2) - WANDER_RANGE;
            BlockPos target = findGround(new BlockPos(x, jelly.blockPosition().getY(), z));
            if (target != null && !level.getFluidState(target).is(FluidTags.WATER)) {
                return target;
            }
        }
        return null;
    }

    private BlockPos findGround(BlockPos startPos) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(startPos.getX(), startPos.getY(), startPos.getZ());
        // Scan down
        for (int y = 0; y < 10; y++) {
            if (level.getBlockState(mutable).isSolid() && level.getBlockState(mutable.above()).isAir()) {
                return mutable.immutable().above();
            }
            mutable.move(0, -1, 0);
        }
        // Reset and scan up
        mutable.set(startPos);
        for (int y = 0; y < 10; y++) {
            if (level.getBlockState(mutable).isSolid() && level.getBlockState(mutable.above()).isAir()) {
                 return mutable.immutable().above();
            }
            mutable.move(0, 1, 0);
        }
        return null;
    }
}
