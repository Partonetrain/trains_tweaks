package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
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
        if(!AllFeatures.RARITY_FEATURE.isIncompatibleLoaded() && RarityFeatureConfig.ENABLED.getAsBoolean() && RarityFeatureConfig.ITEMSTACK_CHECK_TAG.getAsBoolean()){
            ItemStack self = (ItemStack) (Object) this;
            cir.setReturnValue(RarityFeature.getTaggedRarity(self));
        }
        else if(!AllFeatures.RARITY_FEATURE.isIncompatibleLoaded() && RarityFeatureConfig.ENABLED.getAsBoolean() && RarityFeatureConfig.DATA_TAG_ENABLED.getAsBoolean()){
            ItemStack self = (ItemStack) (Object) this;
            cir.setReturnValue(RarityFeature.setTaggedRarity(self)); //will modify rarity component if it isn't already modified.
        }
    }

    @WrapOperation(method = "getRarity", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEnchanted()Z"))
    public boolean trains_tweaks$getRarity2(ItemStack instance, Operation<Boolean> original){
        if(!AllFeatures.RARITY_FEATURE.isIncompatibleLoaded() && RarityFeatureConfig.ENABLED.getAsBoolean() && RarityFeatureConfig.PREVENT_ENCHANTMENT_ALTERING.getAsBoolean()){
            return true; //will evaluate to false
        }
        return original.call(instance);
    }
}
