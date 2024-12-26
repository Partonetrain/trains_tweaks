package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedFeatureConfig;
import net.minecraft.world.effect.MobEffectUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEffectUtil.class)
public class AttackSpeed_MobEffectUtilMixin {

    @ModifyReturnValue(method = "hasDigSpeed", at=@At(value = "RETURN"))
    private static boolean trains_tweaks$hasDigSpeed(boolean original) {
        if (!AllFeatures.ATTACK_SPEED_FEATURE.isIncompatibleLoaded() && AttackSpeedFeatureConfig.ENABLED.getAsBoolean()
                && AttackSpeedFeatureConfig.FIX_EFFECTS.getAsBoolean()) {
            return false;
        }
        return original;
    }


    @ModifyReturnValue(method = "getDigSpeedAmplification", at=@At(value = "RETURN"))
    private static int trains_tweaks$getDigSpeedAmplification(int original){
        if (!AllFeatures.ATTACK_SPEED_FEATURE.isIncompatibleLoaded() && AttackSpeedFeatureConfig.ENABLED.getAsBoolean()
                && AttackSpeedFeatureConfig.FIX_EFFECTS.getAsBoolean()) {
            return 0;
        }
        return original;
    }
}
