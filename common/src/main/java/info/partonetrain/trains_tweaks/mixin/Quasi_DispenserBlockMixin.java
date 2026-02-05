package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.feature.quasi.QuasiFeature;
import info.partonetrain.trains_tweaks.feature.quasi.QuasiFeatureConfig;
import info.partonetrain.trains_tweaks.feature.quasi.QuasiMode;
import info.partonetrain.trains_tweaks.feature.quasi.QuasiSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DispenserBlock.class)
public class Quasi_DispenserBlockMixin {

    @Inject(method = "useWithoutItem", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"), cancellable = true)
    public void trains_tweaks$useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir){
        if(!AllFeatures.QUASI_FEATURE.isIncompatibleLoaded() && QuasiFeatureConfig.ENABLED.getAsBoolean() &&
                (QuasiFeatureConfig.DISPENSER_MODE.get() == QuasiMode.SHIFT_RCLICK_OPT_IN || QuasiFeatureConfig.DISPENSER_MODE.get() == QuasiMode.SHIFT_RCLICK_OPT_OUT)) {

            if(player.isShiftKeyDown()){
                boolean removedFromSave = QuasiFeature.updateCoordsInLevelData((ServerLevel) level, pos);

                QuasiFeature.sendPlayerMessage((ServerPlayer)player, state, pos, removedFromSave);
                cir.setReturnValue(InteractionResult.CONSUME);
            }

        }
    }

    @WrapOperation(method = "neighborChanged", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z", ordinal = 1))
    public boolean trains_tweaks$neighborChanged(Level instance, BlockPos blockPos, Operation<Boolean> original){
        if(!AllFeatures.QUASI_FEATURE.isIncompatibleLoaded() && QuasiFeatureConfig.ENABLED.getAsBoolean()) {
            if(QuasiFeatureConfig.DISPENSER_MODE.get() == QuasiMode.DISABLED){
                return false;
            }
            else if(QuasiFeatureConfig.DISPENSER_MODE.get() == QuasiMode.SHIFT_RCLICK_OPT_IN){
                CommonClass.printInDev("dispenser mode is opt-in");
                if(QuasiFeature.isCoordsInLevelData((ServerLevel) instance, blockPos.below())){
                    CommonClass.printInDev("blockpos is in save data, calling original");
                    return original.call(instance, blockPos);
                }
                else{
                    CommonClass.printInDev("blockpos NOT in save data, returning false");
                    return false;
                }
            }
            else if(QuasiFeatureConfig.DISPENSER_MODE.get() == QuasiMode.SHIFT_RCLICK_OPT_OUT){
                CommonClass.printInDev("dispenser mode is opt-out");
                if(QuasiFeature.isCoordsInLevelData((ServerLevel) instance, blockPos.below())){
                    CommonClass.printInDev("blockpos is in save data, returning false");
                    return false;
                }
                else{
                    CommonClass.printInDev("blockpos NOT in save data, calling original");
                    return original.call(instance, blockPos);
                }
            }

        }
        return original.call(instance, blockPos);
    }

//    @Inject(method = "onRemove", at=@At("HEAD"))
//    void trains_tweaks$onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving, CallbackInfo ci){
//        if(level instanceof ServerLevel sl){
//            QuasiFeature.updateCoordsInLevelData(sl, pos, true);
//        }
//    }
}
