package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedEffects;
import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedFeature;
import info.partonetrain.trains_tweaks.feature.kritz.KritzEffects;
import info.partonetrain.trains_tweaks.feature.kritz.KritzFeature;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEffects.class)
public class Fabric_Kritz_MobEffectsMixin {
    @Inject(method = "<clinit>", at=@At("TAIL"))
    private static void trains_tweaks$clinit(CallbackInfo ci){
        if(KritzFeature.enabled && KritzFeature.addEffects) {
            KritzEffects.fabricInit();
        }
    }
}
