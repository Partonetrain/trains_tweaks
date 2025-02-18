package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.zzz.ZzzFeatureConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RespawnAnchorBlock.class)
public class Zzz_RespawnAnchorBlockMixin {

    @ModifyReturnValue(method = "isRespawnFuel", at= @At(value = "RETURN"))
    private static boolean trains_tweaks$isRespawnFuel(boolean original, ItemStack stack) {
        if (!AllFeatures.ZZZ_FEATURE.isIncompatibleLoaded() && ZzzFeatureConfig.ENABLED.getAsBoolean() && ZzzFeatureConfig.RESPAWN_ANCHOR_FUEL.getAsBoolean()){
            return stack.is(Constants.RESPAWN_FUEL_TAG);
        }
        return original;
    }

}
