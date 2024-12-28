package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.yeet.YeetFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PointedDripstoneBlock.class)
public class Yeet_PointedDripstoneBlockMixin {
    @Inject(method = "onProjectileHit", at= @At(value = "TAIL"))
    public void trains_tweaks$onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile, CallbackInfo ci){
        if(!AllFeatures.YEET_FEATURE.isIncompatibleLoaded() && YeetFeatureConfig.ENABLED.getAsBoolean()
                && YeetFeatureConfig.ANY_IMPACT_BREAKS_DRIPSTONE.getAsBoolean()) {
            if (!level.isClientSide) {
                BlockPos blockpos = hit.getBlockPos();
                if (projectile.mayInteract(level, blockpos)
                        && projectile.mayBreak(level)
                        && projectile.getDeltaMovement().length() > 0.6) {
                    level.destroyBlock(blockpos, true);
                }
            }
        }
    }
}
