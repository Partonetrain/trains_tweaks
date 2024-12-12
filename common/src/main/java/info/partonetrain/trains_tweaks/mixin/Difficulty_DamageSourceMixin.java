package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.difficulty.DifficultyFeature;
import info.partonetrain.trains_tweaks.feature.difficulty.DifficultyFeatureConfig;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(DamageSource.class)
public class Difficulty_DamageSourceMixin {
    @Final
    @Shadow
    private Entity causingEntity;

    @Inject(method = "scalesWithDifficulty()Z", at=@At("HEAD"), cancellable = true)
    private void trains_tweaks$scalesWithDifficulty(CallbackInfoReturnable<Boolean> cir){
        if(!AllFeatures.DIFFICULTY_FEATURE.isIncompatibleLoaded() && DifficultyFeatureConfig.ENABLED.getAsBoolean() &&
                !DifficultyFeatureConfig.DAMAGE_SCALING.getAsBoolean() && causingEntity != null){
            cir.setReturnValue(false);
        }
    }

}
