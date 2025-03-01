package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.goat.GoatFeatureConfig;
import net.minecraft.world.entity.animal.goat.Goat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Goat.class)
public class Goat_GoatMixin {
    @ModifyExpressionValue(method = "finalizeSpawn", at = @At(value = "CONSTANT", args = "doubleValue=0.02"))
    public double trains_tweaks$finalizeSpawn(double original) {
        if (!AllFeatures.GOAT_FEATURE.isIncompatibleLoaded() && GoatFeatureConfig.ENABLED.getAsBoolean() && GoatFeatureConfig.SCREAMING_GOAT_SPAWN_CHANCE.getAsDouble() != GoatFeatureConfig.SCREAMING_GOAT_SPAWN_CHANCE.getDefault()) {
            return GoatFeatureConfig.SCREAMING_GOAT_SPAWN_CHANCE.getAsDouble();
        }
        return original;
    }
}
