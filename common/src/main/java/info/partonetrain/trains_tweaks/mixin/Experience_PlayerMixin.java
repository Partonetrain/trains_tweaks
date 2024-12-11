package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.experience.ExperienceFeatureConfig;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class Experience_PlayerMixin {
    @Shadow
    public int experienceLevel;

    @Inject(at = @At("HEAD"),
            method = "getXpNeededForNextLevel()I",
            cancellable = true)
    private void trains_tweaks$getXpNeededForNextLevel(CallbackInfoReturnable<Integer> cir) {
        if(!AllFeatures.EXPERIENCE_FEATURE.isIncompatibleLoaded() && ExperienceFeatureConfig.ENABLED.getAsBoolean()) {
            int cap = ExperienceFeatureConfig.LEVEL_CAP.getAsInt();
            if (cap > 0 && experienceLevel >= cap) {
                cir.setReturnValue(ExperienceFeatureConfig.CAPPED_XP.getAsInt());
            } else {
                if (ExperienceFeatureConfig.CURVE_MODE.getAsBoolean()) {
                    cir.setReturnValue(experienceLevel == 0 ? ExperienceFeatureConfig.BASE_XP.get() : ExperienceFeatureConfig.BASE_XP.getAsInt() + (experienceLevel * ExperienceFeatureConfig.CURVE_MODE_MULTIPLIER.getAsInt()));
                } else {
                    cir.setReturnValue(ExperienceFeatureConfig.BASE_XP.get());
                }
            }
            cir.cancel();
        }
    }
}
