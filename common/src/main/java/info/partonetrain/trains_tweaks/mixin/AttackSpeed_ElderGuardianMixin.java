package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedFeatureConfig;
import net.minecraft.world.entity.monster.ElderGuardian;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ElderGuardian.class)
public class AttackSpeed_ElderGuardianMixin {
    @ModifyArg(method = "customServerAiStep", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectInstance;<init>(Lnet/minecraft/core/Holder;II)V"), index = 2)
    public int trains_tweaks$customServerAiStep(int original) {
        if (!AllFeatures.ATTACK_SPEED_FEATURE.isIncompatibleLoaded() && AttackSpeedFeatureConfig.ENABLED.getAsBoolean()
                && AttackSpeedFeatureConfig.ELDER_GUARDIAN_FATIGUE_AMPLIFIER.getAsInt() > 2) {
            return AttackSpeedFeatureConfig.ELDER_GUARDIAN_FATIGUE_AMPLIFIER.getAsInt();
        }
        return original;
    }
}
