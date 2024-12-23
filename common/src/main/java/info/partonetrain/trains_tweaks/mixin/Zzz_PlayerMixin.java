package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.zzz.ZzzFeatureConfig;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class Zzz_PlayerMixin {

    @Shadow
    private int sleepCounter;

    @ModifyReturnValue(method="isSleepingLongEnough", at=@At("RETURN"))
    public boolean trains_tweaks$isSleepingLongEnough(boolean original){
        if(!AllFeatures.ZZZ_FEATURE.isIncompatibleLoaded() && ZzzFeatureConfig.ENABLED.getAsBoolean()) {
            Player self = (Player)(Object) this;
            if(ZzzFeatureConfig.CREATIVE_INSTANT_SLEEP.getAsBoolean() && self.getAbilities().instabuild){
                return self.isSleeping();
            }
            return self.isSleeping() && self.getSleepTimer() >= ZzzFeatureConfig.SLEEP_REQUIRED_TICKS.getAsInt();
        }
        return original;
    }
}
