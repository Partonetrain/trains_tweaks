package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.rarity.RarityFeatureConfig;
import net.minecraft.core.component.DataComponents;
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
            Rarity ret = (Rarity)self.getOrDefault(DataComponents.RARITY, Rarity.COMMON);
            if(RarityFeatureConfig.DATA_TAG_ENABLED.getAsBoolean()){
                if(RarityFeatureConfig.DATA_TAG_ENABLED.getAsBoolean()){
                    if (self.is(Constants.COMMON_TAG)) {
                        self.set(DataComponents.RARITY, Rarity.COMMON);
                    }
                    else if(self.is(Constants.UNCOMMON_TAG)){
                        self.set(DataComponents.RARITY, Rarity.UNCOMMON);
                    }
                    else if(self.is(Constants.RARE_TAG)){
                        self.set(DataComponents.RARITY, Rarity.RARE);
                    }
                    else if(self.is(Constants.EPIC_TAG)){
                        self.set(DataComponents.RARITY, Rarity.EPIC);
                    }
                }
                ret = (Rarity)self.getOrDefault(DataComponents.RARITY, Rarity.COMMON);
            }
            cir.setReturnValue(ret);
            cir.cancel();
        }
    }
}
