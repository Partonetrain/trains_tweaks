package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.zzz.ZzzFeature;
import info.partonetrain.trains_tweaks.feature.zzz.ZzzFeatureConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public class Zzz_ServerLevelMixin {
    @Final
    @Shadow
    List<ServerPlayer> players;

    @Inject(method="tick", at= @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;wakeUpAllPlayers()V"))
    public void trains_tweaks$tick(BooleanSupplier hasTimeLeft, CallbackInfo ci){
        if(!AllFeatures.ZZZ_FEATURE.isIncompatibleLoaded() && ZzzFeatureConfig.ENABLED.getAsBoolean()) {
            if(ZzzFeatureConfig.SLEEP_HEALS.getAsBoolean()){
                ZzzFeature.healPlayers(players);
            }
            if(ZzzFeatureConfig.SLEEP_REMOVES_EFFECTS.getAsBoolean()){
                ZzzFeature.removeDebuffs(players);
            }

        }
    }
}
