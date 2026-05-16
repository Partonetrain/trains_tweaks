package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.interdimensional.InterdimensionalFeatureConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseFireBlock.class)
public class Interdimensional_BaseFireBlockMixin {
    @Inject(method = "inPortalDimension", at=@At("RETURN"), cancellable = true)
    private static void trains_tweaks$inPortalDimension(Level level, CallbackInfoReturnable<Boolean> cir){
        if (!AllFeatures.INTERDIMENSIONAL_FEATURE.isIncompatibleLoaded() && InterdimensionalFeatureConfig.ENABLED.getAsBoolean()
                && InterdimensionalFeatureConfig.NETHER_PORTAL_DIMENSIONS_TAG.getAsBoolean()) {
            cir.setReturnValue(level.dimensionTypeRegistration().is(Constants.SUPPORTS_NETHER_PORTALS_TAG));
        }
    }
}
