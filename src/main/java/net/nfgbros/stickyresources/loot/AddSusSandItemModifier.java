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

public class AddSusSandItemModifier extends LootModifier {

    // Codec for serializing the AddSusSandItemModifier
    public static final Supplier<Codec<AddSusSandItemModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ForgeRegistries.ITEMS.getCodec()
                            .fieldOf("item")
                            .forGetter(m -> m.item))
                    .apply(inst, AddSusSandItemModifier::new)
            ));

    // The item to be added to the loot
    private final Item item;

    /**
     * Constructor for the AddSusSandItemModifier
     *
     * @param conditionsIn The conditions to check before applying the modifier
     * @param item         The item to add to the loot
     */
    public AddSusSandItemModifier(LootItemCondition[] conditionsIn, Item item) {
        super(conditionsIn);
        this.item = item;
    }

    /**
     * Applies the loot modifier logic.
     * If all conditions are met, there is a 50% chance to clear the existing loot
     * and replace it with the specified item.
     *
     * @param generatedLoot The loot generated before applying this modifier
     * @param context       The context of the loot
     * @return The modified loot list
     */
    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        // Check conditions; if any condition fails, return the original loot
        for (LootItemCondition condition : this.conditions) {
            if (!condition.test(context)) {
                return generatedLoot;
            }
        }

        // Apply a 50% chance to replace the loot with the specified item
        if (context.getRandom().nextFloat() < 0.5f) { // Adjust probability if needed
            generatedLoot.clear();
            generatedLoot.add(new ItemStack(this.item));
        }

        return generatedLoot;
    }

    /**
     * Returns the codec for this loot modifier.
     *
     * @return The codec to serialize/deserialize this modifier
     */
    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
