package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.difficulty.DifficultyFeatureConfig;
import net.minecraft.world.DifficultyInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DifficultyInstance.class)
public class Difficulty_DifficultyInstanceMixin {
    @ModifyReturnValue(method = "calculateDifficulty", at=@At("RETURN"))
    public float trains_tweaks$calculateDifficulty(float original){
        if(!AllFeatures.DIFFICULTY_FEATURE.isIncompatibleLoaded() && DifficultyFeatureConfig.ENABLED.getAsBoolean()
        && DifficultyFeatureConfig.REGIONAL_DIFFICULTY_MOD.getAsDouble() != 0){
            return (float) Math.max(original + DifficultyFeatureConfig.REGIONAL_DIFFICULTY_MOD.getAsDouble(), 0);
        }
        return original;
    }

    @ModifyReturnValue(method = "getSpecialMultiplier", at=@At("RETURN"))
    public float trains_tweaks$getSpecialMultiplier(float original){
        if(!AllFeatures.DIFFICULTY_FEATURE.isIncompatibleLoaded() && DifficultyFeatureConfig.ENABLED.getAsBoolean()
                && DifficultyFeatureConfig.SPECIAL_MULTIPLIER_MOD.getAsDouble() != 0){
            return (float) Math.max(original + DifficultyFeatureConfig.SPECIAL_MULTIPLIER_MOD.getAsDouble(), 0);
        }
        return original;
    }
}
