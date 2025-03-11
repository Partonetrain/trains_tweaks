package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.horse.HorseFeature;
import info.partonetrain.trains_tweaks.feature.horse.HorseFeatureConfig;
import net.minecraft.world.entity.animal.horse.Llama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Llama.class)
public class Horse_LlamaMixin {
    @ModifyArg(method = "setStrength", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"), index = 0)
    private int trains_tweaks$setRandomStrength(int a) {
        if (!AllFeatures.HORSE_FEATURE.isIncompatibleLoaded() && HorseFeature.enabled && HorseFeatureConfig.LLAMA_STRENGTH_BUFF.getAsInt() != 0) {
            Llama self = (Llama)(Object)this;
            return HorseFeatureConfig.LLAMA_STRENGTH_BUFF.getAsInt();
        }
        return a;
    }
}