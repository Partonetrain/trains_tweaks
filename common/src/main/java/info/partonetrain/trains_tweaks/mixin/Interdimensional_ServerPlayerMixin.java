package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.interdimensional.InterdimensionalFeature;
import info.partonetrain.trains_tweaks.feature.interdimensional.InterdimensionalFeatureConfig;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class Interdimensional_ServerPlayerMixin {
    @Inject(method = "tick", at=@At("TAIL"))
    public void trains_tweaks$tick(CallbackInfo ci){
        if(!AllFeatures.INTERDIMENSIONAL_FEATURE.isIncompatibleLoaded() && InterdimensionalFeatureConfig.ENABLE_EFFECT_RESTRICTIONS.getAsBoolean()){
            ServerPlayer self = (ServerPlayer) (Object) this;
            InterdimensionalFeature.applyEffectRestrictions(self);
        }
    }
}
