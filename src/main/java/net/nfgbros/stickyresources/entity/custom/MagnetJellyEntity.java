package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.nfgbros.stickyresources.util.ModTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;

public class MagnetJellyEntity extends JellyEntity {

    private static final double ATTRACTION_FORCE = 0.05; // Adjust as needed
    private static final double REPULSION_FORCE = 0.1;  // Adjust as needed
    private static final double LEVITATION_DISTANCE = 0.5; // How high above iron blocks
    private static final double MAX_VERTICAL_VELOCITY = 0.3; // Max upward velocity to prevent fast levitation
    private int searchCooldown = 0;
    private static final int SEARCH_COOLDOWN_TICKS = 20;

    public MagnetJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
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
                    BlockPos offsetPos = this.getOnPos().offset(x, y, z);
                    BlockState state = level().getBlockState(offsetPos);
                    if (isValuableBlock(state)) {
                        level().playSound(null, this.getX(), this.getY(), this.getZ(),
                                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.AMBIENT, 1f, 1f);
                        level().addParticle(ParticleTypes.HAPPY_VILLAGER,
                                offsetPos.getX() + random.nextDouble(),
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
        this.level().getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(5), entity -> entity instanceof ItemEntity).forEach(entity -> {
            ItemEntity itemEntity = (ItemEntity) entity;
            if (itemEntity.getItem().is(ModTags.Items.STICKY_ITEMS)) {
                Vec3 direction = this.position().subtract(entity.position()).normalize();
                this.setDeltaMovement(this.getDeltaMovement().add(direction.scale(ATTRACTION_FORCE)));
            }
        });

        // Attraction to other MagnetJelly entities
        this.level().getEntitiesOfClass(MagnetJellyEntity.class, this.getBoundingBox().inflate(5), entity -> entity instanceof MagnetJellyEntity).forEach(entity -> {
            if (entity != this) { // Don't attract itself
                // Calculate the direction from the current entity to the other Magnet Jelly
                Vec3 direction = entity.position().subtract(this.position()).normalize();
                this.setDeltaMovement(this.getDeltaMovement().add(direction.scale(ATTRACTION_FORCE)));
            }
        });

        // Repulsion and levitation from iron blocks
        int entityX = (int) Math.floor(this.getX());
        int entityY = (int) Math.floor(this.getY() - 0.5);
        int entityZ = (int) Math.floor(this.getZ());
        int checkRadius = 1;

        boolean ironBlockFound = false;
        for (int x = -checkRadius; x <= checkRadius; x++) {
            for (int z = -checkRadius; z <= checkRadius; z++) {
                BlockPos checkPos = new BlockPos(entityX + x, entityY, entityZ + z);
                BlockState state = level().getBlockState(checkPos);
                if (state.is(Blocks.IRON_BLOCK)) {
                    ironBlockFound = true;
                    break;
                }
            }
            if (ironBlockFound) {
                break;
            }
        }

        Vec3 currentMovement = this.getDeltaMovement();
        Vec3 newMovement = currentMovement;

        if (ironBlockFound) {
            // Levitation - consistent upward force
            newMovement = newMovement.add(0, 0.1, 0); // Adjust this value for hover height

            // Repulsion - reduced strength
            Vec3 repulsionDirection = new Vec3(0, 1, 0);
            double totalRepulsion = 0;
            for (int x = -checkRadius; x <= checkRadius; x++) {
                for (int z = -checkRadius; z <= checkRadius; z++) {
                    BlockPos checkPos = new BlockPos(entityX + x, entityY, entityZ + z);
                    BlockState state = level().getBlockState(checkPos);
                    if (state.is(Blocks.IRON_BLOCK)) {
                        Vec3 blockRepulsion = this.position().subtract(Vec3.atCenterOf(checkPos)).normalize();
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

        // Damping for vertical movement
        newMovement = new Vec3(newMovement.x(), newMovement.y() * 0.8, newMovement.z()); // Vertical damping

        this.setDeltaMovement(newMovement);

        // Constant gravity (if you still want some gravity influence)
        this.setDeltaMovement(this.getDeltaMovement().add(0, -0.008, 0));
    }


    private boolean isValuableBlock(BlockState state) {
        return state.is(ModTags.Blocks.METAL_DETECTOR_VALUABLES);
    }
}