package net.nfgbros.stickyresources.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.entity.custom.*;

import java.util.EnumMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = StickyResources.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, StickyResources.MOD_ID);

    public enum JellyType {
        AMETHYST(BoneJellyEntity::new, "amethyst_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        BONE(BoneJellyEntity::new, "bone_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        CAKE(CakeJellyEntity::new, "cake_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        CHARCOAL(CharcoalJellyEntity::new, "charcoal_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        COAL(CoalJellyEntity::new, "coal_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        COBBLESTONE(CobblestoneJellyEntity::new, "cobblestone_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        COW(CowJellyEntity::new, "cow_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        DEFAULT(JellyEntity::new, "default_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        DIAMOND(DiamondJellyEntity::new, "diamond_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        DIRT(DirtJellyEntity::new, "dirt_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        ELECTRIC(ElectricJellyEntity::new, "electric_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        EMERALD(EmeraldJellyEntity::new, "emerald_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        ENDERPEARL(EnderPearlJellyEntity::new, "enderpearl_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        FIRE(FireJellyEntity::new, "fire_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        GLASS(GlassJellyEntity::new, "glass_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        GRASS(GrassJellyEntity::new, "grass_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        GRAVEL(GravelJellyEntity::new, "gravel_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        HONEY(HoneyJellyEntity::new, "honey_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        ICE(IceJellyEntity::new, "ice_jelly", 0.5f, 0.5f, JellySwimBehavior.DOLPHIN),
        LAPIS(LapisJellyEntity::new, "lapis_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        LAVA(LavaJellyEntity::new, "lava_jelly", 0.5f, 0.5f, JellySwimBehavior.WATER_LETHAL),
        MAGNET(MagnetJellyEntity::new, "magnet_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        LOGOAK(LogOakJellyEntity::new, "logoak_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        OBSIDIAN(ObsidianJellyEntity::new, "obsidian_jelly", 0.5f, 0.5f, JellySwimBehavior.FLOATING),
        PRISMERINE(PrismerineJellyEntity::new, "prismerine_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        PUMPKIN(PumpkinJellyEntity::new, "pumpkin_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        RAWCOPPER(RawCopperJellyEntity::new, "rawcopper_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        RAWGOLD(RawGoldJellyEntity::new, "rawgold_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        RAWIRON(RawIronJellyEntity::new, "rawiron_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        REDMUSHROOM(RedMushroomJellyEntity::new, "redmushroom_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        REDSTONEDUST(RedstoneDustJellyEntity::new, "redstonedust_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        ROTTENFLESH(RottonFleshJellyEntity::new, "rotton_flesh_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        SAND(SandJellyEntity::new, "sand_jelly", 0.5f, 0.5f, JellySwimBehavior.WATER_DAMAGE),
        RAWSAPPHIRE(SapphireJellyEntity::new, "sapphire_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        STONE(StoneJellyEntity::new, "stone_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        STRAWBERRY(StrawberryJellyEntity::new, "strawberry_jelly", 0.5f, 0.5f, JellySwimBehavior.NONE),
        WATER(WaterJellyEntity::new, "water_jelly", 0.5f, 0.5f, JellySwimBehavior.FISH);

        public enum JellySwimBehavior {
            FISH,
            DOLPHIN,
            WATER_DAMAGE,
            WATER_LETHAL,
            FLOATING,
            SURFACE_SWIMMING,
            NONE
        }

        public final EntityType.EntityFactory<? extends JellyEntity> factory;
        public final String registryName;
        public final float width, height;
        private final JellySwimBehavior swimBehavior;

        JellyType(EntityType.EntityFactory<? extends JellyEntity> factory, String registryName, float width, float height, JellySwimBehavior swimBehavior) {
            this.factory = factory;
            this.registryName = registryName;
            this.width = width;
            this.height = height;
            this.swimBehavior = swimBehavior;
        }

        public JellySwimBehavior getSwimBehavior() {
            return this.swimBehavior;
        }
    }

    public static final Map<JellyType, RegistryObject<EntityType<? extends JellyEntity>>> JELLY_ENTITIES = new EnumMap<>(JellyType.class);

    private static EntityType<? extends JellyEntity> createJellyEntity(JellyType type) {
        return EntityType.Builder.of(type.factory, MobCategory.CREATURE)
                .sized(type.width, type.height)
                .build(type.registryName);
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