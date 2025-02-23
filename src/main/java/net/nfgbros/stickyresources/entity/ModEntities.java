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
    //
    //Rhino
    //
    public static final RegistryObject<EntityType<RhinoEntity>> RHINO =
            ENTITY_TYPES.register("rhino", () -> EntityType.Builder.of(RhinoEntity::new, MobCategory.CREATURE)
                    .sized(2.5f, 2.5f).build("rhino"));
    //
    //Jellies
    //
    //Original Jelly
    //
    public static final RegistryObject<EntityType<JellyEntity>> JELLY =
            ENTITY_TYPES.register("jelly", () -> EntityType.Builder.of(JellyEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly"));
    //
    //Breedable Jellies
    //
    public static final RegistryObject<EntityType<JellyBoneEntity>> JELLY_BONE =
            ENTITY_TYPES.register("jelly_bone", () -> EntityType.Builder.of(JellyBoneEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_bone"));
    public static final RegistryObject<EntityType<JellyCoalEntity>> JELLY_COAL =
            ENTITY_TYPES.register("jelly_coal", () -> EntityType.Builder.of(JellyCoalEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_coal"));
    public static final RegistryObject<EntityType<JellyCobblestoneEntity>> JELLY_COBBLESTONE =
            ENTITY_TYPES.register("jelly_cobblestone", () -> EntityType.Builder.of(JellyCobblestoneEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_cobblestone"));
    public static final RegistryObject<EntityType<JellyCopperEntity>> JELLY_COPPER =
            ENTITY_TYPES.register("jelly_copper", () -> EntityType.Builder.of(JellyCopperEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_copper"));
    public static final RegistryObject<EntityType<JellyDiamondEntity>> JELLY_DIAMOND =
            ENTITY_TYPES.register("jelly_diamond", () -> EntityType.Builder.of(JellyDiamondEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_diamond"));
    public static final RegistryObject<EntityType<JellyDirtEntity>> JELLY_DIRT =
            ENTITY_TYPES.register("jelly_dirt", () -> EntityType.Builder.of(JellyDirtEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_dirt"));
    public static final RegistryObject<EntityType<JellyElectricEntity>> JELLY_ELECTRIC =
            ENTITY_TYPES.register("jelly_electric", () -> EntityType.Builder.of(JellyElectricEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_electric"));
    public static final RegistryObject<EntityType<JellyEmeraldEntity>> JELLY_EMERALD =
            ENTITY_TYPES.register("jelly_emerald", () -> EntityType.Builder.of(JellyEmeraldEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_emerald"));
    public static final RegistryObject<EntityType<JellyEnderPearlEntity>> JELLY_ENDER_PEARL =
            ENTITY_TYPES.register("jelly_ender_pearl", () -> EntityType.Builder.of(JellyEnderPearlEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_ender_pearl"));
    public static final RegistryObject<EntityType<JellyGlassEntity>> JELLY_GLASS =
            ENTITY_TYPES.register("jelly_glass", () -> EntityType.Builder.of(JellyGlassEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_glass"));
    public static final RegistryObject<EntityType<JellyGoldEntity>> JELLY_GOLD =
            ENTITY_TYPES.register("jelly_gold", () -> EntityType.Builder.of(JellyGoldEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_gold"));
    public static final RegistryObject<EntityType<JellyGravelEntity>> JELLY_GRAVEL =
            ENTITY_TYPES.register("jelly_gravel", () -> EntityType.Builder.of(JellyGravelEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_gravel"));
    public static final RegistryObject<EntityType<JellyIronEntity>> JELLY_IRON =
            ENTITY_TYPES.register("jelly_iron", () -> EntityType.Builder.of(JellyIronEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_iron"));
    public static final RegistryObject<EntityType<JellyLapisEntity>> JELLY_LAPIS =
            ENTITY_TYPES.register("jelly_lapis", () -> EntityType.Builder.of(JellyLapisEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_lapis"));
    public static final RegistryObject<EntityType<JellyLavaEntity>> JELLY_LAVA =
            ENTITY_TYPES.register("jelly_lava", () -> EntityType.Builder.of(JellyLavaEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_lava"));
    public static final RegistryObject<EntityType<JellyOakLogEntity>> JELLY_OAK_LOG =
            ENTITY_TYPES.register("jelly_oak", () -> EntityType.Builder.of(JellyOakLogEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_oak"));
    public static final RegistryObject<EntityType<JellyObsidianEntity>> JELLY_OBSIDIAN =
            ENTITY_TYPES.register("jelly_obsidian", () -> EntityType.Builder.of(JellyObsidianEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_obsidian"));
    public static final RegistryObject<EntityType<JellyPrismerineEntity>> JELLY_PRISMERINE =
            ENTITY_TYPES.register("jelly_prismerine", () -> EntityType.Builder.of(JellyPrismerineEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_prismerine"));
    public static final RegistryObject<EntityType<JellyRedstoneEntity>> JELLY_REDSTONE =
            ENTITY_TYPES.register("jelly_redstone", () -> EntityType.Builder.of(JellyRedstoneEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_redstone"));
    public static final RegistryObject<EntityType<JellySandEntity>> JELLY_SAND =
            ENTITY_TYPES.register("jelly_sand", () -> EntityType.Builder.of(JellySandEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_sand"));
    public static final RegistryObject<EntityType<JellySapphireEntity>> JELLY_SAPPHIRE =
            ENTITY_TYPES.register("jelly_sapphire", () -> EntityType.Builder.of(JellySapphireEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_sapphire"));
    public static final RegistryObject<EntityType<JellyWaterEntity>> JELLY_WATER =
            ENTITY_TYPES.register("jelly_water", () -> EntityType.Builder.of(JellyWaterEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("jelly_water"));
    //
    //
    //


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
