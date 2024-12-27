package info.partonetrain.trains_tweaks.feature.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class EnchantAllFunction extends LootItemConditionalFunction {

    public static final MapCodec<EnchantAllFunction> CODEC = RecordCodecBuilder.mapCodec(
            recordCodecBuilder -> commonFields(recordCodecBuilder)
                    .and(
                            recordCodecBuilder.group(
                                    RegistryCodecs.homogeneousList(Registries.ENCHANTMENT).optionalFieldOf("options").forGetter(thisAllFunction -> thisAllFunction.options),
                                    Codec.BOOL.optionalFieldOf("check_compatible", Boolean.TRUE).forGetter(thisAllFunction -> thisAllFunction.checkCompatibile),
                                    Codec.BOOL.optionalFieldOf("sort", Boolean.TRUE).forGetter(thisAllFunction -> thisAllFunction.checkCompatibile)
                            )
                    )
                    .apply(recordCodecBuilder, EnchantAllFunction::new)
    );
    private final Optional<HolderSet<Enchantment>> options;
    private final boolean checkCompatibile;
    private final boolean sort;

    EnchantAllFunction(List<LootItemCondition> conditons, Optional<HolderSet<Enchantment>> options, boolean checkCompatibile, boolean sort) {
        super(conditons);
        this.options = options;
        this.checkCompatibile = checkCompatibile;
        this.sort = sort;
    }

    public @NotNull LootItemFunctionType<EnchantAllFunction> getType() {
        return LootFeature.ENCHANT_ALL_FUNCTION;
    }

    public @NotNull ItemStack run(ItemStack stack, LootContext context) {
        boolean isBook = stack.is(Items.BOOK);
        boolean checkCompatibility = !isBook && this.checkCompatibile;
        boolean sort = this.sort;
        Stream<Holder<Enchantment>> stream = this.options
                .map(HolderSet::stream)
                .orElseGet(() -> context.getLevel().registryAccess().registryOrThrow(Registries.ENCHANTMENT).holders().map(Function.identity()))
                .filter(enchantmentHolder -> (!checkCompatibility || enchantmentHolder.value().canEnchant(stack))
                    && !enchantmentHolder.is(EnchantmentTags.CURSE));
        List<Holder<Enchantment>> list = stream.toList();
        if (list.isEmpty()) {
            Constants.LOG.warn("Couldn't find a compatible enchantment for {}", stack);
            return stack;
        } else {
            return enchantItem(stack, list, checkCompatibility, sort);
        }
    }

    private static ItemStack enchantItem(ItemStack itemStack, List<Holder<Enchantment>> enchantments, boolean checkCompatible, boolean sort) {
        if (itemStack.is(Items.BOOK)) {
            itemStack = new ItemStack(Items.ENCHANTED_BOOK);
        }

        if(sort){
            enchantments = LootFeature.sortEnchantmentsByValue(enchantments);
        }

        for (Holder<Enchantment> enchantment : enchantments) {
            int level = enchantment.value().getMaxLevel();
            if (checkCompatible){
                if(EnchantmentHelper.isEnchantmentCompatible(itemStack.getEnchantments().keySet(), enchantment)) {
                    itemStack.enchant(enchantment, level);
                }
            }
            else{
                if(enchantment.value().canEnchant(itemStack)) {
                    itemStack.enchant(enchantment, level);
                }
            }
        }
        return itemStack;
    }
}
