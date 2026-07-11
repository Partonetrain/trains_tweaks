package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.yeet.YeetFeatureConfig;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProjectileWeaponItem.class)
public class Yeet_ProjectileWeaponItemMixin {
    @ModifyReturnValue(method = "getEnchantmentValue", at=@At("RETURN"))
    public int trains_tweaks$getEnchantmentValue(int original){
        if(!AllFeatures.YEET_FEATURE.isIncompatibleLoaded() && YeetFeatureConfig.ENABLED.getAsBoolean()
                && YeetFeatureConfig.PROJECTILE_WEAPON_ENCHANTABILITY.get() != YeetFeatureConfig.PROJECTILE_WEAPON_ENCHANTABILITY.getDefault()) {
            return YeetFeatureConfig.PROJECTILE_WEAPON_ENCHANTABILITY.get();
        }
        return original;
    }
}
