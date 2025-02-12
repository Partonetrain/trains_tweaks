package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.utilitycommands.UtilityCommandsFeature;
import info.partonetrain.trains_tweaks.feature.utilitycommands.UtilityCommandsFeatureConfig;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Commands.class)
public class NeoForge_UtilityCommands_CommandsMixin {
    @WrapOperation(method = "<init>(Lnet/minecraft/commands/Commands$CommandSelection;Lnet/minecraft/commands/CommandBuildContext;)V", at= @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/gametest/GameTestHooks;isGametestEnabled()Z"))
    private boolean trains_tweaks$init(Operation<Boolean> original) {
        if (!AllFeatures.UTILITY_COMMANDS_FEATURE.isIncompatibleLoaded() && UtilityCommandsFeature.enabled && UtilityCommandsFeature.vanillaDebugCommands) {
            return true;
        }
        return original.call();
    }
}
