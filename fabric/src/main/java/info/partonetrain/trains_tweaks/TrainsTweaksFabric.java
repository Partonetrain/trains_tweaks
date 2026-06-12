package info.partonetrain.trains_tweaks;

import info.partonetrain.trains_tweaks.feature.jumpy.JumpyFeature;
import info.partonetrain.trains_tweaks.feature.jumpy.JumpyFeatureConfig;
import info.partonetrain.trains_tweaks.feature.quasi.QuasiFeature;
import info.partonetrain.trains_tweaks.feature.utilitycommands.KillNonPlayersCommand;
import info.partonetrain.trains_tweaks.feature.utilitycommands.UtilityCommandsFeature;
import net.fabricmc.api.ModInitializer;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.config.ModConfig;

public class TrainsTweaksFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        for(ModFeature mf : AllFeatures.features){
            if(mf instanceof IEarlyConfigReader earlyConfigReader && !earlyConfigReader.isExtraEarly()){
                earlyConfigReader.readConfigsEarly();
            }

            if(mf.configSpec != null){
                NeoForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON,
                        mf.configSpec, mf.getConfigPath());
            }

            if(mf.getFeatureName().equals("UtilityCommands") && UtilityCommandsFeature.enabled){
                if(UtilityCommandsFeature.addKillNonPlayer){
                    CommandRegistrationCallback.EVENT.register((dispatcher, context, selection) ->
                            KillNonPlayersCommand.register(dispatcher));
                }
            }

            if(mf.getFeatureName().equals("Jumpy") && JumpyFeature.enabled){
                ServerEntityEvents.ENTITY_LOAD.register(((entity, serverLevel) -> {

                    if(JumpyFeatureConfig.JUMP_WHILE_MOVING.getAsBoolean()) {
                        if (entity instanceof Mob mob && mob.getType().is(Constants.JUMPS_WHILE_MOVING_TAG)) {
                            JumpyFeature.addJumpWhileMovingGoal(mob);
                        }
                    }

                    if(JumpyFeatureConfig.JUMP_RANDOMLY.getAsBoolean()) {
                        if (entity instanceof Mob mob && mob.getType().is(Constants.JUMPS_RANDOMLY_TAG)) {
                            JumpyFeature.addJumpRandomlyGoal(mob);
                        }
                    }

                }));
            }

            if(mf.getFeatureName().equals("Quasi") && QuasiFeature.enabled){
                UseBlockCallback.EVENT.register((player, world, hand, blockHitResult) -> {
                    if (world instanceof ServerLevel sl && hand == InteractionHand.MAIN_HAND &&
                            !player.isSpectator() && player.getItemInHand(hand).isEmpty()) {
                        BlockPos blockPos = blockHitResult.getBlockPos();
                        BlockState state = world.getBlockState(blockPos);
                        Block block = state.getBlock();
                        if(block instanceof DispenserBlock && QuasiFeature.isDispenserUsable()){
                            if(player.isShiftKeyDown()) {
                                boolean removedFromSave = QuasiFeature.updateCoordsInLevelData(sl, blockPos);
                                QuasiFeature.sendPlayerMessage((ServerPlayer) player, state, blockPos, removedFromSave);
                                return InteractionResult.CONSUME;
                            }
                        }
                        else if(block instanceof PistonBaseBlock && QuasiFeature.isPistonUsable()){
                            if(player.isShiftKeyDown()) {
                                boolean removedFromSave = QuasiFeature.updateCoordsInLevelData(sl, blockPos);
                                QuasiFeature.sendPlayerMessage((ServerPlayer) player, state, blockPos, removedFromSave);
                                player.swing(hand, true); //idk why but it does this automatically on dispenser
                                return InteractionResult.CONSUME;
                            }
                        }

                    }
                    return InteractionResult.PASS;
                });
            }

        }

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            /*
            if(InterdimensionalFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.INTERDIMENSIONAL_FEATURE.isIncompatibleLoaded()){
                if(!AutoResetHandler.configParsed && !InterdimensionalFeatureConfig.AUTO_RESET.get().isBlank()){
                    AutoResetHandler.parseAutoResets();
                }
            }

            if(InterdimensionalFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.INTERDIMENSIONAL_FEATURE.isIncompatibleLoaded()) {
                if(!InterdimensionalFeatureConfig.AUTO_WORLDBORDER.get().isBlank()){
                    if(!InterdimensionalFeature.autoWorldbordersParsed){
                        InterdimensionalFeature.parseAutoBorders();
                    }

                    for(ResourceKey<Level> levelKey : InterdimensionalFeature.autoBorders.keySet()){
                        if(server.getLevel(levelKey) instanceof ServerLevel serverLevel) {
                            InterdimensionalFeature.applyAutoBorders(serverLevel);
                        }
                    }
                }
            }
             */
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            /*
            if(InterdimensionalFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.INTERDIMENSIONAL_FEATURE.isIncompatibleLoaded()){
                if(!InterdimensionalFeatureConfig.AUTO_RESET.get().isBlank()){
                    AutoResetHandler.tick(server);
                }
            }
            */
        });

        ServerWorldEvents.LOAD.register((server, serverLevel) -> {

        });


        CommonClass.init();
    }
}
