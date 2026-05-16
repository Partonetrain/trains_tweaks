package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.bee.BeeFeatureConfig;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeehiveBlockEntity.class)
public class Bee_BeehiveBlockEntityMixin {
    @Inject(method = "isSedated", at=@At("HEAD"), cancellable = true)
    public void trains_tweaks$isSedated(CallbackInfoReturnable<Boolean> cir) {
        if (!AllFeatures.BEE_FEATURE.isIncompatibleLoaded() && BeeFeatureConfig.ENABLED.getAsBoolean() && BeeFeatureConfig.ALWAYS_SEDATED.getAsBoolean()) {
            cir.setReturnValue(true);
        }
    }

    @ModifyArg(method = "releaseOccupant", at= @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"), index = 0)
    private static int trains_tweaks$releaseOccupant(int bound){
        if (!AllFeatures.BEE_FEATURE.isIncompatibleLoaded() && BeeFeatureConfig.ENABLED.getAsBoolean() && BeeFeatureConfig.HONEY_LEVEL_BONUS_CHANCE.getAsInt() != BeeFeatureConfig.HONEY_LEVEL_BONUS_CHANCE.getDefault()) {
            return BeeFeatureConfig.HONEY_LEVEL_BONUS_CHANCE.getAsInt();
        }
        return bound;
    }
}
