package net.nfgbros.stickyresources.entity;

import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.entity.custom.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, StickyResources.MOD_ID);

    private static final float JELLY_WIDTH = 0.5f;
    private static final float JELLY_HEIGHT = 0.5f;

    //
    // Rhino
    //
    public static final RegistryObject<EntityType<RhinoEntity>> RHINO =
            ENTITY_TYPES.register("rhino", () -> EntityType.Builder.of(RhinoEntity::new, MobCategory.CREATURE)
                    .sized(2.5f, 2.5f).build("rhino"));

    //
    // Jellies
    //
    public static final RegistryObject<EntityType<JellyEntity>> JELLY = registerJelly("jelly", JellyEntity::new);
    public static final RegistryObject<EntityType<JellyBoneEntity>> JELLY_BONE = registerJelly("jelly_bone", JellyBoneEntity::new);
    public static final RegistryObject<EntityType<JellyCoalEntity>> JELLY_COAL = registerJelly("jelly_coal", JellyCoalEntity::new);
    public static final RegistryObject<EntityType<JellyCharCoalEntity>> JELLY_CHARCOAL = registerJelly("jelly_charcoal", JellyCharCoalEntity::new);
    public static final RegistryObject<EntityType<JellyCobblestoneEntity>> JELLY_COBBLESTONE = registerJelly("jelly_cobblestone", JellyCobblestoneEntity::new);
    public static final RegistryObject<EntityType<JellyCopperEntity>> JELLY_COPPER = registerJelly("jelly_copper", JellyCopperEntity::new);
    public static final RegistryObject<EntityType<JellyDiamondEntity>> JELLY_DIAMOND = registerJelly("jelly_diamond", JellyDiamondEntity::new);
    public static final RegistryObject<EntityType<JellyDirtEntity>> JELLY_DIRT = registerJelly("jelly_dirt", JellyDirtEntity::new);
    public static final RegistryObject<EntityType<JellyElectricEntity>> JELLY_ELECTRIC = registerJelly("jelly_electric", JellyElectricEntity::new);
    public static final RegistryObject<EntityType<JellyEmeraldEntity>> JELLY_EMERALD = registerJelly("jelly_emerald", JellyEmeraldEntity::new);
    public static final RegistryObject<EntityType<JellyEnderPearlEntity>> JELLY_ENDER_PEARL = registerJelly("jelly_ender_pearl", JellyEnderPearlEntity::new);
    public static final RegistryObject<EntityType<JellyGlassEntity>> JELLY_GLASS = registerJelly("jelly_glass", JellyGlassEntity::new);
    public static final RegistryObject<EntityType<JellyGoldEntity>> JELLY_GOLD = registerJelly("jelly_gold", JellyGoldEntity::new);
    public static final RegistryObject<EntityType<JellyGravelEntity>> JELLY_GRAVEL = registerJelly("jelly_gravel", JellyGravelEntity::new);
    public static final RegistryObject<EntityType<JellyIronEntity>> JELLY_IRON = registerJelly("jelly_iron", JellyIronEntity::new);
    public static final RegistryObject<EntityType<JellyLapisEntity>> JELLY_LAPIS = registerJelly("jelly_lapis", JellyLapisEntity::new);
    public static final RegistryObject<EntityType<JellyLavaEntity>> JELLY_LAVA = registerJelly("jelly_lava", JellyLavaEntity::new);
    public static final RegistryObject<EntityType<JellyOakLogEntity>> JELLY_OAK_LOG = registerJelly("jelly_oak", JellyOakLogEntity::new);
    public static final RegistryObject<EntityType<JellyObsidianEntity>> JELLY_OBSIDIAN = registerJelly("jelly_obsidian", JellyObsidianEntity::new);
    public static final RegistryObject<EntityType<JellyPrismerineEntity>> JELLY_PRISMERINE = registerJelly("jelly_prismerine", JellyPrismerineEntity::new);
    public static final RegistryObject<EntityType<JellyRedstoneEntity>> JELLY_REDSTONE = registerJelly("jelly_redstone", JellyRedstoneEntity::new);
    public static final RegistryObject<EntityType<JellySandEntity>> JELLY_SAND = registerJelly("jelly_sand", JellySandEntity::new);
    public static final RegistryObject<EntityType<JellySapphireEntity>> JELLY_SAPPHIRE = registerJelly("jelly_sapphire", JellySapphireEntity::new);
    public static final RegistryObject<EntityType<JellyWaterEntity>> JELLY_WATER = registerJelly("jelly_water", JellyWaterEntity::new);

    /**
     * Helper method to register different Jelly entities dynamically.
     */
    private static <T extends JellyEntity> RegistryObject<EntityType<T>> registerJelly(String name, EntityType.EntityFactory<T> factory) {
        return ENTITY_TYPES.register(name,
                () -> EntityType.Builder.of(factory, MobCategory.CREATURE)
                        .sized(JELLY_WIDTH, JELLY_HEIGHT)
                        .build(name));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
