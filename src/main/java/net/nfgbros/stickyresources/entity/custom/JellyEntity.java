package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.ai.goal.JellyGrazeGoal;
import net.nfgbros.stickyresources.entity.ai.goal.JellySwarmGoal;
import net.nfgbros.stickyresources.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class JellyEntity extends Animal {
    private int dropItemTickCounter = 0;
    private int absorbItemTickCounter = 0;
    private int oakSaplingGrazedCount = 0;
    private int skeletonSkullGrazedCount = 0;
    private final Map<ModEntities.JellyType, Integer> absorptionCount = new HashMap<>();

    public void incrementOakSaplingGrazedCount() {
        oakSaplingGrazedCount++;
    }

    public int getOakSaplingGrazedCount() {
        return oakSaplingGrazedCount;
    }

    public void incrementskeletonSkullGrazedCount() {
        skeletonSkullGrazedCount++;
    }

    public int getskeletonSkullGrazedCount() {
        return skeletonSkullGrazedCount;
    }
    public void incrementredMushroomGrazedCount() {
        skeletonSkullGrazedCount++;
    }

    public int getredMushroomGrazedCount() {
        return skeletonSkullGrazedCount;
    }

    private JellyEntity swarmLeader; // Store the leader

    public JellyEntity getSwarmLeader() {
        return swarmLeader;
    }

    public void setSwarmLeader(JellyEntity leader) {
        this.swarmLeader = leader;
    }


    public JellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }
    public ModEntities.JellyType getJellyType() {
        return ModEntities.JELLY_ENTITIES.entrySet().stream()
                .filter(entry -> entry.getValue().get() == this.getType())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(ModEntities.JellyType.DEFAULT);
    }

    public static AttributeSupplier.Builder createAttributes(ModEntities.JellyType type) {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        int nearbyJellies = countNearbyJellies();

        if (nearbyJellies >= 5 && StickyResourcesConfig.JELLY_SWARMS_ACTIVE.get()) {
            this.goalSelector.addGoal(2, new JellyGrazeGoal(this));
            this.goalSelector.addGoal(3, new BreedGoal(this, 1.15D));
            this.goalSelector.addGoal(4, new TemptGoal(this, 1.15D, Ingredient.of(Items.SLIME_BALL), false));
            this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
            this.goalSelector.addGoal(6, new JellySwarmGoal(this, 1.2D));
            this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 2.0F));
            this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0D));
            this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        } else {
            // Individual behavior:
            this.goalSelector.addGoal(2, new JellyGrazeGoal(this));
            this.goalSelector.addGoal(3, new BreedGoal(this, 1.15D));
            this.goalSelector.addGoal(4, new TemptGoal(this, 1.15D, Ingredient.of(Items.SLIME_BALL), false));
            this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
            this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 2.0F));
            this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
            this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SLIME_HURT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH;
    }
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        // Cast the other parent to JellyEntity
        JellyEntity other = (JellyEntity) otherParent;
        // If parents are a water jelly and a lava jelly, produce an obsidian jelly baby.
        if (this.getJellyType() == ModEntities.JellyType.WATER && other.getJellyType() == ModEntities.JellyType.LAVA ||
                this.getJellyType() == ModEntities.JellyType.LAVA && other.getJellyType() == ModEntities.JellyType.WATER) {
            return ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.OBSIDIAN).get().create(level);        }
        // If parents are a sand jelly and a lava jelly, produce an obsidian jelly baby.
        else if (this.getJellyType() == ModEntities.JellyType.SAND && other.getJellyType() == ModEntities.JellyType.LAVA ||
                this.getJellyType() == ModEntities.JellyType.LAVA && other.getJellyType() == ModEntities.JellyType.SAND) {
            return ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.GLASS).get().create(level);
        }
        // Otherwise, default to producing offspring of this parent's type.
        return ModEntities.JELLY_ENTITIES.get(this.getJellyType()).get().create(level);
    }
    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SLIME_BALL);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // Check if the damage source is lightning or shock damage
        if (source.getMsgId().equals("lightningBolt")) {
            transformIntoElectricJelly();
            return true; // Entity has been transformed
        }
        else if (source.is(DamageTypes.ON_FIRE)) {
            if (!this.isRemoved()) { // Check if the entity is not already removed
                transformIntoFireJelly(); // Transform the entity into "XXXX" Jelly
            }
            return false; // Prevent the entity from taking damage or dying
        }
        else if (source.is(DamageTypes.DROWN)) {
            if (!this.isRemoved()) { // Check if the entity is not already removed
                transformIntoWaterJelly(); // Transform the entity into "XXXX" Jelly
            }
            return false; // Prevent the entity from taking damage or dying
        }
        else if (source.is(DamageTypes.LAVA)) {
            if (!this.isRemoved()) { // Check if the entity is not already removed
                transformIntoLavaJelly(); // Transform the entity into "XXXX" Jelly
            }
            return false; // Prevent the entity from taking damage or dying
        }
        return super.hurt(source, amount);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isBaby()) return;

        dropItemTickCounter++;
        if (dropItemTickCounter >= 1200) {
            dropJellyItem();
            dropItemTickCounter = 0;
        }
        absorbItemTickCounter++;
        if (absorbItemTickCounter >= 20) {
            absorbNearbyItems();
        }

        // 🛠 Check if Jelly needs to switch to swarm behavior dynamically
        if (StickyResourcesConfig.JELLY_SWARMS_ACTIVE.get() == true) {

            int nearbyJellies = countNearbyJellies();
            boolean isSwarming = this.goalSelector.getRunningGoals()
                    .anyMatch(goal -> goal.getGoal() instanceof JellySwarmGoal);

            if (nearbyJellies >= 5 && !isSwarming) {
                this.goalSelector.addGoal(2, new JellySwarmGoal(this, 1.2D));
            } else if (nearbyJellies < 5 && isSwarming) {
                this.goalSelector.removeGoal(new JellySwarmGoal(this, 1.2D)); // Remove swarm goal
            }
        };
    }
    // Transform Jelly into Electric Jelly when struck by lightning
    private void transformIntoElectricJelly() {
        if (!level().isClientSide) {
            this.discard();  // Remove the current Jelly entity
            JellyEntity electricJelly = ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.ELECTRIC).get().create((ServerLevel) this.level());
            if (electricJelly != null) {
                electricJelly.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                this.level().addFreshEntity(electricJelly);  // Spawn in the new Electric Jelly
            }
        }
    }
    private void transformIntoFireJelly() {
        if (!level().isClientSide) {
            this.discard();  // Remove the current Jelly entity
            JellyEntity fireJelly = ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.FIRE).get().create((ServerLevel) this.level());
            if (fireJelly != null) {
                fireJelly.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                this.level().addFreshEntity(fireJelly);  // Spawn in the new Electric Jelly
            }
        }
    }
    private void transformIntoLavaJelly() {
        if (!level().isClientSide) {
            this.discard();  // Remove the current Jelly entity
            JellyEntity lavaJelly = ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.LAVA).get().create((ServerLevel) this.level());
            if (lavaJelly != null) {
                lavaJelly.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                this.level().addFreshEntity(lavaJelly);  // Spawn in the new Electric Jelly
            }
        }
    }
    private void transformIntoWaterJelly() {
        if (!level().isClientSide) {
            this.discard();  // Remove the current Jelly entity
            JellyEntity waterJelly = ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.WATER).get().create((ServerLevel) this.level());
            if (waterJelly != null) {
                waterJelly.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                this.level().addFreshEntity(waterJelly);  // Spawn in the new Electric Jelly
            }
        }
    }
    private void dropJellyItem() {
        Level world = this.level();
        ItemStack dropStack = ItemStack.EMPTY;
        ModEntities.JellyType type = this.getJellyType();

        if (type == ModEntities.JellyType.AMETHYST) {
            dropStack = new ItemStack(ModItems.STICKY_AMETHYST.get());
        }
        else if (type == ModEntities.JellyType.BONE) {
            dropStack = new ItemStack(ModItems.STICKY_BONE_MEAL.get());
        }
        else if (type == ModEntities.JellyType.CHARCOAL) {
            dropStack = new ItemStack(ModItems.STICKY_CHARCOAL.get());
        }
        else if (type == ModEntities.JellyType.COAL) {
            dropStack = new ItemStack(ModItems.STICKY_COAL.get());
        }
        else if (type == ModEntities.JellyType.COBBLESTONE) {
            dropStack = new ItemStack(ModBlocks.STICKY_COBBLESTONE.get());
        }
        else if (type == ModEntities.JellyType.DEFAULT) {
            dropStack = new ItemStack(Items.SLIME_BALL);
        }
        else if (type == ModEntities.JellyType.DIAMOND) {
            dropStack = new ItemStack(ModItems.STICKY_DIAMOND.get());
        }
        else if (type == ModEntities.JellyType.DIRT) {
            dropStack = new ItemStack(ModBlocks.STICKY_DIRT.get());
        }
        else if (type == ModEntities.JellyType.EMERALD) {
            dropStack = new ItemStack(ModItems.STICKY_EMERALD.get());
        }
        else if (type == ModEntities.JellyType.ENDERPEARL) {
            dropStack = new ItemStack(ModItems.STICKY_ENDER_PEARL.get());
        }
        else if (type == ModEntities.JellyType.GLASS) {
            dropStack = new ItemStack(ModBlocks.STICKY_GLASS.get());
        }
        else if (type == ModEntities.JellyType.GRAVEL) {
            dropStack = new ItemStack(ModBlocks.STICKY_GRAVEL.get());
        }
        else if (type == ModEntities.JellyType.LAPIS) {
            dropStack = new ItemStack(ModItems.STICKY_LAPIS_LAZULI.get());
        }
        else if (type == ModEntities.JellyType.LOGOAK) {
            dropStack = new ItemStack(ModBlocks.STICKY_LOG_OAK.get());
        }
        else if (type == ModEntities.JellyType.OBSIDIAN) {
            dropStack = new ItemStack(ModBlocks.STICKY_OBSIDIAN.get());
        }
        else if (type == ModEntities.JellyType.PRISMERINE) {
            dropStack = new ItemStack(ModItems.STICKY_PRISMERINE_CRYSTALS.get());
        }
        else if (type == ModEntities.JellyType.RAWCOPPER) {
            dropStack = new ItemStack(ModItems.STICKY_RAW_COPPER.get());
        }
        else if (type == ModEntities.JellyType.RAWGOLD) {
            dropStack = new ItemStack(ModItems.STICKY_RAW_GOLD.get());
        }
        else if (type == ModEntities.JellyType.RAWIRON) {
            dropStack = new ItemStack(ModItems.STICKY_RAW_IRON.get());
        }
        else if (type == ModEntities.JellyType.REDSTONEDUST) {
            dropStack = new ItemStack(ModItems.STICKY_REDSTONE_DUST.get());
        }
        else if (type == ModEntities.JellyType.REDMUSHROOM) {
            dropStack = new ItemStack(ModItems.STICKY_RED_MUSHROOM.get());
        }
        else if (type == ModEntities.JellyType.SAND) {
            dropStack = new ItemStack(ModBlocks.STICKY_SAND.get());
        }
        else if (type == ModEntities.JellyType.RAWSAPPHIRE) {
            dropStack = new ItemStack(ModItems.STICKY_RAW_SAPPHIRE.get());
        }
        else if (type == ModEntities.JellyType.STONE) {
            dropStack = new ItemStack(ModBlocks.STICKY_STONE.get());
        }
        if (!dropStack.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), dropStack);
            world.addFreshEntity(itemEntity);
        }
    }

    private void absorbNearbyItems() {
        Level world = this.level();
        ModEntities.JellyType jellyType = this.getJellyType();
        // Absorption check for stone Jelly (only absorbs Raw Iron)
        if (jellyType == ModEntities.JellyType.STONE) {
            world.getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(0.2D)).forEach(itemEntity -> {
                ItemStack stack = itemEntity.getItem();

                // Absorb Coal
                if (stack.getItem() == Items.COAL) {
                    absorptionCount.put(ModEntities.JellyType.COAL, absorptionCount.getOrDefault(ModEntities.JellyType.COAL, 0) + stack.getCount());
                    itemEntity.discard();
                    // Check if the absorption threshold is met
                    if (absorptionCount.get(ModEntities.JellyType.COAL) >= 64) {
                        transformInto(ModEntities.JellyType.COAL);
                    }
                }
                else if (stack.getItem() == Items.RAW_COPPER) {
                    absorptionCount.put(ModEntities.JellyType.RAWCOPPER, absorptionCount.getOrDefault(ModEntities.JellyType.RAWCOPPER, 0) + stack.getCount());
                    itemEntity.discard();
                    // Check if the absorption threshold is met (e.g., 20 items)
                    if (absorptionCount.get(ModEntities.JellyType.RAWCOPPER) >= 64) {
                        transformInto(ModEntities.JellyType.RAWCOPPER);
                    }
                }
                else if (stack.getItem() == Items.RAW_GOLD) {
                    absorptionCount.put(ModEntities.JellyType.RAWGOLD, absorptionCount.getOrDefault(ModEntities.JellyType.RAWGOLD, 0) + stack.getCount());
                    itemEntity.discard();
                    // Check if the absorption threshold is met (e.g., 20 items)
                    if (absorptionCount.get(ModEntities.JellyType.RAWGOLD) >= 64) {
                        transformInto(ModEntities.JellyType.RAWGOLD);
                    }
                }
                else if (stack.getItem() == Items.RAW_IRON) {
                    absorptionCount.put(ModEntities.JellyType.RAWIRON, absorptionCount.getOrDefault(ModEntities.JellyType.RAWIRON, 0) + stack.getCount());
                    itemEntity.discard();
                    // Check if the absorption threshold is met (e.g., 20 items)
                    if (absorptionCount.get(ModEntities.JellyType.RAWIRON) >= 64) {
                        transformInto(ModEntities.JellyType.RAWIRON);
                    }
                }
                // Absorb Lapis
                else if (stack.getItem() == Items.LAPIS_LAZULI) {
                    absorptionCount.put(ModEntities.JellyType.LAPIS, absorptionCount.getOrDefault(ModEntities.JellyType.LAPIS, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.LAPIS) >= 64) {
                        transformInto(ModEntities.JellyType.LAPIS);
                    }
                }
                // Absorb Redstone
                else if (stack.getItem() == Items.REDSTONE) {
                    absorptionCount.put(ModEntities.JellyType.REDSTONEDUST, absorptionCount.getOrDefault(ModEntities.JellyType.REDSTONEDUST, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.REDSTONEDUST) >= 64) {
                        transformInto(ModEntities.JellyType.REDSTONEDUST);
                    }
                }
                else if (stack.getItem() == Items.EMERALD) {
                    absorptionCount.put(ModEntities.JellyType.EMERALD, absorptionCount.getOrDefault(ModEntities.JellyType.EMERALD, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.EMERALD) >= 64) {
                        transformInto(ModEntities.JellyType.EMERALD);
                    }
                }
                // Absorb Sapphire (if custom item exists)
                else if (stack.getItem() == ModItems.SAPPHIRE.get()) {
                    absorptionCount.put(ModEntities.JellyType.RAWSAPPHIRE, absorptionCount.getOrDefault(ModEntities.JellyType.RAWSAPPHIRE, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.RAWSAPPHIRE) >= 64) {
                        transformInto(ModEntities.JellyType.RAWSAPPHIRE);
                    }
                }
                else if (stack.getItem() == Items.DIAMOND) {
                    absorptionCount.put(ModEntities.JellyType.DIAMOND, absorptionCount.getOrDefault(ModEntities.JellyType.DIAMOND, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.DIAMOND) >= 64) {
                        transformInto(ModEntities.JellyType.DIAMOND);
                    }
                }
            });
        }
        // Absorption check for Default Jelly (absorbs the following items)
        if (jellyType == ModEntities.JellyType.DEFAULT) {
            world.getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(0.2D)).forEach(itemEntity -> {
                ItemStack stack = itemEntity.getItem();
                // Check if the absorbed item is Cobblestone, and if so, transform the jelly
                if (stack.getItem() == Items.COBBLESTONE) {
                    absorptionCount.put(ModEntities.JellyType.COBBLESTONE, absorptionCount.getOrDefault(ModEntities.JellyType.COBBLESTONE, 0) + stack.getCount());
                    itemEntity.discard();
                    // Check if the absorption threshold is met (e.g., 20 items)
                    if (absorptionCount.get(ModEntities.JellyType.COBBLESTONE) >= 256) {
                        transformInto(ModEntities.JellyType.COBBLESTONE);
                    }
                }
                // Absorb Dirt
                else if (stack.getItem() == Items.DIRT) {
                    absorptionCount.put(ModEntities.JellyType.DIRT, absorptionCount.getOrDefault(ModEntities.JellyType.DIRT, 0) + stack.getCount());
                    itemEntity.discard();
                    // Check if the absorption threshold is met (e.g., 2 items)
                    if (absorptionCount.get(ModEntities.JellyType.DIRT) >= 128) {
                        transformInto(ModEntities.JellyType.DIRT);
                    }
                }
                // Absorb Gravel
                else if (stack.getItem() == Items.GRAVEL) {
                    absorptionCount.put(ModEntities.JellyType.GRAVEL, absorptionCount.getOrDefault(ModEntities.JellyType.GRAVEL, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.GRAVEL) >= 128) {
                        transformInto(ModEntities.JellyType.GRAVEL);
                    }
                }
                // Absorb Sand
                else if (stack.getItem() == Items.SAND) {
                    absorptionCount.put(ModEntities.JellyType.SAND, absorptionCount.getOrDefault(ModEntities.JellyType.SAND, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.SAND) >= 128) {
                        transformInto(ModEntities.JellyType.SAND);
                    }
                }
                // Absorb Red Mushrooms
                else if (stack.getItem() == Items.RED_MUSHROOM) {
                    absorptionCount.put(ModEntities.JellyType.REDMUSHROOM, absorptionCount.getOrDefault(ModEntities.JellyType.REDMUSHROOM, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.REDMUSHROOM) >= 128) {
                        transformInto(ModEntities.JellyType.REDMUSHROOM);
                    }
                }
                else if (stack.getItem() == Items.ENDER_PEARL) {
                    absorptionCount.put(ModEntities.JellyType.ENDERPEARL, absorptionCount.getOrDefault(ModEntities.JellyType.ENDERPEARL, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.ENDERPEARL) >= 64) {
                        transformInto(ModEntities.JellyType.ENDERPEARL);
                    }
                }
                else if (stack.getItem() == Items.AMETHYST_SHARD) {
                    absorptionCount.put(ModEntities.JellyType.AMETHYST, absorptionCount.getOrDefault(ModEntities.JellyType.AMETHYST, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.AMETHYST) >= 64) {
                        transformInto(ModEntities.JellyType.AMETHYST);
                    }
                }
                else if (stack.getItem() == Items.PRISMARINE_SHARD) {
                    absorptionCount.put(ModEntities.JellyType.PRISMERINE, absorptionCount.getOrDefault(ModEntities.JellyType.PRISMERINE, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.PRISMERINE) >= 64) {
                        transformInto(ModEntities.JellyType.PRISMERINE);
                    }
                }
            });
        }
    }
    public void transformInto(ModEntities.JellyType newType) {
        if (!level().isClientSide) {
            this.discard();
            JellyEntity newJelly = ModEntities.JELLY_ENTITIES.get(newType).get().create((ServerLevel) this.level());
            if (newJelly != null) {
                newJelly.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                this.level().addFreshEntity(newJelly);
            }
        }
    }

    public int countNearbyJellies() {
        final int[] count = {0};  // Use an array to allow modification inside lambda
        this.level().getEntitiesOfClass(JellyEntity.class, this.getBoundingBox().inflate(10.0D))  // Adjust range as needed
                .forEach(otherJelly -> {
                    if (otherJelly != this) {
                        count[0]++;
                    }
                });
        return count[0];
    }
}