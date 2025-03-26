package net.nfgbros.stickyresources.entity;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.item.ModItems;

public class JellyDropManagement {
    private final JellyEntity jelly;

    public JellyDropManagement(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void dropJellyItem() {
        Level world = jelly.level();
        ItemStack dropStack = ItemStack.EMPTY;
        ModEntities.JellyType type = jelly.getJellyType();

        switch (type) {
            case AMETHYST -> dropStack = new ItemStack(ModItems.STICKY_AMETHYST.get());
            case BONE -> dropStack = new ItemStack(ModItems.STICKY_BONE_MEAL.get());
            case CHARCOAL -> dropStack = new ItemStack(ModItems.STICKY_CHARCOAL.get());
            case CAKE -> dropStack = new ItemStack(ModItems.JELLY_CAKE.get());
            case COAL -> dropStack = new ItemStack(ModItems.STICKY_COAL.get());
            case COBBLESTONE -> dropStack = new ItemStack(ModBlocks.STICKY_COBBLESTONE.get());
            case COW -> dropStack = new ItemStack(ModItems.STICKY_BEEF.get());
            case DEFAULT -> dropStack = new ItemStack(Items.SLIME_BALL);
            case DIAMOND -> dropStack = new ItemStack(ModItems.STICKY_DIAMOND.get());
            case DIRT -> dropStack = new ItemStack(ModBlocks.STICKY_DIRT.get());
            case EMERALD -> dropStack = new ItemStack(ModItems.STICKY_EMERALD.get());
            case ENDERPEARL -> dropStack = new ItemStack(ModItems.STICKY_ENDER_PEARL.get());
            case GLASS -> dropStack = new ItemStack(ModBlocks.STICKY_GLASS.get());
            case GRASS -> dropStack = new ItemStack(Items.GRASS);
            case GRAVEL -> dropStack = new ItemStack(ModBlocks.STICKY_GRAVEL.get());
            case HONEY -> dropStack = new ItemStack(ModBlocks.JELLY_HONEY.get());
            case ICE -> dropStack = new ItemStack(ModBlocks.STICKY_ICE.get());
            case LAPIS -> dropStack = new ItemStack(ModItems.STICKY_LAPIS_LAZULI.get());
            case LOGOAK -> dropStack = new ItemStack(ModBlocks.STICKY_LOG_OAK.get());
            case OBSIDIAN -> dropStack = new ItemStack(ModBlocks.STICKY_OBSIDIAN.get());
            case PRISMERINE -> dropStack = new ItemStack(ModItems.STICKY_PRISMERINE_CRYSTALS.get());
            case PUMPKIN -> dropStack = new ItemStack(ModBlocks.STICKY_PUMPKIN.get());
            case RAWCOPPER -> dropStack = new ItemStack(ModItems.STICKY_RAW_COPPER.get());
            case RAWGOLD -> dropStack = new ItemStack(ModItems.STICKY_RAW_GOLD.get());
            case RAWIRON -> dropStack = new ItemStack(ModItems.STICKY_RAW_IRON.get());
            case REDSTONEDUST -> dropStack = new ItemStack(ModItems.STICKY_REDSTONE_DUST.get());
            case ROTTENFLESH -> dropStack = new ItemStack(ModItems.STICKY_ROTTON_FLESH.get());
            case REDMUSHROOM -> dropStack = new ItemStack(ModItems.STICKY_RED_MUSHROOM.get());
            case SAND -> dropStack = new ItemStack(ModBlocks.STICKY_SAND.get());
            case RAWSAPPHIRE -> dropStack = new ItemStack(ModItems.STICKY_RAW_SAPPHIRE.get());
            case STONE -> dropStack = new ItemStack(ModBlocks.STICKY_STONE.get());
            case STRAWBERRY -> dropStack = new ItemStack(ModItems.STICKY_STRAWBERRY.get());
        }

        if (!dropStack.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(world, jelly.getX(), jelly.getY(), jelly.getZ(), dropStack);
            world.addFreshEntity(itemEntity);
        }
    }
}
