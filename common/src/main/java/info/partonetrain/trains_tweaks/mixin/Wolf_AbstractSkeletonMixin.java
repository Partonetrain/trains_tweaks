package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.wolf.WolfFeatureConfig;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractSkeleton.class)
public class Wolf_AbstractSkeletonMixin {
    @WrapOperation(method = "registerGoals", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V", ordinal = 2))
    private void trains_tweaks$registerGoals(GoalSelector instance, int priority, Goal goal, Operation<Void> original){
        if(WolfFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.WOLF_FEATURE.isIncompatibleLoaded() && WolfFeatureConfig.DONT_SCARE_SKELETONS.getAsBoolean()){
            return;
        }
    }
}
