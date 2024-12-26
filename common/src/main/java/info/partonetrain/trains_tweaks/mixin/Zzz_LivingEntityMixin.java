package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.zzz.ZzzFeatureConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class Zzz_LivingEntityMixin {
    @WrapOperation(method = "hurt", at=@At(value="INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;stopSleeping()V"))
    public void trains_tweaks$hurt(LivingEntity instance, Operation<Void> original, @Local(argsOnly = true) DamageSource source){
        if(!AllFeatures.ZZZ_FEATURE.isIncompatibleLoaded() && ZzzFeatureConfig.ENABLED.getAsBoolean()
            && ZzzFeatureConfig.SLEEP_THROUGH_DAMAGE.getAsBoolean()) {
            //Constants.LOG.info(source.type().toString());
            if(!source.is(Constants.SLEEP_THROUGH_DAMAGE_TAG)){
                original.call(instance);
            }
        }
        else
        {
            original.call(instance);
        }
    }
}
