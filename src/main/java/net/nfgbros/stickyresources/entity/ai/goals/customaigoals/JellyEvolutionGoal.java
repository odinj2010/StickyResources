package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.item.ModItems;

import java.util.HashMap;
import java.util.Map;

/**
 * A custom goal for jelly entities that handles jelly transformations based on damage and item absorption.
 *
 * @author YourName
 */
public class JellyEvolutionGoal extends Goal {

    private final JellyEntity jelly;
    private final Map<ModEntities.JellyType, Integer> absorptionCount = new HashMap<>();
    private JellyEntity jellyEntity;

    /**
     * Constructs a new instance of JellyEvolutionGoal.
     *
     * @param jelly The jelly entity that this goal is associated with.
     */
    public JellyEvolutionGoal(JellyEntity jelly) {
        this.jelly = jelly;
    }

    @Override
    public boolean canUse() {
        return true; // This goal should always be active to handle transformations
    }

    @Override
    public void tick() {
        handleDamageTransformations();
        handleItemAbsorption();
    }

    /**
     * Handles jelly transformations based on damage received.
     */
    private void handleDamageTransformations() {
        DamageSource source = jelly.getLastDamageSource();
        /// Makes sure source is not null and the jelly is in its default state
        if (source != null  && ModEntities.JellyType.DEFAULT.equals(jelly.getJellyType())) {
            if (source.is(DamageTypes.LIGHTNING_BOLT)) {
                transformJelly(ModEntities.JellyType.ELECTRIC);
            } else if (source.is(DamageTypes.ON_FIRE)) {
                transformJelly(ModEntities.JellyType.FIRE);
            } else if (source.is(DamageTypes.DROWN)) {
                transformJelly(ModEntities.JellyType.WATER);
            } else if (source.is(DamageTypes.LAVA)) {
                transformJelly(ModEntities.JellyType.LAVA);
            }
        }
    }

    /**
     * Handles jelly transformations based on items absorbed.
     */
    public void handleItemAbsorption() {
        if (jelly.tickCount % 20 == 0) { // Check every 20 ticks
            absorbNearbyItems();
        }
    }

    /**
     * Absorbs nearby items and triggers jelly transformations based on the absorbed items.
     */
    private void absorbNearbyItems() {
        Level world = jelly.level();
        ModEntities.JellyType jellyType = jelly.getJellyType();

        if (jellyType == ModEntities.JellyType.STONE) {
            world.getEntitiesOfClass(ItemEntity.class, jelly.getBoundingBox().inflate(0.2D)).forEach(this::absorbStoneJellyItems);
        } else if (jellyType == ModEntities.JellyType.DEFAULT) {
            world.getEntitiesOfClass(ItemEntity.class, jelly.getBoundingBox().inflate(0.2D)).forEach(this::absorbDefaultJellyItems);
        }
    }

    /**
     * Absorbs nearby items for stone jelly entities and triggers jelly transformations based on the absorbed items.
     *
     * @param itemEntity The item entity to absorb.
     */
    private void absorbStoneJellyItems(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        if (stack.getItem() == Items.COAL) {
            increaseAbsorptionCount(ModEntities.JellyType.COAL, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.COAL) >= 64) {
                transformJelly(ModEntities.JellyType.COAL);
            }
        } else if (stack.getItem() == Items.RAW_COPPER) {
            increaseAbsorptionCount(ModEntities.JellyType.RAWCOPPER, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.RAWCOPPER) >= 64) {
                transformJelly(ModEntities.JellyType.RAWCOPPER);
            }
        } else if (stack.getItem() == Items.RAW_GOLD) {
            increaseAbsorptionCount(ModEntities.JellyType.RAWGOLD, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.RAWGOLD) >= 64) {
                transformJelly(ModEntities.JellyType.RAWGOLD);
            }
        } else if (stack.getItem() == Items.RAW_IRON) {
            increaseAbsorptionCount(ModEntities.JellyType.RAWIRON, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.RAWIRON) >= 64) {
                transformJelly(ModEntities.JellyType.RAWIRON);
            }
        }
        // Absorb Lapis
        else if (stack.getItem() == Items.LAPIS_LAZULI) {
            increaseAbsorptionCount(ModEntities.JellyType.LAPIS, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.LAPIS) >= 64) {
                transformJelly(ModEntities.JellyType.LAPIS);
            }
        }
        // Absorb Redstone
        else if (stack.getItem() == Items.REDSTONE) {
            increaseAbsorptionCount(ModEntities.JellyType.REDSTONEDUST, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.REDSTONEDUST) >= 64) {
                transformJelly(ModEntities.JellyType.REDSTONEDUST);
            }
        } else if (stack.getItem() == Items.EMERALD) {
            increaseAbsorptionCount(ModEntities.JellyType.EMERALD, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.EMERALD) >= 64) {
                transformJelly(ModEntities.JellyType.EMERALD);
            }
        }
        // Absorb Sapphire (if custom item exists)
        else if (stack.getItem() == ModItems.SAPPHIRE.get()) {
            increaseAbsorptionCount(ModEntities.JellyType.RAWSAPPHIRE, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.RAWSAPPHIRE) >= 64) {
                transformJelly(ModEntities.JellyType.RAWSAPPHIRE);
            }
        } else if (stack.getItem() == Items.DIAMOND) {
            increaseAbsorptionCount(ModEntities.JellyType.DIAMOND, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.DIAMOND) >= 64) {
                transformJelly(ModEntities.JellyType.DIAMOND);
            }
        }
    }

    /**
     * Absorbs nearby items for default jelly entities and triggers jelly transformations based on the absorbed items.
     *
     * @param itemEntity The item entity to absorb.
     */
    private void absorbDefaultJellyItems(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        if (stack.getItem() == Items.COBBLESTONE) {
            increaseAbsorptionCount(ModEntities.JellyType.COBBLESTONE, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.COBBLESTONE) >= 256) {
                transformJelly(ModEntities.JellyType.COBBLESTONE);
            }
        }
        // Absorb Dirt
        else if (stack.getItem() == Items.DIRT) {
            increaseAbsorptionCount(ModEntities.JellyType.DIRT, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.DIRT) >= 128) {
                transformJelly(ModEntities.JellyType.DIRT);
            }
        }
        // Absorb Gravel
        else if (stack.getItem() == Items.GRAVEL) {
            increaseAbsorptionCount(ModEntities.JellyType.GRAVEL, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.GRAVEL) >= 128) {
                transformJelly(ModEntities.JellyType.GRAVEL);
            }
        }
        // Absorb Sand
        else if (stack.getItem() == Items.SAND) {
            increaseAbsorptionCount(ModEntities.JellyType.SAND, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.SAND) >= 128) {
                transformJelly(ModEntities.JellyType.SAND);
            }
        }
        // Absorb Red Mushrooms
        else if (stack.getItem() == Items.RED_MUSHROOM) {
            increaseAbsorptionCount(ModEntities.JellyType.REDMUSHROOM, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.REDMUSHROOM) >= 128) {
                transformJelly(ModEntities.JellyType.REDMUSHROOM);
            }
        } else if (stack.getItem() == Items.ENDER_PEARL) {
            increaseAbsorptionCount(ModEntities.JellyType.ENDERPEARL, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.ENDERPEARL) >= 64) {
                transformJelly(ModEntities.JellyType.ENDERPEARL);
            }
        } else if (stack.getItem() == Items.AMETHYST_SHARD) {
            increaseAbsorptionCount(ModEntities.JellyType.AMETHYST, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.AMETHYST) >= 64) {
                transformJelly(ModEntities.JellyType.AMETHYST);
            }
        } else if (stack.getItem() == Items.PRISMARINE_SHARD) {
            increaseAbsorptionCount(ModEntities.JellyType.PRISMERINE, stack.getCount());
            itemEntity.discard();
            if (getAbsorptionCount(ModEntities.JellyType.PRISMERINE) >= 64) {
                transformJelly(ModEntities.JellyType.PRISMERINE);
            }
        }
    }

    /**
     * Transforms the jelly entity into a new jelly entity of the specified type.
     *
     * @param newType The type of the new jelly entity.
     */
    private void transformJelly(ModEntities.JellyType newType) {
        if (!jelly.level().isClientSide) {
            jelly.discard();
            JellyEntity newJelly = ModEntities.JELLY_ENTITIES.get(newType).get().create((ServerLevel) jelly.level());
            if (newJelly != null) {
                newJelly.moveTo(jelly.getX(), jelly.getY(), jelly.getZ(), jelly.getYRot(), jelly.getXRot());
                jelly.level().addFreshEntity(newJelly);
            }
        }
    }

    /**
     * Increases the absorption count for the specified jelly type.
     *
     * @param type The jelly type.
     * @param count The amount to increase the absorption count by.
     */
    private void increaseAbsorptionCount(ModEntities.JellyType type, int count) {
        absorptionCount.put(type, absorptionCount.getOrDefault(type, 0) + count);
    }

    /**
     * Returns the absorption count for the specified jelly type.
     *
     * @param type The jelly type.
     * @return The absorption count.
     */
    private int getAbsorptionCount(ModEntities.JellyType type) {
        return absorptionCount.getOrDefault(type, 0);
    }
}

