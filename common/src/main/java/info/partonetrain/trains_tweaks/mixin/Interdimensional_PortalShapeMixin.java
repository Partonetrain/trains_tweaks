package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.interdimensional.InterdimensionalFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.portal.PortalShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PortalShape.class)
public class Interdimensional_PortalShapeMixin {
    @Shadow
    @Final
    @Mutable //make final field mutable
    private static BlockBehaviour.StatePredicate FRAME;

    @Inject(method = {"<init>(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction$Axis;)V"}, at = {@At("RETURN")})
    private void trains_tweaks$init(LevelAccessor level, BlockPos bottomLeft, Direction.Axis axis, CallbackInfo ci){
        if (!AllFeatures.INTERDIMENSIONAL_FEATURE.isIncompatibleLoaded() && InterdimensionalFeatureConfig.NETHER_PORTAL_BLOCK_TAG.getAsBoolean()) {
            FRAME = (blockState, blockGetter, blockPos) -> blockState.is(Constants.NETHER_PORTAL_FRAME_TAG) && !blockState.is(Constants.NOT_NETHER_PORTAL_FRAME_TAG);
        }
    }
}
