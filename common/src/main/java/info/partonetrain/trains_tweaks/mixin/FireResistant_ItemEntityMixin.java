package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.fireresistant.FireResistantFeatureConfig;
import info.partonetrain.trains_tweaks.platform.Services;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class FireResistant_ItemEntityMixin {
    @Inject(method = "fireImmune", at=@At("HEAD"), cancellable = true)
    public void trains_tweaks$fireImmune(CallbackInfoReturnable<Boolean> cir){
        if(!AllFeatures.FIRE_RESISTANT_FEATURE.isIncompatibleLoaded() && !Services.PLATFORM.isModLoaded("lychee") && FireResistantFeatureConfig.ENABLED.getAsBoolean()){
            ItemEntity self = (ItemEntity) (Object) this;
            if(self.getItem().is(Constants.FIRE_RESISTANT_TAG)){
                cir.setReturnValue(true);
            }
        }
        else if (!AllFeatures.FIRE_RESISTANT_FEATURE.isIncompatibleLoaded() && FireResistantFeatureConfig.ENABLED.getAsBoolean()){
            ItemEntity self = (ItemEntity) (Object) this;
            if(self.getItem().is(Constants.NOT_FIRE_RESISTANT_TAG)){
                cir.setReturnValue(false);
            }
        }
    }
}
