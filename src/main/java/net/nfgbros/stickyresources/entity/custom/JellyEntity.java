package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
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
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class JellyEntity extends Animal {
    private int dropItemTickCounter = 0;
    private int absorbItemTickCounter = 0;
    private final Map<ModEntities.JellyType, Integer> absorptionCount = new HashMap<>();

    public JellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes(ModEntities.JellyType type) {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    public ModEntities.JellyType getJellyType() {
        return ModEntities.JELLY_ENTITIES.entrySet().stream()
                .filter(entry -> entry.getValue().get() == this.getType())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(ModEntities.JellyType.DEFAULT);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.SLIME_BALL), false));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new FollowParentGoal(this, 1.1D));
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
        return super.hurt(source, amount);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isBaby()) return;

        dropItemTickCounter++;
        if (dropItemTickCounter >= 200) {
            dropJellyItem();
            dropItemTickCounter = 0;
        }
        absorbItemTickCounter++;
        if (absorbItemTickCounter >= 20) {
            absorbNearbyItems();
        }
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

    private void dropJellyItem() {
        Level world = this.level();
        ItemStack dropStack = ItemStack.EMPTY;
        ModEntities.JellyType type = this.getJellyType();
        if (type == ModEntities.JellyType.DEFAULT) {
            dropStack = new ItemStack(Items.SLIME_BALL);
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
        else if (type == ModEntities.JellyType.OAKLOG) {
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
        else if (type == ModEntities.JellyType.SAND) {
            dropStack = new ItemStack(ModBlocks.STICKY_SAND.get());
        }
        else if (type == ModEntities.JellyType.SAPPHIRE) {
            dropStack = new ItemStack(ModItems.STICKY_RAW_SAPPHIRE.get());
        }
        if (!dropStack.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), dropStack);
            world.addFreshEntity(itemEntity);
        }
    }

    private void absorbNearbyItems() {
        Level world = this.level();
        ModEntities.JellyType jellyType = this.getJellyType();

        // Absorption check for Cobblestone Jelly (only absorbs Raw Iron)
        if (jellyType == ModEntities.JellyType.COBBLESTONE) {
            world.getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(0.2D)).forEach(itemEntity -> {
                ItemStack stack = itemEntity.getItem();
                // Check if the absorbed item is Raw Iron, and if so, transform the jelly
                if (stack.getItem() == Items.RAW_IRON) {
                    absorptionCount.put(ModEntities.JellyType.RAWIRON, absorptionCount.getOrDefault(ModEntities.JellyType.RAWIRON, 0) + stack.getCount());
                    itemEntity.discard();
                    // Check if the absorption threshold is met (e.g., 20 items)
                    if (absorptionCount.get(ModEntities.JellyType.RAWIRON) >= 2) {
                        transformInto(ModEntities.JellyType.RAWIRON);
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
                    if (absorptionCount.get(ModEntities.JellyType.COBBLESTONE) >= 2) {
                        transformInto(ModEntities.JellyType.COBBLESTONE);
                    }
                }
                // Absorb Dirt
                else if (stack.getItem() == Items.DIRT) {
                    absorptionCount.put(ModEntities.JellyType.DIRT, absorptionCount.getOrDefault(ModEntities.JellyType.DIRT, 0) + stack.getCount());
                    itemEntity.discard();
                    // Check if the absorption threshold is met (e.g., 2 items)
                    if (absorptionCount.get(ModEntities.JellyType.DIRT) >= 2) {
                        transformInto(ModEntities.JellyType.DIRT);
                    }
                }
                // Absorb Bone
                else if (stack.getItem() == Items.BONE) {
                    absorptionCount.put(ModEntities.JellyType.BONE, absorptionCount.getOrDefault(ModEntities.JellyType.BONE, 0) + stack.getCount());
                    itemEntity.discard();
                    // Check if the absorption threshold is met (e.g., 2 items)
                    if (absorptionCount.get(ModEntities.JellyType.BONE) >= 2) {
                        transformInto(ModEntities.JellyType.BONE);
                    }
                }
                // Absorb Coal
                else if (stack.getItem() == Items.COAL) {
                    absorptionCount.put(ModEntities.JellyType.COAL, absorptionCount.getOrDefault(ModEntities.JellyType.COAL, 0) + stack.getCount());
                    itemEntity.discard();
                    // Check if the absorption threshold is met
                    if (absorptionCount.get(ModEntities.JellyType.COAL) >= 2) {
                        transformInto(ModEntities.JellyType.COAL);
                    }
                }
                // Absorb Charcoal
                else if (stack.getItem() == Items.CHARCOAL) {
                    absorptionCount.put(ModEntities.JellyType.CHARCOAL, absorptionCount.getOrDefault(ModEntities.JellyType.CHARCOAL, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.CHARCOAL) >= 2) {
                        transformInto(ModEntities.JellyType.CHARCOAL);
                    }
                }
                // Absorb Emerald
                else if (stack.getItem() == Items.EMERALD) {
                    absorptionCount.put(ModEntities.JellyType.EMERALD, absorptionCount.getOrDefault(ModEntities.JellyType.EMERALD, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.EMERALD) >= 2) {
                        transformInto(ModEntities.JellyType.EMERALD);
                    }
                }
                // Absorb Ender Pearl
                else if (stack.getItem() == Items.ENDER_PEARL) {
                    absorptionCount.put(ModEntities.JellyType.ENDERPEARL, absorptionCount.getOrDefault(ModEntities.JellyType.ENDERPEARL, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.ENDERPEARL) >= 2) {
                        transformInto(ModEntities.JellyType.ENDERPEARL);
                    }
                }
                // Absorb Glass
                else if (stack.getItem() == Items.GLASS) {
                    absorptionCount.put(ModEntities.JellyType.GLASS, absorptionCount.getOrDefault(ModEntities.JellyType.GLASS, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.GLASS) >= 2) {
                        transformInto(ModEntities.JellyType.GLASS);
                    }
                }
                // Absorb Gravel
                else if (stack.getItem() == Items.GRAVEL) {
                    absorptionCount.put(ModEntities.JellyType.GRAVEL, absorptionCount.getOrDefault(ModEntities.JellyType.GRAVEL, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.GRAVEL) >= 2) {
                        transformInto(ModEntities.JellyType.GRAVEL);
                    }
                }
                // Absorb Lapis
                else if (stack.getItem() == Items.LAPIS_LAZULI) {
                    absorptionCount.put(ModEntities.JellyType.LAPIS, absorptionCount.getOrDefault(ModEntities.JellyType.LAPIS, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.LAPIS) >= 2) {
                        transformInto(ModEntities.JellyType.LAPIS);
                    }
                }
                // Absorb Obsidian
                else if (stack.getItem() == Items.OBSIDIAN) {
                    absorptionCount.put(ModEntities.JellyType.OBSIDIAN, absorptionCount.getOrDefault(ModEntities.JellyType.OBSIDIAN, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.OBSIDIAN) >= 2) {
                        transformInto(ModEntities.JellyType.OBSIDIAN);
                    }
                }
                // Absorb Redstone
                else if (stack.getItem() == Items.REDSTONE) {
                    absorptionCount.put(ModEntities.JellyType.REDSTONEDUST, absorptionCount.getOrDefault(ModEntities.JellyType.REDSTONEDUST, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.REDSTONEDUST) >= 2) {
                        transformInto(ModEntities.JellyType.REDSTONEDUST);
                    }
                }
                // Absorb Sand
                else if (stack.getItem() == Items.SAND) {
                    absorptionCount.put(ModEntities.JellyType.SAND, absorptionCount.getOrDefault(ModEntities.JellyType.SAND, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.SAND) >= 2) {
                        transformInto(ModEntities.JellyType.SAND);
                    }
                }
                // Absorb Sapphire (if custom item exists)
                else if (stack.getItem() == ModItems.SAPPHIRE.get()) {
                    absorptionCount.put(ModEntities.JellyType.SAPPHIRE, absorptionCount.getOrDefault(ModEntities.JellyType.SAPPHIRE, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.SAPPHIRE) >= 2) {
                        transformInto(ModEntities.JellyType.SAPPHIRE);
                    }
                }
                // Absorb Water Bucket
                else if (stack.getItem() == Items.WATER_BUCKET) {
                    absorptionCount.put(ModEntities.JellyType.WATER, absorptionCount.getOrDefault(ModEntities.JellyType.WATER, 0) + stack.getCount());
                    itemEntity.discard();
                    if (absorptionCount.get(ModEntities.JellyType.WATER) >= 2) {
                        transformInto(ModEntities.JellyType.WATER);
                    }
                }
            });
        }
    }


    private void transformInto(ModEntities.JellyType newType) {
        if (!level().isClientSide) {
            this.discard();
            JellyEntity newJelly = ModEntities.JELLY_ENTITIES.get(newType).get().create((ServerLevel) this.level());
            if (newJelly != null) {
                newJelly.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                this.level().addFreshEntity(newJelly);
            }
        }
    }
}
