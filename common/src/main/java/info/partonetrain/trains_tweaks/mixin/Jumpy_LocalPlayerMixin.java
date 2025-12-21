package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.jumpy.JumpyFeatureConfig;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public class Jumpy_LocalPlayerMixin {
    @WrapOperation(method = "updateAutoJump", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/core/Holder;)Z"))
    public boolean trains_tweaks$updateAutoJump(LocalPlayer instance, Holder<MobEffect> holder, Operation<Boolean> original){
        if(!AllFeatures.JUMPY_FEATURE.isIncompatibleLoaded() && JumpyFeatureConfig.ENABLED.getAsBoolean() && JumpyFeatureConfig.FIX_JUMPBOOST.get()){
            return false;
        }
        return original.call(instance, holder);
    }
}
