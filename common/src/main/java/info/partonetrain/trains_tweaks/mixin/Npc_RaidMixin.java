package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.npc.NpcFeatureConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Raid.class)
public class Npc_RaidMixin {
    @WrapOperation(method = "tick", at=@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"))
    public boolean trains_tweaks$tick(LivingEntity instance, MobEffectInstance effectInstance, Operation<Boolean> original){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean() && NpcFeatureConfig.DISABLE_HERO_EFFECT.getAsBoolean()) {
            return false;
        }
        return original.call(instance, effectInstance);
    }
}
