package net.nfgbros.stickyresources.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.entity.custom.*;
import net.nfgbros.stickyresources.item.ModItems;

import java.util.*;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = StickyResources.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, StickyResources.MOD_ID);

    public enum JellyType {
        AMETHYST(AmethystJellyEntity::new, "amethyst_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_AMETHYST.get(), 15.0D, 1.5D, 0.25D, false),
        BONE(BoneJellyEntity::new, "bone_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_BONE_MEAL.get(), 10.0D, 1.0D, 0.3D, false),
        CAKE(CakeJellyEntity::new, "cake_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.JELLY_CAKE.get(), 12.0D, 0.5D, 0.2D, false),
        CHARCOAL(CharcoalJellyEntity::new, "charcoal_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_CHARCOAL.get(), 10.0D, 1.0D, 0.25D, false),
        COAL(CoalJellyEntity::new, "coal_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_COAL.get(), 10.0D, 1.0D, 0.25D, false),
        COBBLESTONE(CobblestoneJellyEntity::new, "cobblestone_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModBlocks.STICKY_COBBLESTONE.get(), 20.0D, 2.0D, 0.15D, false),
        COW(CowJellyEntity::new, "cow_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_BEEF.get(), 10.0D, 0.5D, 0.25D, false),
        DEFAULT(JellyEntity::new, "default_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> Items.SLIME_BALL, 10.0D, 1.0D, 0.25D, false),
        DIAMOND(DiamondJellyEntity::new, "diamond_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_DIAMOND.get(), 30.0D, 3.0D, 0.2D, false),
        DIRT(DirtJellyEntity::new, "dirt_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModBlocks.STICKY_DIRT.get(), 8.0D, 0.5D, 0.25D, false),
        ELECTRIC(ElectricJellyEntity::new, "electric_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> Items.SLIME_BALL, 12.0D, 2.5D, 0.35D, true),
        EMERALD(EmeraldJellyEntity::new, "emerald_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_EMERALD.get(), 20.0D, 2.0D, 0.25D, false),
        ENDERPEARL(EnderPearlJellyEntity::new, "enderpearl_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_ENDER_PEARL.get(), 15.0D, 1.0D, 0.4D, false),
        FIRE(FireJellyEntity::new, "fire_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> Items.SLIME_BALL, 12.0D, 3.0D, 0.3D, true),
        GLASS(GlassJellyEntity::new, "glass_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModBlocks.STICKY_GLASS.get(), 5.0D, 0.5D, 0.3D, false),
        GRASS(GrassJellyEntity::new, "grass_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> Items.GRASS, 10.0D, 1.0D, 0.25D, false),
        GRAVEL(GravelJellyEntity::new, "gravel_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModBlocks.STICKY_GRAVEL.get(), 12.0D, 1.2D, 0.22D, false),
        HONEY(HoneyJellyEntity::new, "honey_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModBlocks.JELLY_HONEY.get(), 10.0D, 0.2D, 0.1D, false),
        ICE(IceJellyEntity::new, "ice_jelly", 0.5f, 0.5f, JellySwimBehavior.DOLPHIN, () -> ModBlocks.STICKY_ICE.get(), 12.0D, 1.0D, 0.28D, false),
        LAPIS(LapisJellyEntity::new, "lapis_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_LAPIS_LAZULI.get(), 12.0D, 1.5D, 0.25D, false),
        LAVA(LavaJellyEntity::new, "lava_jelly", 0.5f, 0.5f, JellySwimBehavior.WATER_LETHAL, () -> Items.SLIME_BALL, 25.0D, 4.0D, 0.15D, true),
        MAGNET(MagnetJellyEntity::new, "magnet_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> Items.SLIME_BALL, 15.0D, 1.0D, 0.25D, false),
        LOGOAK(LogOakJellyEntity::new, "logoak_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModBlocks.STICKY_LOG_OAK.get(), 15.0D, 1.5D, 0.2D, false),
        OBSIDIAN(ObsidianJellyEntity::new, "obsidian_jelly", 0.5f, 0.5f, JellySwimBehavior.FLOATING, () -> ModBlocks.STICKY_OBSIDIAN.get(), 40.0D, 2.5D, 0.1D, true),
        PRISMERINE(PrismerineJellyEntity::new, "prismerine_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_PRISMERINE_CRYSTALS.get(), 15.0D, 2.0D, 0.3D, false),
        PUMPKIN(PumpkinJellyEntity::new, "pumpkin_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModBlocks.STICKY_PUMPKIN.get(), 12.0D, 1.0D, 0.22D, false),
        RAWCOPPER(RawCopperJellyEntity::new, "rawcopper_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_RAW_COPPER.get(), 15.0D, 1.5D, 0.2D, false),
        RAWGOLD(RawGoldJellyEntity::new, "rawgold_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_RAW_GOLD.get(), 15.0D, 2.0D, 0.22D, false),
        RAWIRON(RawIronJellyEntity::new, "rawiron_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_RAW_IRON.get(), 20.0D, 2.0D, 0.2D, false),
        REDMUSHROOM(RedMushroomJellyEntity::new, "redmushroom_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_RED_MUSHROOM.get(), 10.0D, 1.0D, 0.25D, false),
        REDSTONEDUST(RedstoneDustJellyEntity::new, "redstonedust_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_REDSTONE_DUST.get(), 10.0D, 1.2D, 0.3D, false),
        ROTTENFLESH(RottonFleshJellyEntity::new, "rotton_flesh_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_ROTTON_FLESH.get(), 15.0D, 2.0D, 0.2D, false),
        SAND(SandJellyEntity::new, "sand_jelly", 0.5f, 0.5f, JellySwimBehavior.WATER_DAMAGE, () -> ModBlocks.STICKY_SAND.get(), 10.0D, 0.5D, 0.25D, false),
        RAWSAPPHIRE(SapphireJellyEntity::new, "sapphire_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_RAW_SAPPHIRE.get(), 25.0D, 2.5D, 0.2D, false),
        STONE(StoneJellyEntity::new, "stone_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModBlocks.STICKY_STONE.get(), 18.0D, 1.5D, 0.18D, false),
        STRAWBERRY(StrawberryJellyEntity::new, "strawberry_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE, () -> ModItems.STICKY_STRAWBERRY.get(), 10.0D, 0.5D, 0.25D, false),
        WATER(WaterJellyEntity::new, "water_jelly", 0.5f, 0.5f, JellySwimBehavior.FISH, () -> Items.SLIME_BALL, 10.0D, 1.0D, 0.35D, false);

        public enum JellySwimBehavior {
            FISH, DOLPHIN, WATER_DAMAGE, WATER_LETHAL, FLOATING, SURFACE_SWIMMING, NONE
        }

        public final EntityType.EntityFactory<? extends JellyEntity> factory;
        public final String registryName;
        public final float width, height;
        public final double maxHealth, attackDamage, movementSpeed;
        public final boolean fireImmune;
        private final JellySwimBehavior swimBehavior;
        private final Supplier<ItemLike> dropProvider;
        private final Map<Item, TransformationData> transformations = new HashMap<>();
        private final Set<Item> loveFoods = new HashSet<>();
        private final Set<Item> preferredFoods = new HashSet<>();
        private final Set<Block> preferredBlocks = new HashSet<>();

        JellyType(EntityType.EntityFactory<? extends JellyEntity> factory, String registryName, float width, float height, JellySwimBehavior swimBehavior, Supplier<ItemLike> dropProvider, double health, double damage, double speed, boolean fireImmune) {
            this.factory = factory;
            this.registryName = registryName;
            this.width = width;
            this.height = height;
            this.swimBehavior = swimBehavior;
            this.dropProvider = dropProvider;
            this.maxHealth = health;
            this.attackDamage = damage;
            this.movementSpeed = speed;
            this.fireImmune = fireImmune;
        }

        public JellySwimBehavior getSwimBehavior() { return this.swimBehavior; }
        public ItemLike getDropItem() { return dropProvider.get(); }
        
        public void addTransformation(Item input, int cost, JellyType result) {
            this.transformations.put(input, new TransformationData(cost, result));
        }

        public Optional<TransformationData> getTransformation(Item item) {
            return Optional.ofNullable(transformations.get(item));
        }

        public void addLoveFood(ItemLike item) { this.loveFoods.add(item.asItem()); }
        public boolean isLoveFood(ItemStack stack) { return this.loveFoods.contains(stack.getItem()); }

        public void addPreferredFood(ItemLike item) { this.preferredFoods.add(item.asItem()); }
        public boolean isPreferredFood(ItemStack stack) { return this.preferredFoods.contains(stack.getItem()); }

        public void addPreferredBlock(Block block) { this.preferredBlocks.add(block); }
        public boolean isPreferredBlock(BlockState state) { return this.preferredBlocks.contains(state.getBlock()); }

        public record TransformationData(int cost, JellyType result) {}
    }

    public static final Map<JellyType, RegistryObject<EntityType<? extends JellyEntity>>> JELLY_ENTITIES = new EnumMap<>(JellyType.class);

    public static void initialize() {
        initializeTransformations();
        initializeFoods();
    }

    private static void initializeTransformations() {
        JellyType.DEFAULT.addTransformation(Items.MAGMA_CREAM, 1, JellyType.LAVA);
        JellyType.DEFAULT.addTransformation(Items.SAND, 32, JellyType.SAND);
        JellyType.DEFAULT.addTransformation(Items.DIRT, 32, JellyType.DIRT);
        JellyType.DEFAULT.addTransformation(Items.STONE, 32, JellyType.STONE);
        JellyType.DEFAULT.addTransformation(Items.OAK_LOG, 16, JellyType.LOGOAK);
        JellyType.DEFAULT.addTransformation(Items.REDSTONE, 16, JellyType.REDSTONEDUST);
        JellyType.STONE.addTransformation(Items.IRON_NUGGET, 8, JellyType.RAWIRON);
        JellyType.STONE.addTransformation(Items.GOLD_NUGGET, 8, JellyType.RAWGOLD);
        JellyType.STONE.addTransformation(Items.COPPER_INGOT, 1, JellyType.RAWCOPPER);
        JellyType.STONE.addTransformation(Items.LAPIS_LAZULI, 4, JellyType.LAPIS);
        JellyType.STONE.addTransformation(Items.COAL, 4, JellyType.COAL);
    }
    
    private static void initializeFoods() {
        JellyType.DEFAULT.addLoveFood(Items.SLIME_BALL);
        JellyType.DEFAULT.addPreferredFood(Items.SLIME_BALL);
        
        JellyType.WATER.addLoveFood(Items.KELP);
        JellyType.WATER.addPreferredFood(Items.SEAGRASS);
        
        JellyType.LAVA.addLoveFood(Items.MAGMA_CREAM);
        JellyType.LAVA.addPreferredFood(Items.MAGMA_CREAM);

        JellyType.FIRE.addLoveFood(Items.BLAZE_POWDER);
        JellyType.FIRE.addPreferredFood(Items.BLAZE_POWDER);

        JellyType.AMETHYST.addLoveFood(Items.AMETHYST_SHARD);
        JellyType.DIAMOND.addLoveFood(Items.DIAMOND);
        JellyType.EMERALD.addLoveFood(Items.EMERALD);
        JellyType.LAPIS.addLoveFood(Items.LAPIS_LAZULI);
        JellyType.REDSTONEDUST.addLoveFood(Items.REDSTONE);
        JellyType.COAL.addLoveFood(Items.COAL);
        JellyType.CHARCOAL.addLoveFood(Items.CHARCOAL);

        JellyType.RAWIRON.addLoveFood(Items.RAW_IRON);
        JellyType.RAWGOLD.addLoveFood(Items.RAW_GOLD);
        JellyType.RAWCOPPER.addLoveFood(Items.RAW_COPPER);
        JellyType.RAWSAPPHIRE.addLoveFood(ModItems.RAW_SAPPHIRE.get());

        JellyType.STONE.addLoveFood(Items.STONE);
        JellyType.COBBLESTONE.addLoveFood(Items.COBBLESTONE);
        JellyType.DIRT.addLoveFood(Items.DIRT);
        JellyType.SAND.addLoveFood(Items.SAND);
        JellyType.GRAVEL.addLoveFood(Items.GRAVEL);
        JellyType.GLASS.addLoveFood(Items.GLASS);
        JellyType.OBSIDIAN.addLoveFood(Items.OBSIDIAN);
        JellyType.LOGOAK.addLoveFood(Items.OAK_LOG);
        JellyType.LOGOAK.addPreferredBlock(Blocks.OAK_LOG);

        JellyType.GRASS.addLoveFood(Items.WHEAT);
        JellyType.COW.addLoveFood(Items.WHEAT);
        JellyType.STRAWBERRY.addLoveFood(ModItems.STRAWBERRY.get());
        JellyType.PUMPKIN.addLoveFood(Items.PUMPKIN);
        JellyType.REDMUSHROOM.addLoveFood(Items.RED_MUSHROOM);
        JellyType.CAKE.addLoveFood(Items.CAKE);
        JellyType.HONEY.addLoveFood(Items.HONEY_BOTTLE);

        JellyType.ENDERPEARL.addLoveFood(Items.ENDER_PEARL);
        JellyType.PRISMERINE.addLoveFood(Items.PRISMARINE_SHARD);
        JellyType.ELECTRIC.addLoveFood(Items.COPPER_INGOT);
        JellyType.MAGNET.addLoveFood(Items.IRON_INGOT);
        JellyType.ROTTENFLESH.addLoveFood(Items.ROTTEN_FLESH);
        JellyType.BONE.addLoveFood(Items.BONE);
        JellyType.BONE.addPreferredBlock(Blocks.BONE_BLOCK);
        JellyType.ICE.addLoveFood(Items.SNOWBALL);
    }

    private static EntityType<? extends JellyEntity> createJellyEntity(JellyType type) {
        EntityType.Builder<? extends JellyEntity> builder = EntityType.Builder.of(type.factory, MobCategory.CREATURE)
                .sized(type.width, type.height);
        
        if (type.fireImmune) {
            builder.fireImmune();
        }
        
        return builder.build(type.registryName);
    }

    public static void register(IEventBus eventBus) {
        for (JellyType type : JellyType.values()) {
            JELLY_ENTITIES.put(type, ENTITY_TYPES.register(type.registryName, () -> createJellyEntity(type)));
        }
        ENTITY_TYPES.register(eventBus);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        for (JellyType type : JellyType.values()) {
            RegistryObject<EntityType<? extends JellyEntity>> entity = JELLY_ENTITIES.get(type);
            if (entity != null && entity.isPresent()) {
                event.put(entity.get(), JellyEntity.createAttributes(type).build());
            }
        }
    }
}