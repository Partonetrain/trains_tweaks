package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.powderwalking.PowderWalkingFeatureConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderWalking_PowderSnowMixin {
    @Inject(method = "canEntityWalkOnPowderSnow(Lnet/minecraft/world/entity/Entity;)Z", at=@At("HEAD"), cancellable = true)
    private static void trains_tweaks$canEntityWalkOnPowderSnow(Entity entity, CallbackInfoReturnable<Boolean> cir){
        if(!AllFeatures.POWDER_WALKING_FEATURE.isIncompatibleLoaded() && PowderWalkingFeatureConfig.ENABLED.getAsBoolean()){
            if(entity instanceof LivingEntity le){
                if(le.getItemBySlot(EquipmentSlot.FEET).is(Constants.POWDER_WALKER_ARMOR_TAG)
                    || le.getItemBySlot(EquipmentSlot.LEGS).is(Constants.POWDER_WALKER_ARMOR_TAG)
                    || le.getItemBySlot(EquipmentSlot.CHEST).is(Constants.POWDER_WALKER_ARMOR_TAG)
                    || le.getItemBySlot(EquipmentSlot.HEAD).is(Constants.POWDER_WALKER_ARMOR_TAG)
                    || le.getItemBySlot(EquipmentSlot.BODY).is(Constants.POWDER_WALKER_ARMOR_TAG)){ //for wolves horse etc
                        cir.setReturnValue(true);
                }

                if(le.getItemBySlot(EquipmentSlot.MAINHAND).is(Constants.POWDER_WALKER_ITEM_TAG)
                        || le.getItemBySlot(EquipmentSlot.OFFHAND).is(Constants.POWDER_WALKER_ITEM_TAG)){
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
