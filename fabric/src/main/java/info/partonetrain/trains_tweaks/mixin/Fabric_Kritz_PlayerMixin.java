package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.feature.kritz.KritzFeature;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class Fabric_Kritz_PlayerMixin {
    @Inject(method = "createAttributes", at = @At("RETURN"))
    private static void trains_tweaks$createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        if(KritzFeature.enabled && KritzFeature.addAttributes) {
            cir.getReturnValue().add(KritzFeature.MELEE_CRIT_CHANCE);
            cir.getReturnValue().add(KritzFeature.RANGED_CRIT_CHANCE);
        }
    }
}
