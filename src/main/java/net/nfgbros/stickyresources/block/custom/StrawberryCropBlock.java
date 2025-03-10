package net.nfgbros.stickyresources.block.custom;

import net.nfgbros.stickyresources.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class StrawberryCropBlock extends CropBlock {
    // Maximum age for the crop
    public static final int MAX_AGE = 5;

    // Property representing the growth stage of the crop
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;

    // Constructor to initialize the crop block with specific properties
    public StrawberryCropBlock(Properties pProperties) {
        super(pProperties);
    }

    // Returns the item used as the seed for this crop
    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.STRAWBERRY_SEEDS.get();
    }

    // Gets the property representing the growth stage
    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    // Gets the maximum age (growth stage)
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    // Defines the block state properties for this crop block
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
