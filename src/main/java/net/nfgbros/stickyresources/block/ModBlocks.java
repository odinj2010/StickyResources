package net.nfgbros.stickyresources.block;

import net.nfgbros.stickyresources.StickyResources;
import net.nfgbros.stickyresources.block.custom.CornCropBlock;
import net.nfgbros.stickyresources.block.custom.WashingStationBlock;
import net.nfgbros.stickyresources.block.custom.StrawberryCropBlock;
import net.nfgbros.stickyresources.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, StickyResources.MOD_ID);

    public static final RegistryObject<Block> STICKY_COBBLESTONE = registerBlock("sticky_cobblestone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.STONE).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> STICKY_DIRT = registerBlock("sticky_dirt",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.ROOTED_DIRT).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> STICKY_GLASS = registerBlock("sticky_glass",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.GLASS).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> STICKY_GRAVEL = registerBlock("sticky_gravel",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.GRAVEL).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> JELLY_HONEY = registerBlock("jelly_honey",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.HONEY_BLOCK).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> STICKY_ICE = registerBlock("sticky_ice",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.MUD_BRICKS).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> STICKY_LOG_OAK = registerBlock("sticky_log_oak",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.WOOD).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> STICKY_OBSIDIAN = registerBlock("sticky_obsidian",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.STONE).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> STICKY_PUMPKIN = registerBlock("sticky_pumpkin",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.STONE).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> STICKY_SAND = registerBlock("sticky_sand",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.SAND).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> STICKY_STONE = registerBlock("sticky_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.STONE).sound(SoundType.SLIME_BLOCK)));




    public static final RegistryObject<Block> SAPPHIRE_BLOCK = registerBlock("sapphire_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> RAW_SAPPHIRE_BLOCK = registerBlock("raw_sapphire_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> SAPPHIRE_ORE = registerBlock("sapphire_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));
    public static final RegistryObject<Block> DEEPSLATE_SAPPHIRE_ORE = registerBlock("deepslate_sapphire_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(3f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));
    public static final RegistryObject<Block> NETHER_SAPPHIRE_ORE = registerBlock("nether_sapphire_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK)
                    .strength(1f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));
    public static final RegistryObject<Block> END_STONE_SAPPHIRE_ORE = registerBlock("end_stone_sapphire_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));

    public static final RegistryObject<Block> SAPPHIRE_STAIRS = registerBlock("sapphire_stairs",
            () -> new StairBlock(() -> ModBlocks.SAPPHIRE_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> SAPPHIRE_SLAB = registerBlock("sapphire_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> SAPPHIRE_BUTTON = registerBlock("sapphire_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).sound(SoundType.AMETHYST),
                    BlockSetType.IRON, 10, true));
    public static final RegistryObject<Block> SAPPHIRE_PRESSURE_PLATE = registerBlock("sapphire_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST),
                    BlockSetType.IRON));

    public static final RegistryObject<Block> SAPPHIRE_FENCE = registerBlock("sapphire_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> SAPPHIRE_FENCE_GATE = registerBlock("sapphire_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST), SoundEvents.CHAIN_PLACE, SoundEvents.ANVIL_BREAK));
    public static final RegistryObject<Block> SAPPHIRE_WALL = registerBlock("sapphire_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> SAPPHIRE_DOOR = registerBlock("sapphire_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST).noOcclusion(), BlockSetType.IRON));
    public static final RegistryObject<Block> SAPPHIRE_TRAPDOOR = registerBlock("sapphire_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST).noOcclusion(), BlockSetType.IRON));


    public static final RegistryObject<Block> STRAWBERRY_CROP = BLOCKS.register("strawberry_crop",
            () -> new StrawberryCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));

    public static final RegistryObject<Block> CORN_CROP = BLOCKS.register("corn_crop",
            () -> new CornCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));


    public static final RegistryObject<Block> CATMINT = registerBlock("catmint",
            () -> new FlowerBlock(() -> MobEffects.LUCK, 5,
                    BlockBehaviour.Properties.copy(Blocks.ALLIUM).noOcclusion().noCollission()));
    public static final RegistryObject<Block> POTTED_CATMINT = BLOCKS.register("potted_catmint",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.CATMINT,
                    BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM).noOcclusion()));

    public static final RegistryObject<Block> WASHING_STATION = registerBlock("washing_station",
            () -> new WashingStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
