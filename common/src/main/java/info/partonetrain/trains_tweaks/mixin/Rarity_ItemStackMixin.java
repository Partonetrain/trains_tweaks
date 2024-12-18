package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.rarity.RarityFeature;
import info.partonetrain.trains_tweaks.feature.rarity.RarityFeatureConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class Rarity_ItemStackMixin {
    @Inject(method = "getRarity", at=@At("HEAD"), cancellable = true)
    public void trains_tweaks$getRarity(CallbackInfoReturnable<Rarity> cir){
        if(!AllFeatures.RARITY_FEATURE.isIncompatibleLoaded() && RarityFeatureConfig.PREVENT_ENCHANTMENT_ALTERING.getAsBoolean()){
            ItemStack self = (ItemStack) (Object) this;
            cir.setReturnValue(RarityFeature.setTaggedRarity(self));
            cir.cancel();
        }
    }
}
