package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.IEarlyConfigReader;
import info.partonetrain.trains_tweaks.feature.jumpy.JumpyFeature;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEffects.class)
public class Jumpy_MobEffectsMixin {
    //jump boost / JUMP
    @WrapOperation(method = "<clinit>", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffects;register(Ljava/lang/String;Lnet/minecraft/world/effect/MobEffect;)Lnet/minecraft/core/Holder;", ordinal = 7))
    private static Holder<MobEffect> trains_tweaks$addAttributeModifier(String name, MobEffect effect, Operation<Holder<MobEffect>> original){
        Constants.LOG.info("jumpy effect tweak here");
        IEarlyConfigReader ecr = (IEarlyConfigReader) (AllFeatures.JUMPY_FEATURE);
        ecr.readConfigsEarly();
        if (JumpyFeature.enabled && JumpyFeature.fixEffect) {
            Constants.LOG.info("jumpy effect tweak on");
            return original.call(name, effect.addAttributeModifier(Attributes.JUMP_STRENGTH, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "effect.jump_boost"), JumpyFeature.fixedEffectModifier, AttributeModifier.Operation.ADD_VALUE));
        }
        Constants.LOG.info("jumpy effect tweak off");
        return original.call(name, effect);
    }
}
