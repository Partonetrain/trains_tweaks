package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.vehicle.VehicleFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WaterlilyBlock.class)
public class Vehicle_WaterLilyBlockMixin {
    @Inject(method = "entityInside", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;destroyBlock(Lnet/minecraft/core/BlockPos;ZLnet/minecraft/world/entity/Entity;)Z"), cancellable = true)
    private void trains_tweaks$entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci){
        //don't bother running the vanilla code after super call since the BoatMixin code should work instead
        if (!AllFeatures.VEHICLE_FEATURE.isIncompatibleLoaded() && VehicleFeatureConfig.ENABLED.getAsBoolean() && VehicleFeatureConfig.BETTER_BOAT_BREAKING.getAsBoolean()) {
            ci.cancel();
        }
    }
}
