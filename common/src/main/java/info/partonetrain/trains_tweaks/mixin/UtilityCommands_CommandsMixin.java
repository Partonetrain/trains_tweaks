package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.utilitycommands.UtilityCommandsFeatureConfig;
import net.minecraft.commands.Commands;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Commands.class)
public class UtilityCommands_CommandsMixin {

    @WrapOperation(method = "<init>(Lnet/minecraft/commands/Commands$CommandSelection;Lnet/minecraft/commands/CommandBuildContext;)V", at= @At(value = "FIELD", target = "Lnet/minecraft/SharedConstants;IS_RUNNING_IN_IDE:Z", opcode = Opcodes.GETSTATIC))
    private boolean trains_tweaks$init(Operation<Boolean> original) {
        if (!AllFeatures.UTILITY_COMMANDS_FEATURE.isIncompatibleLoaded() && UtilityCommandsFeatureConfig.ENABLED.getAsBoolean() && UtilityCommandsFeatureConfig.VANILLA_DEBUG_COMMANDS.getAsBoolean()) {
            return true;
        }
        return original.call();
    }
}
