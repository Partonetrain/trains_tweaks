package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.horse.HorseFeature;
import info.partonetrain.trains_tweaks.feature.horse.HorseFeatureConfig;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractHorse.class)
public class Horse_AbstractHorseMixin {
    @ModifyReturnValue(method = "generateMaxHealth", at = @At("RETURN"))
    private static float trains_tweaks$generateMaxHealth(float original) {
        if (!AllFeatures.HORSE_FEATURE.isIncompatibleLoaded() && HorseFeature.enabled && HorseFeature.healthBuff > 0) {
            return (float) Math.max(original, HorseFeature.healthBuff);
        }
        return original;
    }

    @ModifyReturnValue(method = "generateSpeed", at = @At("RETURN"))
    private static double trains_tweaks$generateSpeed(double original) {
        if (!AllFeatures.HORSE_FEATURE.isIncompatibleLoaded() && HorseFeature.enabled && HorseFeature.speedBuff > 0) {
            return (float) Math.max(original, HorseFeature.speedBuff);
        }
        return original;
    }

    @ModifyReturnValue(method = "generateJumpStrength", at = @At("RETURN"))
    private static double trains_tweaks$generateJumpStrength(double original) {
        if (!AllFeatures.HORSE_FEATURE.isIncompatibleLoaded() && HorseFeature.enabled && HorseFeature.jumpBuff > 0) {
            return (float) Math.max(original, HorseFeature.jumpBuff);
        }
        return original;
    }

    @ModifyReturnValue(method = "createOffspringAttribute", at = @At("RETURN"))
    private static double trains_tweaks$createOffspringAttribute2(double original, double value1, double value2, double min, double max, RandomSource random) {
        if (!AllFeatures.HORSE_FEATURE.isIncompatibleLoaded() && HorseFeature.enabled && HorseFeatureConfig.GUARANTEE_GREATER_THAN_OR_EQUAL_TO_STATS.getAsBoolean()) {
            double average = (value1 + value2) / 2.0;
            //double difference = original - average;
            //Constants.LOG.info("original: " + original + " average: " + average + " difference: " + difference + " return: " + Math.max(original, average));
            return Math.max(original, average);
        }
        return original;
    }
}