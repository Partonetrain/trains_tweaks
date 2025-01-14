package info.partonetrain.trains_tweaks.feature.trigger;

import info.partonetrain.trains_tweaks.ModFeature;
import info.partonetrain.trains_tweaks.feature.loot.EnchantAllFunction;
import info.partonetrain.trains_tweaks.feature.loot.EnchantCurseFunction;
import info.partonetrain.trains_tweaks.feature.loot.EnchantMaxFunction;
import info.partonetrain.trains_tweaks.feature.loot.EnchantTreasureFunction;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TriggerFeature extends ModFeature {

    public TriggerFeature() {
        super("Trigger", null);
    }

    public static GameTimeTrigger GAME_TIME_TRIGGER;
    public static DayTrigger DAY_TRIGGER;
}
