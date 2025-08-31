package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.difficulty.DifficultyFeature;
import info.partonetrain.trains_tweaks.feature.difficulty.DifficultyFeatureConfig;
import net.minecraft.client.gui.screens.social.PlayerEntry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.UUID;

@Mixin(LivingEntity.class)
public class Difficulty_LivingEntityMixin {
    @ModifyArgs(method = "hurt", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", ordinal = 1))
    public void trains_tweaks$hurt(Args args){
        if (!AllFeatures.DIFFICULTY_FEATURE.isIncompatibleLoaded() && DifficultyFeatureConfig.ENABLED.getAsBoolean()
                && DifficultyFeatureConfig.MODIFIED_DIFFICULTY_DAMAGE_MULTIPLIER.getAsDouble() != DifficultyFeatureConfig.MODIFIED_DIFFICULTY_DAMAGE_MULTIPLIER.getDefault()) {

            DamageSource src = args.get(0);
            float dmg = args.get(1);

            if(src.getEntity() instanceof Player player){
                UUID uuid = player.getGameProfile().getId();
                boolean isModified = DifficultyFeature.modifiedPlayers.contains(uuid);
                if(isModified){
                    float newDmg = (float) (dmg * DifficultyFeatureConfig.MODIFIED_DIFFICULTY_DAMAGE_MULTIPLIER.getAsDouble());
                    args.set(1, newDmg);
                }
            }
        }
    }
}
