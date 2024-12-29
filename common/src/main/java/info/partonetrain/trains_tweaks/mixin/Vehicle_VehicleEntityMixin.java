package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.vehicle.VehicleFeatureConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VehicleEntity.class)
public class Vehicle_VehicleEntityMixin {
    @Inject(method = "hurt", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/VehicleEntity;gameEvent(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/Entity;)V"))
    public void trains_tweaks$hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!AllFeatures.VEHICLE_FEATURE.isIncompatibleLoaded() && VehicleFeatureConfig.ENABLED.getAsBoolean() && VehicleFeatureConfig.INSTANT_BREAK.getAsBoolean()) {
            if(source.getEntity() instanceof Player){
                VehicleEntity self = (VehicleEntity) (Object) this;
                self.setDamage(self.getDamage() + 40F);
            }
        }
    }
}
