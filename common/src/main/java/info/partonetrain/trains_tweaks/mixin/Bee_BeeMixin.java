package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.bee.BeeFeatureConfig;
import net.minecraft.world.entity.animal.Bee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bee.class)
public class Bee_BeeMixin {
    @Inject(method = "setHasStung", at=@At("HEAD"), cancellable = true)
    public void trains_tweaks$setHasStung(boolean hasStung, CallbackInfo ci) {
        if (!AllFeatures.BEE_FEATURE.isIncompatibleLoaded() && BeeFeatureConfig.ENABLED.getAsBoolean() && BeeFeatureConfig.BEES_KEEP_STINGER.getAsBoolean()) {
            ci.cancel();
        }
    }
}
