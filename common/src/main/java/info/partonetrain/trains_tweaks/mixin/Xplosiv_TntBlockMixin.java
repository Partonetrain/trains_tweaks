package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.xplosiv.XplosivFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TntBlock.class)
public class Xplosiv_TntBlockMixin extends Block {

    public Xplosiv_TntBlockMixin(Properties properties) {
        super(properties);
    }

    @Unique
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (!AllFeatures.XPLOSIV_FEATURE.isIncompatibleLoaded() && XplosivFeatureConfig.ENABLED.getAsBoolean() && XplosivFeatureConfig.UNSTABLE_TNT.getAsBoolean()) {
            return this.defaultBlockState().trySetValue(BlockStateProperties.UNSTABLE, true);
        }
        return this.defaultBlockState();
    }

    @Inject(method = "onPlace", at=@At("TAIL"))
    public void trains_tweaks$onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving, CallbackInfo ci) {
        if (!AllFeatures.XPLOSIV_FEATURE.isIncompatibleLoaded() && XplosivFeatureConfig.ENABLED.getAsBoolean() && XplosivFeatureConfig.INSTANT_TNT.getAsBoolean()) {
            TntBlock.explode(level, pos);
            level.removeBlock(pos, false);
        }
    }

}
