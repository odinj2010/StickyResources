package net.nfgbros.stickyresources.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    // Food item: Strawberry
    public static final FoodProperties STRAWBERRY = new FoodProperties.Builder()
            .nutrition(2)                  // Provides 2 units of nutrition
            .fast()                        // Consumed quickly
            .saturationMod(0.2f)           // Gives 0.2 saturation
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 0.1f) // 10% chance to apply speed boost for 200 ticks
            .build();

}
