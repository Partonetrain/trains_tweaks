package info.partonetrain.trains_tweaks;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Constants.MOD_ID)
public class TrainsTweaksNeoforge {

    public TrainsTweaksNeoforge(ModContainer container, IEventBus eventBus) {
        for(ModFeature mf : AllFeatures.list){
            if(mf.configSpec != null) {
                container.registerConfig(ModConfig.Type.COMMON, mf.configSpec, mf.getConfigPath());
            }
        }
    }

}