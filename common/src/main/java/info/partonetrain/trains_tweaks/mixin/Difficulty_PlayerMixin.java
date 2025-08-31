package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.difficulty.DifficultyFeature;
import info.partonetrain.trains_tweaks.feature.difficulty.DifficultyFeatureConfig;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.UUID;

@Mixin(Player.class)
public class Difficulty_PlayerMixin {

    /*
    //this way only works with melee damage. see Difficulty_LivingEntityMixin
    //
    @ModifyArg(method = "attack(Lnet/minecraft/world/entity/Entity;)V", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), index = 1)
    public float trains_tweaks$attack(float amount){
        if (!AllFeatures.DIFFICULTY_FEATURE.isIncompatibleLoaded() && DifficultyFeatureConfig.ENABLED.getAsBoolean()
                && DifficultyFeatureConfig.MODIFIED_DIFFICULTY_DAMAGE_MULTIPLIER.getAsDouble() != DifficultyFeatureConfig.MODIFIED_DIFFICULTY_DAMAGE_MULTIPLIER.getDefault()) {
            Player self = (Player)(Object)this;
            UUID uuid = self.getGameProfile().getId();
            boolean isModified = DifficultyFeature.modifiedPlayers.contains(uuid);
            if(isModified){
                return (float) (amount * DifficultyFeatureConfig.MODIFIED_DIFFICULTY_DAMAGE_MULTIPLIER.getAsDouble());
            }
        }
        return amount;
    }
     */

    @ModifyArg(method = "hurt", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), index = 1)
    public float trains_tweaks$hurt(float amount){
        if (!AllFeatures.DIFFICULTY_FEATURE.isIncompatibleLoaded() && DifficultyFeatureConfig.ENABLED.getAsBoolean()
                && DifficultyFeatureConfig.MODIFIED_DIFFICULTY_DAMAGE_RECEIVED_MULTIPLIER.getAsDouble() != DifficultyFeatureConfig.MODIFIED_DIFFICULTY_DAMAGE_RECEIVED_MULTIPLIER.getDefault()) {
            Player self = (Player)(Object)this;
            UUID uuid = self.getGameProfile().getId();
            boolean isModified = DifficultyFeature.modifiedPlayers.contains(uuid);
            if(isModified){
                return (float) (amount * DifficultyFeatureConfig.MODIFIED_DIFFICULTY_DAMAGE_RECEIVED_MULTIPLIER.getAsDouble());
            }
        }
        return amount;
    }

}
