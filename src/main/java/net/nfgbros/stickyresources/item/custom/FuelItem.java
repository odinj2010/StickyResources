package net.nfgbros.stickyresources.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class FuelItem extends Item {
    // Burn time for this fuel item in ticks
    private int burnTime = 0;

    // Constructor to initialize the item properties and burn time
    public FuelItem(Properties pProperties, int burnTime) {
        super(pProperties);
        this.burnTime = burnTime;
    }

    // Overrides the method to return this item's burn time for fuel recipes
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.burnTime;
    }
}
