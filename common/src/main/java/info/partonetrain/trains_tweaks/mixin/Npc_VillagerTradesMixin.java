package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.npc.NpcFeatureConfig;
import net.minecraft.world.entity.npc.VillagerTrades;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(VillagerTrades.EnchantBookForEmeralds.class)
public class Npc_VillagerTradesMixin {
    @ModifyArg(method = "getOffer(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/util/RandomSource;)Lnet/minecraft/world/item/trading/MerchantOffer;", at= @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;nextInt(Lnet/minecraft/util/RandomSource;II)I"), index = 2)
    public int trains_tweaks$getOffer(int originalMaximum){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean() && NpcFeatureConfig.ENCHANTED_BOOK_MAX.getAsInt() != 0) {
            if(originalMaximum > NpcFeatureConfig.ENCHANTED_BOOK_MAX.getAsInt()){
                return NpcFeatureConfig.ENCHANTED_BOOK_MAX.getAsInt();
            }
        }
        return originalMaximum;
    }
}
