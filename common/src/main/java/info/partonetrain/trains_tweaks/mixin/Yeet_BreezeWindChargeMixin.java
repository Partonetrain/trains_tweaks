package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.yeet.YeetFeatureConfig;
import net.minecraft.world.entity.projectile.windcharge.BreezeWindCharge;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BreezeWindCharge.class)
public class Yeet_BreezeWindChargeMixin {
    @ModifyArg(method = "explode", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/Holder;)Lnet/minecraft/world/level/Explosion;"), index = 8)
    public Level.ExplosionInteraction trains_tweaks$explode(Level.ExplosionInteraction explosionInteraction){
        if(!AllFeatures.YEET_FEATURE.isIncompatibleLoaded() && YeetFeatureConfig.ENABLED.getAsBoolean()
                && YeetFeatureConfig.PREVENT_WIND_INTERACTIONS.getAsBoolean()) {
            return Level.ExplosionInteraction.NONE;
        }
        return explosionInteraction;
    }
}
