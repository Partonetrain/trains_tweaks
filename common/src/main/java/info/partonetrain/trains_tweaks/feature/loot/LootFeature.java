package info.partonetrain.trains_tweaks.feature.loot;

import info.partonetrain.trains_tweaks.ModFeature;
import net.minecraft.world.level.storage.loot.functions.*;

public class LootFeature extends ModFeature {

    public LootFeature() {
        super("Loot", null);
    }

    public static LootItemFunctionType<EnchantTreasureFunction> ENCHANT_TREASURE_FUNCTION;
    public static LootItemFunctionType<EnchantCurseFunction> ENCHANT_CURSE_FUNCTION;
}
