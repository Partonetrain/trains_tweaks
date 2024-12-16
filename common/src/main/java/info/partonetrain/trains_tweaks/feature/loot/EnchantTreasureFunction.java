package info.partonetrain.trains_tweaks.feature.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class EnchantTreasureFunction extends LootItemConditionalFunction {

    public static final MapCodec<EnchantTreasureFunction> CODEC = RecordCodecBuilder.mapCodec(
            recordCodecBuilder -> commonFields(recordCodecBuilder)
                    .and(
                            recordCodecBuilder.group(
                                    RegistryCodecs.homogeneousList(Registries.ENCHANTMENT).optionalFieldOf("options").forGetter(enchantTreasureFunction -> enchantTreasureFunction.options),
                                    Codec.BOOL.optionalFieldOf("only_compatible", Boolean.valueOf(true)).forGetter(thisTreasureFunction -> thisTreasureFunction.onlyCompatible)
                            )
                    )
                    .apply(recordCodecBuilder, EnchantTreasureFunction::new)
    );
    private final Optional<HolderSet<Enchantment>> options;
    private final boolean onlyCompatible;

    EnchantTreasureFunction(List<LootItemCondition> conditons, Optional<HolderSet<Enchantment>> options, boolean onlyCompatible) {
        super(conditons);
        this.options = options;
        this.onlyCompatible = onlyCompatible;
    }

    public @NotNull LootItemFunctionType<EnchantRandomlyFunction> getType() {
        return LootItemFunctions.ENCHANT_RANDOMLY;
    }

    public @NotNull ItemStack run(ItemStack stack, LootContext context) {
        RandomSource randomsource = context.getRandom();
        boolean isBook = stack.is(Items.BOOK);
        boolean checkCompatibility = !isBook && this.onlyCompatible;
        Stream<Holder<Enchantment>> stream = this.options
                .map(HolderSet::stream)
                .orElseGet(() -> context.getLevel().registryAccess().registryOrThrow(Registries.ENCHANTMENT).holders().map(Function.identity()))
                .filter(enchantmentHolder -> (!checkCompatibility || enchantmentHolder.value().canEnchant(stack))
                    && enchantmentHolder.is(EnchantmentTags.TREASURE) && !enchantmentHolder.is(EnchantmentTags.CURSE));
        List<Holder<Enchantment>> list = stream.toList();
        Optional<Holder<Enchantment>> optional = Util.getRandomSafe(list, randomsource);
        if (optional.isEmpty()) {
            Constants.LOG.warn("Couldn't find a compatible non-curse treasure enchantment for {}", stack);
            return stack;
        } else {
            return enchantItem(stack, optional.get());
        }
    }

    private static ItemStack enchantItem(ItemStack itemStack, Holder<Enchantment> enchantment) {
        int level = enchantment.value().getMaxLevel();
        if (itemStack.is(Items.BOOK)) {
            itemStack = new ItemStack(Items.ENCHANTED_BOOK);
        }

        itemStack.enchant(enchantment, level);
        return itemStack;
    }
}
