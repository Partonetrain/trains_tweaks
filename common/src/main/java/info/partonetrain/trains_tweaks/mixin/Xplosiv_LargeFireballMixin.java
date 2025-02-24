package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.xplosiv.XplosivFeatureConfig;
import net.minecraft.world.entity.projectile.LargeFireball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LargeFireball.class)
public class Xplosiv_LargeFireballMixin {
    @ModifyArg(method = "onHit", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"), index = 4)
    public float trains_tweaks$onHit(float original) {
        if (!AllFeatures.XPLOSIV_FEATURE.isIncompatibleLoaded() && XplosivFeatureConfig.ENABLED.getAsBoolean() && XplosivFeatureConfig.GHAST_FIREBALL_POWER.getAsInt() != XplosivFeatureConfig.GHAST_FIREBALL_POWER.getDefault()){
            return XplosivFeatureConfig.GHAST_FIREBALL_POWER.getAsInt();
        }
        return original;
    }
}
