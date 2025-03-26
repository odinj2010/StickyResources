package net.nfgbros.stickyresources.util;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.Random;

public class Magnetism {
    private static final double ATTRACTION_FORCE = 0.05; // Adjust as needed
    private static final double REPULSION_FORCE = 0.1;  // Adjust as needed
    private static final double IRON_ABOVE_PULL_FORCE = 0.1; // Adjust this to control the pull strength
    private static final int SEARCH_COOLDOWN_TICKS = 20;

    private final JellyEntity jelly;
    private final Level level;
    private final Random random = new Random(); // Create your own Random instance
    private int searchCooldown = 0;

    public Magnetism(JellyEntity jelly) {
        this.jelly = jelly;
        this.level = jelly.level();
    }

    public void tick() {
        if (!level.isClientSide) {
            metalDetectionTick();
            magneticTick();
        }
    }

    private void metalDetectionTick() {
        if (searchCooldown > 0) {
            searchCooldown--;
            return;
        }

        int range = 4;
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos offsetPos = jelly.getOnPos().offset(x, y, z);
                    BlockState state = level.getBlockState(offsetPos);
                    if (isValuableBlock(state)) {
                        level.playSound(null, jelly.getX(), jelly.getY(), jelly.getZ(),
                                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.AMBIENT, 1f, 1f);
                        level.addParticle(ParticleTypes.HAPPY_VILLAGER,
                                offsetPos.getX() + random.nextDouble(), // Use your own Random instance
                                offsetPos.getY() + random.nextDouble(),
                                offsetPos.getZ() + random.nextDouble(),
                                0, 0, 0);
                        searchCooldown = SEARCH_COOLDOWN_TICKS;
                        return;
                    }
                }
            }
        }
    }

    private void magneticTick() {
        // Attraction to metal items
        level.getEntitiesOfClass(ItemEntity.class, jelly.getBoundingBox().inflate(5), entity -> entity instanceof ItemEntity).forEach(entity -> {
            ItemEntity itemEntity = (ItemEntity) entity;
            if (itemEntity.getItem().is(ModTags.Items.STICKY_ITEMS)) {
                Vec3 direction = jelly.position().subtract(entity.position()).normalize();
                jelly.setDeltaMovement(jelly.getDeltaMovement().add(direction.scale(ATTRACTION_FORCE)));
            }
        });

        // Repulsion and levitation from iron blocks
        int entityX = (int) Math.floor(jelly.getX());
        int entityY = (int) Math.floor(jelly.getY() - 0.5);
        int entityZ = (int) Math.floor(jelly.getZ());
        int checkRadius = 1;

        boolean ironBlockFoundBelow = false;
        boolean ironBlockFoundAbove = false;

        // Check for iron blocks below
        for (int x = -checkRadius; x <= checkRadius; x++) {
            for (int z = -checkRadius; z <= checkRadius; z++) {
                BlockPos checkPos = new BlockPos(entityX + x, entityY, entityZ + z);
                BlockState state = level.getBlockState(checkPos);
                if (state.is(Blocks.IRON_BLOCK)) {
                    ironBlockFoundBelow = true;
                    break;
                }
            }
            if (ironBlockFoundBelow) {
                break;
            }
        }

        // Check for iron blocks above
        for (int x = -checkRadius; x <= checkRadius; x++) {
            for (int z = -checkRadius; z <= checkRadius; z++) {
                BlockPos checkPos = new BlockPos(entityX + x, entityY + 2, entityZ + z); // Checking 2 blocks above
                BlockState state = level.getBlockState(checkPos);
                if (state.is(Blocks.IRON_BLOCK)) {
                    ironBlockFoundAbove = true;
                    break;
                }
            }
            if (ironBlockFoundAbove) {
                break;
            }
        }

        Vec3 currentMovement = jelly.getDeltaMovement();
        Vec3 newMovement = currentMovement;

        if (ironBlockFoundBelow) {
            // Levitation - consistent upward force
            newMovement = newMovement.add(0, 0.1, 0); // Adjust this value for hover height

            // Repulsion - reduced strength
            Vec3 repulsionDirection = new Vec3(0, 1, 0);
            double totalRepulsion = 0;
            for (int x = -checkRadius; x <= checkRadius; x++) {
                for (int z = -checkRadius; z <= checkRadius; z++) {
                    BlockPos checkPos = new BlockPos(entityX + x, entityY, entityZ + z);
                    BlockState state = level.getBlockState(checkPos);
                    if (state.is(Blocks.IRON_BLOCK)) {
                        Vec3 blockRepulsion = jelly.position().subtract(Vec3.atCenterOf(checkPos)).normalize();
                        repulsionDirection = repulsionDirection.add(blockRepulsion).normalize();
                        totalRepulsion += 1;
                    }
                }
            }
            newMovement = newMovement.add(repulsionDirection.scale(REPULSION_FORCE / totalRepulsion * 0.25)); // Reduced repulsion

            // Apply strong damping to horizontal movement
            newMovement = new Vec3(newMovement.x() * 1.1, newMovement.y(), newMovement.z() * 1.1);

        } else {
            // Apply damping even when not over iron to stop residual movement
            newMovement = new Vec3(newMovement.x() * 0.7, newMovement.y(), newMovement.z() * 0.7);
        }

        if (ironBlockFoundAbove) {
            // Consistent downward force to pull towards iron above
            newMovement = newMovement.add(0, -IRON_ABOVE_PULL_FORCE, 0);
        }

        // Damping for vertical movement
        newMovement = new Vec3(newMovement.x(), newMovement.y() * 0.8, newMovement.z()); // Vertical damping

        jelly.setDeltaMovement(newMovement);

        // Constant gravity (if you still want some gravity influence)
        jelly.setDeltaMovement(jelly.getDeltaMovement().add(0, -0.008, 0));
    }

    private boolean isValuableBlock(BlockState state) {
        return state.is(ModTags.Blocks.METAL_DETECTOR_VALUABLES);
    }
}
