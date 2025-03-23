package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.item.ItemEntity;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.entity.ModEntities;

import java.util.EnumSet;
import java.util.Set;

/**
 * A custom AI goal for jelly entities to find and consume food.
 * The jelly will move towards nearby food items and consume them to increase its hunger level.
 * The jelly will only consider consuming food items that match its defined food preferences.
 */
public class JellyHungerGoal extends Goal {
    private final JellyEntity jelly;
    private int hungerCheckCooldown = 0;
    private final Set<Item> foodItems;

    /**
     * Constructs a new JellyHungerGoal for the given jelly entity.
     *
     * @param jelly the jelly entity for which this goal is created
     */
    public JellyHungerGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.foodItems = getFoodItemsForJellyType(jelly.getJellyType());
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK)); // Allow movement and looking
    }

    @Override
    public boolean canUse() {
        // Check if the jelly is hungry and not a baby
        return !jelly.isBaby() && jelly.getHungerLevel() <= JellyEntity.HUNGER_THRESHOLD;
    }

    @Override
    public boolean canContinueToUse() {
        // Continue if the jelly is still hungry
        return jelly.getHungerLevel() <= JellyEntity.HUNGER_THRESHOLD;
    }

    @Override
    public void start() {
        // Reset cooldown when the goal starts
        hungerCheckCooldown = 0;
    }

    @Override
    public void stop() {
        // Stop searching for food when the goal ends
        jelly.getNavigation().stop();
    }

    @Override
    public void tick() {
        // Check for food every 20 ticks (1 second)
        if (hungerCheckCooldown-- <= 0) {
            hungerCheckCooldown = 20;

            // Look for nearby food items
            jelly.level().getEntitiesOfClass(ItemEntity.class, jelly.getBoundingBox().inflate(10.0D), this::isFoodItem)
                    .stream()
                    .findFirst()
                    .ifPresent(itemEntity -> {
                        jelly.getNavigation().moveTo(itemEntity, 1.2D); // Move toward the food
                        if (jelly.distanceToSqr(itemEntity) < 2.0D) {
                            itemEntity.getItem().shrink(1); // Consume the food
                            jelly.feed(5); // Increase hunger level
                        }
                    });
        }
    }

    // Check if an item is food for this jelly type
    private boolean isFoodItem(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        return foodItems.contains(stack.getItem());
    }

    // Define food items for each jelly type
    private Set<Item> getFoodItemsForJellyType(ModEntities.JellyType type) {
        return switch (type) {

            case AMETHYST -> Set.of(Items.AMETHYST_SHARD, Items.CALCITE, Items.BUDDING_AMETHYST);
            case BONE -> Set.of(Items.BONE, Items.BONE_MEAL, Items.WITHER_SKELETON_SKULL);
            case CHARCOAL -> Set.of(Items.CHARCOAL, Items.COAL, Items.CAMPFIRE);
            case CAKE -> Set.of(Items.CAKE, Items.SUGAR, Items.WHEAT);
            case COAL -> Set.of(Items.COAL, Items.COAL_BLOCK, Items.TORCH);
            case COBBLESTONE -> Set.of(Items.COBBLESTONE, Items.STONE, Items.MOSSY_COBBLESTONE);
            case COW -> Set.of(Items.BEEF, Items.LEATHER, Items.MILK_BUCKET);
            case DEFAULT -> Set.of(Items.SLIME_BALL, Items.APPLE, Items.HONEY_BOTTLE);
            case DIAMOND -> Set.of(Items.DIAMOND, Items.DIAMOND_BLOCK, Items.EMERALD);
            case DIRT -> Set.of(Items.DIRT, Items.GRASS_BLOCK, Items.PODZOL);
            case EMERALD -> Set.of(Items.EMERALD, Items.EMERALD_BLOCK, Items.LAPIS_LAZULI);
            case ENDERPEARL -> Set.of(Items.ENDER_PEARL, Items.ENDER_EYE, Items.CHORUS_FRUIT);
            case GLASS -> Set.of(Items.GLASS, Items.GLASS_PANE, Items.WHITE_STAINED_GLASS);
            case GRASS -> Set.of(Items.GRASS, Items.WHEAT_SEEDS, Items.MELON_SEEDS);
            case GRAVEL -> Set.of(Items.GRAVEL, Items.FLINT, Items.CLAY_BALL);
            case HONEY -> Set.of(Items.HONEY_BOTTLE, Items.HONEYCOMB, Items.BEEHIVE);
            case ICE -> Set.of(Items.ICE, Items.PACKED_ICE, Items.BLUE_ICE);
            case LAPIS -> Set.of(Items.LAPIS_LAZULI, Items.LAPIS_BLOCK, Items.CYAN_DYE);
            case LAVA -> Set.of(Items.MAGMA_CREAM, Items.BLAZE_POWDER, Items.NETHERRACK);
            case LOGOAK -> Set.of(Items.OAK_LOG, Items.OAK_PLANKS, Items.STICK);
            case OBSIDIAN -> Set.of(Items.OBSIDIAN, Items.CRYING_OBSIDIAN, Items.NETHERITE_SCRAP);
            case PRISMERINE -> Set.of(Items.PRISMARINE_SHARD, Items.PRISMARINE_CRYSTALS, Items.SEA_LANTERN);
            case PUMPKIN -> Set.of(Items.PUMPKIN, Items.PUMPKIN_SEEDS, Items.CARVED_PUMPKIN);
            case RAWCOPPER -> Set.of(Items.RAW_COPPER, Items.COPPER_INGOT, Items.LIGHTNING_ROD);
            case RAWGOLD -> Set.of(Items.RAW_GOLD, Items.GOLD_INGOT, Items.GOLD_NUGGET);
            case RAWIRON -> Set.of(Items.RAW_IRON, Items.IRON_INGOT, Items.IRON_NUGGET);
            case REDSTONEDUST -> Set.of(Items.REDSTONE, Items.REDSTONE_BLOCK, Items.REDSTONE_TORCH);
            case ROTTENFLESH -> Set.of(Items.ROTTEN_FLESH, Items.ZOMBIE_HEAD, Items.SPIDER_EYE);
            case REDMUSHROOM -> Set.of(Items.RED_MUSHROOM, Items.BROWN_MUSHROOM, Items.MUSHROOM_STEW);
            case SAND -> Set.of(Items.SAND, Items.GLASS, Items.SANDSTONE);
            case RAWSAPPHIRE -> Set.of(Items.AMETHYST_SHARD, Items.LAPIS_LAZULI, Items.QUARTZ);
            case STONE -> Set.of(Items.STONE, Items.COBBLESTONE, Items.ANDESITE);
            case STRAWBERRY -> Set.of(Items.SWEET_BERRIES, Items.GLOW_BERRIES, Items.APPLE);
            case WATER -> Set.of(Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.KELP);
            default -> Set.of(Items.SLIME_BALL); // Default food item
        };
    }

}
