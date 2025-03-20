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


        AMETHYST(BoneJellyEntity::new, "amethyst_jelly", 0.5f, 0.5f), // Adjusted size
        BONE(BoneJellyEntity::new, "bone_jelly", 0.5f, 0.5f), // Adjusted size
        CAKE(CakeJellyEntity::new, "cake_jelly", 0.5f, 0.5f), // Adjusted size
        CHARCOAL(CharcoalJellyEntity::new, "charcoal_jelly", 0.5f, 0.5f), // Adjusted size
        COAL(CoalJellyEntity::new, "coal_jelly", 0.5f, 0.5f), // Adjusted size
        COBBLESTONE(CobblestoneJellyEntity::new, "cobblestone_jelly", 0.5f, 0.5f), // Adjusted size
        COW(CowJellyEntity::new, "cow_jelly", 0.5f, 0.5f), // Adjusted size
        DEFAULT(JellyEntity::new, "default_jelly", 0.5f, 0.5f), // Adjusted size
        DIAMOND(DiamondJellyEntity::new, "diamond_jelly", 0.5f, 0.5f), // Adjusted size
        DIRT(DirtJellyEntity::new, "dirt_jelly", 0.5f, 0.5f), // Adjusted size
        ELECTRIC(ElectricJellyEntity::new, "electric_jelly", 0.5f, 0.5f), // Adjusted size
        EMERALD(EmeraldJellyEntity::new, "emerald_jelly", 0.5f, 0.5f), // Adjusted size
        ENDERPEARL(EnderPearlJellyEntity::new, "enderpearl_jelly", 0.5f, 0.5f), // Adjusted size
        FIRE(FireJellyEntity::new, "fire_jelly", 0.5f, 0.5f), // Adjusted size
        GLASS(GlassJellyEntity::new, "glass_jelly", 0.5f, 0.5f), // Adjusted size
        GRASS(GrassJellyEntity::new, "grass_jelly", 0.5f, 0.5f), // Adjusted size
        GRAVEL(GravelJellyEntity::new, "gravel_jelly", 0.5f, 0.5f), // Adjusted size
        HONEY(HoneyJellyEntity::new, "honey_jelly", 0.5f, 0.5f), // Adjusted size
        ICE(IceJellyEntity::new, "ice_jelly", 0.5f, 0.5f), // Adjusted size
        LAPIS(LapisJellyEntity::new, "lapis_jelly", 0.5f, 0.5f), // Adjusted size
        LAVA(LavaJellyEntity::new, "lava_jelly", 0.5f, 0.5f), // Adjusted size
        MAGNET(MagnetJellyEntity::new, "magnet_jelly", 0.5f, 0.5f), // Adjusted size
        LOGOAK(LogOakJellyEntity::new, "logoak_jelly", 0.5f, 0.5f), // Adjusted size
        OBSIDIAN(ObsidianJellyEntity::new, "obsidian_jelly", 0.5f, 0.5f), // Adjusted size
        PRISMERINE(PrismerineJellyEntity::new, "prismerine_jelly", 0.5f, 0.5f), // Adjusted size
        PUMPKIN(PumpkinJellyEntity::new, "pumpkin_jelly", 0.5f, 0.5f), // Adjusted size
        RAWCOPPER(RawCopperJellyEntity::new, "rawcopper_jelly", 0.5f, 0.5f), // Adjusted size
        RAWGOLD(RawGoldJellyEntity::new, "rawgold_jelly", 0.5f, 0.5f), // Adjusted size
        RAWIRON(RawIronJellyEntity::new, "rawiron_jelly", 0.5f, 0.5f), // Adjusted size
        REDMUSHROOM(RedMushroomJellyEntity::new, "redmushroom_jelly", 0.5f, 0.5f), // Adjusted size
        REDSTONEDUST(RedstoneDustJellyEntity::new, "redstonedust_jelly", 0.5f, 0.5f), // Adjusted size
        ROTTONFLESH(RottonFleshJellyEntity::new, "rotton_flesh_jelly", 0.5f, 0.5f), // Adjusted size
        SAND(SandJellyEntity::new, "sand_jelly", 0.5f, 0.5f), // Adjusted size
        RAWSAPPHIRE(SapphireJellyEntity::new, "sapphire_jelly", 0.5f, 0.5f), // Adjusted size
        STONE(StoneJellyEntity::new, "stone_jelly", 0.5f, 0.5f),
        STRAWBERRY(StrawberryJellyEntity::new, "strawberry_jelly", 0.5f, 0.5f), // Adjusted size
        WATER(WaterJellyEntity::new, "water_jelly", 0.5f, 0.5f); // Adjusted size



        public final EntityType.EntityFactory<? extends JellyEntity> factory;
        public final String registryName;
        public final float width, height;

        JellyType(EntityType.EntityFactory<? extends JellyEntity> factory, String registryName, float width, float height) {
            this.factory = factory;
            this.registryName = registryName;
            this.width = width;
            this.height = height;
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