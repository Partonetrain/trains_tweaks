package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.xplosiv.XplosivFeatureConfig;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Creeper.class)
public class Xplosiv_CreeperMixin {

    @ModifyArg(method = "explodeCreeper", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"), index = 4)
    public float trains_tweaks$explodeCreeper(float original) {
        boolean notDefault = XplosivFeatureConfig.CREEPER_POWER.getAsInt() != XplosivFeatureConfig.CREEPER_POWER.getDefault() || XplosivFeatureConfig.CHARGED_CREEPER_POWER.getAsInt() != XplosivFeatureConfig.CHARGED_CREEPER_POWER.getDefault();
        if (!AllFeatures.XPLOSIV_FEATURE.isIncompatibleLoaded() && XplosivFeatureConfig.ENABLED.getAsBoolean() && notDefault){
            Creeper self = (Creeper)(Object)this;
            if(self.isPowered()){
                return XplosivFeatureConfig.CHARGED_CREEPER_POWER.getAsInt();
            }
            else{
                return XplosivFeatureConfig.CREEPER_POWER.getAsInt();
            }
        }
        return original;
    }
}
