package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.yeet.YeetFeatureConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Snowball.class)
public class Yeet_SnowballMixin {
    @Inject(method = "onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V", at=@At("RETURN"))
    public void trains_tweaks$onHitEntity(EntityHitResult result, CallbackInfo ci){
        if(!AllFeatures.YEET_FEATURE.isIncompatibleLoaded() && YeetFeatureConfig.ENABLED.getAsBoolean() && YeetFeatureConfig.SNOWBALL_FREEZE_TICKS.getAsInt() > 0) {
            Entity e = result.getEntity();
            int f = e.getTicksFrozen();
            if (f <= YeetFeatureConfig.SNOWBALL_FREEZE_MAX.getAsInt() && e.canFreeze()) {
                result.getEntity().setTicksFrozen(Math.min(f + YeetFeatureConfig.SNOWBALL_FREEZE_TICKS.getAsInt(), YeetFeatureConfig.SNOWBALL_FREEZE_MAX.getAsInt()));
            }
            Constants.LOG.info(String.valueOf((result.getEntity().getTicksFrozen())));
        }
    }
}
