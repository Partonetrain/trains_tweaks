package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.npc.NpcFeatureConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractIllager.class)
public class Npc_AbstractIllagerMixin {
    @WrapOperation(method = "canAttack", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isBaby()Z"))
    public boolean trains_tweaks$wantsToPickUp(LivingEntity instance, Operation<Boolean> original){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean()&& NpcFeatureConfig.ILLAGERS_ATTACK_BABIES.getAsBoolean()) {
            return false;
            //this is an elvis operator, so returning false will fall through to super.canAttack()
        }
        return original.call(instance);
    }
}
