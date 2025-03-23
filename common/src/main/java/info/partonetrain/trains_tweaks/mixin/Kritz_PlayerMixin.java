package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.kritz.KritzFeature;
import info.partonetrain.trains_tweaks.feature.kritz.KritzFeatureConfig;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Player.class)
public class Kritz_PlayerMixin {
    @ModifyVariable(method = "attack(Lnet/minecraft/world/entity/Entity;)V", at = @At("STORE"), ordinal = 2) //"flag1"
    public boolean trains_tweaks$attack(boolean original){
        if (!AllFeatures.KRITZ_FEATURE.isIncompatibleLoaded() && KritzFeatureConfig.ENABLED.getAsBoolean() && KritzFeatureConfig.ADD_ATTRIBUTES.getAsBoolean()) {
            Player self = (Player)(Object)this;
            float attributeValue = (float) self.getAttributeValue(KritzFeature.MELEE_CRIT_CHANCE);
            return self.getRandom().nextDouble() < attributeValue;
        }
        return original;

    }

    @ModifyExpressionValue(method = "attack", at = @At(value = "CONSTANT", args = "floatValue=1.5"))
    public float trains_tweaks$attack2(float original) {
        if (!AllFeatures.KRITZ_FEATURE.isIncompatibleLoaded() && KritzFeatureConfig.ENABLED.getAsBoolean() && KritzFeatureConfig.KRIT_MULTIPLIER.getAsDouble() != KritzFeatureConfig.KRIT_MULTIPLIER.getDefault()) {
            KritzFeatureConfig.KRIT_MULTIPLIER.getAsDouble();
        }
        return original;
    }

}
