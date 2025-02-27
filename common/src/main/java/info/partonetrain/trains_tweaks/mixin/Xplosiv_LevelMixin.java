package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.xplosiv.XplosivFeatureConfig;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Level.class)
public class Xplosiv_LevelMixin {
    @ModifyArg(method = "explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;ZLnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/Holder;)Lnet/minecraft/world/level/Explosion;", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Explosion;<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Explosion$BlockInteraction;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/Holder;)V"), index = 8)
    public boolean trains_tweaks$explosionInit(boolean fire) {
        if (!AllFeatures.XPLOSIV_FEATURE.isIncompatibleLoaded() && XplosivFeatureConfig.ENABLED.getAsBoolean() && XplosivFeatureConfig.FIRE_EXPLOSIONS_IN_ULTRAWARM.getAsBoolean()){
            Level self = (Level)(Object)this;
            return self.dimensionType().ultraWarm();
        }
        return fire;
    }
}
