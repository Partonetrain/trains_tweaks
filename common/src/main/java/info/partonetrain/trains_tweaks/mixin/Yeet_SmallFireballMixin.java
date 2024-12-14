package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.yeet.YeetFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmallFireball.class)
public class Yeet_SmallFireballMixin {
    @Inject(method = "onHitBlock", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"), cancellable = true)
    public void trains_tweaks$onHitBlock(BlockHitResult result, CallbackInfo ci){
        if(!AllFeatures.YEET_FEATURE.isIncompatibleLoaded() && YeetFeatureConfig.ENABLED.getAsBoolean() && YeetFeatureConfig.THROW_FIRE_CHARGES.getAsBoolean()) {
            SmallFireball self = (SmallFireball)(Object)this;
            if(self.getOwner() instanceof Player p){
                Level level = p.level();
                BlockPos pos = result.getBlockPos();
                if(!CampfireBlock.isLitCampfire(level.getBlockState(pos))){
                    //I'm honestly not even sure how the projectile lights unlit campfires.
                    level.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
                }

                ci.cancel();
            }

        }
    }
}
