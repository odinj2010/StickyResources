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
        DEFAULT(JellyEntity::new, "default_jelly", 1.0f, 1.0f),
        BONE(BoneJellyEntity::new, "bone_jelly", 1.0f, 1.0f),
        CHARCOAL(CharcoalJellyEntity::new, "charcoal_jelly", 1.0f, 1.0f),
        COAL(CoalJellyEntity::new, "coal_jelly", 1.0f, 1.0f),
        COBBLESTONE(CobblestoneJellyEntity::new, "cobblestone_jelly", 1.0f, 1.0f),
        DIAMOND(DiamondJellyEntity::new, "diamond_jelly", 1.0f, 1.0f),
        DIRT(DirtJellyEntity::new, "dirt_jelly", 1.0f, 1.0f),
        ELECTRIC(ElectricJellyEntity::new, "electric_jelly", 1.0f, 1.0f),
        EMERALD(EmeraldJellyEntity::new, "emerald_jelly", 1.0f, 1.0f),
        ENDERPEARL(EnderPearlJellyEntity::new, "enderpearl_jelly", 1.0f, 1.0f),
        GLASS(GlassJellyEntity::new, "glass_jelly", 1.0f, 1.0f),
        GRAVEL(GravelJellyEntity::new, "gravel_jelly", 1.0f, 1.0f),
        LAPIS(LapisJellyEntity::new, "lapis_jelly", 1.0f, 1.0f),
        LAVA(LavaJellyEntity::new, "lava_jelly", 1.0f, 1.0f),
        MAGNET(MagnetJellyEntity::new, "magnet_jelly", 1.0f, 1.0f),
        OAKLOG(LogOakJellyEntity::new, "logoak_jelly", 1.0f, 1.0f),
        OBSIDIAN(ObsidianJellyEntity::new, "obsidian_jelly", 1.0f, 1.0f),
        PRISMERINE(PrismerineJellyEntity::new, "prismerine_jelly", 1.0f, 1.0f),
        RAWCOPPER(RawCopperJellyEntity::new, "rawcopper_jelly", 1.0f, 1.0f),
        RAWGOLD(RawGoldJellyEntity::new, "rawgold_jelly", 1.0f, 1.0f),
        RAWIRON(RawIronJellyEntity::new, "rawiron_jelly", 1.0f, 1.0f),
        REDSTONEDUST(RedstoneDustJellyEntity::new, "redstonedust_jelly", 1.0f, 1.0f),
        SAND(SandJellyEntity::new, "sand_jelly", 1.0f, 1.0f),
        SAPPHIRE(SapphireJellyEntity::new, "sapphire_jelly", 1.0f, 1.0f),
        WATER(WaterJellyEntity::new, "water_jelly", 1.0f, 1.0f);

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
