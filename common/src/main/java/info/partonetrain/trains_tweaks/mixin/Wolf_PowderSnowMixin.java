package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.powderwalking.PowderWalkingFeatureConfig;
import info.partonetrain.trains_tweaks.feature.wolf.WolfFeatureConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.WolfVariants;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class Wolf_PowderSnowMixin {
    @Inject(method = "canEntityWalkOnPowderSnow(Lnet/minecraft/world/entity/Entity;)Z", at=@At("HEAD"), cancellable = true)
    private static void trains_tweaks$canWolfWalkOnPowderSnow(Entity entity, CallbackInfoReturnable<Boolean> cir){
        if(!AllFeatures.WOLF_FEATURE.isIncompatibleLoaded() && WolfFeatureConfig.ENABLED.getAsBoolean() && WolfFeatureConfig.SNOWY_WOLF_WALKS_ON_POWDER_SNOW.getAsBoolean()){
            if(entity instanceof Wolf wolf){
                cir.setReturnValue(wolf.getVariant().equals(WolfVariants.SNOWY));
            }
        }
    }
}
