package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.feature.trigger.TriggerFeature;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class Trigger_ServerPlayerMixin {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/critereon/PlayerTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;)V"))
    private void trains_tweaks$tick(CallbackInfo ci){
        TriggerFeature.DAY_TRIGGER.trigger((ServerPlayer)(Object)this);
        TriggerFeature.GAME_TIME_TRIGGER.trigger((ServerPlayer)(Object)this);
    }
}
