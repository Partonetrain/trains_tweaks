package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedFeature;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MobEffects.class)
public class AttackSpeed_MobEffectsMixin {

    //haste / DIG_SPEED
    @ModifyArg(method = "<clinit>", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffect;addAttributeModifier(Lnet/minecraft/core/Holder;Lnet/minecraft/resources/ResourceLocation;DLnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;)Lnet/minecraft/world/effect/MobEffect;", ordinal = 2), index = 0)
    private static Holder<Attribute> trains_tweaks$addAttributeModifier(Holder<Attribute> attribute){
        AttackSpeedFeature.readConfigsEarly();
        if (AttackSpeedFeature.enabled && AttackSpeedFeature.fixEffects) {
            return Attributes.BLOCK_BREAK_SPEED;
        }
        return attribute;
    }

    @ModifyArg(method = "<clinit>", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffect;addAttributeModifier(Lnet/minecraft/core/Holder;Lnet/minecraft/resources/ResourceLocation;DLnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;)Lnet/minecraft/world/effect/MobEffect;", ordinal = 2), index = 2)
    private static double trains_tweaks$addAttributeModifier2(double amount){
        if (AttackSpeedFeature.enabled && AttackSpeedFeature.fixEffects) {
            return AttackSpeedFeature.fixedEffectModifier;
        }
        return amount;
    }

    //mining fatigue / DIG_SLOWDOWN
    @ModifyArg(method = "<clinit>", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffect;addAttributeModifier(Lnet/minecraft/core/Holder;Lnet/minecraft/resources/ResourceLocation;DLnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;)Lnet/minecraft/world/effect/MobEffect;", ordinal = 3), index = 0)
    private static Holder<Attribute> trains_tweaks$addAttributeModifier3(Holder<Attribute> attribute){
        if (AttackSpeedFeature.enabled && AttackSpeedFeature.fixEffects) {
            return Attributes.BLOCK_BREAK_SPEED;
        }
        return attribute;
    }

    @ModifyArg(method = "<clinit>", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffect;addAttributeModifier(Lnet/minecraft/core/Holder;Lnet/minecraft/resources/ResourceLocation;DLnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;)Lnet/minecraft/world/effect/MobEffect;", ordinal = 3), index = 2)
    private static double trains_tweaks$addAttributeModifier4(double amount){
        if (AttackSpeedFeature.enabled && AttackSpeedFeature.fixEffects) {
            return AttackSpeedFeature.fixedEffectModifier * -1;
        }
        return amount;
    }
}
