package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.feature.quasi.QuasiFeature;
import info.partonetrain.trains_tweaks.feature.quasi.QuasiFeatureConfig;
import info.partonetrain.trains_tweaks.feature.quasi.QuasiMode;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PistonBaseBlock.class)
public class Quasi_PistonBaseBlockMixin {

    //Moved to mod init events - UseBlockCallback (fabric) / PlayerInteractEvent.RightClickBlock (neo)
//    @Unique
//    @Override //might cause issues if something else does the same
//    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult){
//        if(!AllFeatures.QUASI_FEATURE.isIncompatibleLoaded() && QuasiFeatureConfig.ENABLED.getAsBoolean() &&
//                (QuasiFeatureConfig.PISTON_MODE.get() == QuasiMode.SHIFT_RCLICK_OPT_IN || QuasiFeatureConfig.PISTON_MODE.get() == QuasiMode.SHIFT_RCLICK_OPT_OUT)) {
//
//            if(player.isShiftKeyDown()){
//                boolean removedFromSave = QuasiFeature.updateCoordsInLevelData((ServerLevel) level, pos);
//
//                QuasiFeature.sendPlayerMessage((ServerPlayer)player, state, pos, removedFromSave);
//                return InteractionResult.CONSUME;
//            }
//
//        }
//        return InteractionResult.PASS; //default in BlockBehavior.
//    }

    @WrapOperation(method = "getNeighborSignal", at= @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;above()Lnet/minecraft/core/BlockPos;", ordinal = 0))
    public BlockPos trains_tweaks$getNeighborSignal(BlockPos instance, Operation<BlockPos> original, @Local(argsOnly = true) SignalGetter signalGetter){
        if(!AllFeatures.QUASI_FEATURE.isIncompatibleLoaded() && QuasiFeatureConfig.ENABLED.getAsBoolean()) {
            if(QuasiFeatureConfig.PISTON_MODE.get() == QuasiMode.DISABLED){
                CommonClass.printInDev("piston mode is disabled, returning unmodified blockpos");
                return instance;
            }
            else if(QuasiFeatureConfig.PISTON_MODE.get() == QuasiMode.SHIFT_RCLICK_OPT_IN){
                CommonClass.printInDev("piston mode is opt-in");
                if(QuasiFeature.isCoordsInLevelData((ServerLevel) signalGetter, instance)){
                    CommonClass.printInDev("blockpos is in save data, calling original");
                    return original.call(instance);
                }
                else{
                    CommonClass.printInDev("blockpos NOT in save data, returning unmodified blockpos");
                    return instance;
                }
            }
            else if(QuasiFeatureConfig.PISTON_MODE.get() == QuasiMode.SHIFT_RCLICK_OPT_OUT){
                CommonClass.printInDev("piston mode is opt-out");
                if(QuasiFeature.isCoordsInLevelData((ServerLevel) signalGetter, instance)){
                    CommonClass.printInDev("blockpos is in save data, returning unmodified blockpos");
                    return instance;
                }
                else{
                    CommonClass.printInDev("blockpos NOT in save data, calling original");
                    return original.call(instance);
                }
            }

        }
        return original.call(instance);
    }

//    @Inject(method = "onRemove", at=@At("HEAD"))
//    void trains_tweaks$onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving, CallbackInfo ci){
//        if(level instanceof ServerLevel sl){
//            QuasiFeature.updateCoordsInLevelData(sl, pos, true);
//        }
//    }


}
