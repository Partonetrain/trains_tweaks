package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.xplosiv.XplosivFeatureConfig;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.WitherSkull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(WitherSkull.class)
public class Xplosiv_WitherSkullMixin {

    @ModifyArg(method = "onHit", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"), index = 4)
    public float trains_tweaks$onPlace(float original) {
        if (!AllFeatures.XPLOSIV_FEATURE.isIncompatibleLoaded() && XplosivFeatureConfig.ENABLED.getAsBoolean() && XplosivFeatureConfig.WITHER_SKULL_POWER.getAsInt() != XplosivFeatureConfig.WITHER_SKULL_POWER.getDefault()) {
            return XplosivFeatureConfig.WITHER_SPAWN_POWER.getAsInt();
        }
        return original;
    }

}
