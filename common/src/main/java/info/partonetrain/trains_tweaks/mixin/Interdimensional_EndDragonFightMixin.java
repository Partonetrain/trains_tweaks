package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.interdimensional.InterdimensionalFeature;
import info.partonetrain.trains_tweaks.feature.interdimensional.InterdimensionalFeatureConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EndDragonFight.class, priority = 400) //lower than Yung's Better End Island = gets injected first
public class Interdimensional_EndDragonFightMixin {

    @Final
    @Shadow
    private ObjectArrayList<Integer> gateways;

    /*

    //This following commented-out inject DOES NOT WORK if Yung's Better End Island is installed! The method is cancelled

    @Inject(method = "setDragonKilled", at= @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    void trains_tweaks$setDragonKilled(EnderDragon dragon, CallbackInfo ci){
        if (!AllFeatures.INTERDIMENSIONAL_FEATURE.isIncompatibleLoaded() && InterdimensionalFeatureConfig.ENABLED.getAsBoolean()
                && InterdimensionalFeatureConfig.DRAGON_FIGHT_GATEWAY_SPAWNS.get() != InterdimensionalFeatureConfig.DRAGON_FIGHT_GATEWAY_SPAWNS.getDefault()) {
            //spawn gateways right as dragon egg is spawned
            EndDragonFight self = (EndDragonFight) (Object) this; //self-cast to access members and methods. IDE may not like this, but it works
            if (!gateways.isEmpty()) {
                for(BlockPos pos : InterdimensionalFeature.parseEndGatewaySpawns()){
                    CommonClass.printInDev("spawning gateway at " + pos);
                    self.spawnNewGateway(pos);
                }
            }
        }
    }
     */

    @Inject(method = "setDragonKilled", at= @At(value = "HEAD"))
    void trains_tweaks$setDragonKilled(EnderDragon dragon, CallbackInfo ci){
        if (!AllFeatures.INTERDIMENSIONAL_FEATURE.isIncompatibleLoaded() && InterdimensionalFeatureConfig.ENABLED.getAsBoolean()
                && InterdimensionalFeatureConfig.DRAGON_FIGHT_GATEWAY_SPAWNS.get() != InterdimensionalFeatureConfig.DRAGON_FIGHT_GATEWAY_SPAWNS.getDefault()) {
            //spawn gateways right as dragon egg is spawned
            EndDragonFight self = (EndDragonFight) (Object) this; //self-cast to access members and methods. IDE may not like this, but it works
            if (!self.hasPreviouslyKilledDragon() && !gateways.isEmpty()) {
                for(BlockPos pos : InterdimensionalFeature.parseEndGatewaySpawns()){
                    CommonClass.printInDev("spawning gateway at " + pos);
                    self.spawnNewGateway(pos);
                }
            }
        }
    }


    @Inject(method = "spawnNewGateway()V", at= @At(value = "HEAD"), cancellable = true)
    void trains_tweaks$spawnNewGateway(CallbackInfo ci){
        if (!AllFeatures.INTERDIMENSIONAL_FEATURE.isIncompatibleLoaded() && InterdimensionalFeatureConfig.ENABLED.getAsBoolean() && !InterdimensionalFeatureConfig.DRAGON_FIGHT_GATEWAY_SPAWNS.get().equals(InterdimensionalFeatureConfig.DRAGON_FIGHT_GATEWAY_SPAWNS.getDefault())) {
            //disable vanilla gateway spawning logic.
            Constants.LOG.info("spawnNewGateway() was called, but Interdimensional feature's Dragon Fight Gateway Spawns was set, so nothing happened");
            ci.cancel();
        }
    }
}
