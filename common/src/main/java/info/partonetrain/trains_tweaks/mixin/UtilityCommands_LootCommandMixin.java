package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.context.CommandContext;
import info.partonetrain.trains_tweaks.feature.utilitycommands.UtilityCommandsFeature;
import info.partonetrain.trains_tweaks.feature.utilitycommands.UtilityCommandsFeatureConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.LootCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;

@Mixin(LootCommand.class)
public class UtilityCommands_LootCommandMixin {
    //"Realm RPG Treasure Balloons", an mcreator mod, made me do this.

    //the loot command class is super dense with generics and I didn't see a super obvious place to inject
    //hopefully this does not affect any other situations with the loot command?
    //WHY IS THE LAST ARG NAMED "dropCOnsimer" IN THE SOURCE
    @ModifyArg(method = "dropChestLoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/commands/LootCommand;drop(Lcom/mojang/brigadier/context/CommandContext;Lnet/minecraft/core/Holder;Lnet/minecraft/world/level/storage/loot/LootParams;Lnet/minecraft/server/commands/LootCommand$DropConsumer;)I"), index = 2)
    private static LootParams injectLuck(LootParams params, @Local(argsOnly = true) CommandContext<CommandSourceStack> context) {
        if(UtilityCommandsFeature.enabled && UtilityCommandsFeatureConfig.LOOT_COMMAND_CONSIDERS_LUCK.getAsBoolean()){
            float totalNearbyLuck = 0.0f;
            AABB aabb = AABB.ofSize(context.getSource().getPosition(), 6.5, 6.5, 6.5);

            List<ServerPlayer> nearbyPlayers = context.getSource().getLevel().getEntitiesOfClass(ServerPlayer.class, aabb);
            for(ServerPlayer player : nearbyPlayers){
                totalNearbyLuck += player.getLuck();
            }

            return new LootParams.Builder(context.getSource().getLevel())
                    .withOptionalParameter(LootContextParams.THIS_ENTITY, context.getSource().getEntity())
                    .withParameter(LootContextParams.ORIGIN, context.getSource().getPosition())
                    .withLuck(totalNearbyLuck)
                    .create(LootContextParamSets.CHEST);

        }
        return params;
    }
}
