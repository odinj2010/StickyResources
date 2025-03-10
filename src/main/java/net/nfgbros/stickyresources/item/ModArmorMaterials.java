package net.nfgbros.stickyresources.item;

import net.nfgbros.stickyresources.StickyResources;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    // Define the SAPPHIRE armor material
    SAPPHIRE("sapphire", 26, new int[]{5, 7, 5, 4}, 25,
            SoundEvents.ARMOR_EQUIP_GOLD, 1f, 0f, () -> Ingredient.of(ModItems.SAPPHIRE.get()));

    // Fields for the armor material properties
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    // Base durability for each armor slot in Minecraft
    private static final int[] BASE_DURABILITY = {11, 16, 16, 13};

    // Constructor to initialize all properties
    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound,
                      float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    // Returns the durability for the specified armor type
    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
    }

    // Returns the protection value for the specified armor type
    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionAmounts[pType.ordinal()];
    }

    // Returns the enchantment value of the armor
    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    // Returns the sound event when the armor is equipped
    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    // Returns the ingredient used for repairing the armor
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    // Returns the name of the armor material, prefixed with the mod ID
    @Override
    public String getName() {
        return StickyResources.MOD_ID + ":" + this.name;
    }

    // Returns the toughness value of the armor material
    @Override
    public float getToughness() {
        return this.toughness;
    }

    // Returns the knockback resistance of the armor material
    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
