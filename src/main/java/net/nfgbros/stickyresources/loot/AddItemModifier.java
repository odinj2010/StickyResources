package net.nfgbros.stickyresources.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AddItemModifier extends LootModifier {

    // Codec for serializing/deserializing this modifier
    public static final Supplier<Codec<AddItemModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ForgeRegistries.ITEMS.getCodec()
                            .fieldOf("item")
                            .forGetter(m -> m.item))
                    .apply(inst, AddItemModifier::new)));

    // The item to be added to the loot
    private final Item item;

    /**
     * Constructor for AddItemModifier
     *
     * @param conditionsIn Array of loot conditions to check before applying
     * @param item         The item to add to the loot
     */
    public AddItemModifier(LootItemCondition[] conditionsIn, Item item) {
        super(conditionsIn);
        this.item = item;
    }

    /**
     * Applies the loot modifier logic. If all conditions are met, adds the specified item to
     * the generated loot list.
     *
     * @param generatedLoot The existing generated loot
     * @param context       The loot context containing information such as the loot source
     * @return The modified loot list
     */
    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        // Check all conditions; if any condition fails, return the unmodified loot
        for (LootItemCondition condition : this.conditions) {
            if (!condition.test(context)) {
                return generatedLoot;
            }
        }

        // Add the specified item to the loot
        generatedLoot.add(new ItemStack(this.item));

        return generatedLoot;
    }

    /**
     * Returns the codec for this loot modifier.
     *
     * @return The codec used for serialization and deserialization
     */
    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
