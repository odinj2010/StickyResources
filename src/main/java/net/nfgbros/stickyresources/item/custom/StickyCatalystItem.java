package net.nfgbros.stickyresources.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class StickyCatalystItem extends Item {

    public StickyCatalystItem(Properties properties) {
        super(properties);
    }
    @Override
    public boolean isFoil(ItemStack stack) {
        return true; // Makes the item have an enchantment-like glow
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.sticky_resources.sticky_catalyst.shift"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.sticky_resources.sticky_catalyst.no_shift"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}