package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ConduitBlockEntity.class)
public class AttackSpeed_ConduitBlockEntityMixin {
    @Inject(method = "applyEffects", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"))
    private static void trains_tweaks$applyEffects(Level level, BlockPos pos, List<BlockPos> positions, CallbackInfo ci, @Local Player player){
        if (!AllFeatures.ATTACK_SPEED_FEATURE.isIncompatibleLoaded() && AttackSpeedFeatureConfig.ENABLED.getAsBoolean()
                && AttackSpeedFeatureConfig.ADD_HASTE_TO_CONDUIT.getAsBoolean()) {
            if(player.hasEffect(MobEffects.CONDUIT_POWER)){
                MobEffectInstance mei = player.getEffect(MobEffects.CONDUIT_POWER);
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, mei.getDuration(), mei.getAmplifier(), mei.isAmbient(), mei.isVisible()));
            }
        }
    }
}
