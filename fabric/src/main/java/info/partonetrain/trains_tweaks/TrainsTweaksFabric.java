package info.partonetrain.trains_tweaks;

import info.partonetrain.trains_tweaks.feature.jumpy.JumpyFeature;
import info.partonetrain.trains_tweaks.feature.jumpy.JumpyFeatureConfig;
import info.partonetrain.trains_tweaks.feature.utilitycommands.KillNonPlayersCommand;
import info.partonetrain.trains_tweaks.feature.utilitycommands.UtilityCommandsFeature;
import net.fabricmc.api.ModInitializer;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.world.entity.Mob;
import net.neoforged.fml.config.ModConfig;

public class TrainsTweaksFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        for(ModFeature mf : AllFeatures.features){
            if(mf instanceof IEarlyConfigReader earlyConfigReader){
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
        }
        CommonClass.init();
    }
}
