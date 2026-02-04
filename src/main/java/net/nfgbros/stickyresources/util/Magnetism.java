package net.nfgbros.stickyresources.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.entity.custom.MagnetJellyEntity;
import net.nfgbros.stickyresources.entity.ModEntities; // Add import

import java.util.Random;

public class Magnetism {
    private static final double ATTRACTION_FORCE = 0.08;
    private static final double REPULSION_FORCE = 0.15;
    private static final int SEARCH_COOLDOWN_TICKS = 20;
    private static final int MAGNETIC_RANGE = 2;

    private final JellyEntity jelly;
    private final Level level;
    private final Random random = new Random();
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
                    BlockPos offsetPos = jelly.blockPosition().offset(x, y, z);
                    BlockState state = level.getBlockState(offsetPos);
                    if (isValuableBlock(state)) {
                        level.playSound(null, jelly.getX(), jelly.getY(), jelly.getZ(),
                                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.AMBIENT, 0.5f, 1.2f);
                        searchCooldown = SEARCH_COOLDOWN_TICKS;
                        return;
                    }
                }
            }
        }
    }

    private void magneticTick() {
        boolean isAttracting = true;
        if (jelly instanceof MagnetJellyEntity magnetJelly) {
            isAttracting = magnetJelly.isAttracting();
        }

        handleItemMagnetism(isAttracting);
        handleBlockMagnetism(isAttracting);
        handleEntityMagnetism(isAttracting);
    }

    private void handleEntityMagnetism(boolean isAttracting) {
        level.getEntitiesOfClass(JellyEntity.class, jelly.getBoundingBox().inflate(MAGNETIC_RANGE), 
            other -> other != jelly && isMetallicJelly(other)).forEach(other -> {
                Vec3 direction = jelly.position().subtract(other.position()).normalize();
                double force = isAttracting ? ATTRACTION_FORCE : -REPULSION_FORCE;
                // Apply force to BOTH entities (Newton's 3rd Lawish)
                other.setDeltaMovement(other.getDeltaMovement().add(direction.scale(force)));
                // Jelly itself is anchored by its own AI mostly, but we could pull it too
        });
    }

    private boolean isMetallicJelly(JellyEntity entity) {
        ModEntities.JellyType type = entity.getJellyType();
        return type == ModEntities.JellyType.RAWIRON || 
               type == ModEntities.JellyType.RAWGOLD || 
               type == ModEntities.JellyType.RAWCOPPER || 
               type == ModEntities.JellyType.MAGNET;
               //type == ModEntities.JellyType.IRON_GOLEM; // Fun easter egg? No, JellyType doesn't have Golem.
    }

    private void handleItemMagnetism(boolean isAttracting) {
        level.getEntitiesOfClass(ItemEntity.class, jelly.getBoundingBox().inflate(MAGNETIC_RANGE)).forEach(item -> {
            if (item.getItem().is(ModTags.Items.MAGNETIC_ITEMS)) {
                Vec3 direction = jelly.position().add(0, jelly.getBbHeight() / 2, 0).subtract(item.position()).normalize();
                double force = isAttracting ? ATTRACTION_FORCE : -REPULSION_FORCE;
                item.setDeltaMovement(item.getDeltaMovement().add(direction.scale(force)));
            }
        });
    }

    private void handleBlockMagnetism(boolean isAttracting) {
        Entity affectedEntity = jelly;
        if (jelly.getVehicle() != null) affectedEntity = jelly.getVehicle();

        int entityX = (int) Math.floor(affectedEntity.getX());
        int entityY = (int) Math.floor(affectedEntity.getY() - 0.5);
        int entityZ = (int) Math.floor(affectedEntity.getZ());
        int checkRadius = 1;

        boolean blockFoundBelow = false;
        boolean blockFoundAbove = false;

        // Search logic
        for (int x = -checkRadius; x <= checkRadius; x++) {
            for (int z = -checkRadius; z <= checkRadius; z++) {
                if (level.getBlockState(new BlockPos(entityX + x, entityY, entityZ + z)).is(ModTags.Blocks.MAGNETIC_BLOCKS)) {
                    blockFoundBelow = true;
                }
                if (level.getBlockState(new BlockPos(entityX + x, entityY + 2, entityZ + z)).is(ModTags.Blocks.MAGNETIC_BLOCKS)) {
                    blockFoundAbove = true;
                }
            }
        }

        Vec3 movement = affectedEntity.getDeltaMovement();
        
        if (isAttracting) {
            // ATTRACT MODE: Stick to surfaces
            if (blockFoundBelow) {
                movement = new Vec3(movement.x * 0.5, -0.1, movement.z * 0.5); // Snap to floor
                affectedEntity.setNoGravity(true);
            } else if (blockFoundAbove) {
                movement = new Vec3(movement.x * 0.5, 0.15, movement.z * 0.5); // Snap to ceiling
                affectedEntity.setNoGravity(true);
            } else {
                affectedEntity.setNoGravity(false);
                movement = movement.add(0, -0.04, 0); // Normal gravity
            }
        } else {
            // REPEL MODE: Floating
            if (blockFoundBelow) {
                // FORCE HOVER
                affectedEntity.setNoGravity(true); // Disable gravity so we control Y
                
                // If we are close to the block (Y difference is small), push up
                double heightAboveBlock = affectedEntity.getY() - (entityY + 1.0); // +1.0 is top of block
                
                if (heightAboveBlock < 1.5) {
                    movement = new Vec3(movement.x * 1.1, 0.1, movement.z * 1.1); // Rise
                } else {
                    movement = new Vec3(movement.x * 1.1, 0.0, movement.z * 1.1); // Hover steady
                }
            } else {
                affectedEntity.setNoGravity(false); // Fall normally if no block
                movement = new Vec3(movement.x * 0.9, movement.y - 0.04, movement.z * 0.9);
            }

            if (blockFoundAbove) {
                movement = movement.add(0, -0.2, 0); // Strong push down from ceiling
            }
        }

        affectedEntity.setDeltaMovement(movement);
    }

    private void applyAttraction(Vec3 direction, double distSq) {
        // Obsolete, merged into handleBlockMagnetism
    }

    private void applyRepulsion(Vec3 direction, double distSq) {
        // Obsolete, merged into handleBlockMagnetism
    }

    private boolean isValuableBlock(BlockState state) {
        return state.is(ModTags.Blocks.METAL_DETECTOR_VALUABLES);
    }
}
