package info.partonetrain.trains_tweaks.feature.loot;

import info.partonetrain.trains_tweaks.ModFeature;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.functions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LootFeature extends ModFeature {

    public LootFeature() {
        super("Loot", null);
    }

    public static LootItemFunctionType<EnchantTreasureFunction> ENCHANT_TREASURE_FUNCTION;
    public static LootItemFunctionType<EnchantCurseFunction> ENCHANT_CURSE_FUNCTION;
    public static LootItemFunctionType<EnchantMaxFunction> ENCHANT_MAX_FUNCTION;
    public static LootItemFunctionType<EnchantAllFunction> ENCHANT_ALL_FUNCTION;

    public static List<Holder<Enchantment>> sortEnchantmentsByValue(List<Holder<Enchantment>> enchantments){
        List<Holder<Enchantment>> list = new ArrayList<>(enchantments); //make mutable
        list.sort(new Comparator<Holder<Enchantment>>() {
            @Override
            public int compare(Holder<Enchantment> o1, Holder<Enchantment> o2) {
                //lower weight = rarer = more valuable
                return Integer.compare(o1.value().getWeight(), o2.value().getWeight());
            }
        });
        return list;
    }
}
