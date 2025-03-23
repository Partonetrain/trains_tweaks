package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.kritz.KritzFeature;
import info.partonetrain.trains_tweaks.feature.kritz.KritzFeatureConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ProjectileWeaponItem.class)
public class Kritz_ProjectileWeaponItemMixin {
    @ModifyArg(method = "shoot", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ProjectileWeaponItem;createProjectile(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Z)Lnet/minecraft/world/entity/projectile/Projectile;"), index = 4)
    public boolean trains_tweaks$shoot(boolean isCrit, @Local(ordinal = 0, argsOnly = true) LivingEntity shooter){
        if (!AllFeatures.KRITZ_FEATURE.isIncompatibleLoaded() && KritzFeatureConfig.ENABLED.getAsBoolean() && KritzFeatureConfig.ADD_ATTRIBUTES.getAsBoolean()) {
            if(shooter instanceof Player){
                float attributeValue = (float) shooter.getAttributeValue(KritzFeature.RANGED_CRIT_CHANCE);
                return shooter.getRandom().nextDouble() < attributeValue;
            }
        }
        return isCrit;
    }
}
