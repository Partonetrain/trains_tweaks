package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedFeatureConfig;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class NeoForge_AttackSpeed_PlayerMixin {
    //Wraps this.hasEffect(MobEffects.DIG_SLOWDOWN) in Player#getDigSpeed
    //NeoForge deprecates the vanilla getDestroySpeed method
    @WrapOperation(method = "getDigSpeed", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;hasEffect(Lnet/minecraft/core/Holder;)Z"))
    public boolean trains_tweaks$getDestroySpeed(Player instance, Holder holder, Operation<Boolean> original){
        if(!AllFeatures.ATTACK_SPEED_FEATURE.isIncompatibleLoaded() && AttackSpeedFeatureConfig.ENABLED.getAsBoolean()
                && AttackSpeedFeatureConfig.FIX_EFFECTS.getAsBoolean()) {
            return false;
        }
        else
        {
            return original.call(instance, holder);
        }
    }
}
