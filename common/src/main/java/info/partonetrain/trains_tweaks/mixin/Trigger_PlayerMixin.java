package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.feature.trigger.TriggerFeature;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class Trigger_PlayerMixin {
    @Inject(method = "actuallyHurt", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;gameEvent(Lnet/minecraft/core/Holder;)V"))
    private void trains_tweask$actuallyHurt(DamageSource damageSrc, float damageAmount, CallbackInfo ci){
        if(TriggerFeature.enabled && TriggerFeature.takeDamageAndLiveEnabled) {
            Player self = (Player) (Object) this;
            if(self instanceof ServerPlayer player){
                TriggerFeature.TAKE_DAMAGE_AND_LIVE_TRIGGER.trigger(player, (int) damageAmount); //truncates
            }
        }
    }
}
