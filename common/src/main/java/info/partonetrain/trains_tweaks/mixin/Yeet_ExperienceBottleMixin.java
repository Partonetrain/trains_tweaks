package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.yeet.YeetFeatureConfig;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ThrownExperienceBottle.class)
public class Yeet_ExperienceBottleMixin {
    @ModifyArg(method="onHit", at=@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"), index = 2)
    public int trains_tweaks$onHit(int amount){
        if(!AllFeatures.YEET_FEATURE.isIncompatibleLoaded() && YeetFeatureConfig.ENABLED.getAsBoolean() && YeetFeatureConfig.EXPERIENCE_BOTTLE_AMOUNT.getAsInt() > -1){
            return YeetFeatureConfig.EXPERIENCE_BOTTLE_AMOUNT.getAsInt();
        }
        return amount;
    }
}
