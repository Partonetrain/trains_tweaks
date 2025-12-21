package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.jumpy.JumpyFeatureConfig;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class Jumpy_LivingEntityMixin {
    @WrapOperation(method = "getJumpBoostPower", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/core/Holder;)Z"))
    public boolean trains_tweaks$getJumpBoostPower(LivingEntity instance, Holder<MobEffect> effect, Operation<Boolean> original){
        if(!AllFeatures.JUMPY_FEATURE.isIncompatibleLoaded() && JumpyFeatureConfig.ENABLED.getAsBoolean() && JumpyFeatureConfig.FIX_JUMPBOOST.get()){
            return false;
        }
        return original.call(instance, effect);
    }
}
