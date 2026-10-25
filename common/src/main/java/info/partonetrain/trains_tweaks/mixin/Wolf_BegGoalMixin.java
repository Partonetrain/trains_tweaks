package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.wolf.WolfFeatureConfig;
import net.minecraft.world.entity.ai.goal.BegGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BegGoal.class)
public class Wolf_BegGoalMixin {

    @Shadow
    @Final
    private Wolf wolf;


    @WrapOperation(method = "playerHoldingInteresting", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    public boolean trans_tweaks$playerHoldingInteresting(ItemStack instance, Item item, Operation<Boolean> original){
        if(WolfFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.WOLF_FEATURE.isIncompatibleLoaded() && WolfFeatureConfig.BETTER_BEGGING.getAsBoolean()){
            if(wolf.isTame()){
                return false;
            }
        }
        return original.call(instance, item);
    }

    //prevent wild wolves from tilting head if holding meat
    @WrapOperation(method = "playerHoldingInteresting", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Wolf;isFood(Lnet/minecraft/world/item/ItemStack;)Z"))
    public boolean trans_tweaks$playerHoldingInteresting2(Wolf instance, ItemStack stack, Operation<Boolean> original){
        if(WolfFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.WOLF_FEATURE.isIncompatibleLoaded() && WolfFeatureConfig.PREVENT_WILD_MEAT_FEEDING.getAsBoolean()){
            if(!wolf.isTame()){
                return false;
            }
        }
        return original.call(instance, stack);
    }
}
