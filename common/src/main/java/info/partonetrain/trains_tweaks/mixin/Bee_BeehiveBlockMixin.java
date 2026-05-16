package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.bee.BeeFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BeehiveBlock.class)
public class Bee_BeehiveBlockMixin {
    @WrapOperation(method = "useItemOn", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/CampfireBlock;isSmokeyPos(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z"))
    public boolean trains_tweaks$useItemOn(Level level, BlockPos blockpos, Operation<Boolean> original){
        if (!AllFeatures.BEE_FEATURE.isIncompatibleLoaded() && BeeFeatureConfig.ENABLED.getAsBoolean() && BeeFeatureConfig.ALWAYS_SEDATED.getAsBoolean()) {
            return true;
        }
        return original.call(level, blockpos);
    }

    @ModifyArg(method = "dropHoneycomb", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;I)V"), index = 1)
    private static int trains_tweaks$dropHoneycomb(int count){
        if (!AllFeatures.BEE_FEATURE.isIncompatibleLoaded() && BeeFeatureConfig.ENABLED.getAsBoolean() && BeeFeatureConfig.HONEYCOMB_DROPPED.getAsInt() != BeeFeatureConfig.HONEYCOMB_DROPPED.getDefault()) {
            return BeeFeatureConfig.HONEYCOMB_DROPPED.getAsInt();
        }
        return count;
    }
}
