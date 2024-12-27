package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.loot.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LootItemFunctions.class)
public class Loot_LootItemFunctionsMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void trains_tweaks$clinit(CallbackInfo ci){
        //NeoForge doesn't complain about this, but it might in the future!
        LootFeature.ENCHANT_TREASURE_FUNCTION = Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, Constants.ENCHANT_TREASURE, new LootItemFunctionType(EnchantTreasureFunction.CODEC));
        LootFeature.ENCHANT_CURSE_FUNCTION = Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, Constants.ENCHANT_CURSE, new LootItemFunctionType(EnchantCurseFunction.CODEC));
        LootFeature.ENCHANT_MAX_FUNCTION = Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, Constants.ENCHANT_MAX, new LootItemFunctionType(EnchantMaxFunction.CODEC));
        LootFeature.ENCHANT_ALL_FUNCTION = Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, Constants.ENCHANT_ALL, new LootItemFunctionType(EnchantAllFunction.CODEC));
    }
}
